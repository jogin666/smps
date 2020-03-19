package com.zy.smps_admin_service.entity;


import java.io.Serializable;
import java.sql.Timestamp;


public class AdminRoleEntity implements Serializable {

    private String roleId;
    private String adminId;
    private String account;
    private Timestamp createTime;
    private String creator;

    public AdminRoleEntity(String roleId, String adminId, String account, Timestamp createTime, String creator) {
        this.roleId = roleId;
        this.adminId = adminId;
        this.account = account;
        this.createTime = createTime;
        this.creator = creator;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
}
