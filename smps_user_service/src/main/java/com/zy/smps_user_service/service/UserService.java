package com.zy.smps_user_service.service;

import com.zy.smps_user_service.entity.RoleEntity;
import com.zy.smps_user_service.entity.UserEntity;
import com.zy.smps_user_service.model.PageParam;

import java.io.InputStream;
import java.util.List;

public interface UserService {

    UserEntity findUserByAccount(String account);

    UserEntity exit(String account,String password);

    List<UserEntity> findUserAll(PageParam param);

    String updateUserInfo(UserEntity userEntity);

    String deleteUserByAccount(String account);

    String deleteUserByAccounts(List<String> accountList);

    String save(UserEntity userEntity);

    String saveAll(InputStream stream,String fileName);
}
