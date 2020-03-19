package com.zy.smps_user_service.repository;


import com.zy.smps_user_service.entity.RoleEntity;
import com.zy.smps_user_service.entity.UserEntity;
import com.zy.smps_user_service.model.PageParam;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface UserRepository extends PagingAndSortingRepository<UserEntity,String> {

    @Query("from UserEntity u where u.account=:account")
    UserEntity findUserByAccount(@Param("account")String account);

    @Query("from UserEntity u where u.account like %:account% and u.isAdmin=0")
    List<UserEntity> findUserEntityByAccountLike(@Param("account") String account);

    @Query("from UserEntity u where u.account=:account and u.password=:password")
//    @Query(nativeQuery = true,value = "select * from user where user.account=:#{#account} and user.password=:#{#passord}")
    UserEntity findUserEntityByAccountAndPassword(@Param("account")String account,@Param("password")String password);

    @Modifying
    @Query("delete from UserEntity u where u.account=:#{#account}")
    void deleteByAccount(String account);

    @Modifying
    @Query("delete from UserCollectMessageEntity um where um.account=:account")
    void deleteUserCollectMessageByAccount(@Param("account") String account);

    @Query("from UserEntity u where u.isAdmin=0")
    List<UserEntity> findAll();

    @Query("from UserEntity u where u.className like %:className% and u.account=:account")
    List<UserEntity> findAll(@Param("className")String className,@Param("account") String account);

    @Query("from UserEntity u where u.className like %:className%")
    List<UserEntity> findAllByClassName(@Param("className")String className);

    @Query(nativeQuery = true,value = "select r.name from role r join u_r ur on r.r_id = ur.r_id and ur.u_id=:userId")
    Object findRoleByAccount(@Param("userId")String userId);

}
