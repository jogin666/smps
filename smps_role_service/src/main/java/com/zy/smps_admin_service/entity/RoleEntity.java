package com.zy.smps_admin_service.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class RoleEntity implements Serializable {

    private String roleId;
    private String roleName;
    private String createBy;
    private Integer state;
    private Timestamp createDate;
    private List<PrivilegeEntity> rolePrivileges;

    public static final int ROLE_STATE_EFFECTIVE=1;
    public static final int ROLE_STATE_NOT_EFFECTIVE=0;


    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public List<PrivilegeEntity> getRolePrivileges() {
        return rolePrivileges;
    }

    public void setRolePrivileges(List<PrivilegeEntity> rolePrivileges) {
        this.rolePrivileges = rolePrivileges;
    }

}
