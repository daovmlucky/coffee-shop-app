package com.coffeeshop.service.impl;

import com.coffeeshop.controller.dto.CustomerResponse;
import com.coffeeshop.controller.dto.ShopResponse;
import com.coffeeshop.infra.exception.ErrorCode;
import com.coffeeshop.infra.exception.NotFoundException;
import com.coffeeshop.infra.repository.CustomerRepository;
import com.coffeeshop.infra.repository.entity.Customer;
import com.coffeeshop.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    @Transactional(readOnly = true)
    public Customer getCustomerByNameAndPhone(String name, String phone) {
        return customerRepository.findByMobileNumberAndName(phone, name)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CUSTOMER_NOT_FOUND));
    }
}
