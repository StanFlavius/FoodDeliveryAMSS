package com.example.fooddelivery.controller;

import com.example.fooddelivery.dto.OrderDelivery.OrderAdminDto;
import com.example.fooddelivery.dto.OrderDelivery.ProductOrderDto;
import com.example.fooddelivery.dto.OrderDelivery.OrderResponseDto;
import com.example.fooddelivery.mapper.OrderMapper;
import com.example.fooddelivery.model.CustomOrder;
import com.example.fooddelivery.repository.NormalUserRepository;
import com.example.fooddelivery.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/order")
@Validated
@Api(description = "ORDER OPERATIONS")
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    NormalUserRepository normalUserRepository;

    @ApiOperation(value = "MAKE AN ORDER")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 200, message = "Successful operation"),
            @ApiResponse(code = 400, message = "Invalid request"),
            @ApiResponse(code = 404, message = "Specified resource does not exist")
    })
    @PostMapping("/add")
    public ResponseEntity<OrderResponseDto> addNewOrder(@RequestParam Integer userId,
                                                        @RequestParam Integer restaurantId,
                                                        @RequestParam Optional<String> location,
                                                        @RequestBody List<ProductOrderDto> orderMenus){
        String address = null;
        address = location.orElseGet(() -> normalUserRepository.getById(userId).getAddress());

        return ResponseEntity.ok().body(orderService.addNewOrder(userId, restaurantId, address, orderMenus));
    }

    @ApiOperation(value = "EDIT ORDER STATUS")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 200, message = "Successful operation"),
            @ApiResponse(code = 400, message = "Invalid request"),
            @ApiResponse(code = 404, message = "Specified resource does not exist")
    })
    @PutMapping("/edit/{orderId}/{deliveryId}")
    public ResponseEntity<OrderResponseDto> editOrderStatus(@PathVariable Integer orderId, @PathVariable Integer deliveryId){
        CustomOrder order = orderService.editStatus(orderId, deliveryId);

        return ResponseEntity.ok().body(orderMapper.OrderToOrderResponseDto(order));
    }

    @GetMapping("/forDelivery")
    public ResponseEntity<List<OrderResponseDto>> getOrdersForDelivery(){
        return ResponseEntity.ok(orderService.getOrdersForDelivery());
    }

    @GetMapping("/allUser/{userId}")
    public ResponseEntity<List<OrderResponseDto>> getAllOrdersOfUser(@PathVariable Integer userId){
        return ResponseEntity.ok(orderService.getOrdersPerUser(userId));
    }

    @GetMapping("/allUserCurrent/{userId}")
    public ResponseEntity<List<OrderResponseDto>> getAllCurrentOrdersOfUser(@PathVariable Integer userId){
        return ResponseEntity.ok(orderService.getCurrentOrdersPerUser(userId));
    }

    @GetMapping("/allRest/{restaurantManagerId}")
    public ResponseEntity<List<OrderResponseDto>> getAllOrdersOfRestaurant(@PathVariable Integer restaurantManagerId){
        return ResponseEntity.ok(orderService.getOrdersPerRestaurant(restaurantManagerId));
    }

    @GetMapping("/ordersToday")
    public ResponseEntity<List<OrderAdminDto>> getOrdersOfToday(){
        return ResponseEntity.ok(orderService.getOrdersOfToday());
    }

    @GetMapping("/allOrders")
    public ResponseEntity<List<OrderAdminDto>> getAllOrders(){
        return ResponseEntity.ok(orderService.getAllOrders());
    }
}
