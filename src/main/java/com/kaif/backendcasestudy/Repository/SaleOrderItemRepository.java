package com.kaif.backendcasestudy.Repository;

import com.kaif.backendcasestudy.Model.SaleOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleOrderItemRepository extends JpaRepository<SaleOrderItem, Integer> {
}
