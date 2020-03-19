package com.zy.smps_password_service.service;


import com.zy.smps_password_service.entity.UserEntity;

public interface PasswordService {

    String sendMSM(String phone);

    String sendVerifyNumber(String email);

    String updatePassword(String account, String newPassword);

    boolean checkVerifyNumber(String receiver, String number);

    boolean checkCorrectPass(String account, String password);

    boolean findUserByAccount(String account);

    boolean findUserByAccountAndPhone(String account,String Phone);

    boolean findUserByAccountAndEmail(String account,String email);

}
