package com.example.fooddelivery.service;

import com.example.fooddelivery.dto.RestaurantManager.AddRestaurantManagerDto;
import com.example.fooddelivery.dto.restaurant.AddRestaurantDto;
import com.example.fooddelivery.dto.restaurant.ViewRestaurantDto;
import com.example.fooddelivery.mapper.RestaurantMapper;
import com.example.fooddelivery.model.Restaurant;
import com.example.fooddelivery.model.RestaurantManager;
import com.example.fooddelivery.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RestaurantManagerService restaurantManagerService;

    @Autowired
    private RestaurantMapper restaurantMapper;

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

    public List<ViewRestaurantDto> getAll() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        return restaurantMapper.restaurantListToViewRestaurantList(restaurants);
    }

    public ViewRestaurantDto getByRestaurantManagerId(Integer id) {
        Optional<Restaurant> restaurant = restaurantRepository.findByRestaurantManager_RestaurantManagerId(id);
        return restaurant.map(value -> restaurantMapper.restaurantToViewRestaurantDto(value)).orElse(null);
    }
}
