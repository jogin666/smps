package com.zy.smps_admin_service.entity;

import lombok.Data;

import java.io.Serializable;


public class PrivilegeEntity implements Serializable {

    private String id;
    private String privilegeName;
    private String demo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrivilegeName() {
        return privilegeName;
    }

    public void setPrivilegeName(String privilegeName) {
        this.privilegeName = privilegeName;
    }

    public String getDemo() {
        return demo;
    }

    public void setDemo(String demo) {
        this.demo = demo;
    }
}
