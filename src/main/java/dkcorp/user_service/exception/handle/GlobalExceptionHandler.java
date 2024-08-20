package dkcorp.user_service.exception.handle;

import dkcorp.user_service.dto.ApiErrorDto;
import dkcorp.user_service.exception.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.PropertyValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiErrorDto> handleEntityNotFoundException(EntityNotFoundException ex, HttpServletRequest request) {
        ApiErrorDto apiError = ApiErrorDto.builder()
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .status(HttpStatus.NOT_FOUND.toString())
                .timestamp(System.currentTimeMillis())
                .build();
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorDto> handleValidationExceptions(MethodArgumentNotValidException ex, HttpServletRequest request) {
        FieldError fieldError = ex.getBindingResult().getFieldError();
        if (fieldError != null) {
            ApiErrorDto apiError = ApiErrorDto.builder()
                    .message(fieldError.getDefaultMessage())
                    .field(fieldError.getField())
                    .path(request.getRequestURI())
                    .status(HttpStatus.BAD_REQUEST.toString())
                    .timestamp(System.currentTimeMillis())
                    .build();
            return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PropertyValueException.class)
    public ResponseEntity<ApiErrorDto> handleHibernatePropertyValueException(PropertyValueException ex, HttpServletRequest request) {
        ApiErrorDto apiError = ApiErrorDto.builder()
                .message(String.format("Field %s cannot be null or empty", ex.getPropertyName()))
                .field(ex.getPropertyName())
                .path(request.getRequestURI())
                .status(HttpStatus.BAD_REQUEST.toString())
                .timestamp(System.currentTimeMillis())
                .build();

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }
}
