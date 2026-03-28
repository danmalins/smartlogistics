package org.dgroup.userservicesmartlogistics.handler;

import org.dgroup.userservicesmartlogistics.dto.error.ApiError;
import org.dgroup.userservicesmartlogistics.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {


    // 401 Unauthorized - Wrong login or password
    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<ApiError> handleAuthenticationFailed(AuthenticationFailedException ex) {
        ApiError error = new ApiError(HttpStatus.UNAUTHORIZED, "Authentification error", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    // 403 Forbidden
    @ExceptionHandler(UserNotVerifiedException.class)
    public ResponseEntity<ApiError> handleUserNotVerified(UserNotVerifiedException ex) {
        ApiError error = new ApiError(HttpStatus.FORBIDDEN, "User not verified", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    // 403 Forbidden
    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<ApiError> handleTokenExpired(TokenExpiredException ex) {
        ApiError error = new ApiError(HttpStatus.FORBIDDEN, "Verification token expired", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    // 403 Forbidden
    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ApiError> handleInvalidToken(InvalidTokenException ex) {
        ApiError error = new ApiError(HttpStatus.FORBIDDEN, "Invalid token", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

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
