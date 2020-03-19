package com.zy.smps_user_service.util;

import java.util.HashMap;
import java.util.Map;

public class RequestResultBuilder {

    public static Map<String,String> buildResult(String result){
        HashMap<String, String> map = new HashMap<>();
        map.put("result",result);
        return map;
    }
}
