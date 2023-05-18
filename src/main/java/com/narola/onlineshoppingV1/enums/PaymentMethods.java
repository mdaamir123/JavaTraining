package com.narola.onlineshoppingV1.enums;

public enum PaymentMethods {
    UPI(1),
    DEBIT_CARD(2),
    CREDIT_CARD(3),
    NETBANKING(4),
    COD(5);

    private final int value;

    private PaymentMethods(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
