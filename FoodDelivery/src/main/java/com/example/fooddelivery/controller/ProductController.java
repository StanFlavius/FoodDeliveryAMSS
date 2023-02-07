package com.example.fooddelivery.controller;

import com.example.fooddelivery.CurrentUser;
import com.example.fooddelivery.config.CustomUserDetails;
import com.example.fooddelivery.dto.product.EditProductDto;
import com.example.fooddelivery.dto.product.EditProductQuantityDto;
import com.example.fooddelivery.dto.product.GetProductListDto;
import com.example.fooddelivery.dto.product.NewProductDto;
import com.example.fooddelivery.dto.restaurant.ViewRestaurantDto;
import com.example.fooddelivery.log.Logger;
import com.example.fooddelivery.mapper.ProductMapper;
import com.example.fooddelivery.model.Product;
import com.example.fooddelivery.model.RestaurantManager;
import com.example.fooddelivery.service.ProductService;
import com.example.fooddelivery.service.RestaurantManagerService;
import com.example.fooddelivery.service.RestaurantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/product")
@Validated
@Api(description = "PRODUCT OPERATIONS")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private RestaurantManagerService restaurantManagerService;

    @Autowired
    private ProductMapper productMapper;

    @ApiOperation(value = "ADD PRODUCT")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 200, message = "Successful operation"),
            @ApiResponse(code = 400, message = "Invalid request"),
            @ApiResponse(code = 404, message = "Specified resource does not exist")
    })
    @PostMapping("")
    public ResponseEntity<GetProductListDto> addNewProduct(@RequestBody @Valid NewProductDto newProductDto) {
        CustomUserDetails authenticatedUser = (CustomUserDetails) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        RestaurantManager restaurantManager = restaurantManagerService.getByUserId(authenticatedUser.getId());
        ViewRestaurantDto myRestaurant = restaurantService.getByRestaurantManagerId(restaurantManager.getRestaurantManagerId());

        Product product = productService.addNewProduct(newProductDto, myRestaurant.getId());
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        Logger.getInstance().logMsg(CurrentUser.getInstance().getUserId(), this.getClass().getSimpleName(), new Object(){}.getClass().getEnclosingMethod().getName(), dtf.format(now));

        return ResponseEntity.ok().body(ProductMapper.ProductToGetProductListDto(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity update(
            @PathVariable Integer id,
            @RequestBody @Valid EditProductDto editProductDto
            ) {
        boolean updateSucceeded = productService.update(id, editProductDto);
        if (!updateSucceeded) {
            return ResponseEntity.notFound().build();
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        Logger.getInstance().logMsg(CurrentUser.getInstance().getUserId(), this.getClass().getSimpleName(), new Object(){}.getClass().getEnclosingMethod().getName(), dtf.format(now));

        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "EDIT QUANTITIES OF PRODUCTS")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 200, message = "Successful operation"),
            @ApiResponse(code = 400, message = "Invalid request"),
            @ApiResponse(code = 404, message = "Specified resource does not exist")
    })
    @PutMapping("/quantity/{restaurantId}")
    public ResponseEntity<List<GetProductListDto>> editProductsQuantity(@PathVariable Integer restaurantId,
                                                                        @RequestBody @Valid List<EditProductQuantityDto> productList) {

        List<Product> productList1 =  productService.editProductsQuantity(productList, restaurantId);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        Logger.getInstance().logMsg(CurrentUser.getInstance().getUserId(), this.getClass().getSimpleName(), new Object(){}.getClass().getEnclosingMethod().getName(), dtf.format(now));

        return ResponseEntity.ok().body(productList1.stream().
                map(ProductMapper::ProductToGetProductListDto)
                .collect(Collectors.toList()));
    }

    @ApiOperation(value = "DELETE PRODUCT")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 200, message = "Successful operation"),
            @ApiResponse(code = 400, message = "Invalid request"),
            @ApiResponse(code = 404, message = "Specified resource does not exist")
    })
    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer productId) {
        productService.deleteProduct(productId);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        Logger.getInstance().logMsg(CurrentUser.getInstance().getUserId(), this.getClass().getSimpleName(), new Object(){}.getClass().getEnclosingMethod().getName(), dtf.format(now));

        return ResponseEntity.ok().body("Product deleted");
    }

    @ApiOperation(value = "GET INFO ABOUT ALL PRODUCTS")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 200, message = "Successful operation"),
            @ApiResponse(code = 400, message = "Invalid request"),
            @ApiResponse(code = 404, message = "Specified resource does not exist")
    })
    @GetMapping("")
    public ResponseEntity<List<GetProductListDto>> getProducts(){
        List<GetProductListDto> productList = productService.getProducts();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        Logger.getInstance().logMsg(CurrentUser.getInstance().getUserId(), this.getClass().getSimpleName(), new Object(){}.getClass().getEnclosingMethod().getName(), dtf.format(now));

        return ResponseEntity.ok().body(productList);
    }

    @ApiOperation(value = "GET INFO ABOUT ALL PRODUCTS OF RESTAURANTS")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 200, message = "Successful operation"),
            @ApiResponse(code = 400, message = "Invalid request"),
            @ApiResponse(code = 404, message = "Specified resource does not exist")
    })
    @GetMapping("/{restaurantId}")
    public ResponseEntity<List<GetProductListDto>> getProductsPerRestaurant(@PathVariable Integer restaurantId){
        List<GetProductListDto> productList = productService.getProductsPerRestaurant(restaurantId);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        Logger.getInstance().logMsg(CurrentUser.getInstance().getUserId(), this.getClass().getSimpleName(), new Object(){}.getClass().getEnclosingMethod().getName(), dtf.format(now));

        return ResponseEntity.ok().body(productList);
    }

    @ApiOperation(value = "GET INFO ABOUT MY PRODUCTS")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 200, message = "Successful operation"),
            @ApiResponse(code = 400, message = "Invalid request"),
            @ApiResponse(code = 404, message = "Specified resource does not exist")
    })
    @GetMapping("/my")
    public ResponseEntity<List<GetProductListDto>> getMyProducts() {
        CustomUserDetails authenticatedUser = (CustomUserDetails) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        RestaurantManager restaurantManager = restaurantManagerService.getByUserId(authenticatedUser.getId());
        ViewRestaurantDto myRestaurant = restaurantService.getByRestaurantManagerId(restaurantManager.getRestaurantManagerId());
        List<GetProductListDto> productList = productService.getProductsPerRestaurant(myRestaurant.getId());
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        Logger.getInstance().logMsg(CurrentUser.getInstance().getUserId(), this.getClass().getSimpleName(), new Object(){}.getClass().getEnclosingMethod().getName(), dtf.format(now));

        return ResponseEntity.ok().body(productList);
    }
}