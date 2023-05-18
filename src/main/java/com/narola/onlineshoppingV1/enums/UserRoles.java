package com.narola.onlineshoppingV1.enums;

public enum UserRoles {
    ADMIN(1),
    CUSTOMER(2);

    private final int value;

    private UserRoles(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
