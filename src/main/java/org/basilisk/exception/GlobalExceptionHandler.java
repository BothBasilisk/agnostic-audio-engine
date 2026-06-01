package org.basilisk.exception;

import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

public class GlobalExceptionHandler {
	//Intercepts validation errors
	@ServerExceptionMapper
	public RestResponse<ErrorResponse> mapValidationException(ConstraintViolationException ex){
		String errorMessage = ex.getConstraintViolations().iterator().next().getMessage();
		return RestResponse.status(Response.Status.BAD_REQUEST,
				new ErrorResponse("VALIDATION_ERROR", errorMessage));
	}
	
	//Intercepts REST client errors
	@ServerExceptionMapper
	public RestResponse<ErrorResponse> mapWebApplicationException(WebApplicationException ex){
		if(ex.getResponse().getStatus() >= 500) {
			return RestResponse.status(Response.Status.SERVICE_UNAVAILABLE,
					new ErrorResponse("AI_ENGINE_ERROR", "Comunication with DSP worker failed. "
							+ "Make sure the Python container is running"));
		}
		return RestResponse.status(ex.getResponse().getStatusInfo(),
				new ErrorResponse("HTTP_ERROR", ex.getMessage()));
	}
	
	//Intercepts network errors
	@ServerExceptionMapper
	public RestResponse<ErrorResponse> mapProcessingException(ProcessingException ex){
		return RestResponse.status(Response.Status.SERVICE_UNAVAILABLE,
				new ErrorResponse("AI_ENGINE_OFFLINE", "Connection refused. DSP worker is not running or"
						+ " can't be reached"));
	}
	
	public static class ErrorResponse{
		public String code;
		public String message;
		
		public ErrorResponse(String code, String message) {
			this.code = code;
			this.message = message;
		}
	}
}
