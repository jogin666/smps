package com.zy.smps_user_service.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.File;
import java.io.FileInputStream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@Slf4j
class UserControllerTest {

    private MockMvc mvc;
    private MockHttpServletRequestBuilder request;

    @Autowired
    private UserController controller;

    @BeforeEach
    void setUp() {
        mvc= MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void findUserByAccountAndPass() throws Exception {
        request=post("/user/login")
                .param("account","20161113032")
                .param("password","166599");
        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string(equalTo("exit")));
    }

    @Test
    void findAll() throws Exception {
        request=get("/user/list");
        MvcResult mvcResult = mvc.perform(request).andExpect(status().isOk()).andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        log.info(result);
    }

    @Test
    void findUserByAccount() throws Exception {
        request=get("/user/list/20161113032");
        MvcResult mvcResult = mvc.perform(request).andExpect(status().isOk()).andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        log.info(result);
    }

    @Test
    void updateUserInfo() {
    }

    @Test
    void deleteUserByAccount() throws Exception {
        request=delete("/user/delete/20161113039");
        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string(equalTo("success")));
    }

    @Test
    void deleteAllByAccount() throws Exception {
        request=delete("/user/delete")
                .param("accounts","20161113037,20161113039");
        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string(equalTo("success")));
    }

    @Test
    void save() throws Exception {
        request=post("/user/save");
        request.param("account","20161113039")
                .param("password","123456")
                .param("name","ygy")
                .param("phone","1762163651")
                .param("age","22")
                .param("gender","male")
                .param("image","")
                .param("email","238451339@qq.com")
                .param("state","1")
                .param("is_admin","0");
        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string(equalTo("success")));
    }

    @Test
    void saveAll() throws Exception {
        ResultActions a = mvc.perform(fileUpload("/user/save/file").
                file(new MockMultipartFile("userFile", "person.xlsx", "application/ms-excel",
                        new FileInputStream(new File("C:\\Users\\99447\\Desktop\\orm\\smps\\smps_user_service\\src\\main\\resources\\person.xlsx")))));
        a.andExpect(status().isOk()).andExpect(content().string(equalTo("success")));

    }

}