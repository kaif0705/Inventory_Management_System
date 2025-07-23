package com.kaif.backendcasestudy.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

/**
 * Represents a Product in the StockFlow system.
 * This entity maps to the 'products' table in the database.
 */
@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) // Ensures the 'name' column cannot be null in the database
    private String name;

    // Corrected: SKU must be unique across the platform.
    // 'unique = true' enforces this constraint at the database level.
    @Column(nullable = false, unique = true)
    private String sku;

    // 'Precision' is the total number of digits before decimal point,
    // and 'scale' is the number of digits after the decimal point.
    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_type_id", nullable = true)
    private ProductType productType;

}