package org.basilisk.repository;

import java.util.List;
import java.util.UUID;

import org.basilisk.dto.response.TrackResponseDTO;
import org.basilisk.model.Track;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TrackRepository implements PanacheRepositoryBase<Track, UUID>{
	//Similarity search based on Cosine Distance in pgvector (<=>)
	public List<TrackResponseDTO> findSimilarTracks(UUID targetTrackId, float[] targetEmbedding, int limit){
		return getEntityManager()
				.createNativeQuery(
					"SELECT * FROM tracks " +
					"WHERE id != cast(:id as uuid) " + //Escludo la stessa traccia dalla lista di risultati
					"ORDER BY audio_embedding <=> cast(:vector as vector) " +
					"LIMIT :limit",
					Track.class
				)
				.setParameter("id", targetTrackId)
				.setParameter("vector", targetEmbedding)
				.setParameter("limit", limit)
				.getResultList();
	}
}
