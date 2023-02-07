package com.example.fooddelivery.repositoryEM;

import com.example.fooddelivery.model.Favourites;
import com.example.fooddelivery.model.NormalUser;
import com.example.fooddelivery.model.Restaurant;

import java.util.List;

public interface FavouritesRepositoryEM {
    Favourites findByRestaurantAndUser(Restaurant restaurant, NormalUser user);

    List<Favourites> findByUser(NormalUser normalUser);
}
