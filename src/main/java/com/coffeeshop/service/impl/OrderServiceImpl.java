package com.coffeeshop.service.impl;

import com.coffeeshop.controller.dto.OrderRequest;
import com.coffeeshop.infra.exception.ErrorCode;
import com.coffeeshop.infra.exception.NotFoundException;
import com.coffeeshop.infra.repository.OrdersRepository;
import com.coffeeshop.infra.repository.QueueRepository;
import com.coffeeshop.infra.repository.ShopRepository;
import com.coffeeshop.infra.repository.entity.Orders;
import com.coffeeshop.infra.repository.entity.Queue;
import com.coffeeshop.service.CustomerService;
import com.coffeeshop.service.OrderService;
import com.coffeeshop.service.Status;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrdersRepository orderRepository;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private QueueRepository queueRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    @Transactional
    public Long placeOrder(OrderRequest orderRequest) {
        var customer = customerService.getCustomerByNameAndPhone(orderRequest.getCustomerName(), orderRequest.getCustomerPhone());
        var shop = shopRepository.findByName(orderRequest.getShopName())
                .orElseThrow(() -> new NotFoundException(ErrorCode.SHOP_NOT_FOUND));
        var queuesOfShop = shop.getQueues() + 1;
        shop.setQueues(queuesOfShop);
        shopRepository.save(shop);

        var orderDetailsJson = convertOrderDetailsToJson(orderRequest.getOrderDetails());
        var order = buildOrder(customer.getId(), shop.getId(), orderDetailsJson);
        order = orderRepository.save(order);

        int nextPosition = getNextQueuePosition(shop.getId());
        var queue = buildQueue(shop.getId(), customer.getId(), nextPosition);
        queueRepository.save(queue);

        return order.getId();
    }

    @Override
    @Transactional
    public void completeOrder(Long orderId) {
        var order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.ORDER_NOT_FOUND));
        order.setStatus(Status.COMPLETED.name());
        orderRepository.save(order);

        var queue = queueRepository.findFirstByShopIdAndCustomerIdOrderByPositionAsc(order.getShopId(), order.getCustomerId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.QUEUE_NOT_FOUND));
        queueRepository.delete(queue);

        var shop = shopRepository.findById(order.getShopId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.SHOP_NOT_FOUND));
        shop.setQueues(shop.getQueues() - 1);
        shopRepository.save(shop);
    }

    @Override
    @Transactional
    public void cancelOrder(Long orderId) {
        var order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.ORDER_NOT_FOUND));
        order.setStatus(Status.CANCELLED.name());
        orderRepository.save(order);

        var queue = queueRepository.findFirstByShopIdAndCustomerIdOrderByPositionAsc(order.getShopId(), order.getCustomerId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.QUEUE_NOT_FOUND));
        queueRepository.delete(queue);

        var shop = shopRepository.findById(order.getShopId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.SHOP_NOT_FOUND));
        shop.setQueues(shop.getQueues() - 1);
        shopRepository.save(shop);
    }

    private Queue buildQueue(Long shopId, Long customerId, int nextPosition) {
        return Queue.builder()
                .shopId(shopId)
                .customerId(customerId)
                .position(nextPosition)
                .build();
    }

    private Orders buildOrder(Long customerId, Long shopId, String orderDetails) {
        return Orders.builder()
                .customerId(customerId)
                .shopId(shopId)
                .orderDetails(orderDetails)
                .status(Status.CONFIRMED.name())
                .build();
    }

    private String convertOrderDetailsToJson(OrderRequest.OrderDetails orderDetails) {
        try {
            return objectMapper.writeValueAsString(orderDetails);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert order details to JSON", e);
        }
    }

    private int getNextQueuePosition(Long shopId) {
        return queueRepository.findMaxPositionByShopId(shopId).orElse(0) + 1;
    }
}
