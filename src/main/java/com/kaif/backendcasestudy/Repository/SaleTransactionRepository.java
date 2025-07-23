package com.kaif.backendcasestudy.Repository;

import com.kaif.backendcasestudy.Model.SaleOrderItem;
import com.kaif.backendcasestudy.Model.SaleTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SaleTransactionRepository extends JpaRepository<SaleTransaction, Long> {
    @Query("SELECT soi FROM SaleOrderItem soi " +
            "JOIN soi.saleTransaction st " +
            "JOIN st.warehouse w " +
            "WHERE soi.product.id = :productId " + // Note :productId
            "AND w.company.id = :companyId " +     // Note :companyId
            "AND st.transactionDate BETWEEN :startDate AND :endDate")
    List<SaleOrderItem> findSaleOrderItemsByProductAndCompanyAndDateRange(
            @Param("productId") Long productId, // @Param annotation links to :productId in query
            @Param("companyId") Long companyId, // @Param annotation links to :companyId in query
            @Param("startDate") LocalDateTime startDate, // @Param annotation links to :startDate in query
            @Param("endDate") LocalDateTime endDate      // @Param annotation links to :endDate in query
    );
}
