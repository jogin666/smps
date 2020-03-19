package com.zy.smps_admin_service.mapper;

import com.zy.smps_admin_service.entity.AdminEntity;
import com.zy.smps_admin_service.entity.AdminRoleEntity;
import com.zy.smps_admin_service.model.PageParam;
import com.zy.smps_admin_service.entity.RoleEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminMapper {

    //查询全部
    List<AdminEntity> findAll(PageParam pageParam);
    //条件查询
    List<AdminEntity> findAdminByCondition(PageParam pageParam);
    //Id 检索
    AdminEntity findAdminById(String account);
    //保存
    int save(AdminEntity adminEntity);
    //更新
    int update(AdminEntity adminEntity);
    //删除
    int delete(String account);
    //批量删除
    int deleteAllByAccount(List<String> accounts);
    //删除管理员所拥有的角色
    int deleteAdminAndRole(String account);
    //保存管理员所拥有的角色
    int saveAdminRole(AdminRoleEntity  adminRoleEntity);
    //查询所有角色
    List<RoleEntity> findAllRole();
}
