package com.zy.smps_user_service.util;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.zy.smps_user_service.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class PersonExcelListener extends AnalysisEventListener<UserEntity> {

    private List<UserEntity> users=new ArrayList<>();

    @Override //逐行读取 excel 表格的数据
    public void invoke(UserEntity userEntity, AnalysisContext analysisContext) {
        log.info("解释一条数据{}",userEntity.toString());
        users.add(userEntity);
    }

    @Override  //读取完 excel 表格之后的操作
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("解析数据完成，开始保存数据！");
        showPersonInfo();
    }

    private void showPersonInfo(){
        for (UserEntity user:users){
            log.info("保存数据 {}",user);
        }
    }

    public List<UserEntity>  getUsers(){
        return this.users;
    }
}
