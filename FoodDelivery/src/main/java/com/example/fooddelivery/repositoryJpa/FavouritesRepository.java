package com.example.fooddelivery.repositoryJpa;

import com.example.fooddelivery.model.Favourites;
import com.example.fooddelivery.model.NormalUser;
import com.example.fooddelivery.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavouritesRepository extends JpaRepository<Favourites, Integer> {

}
