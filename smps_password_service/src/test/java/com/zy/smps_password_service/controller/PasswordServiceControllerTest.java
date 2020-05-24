package com.zy.smps_password_service.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Scanner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class PasswordServiceControllerTest {

    private MockMvc mvc;
    private MockHttpServletRequestBuilder request;
    @Autowired
    private PasswordServiceController controller;

    @BeforeEach
    public void setUp(){
        mvc= MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void sendSMS() throws Exception {
        request=post("/ps/code/sms");
        request.param("phone","17621603654");
        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string(equalTo("success")));
        Scanner scanner = new Scanner(System.in);
        String code=scanner.next();
        String password=scanner.next();
        String receiver=scanner.next();
        String oldPassword=scanner.next();
        request=put("/ps/20161113032");
        request.param("newPassword",password)
                .param("code",code)
                .param("oldPassword",oldPassword)
                .param("receiver",receiver);
        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string(equalTo("success")));
    }

    @Test
    void sendEmail() throws Exception {
        //找回密码
        request= post("/ps/code/sml");
        request.param("email","2384513772@qq.com");
        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string(equalTo("success")));

        request=put("/ps/20161113032");
        request.param("newPassword","123456")
                .param("code","1234")
                .param("receiver","2384513772@qq.com");
        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string(equalTo("success")));
    }

    @Test
    void changePassword() throws Exception {

    }
}