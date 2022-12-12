package com.example.fooddelivery.service;

import com.example.fooddelivery.dto.RestaurantManager.AddRestaurantManagerDto;
import com.example.fooddelivery.dto.restaurant.AddRestaurantDto;
import com.example.fooddelivery.model.Restaurant;
import com.example.fooddelivery.model.RestaurantManager;
import com.example.fooddelivery.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RestaurantManagerService restaurantManagerService;

    public Restaurant addNewRestaurant(AddRestaurantDto restaurantDto){
        Restaurant restaurant = new Restaurant();
        restaurant.setAddress(restaurantDto.getAddress());
        restaurant.setName(restaurantDto.getRestaurantName());

        AddRestaurantManagerDto request = new AddRestaurantManagerDto();
        request.setEmail(restaurantDto.getManagerEmail());
        request.setFirstName(restaurantDto.getManagerFirstName());
        request.setLastName(restaurantDto.getManagerLastName());

        RestaurantManager restaurantManager = restaurantManagerService.addNewRestaurantManager(request);

        restaurant.setRestaurantManager(restaurantManager);

        return restaurantRepository.save(restaurant);
    }

    public Restaurant editAddressRestaurant(Integer id, String newAddress){
        Restaurant restaurant = restaurantRepository.getById(id);
        restaurant.setAddress(newAddress);
        return restaurantRepository.save(restaurant);
    }

    public Restaurant editNameRestaurant(Integer id, String newName){
        Restaurant restaurant = restaurantRepository.getById(id);
        restaurant.setName(newName);
        return restaurantRepository.save(restaurant);
    }
}
