package com.zy.smps_admin_service.service.impl;

import com.zy.smps_admin_service.entity.AdminEntity;
import com.zy.smps_admin_service.entity.AdminRoleEntity;
import com.zy.smps_admin_service.mapper.AdminMapper;
import com.zy.smps_admin_service.page.PageBuilder;
import com.zy.smps_admin_service.model.PageParam;
import com.zy.smps_admin_service.page.PageResult;
import com.zy.smps_admin_service.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Transactional
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public List<AdminEntity> findAll() {
        return null;
    }

    @Override //查询全部
    public List<AdminEntity> findAll(PageParam pageParam) {
        List<AdminEntity> admins = adminMapper.findAdminByCondition(pageParam);
        PageResult<AdminEntity> pageResult = PageBuilder.getPageResult(pageParam, admins);
        return pageResult.getContent();
    }

    @Override  //条件查询
    public List<AdminEntity> findAdminByCondition(PageParam pageParam){
        List<AdminEntity> admins = adminMapper.findAdminByCondition(pageParam);
        return admins;
//        PageResult<AdminEntity> pageResult = PageBuilder.getPageResult(pageParam, admins);
//        return pageResult.getContent();
    }

    @Override
    public AdminEntity findAdminById(String account) {
        return adminMapper.findAdminById(account);
    }

    @Override //新增管理员
    public int save(AdminEntity adminEntity,String roleId,String creator) {
        int row = adminMapper.save(adminEntity);
        saveAdminAndRole(adminEntity,roleId,creator);
        return row;
    }

    @Override //删除单个
    public int delete(String account) {
        adminMapper.deleteAdminAndRole(account);//删除拥有的角色
        return adminMapper.delete(account);
    }

    @Override //批量删除
    public int deleteAllByAccount(List<String> accounts) {
        for (String account:accounts) {  //删除拥有的角色
            adminMapper.deleteAdminAndRole(account);
        }
        return adminMapper.deleteAllByAccount(accounts);
    }

    @Override //更新
    public int update(AdminEntity adminEntity,String creator) {
        adminMapper.deleteAdminAndRole(adminEntity.getAccount()); //删除拥有的角色
        saveAdminAndRole(adminEntity,adminEntity.getAdminId(),creator);
        return adminMapper.update(adminEntity);
    }

    private int saveAdminAndRole(AdminEntity adminEntity,String roleId,String creator){
        String account = adminEntity.getAccount();
        String adminId = adminEntity.getUId();
        int row=0;
        AdminRoleEntity entity = new AdminRoleEntity(null, adminId, account,new Timestamp(new Date().getTime()), creator);
        entity.setRoleId(roleId);
        adminMapper.saveAdminRole(entity);
        return row;
    }
}
