"""
LangChain을 사용한 RAG 예제입니다.

02_rag_pure.py에서는 검색, 프롬프트 구성, 생성 과정을 직접 연결했습니다.
LangChain을 사용하면 각 단계를 공통 인터페이스로 연결할 수 있어
나중에 LLM이나 VectorDB를 바꿀 때 수정 범위를 줄일 수 있습니다.
"""

from pathlib import Path

from dotenv import load_dotenv
from langchain_chroma import Chroma
from langchain_core.output_parsers import StrOutputParser
from langchain_core.prompts import ChatPromptTemplate
from langchain_core.runnables import RunnablePassthrough
from langchain_google_genai import ChatGoogleGenerativeAI
from langchain_huggingface import HuggingFaceEmbeddings

BASE_DIR = Path(__file__).parent
load_dotenv(BASE_DIR / ".env")

MODEL_NAME = "jhgan/ko-sroberta-multitask"
CHROMA_PATH = str(BASE_DIR / "chroma_db")
DOC_COLLECTION = "lms_regulation"
GEMINI_MODEL = "gemini-3.5-flash"

# =============================================================
# RAG 1. 검색
embeddings = HuggingFaceEmbeddings(model_name=MODEL_NAME)
vectorstore = Chroma(
    collection_name=DOC_COLLECTION,
    persist_directory=CHROMA_PATH,
    embedding_function=embeddings,
)

# as_retriever(): 질문 문자열을 관련 Document 목록으로 변환한다.
# search_kwargs의 k는 가장 유사한 청크를 몇 개 가져올지 정한다.
retriever = vectorstore.as_retriever(search_kwargs={"k": 3})


def format_docs(docs) -> str:
    return "\n\n".join(
        f"[자료 {i + 1}] (학사규정 {d.metadata['page']}페이지)\n{d.page_content}"
        for i, d in enumerate(docs)
    )


# =============================================================
# RAG 2. 증강
prompt = ChatPromptTemplate.from_messages(
    [
        (
            "system",
            "당신은 LMS 학습 도우미입니다.\n"
            "아래 '참고 자료'에 있는 내용만을 근거로 질문에 답하세요.\n\n"
            "규칙:\n"
            "- 참고 자료에 없는 내용은 절대 지어내지 말 것\n"
            "- 참고 자료로 답할 수 없으면 \"규정에서 관련 내용을 찾지 못했습니다\"라고 답할 것\n"
            "- 답변 끝에 (출처: 학사규정 p.N) 형식으로 근거 페이지를 표기할 것\n\n"
            "[참고 자료]\n{context}",
        ),
        ("human", "{question}"),
    ]
)


# =============================================================
# RAG 3. 생성
llm = ChatGoogleGenerativeAI(model=GEMINI_MODEL, temperature=0.2)
parser = StrOutputParser()


# =============================================================
# 검색 -> 증강 -> 생성 조립
rag_chain = (
    {
        "context": retriever | format_docs,
        "question": RunnablePassthrough(),
    }
    | prompt
    | llm
    | parser
)


if __name__ == "__main__":
    question = "동영상 강의 출석 인정 기준이 뭐야?"
    print(f"질문: {question}")
    print("-" * 60)
    print(rag_chain.invoke(question))