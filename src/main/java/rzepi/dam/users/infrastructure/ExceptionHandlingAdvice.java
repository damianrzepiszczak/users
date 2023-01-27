package rzepi.dam.users.infrastructure;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.ResourceAccessException;

@ControllerAdvice
class ExceptionHandlingAdvice {

    @ExceptionHandler(ResourceAccessException.class)
    ResponseEntity<ErrorMessage> handleExternalApiServiceUnavailable() {
        ErrorMessage errorMessage = new ErrorMessage("Resource not found");
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CallNotPermittedException.class)
    ResponseEntity<ErrorMessage> handleResourceAccessException() {
        ErrorMessage errorMessage = new ErrorMessage("External API is unavailable");
        return new ResponseEntity<>(errorMessage, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @Getter
    @RequiredArgsConstructor
    static class ErrorMessage {
        private final String message;
    }
}
