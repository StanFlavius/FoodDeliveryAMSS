package com.example.fooddelivery.repositoryEM;

import com.example.fooddelivery.model.Product;
import com.example.fooddelivery.model.Restaurant;

import java.util.List;

public interface ProductRepositoryEM {
    Product findByName(String productName);

    Product findByNameAndRestaurant(String productName, Restaurant restaurant);

    Product findByProductIdAndRestaurant(Integer productId, Restaurant restaurant);

    List<Product> findByRestaurant(Restaurant restaurant);
}
