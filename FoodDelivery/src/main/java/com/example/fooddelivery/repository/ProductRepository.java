package com.example.fooddelivery.repository;

import com.example.fooddelivery.model.Product;
import com.example.fooddelivery.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findByName(String productName);

    Product findByNameAndRestaurant(String productName, Restaurant restaurant);

    Product findByProductIdAndRestaurant(Integer productId, Restaurant restaurant);

    List<Product> findByRestaurant(Restaurant restaurant);
}
