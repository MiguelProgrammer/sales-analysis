package com.vybrant.sales.analysis.domain;

import com.vybrant.sales.analysis.enums.Type;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "analysis_sale")
public class Sale implements Serializable {

    /**
     * 003çSale IDç[Item ID-Item Quantity-Item Price]çSalesman name
     * 003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çPedro
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "type")
    private Type type;

    @Column(name = "id_sale")
    private String idSale;

    @OneToMany(targetEntity = Item.class)
    private Set<Item> items;

    @JoinColumn(name = "id_salesman")
    private Salesman salesman;

    public Sale() {
    }

    public Sale(String idSale, Set<Item> items, Salesman salesman) {
        this.type = Type.SALE;
        this.idSale = idSale;
        this.items = items;
        this.salesman = salesman;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getIdSale() {
        return idSale;
    }

    public void setIdSale(String idSale) {
        this.idSale = idSale;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }

    public Salesman getSalesman() {
        return salesman;
    }

    public void setSalesman(Salesman salesman) {
        this.salesman = salesman;
    }
}
