package com.narola.onlineshoppingV1.manager;

import com.narola.onlineshoppingV1.service.SpecificationService;
import com.narola.onlineshoppingV1.system.ProgramTerminator;
import com.narola.onlineshoppingV1.view.SpecificationView;

import static com.narola.onlineshoppingV1.constant.AppConstant.*;

public class SpecificationManager {
    private SpecificationView specificationView = new SpecificationView();
    private SpecificationService specificationService = new SpecificationService();

    public void handleSpecificationCrudOptions(int productId) {

        int choice = specificationView.getSpecificationCrudOperationChoiceFromUser();
        switch (choice) {
            case ADD_SPECIFICATION:
                specificationService.addSpecification(productId);
                break;
            case UPDATE_SPECIFICATION:
                specificationService.updateProductSpecifications(productId);
                break;
            case DELETE_SPECIFICATION:
                specificationService.deleteSpecification(productId);
                break;
            case EXIT:
                ProgramTerminator.exit();
            default:
                System.out.println("Invalid choice selected.");
                handleSpecificationCrudOptions(productId);
                break;
        }
    }
}
