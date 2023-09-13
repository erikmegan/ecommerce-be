package com.ecommerce.assignment.entity.constant.enums;

public enum PaymentStatus {
    PAID("PAID"),
    WAITING("WAITING");

    private String code;

    PaymentStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
