package com.zy.smps_ui.common;

import java.util.HashMap;
import java.util.Map;

public class DataMap {

    private static Map<String, Map<String,Object>> datas=new HashMap<>();

    public static Map<String,Object> put(String userId,String objKey,Object value){
        Map<String, Object> map = datas.get(userId);
        if (map==null){
            map=new HashMap<>();
        }
        map.put(objKey,value);
        return datas.put(userId,map);
    }

    public static Map<String,Object> getData(String userId){
        return datas.get(userId);
    }
}
