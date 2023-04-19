package ManagingOperations.ManagingProduct.ProductOperations.UpdateProducts;

import dao.CategoryDao;
import exceptions.DAOLayerException;
import dao.ProductDao;

import java.util.Scanner;

public class UpdateProductCategory {
    Scanner sc = new Scanner(System.in);

    public void updateProductCategory(int id) {
        try {
            if (!CategoryDao.checkIfCategoriesExists()) {
                return;
            }
        } catch (DAOLayerException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        System.out.println("Enter new category id from the below choices: ");
        try {
            CategoryDao.getAllCategories();
        } catch (DAOLayerException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        int newId = sc.nextInt();
        sc.nextLine();

        if (String.valueOf(newId).trim().length() == 0) {
            return;
        }

        try {
            if (!CategoryDao.checkIfCategoryExists(newId)) {
                return;
            }
        } catch (DAOLayerException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        try {
            ProductDao.updateProductCategory(id, newId);
        } catch (DAOLayerException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Successfully updated.");

    }
}
