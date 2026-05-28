package org.basilisk.model;

import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name ="tracks")
public class Track {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id")
	private UUID id;
	
	@Column(name = "artist")
	private String artist;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "album")
	private String album;
	
	@Column(name = "song_path")
	private String songPath;
	
	@Column(name = "audio_embedding", columnDefinition = "vector(512)")
	@JdbcTypeCode(SqlTypes.VECTOR)
	private float[] audioEmbedding;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getSongPath() {
		return songPath;
	}

	public void setSongPath(String songPath) {
		this.songPath = songPath;
	}

	public float[] getAudioEmbedding() {
		return audioEmbedding;
	}

	public void setAudioEmbedding(float[] audioEmbedding) {
		this.audioEmbedding = audioEmbedding;
	}
	
	
}
