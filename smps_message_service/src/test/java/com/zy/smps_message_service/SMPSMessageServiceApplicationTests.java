package com.zy.smps_message_service;

import com.zy.smps_message_service.entity.MessageEntity;
import com.zy.smps_message_service.entity.PageParam;
import com.zy.smps_message_service.service.MessageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.Date;

@SpringBootTest
class SMPSMessageServiceApplicationTests {

    @Autowired
    private MessageService messageService;

    @Test
    void contextLoads() {
    }

    @Test
    void testSave(){
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setAccount("20161113033");  //发布者账号
        messageEntity.setTitle("测试"); //标题
        messageEntity.setContent("123456852323323223"); //内容
        messageEntity.setCreateTime(new Timestamp(new Date().getTime())); //时间
        messageEntity.setMessState(MessageEntity.MESSAGE_STATE_CHECK); //状态
        messageEntity.setCommentNum(0); //评论量
        messageEntity.setViewNum(0); //阅读量
        messageEntity.setAuthority(MessageEntity.MESSAGE_NOT_AUTHORITY); //默认非官方发布
        messageEntity.setTypeId(new String[]{"t00001"});
        messageService.saveMessage(messageEntity);
    }

    @Test
    void lombokHashCode(){
        PageParam p1 = new PageParam();
        p1.setAccount("20161113032");
        PageParam p2 = new PageParam();
        p2.setAccount("20161113032");
        System.out.println(p1.hashCode());
        System.out.println(p2.hashCode());
    }
}
