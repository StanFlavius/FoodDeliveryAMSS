package com.example.fooddelivery.repositoryEM;

import com.example.fooddelivery.model.CustomOrder;
import com.example.fooddelivery.model.OrderProduct;

import java.util.List;

public interface OrderProductRepositoryEM {
    List<OrderProduct> getByCustomOrder(CustomOrder customOrder);
}
