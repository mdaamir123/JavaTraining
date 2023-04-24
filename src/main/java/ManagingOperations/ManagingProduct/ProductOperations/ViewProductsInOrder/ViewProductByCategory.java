package ManagingOperations.ManagingProduct.ProductOperations.ViewProductsInOrder;

import dao.CategoryDao;
import exception.DAOLayerException;
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
                return null;
            }

            Display.printCategories(CategoryDao.getAllCategories());

            System.out.println("Enter id of the category: ");
            int id = sc.nextInt();
            sc.nextLine();


            if (!CategoryDao.checkIfCategoryExists(id)) {
                System.out.println("Please enter valid id.");
                return null;
            }

            resultSet = ProductDao.getProductsByCategory(id);

            if (resultSet.size() == 0) {
                return products;
            }

            for (Product product : resultSet) {
                if (product.getProductCategoryId() == id) {
                    products.add(product);
                }
            }
            return products;
        } catch (DAOLayerException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return products;
    }
}
