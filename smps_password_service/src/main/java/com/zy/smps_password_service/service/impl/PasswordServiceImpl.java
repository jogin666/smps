package com.zy.smps_password_service.service.impl;

import com.zy.smps_password_service.helper.CodeHelper;
import com.zy.smps_password_service.helper.SMSHelper;
import com.zy.smps_password_service.entity.UserEntity;
import com.zy.smps_password_service.reposity.PasswordRepository;
import com.zy.smps_password_service.service.PasswordService;
import com.zy.smps_password_service.helper.MailHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@Slf4j
public class PasswordServiceImpl implements PasswordService {

    private final static String SUCCESS="success";
    private final static String FAILURE="failure";

    @Autowired
    private PasswordRepository userRepository;
    @Autowired
    private JavaMailSender mailSender;


    //手机发送验证码
    @Override
    public String sendMSM(String phone) {
        String code = CodeHelper.createCode(phone);
        SMSHelper.sendSMS(phone,code);
        return SUCCESS;
    }

    //邮件发送验证码
    @Override
    public String sendVerifyNumber(String email) {
        mailSender.send(MailHelper.builderMailMessage(email));
        return SUCCESS;
    }

    //更新密码
    @Override
    public String updatePassword(String account,String newPassword) {
        UserEntity userEntity = userRepository.findUserEntityByAccount(account);
        userEntity.setPassword(newPassword);
        userRepository.save(userEntity);
        return SUCCESS;
    }

    //验证码是否正确
    @Override
    public boolean checkVerifyNumber(String receiver,String number) {
        String code = CodeHelper.find(receiver);
        if (number.equals(code)){
            return true;
        }
        return false;
    }

    //旧密码是否正确
    @Override
    public boolean checkCorrectPass(String account,String password) {
        UserEntity user = userRepository.findUserEntityByAccount(account);
        log.info("user: "+user);
        if (user!=null && password.equals(user.getPassword())){
            return true;
        }
        return false;
    }

    @Override
    public boolean findUserByAccount(String account) {
        UserEntity user = userRepository.findUserEntityByAccount(account);
        if (user!=null){
            return true;
        }
        return false;
    }

    @Override
    public boolean findUserByAccountAndPhone(String account, String phone) {
        UserEntity user = userRepository.findUserEntityByAccountAndPhone(account, phone);
        log.info("user: "+user);
        if (user!=null){
            return true;
        }
        return false;
    }

    @Override
    public boolean findUserByAccountAndEmail(String account, String email) {
        UserEntity user = userRepository.findUserEntityByAccountAndEmail(account, email);
        if (user!=null){
            return true;
        }
        return false;
    }
}
