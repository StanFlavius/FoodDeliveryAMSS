package com.example.fooddelivery.controller;

import com.example.fooddelivery.CurrentUser;
import com.example.fooddelivery.dto.user.EditUserLocationDto;
import com.example.fooddelivery.dto.user.EditUserPasswordDto;
import com.example.fooddelivery.log.Logger;
import com.example.fooddelivery.model.UserEntity;
import com.example.fooddelivery.service.NormalUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@RestController
@RequestMapping("/user")
@Validated
@Api(description = "USER OPERATIONS")
public class UserController {

    @Autowired
    private NormalUserService normalUserService;

//    @ApiOperation(value = "EDIT LOCATION")
//    @ApiResponses(value = {
//            @ApiResponse(code = 500, message = "Internal server error"),
//            @ApiResponse(code = 200, message = "Successful operation"),
//            @ApiResponse(code = 400, message = "Invalid request"),
//            @ApiResponse(code = 404, message = "Specified resource does not exist")
//    })
//    @PutMapping("/editLoc/{userId}/{loc}")
//    public ResponseEntity<EditUserLocationDto> editLocation(@PathVariable Integer userId,
//                                                            @PathVariable @NotBlank(message = "New location is required") String loc){
////        userService.editLocation(userId, loc);
////
////        return ResponseEntity.ok().body("Location modified");
//        return ResponseEntity.ok().body(normalUserService.editLocation(userId, loc));
//    }

    @ApiOperation(value = "EDIT PASSWORD")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Internal server error"),
            @ApiResponse(code = 200, message = "Successful operation"),
            @ApiResponse(code = 400, message = "Invalid request"),
            @ApiResponse(code = 404, message = "Specified resource does not exist")
    })
    @PutMapping("/editPass/{userId}/{newPassword}")
    public ResponseEntity<EditUserPasswordDto> editPassword(@PathVariable Integer userId,
                                                            @PathVariable @NotBlank(message = "New password is required") String newPassword){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        Logger.getInstance().logMsg(CurrentUser.getInstance().getUserId(), this.getClass().getSimpleName(), new Object(){}.getClass().getEnclosingMethod().getName(), dtf.format(now));

        return ResponseEntity.ok().body(normalUserService.editPassword(userId, newPassword));
    }
}
