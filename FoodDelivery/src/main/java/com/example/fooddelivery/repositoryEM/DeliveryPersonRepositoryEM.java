package com.example.fooddelivery.repositoryEM;

import com.example.fooddelivery.model.DeliveryPerson;

public interface DeliveryPersonRepositoryEM {
    DeliveryPerson findByDeliveryPersonId(Integer id);

    DeliveryPerson findByUserEntity_Id(Integer userId);
}
