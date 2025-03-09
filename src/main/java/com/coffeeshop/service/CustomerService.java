package com.coffeeshop.service;

import com.coffeeshop.controller.dto.CustomerResponse;
import com.coffeeshop.infra.repository.entity.Customer;

public interface CustomerService {
    Customer getCustomerByNameAndPhone(String name, String phone);
}
