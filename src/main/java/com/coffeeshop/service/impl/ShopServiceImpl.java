package com.coffeeshop.service.impl;

import com.coffeeshop.controller.dto.ShopResponse;
import com.coffeeshop.infra.exception.ErrorCode;
import com.coffeeshop.infra.exception.NotFoundException;
import com.coffeeshop.infra.repository.ShopRepository;
import com.coffeeshop.infra.repository.entity.Shop;
import com.coffeeshop.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopRepository shopRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Shop> getShops() {
        return shopRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Shop getShopByName(String name) {
        return shopRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException(ErrorCode.SHOP_NOT_FOUND));
    }
}
