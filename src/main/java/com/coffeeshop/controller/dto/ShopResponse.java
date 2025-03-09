package com.coffeeshop.controller.dto;

import com.coffeeshop.infra.repository.entity.Shop;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalTime;

@Data
@Builder
@Jacksonized
public class ShopResponse {
    private Long id;
    private String name;
    private String location;
    private String contactDetails;
    private String menu;
    private Integer queues;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime openingTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime closingTime;

    public static ShopResponse fromEntity(Shop shop) {
        return ShopResponse.builder()
                .id(shop.getId())
                .name(shop.getName())
                .location(shop.getLocation())
                .contactDetails(shop.getContactDetails())
                .menu(shop.getMenu())
                .queues(shop.getQueues())
                .openingTime(shop.getOpeningTime())
                .closingTime(shop.getClosingTime())
                .build();
    }
}
