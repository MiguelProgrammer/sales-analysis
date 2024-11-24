package com.vybrant.sales.analysis.domain;

import com.vybrant.sales.analysis.enums.BusinessType;
import com.vybrant.sales.analysis.enums.Type;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "analysis_client")
public class Client implements Serializable {

    /**
     * 002çClientIDçNameçBusiness
     * 002ç2345675434544345çJose da SilvaçRural
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alc_id")
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

    public Client() {
    }

    public Client(String idClient, String name) {
        this.type = Type.CLIENT;
        this.idClient = idClient;
        this.name = name;
        this.businessType = BusinessType.RURAL;
    }

}
