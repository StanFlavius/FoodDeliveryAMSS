package com.example.fooddelivery.repositoryEM;

import com.example.fooddelivery.model.Restaurant;
import com.example.fooddelivery.model.RestaurantManager;
import com.example.fooddelivery.model.UserEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

@Repository
public class RestaurantManagerRepositoryImpl implements RestaurantManagerRepositoryEM{
    EntityManager entityManager;

    public RestaurantManagerRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public RestaurantManager findByUserEntity_Id(Integer id) {
        Query jpqlQuery2 = entityManager.createQuery("SELECT u FROM UserEntity u WHERE u.id=:id");

        UserEntity userEntity = (UserEntity) jpqlQuery2.getSingleResult();

        Query jpqlQuery = entityManager.createQuery("SELECT r FROM RestaurantManager r WHERE r.userEntity=:user");
        jpqlQuery.setParameter("user", userEntity);
        RestaurantManager restaurantManager = new RestaurantManager();
        try{
            restaurantManager = (RestaurantManager) jpqlQuery.getSingleResult();
        }
        catch (NoResultException exp){}
        return restaurantManager;
    }
}
