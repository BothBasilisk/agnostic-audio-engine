package org.basilisk.client;

import org.basilisk.dto.request.AudioRequest;
import org.basilisk.dto.response.AudioResponse;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@RegisterRestClient(configKey = "dsp-worker")
public interface DspWorkerClient {
	@POST
	@Path("/encode")
	AudioResponse encodeAudio(AudioRequest request);
}
