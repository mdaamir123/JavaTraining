package ManagingOperations.ManagingCategory.CategoryOperations;

import dao.CategoryDao;
import exceptions.DAOLayerException;

import java.util.Scanner;

public class AddCategory {

    public void addCategory() {
        System.out.println("Please enter category you want to add: ");
        Scanner sc = new Scanner(System.in);
        String newCategory = sc.nextLine();
        //TODO : Take input here. Not in DAO. and pass data to dao method
        try {
            CategoryDao.addCategory(newCategory);
        } catch (DAOLayerException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Successfully added.");
    }

}
