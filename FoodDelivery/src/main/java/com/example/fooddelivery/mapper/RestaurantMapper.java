package com.example.fooddelivery.mapper;

import com.example.fooddelivery.dto.product.GetProductListDto;
import com.example.fooddelivery.dto.restaurant.ViewRestaurantDto;
import com.example.fooddelivery.model.Product;
import com.example.fooddelivery.model.Restaurant;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RestaurantMapper {

    public ViewRestaurantDto restaurantToViewRestaurantDto(Restaurant restaurant) {
        return new ViewRestaurantDto(
                restaurant.getRestaurantId(),
                restaurant.getName(),
                restaurant.getAddress(),
                restaurant.getRestaurantManager().getFirstName() + " " + restaurant.getRestaurantManager().getLastName()
        );
    }

    public List<ViewRestaurantDto> restaurantListToViewRestaurantList(List<Restaurant> restaurants) {
        List<ViewRestaurantDto> viewRestaurantDtos = new ArrayList<>();
        for (Restaurant restaurant : restaurants) {
            viewRestaurantDtos.add(restaurantToViewRestaurantDto(restaurant));
        }
        return viewRestaurantDtos;
    }
}
