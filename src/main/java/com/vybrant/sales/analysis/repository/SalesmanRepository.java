package com.vybrant.sales.analysis.repository;

import com.vybrant.sales.analysis.domain.Salesman;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SalesmanRepository extends JpaRepository<Salesman, Long> {

    @Query(name = "SELECT * FROM analysis_salesman WHERE name_salesman =:nameSalesman ORDER BY id DESC LIMIT 1", nativeQuery = true)
    List<Salesman> getByName(@Param("nameSalesman") String dado);
}
