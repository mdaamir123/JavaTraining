package ManagingOperations.ManagingProduct.ProductOperations;

import ManagingOperations.ManagingProduct.ProductOperations.ViewProductsInOrder.ViewProductByCategory;
import ManagingOperations.ManagingProduct.ProductOperations.ViewProductsInOrder.SortByPrice;
import ManagingOperations.ManagingProduct.ProductOperations.ViewProductsInOrder.SortByPriceAsc;
import ManagingOperations.ManagingProduct.ProductOperations.ViewProductsInOrder.ViewProductById;
import dao.ProductDao;
import display.Display;
import model.Product;
import model.Specification;

import java.util.List;
import java.util.Scanner;

public class ShowProducts {
    Scanner sc = new Scanner(System.in);

    public void viewProducts() {
        if (!ProductDao.checkIfProductsExists()) {
            System.out.println("No products are present.");
            return;
        }

        List<Product> resultSet = ProductDao.getALlProducts();
        List<Specification> attributeSet = ProductDao.getAllSpecifications();

        System.out.println("Select the way of viewing products: ");
        System.out.println("1. View all products.");
        System.out.println("2. View all by highest price.");
        System.out.println("3. View all by lowest price.");
        System.out.println("4. View products by category.");
        System.out.println("5. View Product by id.");

        int choice = sc.nextInt();

        switch (choice) {
            case 1:
                Display.printProducts(resultSet);
                break;
            case 2:
                SortByPrice sortByPrice = new SortByPrice(resultSet);
                Display.printProducts(sortByPrice.sortByPrice());
                break;
            case 3:
                SortByPriceAsc sortByPriceAsc = new SortByPriceAsc(resultSet);
                Display.printProducts(sortByPriceAsc.sortByPriceAsc());
                break;
            case 4:
                ViewProductByCategory viewProductByCategory = new ViewProductByCategory();
                Display.printProducts(viewProductByCategory.viewProductByCategory());
                break;
            case 5:
                ViewProductById.getProductById(resultSet, attributeSet);
                break;
            default:
                break;
        }

    }
}
