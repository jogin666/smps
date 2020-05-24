package com.zy.smps_message_service.service.impl;

import com.zy.smps_message_service.entity.CollectMessageEntity;
import com.zy.smps_message_service.entity.MessageEntity;
import com.zy.smps_message_service.entity.MessageTypeEntity;
import com.zy.smps_message_service.entity.PageParam;
import com.zy.smps_message_service.service.MessageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MessageServiceImplTest {

    @Autowired
    private MessageService messageService;
    @Test
    void findAll() {
        PageParam pageParam = new PageParam();
        pageParam.setTitle("n");
        pageParam.setTypeName("新闻");
        List<MessageEntity> mess = messageService.findNotPublishMess(pageParam);
        for(MessageEntity m:mess){
            String title=m.getTitle();
            List<MessageTypeEntity> types = m.getTypes();
            for (MessageTypeEntity type:types){
                System.out.println("title: "+title+", type: "+type.getTypeName());
            }
        }

    }

    @Test
    void findMessageByTitleLike() {
        PageParam pageParam = new PageParam();
        pageParam.setAccount("20161113033");
        pageParam.setTypeId("t00001");
        List<CollectMessageEntity> collectMess = messageService.findUserCollectMess(pageParam);
        for(CollectMessageEntity entity:collectMess){
            List<MessageTypeEntity> types = entity.getTypes();
            for (MessageTypeEntity typeEntity:types){
                System.out.println(typeEntity.getTypeName());
            }
            System.out.println(entity);
        }

    }

    @Test
    void findMessageByType() {
    }

    @Test
    void updateMessageCommentNum() {
    }

    @Test
    void findOneById() {
    }

    @Test
    void findMessageAndPaging() {
    }

    @Test
    void saveMessage() {
    }

    @Test
    void updateMessageState() {
    }

    @Test
    void findMessageByAccount() {
    }

    @Test
    void updateMessage() {
    }

    @Test
    void deleteMessage() {
    }

    @Test
    void saveComment() {
    }

    @Test
    void deleteCommentById() {
    }

    @Test
    void deleteCommentBymId() {
    }

    @Test
    void findUserCollectMess() {
    }
}