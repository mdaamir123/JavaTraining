package com.narola.onlineshoppingV1.model;

import java.time.LocalDateTime;

public class Specification {
    private int specId;
    private int specProductId;
    private String specAttributeName;
    private String specAttributeValue;
    private int createdBy;
    private int updatedBy;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

    public int getSpecId() {
        return specId;
    }

    public void setSpecId(int specId) {
        this.specId = specId;
    }

    public int getSpecProductId() {
        return specProductId;
    }

    public void setSpecProductId(int specProductId) {
        this.specProductId = specProductId;
    }

    public String getSpecAttributeName() {
        return specAttributeName;
    }

    public void setSpecAttributeName(String specAttributeName) {
        this.specAttributeName = specAttributeName;
    }

    public String getSpecAttributeValue() {
        return specAttributeValue;
    }

    public void setSpecAttributeValue(String specAttributeValue) {
        this.specAttributeValue = specAttributeValue;
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
