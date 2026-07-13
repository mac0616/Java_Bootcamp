from fastapi import FastAPI
from config import settings

app = FastAPI(title=settings.app_name)

@app.get("/config")
def config() :
    return {
        "api_name" : settings.app_name,
        "model_name" : settings.model_name,
        "api_key" : settings.api_key
    }