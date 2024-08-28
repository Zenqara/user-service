package dkcorp.user_service.exception.handle;

import dkcorp.user_service.dto.ErrorDto;
import dkcorp.user_service.exception.DataValidationException;
import dkcorp.user_service.exception.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.PropertyValueException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorDto> handleNotFoundException(NotFoundException ex, HttpServletRequest request) {
        log.error("Entity not found: {}", ex.getMessage());
        ErrorDto errorDto = createErrorDto(
                ex.getMessage(),
                null,
                request.getRequestURI(),
                HttpStatus.NOT_FOUND
        );

        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataValidationException.class)
    public ResponseEntity<ErrorDto> handleDataValidationException(DataValidationException ex, HttpServletRequest request) {
        log.error("Data validation error: {}", ex.getMessage());

        ErrorDto errorDto = createErrorDto(
                ex.getMessage(),
                null,
                request.getRequestURI(),
                HttpStatus.BAD_REQUEST
        );

        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        FieldError fieldError = ex.getBindingResult().getFieldError();
        String message = fieldError != null ? fieldError.getDefaultMessage() : "Validation error";
        String field = fieldError != null ? fieldError.getField() : null;
        log.error("Validation error on field {}: {}", field, message);
        ErrorDto errorDto = createErrorDto(
                message,
                field,
                request.getRequestURI(),
                HttpStatus.BAD_REQUEST
        );

        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PropertyValueException.class)
    public ResponseEntity<ErrorDto> handlePropertyValueException(PropertyValueException ex, HttpServletRequest request) {
        String message = String.format("Field '%s' cannot be null or empty", ex.getPropertyName());
        log.error("Property value error: {}", message);
        ErrorDto errorDto = createErrorDto(
                message,
                ex.getPropertyName(),
                request.getRequestURI(),
                HttpStatus.BAD_REQUEST
        );

        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorDto> handleDataIntegrityViolationException(DataIntegrityViolationException ex, HttpServletRequest request) {
        String message = "Unique constraint violation: Username, email, and phone should be unique";
        log.error("Data integrity violation: {}", message, ex);
        ErrorDto errorDto = createErrorDto(
                message,
                null,
                request.getRequestURI(),
                HttpStatus.CONFLICT
        );

        return new ResponseEntity<>(errorDto, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleGenericException(Exception ex, HttpServletRequest request) {
        String message = "An unexpected error occurred. Please try again later.";
        log.error("Unhandled exception: {}", ex.getMessage(), ex);
        ErrorDto errorDto = createErrorDto(
                message,
                null,
                request.getRequestURI(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );

        return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ErrorDto createErrorDto(String message, String field, String path, HttpStatus status) {
        return ErrorDto.builder()
                .message(message)
                .field(field)
                .path(path)
                .status(status.toString())
                .timestamp(System.currentTimeMillis())
                .build();
    }
}
