package com.vybrant.sales.analysis.domain;

import com.vybrant.sales.analysis.enums.Type;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.context.annotation.Primary;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "analysis_salesman")
public class Salesman implements Serializable {

    /**
     * 001çSalesmanIDçNameçSalary
     * 001ç1234567891234çPedroç50000
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
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

    @ManyToOne(targetEntity = Sale.class, fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Sale sale;

    @Column(name = "date")
    private Date date = new Date();

    public Salesman() {
    }

    public Salesman(String idSalesman, String name, BigDecimal salary) {
        this.type = Type.SALESMAN;
        this.idSalesman = idSalesman;
        this.name = name;
        this.salary = salary;
    }
}
