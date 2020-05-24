package com.zy.smps_user_service;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.zy.smps_user_service.entity.UserEntity;
import com.zy.smps_user_service.model.PageParam;
import com.zy.smps_user_service.service.UserService;
import com.zy.smps_user_service.util.ExcelUtil;
import org.junit.jupiter.api.Test;
import com.alibaba.excel.EasyExcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SmpsUserServiceApplicationTests {

    @Autowired
    private UserService userService;

    @Test
    void contextLoads() {
    }

    @Test
    void readExcel(){
        List<UserEntity> userEntities = ExcelUtil.readExcel();
        userEntities.forEach(System.out::println);
    }

    @Test
    public void simpleWrite() {
        List<UserEntity> users=userService.findUserAll(new PageParam());
        // 写法1
        String fileName = "C:\\Users\\99447\\Desktop\\orm\\smps\\smps_user_service\\src\\main\\resources" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        EasyExcel.write(fileName, UserEntity.class).sheet("模板").doWrite(users);

        // 写法2
//        fileName = "C:\\Users\\99447\\Desktop\\orm\\smps\\smps_user_service\\src\\main\\resources" + System.currentTimeMillis() + ".xlsx";
//        // 这里 需要指定写用哪个class去写
//        ExcelWriter excelWriter = EasyExcel.write(fileName, UserEntity.class).build();
//        WriteSheet writeSheet = EasyExcel.writerSheet("模板").build();
//        excelWriter.write(users, writeSheet);
//        // 千万别忘记finish 会帮忙关闭流
//        excelWriter.finish();
    }

}
