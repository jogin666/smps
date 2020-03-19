package com.zy.smps_password_service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PageParam {

    private String receiver;
    private String code;
    private String oldPassword;
    private String newPassword;
    private String account;
    private String email;
    private String phone;

}
