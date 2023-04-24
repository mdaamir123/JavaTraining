package ManagingOperations.ManagingProduct.ProductOperations;

import ManagingOperations.ManagingProduct.ProductManagement;
import ManagingOperations.ManagingProduct.ProductOperations.ViewProductsInOrder.ViewProductByCategory;
import ManagingOperations.ManagingProduct.ProductOperations.ViewProductsInOrder.SortByPrice;
import ManagingOperations.ManagingProduct.ProductOperations.ViewProductsInOrder.SortByPriceAsc;
import ManagingOperations.ManagingProduct.ProductOperations.ViewProductsInOrder.ViewProductById;
import exception.DAOLayerException;
import dao.ProductDao;
import display.Display;

import java.util.Scanner;


public class ShowProducts {
    Scanner sc = new Scanner(System.in);

    public void viewProducts() {
        try {
            if (!ProductDao.checkIfProductsExists()) {
                System.out.println("No products are present.");
                return;
            }

            System.out.println("Select the way of viewing products: ");
            System.out.println("1. View all products.");
            System.out.println("2. View all by highest price.");
            System.out.println("3. View all by lowest price.");
            System.out.println("4. View products by category.");
            System.out.println("5. View Product by id.");
            System.out.println("6. Back");

            int choice = sc.nextInt();

            final int VIEW_ALL_PRODUCTS = 1;
            final int VIEW_BY_HIGHEST_PRICE = 2;
            final int VIEW_BY_LOWEST_PRICE = 3;
            final int VIEW_BY_CATEGORY = 4;
            final int VIEW_BY_ID = 5;
            final int BACK = 6;
            final int LOGOUT = 7;
            switch (choice) {
                case VIEW_ALL_PRODUCTS:
                    Display.printProducts(ProductDao.getALlProducts());
                    break;
                case VIEW_BY_HIGHEST_PRICE:
                    SortByPrice sortByPrice = new SortByPrice(ProductDao.getALlProducts());
                    Display.printProducts(sortByPrice.sortByPrice());
                    break;
                case VIEW_BY_LOWEST_PRICE:
                    SortByPriceAsc sortByPriceAsc = new SortByPriceAsc(ProductDao.getALlProducts());
                    Display.printProducts(sortByPriceAsc.sortByPriceAsc());
                    break;
                case VIEW_BY_CATEGORY:
                    ViewProductByCategory viewProductByCategory = new ViewProductByCategory();
                    Display.printProducts(viewProductByCategory.viewProductByCategory());
                    break;
                case VIEW_BY_ID:
                    ViewProductById.getProductById();
                    break;
                case BACK:
                    ProductManagement.handleProductManagement();
                    break;
                default:
                    viewProducts();
                    break;
            }
            viewProducts();
        } catch (DAOLayerException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
