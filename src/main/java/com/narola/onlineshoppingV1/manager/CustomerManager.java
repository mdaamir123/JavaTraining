package com.narola.onlineshoppingV1.manager;

public class CustomerManager {
    private ProductManager productManager = new ProductManager();

    public void customerOptions() {
        productManager.handleProductManagement();
    }
}
