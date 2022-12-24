package com.example.fooddelivery.dto.restaurant;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ViewRestaurantDto {

    private Integer id;

    private String name;

    private String address;

    private String managerName;
}
