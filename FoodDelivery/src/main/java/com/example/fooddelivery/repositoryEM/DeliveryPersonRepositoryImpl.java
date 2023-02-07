package com.example.fooddelivery.repositoryEM;

import com.example.fooddelivery.model.DeliveryPerson;
import com.example.fooddelivery.model.Product;
import com.example.fooddelivery.model.UserEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;

@Repository
public class DeliveryPersonRepositoryImpl implements DeliveryPersonRepositoryEM{
    EntityManager entityManager;

    public DeliveryPersonRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public DeliveryPerson findByDeliveryPersonId(Integer id) {
        Query jpqlQuery = entityManager.createQuery("SELECT d FROM DeliveryPerson d WHERE d.deliveryPersonId=:id");
        jpqlQuery.setParameter("id", id);
        DeliveryPerson deliveryPerson = new DeliveryPerson();
        try{
            deliveryPerson = (DeliveryPerson) jpqlQuery.getSingleResult();
        }
        catch (NoResultException exp){}
        return deliveryPerson;
    }

    @Override
    public DeliveryPerson findByUserEntity_Id(Integer id) {
        Query jpqlQuery2 = entityManager.createQuery("SELECT u FROM UserEntity u WHERE u.id=:id");

        UserEntity userEntity = (UserEntity) jpqlQuery2.getSingleResult();

        Query jpqlQuery = entityManager.createQuery("SELECT d FROM DeliveryPerson d WHERE d.userEntity=:user");
        jpqlQuery.setParameter("user", userEntity);
        DeliveryPerson deliveryPerson = new DeliveryPerson();
        try{
            deliveryPerson = (DeliveryPerson) jpqlQuery.getSingleResult();
        }
        catch (NoResultException exp){}
        return deliveryPerson;
    }
}
