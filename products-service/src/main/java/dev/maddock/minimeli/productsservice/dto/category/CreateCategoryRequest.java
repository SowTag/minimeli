package dev.maddock.minimeli.productsservice.dto.category;

import dev.maddock.minimeli.servicecommons.annotation.NullOrNotBlank;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link dev.maddock.minimeli.productsservice.entity.Category}
 */
public record CreateCategoryRequest(
        @NotBlank(message = "Title can't be blank") @Size(message = "Title must be 2-128 characters long", min = 2) String title,
        @NullOrNotBlank(message = "Description must be either null or not blank") String description,
        UUID parentCategoryId) implements Serializable {
}