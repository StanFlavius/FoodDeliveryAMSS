package com.example.fooddelivery.controller;

import com.example.fooddelivery.dto.review.OverallReviewRestaurant;
import com.example.fooddelivery.model.Favourites;
import com.example.fooddelivery.service.FavouritesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favourites")
public class FavouritesController {

    @Autowired
    private FavouritesService favouritesService;

    @PostMapping("/{userId}/{restaurantId}")
    public ResponseEntity<String> addFavourite(@PathVariable Integer userId, @PathVariable Integer restaurantId){
        favouritesService.addFavouriteRestaurant(userId, restaurantId);

        return ResponseEntity.ok("Success");
    }

    @DeleteMapping("/{userId}/{restaurantId}")
    public ResponseEntity<String> deleteFavourite(@PathVariable Integer userId, @PathVariable Integer restaurantId){
        favouritesService.deleteFavouriteRestaurant(userId, restaurantId);
        return ResponseEntity.ok("Success");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<OverallReviewRestaurant>> getFavourites(@PathVariable Integer userId){
        return ResponseEntity.ok(favouritesService.getFavs(userId));
    }
}
