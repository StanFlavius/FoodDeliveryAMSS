package com.example.fooddelivery.repositoryEM;

import com.example.fooddelivery.model.CustomOrder;
import com.example.fooddelivery.model.NormalUser;
import com.example.fooddelivery.model.Product;
import com.example.fooddelivery.model.Restaurant;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderRepositoryImpl implements OrderRepositoryEM{

    EntityManager entityManager;

    public OrderRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public CustomOrder findByOrderId(Integer orderId) {
        Query jpqlQuery = entityManager.createQuery("SELECT co FROM CustomOrder co WHERE co.orderId=:id");
        jpqlQuery.setParameter("id", orderId);
        CustomOrder customOrder = new CustomOrder();
        try{
            customOrder = (CustomOrder) jpqlQuery.getSingleResult();
        }
        catch (NoResultException exp){}
        return customOrder;
    }

    @Override
    public List<CustomOrder> findByStatus(String status) {
        Query jpqlQuery = entityManager.createQuery("SELECT co FROM CustomOrder co WHERE co.status=:status");
        jpqlQuery.setParameter("status", status);
        List<CustomOrder> customOrders = new ArrayList<>();
        try{
            customOrders = (List<CustomOrder>) jpqlQuery.getResultList();
        }
        catch (NoResultException exp){}
        return customOrders;
    }

    @Override
    public List<CustomOrder> findByUser(NormalUser user) {
        Query jpqlQuery = entityManager.createQuery("SELECT co FROM CustomOrder co WHERE co.user=:user");
        jpqlQuery.setParameter("user", user);
        List<CustomOrder> customOrders = new ArrayList<>();
        try{
            customOrders = (List<CustomOrder>) jpqlQuery.getResultList();
        }
        catch (NoResultException exp){}
        return customOrders;
    }

    @Override
    public List<CustomOrder> findByUserAndStatus(NormalUser user, String status) {
        Query jpqlQuery = entityManager.createQuery("SELECT co FROM CustomOrder co WHERE co.status=:status AND co.user=:user");
        jpqlQuery.setParameter("status", status);
        jpqlQuery.setParameter("user", user);
        List<CustomOrder> customOrders = new ArrayList<>();
        try{
            customOrders = (List<CustomOrder>) jpqlQuery.getResultList();
        }
        catch (NoResultException exp){}
        return customOrders;
    }

    @Override
    public List<CustomOrder> findByRestaurant(Restaurant restaurant) {
        Query jpqlQuery = entityManager.createQuery("SELECT co FROM CustomOrder co WHERE co.restaurant=:rest");
        jpqlQuery.setParameter("rest", restaurant);
        List<CustomOrder> customOrders = new ArrayList<>();
        try{
            customOrders = (List<CustomOrder>) jpqlQuery.getResultList();
        }
        catch (NoResultException exp){}
        return customOrders;
    }
}
