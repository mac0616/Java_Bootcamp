"""
/chat 하나로 모든 기능을 제공하는 FastAPI 통합 라우팅 챗봇이다.
Spring 학사 정보 조회, 학사규정 RAG 검색, 강좌 추천 기능을 도구로 등록하고
사용자의 질문에 따라 Gemini가 필요한 도구를 선택해 답변한다.
"""

import os
from contextlib import asynccontextmanager

import chromadb
import httpx
from chromadb.utils import embedding_functions
from dotenv import load_dotenv
from fastapi import FastAPI
from google import genai
from google.genai import types
from pydantic import BaseModel, Field

load_dotenv()

MODEL_NAME = "jhgan/ko-sroberta-multitask"
CHROMA_PATH = "./chroma_db"
GEMINI_MODEL = "gemini-3.5-flash"
SPRING_BASE_URL = os.getenv("SPRING_BASE_URL", "localhost")
TIMEOUT = httpx.Timeout(5.0)
SIMILARITY_THRESHOLD = 0.35

resources: dict = {}

# 애플리케이션 시작 시 모델과 DB 클라이언트를 한 번만 준비하고 종료 시 정리한다.
@asynccontextmanager
async def lifespan(app: FastAPI):
    print("초기화 중...")
    chroma = chromadb.PersistentClient(path=CHROMA_PATH)
    ef = embedding_functions.SentenceTransformerEmbeddingFunction(model_name=MODEL_NAME)
    resources["docs"] = chroma.get_or_create_collection(
        "lms_regulation", embedding_function=ef, metadata={"hnsw:space": "cosine"})
    resources["courses"] = chroma.get_or_create_collection(
        "lms_courses", embedding_function=ef, metadata={"hnsw:space": "cosine"})
    resources["genai"] = genai.Client(api_key=os.getenv("GOOGLE_API_KEY"))
    print("초기화 완료")
    # yield 이전은 서버가 시작될 때 한 번 실행되고, yield 이후는 서버가 종료될 때 실행된다.
    # Spring 서버는 별도 프로세스이므로 여기에서 시작하거나 종료하지 않는다.
    yield
    # 서버 종료 시 메모리에 보관한 클라이언트 참조를 정리한다.
    # 예: 테스트나 개발 서버 재시작 시 기존 자원을 남기지 않는다.
    resources.clear()


app = FastAPI(
    title="LMS AI Server!!",
    description="RAG + Function Calling 학습 예제",
    lifespan=lifespan
)

# ====================================================================================
# 요청과 응답에 사용하는 데이터 모델
class ChatMessage(BaseModel):
    role: str = Field(..., pattern="^(user|assistant)$")
    content: str


class ChatRequest(BaseModel):
    question: str = Field(..., min_length=1)
    student_id: int = Field(1, description="현재 로그인한 학생의 ID (Spring 조회에 사용)")
    history: list[ChatMessage] = Field(default_factory=list)


class ChatResponse(BaseModel):
    answer: str
    used_tools: list[str]   # Gemini가 답변을 만들 때 실제로 호출한 도구 이름 목록
# ====================================================================================


def search_regulation(query: str) -> dict:
    """LMS 학사규정에서 질문과 관련된 조항을 검색한다.
    출석, 수료, 평가, 재수강 등 학사규정에 관한 질문에 사용한다.
    개인의 수강 현황이나 성적 조회에는 사용하지 않는다.
    """
    results = resources["docs"].query(query_texts=[query], n_results=3)
    chunks = []
    for doc, meta, dist in zip(
        results["documents"][0], results["metadatas"][0], results["distances"][0]
    ):
        if (1 - dist) >= SIMILARITY_THRESHOLD:
            chunks.append({"page": meta["page"], "content": doc})
    if not chunks:
        return {"result": "학사규정에서 관련 내용을 찾지 못했습니다."}
    return {
        "regulations": chunks,
        "instruction": "각 조항을 근거로 답하고, 답변 끝에 (출처: 학사규정 p.N)을 표기하세요."
    }


# ======================================================================
# 도구 2~4: Spring DB 조회 (01에서 작성한 Spring 호출 함수를 그대로 사용한다.)
# ======================================================================
def _spring_get(path: str, params: dict | None = None) -> dict:
    """Spring API GET 요청을 공통 처리하는 내부 함수다."""
    try:
        resp = httpx.get(f"{SPRING_BASE_URL}{path}", params=params, timeout=TIMEOUT)
        resp.raise_for_status()
        return resp.json()
    except httpx.ConnectError:
        return {"error": "학사 정보 서버(Spring)에 연결할 수 없습니다."}
    except httpx.TimeoutException:
        return {"error": "학사 정보 서버의 응답이 지연되고 있습니다(5초 초과)."}
    except httpx.HTTPStatusError as e:
        return {"error": f"학사 정보 조회에 실패했습니다 (HTTP {e.response.status_code})"}


def get_my_courses(student_id: int) -> dict:
    """학생이 수강 중인 강좌와 각 강좌의 진도율을 조회한다.
    '내 강의', '수강 중인 강좌', '진도율' 질문에 사용한다."""
    return _spring_get(f"/api/students/{student_id}/courses")


def get_assignments_due(student_id: int, days: int = 7) -> dict:
    """학생의 마감 임박 과제 목록을 조회한다. '과제', '마감' 질문에 사용한다."""
    return _spring_get(f"/api/students/{student_id}/assignments/upcoming",
                       params={"days": days})


def get_my_summary(student_id: int) -> dict:
    """학생의 학습 요약(평균 진도율과 성적)을 조회한다.
    '내 성적', '학습 현황' 질문에 사용한다."""
    return _spring_get(f"/api/students/{student_id}/summary")


# ======================================================================
# 도구 5: 강좌 추천
# ======================================================================
def recommend_next_courses(course_code: str) -> dict:
    """기준 강좌 코드(예: C003)와 비슷한 다음 강좌를 추천한다.
    '다음에 들을 강좌', '강좌 추천' 질문에 사용한다.
    먼저 get_my_courses로 수강 중인 강좌 코드를 확인한 뒤 호출할 수 있다.
    """
    courses = resources["courses"]
    seed = courses.get(ids=[course_code])
    if not seed["ids"]:
        return {"error": f"강좌 코드를 찾을 수 없습니다: {course_code}"}
    results = courses.query(query_texts=[seed["documents"][0]], n_results=4)
    recs = [
        {"code": cid, "title": meta["title"],
         "category": meta["category"], "level": meta["level"]}
        for cid, meta in zip(results["ids"][0], results["metadatas"][0])
        if cid != course_code
    ]
    return {"based_on": seed["metadatas"][0]["title"], "recommendations": recs[:3]}
# ====================================================================================
# ====================================================================================

# ====================================================================================
# API 라우터
@app.post("/chat", response_model=ChatResponse)
def chat(req: ChatRequest):
    config = types.GenerateContentConfig(
        tools=[search_regulation, get_my_courses, get_assignments_due,
               get_my_summary, recommend_next_courses],
        system_instruction=(
            "너는 LMS 학습 도우미다. "
            f"현재 로그인한 학생의 ID는 {req.student_id}이다. "
            "학사규정에 관한 질문은 search_regulation을 사용하고 참고 자료만 근거로 답한다. "
            "학생 개인의 강의, 과제, 성적 정보는 반드시 Spring 조회 도구를 사용한다. "
            "질문에 필요한 경우 여러 도구를 순서대로 조합해서 사용한다. "
            "조회 결과에 없는 내용은 지어내지 않는다. "
            "함수가 error를 반환하면 그 상황을 정중하게 설명한다."
        ),
        temperature=0.2,
    )

    # 이전 대화를 Gemini contents 형식으로 변환한다. (assistant 역할은 model로 전달)
    contents: list = []
    for m in req.history:
        contents.append(types.Content(
            role="user" if m.role == "user" else "model",
            parts=[types.Part.from_text(text=m.content)],
        ))
    contents.append(types.Content(
        role="user", parts=[types.Part.from_text(text=req.question)]
    ))

    # 등록된 도구 호출과 결과 전달은 Gemini SDK의 자동 Function Calling이 처리한다.
    response = resources["genai"].models.generate_content(
        model=GEMINI_MODEL, contents=contents, config=config
    )

    # 자동 Function Calling 기록에서 실제 호출한 도구 이름을 추출한다.
    used = []
    for content in (response.automatic_function_calling_history or []):
        for part in (content.parts or []):
            if part.function_call:
                used.append(part.function_call.name)

    return ChatResponse(answer=response.text or "(응답이 없습니다)", used_tools=used)

# ====================================================================================
