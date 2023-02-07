package com.example.fooddelivery.repositoryEM;

import com.example.fooddelivery.model.Product;
import com.example.fooddelivery.model.Restaurant;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepositoryImpl implements ProductRepositoryEM {

    EntityManager entityManager;

    public ProductRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Product findByName(String productName) {
        Query jpqlQuery = entityManager.createQuery("SELECT p FROM Product p WHERE p.name=:name");
        jpqlQuery.setParameter("name", productName);
        Product product = new Product();
        try{
            product = (Product) jpqlQuery.getSingleResult();
        }
        catch (NoResultException exp){}
        return product;
    }

    @Override
    public Product findByNameAndRestaurant(String productName, Restaurant restaurant) {
        Query jpqlQuery = entityManager.createQuery("SELECT p FROM Product p WHERE p.name=:name AND p.restaurant=:restaurant");
        jpqlQuery.setParameter("name", productName);
        jpqlQuery.setParameter("restaurant", restaurant);
        Product product = new Product();
        try{
            product = (Product) jpqlQuery.getSingleResult();
        }
        catch (NoResultException exp){}
        return product;
    }

    @Override
    public Product findByProductIdAndRestaurant(Integer productId, Restaurant restaurant) {
        Query jpqlQuery = entityManager.createQuery("SELECT p FROM Product p WHERE p.productId=:productId AND p.restaurant=:restaurant");
        jpqlQuery.setParameter("productId", productId);
        jpqlQuery.setParameter("restaurant", restaurant);
        Product product = new Product();
        try{
            product = (Product) jpqlQuery.getSingleResult();
        }
        catch (NoResultException exp){}
        return product;
    }

    @Override
    public List<Product> findByRestaurant(Restaurant restaurant) {
        Query jpqlQuery = entityManager.createQuery("SELECT p FROM Product p WHERE p.restaurant=:restaurant");
        jpqlQuery.setParameter("restaurant", restaurant);
        List<Product> products = new ArrayList<>();
        try{
            products = (List<Product>) jpqlQuery.getResultList();
        }
        catch (NoResultException exp){}
        return products;
    }
}
