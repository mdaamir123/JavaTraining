package com.narola.onlineshopping.service.order;

import com.narola.onlineshopping.dao.OrderDao;
import com.narola.onlineshopping.display.Display;
import com.narola.onlineshopping.exception.DAOLayerException;
import com.narola.onlineshopping.model.Order;

import java.util.List;

public class OrderManager {
    public static void handleOrderManagement() {
        try {
            List<Order> orderList = OrderDao.getOrders();
            if (orderList.isEmpty()) {
                System.out.println("No orders present.");
                return;
            }
            Display.printUserOrders(orderList);
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
    }
}
