package org.basilisk;

import org.junit.jupiter.api.Test;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

@QuarkusTest
@QuarkusTestResource(MockDspWorkerResource.class)
public class TrackResourceTest {
	@Test
	public void testCreateTrackSuccess() {
		String requestJson = """
				{
					"artist": "Test Artist",
				    "title": "Test Title",
				    "songPath": "http://minio:9000/audio-catalog/test.mp3"
				}
			""";
		
		given()
			.contentType(ContentType.JSON)
			.body(requestJson)
		.when()
			.post("/api/tracks")
        .then()
            .statusCode(201)
            .body("id", notNullValue())
            .body("artist", is("Test Artist"))
            .body("title", is("Test Title"));
	}
	
	@Test
    public void testCreateTrackValidationFailure() {
        String invalidJson = """
            {
                "artist": "Test Artist",
                "title": "Test Title",
                "songPath": "ciao_non_sono_un_url"
            }
        """;

        given()
            .contentType(ContentType.JSON)
            .body(invalidJson)
        .when()
            .post("/api/tracks")
        .then()
            .statusCode(400)
            .body("code", is("VALIDATION_ERROR"));
    }
}
