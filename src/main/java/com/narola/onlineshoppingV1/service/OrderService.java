package com.narola.onlineshoppingV1.service;

import com.narola.onlineshoppingV1.dao.OrderDao;
import com.narola.onlineshoppingV1.display.Display;
import com.narola.onlineshoppingV1.exception.DAOLayerException;
import com.narola.onlineshoppingV1.model.Order;
import com.narola.onlineshoppingV1.view.OrderView;

import java.util.List;

public class OrderService {
    private OrderView orderView = new OrderView();

    public void handleOrderManagement() {
        try {
            List<Order> orderList = OrderDao.getOrders();
            if (orderList.isEmpty()) {
                orderView.displayErrorMessage("No orders present.");
                return;
            }
            Display.printUserOrders(orderList);
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
    }
}
