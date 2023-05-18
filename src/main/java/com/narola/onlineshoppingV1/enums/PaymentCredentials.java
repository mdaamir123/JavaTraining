package com.narola.onlineshoppingV1.enums;

public enum PaymentCredentials {
    UPI_ID(1),
    DEBIT_CARD_NUMBER(2),
    NAME_ON_THE_DEBIT_CARD(3),
    DEBIT_CARD_CVV_CODE(4),
    DEBIT_CARD_EXPIRATION_DATE(5),
    CASH(6),
    CREDIT_CARD_NUMBER(7),
    CREDIT_CARD_EXPIRATION_DATE(8),
    CREDIT_CARD_CVV_CODE(9),
    NAME_ON_THE_CREDIT_CARD(10),
    CREDIT_CARD_TYPE(11),
    NETBANKING_USER_ID(12),
    NETBANKING_PASSWORD(13);

    private final int value;

    private PaymentCredentials(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
