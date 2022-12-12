package com.example.fooddelivery.repository;

import com.example.fooddelivery.model.Restaurant;
import com.example.fooddelivery.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    List<Review> findByRestaurant(Restaurant restaurant);
}
