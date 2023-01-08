package com.example.fooddelivery.dto.product;

import lombok.Data;

import java.util.List;

@Data
public class GetProductListDto {
    private Integer productId;

    private String productName;

    private Integer price;

    private Integer quantity;

    private String restaurant;

    public GetProductListDto(){}

    public GetProductListDto(String productName, Integer price, Integer quantity, String restName) {
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.restaurant = restName;
    }
}
