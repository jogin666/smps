package com.zy.smps_password_service.util;

import java.util.HashMap;
import java.util.Map;

public class RequestResultMap {

    public static Map<String,String> buildRequestResult(String result){
        HashMap<String, String> map = new HashMap<>();
        map.put("result",result);
        return map;
    }
}
