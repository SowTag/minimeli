package dev.maddock.minimeli.productsservice.dto.category;

import dev.maddock.minimeli.productsservice.entity.Category;
import lombok.Builder;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link dev.maddock.minimeli.productsservice.entity.Category}
 */
@Builder
public record CategoryDto(UUID id, String title, String description, UUID parentCategoryId) implements Serializable {
    public static CategoryDto from(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .title(category.getTitle())
                .description(category.getDescription())
                .parentCategoryId(category.getParentCategory().getId())
                .build();
    }
}