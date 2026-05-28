FROM python:3.10-slim

# C dependencies for audio elaboration (libsndfile)
RUN apt-get update && apt-get install -y libsndfile1 && rm -rf /var/lib/apt/lists/*

WORKDIR /app

# Copy and install dependencies from requirements.txt
COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

# Main code
COPY main.py .

EXPOSE 8001

# Starting AI server
CMD ["uvicorn", "main:app", "--host", "0.0.0.0", "--port", "8001"]