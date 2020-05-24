package com.zy.smps_common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AdminRoleEntity {

    private String roleId;
    private String adminId;
    private String account;
    private LocalDateTime createTime;
    private String creator;
}
