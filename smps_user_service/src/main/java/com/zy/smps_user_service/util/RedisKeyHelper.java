package com.zy.smps_user_service.util;

import java.util.HashSet;
import java.util.Set;

public class RedisKeyHelper {

    private final static Set<String> keys=new HashSet<>();

    public static Set<String> keySet(){
        return keys;
    }
}
