package com.vybrant.sales.analysis.domain;

import com.vybrant.sales.analysis.enums.BusinessType;
import com.vybrant.sales.analysis.enums.Type;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "analysis_client")
public class Client implements Serializable {

    /**
     * 002çClientIDçNameçBusiness
     * 002ç2345675434544345çJose da SilvaçRural
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public String getIdClient() {
        return idClient;
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BusinessType getBusinessType() {
        return businessType;
    }

    public void setBusinessType(BusinessType businessType) {
        this.businessType = businessType;
    }
}
