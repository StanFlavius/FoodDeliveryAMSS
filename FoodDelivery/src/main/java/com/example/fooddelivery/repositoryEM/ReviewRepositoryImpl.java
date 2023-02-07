package com.example.fooddelivery.repositoryEM;

import com.example.fooddelivery.model.Product;
import com.example.fooddelivery.model.Restaurant;
import com.example.fooddelivery.model.Review;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReviewRepositoryImpl implements ReviewRepositoryEM{
    EntityManager entityManager;

    public ReviewRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Override
    public List<Review> findByRestaurant(Restaurant restaurant) {
        Query jpqlQuery = entityManager.createQuery("SELECT r FROM Review r WHERE r.restaurant=:rest");
        jpqlQuery.setParameter("rest", restaurant);
        List<Review> reviewList = new ArrayList<>();
        try{
            reviewList = (List<Review>) jpqlQuery.getSingleResult();
        }
        catch (NoResultException exp){}
        return reviewList;
    }
}
