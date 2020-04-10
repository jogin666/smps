package com.zy.smps_password_service.service.impl;

import com.zy.smps_password_service.helper.CodeHelper;
import com.zy.smps_password_service.helper.SMSHelper;
import com.zy.smps_password_service.entity.UserEntity;
import com.zy.smps_password_service.reposity.PasswordRepository;
import com.zy.smps_password_service.service.PasswordService;
import com.zy.smps_password_service.helper.MailHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

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
    @Autowired
    private StringRedisTemplate redisTemplate;


    //手机发送验证码
    @Override
    public String sendMSM(String phone) {
        String code = CodeHelper.createCode();
        if (!redisTemplate.hasKey(phone)){
            redisTemplate.opsForValue().set(phone,code);
            redisTemplate.expire(phone,3, TimeUnit.MINUTES);
        }
        SMSHelper.sendSMS(phone,code);
        return SUCCESS;
    }

    //邮件发送验证码
    @Override
    public String sendVerifyNumber(String email) {
        String code=CodeHelper.createCode();
        if (redisTemplate.hasKey(email)) {
            redisTemplate.opsForValue().set(email, code);
            redisTemplate.expire(email, 3, TimeUnit.MINUTES);
        }
        mailSender.send(MailHelper.builderMailMessage(email,code));
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
        String code = redisTemplate.opsForValue().get(receiver);
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
