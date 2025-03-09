package com.coffeeshop.controller;

import com.coffeeshop.controller.dto.ShopResponse;
import com.coffeeshop.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Size;
import java.util.List;

@RestController
@RequestMapping("/shops")
@Validated
public class ShopController {
    @Autowired
    private ShopService shopService;

    @GetMapping
    public List<ShopResponse> getShops() {
        return shopService.getShops().stream().map(ShopResponse::fromEntity).toList();
    }

    @GetMapping("/{name}")
    public ShopResponse getShopByName(@PathVariable @Size(max = 100) String name) {
        return ShopResponse.fromEntity(shopService.getShopByName(name));
    }
}
