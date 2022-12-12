package com.example.fooddelivery.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reviewId;

    private String text;

    private Integer rating;

    @ManyToOne
    @JoinColumn(name = "normal_user_id")
    private NormalUser user;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
}
