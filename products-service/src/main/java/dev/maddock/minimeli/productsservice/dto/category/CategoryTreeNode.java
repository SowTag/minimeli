package dev.maddock.minimeli.productsservice.dto.category;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * DTO for {@link dev.maddock.minimeli.productsservice.entity.Category}
 */
public record CategoryTreeNode(UUID id, String title, String description,
                               List<CategoryTreeNode> children) implements Serializable {
}