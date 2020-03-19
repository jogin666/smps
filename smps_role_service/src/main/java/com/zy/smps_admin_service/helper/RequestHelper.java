package com.zy.smps_admin_service.helper;


import java.util.HashMap;
import java.util.Map;

public class RequestHelper {

    public static Map<String,String> buildRequestMap(String result){
        HashMap<String, String> map = new HashMap<>();
        map.put("result",result);
        return map;
    }
}
