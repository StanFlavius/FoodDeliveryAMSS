package com.example.fooddelivery.repositoryJpa;

import com.example.fooddelivery.model.DeliveryPerson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryPersonRepository extends JpaRepository<DeliveryPerson, Integer> {

}
