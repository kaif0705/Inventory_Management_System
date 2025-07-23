package com.kaif.backendcasestudy.Repository;

import com.kaif.backendcasestudy.Model.ProductSupplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductSupplierRepository extends JpaRepository<ProductSupplier, Long> {
    List<ProductSupplier> findByProductId(Long id);

    Optional<Object> findByProductIdAndSupplierId(Long id, Long id1);
}
