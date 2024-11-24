package com.vybrant.sales.analysis.repository;

import com.vybrant.sales.analysis.domain.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query(name = "SELECT MAX(total) FROM analysis_sale limit 1", nativeQuery = true)
    Optional<Sale> findTopByOrderByTotalDesc();

    @Query(name = "SELECT MIN(total) FROM analysis_sale limit 1", nativeQuery = true)
    Optional<Sale> findFirstByOrderByTotalAsc();

    List<Sale> findByIdSale(String idSale);
}
