package org.basilisk.dto.response;

public class AudioResponse {
	private String status;
	private float[] embedding;
	
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public float[] getEmbedding() {
		return this.embedding;
	}
	
	public void setEmbedding(float[] embedding) {
		this.embedding = embedding;
	}
}
