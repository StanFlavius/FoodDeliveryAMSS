package com.example.fooddelivery.repository;

import com.example.fooddelivery.model.CustomOrder;
import com.example.fooddelivery.model.NormalUser;
import com.example.fooddelivery.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<CustomOrder, Integer> {

    CustomOrder findByOrderId(Integer orderId);

    List<CustomOrder> findByStatus(String status);

    List<CustomOrder> findByUser(NormalUser user);

    List<CustomOrder> findByUserAndStatus(NormalUser user, String status);

    List<CustomOrder> findByRestaurant(Restaurant restaurant);
}
