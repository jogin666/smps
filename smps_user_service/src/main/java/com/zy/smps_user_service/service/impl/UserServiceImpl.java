package com.zy.smps_user_service.service.impl;

import com.zy.smps_user_service.entity.UserEntity;
import com.zy.smps_user_service.model.PageParam;
import com.zy.smps_user_service.repository.UserRepository;
import com.zy.smps_user_service.service.UserService;
import com.zy.smps_user_service.util.ExcelUtil;
import com.zy.smps_user_service.util.RedisKeyHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.util.List;

@Transactional
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    private final String SUCCESS="success";
    private final String FAILURE="failure";


    @Override  //查询单个
    public UserEntity findUserByAccount(String account) {
        UserEntity userEntity = userRepository.findUserByAccount(account);
        return userEntity;
    }

    @Override //根据账号和密码查询
    public UserEntity exit(String account, String password) {
        log.info(account);
        log.info(password);
        UserEntity userEntity = userRepository.findUserEntityByAccountAndPassword(account, password);
        if (userEntity!=null){
            String userId = userEntity.getUserId();
            String str =(String) userRepository.findRoleByAccount(userId);
            userEntity.setRoleName(str);
        }
        return userEntity;
    }

    @Override  //查询全部
    public List<UserEntity> findUserAll(PageParam pageParam) {
        List<UserEntity> users;
        pageParam.setPage(0);
        String key = String.valueOf(pageParam.hashCode());
        if (redisTemplate.hasKey(key)){
            return (List<UserEntity>) redisTemplate.opsForSet().pop(key);
        }else {
            String className = pageParam.getClassName();
            String account = pageParam.getAccount();
            if (!StringUtils.isEmpty(className)) {
                users = userRepository.findAllByClassName(className);
            } else if (!StringUtils.isEmpty(account)) {
                users = userRepository.findUserEntityByAccountLike(account);
            } else if (!StringUtils.isEmpty(account) && !StringUtils.isEmpty(className)) {
                users = userRepository.findAll(className, account);
            } else {
                users = userRepository.findAll();
            }
            redisTemplate.opsForSet().add(key,users);
            RedisKeyHelper.keySet().add(key);
            return users;
        }
    }

    @Override //更新
    public String updateUserInfo(UserEntity userEntity) {
        UserEntity user = userRepository.save(userEntity);
        if (user.equals(userEntity)) {
            redisTemplate.delete(RedisKeyHelper.keySet());
            return SUCCESS;
        }
        return FAILURE;
    }

    @Override //删除单个
    public String deleteUserByAccount(String account) {
        userRepository.deleteByAccount(account);
        userRepository.deleteByAccount(account);
        redisTemplate.delete(RedisKeyHelper.keySet());
        return SUCCESS;
    }

    @Override //删除全部
    public String deleteUserByAccounts(List<String> accountList) {
        for (String account:accountList){
            userRepository.deleteUserCollectMessageByAccount(account);
            userRepository.deleteByAccount(account);
        }
        redisTemplate.delete(RedisKeyHelper.keySet());
        return SUCCESS;
    }

    @Override //保存单个
    public String save(UserEntity userEntity) {
        UserEntity user = userRepository.save(userEntity);
        if (user.equals(userEntity)) {
            return SUCCESS;
        }
        redisTemplate.delete(RedisKeyHelper.keySet());
        return FAILURE;
    }

    @Override //保存全部
    public String saveAll(InputStream stream, String fileName) {
        List<UserEntity> users = ExcelUtil.readExcel(stream,fileName);
        if (users!=null && users.size()>0){
            users.forEach(System.out::println);
            for (UserEntity userEntity:users){
                userEntity.setIsAdmin(0);
                userEntity.setState(1);
                userEntity.setPassword("123456");
                userRepository.save(userEntity);
            }
            redisTemplate.delete(RedisKeyHelper.keySet());
            return SUCCESS;
        }
        return FAILURE;
    }
}
