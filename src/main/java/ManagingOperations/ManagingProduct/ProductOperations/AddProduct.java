package ManagingOperations.ManagingProduct.ProductOperations;

import dao.CategoryDao;
import exception.DAOLayerException;
import dao.ProductDao;
import display.Display;
import model.Product;
import model.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AddProduct {
    Scanner sc = new Scanner(System.in);

    public void addProduct() {
        try {
            if (!CategoryDao.checkIfCategoriesExists()) {
                System.out.println("No categories present. Kindly add one before.");
                return;
            }

            Product product = new Product();
            System.out.println("Enter product title: ");
            product.setProductTitle(sc.nextLine());
            System.out.println("Enter description: ");
            product.setProductDescription(sc.nextLine());
            System.out.println("Enter price: ");
            product.setProductPrice(sc.nextFloat());
            sc.nextLine();
            System.out.println("Enter category_id from below: ");

            Display.printCategories(CategoryDao.getAllCategories());

            int category_id = sc.nextInt();
            sc.nextLine();
            product.setProductCategoryId(category_id);


            if (!CategoryDao.checkIfCategoryExists(category_id)) {
                System.out.println("Category id does not exists.");
                return;
            }

            System.out.println("Enter discount: ");
            product.setProductDiscount(sc.nextFloat());
            sc.nextLine();
            System.out.println("Enter brand: ");
            product.setProductBrand(sc.nextLine());
            System.out.println("Do you want to add specifications ?");
            System.out.println("1. Yes");
            System.out.println("2. No");
            int spec = sc.nextInt();

            sc.nextLine();
            List<Specification> specificationList = new ArrayList<>();
            if (spec == 1) {
                do {
                    Specification specification = new Specification();
                    System.out.println("Add attribute name: ");
                    specification.setSpecAttributeName(sc.nextLine());
                    System.out.println("Add attribute value: ");
                    specification.setSpecAttributeValue(sc.nextLine());
                    specificationList.add(specification);
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

            product.setSpecificationList(specificationList);
            ProductDao.addProduct(product);
            System.out.println("Product successfully inserted !!!");
        } catch (DAOLayerException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
