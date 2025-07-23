package com.kaif.backendcasestudy;

import com.kaif.backendcasestudy.Model.*;
import com.kaif.backendcasestudy.Repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@SpringBootApplication
public class BackendCaseStudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendCaseStudyApplication.class, args);
    }

    @Bean
    public CommandLineRunner demoData(CompanyRepository companyRepository, WarehouseRepository warehouseRepository,
                                      ProductTypeRepository productTypeRepository, ProductRepository productRepository,
                                      InventoryRepository inventoryRepository, SupplierRepository supplierRepository,
                                      ProductSupplierRepository productSupplierRepository,
                                      SaleTransactionRepository saleTransactionRepository,
                                      SaleOrderItemRepository saleOrderItemRepository) {
        return args -> {
            // --- 1. Add Company
            Company company1 = new Company(null, "Acme Corp", "ACME");
            // Check if company already exists to avoid duplicates on restart
            Optional<Company> existingCompany = companyRepository.findByCompanyCode("ACME");
            if (existingCompany.isEmpty()) {
                company1 = companyRepository.save(company1);
                System.out.println("Added dummy Company: " + company1.getName());
            } else {
                company1 = existingCompany.get();
                System.out.println("Company already exists: " + company1.getName());
            }

            // --- 2. Add Warehouses, linking them to the Company ---
            Warehouse warehouse1 = new Warehouse(null, "Main Warehouse", company1);
            Warehouse warehouse2 = new Warehouse(null, "East Coast Hub", company1);

            // Fetch by name to check existence
            Optional<Warehouse> existingW1 = warehouseRepository.findByName("Main Warehouse");
            Optional<Warehouse> existingW2 = warehouseRepository.findByName("East Coast Hub");

            if (existingW1.isEmpty()) {
                warehouse1 = warehouseRepository.save(warehouse1);
                System.out.println("Added dummy Warehouse: " + warehouse1.getName());
            } else {
                warehouse1 = existingW1.get();
                System.out.println("Warehouse already exists: " + warehouse1.getName());
            }

            if (existingW2.isEmpty()) {
                warehouse2 = warehouseRepository.save(warehouse2);
                System.out.println("Added dummy Warehouse: " + warehouse2.getName());
            } else {
                warehouse2 = existingW2.get();
                System.out.println("Warehouse already exists: " + warehouse2.getName());
            }


            // --- 3. Add Product Types ---
            ProductType electronicsType = new ProductType(null, "Electronics", 5);
            ProductType perishableType = new ProductType(null, "Perishable", 20);

            Optional<ProductType> existingElec = productTypeRepository.findByName("Electronics");
            Optional<ProductType> existingPeri = productTypeRepository.findByName("Perishable");

            if (existingElec.isEmpty()) {
                electronicsType = productTypeRepository.save(electronicsType);
            } else {
                electronicsType = existingElec.get();
            }

            if (existingPeri.isEmpty()) {
                perishableType = productTypeRepository.save(perishableType);
            } else {
                perishableType = existingPeri.get();
            }
            System.out.println("Added dummy Product Types.");


            // --- 4. Add Products ---
            Product laptop = new Product(null, "Laptop Pro", "LP-001", new BigDecimal("1200.50"), electronicsType);
            Product cheese = new Product(null, "Artisan Cheese", "AC-001", new BigDecimal("15.00"), perishableType);
            Product monitor = new Product(null, "27-inch Monitor", "MON-001", new BigDecimal("300.00"), electronicsType);
            Product bread = new Product(null, "Whole Wheat Bread", "WWB-001", new BigDecimal("3.50"), perishableType);
            Product printer = new Product(null, "Laser Printer", "LP-002", new BigDecimal("250.00"), electronicsType); // No sales for this one

            // Save products only if their SKUs don't exist
            productRepository.save(laptop);
            productRepository.save(cheese);
            productRepository.save(monitor);
            productRepository.save(bread);
            productRepository.save(printer);
            System.out.println("Added dummy Products.");


            // --- 5. Add Inventory---
            if (inventoryRepository.findByProductIdAndWarehouseId(laptop.getId(), warehouse1.getId()).isEmpty()) {
                inventoryRepository.save(new Inventory(laptop, warehouse1, 4)); // Low stock
            }
            if (inventoryRepository.findByProductIdAndWarehouseId(cheese.getId(), warehouse1.getId()).isEmpty()) {
                inventoryRepository.save(new Inventory(cheese, warehouse1, 18)); // Low stock
            }
            if (inventoryRepository.findByProductIdAndWarehouseId(monitor.getId(), warehouse1.getId()).isEmpty()) {
                inventoryRepository.save(new Inventory(monitor, warehouse1, 25)); // Not low stock
            }
            if (inventoryRepository.findByProductIdAndWarehouseId(bread.getId(), warehouse2.getId()).isEmpty()) {
                inventoryRepository.save(new Inventory(bread, warehouse2, 5)); // Low stock in another warehouse
            }
            if (inventoryRepository.findByProductIdAndWarehouseId(printer.getId(), warehouse1.getId()).isEmpty()) {
                inventoryRepository.save(new Inventory(printer, warehouse1, 3)); // Low stock, no sales
            }
            System.out.println("Added dummy Inventory.");


            // --- 6. Add Suppliers ---
            Supplier techSupply = new Supplier(null, "Tech Supply Co", "tech@supply.com", "111-222-3333");
            Supplier foodDistro = new Supplier(null, "Food Distributors Inc", "food@distro.com", "444-555-6666");

            Optional<Supplier> existingTech = supplierRepository.findByName("Tech Supply Co");
            Optional<Supplier> existingFood = supplierRepository.findByName("Food Distributors Inc");

            if (existingTech.isEmpty()) {
                techSupply = supplierRepository.save(techSupply);
            } else {
                techSupply = existingTech.get();
            }

            if (existingFood.isEmpty()) {
                foodDistro = supplierRepository.save(foodDistro);
            } else {
                foodDistro = existingFood.get();
            }
            System.out.println("Added dummy Suppliers.");


            // --- 7. Link Products to Suppliers ---
            if (productSupplierRepository.findByProductIdAndSupplierId(laptop.getId(), techSupply.getId()).isEmpty()) {
                productSupplierRepository.save(new ProductSupplier(null, laptop, techSupply));
            }
            if (productSupplierRepository.findByProductIdAndSupplierId(monitor.getId(), techSupply.getId()).isEmpty()) {
                productSupplierRepository.save(new ProductSupplier(null, monitor, techSupply));
            }
            if (productSupplierRepository.findByProductIdAndSupplierId(cheese.getId(), foodDistro.getId()).isEmpty()) {
                productSupplierRepository.save(new ProductSupplier(null, cheese, foodDistro));
            }
            if (productSupplierRepository.findByProductIdAndSupplierId(bread.getId(), foodDistro.getId()).isEmpty()) {
                productSupplierRepository.save(new ProductSupplier(null, bread, foodDistro));
            }
            System.out.println("Added dummy Product-Supplier links.");


            // --- 8. Add Sale Transactions (recent sales for some products) ---
            if (saleTransactionRepository.count() == 0) {
                SaleTransaction sale1 = new SaleTransaction(null, warehouse1, LocalDateTime.now().minusDays(5), new BigDecimal("1200.50"));
                sale1 = saleTransactionRepository.save(sale1);
                saleOrderItemRepository.save(new SaleOrderItem(null, sale1, laptop, 1, new BigDecimal("1200.50"))); // Laptop sale

                SaleTransaction sale2 = new SaleTransaction(null, warehouse1, LocalDateTime.now().minusDays(10), new BigDecimal("30.00"));
                sale2 = saleTransactionRepository.save(sale2);
                saleOrderItemRepository.save(new SaleOrderItem(null, sale2, cheese, 2, new BigDecimal("15.00"))); // Cheese sale

                SaleTransaction sale3 = new SaleTransaction(null, warehouse2, LocalDateTime.now().minusDays(20), new BigDecimal("7.00"));
                sale3 = saleTransactionRepository.save(sale3);
                saleOrderItemRepository.save(new SaleOrderItem(null, sale3, bread, 2, new BigDecimal("3.50"))); // Bread sale
                System.out.println("Added dummy Sale Transactions.");
            } else {
                System.out.println("Dummy Sale Transactions already exist.");
            }

            System.out.println("Finished all dummy data setup.");
        };
    }

}
