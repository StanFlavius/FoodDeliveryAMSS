package com.example.fooddelivery.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class RestaurantManager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer restaurantManagerId;

    private String firstName;

    private String lastName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;
}
