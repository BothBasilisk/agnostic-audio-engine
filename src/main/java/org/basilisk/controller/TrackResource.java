package org.basilisk.controller;

import java.util.List;
import java.util.UUID;

import org.basilisk.client.DspWorkerClient;
import org.basilisk.dto.request.AudioRequest;
import org.basilisk.dto.request.TrackRequestDTO;
import org.basilisk.dto.response.AudioResponse;
import org.basilisk.dto.response.TrackResponseDTO;
import org.basilisk.model.Track;
import org.basilisk.repository.TrackRepository;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/tracks")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "AI Audio Tracks", description = "Endpoint for analysis and vectorial search of music records")
public class TrackResource {
	@Inject
    TrackRepository trackRepository;
	
	@Inject
	@RestClient
	DspWorkerClient dspClient;
	
	@POST
    @Transactional
    @Operation(summary = "Analyze and save a record", 
    description = "Sends the URL (MinIO or S3) of an audio file to the Python worker for "
    		+ "the vectorial extraction and saves the result in Postgres.")
    public Response createTrack(@Valid TrackRequestDTO requestDTO) {
		System.out.println("Calling AI to analyze audio");
		
		// --- 1. REQUEST DTO -> ENTITY mapping
		Track track = new Track();
		track.setArtist(requestDTO.getArtist());
        track.setTitle(requestDTO.getTitle());
        track.setSongPath(requestDTO.getSongPath());
		
		// --- 2. Calling python script ---
		AudioRequest aiRequest = new AudioRequest(track.getSongPath());
		AudioResponse aiResponse = dspClient.encodeAudio(aiRequest);
		
		// --- 3. Storing song embedding ---
		track.setAudioEmbedding(aiResponse.getEmbedding());
		
		System.out.println("Song DNA extracted");
		
		// --- 4. Saving the song ---
		trackRepository.persist(track);
		
		// --- 5. ENTITY -> RESPONSE DTO mapping
		TrackResponseDTO responseDTO = new TrackResponseDTO(
	            track.getId(), track.getArtist(), track.getTitle(), track.getSongPath()
	        );
		
		return Response.status(Response.Status.CREATED).entity(responseDTO).build();
	}
	
	@GET
	@Operation(summary = "List of all records", 
	description = "Returns all music records in db without the vectors.")
	public Response getAllTracks() {
		List<TrackResponseDTO> dtos = trackRepository.listAll().stream()
	            .map(t -> new TrackResponseDTO(t.getId(), t.getArtist(), t.getTitle(), t.getSongPath()))
	            .toList();
		return Response.ok(dtos).build();
	}
	
	@GET
	@Path("/{id}/similar")
	@Operation(summary = "Semantic AI Search", 
	description = "Find the 3 tracks that are most similar acoustically "
			+ "using the Cosine Distance in the Vector Database.")
	public Response getSimilarTracks(@PathParam("id") UUID id) {
		// --- 1. Find starting track ---
		Track sourceTrack = trackRepository.findById(id);
		if(sourceTrack == null) {
			return Response.status(Response.Status.NOT_FOUND).entity("Track not found").build();
		}
		
		// --- 2. Searching X songs "closer" in vectorial space
		List<TrackResponseDTO> similarTracks = trackRepository.findSimilarTracks(
				sourceTrack.getId(), 
				sourceTrack.getAudioEmbedding(), 
				3
		);
		
		return Response.ok(similarTracks).build();
	}
}
