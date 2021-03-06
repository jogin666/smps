package com.zy.smps_ui.controller;

import com.zy.smps_ui.util.IPAddressHelper;
import com.zy.smps_ui.model.UserModel;
import com.zy.smps_ui.util.RequestResultBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/UI")
@Slf4j
public class UIController {

    private final String USER="user";

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @GetMapping("/home")
    public String getHomeView(){
        return "/view/message/home";
    }

    @GetMapping("/syshome")
    public String getAdminView(){
        return "/view/sys/sysview";
    }

    @GetMapping("/home/tag")
    public String getHomeView(HttpServletRequest request){
        try{
            UserModel user = (UserModel) request.getSession().getAttribute(USER);
            if (user.getIsAdmin()==1){
                return "/view/sys/home";
            }
            return "/view/message/home";
        }catch (Exception e){
            return "/view/message/home";
        }
    }

    @GetMapping("/sys/message")
    public String getMessageCheckView(HttpServletRequest request){
        String roleName = getUserModel(request).getRoleName();
        if (roleName.contains("管理员") || roleName.contains("消息审核员")){
            return "/view/sys/message";
        }
        return "/view/sys/tip";
    }

    @GetMapping("/sys/user")
    public String getUserManagerView(HttpServletRequest request){
        String roleName = getUserModel(request).getRoleName();
        if (roleName.contains("管理员")){
            return "/view/sys/user";
        }
        return "/view/sys/tip";
    }

    @GetMapping("/sys/role")
    public String getRoleView(HttpServletRequest request){
        String roleName=getUserModel(request).getRoleName();
        if (roleName.contains("管理员")){
            return "/view/sys/role";
        }
        return "/view/sys/tip";
    }
    
    @GetMapping("/sys/admin")
    public String getAdminView(HttpServletRequest request){
        String roleName = getUserModel(request).getRoleName();
        if (roleName.contains("超级管理员")){
            return "/view/sys/admin";
        }
        return "/view/sys/tip";
    }

    @GetMapping("/template/{mId}")
    public String getTemplateView(@PathVariable("mId")String mId){
        return "/view/template/show_mess_template";
    }

    @GetMapping("/template/check/{mId}")
    public String getMessageView(@PathVariable("mId")String mId){
        log.info("mId: "+mId);
        return "/view/template/check_mess_template";
    }

    @GetMapping("/loginOut")
    public String getLoginView(HttpServletRequest  request){
        request.getSession().removeAttribute(USER);
        redisTemplate.opsForHash().delete(USER,IPAddressHelper.getIPAddress(request));
        return "/index";
    }

    @GetMapping("/user/info")
    public String getUserInfoView(HttpServletRequest request){
        UserModel model = getUserModel(request);
        if (model.getIsAdmin()==1){
            return "/view/sys/userInfo";
        }
        return "/view/user/userInfo";
    }

    @GetMapping("/user/{account}")
    public String getUserEditView(@PathVariable("account")String account){
        return "/view/sys/useredit";
    }

    @GetMapping("/admin/{account}")
    public String getAdminView(@PathVariable("account")String account){
        return "/view/sys/adminedit";
    }

    @GetMapping("/role/{roleId}")
    public String getRoleView(@PathVariable("roleId")String roleId){
        return "/view/sys/roleedit";
    }

    @ResponseBody
    @PostMapping("/user")
    public Map<String,String> login(@ModelAttribute(USER) UserModel userModel, HttpServletRequest request){
        log.info("post: user: "+userModel);
        request.getSession().setAttribute(USER,userModel);
        String ipAddress = IPAddressHelper.getIPAddress(request);
        redisTemplate.opsForHash().put(USER,ipAddress,userModel);
        Map<String, String> map = RequestResultBuilder.buildResult("success");
        String url="";
        if (userModel.getIsAdmin()==0){
            url="UI/home";
        }else{
            url="UI/syshome";
        }

        map.put("url",url);
        return map;
    }

    @ResponseBody
    @GetMapping("/user/")
    public UserModel getUser(HttpServletRequest request){
        UserModel model =getUserModel(request);
        if (model==null){
            model=getUserModel(request);
        }
        try{
            if ("male".equals(model.getGender())){
                model.setMale(true);
                model.setGenderStr("男");
            }else{
                model.setMale(false);
                model.setGenderStr("女");
            }
            log.info("user: "+model);
            return model;
        }catch (Exception e){
            return null;
        }
    }

    @ResponseBody
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

    private UserModel getUserModel(HttpServletRequest request){
        UserModel model=(UserModel) request.getSession().getAttribute(USER);
        if (model==null){
            String ip=IPAddressHelper.getIPAddress(request);
            model= (UserModel) redisTemplate.opsForHash().get(USER,ip);
        }
        return model;
    }
}
