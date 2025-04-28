import os

import asyncpg
import joblib
import numpy as np
import pandas as pd
from fastapi import FastAPI
from pydantic import BaseModel
from sklearn.tree import DecisionTreeRegressor

app = FastAPI()

DB_USER = os.getenv("DB_USER", "postgres")
DB_PASSWORD = os.getenv("DB_PASSWORD", "postgres")
DB_HOST = os.getenv("DB_HOST", "127.0.0.1")
DB_PORT = os.getenv("DB_PORT", "5433")
DB_NAME = os.getenv("DB_NAME", "no_smoking")

user_models = {}


class PredictRequest(BaseModel):
    user_id: int
    weekday: int
    is_holiday: bool


async def load_data_for_user(user_id):
    conn = await asyncpg.connect(
        user=DB_USER,
        password=DB_PASSWORD,
        database=DB_NAME,
        host=DB_HOST,
        port=DB_PORT,
    )
    rows = await conn.fetch(
        "SELECT weekday, is_holiday, cigarettes_smoked_count FROM ns_daily_smoking_data WHERE user_id = $1", user_id
    )
    await conn.close()

    data = pd.DataFrame(rows, columns=["weekday", "is_holiday", "cigarettes_smoked_count"])
    return data


async def train(user_id):
    data = await load_data_for_user(user_id)
    if data.empty:
        raise ValueError(f"No training data available for user {user_id}")

    X = data[["weekday", "is_holiday"]]
    y = data["cigarettes_smoked_count"]

    model = DecisionTreeRegressor()
    model.fit(X, y)

    model_path = f"/models/model_user_{user_id}.pkl"
    os.makedirs(os.path.dirname(model_path), exist_ok=True)
    joblib.dump(model, model_path)

    user_models[user_id] = model


@app.post("/predict")
def predict(req: PredictRequest):
    model = user_models.get(req.user_id)

    if model is None:
        return {"error": f"Model not trained for user {req.user_id}"}

    input_data = np.array([[req.weekday, int(req.is_holiday)]])
    prediction = model.predict(input_data)
    return {
        "predicted_cigarettes": max(0, int(prediction[0]))
    }


@app.post("/train/{user_id}")
async def train_model(user_id: int):
    try:
        await train(user_id)
        return {"message": f"✅ Model retrained successfully for user {user_id}"}
    except Exception as e:
        return {"error": str(e)}
