package com.zy.smps_admin_service.controller;

import com.zy.smps_admin_service.entity.*;
import com.zy.smps_admin_service.helper.RequestHelper;
import com.zy.smps_admin_service.model.PageParam;
import com.zy.smps_admin_service.model.RequestResultModel;
import com.zy.smps_admin_service.model.RoleModel;
import com.zy.smps_admin_service.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;

@RestController
@RequestMapping("/role")
@Slf4j
public class RoleController {

    @Autowired
    private RoleService roleService;

    private final String SUCCESS="success";
    private final String FAILURE="failure";

    private final static String EFFECTIVE="有效";
    private final static String UNEFFECTIVE="无效";

    @GetMapping("/{roleId}") //查询单个
    public RoleEntity findRoleById(@PathVariable("roleId")String roleId){
        return roleService.findRoleById(roleId);
    }

    @GetMapping("/list") //查询全部
    public RequestResultModel<RoleModel> findAll(@ModelAttribute PageParam pageParam){
        int start=(pageParam.getPage()-1)*pageParam.getLimit();
        pageParam.setStart(start);
        List<RoleEntity> roles = roleService.findAll(pageParam);
        return handlerResult(roles,pageParam);
    }

    @GetMapping("list/condition") //条件查询
    public RequestResultModel<RoleModel> findRoleByCondition(@ModelAttribute PageParam pageParam){
        List<RoleEntity> roles = roleService.findRoleByCondition(pageParam);
        return handlerResult(roles,pageParam);
    }

    private RequestResultModel<RoleModel> handlerResult(List<RoleEntity> roles,PageParam pageParam){
        ArrayList<RoleModel> roleModels = new ArrayList<>();
        RequestResultModel<RoleModel> requestResult=null;
        try{
            int start=(pageParam.getPage()-1)*pageParam.getLimit();
            int end=start+pageParam.getLimit();
            if (end>roles.size()){
                end=roles.size();
            }
            for (int i=start;i<end;i++) {
                RoleEntity role=roles.get(i);
                StringBuffer stringBuffer = new StringBuffer();
                Iterator<PrivilegeEntity> iterator = role.getRolePrivileges().iterator();
                while (iterator.hasNext()){
                    if (stringBuffer.length()>0){
                        stringBuffer.append("、");
                    }
                    String privilegeName = iterator.next().getPrivilegeName();
                    stringBuffer.append(privilegeName);
                }
                RoleModel roleModel = new RoleModel();
                if (role.getState()==RoleEntity.ROLE_STATE_EFFECTIVE){ //状态
                    roleModel.setState(EFFECTIVE);
                }else{
                    roleModel.setState(UNEFFECTIVE);
                }
                roleModel.setRoleName(role.getRoleName());
                roleModel.setRoleId(role.getRoleId());
                roleModel.setPrivilege(stringBuffer.toString());
                roleModels.add(roleModel);
            }
            requestResult=new RequestResultModel<>("",0,roles.size(),roleModels);
        }catch (NullPointerException e){
            requestResult=new RequestResultModel<>("数据为空",0,0,null);
        }
        return requestResult;
    }

    @PostMapping("/")  //新增一个 role
    public Map<String,String> saveRole(@ModelAttribute RoleEntity roleEntity,
                           @RequestParam("privileges")String privilegeIds){
        String[] ps = privilegeIds.split(",");
        Timestamp createDate = new Timestamp(new Date().getTime());
        roleEntity.setCreateDate(createDate);
        return getResult(roleService.save(roleEntity, Arrays.asList(ps)));
    }

    @PutMapping("/{roleId}") //更新 一个 role
    public Map<String,String> updateRole(@PathVariable("roleId")String roleId,
                             @ModelAttribute RoleEntity roleEntity,
                             @RequestParam("privilegeIds")List<String> privilegeIds){
        roleEntity.setRoleId(roleId);
        return getResult(roleService.update(roleEntity,privilegeIds));
    }

    @DeleteMapping("/{roleId}") //删除一个 role
    public Map<String,String> deleteRoleById(@PathVariable("roleId") String roleId){
        return getResult(roleService.delete(roleId));
    }

    @DeleteMapping("/all")  //批量删除
    public Map<String,String> deleteAllByRoleIds(@RequestParam("roleIds")String roleIds){
        String[] ids = roleIds.split(",");
        List<String> roleIdList = Arrays.asList(ids);
        return getResult(roleService.deleteAllByRoleIds(roleIdList));
    }

    @GetMapping("/privilege/list")
    public List<PrivilegeEntity> findAllPrivilege(){
        return roleService.findAllPrivilege();
    }

    private Map<String,String> getResult(int row){
        if (row!=0){
            return RequestHelper.buildRequestMap(SUCCESS);
        }
        return RequestHelper.buildRequestMap(FAILURE);
    }
}
