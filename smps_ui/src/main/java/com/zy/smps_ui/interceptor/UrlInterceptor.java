package com.zy.smps_ui.interceptor;

import com.zy.smps_ui.data.DataMap;
import com.zy.smps_ui.model.UserModel;
import com.zy.smps_ui.util.IPAddressHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

@Component
public class UrlInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
       try {
           UserModel user = (UserModel) request.getSession().getAttribute("user");
           if (user==null){
               String ipAddress = IPAddressHelper.getIPAddress(request);
               user= (UserModel) redisTemplate.opsForHash().get("user",ipAddress);
           }
           if (user==null) {
               response.sendRedirect(request.getContextPath()+"/");  //转到登录页面
               return false;
           }
           String uri = request.getRequestURI();
           if (uri.contains("/view/sys/") && (user.getIsAdmin()!=1)){
               response.setContentType("text/html; charset=UTF-8"); //转码
               PrintWriter out = response.getWriter();
               out.flush();
               String script="<script> " +
                       "alert('您不是后台管理人员，没有访问权限');" +
                       "history.back()" +
                       ";</script>";
               out.println(script);
//               response.getWriter().write("<script> alert(\"！\")");
//               response.flushBuffer();
               return false;
           }
           return true;
       }catch (NullPointerException e){
           return  false;
       }
    }
}
