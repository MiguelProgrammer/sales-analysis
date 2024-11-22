package com.vybrant.sales.analysis.repository;

import com.vybrant.sales.analysis.domain.Salesman;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesmanRepository extends JpaRepository<Salesman, Long> {

}
