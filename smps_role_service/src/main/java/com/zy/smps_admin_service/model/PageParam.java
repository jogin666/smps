package com.zy.smps_admin_service.model;

import lombok.Data;

@Data
public class PageParam {

    public static final int PAGE_NUM=10; //默认业大小

    private int page; // 当前页码
    private int limit; //每页数量
    private Integer start;
    private Integer end;

    private String name;  //角色名称
    private Integer state;
    private String account; //账号

}
