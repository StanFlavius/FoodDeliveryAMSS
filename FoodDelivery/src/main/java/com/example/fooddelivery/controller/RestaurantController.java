package com.example.fooddelivery.controller;

import com.example.fooddelivery.dto.auth.RegistrationRequest;
import com.example.fooddelivery.dto.restaurant.AddRestaurantDto;
import com.example.fooddelivery.dto.restaurant.ViewRestaurantDto;
import com.example.fooddelivery.model.Restaurant;
import com.example.fooddelivery.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
public class RestaurantController {

    @Autowired
    RestaurantService restaurantService;

    @PostMapping("/restaurant")
    public ResponseEntity<Restaurant> addNewRestaurant(@RequestBody @Valid AddRestaurantDto addRestaurantDto) {

        return ResponseEntity.ok().body(restaurantService.addNewRestaurant(addRestaurantDto));
    }

    @PutMapping("/restaurant/editAddress/{id}/{newAddress}")
    public ResponseEntity<Restaurant> editAddressRestaurant(@PathVariable Integer id, @PathVariable String newAddress){
        return ResponseEntity.ok(restaurantService.editAddressRestaurant(id, newAddress));
    }

    @PutMapping("/restaurant/editName/{id}/{newName}")
    public ResponseEntity<Restaurant> editNameRestaurant(@PathVariable Integer id, @PathVariable String newName){
        return ResponseEntity.ok(restaurantService.editNameRestaurant(id, newName));
    }

    @GetMapping("/restaurants")
    public ResponseEntity<List<ViewRestaurantDto>> getAll() {
        List<ViewRestaurantDto> viewRestaurantDtos = restaurantService.getAll();
        return ResponseEntity.ok(viewRestaurantDtos);
    }
}
