package com.coffeeshop.controller.dto;

import com.coffeeshop.infra.repository.entity.Customer;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class CustomerResponse {
    private String name;
    private String mobileNumber;
    private String address;

    public static CustomerResponse fromEntity(Customer customer) {
        return CustomerResponse.builder()
                .name(customer.getName())
                .mobileNumber(customer.getMobileNumber())
                .address(customer.getAddress())
                .build();
    }
}
