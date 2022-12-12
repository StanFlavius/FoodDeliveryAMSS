package com.example.fooddelivery.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class CustomOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    private String status;

    private String address;

    private Timestamp statusTime;

    private Integer totalCost;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @OneToMany(mappedBy = "customOrder")
    List<OrderProduct> orderProductList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "delivery_person_id")
    private DeliveryPerson deliveryPerson;

    @ManyToOne
    @JoinColumn(name = "normal_user_id")
    private NormalUser user;
}
