package com.zy.smps_password_service.reposity;

import com.zy.smps_password_service.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface PasswordRepository extends CrudRepository<UserEntity,String> {

    UserEntity findUserEntityByAccount(String account);

    UserEntity findUserEntityByAccountAndPhone(String account,String phone);

    UserEntity findUserEntityByAccountAndEmail(String account,String email);
}
