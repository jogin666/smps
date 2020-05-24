package com.zy.smps_admin_service.service;

import com.zy.smps_admin_service.entity.AdminEntity;
import com.zy.smps_admin_service.model.PageParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminService {


    List<AdminEntity> findAll();
    //查询全部
    List<AdminEntity> findAll(PageParam pageParam);
    //条件查询
    List<AdminEntity> findAdminByCondition(PageParam pageParam);
    //Id 检索
    AdminEntity findAdminById(String account);
    //保存
    int save(AdminEntity adminEntity,String roleId,String creator);
    //删除
    int delete(String account);
    //批量删除
    int deleteAllByAccount(List<String> accounts);
    //跟新
    int update(AdminEntity adminEntity,String creator);
}
