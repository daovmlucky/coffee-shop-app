package com.coffeeshop.controller;


import com.coffeeshop.controller.dto.OrderRequest;
import com.coffeeshop.service.OrderService;

import com.coffeeshop.service.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class OrderControllerTest {

    private MockMvc mockMvc;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    private OrderRequest orderRequest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();

        orderRequest = OrderRequest.builder()
                .customerName("John Doe")
                .customerPhone("1234567890")
                .shopName("Coffee Shop 1")
                .build();
    }

    @Test
    public void testPlaceOrder() throws Exception {
        when(orderService.placeOrder(any(OrderRequest.class))).thenReturn(1L);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"customerName\":\"John Doe\",\"customerPhone\":\"1234567890\",\"shopName\":\"Coffee Shop 1\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value(1L))
                .andExpect(jsonPath("$.status").value(Status.CONFIRMED.name()));
    }

    @Test
    public void testCompleteOrder() throws Exception {
        doNothing().when(orderService).completeOrder(anyLong());

        mockMvc.perform(post("/orders/1/complete"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value(1L))
                .andExpect(jsonPath("$.status").value(Status.COMPLETED.name()));
    }

    @Test
    public void testCancelOrder() throws Exception {
        doNothing().when(orderService).cancelOrder(anyLong());

        mockMvc.perform(delete("/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value(1L))
                .andExpect(jsonPath("$.status").value(Status.CANCELLED.name()));
    }
}
