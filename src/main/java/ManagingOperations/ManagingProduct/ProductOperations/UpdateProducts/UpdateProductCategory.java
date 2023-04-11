package ManagingOperations.ManagingProduct.ProductOperations.UpdateProducts;

import dao.CategoryDao;
import dao.ProductDao;

import java.util.Scanner;

public class UpdateProductCategory {
    Scanner sc = new Scanner(System.in);

    public  void updateProductCategory(int id) {
        if(!CategoryDao.checkIfCategoriesExists()) {
            return;
        }

        System.out.println("Enter new category id from the below choices: ");
        CategoryDao.getAllCategories();

        int newId = sc.nextInt();
        sc.nextLine();

        if (String.valueOf(newId).trim().length() == 0) {
            return;
        }

        if(!CategoryDao.checkIfCategoryExists(newId)) {
            return;
        }

        ProductDao.updateProductCategory(id, newId);
        System.out.println("Successfully updated.");

    }
}
