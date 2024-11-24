package com.vybrant.sales.analysis.repository;

import com.vybrant.sales.analysis.domain.Item;
import com.vybrant.sales.analysis.domain.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findByIdItem(Long idItem);
}
