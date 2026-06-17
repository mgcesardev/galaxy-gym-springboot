package cmg.example.galaxy_gym_backend.exceptions;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    @Test
    void testApiExceptionConstructorsAndGetters() {
        // Test generic constructor
        ApiException ex1 = new ApiException("Generic error message");
        assertEquals("Generic error message", ex1.getMessage());
        assertEquals("GENERIC_ERROR", ex1.getUseCase());

        // Test custom use case constructor
        ApiException ex2 = new ApiException("Custom error message", "USER_NOT_FOUND");
        assertEquals("Custom error message", ex2.getMessage());
        assertEquals("USER_NOT_FOUND", ex2.getUseCase());
    }

    @Test
    void testApiErrorResponseRecord() {
        LocalDateTime now = LocalDateTime.now();
        ApiErrorResponse response = new ApiErrorResponse(now, 400, "Bad Request", "Error message", "/test-path");

        assertEquals(now, response.timestamp());
        assertEquals(400, response.status());
        assertEquals("Bad Request", response.error());
        assertEquals("Error message", response.message());
        assertEquals("/test-path", response.path());
    }

    @Test
    void testHandleApiException() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        ApiException exception = new ApiException("Test API Exception", "TEST_CASE");
        WebRequest webRequest = Mockito.mock(WebRequest.class);
        when(webRequest.getDescription(false)).thenReturn("uri=/api/v1/test");

        ResponseEntity<ApiErrorResponse> responseEntity = handler.handleApiException(exception, webRequest);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(400, responseEntity.getBody().status());
        assertEquals("Bad Request", responseEntity.getBody().error());
        assertEquals("Test API Exception", responseEntity.getBody().message());
        assertEquals("/api/v1/test", responseEntity.getBody().path());
        assertNotNull(responseEntity.getBody().timestamp());
    }

    @Test
    void testHandleGeneralException() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        Exception exception = new Exception("Root cause unexpected error");
        WebRequest webRequest = Mockito.mock(WebRequest.class);
        when(webRequest.getDescription(false)).thenReturn("uri=/api/v1/fatal");

        ResponseEntity<ApiErrorResponse> responseEntity = handler.handleGeneralException(exception, webRequest);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(500, responseEntity.getBody().status());
        assertEquals("Internal Server Error", responseEntity.getBody().error());
        assertEquals("Ocurrió un error interno en el servidor. Por favor, intente más tarde.", responseEntity.getBody().message());
        assertEquals("/api/v1/fatal", responseEntity.getBody().path());
        assertNotNull(responseEntity.getBody().timestamp());
    }
}
