package com.example.fooddelivery.service;

import com.example.fooddelivery.dto.RestaurantManager.AddRestaurantManagerDto;
import com.example.fooddelivery.dto.auth.RegistrationRequest;
import com.example.fooddelivery.exception.userExp.EmailExist;
import com.example.fooddelivery.model.*;
import com.example.fooddelivery.repository.RestaurantManagerRepository;
import com.example.fooddelivery.repository.RoleEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RestaurantManagerService {

    @Autowired
    RestaurantManagerRepository restaurantManagerRepository;

    @Autowired
    private NormalUserService normalUserService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleEntityRepository roleEntityRepository;

    public RestaurantManager addNewRestaurantManager(AddRestaurantManagerDto restaurantManagerDto){
        UserEntity userEntity = normalUserService.findByLogin(restaurantManagerDto.getEmail());
        if (userEntity != null)
            throw new EmailExist("User with email: " + restaurantManagerDto.getEmail() + " already exists");

        userEntity = new UserEntity();
        userEntity.setPassword(passwordEncoder.encode("123456"));
        userEntity.setEmail(restaurantManagerDto.getEmail());

        RoleEntity userRole = roleEntityRepository.findByName("ROLE_RESTMAN");
        userEntity.setRoleEntity(userRole);

        RestaurantManager restaurantManager = new RestaurantManager();
        restaurantManager.setFirstName(restaurantManagerDto.getFirstName());
        restaurantManager.setLastName(restaurantManagerDto.getLastName());
        restaurantManager.setUserEntity(userEntity);

        return restaurantManagerRepository.save(restaurantManager);
    }

    public RestaurantManager getByUserId(Integer userId) {
        return restaurantManagerRepository.findByUserEntity_Id(userId);
    }
}
