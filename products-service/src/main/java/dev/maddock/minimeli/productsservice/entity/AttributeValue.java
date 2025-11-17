package dev.maddock.minimeli.productsservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "attribute_value")
public class AttributeValue {
    @Id
    @Column(nullable = false)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "attribute_id", nullable = false)
    private ProductAttribute attribute;

    @Column(name = "string_value")
    private String stringValue = null;

    @Column(name = "long_value")
    private Long longValue = null;

    @Column(name = "double_value")
    private Double doubleValue = null;

    @Column(name = "boolean_value")
    private Boolean booleanValue = null;


}