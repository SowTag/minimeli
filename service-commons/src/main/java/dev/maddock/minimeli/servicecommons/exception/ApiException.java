package dev.maddock.minimeli.servicecommons.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Represents a generic business-specific API exception, will be handled by the
 */
@Getter
public abstract class ApiException extends RuntimeException {
    private final HttpStatus statusCode;
    private final Enum<?> errorCode;
    private final String message;

    public ApiException(HttpStatus statusCode, Enum<?> errorCode, String message) {
        super(message);
        this.statusCode = statusCode;
        this.errorCode = errorCode;
        this.message = message;

    }
}
