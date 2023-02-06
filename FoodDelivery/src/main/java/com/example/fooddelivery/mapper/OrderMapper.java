package com.example.fooddelivery.mapper;

import com.example.fooddelivery.dto.OrderDelivery.OrderAdminDto;
import com.example.fooddelivery.dto.OrderDelivery.OrderResponseDto;
import com.example.fooddelivery.model.CustomOrder;
import com.example.fooddelivery.model.OrderProduct;
import com.example.fooddelivery.repositoryEM.OrderProductRepositoryEM;
import com.example.fooddelivery.repositoryJpa.OrderProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderMapper {

    @Autowired
    OrderProductRepository orderProductRepository;

    @Autowired
    OrderProductRepositoryEM orderProductRepositoryEM;

    public OrderResponseDto OrderToOrderResponseDto(CustomOrder customOrder){

        OrderResponseDto orderResponseDto = new OrderResponseDto();
        orderResponseDto.setAddress(customOrder.getAddress());
        orderResponseDto.setTotalPrice(customOrder.getTotalCost());
        orderResponseDto.setStatus(customOrder.getStatus());
        orderResponseDto.setLastTime(customOrder.getStatusTime());
        orderResponseDto.setOrderId(customOrder.getOrderId());
        orderResponseDto.setRestaurantName(customOrder.getRestaurant().getName());

        if(customOrder.getDeliveryPerson() == null)
            orderResponseDto.setDeliveryPersonName(" ");
        else{
            orderResponseDto.setDeliveryPersonName(
                    customOrder.getDeliveryPerson().getFirstName() + " " + customOrder.getDeliveryPerson().getLastName()
            );
        }

        List<String> productList = new ArrayList<>();
        List<OrderProduct> orderProductList = orderProductRepositoryEM.getByCustomOrder(customOrder);
        for (OrderProduct orderProduct : orderProductList) {
            productList.add(orderProduct.getProduct().getName());
        }
        orderResponseDto.setProductList(productList);

        return orderResponseDto;
    }

    public OrderAdminDto OrderToOrderAdminDto(CustomOrder customOrder){
        OrderAdminDto orderAdminDto = new OrderAdminDto();
        orderAdminDto.setOrderId(customOrder.getOrderId());
        orderAdminDto.setRestaurantName(customOrder.getRestaurant().getName());
        orderAdminDto.setUserName(customOrder.getUser().getFirstName() + " " + customOrder.getUser().getLastName());
        if(customOrder.getDeliveryPerson() != null)
            orderAdminDto.setDeliveryName(customOrder.getDeliveryPerson().getFirstName() + " " + customOrder.getDeliveryPerson().getLastName());

        orderAdminDto.setTotalCost(customOrder.getTotalCost());

        return orderAdminDto;
    }
}
