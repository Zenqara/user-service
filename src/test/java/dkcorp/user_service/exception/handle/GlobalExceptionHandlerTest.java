package dkcorp.user_service.exception.handle;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import dkcorp.user_service.dto.ErrorDto;
import dkcorp.user_service.exception.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.PropertyValueException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {
    private final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

    @BeforeEach
    void setUp() {
        Logger logger = (Logger) LoggerFactory.getLogger(GlobalExceptionHandler.class);
        logger.setLevel(Level.OFF);
    }

    @Test
    void testHandleNotFoundException() {
        String errorMessage = "Entity not found";
        String requestUri = "/api/v1/users/1";
        NotFoundException ex = new NotFoundException(errorMessage);

        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn(requestUri);

        ResponseEntity<ErrorDto> response = globalExceptionHandler.handleNotFoundException(ex, request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(errorMessage, Objects.requireNonNull(response.getBody()).getMessage());
        assertEquals(requestUri, response.getBody().getPath());
        assertEquals(HttpStatus.NOT_FOUND.toString(), response.getBody().getStatus());
    }

    @Test
    void testHandleMethodArgumentNotValidException_withFieldError() {
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

        ResponseEntity<ErrorDto> response = globalExceptionHandler.handleMethodArgumentNotValidException(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(errorMessage, Objects.requireNonNull(response.getBody()).getMessage());
        assertEquals(fieldName, response.getBody().getField());
        assertEquals(requestUri, response.getBody().getPath());
        assertEquals(HttpStatus.BAD_REQUEST.toString(), response.getBody().getStatus());
    }

    @Test
    void testHandleMethodArgumentNotValidException_withoutFieldError() {
        String requestUri = "/api/v1/users";

        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        when(bindingResult.getFieldError()).thenReturn(null);

        MethodArgumentNotValidException ex = Mockito.mock(MethodArgumentNotValidException.class);
        when(ex.getBindingResult()).thenReturn(bindingResult);

        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn(requestUri);

        ResponseEntity<ErrorDto> response = globalExceptionHandler.handleMethodArgumentNotValidException(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        ErrorDto errorDto = response.getBody();
        assert errorDto != null;
        assertEquals("Validation error", errorDto.getMessage());
        assertNull(errorDto.getField());
        assertEquals(requestUri, errorDto.getPath());
        assertEquals(HttpStatus.BAD_REQUEST.toString(), errorDto.getStatus());
    }

    @Test
    void testHandlePropertyValueException() {
        String propertyName = "name";
        String errorMessage = String.format("Field '%s' cannot be null or empty", propertyName);
        String requestUri = "/api/v1/users";

        PropertyValueException ex = new PropertyValueException(errorMessage, "entityName", propertyName);

        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn(requestUri);

        ResponseEntity<ErrorDto> response = globalExceptionHandler.handlePropertyValueException(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(errorMessage, Objects.requireNonNull(response.getBody()).getMessage());
        assertEquals(propertyName, response.getBody().getField());
        assertEquals(requestUri, response.getBody().getPath());
        assertEquals(HttpStatus.BAD_REQUEST.toString(), response.getBody().getStatus());
    }

    @Test
    void testHandleDataIntegrityViolationException() {
        String errorMessage = "Unique constraint violation: Username, email, and phone should be unique";
        String requestUri = "/api/v1/users";

        DataIntegrityViolationException ex = new DataIntegrityViolationException(errorMessage);

        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn(requestUri);

        ResponseEntity<ErrorDto> response = globalExceptionHandler.handleDataIntegrityViolationException(ex, request);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals(errorMessage, Objects.requireNonNull(response.getBody()).getMessage());
        assertEquals(requestUri, response.getBody().getPath());
        assertEquals(HttpStatus.CONFLICT.toString(), response.getBody().getStatus());
    }

    @Test
    void testHandleGenericException() {
        String errorMessage = "An unexpected error occurred. Please try again later.";
        String requestUri = "/api/v1/users";

        Exception ex = new Exception("Some unexpected error");

        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn(requestUri);

        ResponseEntity<ErrorDto> response = globalExceptionHandler.handleGenericException(ex, request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(errorMessage, Objects.requireNonNull(response.getBody()).getMessage());
        assertEquals(requestUri, response.getBody().getPath());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.toString(), response.getBody().getStatus());
    }
}
