package com.coffeeshop.service.impl;

import com.coffeeshop.controller.dto.OrderRequest;
import com.coffeeshop.infra.repository.OrdersRepository;
import com.coffeeshop.infra.repository.QueueRepository;
import com.coffeeshop.infra.repository.ShopRepository;
import com.coffeeshop.infra.repository.entity.Customer;
import com.coffeeshop.infra.repository.entity.Orders;
import com.coffeeshop.infra.repository.entity.Queue;
import com.coffeeshop.infra.repository.entity.Shop;
import com.coffeeshop.service.CustomerService;
import com.coffeeshop.service.Status;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class OrderServiceImplTest {
    @Mock
    private OrdersRepository orderRepository;

    @Mock
    private ShopRepository shopRepository;

    @Mock
    private QueueRepository queueRepository;

    @Mock
    private CustomerService customerService;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private OrderServiceImpl orderService;

    private OrderRequest orderRequest;
    private Orders order;
    private Queue queue;
    private Shop shop;

    @BeforeEach
    public void setUp() {
        orderRequest = OrderRequest.builder()
                .customerName("John Doe")
                .customerPhone("1234567890")
                .shopName("Coffee Shop 1")
                .build();

        order = new Orders();
        order.setId(1L);
        order.setCustomerId(1L);
        order.setShopId(1L);
        order.setStatus(Status.CONFIRMED.name());

        queue = new Queue();
        queue.setShopId(1L);
        queue.setCustomerId(1L);
        queue.setPosition(1);

        shop = new Shop();
        shop.setId(1L);
        shop.setQueues(1);
    }

    @Test
    public void testPlaceOrder() {
        when(customerService.getCustomerByNameAndPhone(anyString(), anyString())).thenReturn(new Customer());
        when(shopRepository.findByName(anyString())).thenReturn(Optional.of(shop));
        when(orderRepository.save(any(Orders.class))).thenReturn(order);
        when(queueRepository.findMaxPositionByShopId(anyLong())).thenReturn(Optional.of(1));

        Long orderId = orderService.placeOrder(orderRequest);

        assertNotNull(orderId);
        assertEquals(order.getId(), orderId);
        verify(shopRepository, times(1)).save(any(Shop.class));
        verify(orderRepository, times(1)).save(any(Orders.class));
        verify(queueRepository, times(1)).save(any(Queue.class));
    }

    @Test
    public void testCompleteOrder() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
        when(queueRepository.findFirstByShopIdAndCustomerIdOrderByPositionAsc(anyLong(), anyLong())).thenReturn(Optional.of(queue));
        when(shopRepository.findById(anyLong())).thenReturn(Optional.of(shop));

        orderService.completeOrder(1L);

        verify(orderRepository, times(1)).save(order);
        verify(queueRepository, times(1)).delete(queue);
        verify(shopRepository, times(1)).save(shop);
    }

    @Test
    public void testCancelOrder() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
        when(queueRepository.findFirstByShopIdAndCustomerIdOrderByPositionAsc(anyLong(), anyLong())).thenReturn(Optional.of(queue));
        when(shopRepository.findById(anyLong())).thenReturn(Optional.of(shop));

        orderService.cancelOrder(1L);

        verify(orderRepository, times(1)).save(order);
        verify(queueRepository, times(1)).delete(queue);
        verify(shopRepository, times(1)).save(shop);
    }
}
