package com.zy.smps_admin_service.service.impl;

import com.zy.smps_admin_service.entity.PrivilegeEntity;
import com.zy.smps_admin_service.entity.RoleEntity;
import com.zy.smps_admin_service.mapper.RoleMapper;
import com.zy.smps_admin_service.page.PageBuilder;
import com.zy.smps_admin_service.model.PageParam;
import com.zy.smps_admin_service.page.PageResult;
import com.zy.smps_admin_service.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<RoleEntity> findAll(PageParam pageParam) {
        List<RoleEntity> roles = roleMapper.findAll(pageParam);
        return roles;
//        PageResult<RoleEntity> pageResult = PageBuilder.getPageResult(pageParam, roles);
//        return pageResult.getContent();
    }

    @Override
    public List<RoleEntity> findRoleByCondition(PageParam pageParam) {
        List<RoleEntity> roles = roleMapper.findRoleByCondition(pageParam);
        PageResult<RoleEntity> pageResult = PageBuilder.getPageResult(pageParam, roles);
        return pageResult.getContent();
    }

    @Override
    public RoleEntity findRoleById(String roleId) {
        return roleMapper.findRoleById(roleId);
    }

    @Override
    public int save(RoleEntity roleEntity,List<String> privilegeIds) {
        int row=roleMapper.saveRole(roleEntity);
        String roleId = roleEntity.getRoleId();
        for (String privilegeId:privilegeIds){
            roleMapper.saveRolePrivilege(roleId,privilegeId);
        }
        return row;
    }

    @Override
    public int delete(String roleId) {
        roleMapper.deleteRolePrivilege(roleId);
        return roleMapper.deleteRole(roleId);
    }

    @Override
    public int deleteAllByRoleIds(List<String> roleIds) {
        for (String roleId : roleIds){
            roleMapper.deleteRolePrivilege(roleId);
        }
        return roleMapper.deleteAllByRoleIds(roleIds);
    }

    @Override
    public int update(RoleEntity roleEntity,List<String> privilegeIds) {
        String roleId = roleEntity.getRoleId();
        roleMapper.deleteRolePrivilege(roleId);
        for (String privilegeId:privilegeIds){
            roleMapper.saveRolePrivilege(roleId,privilegeId);
        }
        return roleMapper.updateRole(roleEntity);
    }

    @Override
    public List<PrivilegeEntity> findAllPrivilege() {
        return roleMapper.findAllPrivilege();
    }
}
