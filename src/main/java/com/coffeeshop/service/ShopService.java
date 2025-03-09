package com.coffeeshop.service;

import com.coffeeshop.controller.dto.ShopResponse;
import com.coffeeshop.infra.repository.entity.Shop;

import java.util.List;

public interface ShopService {
    List<Shop> getShops();

    Shop getShopByName(String name);
}
