package com.example.fooddelivery.dto.restaurant;

import lombok.Data;

@Data
public class AddRestaurantDto {

    private String restaurantName;

    private String address;

    private String managerEmail;

    private String managerFirstName;

    private String managerLastName;
}
