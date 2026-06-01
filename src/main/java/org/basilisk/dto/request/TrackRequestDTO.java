package org.basilisk.dto.request;

import org.hibernate.validator.constraints.URL;

import jakarta.validation.constraints.NotBlank;

public class TrackRequestDTO {
	@NotBlank(message = "Artist can't be empty")
	private String artist;
	
	@NotBlank(message = "Title can't be empty")
    private String title;
	
	@NotBlank(message = "Song path is mandatory")
	@URL(message = "Song path must be a valid URL (ex. http://...)")
    private String songPath;
    
    public String getArtist() { return artist; }
    public void setArtist(String artist) { this.artist = artist; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getSongPath() { return songPath; }
    public void setSongPath(String songPath) { this.songPath = songPath; }
}
