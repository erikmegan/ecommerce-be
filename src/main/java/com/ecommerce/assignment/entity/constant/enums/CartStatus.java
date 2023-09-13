package com.ecommerce.assignment.entity.constant.enums;

public enum  CartStatus {

    DELETED("DELETED"),
    CART("CART"),
    ORDERED("ORDERED");

    private String code;

    CartStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
