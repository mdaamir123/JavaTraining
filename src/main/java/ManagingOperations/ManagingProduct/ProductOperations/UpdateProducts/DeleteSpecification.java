package ManagingOperations.ManagingProduct.ProductOperations.UpdateProducts;

import dao.ProductDao;
import display.Display;
import exception.DAOLayerException;
import model.Specification;
import session.LoggedInUser;

import java.util.Scanner;

public class DeleteSpecification {
    public static void deleteSpecification(int productId) {
        Scanner sc = new Scanner(System.in);
        Specification specification = new Specification();
        specification.setSpecProductId(productId);
        specification.setUpdatedBy(LoggedInUser.currentUser.getUserId());

        try {
            if (ProductDao.getProductSpecifications(productId).isEmpty()) {
                System.out.println("No specification to delete.");
                return;
            }

            Display.printProductSpecifications(ProductDao.getProductSpecifications(productId));
            System.out.println("Enter id of the specification you want to delete: ");
            int specId = sc.nextInt();
            sc.nextLine();

            if(!ProductDao.checkIfSpecificationExists(specId, productId)) {
                System.out.println("No specification with id " + specId + " exists.");
                deleteSpecification(productId);
            }

            else {
                ProductDao.deleteProductSpecification(specId);
                System.out.println("Successfully deleted !!!");
            }
        } catch (DAOLayerException de) {
            de.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
