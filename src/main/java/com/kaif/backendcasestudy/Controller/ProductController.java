package com.kaif.backendcasestudy.Controller;

import com.kaif.backendcasestudy.Model.Product;
import com.kaif.backendcasestudy.Payloads.ProductCreateRequestDTO;
import com.kaif.backendcasestudy.Payloads.ProductResponseDTO;
import com.kaif.backendcasestudy.Service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@Valid @RequestBody ProductCreateRequestDTO requestDTO) {
        // Corrected: @Valid annotation ensures input validation happens automatically
        // before the method body is executed. If validation fails, MethodArgumentNotValidException
        // is thrown and caught by GlobalExceptionHandler.

        // Corrected: Delegate the business logic to the ProductService.
        // The service handles SKU uniqueness, warehouse existence, and transactional atomicity.
        Product createdProduct = productService.createProduct(requestDTO);

        // Corrected: Construct a clean response DTO for the client.
        ProductResponseDTO responseDTO = new ProductResponseDTO(
                createdProduct.getId(),
                createdProduct.getName(),
                createdProduct.getSku(),
                createdProduct.getPrice(),
                "Product created successfully"
        );

        // Corrected: Return HTTP 201 Created status for successful resource creation.
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }
}
