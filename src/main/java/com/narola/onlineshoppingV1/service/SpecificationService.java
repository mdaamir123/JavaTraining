package com.narola.onlineshoppingV1.service;

import com.narola.onlineshoppingV1.dao.ProductDao;
import com.narola.onlineshoppingV1.exception.DAOLayerException;
import com.narola.onlineshoppingV1.model.Specification;
import com.narola.onlineshoppingV1.session.LoggedInUser;
import com.narola.onlineshoppingV1.validation.InputValidator;
import com.narola.onlineshoppingV1.view.SpecificationView;

import java.util.Scanner;

public class SpecificationService {
    private SpecificationView specificationView = new SpecificationView();

    public void addSpecification(int productId) {
        Specification specification = new Specification();
        specification.setSpecProductId(productId);
        specification.setSpecAttributeName(specificationView.getAttributeNameFromUser());
        specification.setSpecAttributeValue(specificationView.getAttributeValueFromUser());
        specification.setCreatedBy(LoggedInUser.currentUser.getUserId());
        specification.setUpdatedBy(LoggedInUser.currentUser.getUserId());
        try {
            ProductDao.addNewSpecification(specification);
        } catch (DAOLayerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteSpecification(int productId) {
        Specification specification = new Specification();
        specification.setSpecProductId(productId);
        specification.setUpdatedBy(LoggedInUser.currentUser.getUserId());

        try {
            if (ProductDao.getProductSpecifications(productId).isEmpty()) {
                specificationView.displayErrorMessage("No specification to delete.");
                return;
            }

            int specId = specificationView.getSpecificationIdToDeleteFromUser(productId);

            if (!ProductDao.doSpecificationExists(specId, productId)) {
                specificationView.displayErrorMessage("No specification with id " + specId + " exists.");
                deleteSpecification(productId);
            } else {
                ProductDao.deleteProductSpecification(specId);
                specificationView.displaySuccessMessage("Successfully deleted !!!");
            }
        } catch (DAOLayerException de) {
            de.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateProductSpecifications(int id) {
        try {
            int specId = specificationView.getSpecificationIdFromUserToUpdate(id);

            if (!ProductDao.doSpecificationExists(specId, id)) {
                specificationView.displayErrorMessage("Please enter valid specification id.");
                updateProductSpecifications(id);
            } else {

                String newAttName = specificationView.getAttributeNameToUpdateFromUser();
                if (!InputValidator.isEmpty(newAttName)) {
                    ProductDao.updateProductSpecificationAttributeName(specId, newAttName);
                }

                String newAttValue = specificationView.getAttributeValueToUpdateFromUser();
                if (!InputValidator.isEmpty(newAttValue)) {
                    ProductDao.updateProductSpecificationAttributeValue(specId, newAttValue);
                }

                specificationView.displaySuccessMessage("Successfully updated.");
            }
        } catch (DAOLayerException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
