package com.example.fooddelivery.repositoryEM;

import com.example.fooddelivery.model.CustomOrder;
import com.example.fooddelivery.model.OrderProduct;
import com.example.fooddelivery.model.Product;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.Order;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderProductRepositoryImpl implements OrderProductRepositoryEM{
    EntityManager entityManager;

    public OrderProductRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Override
    public List<OrderProduct> getByCustomOrder(CustomOrder customOrder) {
        Query jpqlQuery = entityManager.createQuery("SELECT op FROM OrderProduct op WHERE op.customOrder=:custom");
        jpqlQuery.setParameter("custom", customOrder);
        List<OrderProduct> orderProducts = new ArrayList<>();
        try{
            orderProducts = (List<OrderProduct>) jpqlQuery.getResultList();
        }
        catch (NoResultException exp){}
        return orderProducts;
    }
}
