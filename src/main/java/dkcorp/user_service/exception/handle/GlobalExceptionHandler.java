package dkcorp.user_service.exception.handle;

import dkcorp.user_service.dto.ApiErrorDto;
import dkcorp.user_service.exception.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.PropertyValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiErrorDto> handleEntityNotFoundException(EntityNotFoundException ex, HttpServletRequest request) {
        log.error("Entity not found: {}", ex.getMessage());
        ApiErrorDto apiError = createApiErrorDto(
                ex.getMessage(),
                null,
                request.getRequestURI(),
                HttpStatus.NOT_FOUND
        );
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorDto> handleValidationExceptions(MethodArgumentNotValidException ex, HttpServletRequest request) {
        FieldError fieldError = ex.getBindingResult().getFieldError();
        String message = fieldError != null ? fieldError.getDefaultMessage() : "Validation error";
        String field = fieldError != null ? fieldError.getField() : null;
        log.error("Validation error on field {}: {}", field, message);
        ApiErrorDto apiError = createApiErrorDto(
                message,
                field,
                request.getRequestURI(),
                HttpStatus.BAD_REQUEST
        );
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PropertyValueException.class)
    public ResponseEntity<ApiErrorDto> handlePropertyValueException(PropertyValueException ex, HttpServletRequest request) {
        String message = String.format("Field '%s' cannot be null or empty", ex.getPropertyName());
        log.error("Property value error: {}", message);
        ApiErrorDto apiError = createApiErrorDto(
                message,
                ex.getPropertyName(),
                request.getRequestURI(),
                HttpStatus.BAD_REQUEST
        );
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorDto> handleGenericException(Exception ex, HttpServletRequest request) {
        String message = "An unexpected error occurred. Please try again later.";
        log.error("Unhandled exception: {}", ex.getMessage(), ex);
        ApiErrorDto apiError = createApiErrorDto(
                message,
                null,
                request.getRequestURI(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ApiErrorDto createApiErrorDto(String message, String field, String path, HttpStatus status) {
        return ApiErrorDto.builder()
                .message(message)
                .field(field)
                .path(path)
                .status(status.toString())
                .timestamp(System.currentTimeMillis())
                .build();
    }
}
