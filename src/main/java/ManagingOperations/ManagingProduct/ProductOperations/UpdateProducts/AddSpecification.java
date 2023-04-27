package ManagingOperations.ManagingProduct.ProductOperations.UpdateProducts;

import ManagingOperations.ManagingProduct.ProductOperations.UpdateProduct;
import dao.ProductDao;
import exception.DAOLayerException;
import model.Specification;
import session.LoggedInUser;

import java.util.Scanner;

public class AddSpecification {
    public static void addSpecification(int productId) {
        Scanner sc = new Scanner(System.in);
        Specification specification = new Specification();
        specification.setSpecProductId(productId);

        System.out.println("Enter attribute name: ");
        specification.setSpecAttributeName(sc.nextLine());

        System.out.println("Enter attribute value: ");
        specification.setSpecAttributeValue(sc.nextLine());

        specification.setCreatedBy(LoggedInUser.currentUser.getUserId());
        specification.setUpdatedBy(LoggedInUser.currentUser.getUserId());

        try {
            ProductDao.addNewSpecification(specification);
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }


    }
}
