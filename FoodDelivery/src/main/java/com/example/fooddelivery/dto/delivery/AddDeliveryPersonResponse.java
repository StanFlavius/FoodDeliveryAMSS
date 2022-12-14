package com.example.fooddelivery.dto.delivery;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AddDeliveryPersonResponse {
    private Integer deliveryPersonId;

    private String firstName;

    private String lastName;

    private Integer salary;

    private Integer scheduleStart;

    private Integer scheduleStop;


    public AddDeliveryPersonResponse(Integer deliveryPersonId, String firstName, String lastName, Integer salary, Integer scheduleStart, Integer scheduleStop) {
        this.deliveryPersonId = deliveryPersonId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.scheduleStart = scheduleStart;
        this.scheduleStop = scheduleStop;
    }
}
