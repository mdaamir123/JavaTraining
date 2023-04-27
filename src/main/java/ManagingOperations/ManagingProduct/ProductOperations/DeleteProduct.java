package ManagingOperations.ManagingProduct.ProductOperations;

import ManagingOperations.ManagingProduct.ProductManagement;
import dao.ProductDao;
import display.Display;
import exception.DAOLayerException;

import java.util.Scanner;

public class DeleteProduct {
    public void deleteProduct() {
        Scanner sc = new Scanner(System.in);
        try{
            if(!ProductDao.checkIfProductsExists()) {
                System.out.println("No products exists !!!");
                ProductManagement.handleProductManagement();
            }

            else {
                Display.printProducts(ProductDao.getALlProducts());
                System.out.println("Please enter the id of the product you want to delete: ");

                int productId = sc.nextInt();
                sc.nextLine();

                if(!ProductDao.checkIfProductExists(productId)) {
                    System.out.println("Product not present with id " + productId);
                    deleteProduct();
                }

                else {
                    ProductDao.deleteProduct(productId);
                    System.out.println("Product successfully deleted !!!");
                    ProductManagement.handleProductManagement();
                }
            }
        }
        catch (DAOLayerException de) {
            de.printStackTrace();
            deleteProduct();
        } catch (Exception e) {
            e.printStackTrace();
            deleteProduct();
        }
    }
}
