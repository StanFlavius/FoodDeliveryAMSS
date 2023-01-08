package com.example.fooddelivery.dto.product;

import lombok.Data;

@Data
public class EditProductDto {
    private String name;

    private Integer quantity;

    private Integer price;
}
