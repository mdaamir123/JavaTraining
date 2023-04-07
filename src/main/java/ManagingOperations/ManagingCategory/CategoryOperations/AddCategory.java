package ManagingOperations.ManagingCategory.CategoryOperations;

import dao.CategoryDao;

public class AddCategory {

    public void addCategory() {
        System.out.println("Please enter category you want to add: ");
        CategoryDao.addCategory();
    }

}
