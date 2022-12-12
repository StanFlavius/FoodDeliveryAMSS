package com.example.fooddelivery.controller;

import com.example.fooddelivery.dto.RestaurantManager.AddRestaurantManagerDto;
import com.example.fooddelivery.model.RestaurantManager;
import com.example.fooddelivery.service.RestaurantManagerService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/restman")
@Validated
public class RestaurantManagerController {

    @Autowired
    RestaurantManagerService restaurantManagerService;

    @PostMapping("")
    public ResponseEntity<RestaurantManager> addNewRestaurantManager(@RequestBody @Valid AddRestaurantManagerDto addRestaurantManagerDto){

        return ResponseEntity.ok(restaurantManagerService.addNewRestaurantManager(addRestaurantManagerDto));
    }
}
