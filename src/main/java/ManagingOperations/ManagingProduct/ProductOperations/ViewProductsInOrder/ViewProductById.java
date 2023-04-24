package ManagingOperations.ManagingProduct.ProductOperations.ViewProductsInOrder;

import dao.ProductDao;
import display.Display;
import exception.DAOLayerException;

import java.util.Scanner;

public class ViewProductById {
    public static void getProductById() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter id of product.");
            int productId = sc.nextInt();
            sc.nextLine();
            if (!ProductDao.checkIfProductExists(productId)) {
                System.out.println("No such product exists !!!");
                return;
            }

            Display.printProductById(ProductDao.getProductById(productId));
        } catch (DAOLayerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
