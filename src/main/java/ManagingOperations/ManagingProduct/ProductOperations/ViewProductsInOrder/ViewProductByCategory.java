package ManagingOperations.ManagingProduct.ProductOperations.ViewProductsInOrder;

import dao.CategoryDao;
import exceptions.DAOLayerException;
import dao.ProductDao;
import display.Display;
import model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ViewProductByCategory {
    List<Product> resultSet;
    List<Product> products = new ArrayList<>();
    Scanner sc = new Scanner(System.in);

    public List<Product> viewProductByCategory() {
        try {
            if (!CategoryDao.checkIfCategoriesExists()) {
                System.out.println("No categories available.");
            }
        } catch (DAOLayerException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        try {
            Display.printCategories(CategoryDao.getAllCategories());
        } catch (DAOLayerException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        System.out.println("Enter id of the category: ");
        int id = sc.nextInt();
        sc.nextLine();

        try {
            if (!CategoryDao.checkIfCategoryExists(id)) {
                System.out.println("Please enter valid id.");
            }
        } catch (DAOLayerException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        try {
            resultSet = ProductDao.getProductsByCategory(id);
        } catch (DAOLayerException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        if (resultSet.size() == 0) {
            System.out.println("No products available for given category.");
        }

        for (Product product : resultSet) {
            if (product.getProductCategoryId() == id) {
                products.add(product);
            }
        }
        return products;
    }

}
