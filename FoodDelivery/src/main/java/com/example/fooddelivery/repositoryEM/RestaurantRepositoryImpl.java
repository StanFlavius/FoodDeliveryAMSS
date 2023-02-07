package com.example.fooddelivery.repositoryEM;

import com.example.fooddelivery.model.Product;
import com.example.fooddelivery.model.Restaurant;
import com.example.fooddelivery.model.RestaurantManager;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.Optional;

@Repository
public class RestaurantRepositoryImpl implements RestaurantRepositoryEM{

    EntityManager entityManager;

    public RestaurantRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Restaurant findByRestaurantManager(RestaurantManager restaurantManager) {
        Query jpqlQuery = entityManager.createQuery("SELECT r FROM Restaurant r WHERE r.restaurantManager=:rman");
        jpqlQuery.setParameter("rman", restaurantManager);
        Restaurant restaurant = new Restaurant();
        try{
            restaurant = (Restaurant) jpqlQuery.getSingleResult();
        }
        catch (NoResultException exp){}
        return restaurant;
    }

    @Override
    public Optional<Restaurant> findByRestaurantManager_RestaurantManagerId(Integer id){
        Query jpqlQuery = entityManager.createQuery("SELECT r FROM RestaurantManager r WHERE r.restaurantManagerId=:id");
        jpqlQuery.setParameter("id", id);
        RestaurantManager restaurantManager = (RestaurantManager) jpqlQuery.getSingleResult();

        Query jpqlQuery2 = entityManager.createQuery("SELECT r FROM Restaurant r WHERE r.restaurantManager=:rman");
        jpqlQuery2.setParameter("rman", restaurantManager);

        Restaurant restaurant = new Restaurant();
        try{
            restaurant = (Restaurant) jpqlQuery2.getSingleResult();
        }
        catch (NoResultException exp){}

        return Optional.ofNullable(restaurant);
    }
}
