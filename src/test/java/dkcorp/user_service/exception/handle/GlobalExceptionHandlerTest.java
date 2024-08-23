package dkcorp.user_service.exception.handle;

import dkcorp.user_service.dto.ApiErrorDto;
import dkcorp.user_service.exception.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {
    private final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

    @Test
    void testHandleEntityNotFoundException() {
        String errorMessage = "Entity not found";
        String requestUri = "/api/v1/users/1";
        EntityNotFoundException ex = new EntityNotFoundException(errorMessage);

        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn(requestUri);

        ResponseEntity<ApiErrorDto> response = globalExceptionHandler.handleEntityNotFoundException(ex, request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(errorMessage, Objects.requireNonNull(response.getBody()).getMessage());
        assertEquals(requestUri, response.getBody().getPath());
        assertEquals(HttpStatus.NOT_FOUND.toString(), response.getBody().getStatus());
    }
}
