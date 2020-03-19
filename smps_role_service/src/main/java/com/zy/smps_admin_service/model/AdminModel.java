package com.zy.smps_admin_service.model;

import lombok.Data;

@Data
public class AdminModel {

    private String adminId;
    private String adminName;
    private String phone;
    private String email;
    private String roleName;
    private String state;
    private String joinTime;

    public static final int ADMIN_STATE_EFFECTIVE=1;
    public static final int ADMIN_STATE_UNEFFECTIVE=0;

}
