package com.vybrant.sales.analysis.domain;

import com.vybrant.sales.analysis.enums.Type;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "analysis_sale")
public class Sale {

    /**
     * 003çSale IDç[Item ID-Item Quantity-Item Price]çSalesman name
     * 003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çPaulo
     */
    @Id
    @Setter(AccessLevel.PRIVATE)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "type")
    private Type type;

    @Column(name = "id_sale")
    private String idSale;

    @Column(name = "specification")
    private String specification;

    @Column(name = "other_name")
    private String otherName;

    public Sale CreateSale(String[] dataSale){
        return Sale.builder().type(Type.SALE).idSale(dataSale[1])
                .specification(dataSale[2]).otherName(dataSale[3]).build();
    }
    
}
