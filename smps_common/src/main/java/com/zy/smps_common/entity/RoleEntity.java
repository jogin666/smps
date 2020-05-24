package com.zy.smps_common.entity;

import lombok.Data;

import java.util.List;

@Data
public class RoleEntity {

    private String roleId;
    private int number;
    private String roleName;
    private String state;
    private List<PrivilegeEntity> rolePrivileges;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
