package com.example.fooddelivery.service;

import com.example.fooddelivery.dto.product.EditProductQuantityDto;
import com.example.fooddelivery.dto.product.GetProductListDto;
import com.example.fooddelivery.dto.product.NewProductDto;
import com.example.fooddelivery.exception.productExp.MenuHasProductToBeDeleted;
import com.example.fooddelivery.exception.productExp.ProductAlreadyExist;
import com.example.fooddelivery.exception.productExp.ProductDoesNotExist;
import com.example.fooddelivery.model.Product;
import com.example.fooddelivery.model.Restaurant;
import com.example.fooddelivery.repository.ProductRepository;
import com.example.fooddelivery.repository.RestaurantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    RestaurantRepository restaurantRepository;

    public Product addNewProduct(NewProductDto newProduct, Integer restaurantId) {

        Restaurant restaurant = restaurantRepository.getById(restaurantId);

        Product productExist = productRepository.findByNameAndRestaurant(newProduct.getName(), restaurant);
        if (productExist != null)
            throw new ProductAlreadyExist("Product " + newProduct.getName() + " already exists in " + restaurant.getName());

        Product product = new Product();
        product.setName(newProduct.getName());
        product.setQuantity(newProduct.getQuantity());
        product.setPrice(newProduct.getPrice());
        product.setRestaurant(restaurant);

        productRepository.save(product);

        return product;
    }

    public List<Product> editProductsQuantity(List<EditProductQuantityDto> productList, Integer restaurantId){
        Restaurant restaurant = restaurantRepository.getById(restaurantId);

        for (EditProductQuantityDto p: productList) {
            Product productExist = productRepository.findByNameAndRestaurant(p.getProductName(), restaurant);
            if (productExist == null)
                throw new ProductDoesNotExist("Product " + p.getProductName() + " does not exist in " + restaurant.getName());
        }

        List<Product> productArrayList = new ArrayList<>();

        for (EditProductQuantityDto p: productList) {
            Product productExist = productRepository.findByNameAndRestaurant(p.getProductName(), restaurant);
            productExist.setQuantity(productExist.getQuantity() + p.getSuplQuantity());
            productRepository.save(productExist);
            productArrayList.add(productExist);
        }

        return productArrayList;
    }

    public void deleteProduct (Integer productId, Integer restaurantId){
        Product productExist = productRepository.findByProductIdAndRestaurant(productId, restaurantRepository.getById(restaurantId));

        productRepository.delete(productExist);
    }

    public List<GetProductListDto> getProducts (){
        List<Product> productList = productRepository.findAll();
        List<GetProductListDto> advProductList = new ArrayList<>();

        for (Product p:productList) {
            GetProductListDto advProduct = new GetProductListDto();
            advProduct.setProductName(p.getName());
            advProduct.setQuantity(p.getQuantity());
            advProduct.setPrice(p.getPrice());
            advProduct.setRestaurant(p.getRestaurant().getName());

            advProductList.add(advProduct);
        }

        return advProductList;
    }

    public List<GetProductListDto> getProductsPerRestaurant (Integer restaurantId){
        Restaurant restaurant = restaurantRepository.getById(restaurantId);

        List<Product> productList = productRepository.findByRestaurant(restaurant);
        List<GetProductListDto> advProductList = new ArrayList<>();

        for (Product p:productList) {
            GetProductListDto advProduct = new GetProductListDto();
            advProduct.setProductName(p.getName());
            advProduct.setQuantity(p.getQuantity());
            advProduct.setPrice(p.getPrice());
            advProduct.setRestaurant(p.getRestaurant().getName());

            advProductList.add(advProduct);
        }

        return advProductList;
    }
}
