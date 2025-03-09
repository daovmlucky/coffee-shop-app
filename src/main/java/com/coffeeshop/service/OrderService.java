package com.coffeeshop.service;

import com.coffeeshop.controller.dto.OrderRequest;

public interface OrderService {
    Long placeOrder(OrderRequest orderRequest);
    void completeOrder(Long orderId);
    void cancelOrder(Long orderId);

}
