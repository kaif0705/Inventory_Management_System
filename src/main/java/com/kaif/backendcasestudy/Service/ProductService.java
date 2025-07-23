package com.kaif.backendcasestudy.Service;

import com.kaif.backendcasestudy.Exceptions.DuplicateSkuException;
import com.kaif.backendcasestudy.Exceptions.ResourceNotFoundException;
import com.kaif.backendcasestudy.Model.Inventory;
import com.kaif.backendcasestudy.Model.Product;
import com.kaif.backendcasestudy.Model.Warehouse;
import com.kaif.backendcasestudy.Payloads.ProductCreateRequestDTO;
import com.kaif.backendcasestudy.Repository.InventoryRepository;
import com.kaif.backendcasestudy.Repository.ProductRepository;
import com.kaif.backendcasestudy.Repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;
    private final WarehouseRepository warehouseRepository;

    @Autowired
    public ProductService(ProductRepository productRepository,
                          InventoryRepository inventoryRepository,
                          WarehouseRepository warehouseRepository) {
        this.productRepository = productRepository;
        this.inventoryRepository = inventoryRepository;
        this.warehouseRepository = warehouseRepository;
    }

    @Transactional
    public Product createProduct(ProductCreateRequestDTO requestDTO) {
        if (productRepository.findBySku(requestDTO.getSku()).isPresent()) {
            throw new DuplicateSkuException(requestDTO.getSku(),
                    productRepository.findBySku(requestDTO.getSku()).get().getId());
        }
        Warehouse warehouse = warehouseRepository.findById(requestDTO.getWarehouseId())
                .orElseThrow(() -> new ResourceNotFoundException("Warehouse", "id", requestDTO.getWarehouseId()));

        // Corrected: 3. Create new Product entity
        Product product = new Product();
        product.setName(requestDTO.getName());
        product.setSku(requestDTO.getSku());
        product.setPrice(requestDTO.getPrice());

        // Save the product.
        productRepository.save(product);

        // Corrected: 4. Create initial Inventory entry
        Inventory inventory = new Inventory(
                product,
                warehouse,
                requestDTO.getInitialQuantity()
        );

        // Save the inventory entry.
        inventoryRepository.save(inventory);

        // Return the created product entity.
        return product;
    }
}