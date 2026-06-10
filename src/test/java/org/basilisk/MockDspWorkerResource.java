package org.basilisk;

import java.util.Collections;
import java.util.Map;

import com.github.tomakehurst.wiremock.WireMockServer;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

public class MockDspWorkerResource implements QuarkusTestResourceLifecycleManager{

	private WireMockServer wireMockServer;
	
	@Override
	public Map<String, String> start() {
		// --- 1. Starting Mock Server ---
		wireMockServer = new WireMockServer(options().dynamicPort());
		wireMockServer.start();
		
		// --- 2. Fake data preparation ---
		String fakeEmbedding = "[" + String.join(",", Collections.nCopies(512, "0.1")) + "]";
		String fakeJsonResponse = "{ \"status\": \"success\", \"embedding\": " + fakeEmbedding + " }";
		
		wireMockServer.stubFor(post(urlEqualTo("/encode"))
				.willReturn(aResponse()
						.withHeader("Content-Type", "application/json")
						.withBody(fakeJsonResponse)));
		
		// --- 3. Returning a fake response ---
		return Map.of("quarkus.rest-client.dsp-worker.url", wireMockServer.baseUrl());
	}

	@Override
	public void stop() {
		if (wireMockServer != null) {
            wireMockServer.stop();
        }
	}
	
}
