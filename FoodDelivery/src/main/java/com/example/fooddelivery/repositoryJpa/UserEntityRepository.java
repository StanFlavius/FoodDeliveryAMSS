package com.example.fooddelivery.repositoryJpa;

import com.example.fooddelivery.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEntityRepository extends JpaRepository<UserEntity, Integer> {

}
