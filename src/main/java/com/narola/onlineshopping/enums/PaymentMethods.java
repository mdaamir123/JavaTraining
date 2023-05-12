package com.narola.onlineshopping.enums;

public enum PaymentMethods {
    UPI(1),
    DEBIT_CARD(2),
    CREDIT_CARD(3),
    NET_BANKING(4),
    COD(5);

    private final int value;

    private PaymentMethods(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
