package org.basilisk.dto.response;

import java.util.UUID;

public class TrackResponseDTO {
	private UUID id;
    private String artist;
    private String title;
    private String songPath;
    
    public TrackResponseDTO(UUID id, String artist, String title, String songPath) {
        this.id = id;
        this.artist = artist;
        this.title = title;
        this.songPath = songPath;
    }
    
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getArtist() { return artist; }
    public void setArtist(String artist) { this.artist = artist; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getSongPath() { return songPath; }
    public void setSongPath(String songPath) { this.songPath = songPath; }
}
