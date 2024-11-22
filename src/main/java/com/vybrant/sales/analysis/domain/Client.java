package com.vybrant.sales.analysis.domain;

import com.vybrant.sales.analysis.enums.BusinessType;
import com.vybrant.sales.analysis.enums.Type;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "analysis_client")
public class Client {

    /**
     * 002çClientIDçNameçBusiness
     * 002ç2345675434544345çJose da SilvaçRural
     */
    @Id
    @Setter(AccessLevel.PRIVATE)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "type")
    private Type type;

    @Column(name = "id_client")
    private String idClient;

    @Column(name = "name")
    private String name;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "business_type")
    private BusinessType businessType;

    public Client CreateClient(String[] dados) {
        return Client.builder().type(Type.CLIENT).idClient(dados[1]).name(dados[2])
                .businessType(BusinessType.valueOf(dados[3].toUpperCase())).build();
    }
}
