package com.example.fooddelivery.controller;

import com.example.fooddelivery.dto.auth.RegistrationRequest;
import com.example.fooddelivery.dto.restaurant.AddRestaurantDto;
import com.example.fooddelivery.model.Restaurant;
import com.example.fooddelivery.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/restaurant")
@Validated
public class RestaurantController {

    @Autowired
    RestaurantService restaurantService;

    @PostMapping("")
    public ResponseEntity<Restaurant> addNewRestaurant(@RequestBody @Valid AddRestaurantDto addRestaurantDto) {

        return ResponseEntity.ok().body(restaurantService.addNewRestaurant(addRestaurantDto));
    }

    @PutMapping("/editAddress/{id}/{newAddress}")
    public ResponseEntity<Restaurant> editAddressRestaurant(@PathVariable Integer id, @PathVariable String newAddress){
        return ResponseEntity.ok(restaurantService.editAddressRestaurant(id, newAddress));
    }

    @PutMapping("/editName/{id}/{newName}")
    public ResponseEntity<Restaurant> editNameRestaurant(@PathVariable Integer id, @PathVariable String newName){
        return ResponseEntity.ok(restaurantService.editNameRestaurant(id, newName));
    }
}
