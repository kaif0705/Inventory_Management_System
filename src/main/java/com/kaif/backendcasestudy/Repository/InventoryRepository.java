package com.kaif.backendcasestudy.Repository;

import com.kaif.backendcasestudy.Model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Optional<Object> findByProductIdAndWarehouseId(Long id, Long id1);
}
