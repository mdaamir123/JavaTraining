package com.narola.onlineshopping.model;

import java.time.LocalDateTime;

public class UserPaymentCredential {
    private int userPaymentCredentialId;
    private int orderId;
    private int credentialId;
    private String credentialValue;
    private int createdBy;
    private int updatedBy;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

    public int getUserPaymentCredentialId() {
        return userPaymentCredentialId;
    }

    public void setUserPaymentCredentialId(int userPaymentCredentialId) {
        this.userPaymentCredentialId = userPaymentCredentialId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getCredentialId() {
        return credentialId;
    }

    public void setCredentialId(int credentialId) {
        this.credentialId = credentialId;
    }

    public String getCredentialValue() {
        return credentialValue;
    }

    public void setCredentialValue(String credentialValue) {
        this.credentialValue = credentialValue;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public int getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(int updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }
}
