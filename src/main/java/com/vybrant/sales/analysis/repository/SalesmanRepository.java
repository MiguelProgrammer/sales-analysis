package com.vybrant.sales.analysis.repository;

import com.vybrant.sales.analysis.domain.Salesman;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SalesmanRepository extends JpaRepository<Salesman, Long> {

    Optional<Salesman> findByName(String dado);
}
