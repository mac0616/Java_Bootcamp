"""
[목표]
1. LMS 규정이 담긴 사내 문서 PDF에서 Text를 추출한다.
2. 긴 문서를 Chunk로 자르는 이유와 방법을 이해한다.
3. Chunk로 나눈 Text를 임베딩하여 VectorDB에 저장하고 검색한다.

긴 문서 전체를 한 번에 임베딩하면 하나의 벡터 안에 여러 주제가 섞여
검색 정확도가 떨어질 수 있다. 그래서 문서를 적당한 크기의 조각으로
나눈 뒤 각 조각을 따로 임베딩하고, 질문과 가장 가까운 조각을 찾는다.
"""

from pathlib import Path

import chromadb
from chromadb.utils import embedding_functions
from pypdf import PdfReader

# STEP 1. 사용할 모델, DB 경로, 컬렉션명, 청크 크기 설정
MODEL_NAME = "jhgan/ko-sroberta-multitask"
BASE_DIR = Path(__file__).parent
CHROMA_PATH = str(BASE_DIR / "chroma_db")
DOC_COLLECTION = "lms_regulation"

CHUNK_SIZE = 500
CHUNK_OVERLAP = 50


def extract_pages(pdf_path: str) -> list[dict]:
    """PDF의 각 페이지에서 텍스트를 추출한다."""
    reader = PdfReader(pdf_path)
    pages = []

    for page_num, page in enumerate(reader.pages, start=1):
        text = page.extract_text() or ""
        text = text.strip()
        if text:
            pages.append({"page": page_num, "text": text})

    print(
        f"PDF 추출 완료: 총 {len(reader.pages)}페이지 중 "
        f"{len(pages)}페이지에 텍스트 존재"
    )
    return pages


def chunk_text(
    text: str,
    size: int = CHUNK_SIZE,
    overlap: int = CHUNK_OVERLAP,
) -> list[str]:
    """텍스트를 size 글자 단위로 자르고, 다음 청크와 overlap만큼 겹치게 한다."""
    if overlap >= size:
        raise ValueError("overlap은 size보다 작아야 합니다.")

    if len(text) <= size:
        return [text]

    chunks = []
    start = 0

    while start < len(text):
        end = start + size
        chunk = text[start:end].strip()
        if chunk:
            chunks.append(chunk)
        start = end - overlap

    return chunks


def build_chunks(pages: list[dict]) -> tuple[list[str], list[dict], list[str]]:
    """페이지별 텍스트를 청크로 나누고 Chroma 저장용 documents, metadatas, ids를 만든다."""
    documents, metadatas, ids = [], [], []

    for page in pages:
        for i, chunk in enumerate(chunk_text(page["text"])):
            documents.append(chunk)
            metadatas.append({"source": "LMS 학사규정", "page": page["page"]})
            ids.append(f"p{page['page']}_c{i}")

    print(f"청킹 완료: {len(pages)}페이지 -> {len(documents)}개 청크")
    return documents, metadatas, ids


def get_collection():
    """LMS 규정 문서 컬렉션을 가져온다."""
    client = chromadb.PersistentClient(path=CHROMA_PATH)
    embedding_fn = embedding_functions.SentenceTransformerEmbeddingFunction(
        model_name=MODEL_NAME,
    )

    return client.get_or_create_collection(
        name=DOC_COLLECTION,
        embedding_function=embedding_fn,
        metadata={"hnsw:space": "cosine"},
    )


def ask(collection, question: str, top_k: int = 2):
    """질문과 가장 관련 있는 규정 청크를 찾아 출력한다."""
    results = collection.query(query_texts=[question], n_results=top_k)

    print(f"\n질문: \"{question}\"")
    for doc, meta, dist in zip(
        results["documents"][0],
        results["metadatas"][0],
        results["distances"][0],
    ):
        preview = doc[:120].replace("\n", " ")
        print(f"  [유사도 {1 - dist:.4f}] ({meta['source']} p.{meta['page']})")
        print(f"    {preview}...")


if __name__ == "__main__":
    pdf_path = BASE_DIR / "data" / "lms_regulation.pdf"

    pages = extract_pages(str(pdf_path))
    documents, metadatas, ids = build_chunks(pages)

    collection = get_collection()
    collection.upsert(documents=documents, metadatas=metadatas, ids=ids)
    print(f"저장 완료: 컬렉션 '{DOC_COLLECTION}'에 {collection.count()}개 청크")

    ask(collection, "운영자 강의 출석 인정 기준은 뭐야?")
    ask(collection, "과제를 늦게 내면 어떻게 돼?")
    ask(collection, "수료하려면 어떤 조건을 만족해야 해?")
    ask(collection, "환불 규정 알려줘")

# 규정에 없는 질문 ex) "점심 메뉴 추천" 을 넣은면 어떻게 될까?
# 기준선 (threshold) 을 설정해서 코사인 유사도 점수가 예를 들어 0.4 미만이면 답변할 수 없는 구조로 설계해야 한다.