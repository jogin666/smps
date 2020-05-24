package com.zy.smps_message_service.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.UnsupportedEncodingException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Slf4j
class MessageControllerTest {

    private MockMvc mvc;
    @Autowired
    private MessageController controller;
    MockHttpServletRequestBuilder request;


    @BeforeEach
    void setUp() {
        mvc= MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void findAll() throws Exception {
        request= get("/view/list");
        MvcResult mvcResult = mvc.perform(request).andExpect(status().isOk()).andReturn();
        log.info(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void findMessageByCondition() throws Exception {
        request=get("/view/list/condition");
        request.param("title","test");
        //request.param("typeName","新闻");
        MvcResult mvcResult = mvc.perform(request).andExpect(status().isOk()).andReturn();
        log.info(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void findMessageByAccount() throws Exception {
        request=get("/view/list/20161113038");
        MvcResult mvcResult = mvc.perform(request).andExpect(status().isOk()).andReturn();
        log.info(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void findOneById() throws Exception {
        request=get("/view/m00001");
        MvcResult mvcResult = mvc.perform(request).andExpect(status().isOk()).andReturn();
        log.info(mvcResult.getResponse().getContentAsString());
    }

    @Test
    void saveMessage() throws Exception {
        request=post("/view/");
        request.param("title","test5")
                .param("content","开学通知")
                .param("account","20161113032")
                .param("typeId","t00003,t00002");
        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string(equalTo("success")));

    }

    @Test
    void deleteMessage() throws Exception {
        request=delete("/view/518b61725d5c11ea85c800e04c680037");
        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string(equalTo("success")));
    }

    @Test
    void updateMessageInfo() throws Exception {
        request=put("/view/03b180475d5811ea85c800e04c680037");
        request.param("title","test5")
                .param("content","开学通知12")
                .param("typeId","t00003,t00002");
        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string(equalTo("success")));
    }

    @Test
    void updateMessState() throws Exception {
        request=put("/view/state/03b180475d5811ea85c800e04c680037")
                .param("state","1");
        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string(equalTo("success")));
    }

    @Test
    void saveComment() throws Exception {
        request=post("/view/comment")
                .param("messId","82b56a3b5d5b11ea85c800e04c680037")
                .param("commentAccount","20161113032")
                .param("commentName","zy")
                .param("comment","test");
        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string(equalTo("success")));
    }

    @Test
    void deleteCommentById() throws Exception {
        request=delete("/view/comment/afaea5345d5e11ea85c800e04c680037")
                .param("mId","82b56a3b5d5b11ea85c800e04c680037");
        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string(equalTo("success")));
    }
}