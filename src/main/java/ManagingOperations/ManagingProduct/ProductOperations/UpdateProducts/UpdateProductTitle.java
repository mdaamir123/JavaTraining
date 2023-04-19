package ManagingOperations.ManagingProduct.ProductOperations.UpdateProducts;

import exceptions.DAOLayerException;
import dao.ProductDao;

import java.util.Scanner;

public class UpdateProductTitle {
    public void updateProductTitle(int id) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter new product title. Leave blank if don't want to modify.");
        String newTitle = sc.nextLine();

        if (newTitle.trim().length() == 0) {
            return;
        }

        try {
            ProductDao.updateProductTitle(id, newTitle);
        } catch (DAOLayerException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Successfully updated.");
    }
}
