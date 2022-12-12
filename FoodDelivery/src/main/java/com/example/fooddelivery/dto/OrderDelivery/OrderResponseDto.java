package com.example.fooddelivery.dto.OrderDelivery;

import lombok.Data;
import org.springframework.web.bind.annotation.PutMapping;

import java.sql.Timestamp;
import java.util.List;

@Data
public class OrderResponseDto {

    private Integer orderId;

    private List<String> productList;

    private Integer totalPrice;

    private String address;

    private String deliveryPersonName;

    private Timestamp lastTime;

    private String status;

    private String restaurantName;

    public  OrderResponseDto(){}

    public OrderResponseDto(Integer totalPrice, String location, String deliveryPersonName, Timestamp orderTime, String status, String restaurantName) {
        this.totalPrice = totalPrice;
        this.address = location;
        this.deliveryPersonName = deliveryPersonName;
        this.lastTime = orderTime;
        this.status = status;
        this.restaurantName = restaurantName;
    }
}
