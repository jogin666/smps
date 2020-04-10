package com.zy.smps_message_service.helper;

import java.util.HashSet;
import java.util.Set;

public class RedisHelper {

    private final static Set<String> redisKeys=new HashSet<>();

    public static Set<String> keySet(){
        return  redisKeys;
    }
}
