package com.example.fooddelivery;

import com.example.fooddelivery.model.UserEntity;

public class CurrentUser {


    private static CurrentUser currentUser = null;
    private Integer id;

    private CurrentUser() { }

    public static synchronized CurrentUser getInstance(){
        if(currentUser == null)
            currentUser = new CurrentUser();
        return currentUser;
    }

    public void setCurrentUser (UserEntity userEntity) {
        id = userEntity.getId();
    }

    public Integer getUserId () {
        return id;
    }
}
