package ManagingOperations.ManagingProduct.ProductOperations;

import dao.CategoryDao;
import dao.ProductDao;
import model.Specification;

import java.util.Scanner;

public class AddProduct {
    Scanner sc = new Scanner(System.in);

    public void addProduct() {
        if (!CategoryDao.checkIfCategoriesExists()) {
            System.out.println("Kindly add one before.");
            return;
        }
        System.out.println("Enter product title: ");
        String product_title = sc.nextLine();
        System.out.println("Enter description: ");
        String description = sc.nextLine();
        System.out.println("Enter price: ");
        float price = sc.nextFloat();
        sc.nextLine();
        System.out.println("Enter category_id from below: ");
        CategoryDao.getAllCategories();
        int category = sc.nextInt();
        sc.nextLine();
        if(!CategoryDao.checkIfCategoryExists(category)) {
            System.out.println("Category id does not exist.");
            return;
        }
        System.out.println("Enter discount: ");
        float discount = sc.nextFloat();
        sc.nextLine();
        System.out.println("Enter brand: ");
        String brand = sc.nextLine();
        ProductDao.addProduct(product_title, description, price, category, discount, brand);
        System.out.println("Do you want to add specifications ?");
        System.out.println("1. Yes");
        System.out.println("2. No");
        int spec = sc.nextInt();
        sc.nextLine();

        if (spec == 1) {

            do {
                Specification specification = new Specification();
                System.out.println("Add attribute name: ");
                specification.setSpecAttributeName(sc.nextLine());
                System.out.println("Add attribute value: ");
                specification.setSpecAttributeValue(sc.nextLine());
                ProductDao.addSpecification(specification);
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
