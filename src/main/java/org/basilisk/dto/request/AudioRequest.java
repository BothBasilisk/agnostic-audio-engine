package org.basilisk.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AudioRequest {
	@JsonProperty("audio_url")
	private String audioUrl;
	
	public AudioRequest(String audioUrl) {
		this.audioUrl = audioUrl;
	}
	
	public String getAudioUrl() {
		return this.audioUrl;
	}
	
	public void setAudioUrl(String audioUrl) {
		this.audioUrl = audioUrl;
	}
}
