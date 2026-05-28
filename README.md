# 🧠 Agnostic Intelligence Layer (AIL) 🎵

![Java 21](https://img.shields.io/badge/Java-21-orange)
![Quarkus](https://img.shields.io/badge/Quarkus-3.x-blue)
![Python](https://img.shields.io/badge/Python-3.10-yellow)
![PostgreSQL](https://img.shields.io/badge/pgvector-Vector_DB-blue)
![License](https://img.shields.io/badge/License-MIT-green)

A self-hosted, privacy-first, zero-shot semantic audio search engine. 

Traditional music cataloging relies on manual textual tags (Genre, BPM, Mood). **AIL changes the game.** It uses Deep Learning (CLAP Neural Networks) to "listen" to audio files, extracts their acoustic DNA into a 512-dimensional latent space, and uses Vector Databases (`pgvector`) to find acoustically similar tracks in milliseconds.

## ✨ Core Features
* 🕵️ **100% Private & Air-Gapped:** No external APIs (like Spotify). Your audio files never leave your local network/S3.
* 🎧 **Acoustic Semantic Search:** Find "tracks that sound exactly like this one" using Cosine Similarity.
* 🚀 **High Performance:** Java/Quarkus reactive orchestrator paired with a stateless Python FastAPI DSP engine.
* 📦 **Batteries Included:** Comes with local S3 storage (MinIO) and Vector DB ready to go.

## 🏗️ Architecture
1. **Core Orchestrator:** Quarkus (Java) handles routing, transactions, and REST APIs.
2. **DSP Engine:** Python FastAPI worker running HuggingFace's CLAP model. Converts `.wav/.mp3` to `float[512]` tensors.
3. **Vector Memory:** PostgreSQL + `pgvector` extension for sub-millisecond HNSW spatial queries.
4. **Storage:** MinIO (Local S3 clone) to host audio files securely.

---

## 🚀 Quick Start (One-Click Setup)

### 1. Start the Infrastructure
Launch the Database, Storage, and AI Engine in one command:
```bash
docker compose up -d
```
(Note: The first run might take a few minutes as the Python container downloads the Neural Network weights).

### 2. Upload your Audio Files
1. Open MinIO UI at http://localhost:9001 (User: admin_user, Pass: admin_password).
2. Go to Buckets -> audio-catalog -> Click Upload.
3. Upload some .mp3 or .wav files.

### 3. Start the Quarkus Orchestrator
code
```bash
./mvnw clean compile quarkus:dev
```

### 4. Play with the AI! 🪄
Open the interactive Swagger UI and test the APIs directly from your browser:
👉 http://localhost:8080/q/swagger-ui
1. Use POST /api/tracks to ingest a track (Use the MinIO url: http://minio:9000/audio-catalog/your-file.mp3).
2. Use GET /api/tracks/{id}/similar to let the AI find the closest acoustic matches!

Built with ❤️ for the Audio Engineering and Data Science community.