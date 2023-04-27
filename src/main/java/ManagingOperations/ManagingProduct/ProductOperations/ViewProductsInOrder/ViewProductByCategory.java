package ManagingOperations.ManagingProduct.ProductOperations.ViewProductsInOrder;

import ManagingOperations.ManagingProduct.ProductOperations.ShowProducts;
import comparator.SortProductsByPrice;
import dao.CategoryDao;
import exception.DAOLayerException;
import dao.ProductDao;
import display.Display;
import model.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class ViewProductByCategory {
    List<Product> resultSet;
    List<Product> products = new ArrayList<>();
    Scanner sc = new Scanner(System.in);

    public List<Product> viewProductByCategory() {

        final int RETRIEVED_ORDER = 1;
        final int HIGHEST_PRICE = 2;
        final int LOWEST_PRICE = 3;
        final int BACK = 4;

        try {
            if (!CategoryDao.checkIfCategoriesExists()) {
                System.out.println("No categories available.");
                return null;
            }

            Display.printCategories(CategoryDao.getAllCategories());
            int choice = displayAndSelectViewingChoices();


            switch (choice) {
                case RETRIEVED_ORDER:
                    resultSet = getProducts();
                    if(resultSet == null) {
                        return null;
                    }
                    if (resultSet.size() == 0) {
                        return products;
                    }
                    break;
                case HIGHEST_PRICE:
                    resultSet = getProducts();
                    if(resultSet == null) {
                        return null;
                    }
                    if (resultSet.size() == 0) {
                        return products;
                    }
                    Collections.sort(resultSet, new SortProductsByPrice().reversed());
                    break;
                case LOWEST_PRICE:
                    resultSet = getProducts();
                    if(resultSet == null) {
                        return null;
                    }
                    if (resultSet.size() == 0) {
                        return products;
                    }
                    Collections.sort(resultSet, new SortProductsByPrice());
                    break;
                case BACK:
                    ShowProducts showProducts = new ShowProducts();
                    showProducts.viewProducts();
                    break;
                default:
                    System.out.println("Invalid choice selected.");
                    viewProductByCategory();
                    break;
            }


            for (Product product : resultSet) {
                products.add(product);
            }

            return products;
        } catch (DAOLayerException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return products;
    }

    private int displayAndSelectViewingChoices() {
        System.out.println("Please enter choice of viewing products: ");
        System.out.println("1: In retrieved order");
        System.out.println("2: By highest price");
        System.out.println("3: By lowest price");
        System.out.println("4: Back");
        int choice = sc.nextInt();
        sc.nextLine();
        return choice;
    }

    private List<Product> getProducts() {
        System.out.println("Enter id of the category: ");
        int id = sc.nextInt();
        sc.nextLine();

        try {
            if (!CategoryDao.checkIfCategoryExists(id)) {
                System.out.println("Please enter valid id.");
                return null;
            }

            resultSet = ProductDao.getProductsByCategory(id);
            return resultSet;
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
        return null;
    }
}
