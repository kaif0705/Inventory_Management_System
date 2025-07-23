package com.kaif.backendcasestudy.Repository;

import com.kaif.backendcasestudy.Model.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductType, Integer> {
    Optional<ProductType> findByName(String perishable);
}
