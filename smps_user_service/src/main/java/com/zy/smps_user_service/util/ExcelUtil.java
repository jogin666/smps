package com.zy.smps_user_service.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.zy.smps_user_service.entity.UserEntity;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ExcelUtil {

    //处理上传的数据对象
    public static List<UserEntity> readExcel(InputStream stream, String fileName){
        boolean isExcelFile=fileName.matches("^.+\\.(?i)(xls)$");
        if (!isExcelFile){
            PersonExcelListener personExcelListener = new PersonExcelListener();
            EasyExcel.read(stream, UserEntity.class,personExcelListener).sheet().doRead();
            return personExcelListener.getUsers();
        }
        return new ArrayList<>();
    }

    public static void writeExcel(ServletOutputStream stream,List<UserEntity> users)throws Exception{
        EasyExcel.write(stream, UserEntity.class).autoCloseStream(Boolean.FALSE).sheet("用户数据")
                .doWrite(users);
    }

    public static List<UserEntity> readExcel(){
        String fileName="person.xlsx";
        InputStream stream = ExcelUtil.class.getClassLoader().getResourceAsStream(fileName);
        return readExcel(stream,fileName);
//        PersonExcelListener personExcelListener = new PersonExcelListener();
//        EasyExcel.read(stream, UserEntity.class,personExcelListener).sheet().doRead();
    }
}
