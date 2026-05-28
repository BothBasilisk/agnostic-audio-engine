import os
import torch
import librosa
import requests
import tempfile
from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
from transformers import ClapModel, ClapProcessor

app = FastAPI(title="Agnostic AI - Audio Encoder")

print("Loading CLAP... (might take a while)")
model_id = "laion/clap-htsat-unfused"
processor = ClapProcessor.from_pretrained(model_id)
model = ClapModel.from_pretrained(model_id)
model.eval()
print("Model ready")

class AudioRequest(BaseModel):
    audio_url: str

@app.post("/encode")
async def encode_audio(request: AudioRequest):
    tmp_path = None
    try:
        # --- 1. Download audio on a temp file ---
        headers = {"User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36"}
        response = requests.get(request.audio_url, stream=True, headers=headers)
        response.raise_for_status()
        
        with tempfile.NamedTemporaryFile(delete=False, suffix=".wav") as tmp_file:
            for chunk in response.iter_content(chunk_size=8192):
                tmp_file.write(chunk)
            tmp_path = tmp_file.name

        # --- 2. Load audio track with librosa ---
        audio_data, sr = librosa.load(tmp_path, sr=48000)

        # --- 3. Tensors ---
        inputs = processor(audios=audio_data, sampling_rate=48000, return_tensors="pt")
        
        # --- 4. Embedding extraction ---
        with torch.no_grad():
            outputs = model.get_audio_features(**inputs)

        embedding = outputs[0].tolist()

        return {"status": "success", "embedding": embedding}

    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))
    finally:
        if tmp_path and os.path.exists(tmp_path):
            os.remove(tmp_path)