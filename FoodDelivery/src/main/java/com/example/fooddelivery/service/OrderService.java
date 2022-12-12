package com.example.fooddelivery.service;

import com.example.fooddelivery.dto.OrderDelivery.OrderAdminDto;
import com.example.fooddelivery.dto.OrderDelivery.ProductOrderDto;
import com.example.fooddelivery.dto.OrderDelivery.OrderResponseDto;
import com.example.fooddelivery.exception.orderExp.NoDeliveryPersonAvailvable;
import com.example.fooddelivery.exception.orderExp.NoMoreProducts;
import com.example.fooddelivery.mapper.OrderMapper;
import com.example.fooddelivery.model.*;
import com.example.fooddelivery.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    DeliveryPersonRepository deliveryPersonRepository;

    @Autowired
    UserEntityRepository userEntityRepository;

    @Autowired
    NormalUserRepository normalUserRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderProductRepository orderProductRepository;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    RestaurantManagerRepository restaurantManagerRepository;

    public OrderResponseDto addNewOrder(Integer userId, Integer restaurantId, String address, List<ProductOrderDto> productOrderList) {
        //check available delivery person
        Calendar now = Calendar.getInstance();
        Integer currHour = now.get(Calendar.HOUR_OF_DAY);

        Boolean ok = Boolean.FALSE;
        List<DeliveryPerson> deliveryPersonList = deliveryPersonRepository.findAll();
        for (DeliveryPerson deliveryPerson : deliveryPersonList) {
            if (currHour >= deliveryPerson.getScheduleStart() &&
                currHour <= deliveryPerson.getScheduleStop()){
                if (deliveryPerson.getAvailability().equals("AVAILABLE")){
                    ok = Boolean.TRUE;
                }
            }
        }

        if (ok == Boolean.FALSE)
            throw new NoDeliveryPersonAvailvable("No delivery person available. Try again later.");

        //check products
        Integer totalCost = 0;
        Restaurant restaurant = restaurantRepository.getById(restaurantId);
        for (ProductOrderDto productOrder : productOrderList){
            String productName = productOrder.getProduct();
            Integer quantity = productOrder.getQuantity();
            Product product = productRepository.findByNameAndRestaurant(productName, restaurant);
            Integer quantityDB = product.getQuantity();
            if(quantity > quantityDB)
                throw new NoMoreProducts("We don't have resources anymore: product " + productName + " is not available in " + restaurant.getName());
            totalCost += (quantity * productRepository.findByName(productName).getPrice());
        }

        CustomOrder customOrder = new CustomOrder();
        customOrder.setTotalCost(totalCost);
        customOrder.setAddress(address);
        customOrder.setStatus("preparing");
        customOrder.setStatusTime(new Timestamp(System.currentTimeMillis()));
        customOrder.setRestaurant(restaurantRepository.getById(restaurantId));

        NormalUser normalUser = normalUserRepository.getById(userId);
        customOrder.setUser(normalUser);
        orderRepository.save(customOrder);

        List<String> productList = new ArrayList<>();
        for (ProductOrderDto productOrder : productOrderList){
            String productName = productOrder.getProduct();
            productList.add(productName);
            Integer quantity = productOrder.getQuantity();
            Product product = productRepository.findByName(productName);
            product.setQuantity(product.getQuantity() - productOrder.getQuantity());
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setProduct(product);
            orderProduct.setQuantity(quantity);
            orderProduct.setCustomOrder(customOrder);
            orderProductRepository.save(orderProduct);
        }

        OrderResponseDto orderResponseDto = new OrderResponseDto();
        orderResponseDto.setOrderId(customOrder.getOrderId());
        orderResponseDto.setAddress(customOrder.getAddress());
        orderResponseDto.setTotalPrice(customOrder.getTotalCost());
        orderResponseDto.setStatus(customOrder.getStatus());
        orderResponseDto.setLastTime(customOrder.getStatusTime());
        orderResponseDto.setRestaurantName(customOrder.getRestaurant().getName());

        orderResponseDto.setProductList(productList);

        return orderResponseDto;
    }

    public CustomOrder editStatus(Integer orderId, Integer deliveryId) {
        CustomOrder order = orderRepository.findByOrderId(orderId);

        if(order.getStatus().equals("preparing")){
            order.setStatus("on the way");
            DeliveryPerson deliveryPerson = deliveryPersonRepository.findByDeliveryPersonId(deliveryId);
            deliveryPerson.setAvailability("NOT AVAILABLE");
            deliveryPersonRepository.save(deliveryPerson);
            order.setDeliveryPerson(deliveryPerson);
            order.setStatusTime(new Timestamp(System.currentTimeMillis()));
        }
        else if(order.getStatus().equals("on the way")){
            order.setStatus("delivered");
            DeliveryPerson deliveryPerson = deliveryPersonRepository.findByDeliveryPersonId(deliveryId);
            deliveryPerson.setAvailability("AVAILABLE");
            deliveryPersonRepository.save(deliveryPerson);
            order.setStatusTime(new Timestamp(System.currentTimeMillis()));
        }

        return orderRepository.save(order);
    }

    public List<OrderResponseDto> getOrdersForDelivery(){
        List<CustomOrder> orderList = orderRepository.findByStatus("preparing");

        List<OrderResponseDto> orderResponseDtoList = new ArrayList<>();

        for (CustomOrder order : orderList) {
            orderResponseDtoList.add(orderMapper.OrderToOrderResponseDto(order));
        }

        return orderResponseDtoList;
    }

    public List<OrderResponseDto> getOrdersPerUser(Integer userId){
        NormalUser normalUser = normalUserRepository.getById(userId);

        List<CustomOrder> orderList = orderRepository.findByUser(normalUser);

        List<OrderResponseDto> orderResponseDtoList = new ArrayList<>();

        for (CustomOrder order : orderList) {
            orderResponseDtoList.add(orderMapper.OrderToOrderResponseDto(order));
        }

        return orderResponseDtoList;
    }

    public List<OrderResponseDto> getCurrentOrdersPerUser(Integer userId){
        NormalUser normalUser = normalUserRepository.getById(userId);

        List<CustomOrder> orderList = orderRepository.findByUserAndStatus(normalUser, "pending");

        List<OrderResponseDto> orderResponseDtoList = new ArrayList<>();

        for (CustomOrder order : orderList) {
            orderResponseDtoList.add(orderMapper.OrderToOrderResponseDto(order));
        }

        return orderResponseDtoList;
    }

    public List<OrderResponseDto> getOrdersPerRestaurant(Integer restaurantManagerId){
        RestaurantManager restaurantManager = restaurantManagerRepository.getById(restaurantManagerId);

        Restaurant restaurant = restaurantRepository.findByRestaurantManager(restaurantManager);

        List<CustomOrder> orderList = orderRepository.findByRestaurant(restaurant);

        List<OrderResponseDto> orderResponseDtoList = new ArrayList<>();

        for (CustomOrder order : orderList) {
            orderResponseDtoList.add(orderMapper.OrderToOrderResponseDto(order));
        }

        return orderResponseDtoList;
    }

    public List<OrderAdminDto> getOrdersOfToday() {
        List<CustomOrder> filteredOrderList = new ArrayList<>();

        for (CustomOrder order : orderRepository.findAll()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String orderTime = sdf.format(order.getStatusTime());
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime now = LocalDateTime.now();
            String currTime = dtf.format(now);

            if (orderTime.equals(currTime)) {
                filteredOrderList.add(order);
            }
        }

        List<OrderAdminDto> orderAdminDtos = new ArrayList<>();
        for (CustomOrder order : filteredOrderList) {
            orderAdminDtos.add(orderMapper.OrderToOrderAdminDto(order));
        }

        return orderAdminDtos;
    }

    public List<OrderAdminDto> getAllOrders() {
        List<CustomOrder> filteredOrderList = orderRepository.findAll();

        List<OrderAdminDto> orderAdminDtos = new ArrayList<>();
        for (CustomOrder order : filteredOrderList) {
            orderAdminDtos.add(orderMapper.OrderToOrderAdminDto(order));
        }

        return orderAdminDtos;
    }

}
