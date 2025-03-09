package com.coffeeshop.controller;


import com.coffeeshop.infra.repository.entity.Shop;
import com.coffeeshop.service.ShopService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class ShopControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ShopService shopService;

    @InjectMocks
    private ShopController shopController;

    private Shop shop;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(shopController).build();

        shop = new Shop();
        shop.setId(1L);
        shop.setName("Coffee Shop 1");
        shop.setLocation("Downtown");
    }

    @Test
    public void testGetShops() throws Exception {
        when(shopService.getShops()).thenReturn(Arrays.asList(shop));

        mockMvc.perform(get("/shops"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(shop.getName()))
                .andExpect(jsonPath("$[0].location").value(shop.getLocation()));
    }

    @Test
    public void testGetShopByName() throws Exception {
        when(shopService.getShopByName(anyString())).thenReturn(shop);

        mockMvc.perform(get("/shops/Coffee Shop 1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(shop.getName()))
                .andExpect(jsonPath("$.location").value(shop.getLocation()));
    }
}