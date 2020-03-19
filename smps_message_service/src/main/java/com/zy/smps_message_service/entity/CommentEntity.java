package com.zy.smps_message_service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
public class CommentEntity implements Serializable {

    private String id; //id

    private String messId; //消息Id

    private String commentAccount; //评论账号

    private String commentName; //名称

    private String replyAccount; //回复账号

    private String replyName; //名称

    private String comment; //内容

    private Timestamp createDate; //时间
}
