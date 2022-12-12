package com.example.fooddelivery.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class NormalUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer normalUserId;

    private String firstName;

    private String lastName;

    private String address;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @OneToMany(mappedBy = "user")
    private List<CustomOrder> customOrderList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Review> reviewList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Favourites> favouritesList = new ArrayList<>();
}
