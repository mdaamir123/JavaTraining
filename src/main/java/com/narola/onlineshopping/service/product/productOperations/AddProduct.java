package com.narola.onlineshopping.service.product.productOperations;

import com.narola.onlineshopping.dao.CategoryDao;
import com.narola.onlineshopping.dao.ProductDao;
import com.narola.onlineshopping.display.Display;
import com.narola.onlineshopping.exception.DAOLayerException;
import com.narola.onlineshopping.input.TakeInput;
import com.narola.onlineshopping.model.Product;
import com.narola.onlineshopping.model.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AddProduct {
    public static void addProduct() {
        Scanner sc = new Scanner(System.in);
        try {
            if (!CategoryDao.doCategoriesExists()) {
                System.out.println("No categories present. Kindly add one before.");
                return;
            }

            Product product = new Product();
            System.out.println("Enter product title: ");
            product.setProductTitle(TakeInput.getStrInput());
            System.out.println("Enter description: ");
            product.setProductDescription(TakeInput.getStrInput());
            System.out.println("Enter price: ");
            product.setProductPrice(TakeInput.getFloatInput());
            System.out.println("Enter category_id from below: ");
            Display.printCategories(CategoryDao.getAllCategories());
            int category_id = TakeInput.getIntInput();
            product.setProductCategoryId(category_id);

            if (!CategoryDao.doCategoryExists(category_id)) {
                System.out.println("Category id does not exists.");
                return;
            }

            System.out.println("Enter discount: ");
            product.setProductDiscount(TakeInput.getFloatInput());
            System.out.println("Enter brand: ");
            product.setProductBrand(TakeInput.getStrInput());
            System.out.println("Do you want to add specifications ?");
            System.out.println("1. Yes");
            System.out.println("2. No");
            int spec = TakeInput.getIntInput();
            List<Specification> specificationList = new ArrayList<>();
            if (spec == 1) {
                do {
                    Specification specification = new Specification();
                    System.out.println("Add attribute name: ");
                    specification.setSpecAttributeName(TakeInput.getStrInput());
                    System.out.println("Add attribute value: ");
                    specification.setSpecAttributeValue(TakeInput.getStrInput());
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
