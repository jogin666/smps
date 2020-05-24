package com.zy.smps_admin_service.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@Slf4j
class AdminControllerTest {

    private MockMvc mvc;
    private MockHttpServletRequestBuilder request;

    @Autowired
    private AdminController controller;

    @BeforeEach
    void setUp() {
        mvc= MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void findAll() throws Exception {
        request=get("/admin/list");
        MvcResult mvcResult = mvc.perform(request).andExpect(status().isOk()).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        String result=response.getContentAsString();
        log.info(result);
    }

    @Test
    void findAdminByCondition() throws Exception {
        request=get("/admin/list/condition");
        request.param("name","tea");
        MvcResult mvcResult = mvc.perform(request).andExpect(status().isOk()).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        log.info(response.getContentAsString());
    }

    @Test
    void findAdminById() throws Exception {
        request=get("/admin/sadmin02");
        request.param("name","tea");
        MvcResult mvcResult = mvc.perform(request).andExpect(status().isOk()).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        log.info(response.getContentAsString());
    }

    @Test  //测试未完成
    void saveAdmin() throws Exception {
        request=post("/admin/");
        request.param("account","201611113039")
                .param("password","123456")
                .param("name","ygy")
                .param("phone","1762163651")
                .param("age","22")
                .param("gender","male")
                .param("image","")
                .param("email","238451339@qq.com")
                .param("state","1")
                .param("is_admin","1");
        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string(equalTo("success")));
    }

    @Test
    void deleteAdmin() throws Exception {
        request=delete("/admin/20161113039");
        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string(equalTo("success")));
    }

    @Test
    void deleteAllByAccount() throws Exception {
        request=delete("/admin/all");
        request.param("accounts","20161113037,20161113039");
        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string(equalTo("success")));
    }

    @Test  //测试未完成
    void updateAdmin() throws Exception {
        request=put("/admin/20161113039");
        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string(equalTo("success")));
    }
}