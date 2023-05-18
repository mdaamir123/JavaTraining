package com.narola.onlineshoppingV1.model;

import java.time.LocalDateTime;

public class CreditCardType {
    private int creditCardTypeId;
    private String creditCardTypeName;
    private int createdBy;
    private int updatedBy;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

    public int getCreditCardTypeId() {
        return creditCardTypeId;
    }

    public void setCreditCardTypeId(int creditCardTypeId) {
        this.creditCardTypeId = creditCardTypeId;
    }

    public String getCreditCardTypeName() {
        return creditCardTypeName;
    }

    public void setCreditCardTypeName(String creditCardTypeName) {
        this.creditCardTypeName = creditCardTypeName;
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
