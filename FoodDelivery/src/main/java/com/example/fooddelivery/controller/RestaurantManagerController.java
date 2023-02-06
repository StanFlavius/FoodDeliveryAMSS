package com.example.fooddelivery.controller;

import com.example.fooddelivery.CurrentUser;
import com.example.fooddelivery.dto.RestaurantManager.AddRestaurantManagerDto;
import com.example.fooddelivery.log.Logger;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/restman")
@Validated
public class RestaurantManagerController {

    @Autowired
    RestaurantManagerService restaurantManagerService;

    @PostMapping("")
    public ResponseEntity<RestaurantManager> addNewRestaurantManager(@RequestBody @Valid AddRestaurantManagerDto addRestaurantManagerDto){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        Logger.getInstance().logMsg(CurrentUser.getInstance().getUserId(), this.getClass().getSimpleName(), new Object(){}.getClass().getEnclosingMethod().getName(), dtf.format(now));

        return ResponseEntity.ok(restaurantManagerService.addNewRestaurantManager(addRestaurantManagerDto));
    }
}
