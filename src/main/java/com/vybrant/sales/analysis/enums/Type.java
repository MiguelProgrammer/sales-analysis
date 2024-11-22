package com.vybrant.sales.analysis.enums;

public enum Type {

    SALESMAN("001"),
    CLIENT("001"),
    SALE("001");

    private final String numberType;

    Type(String numberType) {
        this.numberType = numberType;
    }
}
