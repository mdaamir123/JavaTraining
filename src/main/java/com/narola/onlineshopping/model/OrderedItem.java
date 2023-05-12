package com.narola.onlineshopping.model;

import java.time.LocalDateTime;

public class OrderedItem {
    private int orderedItemId;
    private int orderId;
    private int cartId;
    private int createdBy;
    private int updatedBy;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

    public int getOrderedItemId() {
        return orderedItemId;
    }

    public void setOrderedItemId(int orderedItemId) {
        this.orderedItemId = orderedItemId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
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
