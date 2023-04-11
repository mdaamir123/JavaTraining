package display;

import model.Category;
import model.Product;
import model.Specification;

import java.util.List;
import java.util.Scanner;

public class Display {
    //TODO :
    public static void printCategories(List<Category> categories) {
        for (int i = 0; i < categories.size(); i++) {
            System.out.println("ID: " + categories.get(i).getCategoryId() +
                    " CATEGORY_NAME: " + categories.get(i).getCategoryName() +
                    " DATE_ADDED: " + categories.get(i).getCreatedOn());
        }
    }

    public static void printDuplicateCategories(List<String> duplicateCategoriesList) {
        if (duplicateCategoriesList.isEmpty()) {
            System.out.println("There are no duplicate categories. ");
            return;
        }

        for (int i = 0; i < duplicateCategoriesList.size(); i++) {
            System.out.println("CATEGORY_NAME: " + duplicateCategoriesList.get(i));
        }
    }

    public static void printProducts(List<Product> products) {
        if(products.size() == 0) {
            System.out.println("There are no products available.");
        }

        for (var product : products) {
            System.out.println("Product ID: " + product.getProductId() + " Product Title: " + product.getProductTitle()
                    + " Price: " + product.getProductPrice() + " Discount: " + product.getProductDiscount() + "% Category ID: " + product.getProductCategoryId()
                    + " Brand: " + product.getProductBrand());
        }
    }

    public static void printProductById(List<Product> products, List<Specification> specifications) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter id of product.");
        int id = sc.nextInt();
        sc.nextLine();
        boolean exists = false;

        for (var product : products) {
            if (product.getProductId() == id) {
                exists = true;
                System.out.println("ID          :" + product.getProductId());
                System.out.println("Title       :" + product.getProductTitle());
                System.out.println("Description :" + product.getProductDescription());
                System.out.println("Price       :" + product.getProductPrice());
                System.out.println("Category ID :" + product.getProductCategoryId());
                System.out.println("Discount    :" + product.getProductDiscount() + "%");
                System.out.println("Brand       :" + product.getProductBrand());

            }
        }

        if (!exists) {
            System.out.println("No product available with id: " + id);
            return;
        }

        for (var spec : specifications) {
            if (spec.getSpecProductId() == id) {
                if (exists) {
                    System.out.println("Specifications:");
                    exists = false;
                }

                System.out.println("              " + spec.getSpecAttributeName() + " : " + spec.getSpecAttributeValue());
            }
        }
    }
}
