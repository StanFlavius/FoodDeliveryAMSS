package com.example.fooddelivery.dto.product;

import lombok.Data;

import java.util.Optional;

@Data
public class NewProductDto {
    private String name;

    private Integer price;

    private Integer quantity;
}
