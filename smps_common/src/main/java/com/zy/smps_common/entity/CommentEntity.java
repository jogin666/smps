package com.zy.smps_common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CommentEntity {

    private String id; //id

    private String messId; //消息Id

    private String commentAccount; //评论账号

    private String commentName; //名称

    private String replyAccount; //回复账号

    private String replyName; //名称

    private String comment; //内容

    private LocalDateTime createDate; //时间
}
