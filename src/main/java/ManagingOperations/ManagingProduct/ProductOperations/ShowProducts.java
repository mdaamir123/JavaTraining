package ManagingOperations.ManagingProduct.ProductOperations;

import ManagingOperations.ManagingProduct.ProductOperations.ViewProductsInOrder.ShowProducts.PrintProductById;
import ManagingOperations.ManagingProduct.ProductOperations.ViewProductsInOrder.ShowProducts.PrintProducts;
import ManagingOperations.ManagingProduct.ProductOperations.ViewProductsInOrder.ViewProductByCategory;
import ManagingOperations.ManagingProduct.ProductOperations.ViewProductsInOrder.SortByPrice;
import ManagingOperations.ManagingProduct.ProductOperations.ViewProductsInOrder.SortByPriceAsc;
import config.DatabaseConfig;
import dao.ProductDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ShowProducts {
    DatabaseConfig config = new DatabaseConfig();
    Scanner sc = new Scanner(System.in);

    public void viewProducts() {
        if (!ProductDao.checkIfProductsExists()) {
            return;
        }

        List<List<String>> resultSet = ProductDao.getALlProducts();
        List<List<String>> attributeSet = ProductDao.getAllSpecifications();

        System.out.println("Select the way of viewing products: ");
        System.out.println("1. View all products.");
        System.out.println("2. View all by highest price.");
        System.out.println("3. View all by lowest price.");
        System.out.println("4. View products by category.");
        System.out.println("5. View Product by id.");

        int choice = sc.nextInt();

        switch (choice) {
            case 1:
                PrintProducts.printProducts(resultSet);
                break;
            case 2:
                SortByPrice sortByPrice = new SortByPrice(resultSet);
                PrintProducts.printProducts(sortByPrice.sortByPrice());
                break;
            case 3:
                SortByPriceAsc sortByPriceAsc = new SortByPriceAsc(resultSet);
                PrintProducts.printProducts(sortByPriceAsc.sortByPriceAsc());
                break;
            case 4:
                ViewProductByCategory viewProductByCategory = new ViewProductByCategory(resultSet);
                PrintProducts.printProducts(viewProductByCategory.viewProductByCategory());
                break;
            case 5:
                PrintProductById.printProductById(resultSet, attributeSet);
                break;
            default:
                break;
        }

    }
}
