package com.narola.onlineshopping.service.cart.cartOperations;

import com.narola.onlineshopping.dao.CartDao;
import com.narola.onlineshopping.dao.OrderDao;
import com.narola.onlineshopping.dao.UserDao;
import com.narola.onlineshopping.display.Display;
import com.narola.onlineshopping.exception.DAOLayerException;
import com.narola.onlineshopping.model.Order;
import com.narola.onlineshopping.model.UserAddress;
import com.narola.onlineshopping.model.UserPaymentCredential;
import com.narola.onlineshopping.service.payment.PaymentManager;
import com.narola.onlineshopping.service.user.UserService;
import com.narola.onlineshopping.service.user.address.UserAddressManager;
import com.narola.onlineshopping.session.LoggedInUser;

import java.util.List;

public class CheckOut {
    public static void proceedToCheckout() {
        try {
            Order order = new Order();
            if (!CartDao.isCartEmpty(LoggedInUser.getCurrentUser().getUserId())) {
                System.out.println("Your cart is empty. Please add items to cart.");
                return;
            }

            Display.printUserCartItems(CartDao.getCartItems());
            List<UserAddress> userAddressList = UserDao.getUserAddresses();
            if (userAddressList.isEmpty()) {
                System.out.println("No addresses present. Kindly add one.");
                UserService.addUserAddress(order);
            } else {
                UserAddressManager.handleUserAddressManagement(order);
            }
            List<UserPaymentCredential> userPaymentCredentialList = PaymentManager.handlePaymentManagement(order);
            OrderDao.addOrder(order, userPaymentCredentialList);
            System.out.println("Your order placed successfully !!!");
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
    }
}
