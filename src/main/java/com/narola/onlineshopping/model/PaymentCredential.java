package com.narola.onlineshopping.model;

import java.time.LocalDateTime;

public class PaymentCredential {
    private int paymentCredentialId;
    private String paymentCredentialName;
    private int createdBy;
    private int updatedBy;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

    public int getPaymentCredentialId() {
        return paymentCredentialId;
    }

    public void setPaymentCredentialId(int paymentCredentialId) {
        this.paymentCredentialId = paymentCredentialId;
    }

    public String getPaymentCredentialName() {
        return paymentCredentialName;
    }

    public void setPaymentCredentialName(String paymentCredentialName) {
        this.paymentCredentialName = paymentCredentialName;
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
