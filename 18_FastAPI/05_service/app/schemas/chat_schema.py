from pydantic import BaseModel , Field

class ChatRequest(BaseModel):
    # ge : greater equals 크거나 같은 (~~ 이상) , le : less equals 작거나 같은 (~~이하)
    # el 표기법
    question : str = Field(min_length=1, max_length=10, description="사용자의 질문")

class ChatResponse(BaseModel):
    question : str
    answer : str
    model : str