package com.zy.smps_ui.interceptor;

import com.zy.smps_ui.data.DataMap;
import com.zy.smps_ui.model.UserModel;
import com.zy.smps_ui.util.IPAddressHelper;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class UrlInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
       try {
           UserModel user = (UserModel) request.getSession().getAttribute("user");
           if (user==null){
               Map<String, Object> map = DataMap.getData("user");
               if (map!=null){
                   String ipAddress = IPAddressHelper.getIPAddress(request);
                   user= (UserModel) map.get(ipAddress);
               }
           }
           if (user==null) {
               response.sendRedirect(request.getContextPath()+"/");  //转到登录页面
               return false;
           }
           return true;
       }catch (NullPointerException e){
           return  false;
       }
    }
}
