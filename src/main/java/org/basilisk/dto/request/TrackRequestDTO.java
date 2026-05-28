package org.basilisk.dto.request;

public class TrackRequestDTO {
	private String artist;
    private String title;
    private String songPath;
    
    public String getArtist() { return artist; }
    public void setArtist(String artist) { this.artist = artist; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getSongPath() { return songPath; }
    public void setSongPath(String songPath) { this.songPath = songPath; }
}
