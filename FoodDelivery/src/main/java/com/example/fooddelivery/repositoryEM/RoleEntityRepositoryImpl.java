package com.example.fooddelivery.repositoryEM;

import com.example.fooddelivery.model.Product;
import com.example.fooddelivery.model.RoleEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

@Repository
public class RoleEntityRepositoryImpl implements RoleEntityRepositoryEM{
    EntityManager entityManager;

    public RoleEntityRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Override
    public RoleEntity findByName(String name) {
        Query jpqlQuery = entityManager.createQuery("SELECT re FROM RoleEntity re WHERE re.name=:name");
        jpqlQuery.setParameter("name", name);
        RoleEntity roleEntity = new RoleEntity();
        try{
            roleEntity = (RoleEntity) jpqlQuery.getSingleResult();
        }
        catch (NoResultException exp){}
        return roleEntity;
    }
}
