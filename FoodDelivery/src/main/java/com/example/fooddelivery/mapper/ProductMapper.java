package com.example.fooddelivery.mapper;

import com.example.fooddelivery.dto.product.GetProductListDto;
import com.example.fooddelivery.model.Product;
import com.example.fooddelivery.model.Restaurant;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public static GetProductListDto ProductToGetProductListDto(Product product){
        return new GetProductListDto(
                product.getName(),
                product.getPrice(),
                product.getQuantity(),
                product.getRestaurant().getName()
        );
    }
}
