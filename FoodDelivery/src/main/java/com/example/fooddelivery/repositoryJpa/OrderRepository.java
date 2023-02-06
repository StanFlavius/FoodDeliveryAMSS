package com.example.fooddelivery.repositoryJpa;

import com.example.fooddelivery.model.CustomOrder;
import com.example.fooddelivery.model.NormalUser;
import com.example.fooddelivery.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<CustomOrder, Integer> {

}
