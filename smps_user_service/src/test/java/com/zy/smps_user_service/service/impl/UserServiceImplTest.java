package com.zy.smps_user_service.service.impl;

import com.zy.smps_user_service.entity.UserEntity;
import com.zy.smps_user_service.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Test
    void exit(){
        UserEntity sadmin02 = userService.exit("sadmin02", "123456");
        System.out.println(sadmin02.getRoleName());
        System.out.println(sadmin02);
    }
}