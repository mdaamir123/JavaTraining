package ManagingOperations.ManagingProduct.ProductOperations.ViewProductsInOrder;

import dao.CategoryDao;
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
        if(!CategoryDao.checkIfCategoriesExists()) {
            System.out.println("No categories available.");
        }

        Display.printCategories(CategoryDao.getAllCategories());

        System.out.println("Enter id of the category: ");
        int id = sc.nextInt();
        sc.nextLine();

        if(!CategoryDao.checkIfCategoryExists(id)) {
            System.out.println("Please enter valid id.");
        }

        resultSet = ProductDao.getProductsByCategory(id);

        if(resultSet.size() == 0) {
            System.out.println("No products available for given category.");
        }

        for (Product product : resultSet) {
            if(product.getProductCategoryId() == id) {
                products.add(product);
            }
        }
        return products;
    }

}
