package com.vybrant.sales.analysis.domain;

import com.vybrant.sales.analysis.enums.Type;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "analysis_salesman")
public class Salesman {

    /**
     * 001çSalesmanIDçNameçSalary
     * 001ç1234567891234çPedroç50000
     */
    @Id
    @Setter(AccessLevel.PRIVATE)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "type")
    private Type type;

    @Column(name = "id_salesman")
    private String idSalesman;

    @Column(name = "name")
    private String name;

    @Column(name = "salary")
    private BigDecimal salary;

    public Salesman CreateSalesman(String[] dados) {
        return Salesman.builder()
                .type(Type.SALESMAN)
                .idSalesman(dados[1]).name(dados[2])
                .salary(new BigDecimal(dados[3]).setScale(2, RoundingMode.HALF_UP)).build();
    }
}
