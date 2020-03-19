package com.zy.smps_password_service.controller;

import com.zy.smps_password_service.entity.PageParam;
import com.zy.smps_password_service.service.PasswordService;
import com.zy.smps_password_service.util.RequestResultMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/password")
@Slf4j
public class PasswordServiceController {

    @Autowired
    private PasswordService passwordService;
    private final String RESULT_FAILURE="failure";
    private final String ERROR="true";

    @GetMapping("/code/sms")
    public Map<String,String> sendSMS(@RequestParam("receiver") String phone,@RequestParam("account")String account){
        log.info("手机发送验证码 phone: "+phone);
        HashMap<String, String> map = new HashMap<>();
        if (passwordService.findUserByAccount(account)){
            boolean t = passwordService.findUserByAccountAndPhone(account, phone);
            if (t){
                map.put("result",passwordService.sendMSM(phone));
                return map;
            }
            map.put("codeWayError",ERROR);
        }
        map.put("accountError",ERROR);
        return map;
    }

    @GetMapping("/code/sml")
    public Map<String,String> sendEmail(@RequestParam("receiver") String email,@RequestParam("account")String account){
        log.info("邮箱发送验证码： email" +email);
        HashMap<String, String> map = new HashMap<>();
        if (passwordService.findUserByAccount(account)){
            boolean t = passwordService.findUserByAccountAndEmail(account, email);
            if (t){
                 map.put("result",passwordService.sendVerifyNumber(email));
                 return map;
            }
            map.put("codeWayError",ERROR);
        }
        map.put("accountError",ERROR);
        return map;
    }

    @PutMapping("/{account}")
    public Map<String,String> changePassword(@PathVariable("account")String account, @ModelAttribute PageParam pageParam){
        log.info("pageParam :"+pageParam);
        String result="";
        HashMap<String, String> resultMap = new HashMap<>();
        try{
            String newPassword = pageParam.getNewPassword();
            String receiver = pageParam.getReceiver();
            if(!StringUtils.isEmpty(receiver)){  //找回密码
                String code = pageParam.getCode();
                if (passwordService.checkVerifyNumber(receiver, code)) {
                    result = passwordService.updatePassword(account, newPassword);
                }else{
                    resultMap.put("codeError","true");
                }
            }else{   //修改密码
                String oldPassword = pageParam.getOldPassword();
                boolean t = passwordService.checkCorrectPass(account,oldPassword);
                if (!t){
                    resultMap.put("passwordError","true");
                }else{
                    result=passwordService.updatePassword(account,newPassword);
                }
            }
        }catch (NullPointerException e){
            log.error("获取页面参数失败",e);
        }
        resultMap.put("result",result);
        return resultMap;
    }

}
