package dkcorp.user_service.exception.handle;

import dkcorp.user_service.exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionHandlerTest {
    private final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

    @Test
    void testHandleEntityNotFoundException() {
        String errorMessage = "Entity not found";
        EntityNotFoundException exception = new EntityNotFoundException(errorMessage);

        ResponseEntity<String> response = globalExceptionHandler.handleEntityNotFoundException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(errorMessage, response.getBody());
    }
}
