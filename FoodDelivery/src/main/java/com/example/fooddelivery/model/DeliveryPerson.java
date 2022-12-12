package com.example.fooddelivery.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class DeliveryPerson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer deliveryPersonId;

    private String firstName;

    private String lastName;

    private String availability;

    private Integer salary;

    private Integer scheduleStart;

    private Integer scheduleStop;

    @OneToMany(mappedBy = "deliveryPerson")
    private List<CustomOrder> customOrderList = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;
}
