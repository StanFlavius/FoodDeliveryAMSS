package com.example.fooddelivery.dto.OrderDelivery;

import lombok.Data;

import javax.persistence.criteria.CriteriaBuilder;

@Data
public class OrderAdminDto {

    private Integer orderId;

    private Integer totalCost;

    private String userName;

    private String restaurantName;

    private String deliveryName;

    public OrderAdminDto(){};

    public OrderAdminDto(Integer orderId, Integer totalCost, String userName, String restaurantName, String deliveryName) {
        this.orderId = orderId;
        this.totalCost = totalCost;
        this.userName = userName;
        this.restaurantName = restaurantName;
        this.deliveryName = deliveryName;
    }
}
