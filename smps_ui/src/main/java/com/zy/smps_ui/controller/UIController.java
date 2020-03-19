package com.zy.smps_ui.controller;

import com.zy.smps_ui.common.DataMap;
import com.zy.smps_ui.configuration.IPAddressHelper;
import com.zy.smps_ui.model.UserModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/UI")
@Slf4j
public class UIController {


    @GetMapping("/view/info")
    public String getHomeView(){
        return "/view/message/home";
    }

    @GetMapping("/view/password")
    public String getForgetPassView(){
        return "/view/search_password";
    }

    @GetMapping("/view/admin")
    public String getAdminView(){
        return "/view/admin/sysview";
    }

    @GetMapping("/user/loginOut")
    public String getLoginView(HttpServletRequest  request){
        request.getSession().removeAttribute("user");
        return "/index";
    }

    @GetMapping("/view/home")
    public String getHomeView(HttpServletRequest request){
        try{
            UserModel user = (UserModel) request.getSession().getAttribute("user");
            if (user.getIsAdmin()==1){
                return "/view/admin/home";
            }
            return "/view/message/home";
        }catch (Exception e){
            return "/view/view/home";
        }
    }

    @GetMapping("/message/view/check")
    public String getMessageView(@RequestParam("messId")String messId,HttpServletRequest request){
        HttpSession session = request.getSession();
        UserModel model = (UserModel) session.getAttribute("user");
        session.setAttribute(model.getAccount(),messId);
        return "/view/admin/check_message";
    }

    @GetMapping("/message/view/edit")
    public String getMessageEditView(@RequestParam("messId")String messId,HttpServletRequest request){
        HttpSession session = request.getSession();
        UserModel model = (UserModel) session.getAttribute("user");
        if (model==null){
            String ipAddress= IPAddressHelper.getIPAddress(request);
            model= (UserModel) DataMap.getData("user").get(ipAddress);
        }
        session.setAttribute(model.getAccount(),messId);
        return "/view/message/edit";
    }

    @ResponseBody
    @GetMapping("/message")
    public Map<String,String> getMessId(HttpServletRequest request){
        HashMap<String, String> result = new HashMap<>();
        HttpSession session = request.getSession();
        UserModel model= (UserModel) session.getAttribute("user");
        String messId=(String) session.getAttribute(model.getAccount());
        if (!StringUtils.isEmpty(messId)){
            result.put("result","success");
            result.put("messId",messId);
        }else{
            result.put("result","failure");
            result.put("msg","获取消息Id 失败");
        }
        return result;
    }

}
