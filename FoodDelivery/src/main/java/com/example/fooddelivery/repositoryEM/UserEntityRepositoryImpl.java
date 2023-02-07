package com.example.fooddelivery.repositoryEM;

import com.example.fooddelivery.model.Product;
import com.example.fooddelivery.model.UserEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

@Repository
public class UserEntityRepositoryImpl implements UserEntityRepositoryEM{

    EntityManager entityManager;

    public UserEntityRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public UserEntity findByEmail(String email) {
        Query jpqlQuery = entityManager.createQuery("SELECT ue FROM UserEntity ue WHERE ue.email=:email");
        jpqlQuery.setParameter("email", email);
        UserEntity userEntity = new UserEntity();
        try{
            userEntity = (UserEntity) jpqlQuery.getSingleResult();
        }
        catch (NoResultException exp){}
        return userEntity;
    }
}
