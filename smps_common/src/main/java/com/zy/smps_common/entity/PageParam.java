package com.zy.smps_common.entity;

import lombok.Data;

@Data
public class PageParam {

    //common pageparam
    public static final int PAGE_NUM=10; //默认业大小
    private int pageSize; // 当前页码
    private int pageNumber; //每页数量
    private String account; //账号

    //role service pageparam

    private String name;  //角色名称
    private Integer roleNumber; //角色编号
    private Integer state; //角色状态

    //password service pageparam
    private String receiver; // 验证码接受者
    private String code;		//验证码
    private String oldPassword;  //旧密码
    private String newPassword; //新密码号

    //view service pageparam
    private String typeName;  //消息类型
    private String title;  //标题
}
