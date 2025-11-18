package dev.maddock.minimeli.servicecommons.advice;

import dev.maddock.minimeli.servicecommons.dto.error.BaseApiErrorResponse;
import dev.maddock.minimeli.servicecommons.dto.error.SimpleApiErrorResponse;
import dev.maddock.minimeli.servicecommons.exception.ApiException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
class ApiExceptionAdvice {
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<BaseApiErrorResponse> handleGenericApiError(ApiException ex, HttpServletRequest request) {
        BaseApiErrorResponse response = SimpleApiErrorResponse.builder()
                .code(ex.getErrorCode().toString())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .timestamp(Instant.now())
                .build();

        return ResponseEntity.status(ex.getStatusCode()).body(response);
    }
}
