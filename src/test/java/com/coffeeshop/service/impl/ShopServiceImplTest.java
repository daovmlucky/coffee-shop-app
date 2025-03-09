package com.coffeeshop.service.impl;

import com.coffeeshop.infra.exception.NotFoundException;
import com.coffeeshop.infra.repository.ShopRepository;
import com.coffeeshop.infra.repository.entity.Shop;
import com.coffeeshop.service.impl.ShopServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ShopServiceImplTest {

    @Mock
    private ShopRepository shopRepository;

    @InjectMocks
    private ShopServiceImpl shopService;

    private Shop shop;

    @BeforeEach
    public void setUp() {
        shop = new Shop();
        shop.setId(1L);
        shop.setName("Coffee Shop 1");
        shop.setLocation("Downtown");
    }

    @Test
    public void testGetShops() {
        when(shopRepository.findAll()).thenReturn(Arrays.asList(shop));

        List<Shop> shops = shopService.getShops();

        assertNotNull(shops);
        assertEquals(1, shops.size());
        verify(shopRepository, times(1)).findAll();
    }

    @Test
    public void testGetShopByName() {
        when(shopRepository.findByName(anyString())).thenReturn(Optional.of(shop));

        Shop foundShop = shopService.getShopByName("Coffee Shop 1");

        assertNotNull(foundShop);
        assertEquals(shop.getName(), foundShop.getName());
        verify(shopRepository, times(1)).findByName(anyString());
    }

    @Test
    public void testGetShopByName_NotFound() {
        when(shopRepository.findByName(anyString())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> shopService.getShopByName("Nonexistent Shop"));
        verify(shopRepository, times(1)).findByName(anyString());
    }
}
