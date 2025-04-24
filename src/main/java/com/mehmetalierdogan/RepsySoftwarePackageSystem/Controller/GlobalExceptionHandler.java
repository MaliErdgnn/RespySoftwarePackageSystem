package com.mehmetalierdogan.RepsySoftwarePackageSystem.Controller;
import com.mehmetalierdogan.RepsySoftwarePackageSystem.DataTransferObjects.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiError> handleStatusException(ResponseStatusException ex, HttpServletRequest req) {
        String reasonPhrase = (ex.getStatusCode() instanceof HttpStatus)
                ? ((HttpStatus) ex.getStatusCode()).getReasonPhrase()
                : ex.getStatusCode().toString();

        ApiError err = new ApiError(
                ex.getStatusCode().value(),
                reasonPhrase,
                ex.getReason(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(ex.getStatusCode()).body(err);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleOther(Exception ex, HttpServletRequest req) {
        ApiError err = new ApiError(
                500,
                "Internal Server Error",
                ex.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
    }


}
