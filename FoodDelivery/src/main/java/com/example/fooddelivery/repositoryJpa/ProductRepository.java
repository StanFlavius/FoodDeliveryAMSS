package com.example.fooddelivery.repositoryJpa;

import com.example.fooddelivery.model.Product;
import com.example.fooddelivery.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}
