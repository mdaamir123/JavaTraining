package ManagingOperations.ManagingProduct.ProductOperations;

import config.DatabaseConfig;
import dao.CategoryDao;
import dao.ProductDao;
import java.util.Scanner;

public class AddProduct {
    Scanner sc = new Scanner(System.in);

    public void addProduct() {
        if (!CategoryDao.checkIfCategoriesExists()) {
            System.out.println("Kindly add one before.");
            return;
        }

        ProductDao.addProduct();
        System.out.println("Do you want to add specifications ?");
        System.out.println("1. Yes");
        System.out.println("2. No");
        int spec = sc.nextInt();
        sc.nextLine();

        if (spec == 1) {

            do {
                ProductDao.addSpecification();
                System.out.println("Press q to quit: ");
                char pressed = sc.next().charAt(0);
                sc.nextLine();
                if (pressed == 'q') {
                    spec = 0;
                }
            } while (spec == 1);
        } else if (spec != 1) {
            System.out.println("No attributes added to product.");
        }

        System.out.println("Product successfully inserted !!!");

    }
}
