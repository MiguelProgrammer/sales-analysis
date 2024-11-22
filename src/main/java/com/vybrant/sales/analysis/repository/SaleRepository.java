package com.vybrant.sales.analysis.repository;

import com.vybrant.sales.analysis.domain.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Long> {

}
