package com.narola.onlineshopping.service.product.productOperations.ViewProductsInOrder;

import com.narola.onlineshopping.comparator.SortProductsByPrice;
import com.narola.onlineshopping.model.Product;

import java.util.Collections;
import java.util.List;

public class SortByPrice {
    List<Product> products;

    public SortByPrice(List<Product> products) {
        this.products = products;
    }

    public List<Product> sortByPrice() {
        Collections.sort(products, new SortProductsByPrice().reversed());
        return products;
    }
}
