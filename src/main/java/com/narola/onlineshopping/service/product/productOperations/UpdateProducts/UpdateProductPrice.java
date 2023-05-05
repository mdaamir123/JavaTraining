package com.narola.onlineshopping.service.product.productOperations.UpdateProducts;

import com.narola.onlineshopping.exception.DAOLayerException;
import com.narola.onlineshopping.dao.ProductDao;

import java.util.Scanner;

public class UpdateProductPrice {
    public void updateProductPrice(int id) {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter new product price. Leave blank if don't want to modify.");
            float newPrice = sc.nextFloat();
            sc.nextLine();
            if (String.valueOf(newPrice).trim().length() == 0) {
                return;
            }
            ProductDao.updateProductPrice(id, newPrice);
            System.out.println("Successfully updated.");
        } catch (DAOLayerException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
