package com.example.fooddelivery.controller;

import com.example.fooddelivery.CurrentUser;
import com.example.fooddelivery.dto.review.OverallReviewRestaurant;
import com.example.fooddelivery.log.Logger;
import com.example.fooddelivery.model.Favourites;
import com.example.fooddelivery.service.FavouritesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/favourites")
public class FavouritesController {

    @Autowired
    private FavouritesService favouritesService;

    @PostMapping("/{userId}/{restaurantId}")
    public ResponseEntity<String> addFavourite(@PathVariable Integer userId, @PathVariable Integer restaurantId){
        favouritesService.addFavouriteRestaurant(userId, restaurantId);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        Logger.getInstance().logMsg(CurrentUser.getInstance().getUserId(), this.getClass().getSimpleName(), new Object(){}.getClass().getEnclosingMethod().getName(), dtf.format(now));

        return ResponseEntity.ok("Success");
    }

    @DeleteMapping("/{userId}/{restaurantId}")
    public ResponseEntity<String> deleteFavourite(@PathVariable Integer userId, @PathVariable Integer restaurantId){
        favouritesService.deleteFavouriteRestaurant(userId, restaurantId);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        Logger.getInstance().logMsg(CurrentUser.getInstance().getUserId(), this.getClass().getSimpleName(), new Object(){}.getClass().getEnclosingMethod().getName(), dtf.format(now));

        return ResponseEntity.ok("Success");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<OverallReviewRestaurant>> getFavourites(@PathVariable Integer userId){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        Logger.getInstance().logMsg(CurrentUser.getInstance().getUserId(), this.getClass().getSimpleName(), new Object(){}.getClass().getEnclosingMethod().getName(), dtf.format(now));

        return ResponseEntity.ok(favouritesService.getFavs(userId));
    }
}
