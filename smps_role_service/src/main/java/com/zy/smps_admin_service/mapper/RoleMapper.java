package com.zy.smps_admin_service.mapper;

import com.zy.smps_admin_service.entity.PrivilegeEntity;
import com.zy.smps_admin_service.entity.RoleEntity;
import com.zy.smps_admin_service.model.PageParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper {

    //查询全部
    List<RoleEntity> findAll(PageParam pageParam);
    //条件查询
    List<RoleEntity> findRoleByCondition(PageParam pageParam);
    //单个查询
    RoleEntity findRoleById(String roleId);
    //保存
    int saveRole(RoleEntity roleEntity);
    //删除
    int deleteRole(String roleId);
    //批量删除
    int deleteAllByRoleIds(List<String> roleIds);
    //更新角色
    int updateRole(RoleEntity roleEntity);
    //删除角色的权限
    int deleteRolePrivilege(String roleId);
    //保存角色权限
    int saveRolePrivilege(String roleId,String privilegeId);
    //查询系统的所有权限
    List<PrivilegeEntity> findAllPrivilege();
    //获取最大的role nunmber
    int getMaxRoleNumber();
}
