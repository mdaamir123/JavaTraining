package com.narola.onlineshopping.display;

import com.narola.onlineshopping.service.product.productOperations.ViewProductsInOrder.ViewProductByCategory;
import com.narola.onlineshopping.model.CartItems;
import com.narola.onlineshopping.model.Product;
import com.narola.onlineshopping.model.Specification;
import com.narola.onlineshopping.model.Category;

import java.util.List;

public class Display {
    //TODO :
    public static void printCategories(List<Category> categories) {
        System.out.println("ID \t DATE_ADDED\t\t \t\t CATEGORY_NAME ");
        for (int i = 0; i < categories.size(); i++) {
            System.out.println(categories.get(i).getCategoryId() + " |\t" +
                    categories.get(i).getCreatedOn() + "\t\t |\t" +
                    categories.get(i).getCategoryName());
        }
    }

    public static void printDuplicateCategories(List<String> duplicateCategoriesList) {
        if (duplicateCategoriesList.isEmpty()) {
            System.out.println("There are no duplicate categories. ");
            return;
        }

        System.out.println("CATEGORY_NAME: \n");
        for (int i = 0; i < duplicateCategoriesList.size(); i++) {
            System.out.println(duplicateCategoriesList.get(i));
        }
    }

    public static void printProducts(List<Product> products) {
        if (products == null) {
            ViewProductByCategory viewProductByCategory = new ViewProductByCategory();
            Display.printProducts(viewProductByCategory.viewProductByCategory());
        } else if (products.size() == 0) {
            System.out.println("There are no products available.");
            ViewProductByCategory viewProductByCategory = new ViewProductByCategory();
            Display.printProducts(viewProductByCategory.viewProductByCategory());
        } else {
            System.out.println("Product ID \t Product Title \t\t Price \t\t Discount \t Category ID \t Brand");
            for (Product product : products) {
                System.out.println(product.getProductId() + "\t\t " + product.getProductTitle()
                        + "\t\t" + product.getProductPrice() + "\t\t" + product.getProductDiscount() + "% \t\t" + product.getProductCategoryId()
                        + "\t\t" + product.getProductBrand());
            }
        }
    }

    public static void printProductById(Product product) {
        System.out.println("ID            :" + product.getProductId());
        System.out.println("Title         :" + product.getProductTitle());
        System.out.println("Description   :" + product.getProductDescription());
        System.out.println("Price         :" + product.getProductPrice());
        System.out.println("Category ID   :" + product.getProductCategoryId());
        System.out.println("Discount      :" + product.getProductDiscount() + "%");
        System.out.println("Brand         :" + product.getProductBrand());
        System.out.println("Specifications:");
        for (Specification spec : product.getSpecificationList()) {
            System.out.println("\t\t\t" + spec.getSpecAttributeName() + " : " + spec.getSpecAttributeValue());
        }
    }

    public static void printProductSpecifications(List<Specification> specifications) {
        for (Specification specification : specifications) {
            System.out.println("ID: " + specification.getSpecId() +
                    " PRODUCT_ID: " + specification.getSpecProductId() +
                    " ATTRIBUTE_NAME: " + specification.getSpecAttributeName() +
                    " ATTRIBUTE_VALUE: " + specification.getSpecAttributeValue());
        }
    }

    public static void printUserCartItems(List<CartItems> cartItemsList) {
        if (cartItemsList.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }
        float totalCartValue = 0;
        System.out.println("ID\tProduct Title\tPrice\t\tBrand\t  Quantity");

        for (CartItems cartItem : cartItemsList) {
            System.out.println(cartItem.getProductId() + "\t" + cartItem.getProductTitle() + "\t" +
                    cartItem.getPrice() + "\t" + cartItem.getBrand() + "\t  " + cartItem.getQuantity());
            totalCartValue += (cartItem.getPrice() * cartItem.getQuantity());
        }
        System.out.println("Your total cart value is: " + totalCartValue);
    }
}
