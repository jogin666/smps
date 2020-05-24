package com.zy.smps_admin_service;

import com.zy.smps_admin_service.entity.AdminEntity;
import com.zy.smps_admin_service.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
class SmpsAdminServiceApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private AdminService adminService;

    @Test
    void test(){
        List<AdminEntity> all = adminService.findAll();
        for (AdminEntity adminEntity:all){
            log.info(adminEntity.toString());
        }
    }
}
