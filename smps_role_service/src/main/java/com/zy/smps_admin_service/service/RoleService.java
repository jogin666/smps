package com.zy.smps_admin_service.service;

import com.zy.smps_admin_service.entity.PrivilegeEntity;
import com.zy.smps_admin_service.entity.RoleEntity;
import com.zy.smps_admin_service.model.PageParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleService {

    //查询全部
    List<RoleEntity> findAll(PageParam pageParam);
    //条件查询
    List<RoleEntity> findRoleByCondition(PageParam pageParam);
    //单个查询
    RoleEntity findRoleById(String roleId);
    //保存
    int save(RoleEntity roleEntity,List<String> privilegeIds);
    //删除
    int delete(String roleId);
    //批量删除
    int deleteAllByRoleIds(List<String> roleIds);
    //更新
    int update(RoleEntity roleEntity,List<String> privilegeIds);
    //查询系统的权限
    List<PrivilegeEntity> findAllPrivilege();
}
