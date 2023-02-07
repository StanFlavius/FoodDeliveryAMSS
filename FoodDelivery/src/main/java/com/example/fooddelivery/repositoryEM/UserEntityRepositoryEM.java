package com.example.fooddelivery.repositoryEM;

import com.example.fooddelivery.model.UserEntity;

public interface UserEntityRepositoryEM {
    UserEntity findByEmail(String email);
}
