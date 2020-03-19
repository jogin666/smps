package com.zy.smps_ui.controller;

import com.zy.smps_ui.common.DataMap;
import com.zy.smps_ui.configuration.IPAddressHelper;
import com.zy.smps_ui.model.UserModel;
import com.zy.smps_ui.util.RequestResultBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/UI")
public class HelperController {

    private final String USER="user";

    @PostMapping("/user")
    public Map<String,String> login(@ModelAttribute("user") UserModel userModel,HttpServletRequest request){
        log.info("post: user: "+userModel);
        request.getSession().setAttribute(USER,userModel);
        String ipAddress = IPAddressHelper.getIPAddress(request);
        DataMap.put(USER,ipAddress,userModel);
        Map<String, String> map = RequestResultBuilder.buildResult("success");
        String url="";
        if (userModel.getIsAdmin()==0){
            url="UI/view/info";
        }else{
            url="UI/view/admin";
        }

        map.put("url",url);
        return map;
    }

    @GetMapping("/user/")
    public UserModel getUser(HttpServletRequest request){
        UserModel model = (UserModel) request.getSession().getAttribute(USER);
        if (model==null){
            String ipAddress=IPAddressHelper.getIPAddress(request);
            model= (UserModel) DataMap.getData(USER).get(ipAddress);
        }
        if ("male".equals(model.getGender())){
            model.setMale(true);
        }else{
            model.setMale(false);
        }
        log.info("user: "+model);
        return model;
    }

    @PutMapping("/user/")
    public Map<String,String> updateUserInfo(@ModelAttribute UserModel model,HttpServletRequest request){
        UserModel user=(UserModel)request.getSession().getAttribute(USER);
        log.info("put : user: "+user);
        user.setEmail(model.getEmail());
        user.setPhone(model.getPhone());
        user.setImage(model.getImage());
        request.getSession().setAttribute(USER,user);
        return RequestResultBuilder.buildResult("success");
    }

}
