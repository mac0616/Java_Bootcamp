import os
from pathlib import Path

import chromadb
from chromadb.utils import embedding_functions
from dotenv import load_dotenv
from google import genai
from google.genai import types

BASE_DIR = Path(__file__).parent
load_dotenv(BASE_DIR / ".env")

# 임베딩 모델, Chroma 컬렉션, AI 모델
MODEL_NAME = "jhgan/ko-sroberta-multitask"
CHROMA_PATH = str(BASE_DIR / "chroma_db")
DOC_COLLECTION = "lms_regulation"
GEMINI_MODEL = "gemini-3.5-flash"

# 검색 결과가 0.35 미만의 유사도이면 규정에 없는 질문으로 판단한다.
SIMILARITY_THRESHOLD = 0.35

client = genai.Client(api_key=os.getenv("GOOGLE_API_KEY"))


# RAG: Retrieval(검색), Augmentation(증강), Generation(생성)
def rag_answer(question: str) -> str:
    # 1. 검색: VectorDB에 저장해둔 PDF 청크를 검색한다.
    chunks = retrieve(question)

    if not chunks:
        return "규정에서 관련 내용을 찾지 못했습니다. 운영 지원팀에 문의해주세요."

    # 2. 증강: 검색된 내용을 바탕으로 프롬프트를 만든다.
    prompt = build_prompt(question, chunks)

    # 3. 생성: 검색 및 증강된 내용을 바탕으로 AI에게 답변을 요청한다.
    return generate(prompt)


# RAG 1단계: 검색
def retrieve(question: str, top_k: int = 3) -> list[dict]:
    chroma = chromadb.PersistentClient(path=CHROMA_PATH)

    collection = chroma.get_or_create_collection(
        name=DOC_COLLECTION,
        embedding_function=embedding_functions.SentenceTransformerEmbeddingFunction(
            model_name=MODEL_NAME,
        ),
        metadata={"hnsw:space": "cosine"},
    )

    results = collection.query(query_texts=[question], n_results=top_k)

    chunks = []
    for doc, meta, dist in zip(
        results["documents"][0],
        results["metadatas"][0],
        results["distances"][0],
    ):
        similarity = 1 - dist
        if similarity >= SIMILARITY_THRESHOLD:
            chunks.append(
                {
                    "content": doc,
                    "page": meta["page"],
                    "similarity": similarity,
                }
            )

    return chunks


# RAG 2단계: 증강
def build_prompt(question: str, chunks: list[dict]) -> str:
    context = "\n\n".join(
        f"[자료 {i + 1}] (학사규정 {c['page']}페이지)\n{c['content']}"
        for i, c in enumerate(chunks)
    )

    return f"""당신은 학원의 LMS 학습 도우미입니다.
아래 '참고 자료'에 있는 내용만을 근거로 질문에 답하세요.

규칙:
- 참고 자료에 없는 내용은 절대 지어내지 말 것
- 참고 자료로 답할 수 없으면 "규정에서 관련 내용을 찾지 못했습니다"라고 답할 것
- 답변 끝에 근거로 사용한 자료의 페이지를 (출처: 학사규정 p.N) 형식으로 표기할 것

[참고 자료]
{context}

[질문]
{question}"""


# RAG 3단계: 생성
def generate(prompt: str) -> str:
    response = client.models.generate_content(
        model=GEMINI_MODEL,
        contents=prompt,
        config=types.GenerateContentConfig(temperature=0.2),
    )

    return response.text


if __name__ == "__main__":
    question = "동영상 강의 출석 인정 기준이 뭐야? 구체적인 진도율 퍼센트로 답해줘."
    print(f"질문: {question}")
    print("-" * 60)
    print(rag_answer(question))
    print()
    print("01번 파일의 답변과 02파일의 답변을 비교해보자!")
    print("01번은 자기가 알아서 규정을 만들어내고 답변을 했다.")
    print("RAG 가 적용된 02번은 사내규정을 바탕으로 답변을 해주게 된다.")

for q in [
        # 리스트에 질문 문자열을 모아두면 같은 처리 코드를 반복 작성하지 않아도 된다.
        "과제를 마감 이후에 제출하면 점수가 어떻게 되나요?",
        "수료 조건을 알려주세요",
        "과제할 때 ChatGPT 같은 AI를 써도 되나요?",
    ]:
        print("\n" + "=" * 60)
        print(f"질문: {q}")
        print("-" * 60)
        print(rag_answer(q))


print("\n" + "=" * 60)
q = "오늘 점심 메뉴 추천해줘"
print(f"질문: {q}")
print("-" * 60)
print(rag_answer(q))

# ==============================================
