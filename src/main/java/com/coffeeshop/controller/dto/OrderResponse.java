package com.coffeeshop.controller.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class OrderResponse {
    private Long orderId;
    private String status;
}
