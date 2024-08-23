package dkcorp.user_service.exception.handle;

import dkcorp.user_service.dto.ApiErrorDto;
import dkcorp.user_service.exception.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.PropertyValueException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

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

    @Test
    void testHandleValidationExceptions_withFieldError() {
        String fieldName = "username";
        String errorMessage = "Username cannot be empty";
        String requestUri = "/api/v1/users";

        FieldError fieldError = Mockito.mock(FieldError.class);
        when(fieldError.getDefaultMessage()).thenReturn(errorMessage);
        when(fieldError.getField()).thenReturn(fieldName);

        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        when(bindingResult.getFieldError()).thenReturn(fieldError);

        MethodArgumentNotValidException ex = Mockito.mock(MethodArgumentNotValidException.class);
        when(ex.getBindingResult()).thenReturn(bindingResult);

        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn(requestUri);

        ResponseEntity<ApiErrorDto> response = globalExceptionHandler.handleValidationExceptions(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(errorMessage, Objects.requireNonNull(response.getBody()).getMessage());
        assertEquals(fieldName, response.getBody().getField());
        assertEquals(requestUri, response.getBody().getPath());
        assertEquals(HttpStatus.BAD_REQUEST.toString(), response.getBody().getStatus());
    }

    @Test
    void testHandlePropertyValueException() {
        String propertyName = "name";
        String errorMessage = String.format("Field '%s' cannot be null or empty", propertyName);
        String requestUri = "/api/v1/users";

        PropertyValueException ex = new PropertyValueException(errorMessage, "entityName", propertyName);

        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn(requestUri);

        ResponseEntity<ApiErrorDto> response = globalExceptionHandler.handlePropertyValueException(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(errorMessage, Objects.requireNonNull(response.getBody()).getMessage());
        assertEquals(propertyName, response.getBody().getField());
        assertEquals(requestUri, response.getBody().getPath());
        assertEquals(HttpStatus.BAD_REQUEST.toString(), response.getBody().getStatus());
    }
}
