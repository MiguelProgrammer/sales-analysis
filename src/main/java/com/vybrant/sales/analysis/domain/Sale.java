package com.vybrant.sales.analysis.domain;

import com.vybrant.sales.analysis.enums.Type;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "analysis_sale")
public class Sale implements Serializable {

    /**
     * 003çSale IDç[Item ID-Item Quantity-Item Price]çSalesman name
     * 003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çPedro
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "type")
    private Type type;

    @Column(name = "id_sale")
    private String idSale;

    @Column(name = "total")
    private BigDecimal total;

    @OneToMany(targetEntity = Item.class, fetch = FetchType.EAGER, cascade = CascadeType.MERGE, orphanRemoval = true)
    private Set<Item> items;

    @OneToOne(targetEntity = Salesman.class, fetch = FetchType.EAGER, cascade = CascadeType.MERGE, optional = false)
    private Salesman salesman;

    public Sale() {
    }

    public Sale(String idSale, Set<Item> items, Salesman salesman, BigDecimal total) {
        this.type = Type.SALE;
        this.idSale = idSale;
        this.items = items;
        this.salesman = salesman;
        this.total = total;
    }
}
