package com.kaif.backendcasestudy.Payloads;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreateRequestDTO {
    @NotBlank(message = "Product name is required")
    private String name;

    @NotBlank(message = "SKU is required")
    private String sku;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", message = "Price cannot be negative")
    private BigDecimal price;

    @NotNull(message = "Warehouse ID is required")
    @Min(value = 1, message = "Warehouse ID must be positive")
    private Long warehouseId;

    @NotNull(message = "Initial quantity is required")
    @Min(value = 0, message = "Initial quantity cannot be negative")
    private Integer initialQuantity;
}
