package com.vybrant.sales.analysis.domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "analysis_item")
public class Item implements Serializable {

    /**
     * [Item ID-Item Quantity-Item Price]
     * [1-34-10,2-33-1.50,3-40-0.10]
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "id_item")
    private Long idItem;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "price")
    private BigDecimal price;

    public Item() {
    }

    public Item(Long idItem, Integer quantity, BigDecimal price) {
        this.idItem = idItem;
        this.quantity = quantity;
        this.price = price;
    }

}
