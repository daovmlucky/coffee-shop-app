package com.coffeeshop.controller;

import com.coffeeshop.controller.dto.OrderRequest;
import com.coffeeshop.controller.dto.OrderResponse;
import com.coffeeshop.service.OrderService;
import com.coffeeshop.service.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public OrderResponse placeOrder(@RequestBody OrderRequest orderRequest) {
        Long orderId = orderService.placeOrder(orderRequest);
        return OrderResponse.builder()
                .orderId(orderId)
                .status(Status.CONFIRMED.name())
                .build();
    }

    @PostMapping("/{orderId}/complete")
    public OrderResponse completeOrder(@PathVariable Long orderId) {
        orderService.completeOrder(orderId);
        return OrderResponse.builder()
                .orderId(orderId)
                .status(Status.COMPLETED.name())
                .build();
    }

    @DeleteMapping("/{orderId}")
    public OrderResponse cancelOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
        return OrderResponse.builder()
                .orderId(orderId)
                .status(Status.CANCELLED.name())
                .build();
    }

}
