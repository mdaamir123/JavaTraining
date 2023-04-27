package ManagingOperations.ManagingCategory.CategoryOperations;

import ManagingOperations.ManagingCategory.CategoryManagement;
import dao.CategoryDao;
import display.Display;
import exception.DAOLayerException;

import java.util.Scanner;

public class DeleteCategory {
    public void deleteCategory() {
        Scanner sc = new Scanner(System.in);
        try {
            if (!CategoryDao.checkIfCategoriesExists()) {
                System.out.println("No category available !!!");
                CategoryManagement.handleCategoryManagement();
            }
            else {
                Display.printCategories(CategoryDao.getAllCategories());
                System.out.println("Please enter the id of the category you want to delete: ");

                int categoryId = sc.nextInt();
                sc.nextLine();

                if(!CategoryDao.checkIfCategoryExists(categoryId)) {
                    System.out.println("Category not present with id " + categoryId);
                    deleteCategory();
                }

                else{
                    CategoryDao.deleteCategory(categoryId);
                    System.out.println("Category successfully deleted !!!");
                    CategoryManagement.handleCategoryManagement();
                }
            }
        } catch (DAOLayerException de) {
            de.printStackTrace();
            deleteCategory();
        } catch (Exception e) {
            e.printStackTrace();
            deleteCategory();
        }
    }
}
