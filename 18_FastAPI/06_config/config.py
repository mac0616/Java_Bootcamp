import os
from dotenv import load_dotenv

# .env 를 읽어서 환경변수로 등록
load_dotenv()


class Settings:
    app_name = os.getenv("APP_NAME")
    api_key = os.getenv("API_KEY")
    model_name = os.getenv("MODEL_NAME")


settings = Settings()