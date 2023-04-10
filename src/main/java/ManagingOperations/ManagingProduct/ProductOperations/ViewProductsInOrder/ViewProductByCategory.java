package ManagingOperations.ManagingProduct.ProductOperations.ViewProductsInOrder;

import dao.CategoryDao;
import dao.ProductDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ViewProductByCategory {
    List<List<String>> resultSet;
    List<List<String>> products = new ArrayList<>();
    Scanner sc = new Scanner(System.in);

    public List<List<String>> viewProductByCategory() {
        if(!CategoryDao.checkIfCategoriesExists()) {
            System.out.println("No categories available.");
            System.exit(0);
        }

        CategoryDao.getAllCategories();

        System.out.println("Enter id of the category: ");
        int id = sc.nextInt();
        sc.nextLine();

        if(!CategoryDao.checkIfCategoryExists(id)) {
            System.out.println("Please enter valid id.");
            System.exit(0);
        }

        resultSet = ProductDao.getProductsByCategory(id);

        if(resultSet.size() == 0) {
            System.out.println("No products available for given category.");
            System.exit(0);
        }

        for (var product : resultSet) {
            if(Integer.parseInt(product.get(4)) == id) {
                products.add(product);
            }
        }
        return products;
    }

}
