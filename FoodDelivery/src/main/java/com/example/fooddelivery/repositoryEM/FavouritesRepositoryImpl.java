package com.example.fooddelivery.repositoryEM;

import com.example.fooddelivery.model.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FavouritesRepositoryImpl implements FavouritesRepositoryEM{
    EntityManager entityManager;

    public FavouritesRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public Favourites findByRestaurantAndUser(Restaurant restaurant, NormalUser user) {
        Query jpqlQuery = entityManager.createQuery("SELECT f FROM Favourites f WHERE f.restaurant=:rest AND f.user=:user");
        jpqlQuery.setParameter("rest", restaurant);
        jpqlQuery.setParameter("user", user);
        Favourites favourites = new Favourites();
        try{
            favourites = (Favourites) jpqlQuery.getSingleResult();
        }
        catch (NoResultException exp){}
        return favourites;
    }

    @Override
    public List<Favourites> findByUser(NormalUser normalUser) {
        Query jpqlQuery = entityManager.createQuery("SELECT f FROM Favourites f WHERE f.user=:user");
        jpqlQuery.setParameter("user", normalUser);
        List<Favourites> favourites = new ArrayList<>();
        try{
            favourites = (List<Favourites>) jpqlQuery.getResultList();
        }
        catch (NoResultException exp){}
        return favourites;
    }
}
