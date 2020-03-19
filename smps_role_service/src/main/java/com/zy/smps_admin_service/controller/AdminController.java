package com.zy.smps_admin_service.controller;

import com.zy.smps_admin_service.entity.AdminEntity;
import com.zy.smps_admin_service.entity.RoleEntity;
import com.zy.smps_admin_service.helper.RequestHelper;
import com.zy.smps_admin_service.model.AdminModel;
import com.zy.smps_admin_service.model.PageParam;
import com.zy.smps_admin_service.model.RequestResultModel;
import com.zy.smps_admin_service.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    private final String SUCCESS="success";
    private final String FAILURE="failure";

//    @GetMapping("/list")
//    public RequestResultModel<AdminModel> findAll(@ModelAttribute PageParam pageParam){
//        List<AdminEntity> admins = adminService.findAll(pageParam);
//    }

    @GetMapping("/list")
    public RequestResultModel<AdminModel> findAll(@ModelAttribute PageParam pageParam){
        ArrayList<AdminModel> adminModels = new ArrayList<>();
        List<AdminEntity> admins = adminService.findAll(pageParam);
        int size=admins.size();
        try{
            int start=(pageParam.getPage()-1)*pageParam.getLimit();
            int end=start+pageParam.getLimit();
            if (end>admins.size()){
                end=admins.size();
            }
            for (int i=start;i<end;i++){
                AdminEntity admin=admins.get(i);
                String roleName=admin.getRoleEntity().getRoleName();
                AdminModel model = new AdminModel();
                model.setRoleName(roleName);
                model.setAdminId(admin.getAccount());
                model.setAdminName(admin.getName());
                model.setEmail(admin.getEmail());
                model.setPhone(admin.getPhone());
                if (admin.getState()==AdminModel.ADMIN_STATE_EFFECTIVE){
                    model.setState("有效");
                }else{
                    model.setState("无效");
                }
                Timestamp createTime = admin.getCreateTime();
                LocalDateTime dateTime = createTime.toLocalDateTime();
                DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String format = dateTime.format(formatter);
                model.setJoinTime(format);
                adminModels.add(model);
            }
            return new RequestResultModel<>(SUCCESS,0,size,adminModels);
        }catch (NullPointerException e){
            return  new RequestResultModel<>(FAILURE,-1,0,null);
        }
    }

//    private AdminModel getAdminModel(String roleName,String adminId,String adminName,String eamil)

    @GetMapping("/list/condition")
    public List<AdminEntity> findAdminByCondition(@ModelAttribute PageParam pageParam){
        return adminService.findAdminByCondition(pageParam);
    }

    @GetMapping("/{account}")
    public AdminEntity findAdminById(@PathVariable("account") String account){
        return adminService.findAdminById(account);
    }

    @PostMapping("/")
    public Map<String,String> saveAdmin(@ModelAttribute AdminEntity adminEntity,
                         @RequestParam("roleId")String roleId, @RequestParam("adminAccount")String adminAccount){
        adminEntity.setPassword("123456");
        adminEntity.setIsAdmin(1);
        int row=adminService.save(adminEntity,roleId,adminAccount);
        return getResult(row);
    }

    @DeleteMapping("/{account}")
    public  Map<String,String> deleteAdmin(@PathVariable("account")String account){
        return getResult(adminService.delete(account));
    }

    @DeleteMapping("/all")
    public Map<String,String> deleteAllByAccount(@RequestParam("adminIds") String adminId){
        String[] adminIds = adminId.split(",");
        return getResult(adminService.deleteAllByAccount(Arrays.asList(adminIds)));
    }

    @PutMapping("/{account}")
    public  Map<String,String> updateAdmin(@PathVariable("account")String account,@ModelAttribute AdminEntity adminEntity,
                              @RequestParam("roleIds")List<String> roleIds, HttpServletRequest request){
        AdminEntity admin= (AdminEntity) request.getSession().getAttribute("user");
        String creator = admin.getAccount();
        adminEntity.setAccount(account);
        int row=adminService.update(adminEntity,roleIds,creator);
        return getResult(row);
    }


    private Map<String,String> getResult(int row){
        if (row!=0){
            return RequestHelper.buildRequestMap(SUCCESS);
        }
        return RequestHelper.buildRequestMap(FAILURE);
    }

}
