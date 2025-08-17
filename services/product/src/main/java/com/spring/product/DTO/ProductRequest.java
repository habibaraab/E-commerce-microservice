package com.spring.product.DTO;

import com.spring.product.Model.Category;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductRequest(
         Integer id,
         @NotNull(message = "Name is required")
         String name,
         @NotNull(message = "Product description is required")
         String description,
         @Positive(message = "Available quantity should be positive")
         double quantity,
         @Positive(message = "Price should be positive")
         BigDecimal price,
         @NotNull(message = "Product category is required")
         Integer categoryId
) {
}
