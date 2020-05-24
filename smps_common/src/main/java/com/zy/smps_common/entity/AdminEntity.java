package com.zy.smps_common.entity;

import lombok.Data;

import java.util.List;

@Data
public class AdminEntity {

    private String uId;
    private String account;
    private String password;
    private String name;
    private String phone;
    private int age;
    private String gender;
    private String image;
    private String email;
    private int state;
    private int isAdmin;
    private List<RoleEntity> adminRoleEntityList;

}
