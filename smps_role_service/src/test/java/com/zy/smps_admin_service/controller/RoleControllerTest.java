package com.zy.smps_admin_service.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.CoreMatchers.equalTo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Slf4j
@SpringBootTest
class RoleControllerTest {


    private MockMvc mockMvc;
    private MockHttpServletRequestBuilder request;

    @Autowired
    private RoleController controller;

    @BeforeEach
    void setUp() {
        mockMvc=MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void findRoleById() throws Exception {
        request=get("/role/r00001");
        MvcResult mvcResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        log.info(result);
    }

    @Test
    void findAll() throws Exception {
        request=get("/role/list");
        MvcResult mvcResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        log.info(result);
    }

    @Test
    void findRoleByCondition() throws Exception {
        request=get("/role/list/condition");
        request.param("name","管理员");
       // request.param("state","1");
        //request.param("roleNumber","2");
        MvcResult mvcResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        log.info(result);
    }

    @Test
    void saveRole() throws Exception {
        request=post("/role/")
                .param("roleName","普通管理员")
                .param("state","1")
                .param("number","10")
                .param("privilegeIds","p00002,p00003");
        mockMvc.perform(request).andExpect(status().isOk()).andExpect(content().string(equalTo("success")));
    }

    @Test
    void updateRole() throws Exception {
        request=put("/role/r00005")
                .param("roleName","普通管理员")
                .param("state","0")
                .param("number","5")
                .param("privilegeIds","p00001,p00003");
        mockMvc.perform(request).andExpect(status().isOk()).andExpect(content().string(equalTo("success")));
    }

    @Test
    void deleteRoleById() throws Exception {
        request=delete("/role/r00005");
        mockMvc.perform(request).andExpect(status().isOk()).andExpect(content().string(equalTo("success")));
    }

    @Test
    void deleteAllByRoleIds() throws Exception {
        request=delete("/role/all")
                .param("roleIds","r00005");
        mockMvc.perform(request).andExpect(status().isOk()).andExpect(content().string(equalTo("success")));
    }

    @Test
    void findAllPrivilege() throws Exception {
        request=get("/role/privilege/list");
        MvcResult mvcResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        log.info(mvcResult.getResponse().getContentAsString());
    }
}