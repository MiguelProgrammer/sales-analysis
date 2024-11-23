package com.vybrant.sales.analysis.repository;

import com.vybrant.sales.analysis.domain.Item;
import com.vybrant.sales.analysis.domain.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

}
