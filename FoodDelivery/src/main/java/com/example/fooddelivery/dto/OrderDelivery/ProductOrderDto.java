package com.example.fooddelivery.dto.OrderDelivery;

import lombok.Data;

import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ProductOrderDto {

    @NotEmpty(message = "Please choose a product.")
    String Product;

    @Min(value = 1, message = "The quantity must be at least 1")
    Integer quantity;
}
