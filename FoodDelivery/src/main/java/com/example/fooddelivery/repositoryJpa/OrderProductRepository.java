package com.example.fooddelivery.repositoryJpa;

import com.example.fooddelivery.model.CustomOrder;
import com.example.fooddelivery.model.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Integer> {

}
