package com.example.fooddelivery.repository;

import com.example.fooddelivery.model.NormalUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NormalUserRepository extends JpaRepository<NormalUser, Integer> {
}
