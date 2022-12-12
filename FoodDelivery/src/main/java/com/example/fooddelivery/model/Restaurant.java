package com.example.fooddelivery.model;

import lombok.Data;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer restaurantId;

    private String name;

    private String address;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "restaurant_manager_id")
    private RestaurantManager restaurantManager;

    @OneToMany(mappedBy = "restaurant")
    private List<Review> reviewList = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant")
    private List<Favourites> favouritesList = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant")
    private List<Product> productList = new ArrayList<>();
}
