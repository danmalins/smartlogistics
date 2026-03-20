package org.dgroup.userservicesmartlogistics.handler;

import org.dgroup.userservicesmartlogistics.dto.error.ApiError;
import org.dgroup.userservicesmartlogistics.exception.ClientNotFoundException;
import org.dgroup.userservicesmartlogistics.exception.CustomAccessDeniedException;
import org.dgroup.userservicesmartlogistics.exception.DriverNotFoundException;
import org.dgroup.userservicesmartlogistics.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 403 Forbidden
    @ExceptionHandler(CustomAccessDeniedException.class)
    public ResponseEntity<ApiError> handleAccessDenied(CustomAccessDeniedException ex) {
        ApiError error = new ApiError(HttpStatus.FORBIDDEN, "Access denied", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    // 404 Not Found
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotFound(UserNotFoundException ex) {
        ApiError error = new ApiError(HttpStatus.NOT_FOUND, "User not found", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // 404 Not Found
    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<ApiError> handleClientNotFound(ClientNotFoundException ex) {
        ApiError error = new ApiError(HttpStatus.NOT_FOUND, "Client not found", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // 404 Not Found
    @ExceptionHandler(DriverNotFoundException.class)
    public ResponseEntity<ApiError> handleDriverNotFound(DriverNotFoundException ex) {
        ApiError error = new ApiError(HttpStatus.NOT_FOUND, "Driver not found", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
