package com.zy.smps_ui.data;

import java.util.HashMap;
import java.util.Map;

public class DataMap {

    private static Map<String, Map<String,Object>> datas=new HashMap<>();

    public static Map<String,Object> put(String key1,String key2,Object value){
        Map<String, Object> map = datas.get(key1);
        if (map==null){
            map=new HashMap<>();
        }
        map.put(key2,value);
        return datas.put(key1,map);
    }

    public static Map<String,Object> getData(String key){
        return datas.get(key);
    }
    
    public static Map<String,Object> removeData(String key){
        return datas.remove(key);
    }
}
