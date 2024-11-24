package com.vybrant.sales.analysis.repository;

import com.vybrant.sales.analysis.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
