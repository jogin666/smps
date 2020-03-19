package com.zy.smps_ui.util;

import java.util.HashMap;
import java.util.Map;

public class RequestResultBuilder {

    public static Map<String,String> buildResult(String result){
        HashMap<String,String> resultMap = new HashMap<>();
        resultMap.put("result",result);
        return resultMap;
    }
}
