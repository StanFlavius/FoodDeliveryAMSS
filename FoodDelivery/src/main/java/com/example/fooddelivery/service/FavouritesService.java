package com.example.fooddelivery.service;

import com.example.fooddelivery.dto.review.OverallReviewRestaurant;
import com.example.fooddelivery.exception.userExp.EmailExist;
import com.example.fooddelivery.model.Favourites;
import com.example.fooddelivery.model.NormalUser;
import com.example.fooddelivery.model.Restaurant;
import com.example.fooddelivery.repository.FavouritesRepository;
import com.example.fooddelivery.repository.NormalUserRepository;
import com.example.fooddelivery.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FavouritesService {

    @Autowired
    private FavouritesRepository favouritesRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private NormalUserRepository normalUserRepository;

    @Autowired
    private ReviewService reviewService;

    public Favourites addFavouriteRestaurant(Integer userId, Integer restaurantId){
        Restaurant restaurant = restaurantRepository.getById(restaurantId);
        NormalUser normalUser = normalUserRepository.getById(userId);
        Favourites favourites = favouritesRepository.findByRestaurantAndUser(restaurant, normalUser);
        if(favourites != null){
            throw new EmailExist("This user already has this restaurant in his favourites list");
        }

        favourites = new Favourites();
        favourites.setRestaurant(restaurantRepository.getById(restaurantId));
        favourites.setUser(normalUserRepository.getById(userId));

        return favouritesRepository.save(favourites);
    }

    public void deleteFavouriteRestaurant(Integer userId, Integer restaurantId){
        Restaurant restaurant = restaurantRepository.getById(restaurantId);
        NormalUser normalUser = normalUserRepository.getById(userId);
        Favourites favourites = favouritesRepository.findByRestaurantAndUser(restaurant, normalUser);

        favouritesRepository.delete(favourites);
    }

    public List<OverallReviewRestaurant> getFavs(Integer userId) {
        List<Favourites> favRestaurants = favouritesRepository.findByUser(normalUserRepository.getById(userId));

        List<Restaurant> restaurants = new ArrayList<>();
        for (Favourites fav:favRestaurants) {
            restaurants.add(fav.getRestaurant());
        }

        List<OverallReviewRestaurant> favList = new ArrayList<>();
        for (Restaurant rest:restaurants) {
            favList.add(reviewService.getOverallReviewOfRestaurant(rest.getRestaurantId()));
        }

        return favList;
    }
}
