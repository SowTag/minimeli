package dev.maddock.minimeli.productsservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "category_path", indexes = {
        @Index(name = "id_category_path_ancestor", columnList = "ancestor_id"),
        @Index(name = "id_category_path_descendant", columnList = "descendant_id")
})
public class CategoryPath {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ancestor_id", nullable = false)
    private Category ancestor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "descendant_id", nullable = false)
    private Category descendant;

    @Column(nullable = false)
    private int depth;
}