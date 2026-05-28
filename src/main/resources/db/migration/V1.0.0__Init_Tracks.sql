CREATE EXTENSION IF NOT EXISTS vector;

CREATE TABLE tracks (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  artist VARCHAR(500),
  title VARCHAR(500),
  album VARCHAR(500),
  song_path VARCHAR(2000),
  audio_embedding VECTOR(512)
);

CREATE INDEX ON tracks USING hnsw (audio_embedding vector_cosine_ops);