package com.zy.smps_message_service.entity;

import lombok.Data;

@Data
public class PageParam {

    public static final int PAGE_NUM=10;

    private String typeName;
    private String title;
    private String state;
    private int page;
    private int limit;
    private String account;
    private String typeId;

    private int start;
}
