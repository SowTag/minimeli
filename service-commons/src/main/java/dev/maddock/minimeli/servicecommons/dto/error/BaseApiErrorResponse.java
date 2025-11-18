package dev.maddock.minimeli.servicecommons.dto.error;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

/**
 * Represents a base API error response. These are returned whenever an {@link dev.maddock.minimeli.servicecommons.exception.ApiException}
 * is thrown, detailing some information about the error, like a domain-specific code, a message, the URI path and a timestamp.
 * This interface allows extending those properties further, to enhance errors (for example, if there's a binding error,
 * one might want to return the list of values which failed to bind, and this interface allows writing a custom record
 * that includes said list while ensuring consistent error responses.
 */
public interface BaseApiErrorResponse {
    /**
     * Business logic specific error code
     */
    @JsonProperty
    String code();

    /**
     * A message explaining the error
     */
    @JsonProperty
    String message();

    /**
     * The API path that caused the error
     */
    @JsonProperty
    String path();

    /**
     * Timestamp where the error occurred
     */
    @JsonProperty
    Instant timestamp();
}