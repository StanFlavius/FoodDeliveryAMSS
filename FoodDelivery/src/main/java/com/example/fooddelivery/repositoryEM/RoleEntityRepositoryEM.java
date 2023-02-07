package com.example.fooddelivery.repositoryEM;

import com.example.fooddelivery.model.RoleEntity;

public interface RoleEntityRepositoryEM {
    RoleEntity findByName(String name);
}
