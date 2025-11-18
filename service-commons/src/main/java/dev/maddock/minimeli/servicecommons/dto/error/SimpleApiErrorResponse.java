package dev.maddock.minimeli.servicecommons.dto.error;

import lombok.Builder;

import java.time.Instant;

@Builder
public record SimpleApiErrorResponse(String code, String message, String path,
                                     Instant timestamp) implements BaseApiErrorResponse {
}