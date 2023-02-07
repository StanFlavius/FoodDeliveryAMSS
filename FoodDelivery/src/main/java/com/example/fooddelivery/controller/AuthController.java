package com.example.fooddelivery.controller;

import com.example.fooddelivery.CurrentUser;
import com.example.fooddelivery.config.jwt.JwtProvider;
import com.example.fooddelivery.dto.auth.AuthRequest;
import com.example.fooddelivery.dto.auth.RegistrationRequest;
import com.example.fooddelivery.log.Logger;
import com.example.fooddelivery.model.UserEntity;
import com.example.fooddelivery.repositoryJpa.RoleEntityRepository;
import com.example.fooddelivery.repositoryJpa.UserEntityRepository;
import com.example.fooddelivery.service.NormalUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

@Slf4j
@RestController
@Api(description = "AUTHENTICATION OPERATIONS")
@Validated
public class AuthController {

    @Autowired
    private NormalUserService userService;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Autowired
    private RoleEntityRepository roleEntityRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @ApiOperation(value = "REGISTER USER")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 200, message = "Successful operation"),
            @ApiResponse(code = 400, message = "Invalid request"),
            @ApiResponse(code = 404, message = "Specified resource does not exist")
    })
    @PostMapping("/register")
    public ResponseEntity<RegistrationRequest> registerUser(@RequestBody @Valid RegistrationRequest registrationRequest) {

        userService.saveUser(registrationRequest, "ROLE_USER");
//        UserEntity userEntity = new UserEntity();
//        userEntity.setRoleEntity(roleEntityRepository.findByName("ROLE_ADMIN"));
//        userEntity.setEmail(registrationRequest.getEmail());
//        userEntity.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
//        userEntityRepository.save(userEntity);
        return ResponseEntity.ok().body(registrationRequest);
    }

//    @ApiOperation(value = "GET ALL USERS")
//    @ApiResponses(value = {
//            @ApiResponse(code = 500, message = "Internal server error"),
//            @ApiResponse(code = 200, message = "Successful operation"),
//            @ApiResponse(code = 400, message = "Invalid request"),
//            @ApiResponse(code = 404, message = "Specified resource does not exist")
//    })
//    @GetMapping("/getU")
//    public List<UserEntity> getU(){
//        return userService.getUsers();
//    }

    @ApiOperation(value = "AUTHENTICATE USER/ADMIN/DELIVERY PERSON")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 200, message = "Successful operation"),
            @ApiResponse(code = 400, message = "Invalid request"),
            @ApiResponse(code = 404, message = "Specified resource does not exist")
    })
    @PostMapping("/auth")
    public ResponseEntity<HashMap<String, String>> auth(@RequestBody @Valid AuthRequest request) {
        UserEntity userEntity = userService.findByLoginAndPassword(request.getEmail(), request.getPassword());
        String token = jwtProvider.generateToken(userEntity.getEmail());
        HashMap<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("userId", userEntity.getId().toString());
        response.put("email", userEntity.getEmail());
        response.put("role", userEntity.getRoleEntity().getName());

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        CurrentUser.getInstance().setCurrentUser(userEntity);

        Logger.getInstance().logMsg(CurrentUser.getInstance().getUserId(), this.getClass().getSimpleName(), new Object(){}.getClass().getEnclosingMethod().getName(), dtf.format(now));


        return ResponseEntity.ok().body(response);
    }

    @ApiOperation(value = "TEST ADMIN AUTHENTICATION")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 200, message = "Successful operation"),
            @ApiResponse(code = 400, message = "Invalid request"),
            @ApiResponse(code = 404, message = "Specified resource does not exist")
    })
    @GetMapping("/admin/get")
    public String getAdmin() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        Logger.getInstance().logMsg(CurrentUser.getInstance().getUserId(), this.getClass().getSimpleName(), new Object(){}.getClass().getEnclosingMethod().getName(), dtf.format(now));

        return "Hi admin";
    }

    @ApiOperation(value = "TEST USER AUTHENTICATION")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 200, message = "Successful operation"),
            @ApiResponse(code = 400, message = "Invalid request"),
            @ApiResponse(code = 404, message = "Specified resource does not exist")
    })
    @GetMapping("/user/get")
    public String getUser() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        Logger.getInstance().logMsg(CurrentUser.getInstance().getUserId(), this.getClass().getSimpleName(), new Object(){}.getClass().getEnclosingMethod().getName(), dtf.format(now));

        return "Hi user";
    }

    @ApiOperation(value = "TEST DELIVERY PERSON AUTHENTICATION")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 200, message = "Successful operation"),
            @ApiResponse(code = 400, message = "Invalid request"),
            @ApiResponse(code = 404, message = "Specified resource does not exist")
    })
    @GetMapping("/del/get")
    public String getDelivery() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        Logger.getInstance().logMsg(CurrentUser.getInstance().getUserId(), this.getClass().getSimpleName(), new Object(){}.getClass().getEnclosingMethod().getName(), dtf.format(now));

        return "Hi delivery";
    }

    @ApiOperation(value = "TEST RESTAURANT MANAGER AUTHENTICATION")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 200, message = "Successful operation"),
            @ApiResponse(code = 400, message = "Invalid request"),
            @ApiResponse(code = 404, message = "Specified resource does not exist")
    })
    @GetMapping("/restman/get")
    public String getRestManager() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        Logger.getInstance().logMsg(CurrentUser.getInstance().getUserId(), this.getClass().getSimpleName(), new Object(){}.getClass().getEnclosingMethod().getName(), dtf.format(now));

        return "Hi manager";
    }
}
