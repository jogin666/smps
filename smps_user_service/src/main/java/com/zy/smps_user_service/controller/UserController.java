package com.zy.smps_user_service.controller;

import com.alibaba.fastjson.JSON;
import com.zy.smps_user_service.entity.UserEntity;
import com.zy.smps_user_service.model.PageParam;
import com.zy.smps_user_service.model.RequestResultModel;
import com.zy.smps_user_service.service.UserService;

import com.zy.smps_user_service.util.ExcelUtil;
import com.zy.smps_user_service.util.RequestResultBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.*;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    private final String SUCCESS="success";
    private final String FAILURE="failure";

    @PostMapping("/login")  //查询学生是否存在
    public Map<String,Object> findUserByAccountAndPass(@ModelAttribute UserEntity userEntity){
        log.info("account: "+userEntity.getAccount());
        log.info("password: "+userEntity.getPassword());
        UserEntity user = userService.exit(userEntity.getAccount(), userEntity.getPassword());
        HashMap<String, Object> map = new HashMap<>();
        if (user!=null){
            map.put("user",user);
            map.put("result",SUCCESS);
            log.info("result: "+SUCCESS);
        }else{
            log.info("result: "+FAILURE);
            map.put("result:",FAILURE);
        }
        return map;
    }

    @GetMapping("/list")  //查询全部学生（默认已有分页信息）
    public RequestResultModel<UserEntity> findAll(@ModelAttribute PageParam pageParam){
        int start=(pageParam.getPage()-1)*pageParam.getLimit();
        int end=start+pageParam.getLimit();
        List<UserEntity> users = userService.findUserAll(pageParam);
        if (users!=null){
            if (end>users.size()){
                end=users.size();
            }
            return new RequestResultModel<>("success", 0, users.size(), users.subList(start,end));
        }
        return new RequestResultModel<>("请求失败", -1, -1, null);

    }

    @GetMapping("/list/{account}") //账号检索
    public UserEntity findUserByAccount(@PathVariable("account")String account){
        return userService.findUserByAccount(account);
    }

    @PutMapping("/{account}")//更新
    public Map<String,String> updateUserInfo(@PathVariable("account")String account, @ModelAttribute UserEntity userEntity,
                                 @RequestParam("headImg")MultipartFile headImg, HttpServletRequest request){
        UserEntity user = userService.findUserByAccount(account);
        String result="";
        if (saveHeadImg(user,headImg,request)){
            user.setEmail(userEntity.getEmail());
            user.setPhone(userEntity.getPhone());
            result =userService.updateUserInfo(user);
        }
        Map<String, String> map = RequestResultBuilder.buildResult(result);
        map.put("phone",user.getPhone());
        map.put("email",user.getEmail());
        map.put("image",user.getImage());
        return map;
    }

    @PutMapping("list/{account}")
    public Map<String,String> updateUserInfo(@PathVariable("account")String account,@ModelAttribute UserEntity userEntity){
        UserEntity user = userService.findUserByAccount(account);
        userEntity.setImage(user.getImage());
        userEntity.setUserId(user.getUserId());
        userEntity.setPassword(user.getPassword());
        userEntity.setIsAdmin(user.getIsAdmin());
        String result=userService.updateUserInfo(userEntity);
        return RequestResultBuilder.buildResult(result);
    }

    @DeleteMapping("/delete/{account}") //删除单个用户
    public Map<String,String> deleteUserByAccount(@PathVariable("account")String account){
         String result=userService.deleteUserByAccount(account);
        return RequestResultBuilder.buildResult(result);
    }

    @DeleteMapping("/delete") //批量删除
    public Map<String,String> deleteAllByAccount(@Param("accounts") String accounts){
        String result="";
        if (accounts!=null && accounts.length()!=0){
            String[] accountArray = accounts.split(",");
            List<String> accountList = Arrays.asList(accountArray);
            result =userService.deleteUserByAccounts(accountList);
        }
        return RequestResultBuilder.buildResult(result);
    }

    @PostMapping("/") //保存单个学生
    public Map<String,String> save(@ModelAttribute UserEntity userEntity){
        userEntity.setPassword("123456");
        userEntity.setState(1);
        userEntity.setIsAdmin(0);
        UserEntity user = userService.findUserByAccount(userEntity.getAccount());
        if (user!=null){
            Map<String, String> map = RequestResultBuilder.buildResult(FAILURE);
            map.put("msg","账号已存在,添加失败！");
            return map;
        }
        String result=userService.save(userEntity);
        return RequestResultBuilder.buildResult(result);
    }

    @PostMapping("/upload") //批量保存
    public Map<String,String> saveAll(@RequestParam("file") MultipartFile userFile) throws IOException {
        String result="";
        if (userFile!=null){
            String userExcelFileName=userFile.getOriginalFilename();
            InputStream stream=userFile.getInputStream();
            if (userExcelFileName.matches("^.+\\.(?i)((xls)|(xlsx))$")){
                result=userService.saveAll(stream,userExcelFileName);
            }
        }
        return RequestResultBuilder.buildResult(result);
    }

    //保存用户头像
    private  boolean saveHeadImg(UserEntity user, MultipartFile headImg, HttpServletRequest request){
        if(user!=null && headImg!=null){
            try {
                //获取绝对路径
                String path = ResourceUtils.getURL("classpath:").getPath()+"static/upload/";
                //文件名
                String fileName=headImg.getOriginalFilename();
                //文件重命名
                String fileReName = UUID.randomUUID().toString().replaceAll("-", "") + fileName;
                //复制文件
                String filePath=path+fileReName;
                IOUtils.copy(headImg.getInputStream(),new FileOutputStream(filePath));
                user.setImage("http://127.0.0.1:9007/upload/" +fileReName);
                return true;
            }catch (IOException e){
                throw new RuntimeException("无法获取用户的头像");
            }
        }
        return false;
    }

    @GetMapping("/download")
    public void downloadFailedUsingJson(@ModelAttribute PageParam param, HttpServletResponse response) throws IOException {
        // 这里注意 有同学反应使用swagger会导致各种问题，请直接用浏览器或者用 postman'
        Map<String, String> map = new HashMap<>();
        try {
            List<UserEntity> users = userService.findUserAll(param);
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("SMPS 用户信息", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            // 这里需要设置不关闭流
            ExcelUtil.writeExcel(response.getOutputStream(),users);
        } catch (Exception e) {
            // 重置response
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            map.put("result", FAILURE);
            map.put("msg", "下载文件失败" + e.getMessage());
            response.getWriter().println(JSON.toJSONString(map));
        }

    }
}
