package com.narola.onlineshoppingV1.view;

import com.narola.onlineshoppingV1.dao.ProductDao;
import com.narola.onlineshoppingV1.display.Display;
import com.narola.onlineshoppingV1.exception.DAOLayerException;
import com.narola.onlineshoppingV1.input.InputHandler;

import static com.narola.onlineshoppingV1.constant.AppConstant.*;

public class SpecificationView {

    public int getChoiceFromUserIfWantToModifySpecification() {
        System.out.println("Do you want to add/update/delete product specifications ?");
        System.out.println("1. Yes");
        System.out.println("Other. No");
        return InputHandler.getIntInput();
    }

    public int getSpecificationCrudOperationChoiceFromUser() {
        System.out.println("Please select one option: ");
        System.out.println(ADD_SPECIFICATION + ". Add specification");
        System.out.println(UPDATE_SPECIFICATION + ". Update specification");
        System.out.println(DELETE_SPECIFICATION + ". Delete specification");
        System.out.println(EXIT + ". Exit");
        return InputHandler.getIntInput();
    }

    public String getAttributeNameFromUser() {
        System.out.println("Enter attribute name: ");
        return InputHandler.getStrInput();
    }

    public String getAttributeValueFromUser() {
        System.out.println("Enter attribute value: ");
        return InputHandler.getStrInput();
    }

    public int getSpecificationIdToDeleteFromUser(int productId) {
        try {
            Display.printProductSpecifications(ProductDao.getProductSpecifications(productId));
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
        System.out.println("Enter id of the specification you want to delete: ");
        return InputHandler.getIntInput();
    }

    public int getSpecificationIdFromUserToUpdate(int id) {
        try {
            Display.printProductSpecifications(ProductDao.getAllProductSpecifications(id));
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
        System.out.println("Please enter id of specification you want to update.");
        return InputHandler.getIntInput();
    }

    public String getAttributeNameToUpdateFromUser() {
        System.out.println("Please enter new attribute name. Leave blank if don't want to modify it.");
        return InputHandler.getStrInput(true);
    }

    public String getAttributeValueToUpdateFromUser() {
        System.out.println("Please enter new attribute value. Leave blank if don't want to modify it.");
        return InputHandler.getStrInput(true);
    }

    public void displaySuccessMessage(String message) {
        System.out.println(message);
    }

    public void displayErrorMessage(String message) {
        System.out.println(message);
    }
}
