package com.zy.smps_message_service.entity;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Data
public class CollectMessageEntity implements Serializable {

    private String messId;
    private String userId;
    private String account;
    private Timestamp collectTime;
    private List<MessageTypeEntity> types;

    //輔助屬性
    private String typeStr;
    private String userName;
    private String messTitle;
    private String collectionTimeStr;
}
