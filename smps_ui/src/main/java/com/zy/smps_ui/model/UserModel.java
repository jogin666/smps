package com.zy.smps_ui.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserModel implements Serializable {
    
    private String userId;
    private String account;
    private String password;
    private String name;
    private String phone;
    private Integer age;
    private String gender;
    private String image;
    private String email;
    private Integer state;
    private Integer isAdmin;
    private boolean isMale;
    private String roleName;
    private String class_dept;
    private String genderStr;
}
