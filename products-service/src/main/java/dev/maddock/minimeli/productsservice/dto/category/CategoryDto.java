package dev.maddock.minimeli.productsservice.dto.category;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link dev.maddock.minimeli.productsservice.entity.Category}
 */
public record CategoryDto(UUID id, String title, String description, UUID parentCategoryId) implements Serializable {
}