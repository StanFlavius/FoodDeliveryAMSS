package com.example.fooddelivery.repositoryEM;

import com.example.fooddelivery.model.CustomOrder;
import com.example.fooddelivery.model.NormalUser;
import com.example.fooddelivery.model.Restaurant;

import java.util.List;

public interface OrderRepositoryEM {
    CustomOrder findByOrderId(Integer orderId);

    List<CustomOrder> findByStatus(String status);

    List<CustomOrder> findByUser(NormalUser user);

    List<CustomOrder> findByUserAndStatus(NormalUser user, String status);

    List<CustomOrder> findByRestaurant(Restaurant restaurant);
}
