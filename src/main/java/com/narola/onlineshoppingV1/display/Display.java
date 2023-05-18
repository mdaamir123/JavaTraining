package com.narola.onlineshoppingV1.display;

import com.narola.onlineshoppingV1.model.*;

import java.util.List;

public class Display {
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
        if (products.size() == 0) {
            System.out.println("There are no products available.");
            return;
        }

        System.out.println("Product ID \t Product Title \t\t Price \t\t Discount \t Category ID \t Brand");
        for (Product product : products) {
            System.out.println(product.getProductId() + "\t\t " + product.getProductTitle()
                    + "\t\t" + product.getProductPrice() + "\t\t" + product.getProductDiscount() + "% \t\t" + product.getProductCategoryId()
                    + "\t\t" + product.getProductBrand());
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

    public static void printUserCartItems(List<CartItem> cartItemsList) {
        if (cartItemsList.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }
        float totalCartValue = 0;
        System.out.println("ID\tProduct Title\tPrice\t\tBrand\t  Quantity");
        for (CartItem cartItem : cartItemsList) {
            System.out.println(cartItem.getProductId() + "\t" + cartItem.getProductTitle() + "\t" +
                    cartItem.getPrice() + "\t" + cartItem.getBrand() + "\t  " + cartItem.getQuantity());
            totalCartValue += (cartItem.getPrice() * cartItem.getQuantity());
        }
        System.out.println("Your total cart value is: " + totalCartValue);
    }

    public static void printUserAddress(List<UserAddress> userAddressList) {
        System.out.println("ID\tAddress_Line_1\tAddress_line_2\tLandmark\tPincode\tCity\tState");
        for (UserAddress userAddress : userAddressList) {
            System.out.println(userAddress.getUserAddressId() + "\t" + userAddress.getAddressLine1()
                    + "\t" + userAddress.getAddressLine2() + "\t" + userAddress.getLandmark() + "\t"
                    + userAddress.getPincode() + "\t" + userAddress.getCityName() + "\t" + userAddress.getStateName());
        }
    }

    public static void printCities(List<City> cityList) {
        System.out.println("ID\t\tCity");
        for (City city : cityList) {
            System.out.println(city.getCityId() + "\t\t" + city.getCityName());
        }
    }

    public static void printStates(List<State> stateList) {
        System.out.println("ID\t\tCity");
        for (State state : stateList) {
            System.out.println(state.getStateId() + "\t\t" + state.getStateName());
        }
    }

    public static void printBankNames(List<Bank> bankList) {
        System.out.println("ID\t\tBank");
        for (Bank bank : bankList) {
            System.out.println(bank.getBankId() + "\t\t" + bank.getBankName());
        }
    }

    public static void printCreditCardTypes(List<CreditCardType> creditCardTypeList) {
        System.out.println("ID\t\tCredit Card Type");
        for (CreditCardType creditCardType : creditCardTypeList) {
            System.out.println(creditCardType.getCreditCardTypeId() + "\t\t" +
                    creditCardType.getCreditCardTypeName());
        }
    }

    public static void printPaymentCredentials(List<PaymentCredential> paymentCredentialList) {
        System.out.println("ID\t\tPayment Credential");
        for (PaymentCredential paymentCredential : paymentCredentialList) {
            System.out.println(paymentCredential.getPaymentCredentialId() + "\t\t" +
                    paymentCredential.getPaymentCredentialName());
        }
    }

    public static void printPaymentMethods(List<PaymentMethod> paymentMethodsList) {
        System.out.println("ID\t\tPayment Methods");
        for (PaymentMethod paymentMethod : paymentMethodsList) {
            System.out.println(paymentMethod.getPaymentMethodId() + "\t\t" +
                    paymentMethod.getPaymentMethodName());
        }
    }

    public static void printUserOrders(List<Order> orderList) {
        System.out.println("ID\t\tTotal Amount\t\tOrdered Date");
        for (Order order : orderList) {
            System.out.println(order.getOrderId() + "\t\t" +
                    order.getTotalAmount() + "\t\t" + order.getOrderDate());
        }
    }
}
