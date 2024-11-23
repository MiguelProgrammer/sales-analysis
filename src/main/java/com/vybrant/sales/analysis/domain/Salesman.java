package com.vybrant.sales.analysis.domain;

import com.vybrant.sales.analysis.enums.Type;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;

@Entity
@Table(name = "analysis_salesman")
public class Salesman implements Serializable {

    /**
     * 001çSalesmanIDçNameçSalary
     * 001ç1234567891234çPedroç50000
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "type")
    private Type type;

    @Column(name = "id_salesman")
    private String idSalesman;

    @Column(name = "name_salesman")
    private String name;

    @Column(name = "salary")
    private BigDecimal salary;

    @JoinColumn(name = "id_sale")
    private Sale sale;

    public Salesman() {
    }

    public Salesman(String idSalesman, String name, BigDecimal salary) {
        this.type = Type.SALESMAN;
        this.idSalesman = idSalesman;
        this.name = name;
        this.salary = salary;
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

    public String getIdSalesman() {
        return idSalesman;
    }

    public void setIdSalesman(String idSalesman) {
        this.idSalesman = idSalesman;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }
}
