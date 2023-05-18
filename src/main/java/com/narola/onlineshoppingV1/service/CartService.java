package com.narola.onlineshoppingV1.service;

import com.narola.onlineshoppingV1.dao.CartDao;
import com.narola.onlineshoppingV1.dao.OrderDao;
import com.narola.onlineshoppingV1.dao.ProductDao;
import com.narola.onlineshoppingV1.dao.UserDao;
import com.narola.onlineshoppingV1.display.Display;
import com.narola.onlineshoppingV1.exception.DAOLayerException;
import com.narola.onlineshoppingV1.manager.UserManager;
import com.narola.onlineshoppingV1.model.Order;
import com.narola.onlineshoppingV1.model.UserAddress;
import com.narola.onlineshoppingV1.model.UserPaymentCredential;
import com.narola.onlineshoppingV1.session.LoggedInUser;
import com.narola.onlineshoppingV1.system.ProgramTerminator;
import com.narola.onlineshoppingV1.view.CartView;

import java.util.List;

import static com.narola.onlineshoppingV1.constant.AppConstant.*;

public class CartService {
    private CartView cartView = new CartView();
    private UserManager userManager = new UserManager();
    private UserService userService = new UserService();
    private PaymentService paymentService = new PaymentService();

    public void getCartItems() {
        try {
            Display.printUserCartItems(CartDao.getCartItems());
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
    }

    public void addItemToCart() {
        try {
            if (!ProductDao.doProductsExists()) {
                cartView.displayErrorMessage("No products are present.");
                return;
            }
            int productId = cartView.getProductIdToAddToCartFromUser();
            if (!ProductDao.doProductExists(productId)) {
                cartView.displayErrorMessage("Please enter valid product id.");
                addItemToCart();
            } else if (CartDao.doItemExists(LoggedInUser.getCurrentUser().getUserId(), productId)) {
                CartDao.updateProductQuantity(LoggedInUser.getCurrentUser().getUserId(), productId, 1);
            } else {
                CartDao.addItemToCart(LoggedInUser.getCurrentUser().getUserId(), productId);
                cartView.displaySuccessMessage("Item successfully added.");
            }
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
    }

    public void updateItemQuantity() {
        try {
            if (!CartDao.isCartEmpty(LoggedInUser.getCurrentUser().getUserId())) {
                cartView.displayErrorMessage("No item is present in your cart.");
                return;
            }

            int productId = cartView.getProductIdFromUserToModifyCartQuantity();
            if (!CartDao.doItemExists(LoggedInUser.getCurrentUser().getUserId(), productId)) {
                cartView.displayErrorMessage("No such item exists in your cart.");
                updateItemQuantity();
            } else {
                updateItem(productId);
                cartView.displaySuccessMessage("Cart updated successfully.");
            }
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
    }

    private void updateItem(int productId) throws DAOLayerException {
        int choice = cartView.getChoiceFromUserToModifyProductQuantityInCart();

        try {
            switch (choice) {
                case INCREASE_QUANTITY_BY_1:
                    CartDao.updateProductQuantity(LoggedInUser.getCurrentUser().getUserId(), productId, 1);
                    break;
                case DECREASE_QUANTITY_BY_1:
                    CartDao.updateProductQuantity(LoggedInUser.getCurrentUser().getUserId(), productId, -1);
                    CartDao.deleteItemIfZeroQuantity(LoggedInUser.getCurrentUser().getUserId(), productId);
                    break;
                case EXIT:
                    ProgramTerminator.exit();
                default:
                    System.out.println("Please enter valid choice.");
                    updateItem(productId);
                    break;
            }
        } catch (DAOLayerException e) {
            throw new DAOLayerException(e);
        }
    }

    public void deleteProductFromCart() {
        try {
            if (!CartDao.isCartEmpty(LoggedInUser.getCurrentUser().getUserId())) {
                cartView.displayErrorMessage("No item is present in your cart.");
                return;
            }

            int productId = cartView.getProductIdFromUserToRemoveProductFromCart();

            if (!CartDao.doItemExists(LoggedInUser.getCurrentUser().getUserId(), productId)) {
                cartView.displayErrorMessage("No such item exists in your cart.");
            } else {
                CartDao.deleteItemFromCart(LoggedInUser.getCurrentUser().getUserId(), productId);
                cartView.displaySuccessMessage("Item successfully deleted.");
            }
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
    }

    public void proceedToCheckout() {
        try {
            Order order = new Order();
            if (!CartDao.isCartEmpty(LoggedInUser.getCurrentUser().getUserId())) {
                cartView.displayErrorMessage("Your cart is empty. Please add items to cart.");
                return;
            }

            Display.printUserCartItems(CartDao.getCartItems());
            List<UserAddress> userAddressList = UserDao.getUserAddresses();
            if (userAddressList.isEmpty()) {
                cartView.displayErrorMessage("No addresses present. Kindly add one.");
                userService.addUserAddress(order);
            } else {
                userManager.handleUserAddressManagement(order);
            }
            List<UserPaymentCredential> userPaymentCredentialList = paymentService.handlePaymentManagement(order);
            OrderDao.addOrder(order, userPaymentCredentialList);
            System.out.println("Your order placed successfully !!!");
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
    }
}
