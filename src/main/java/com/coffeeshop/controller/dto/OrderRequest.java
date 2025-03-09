package com.coffeeshop.controller.dto;

import com.coffeeshop.infra.validator.VerifyString;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
@Jacksonized
public class OrderRequest {

    @NotNull
    @VerifyString
    @Size(max = 100)
    private String customerName;

    @NotNull
    @VerifyString
    @Size(max = 10)
    private String customerPhone;

    @NotNull
    @VerifyString
    @Size(max = 100)
    private String shopName;

    @EqualsAndHashCode.Exclude
    private OrderDetails orderDetails;

    @Data
    @Builder
    @Jacksonized
    public static class OrderDetails {
        @NotNull
        private List<@NotNull Item> items;
    }

    @Data
    @Builder
    @Jacksonized
    public static class Item {
        @NotNull
        @VerifyString
        @Size(max = 100)
        private String name;

        @NotNull
        private Integer quantity;
    }
}
