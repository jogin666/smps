package com.zy.smps_password_service.helper;

import java.util.HashMap;
import java.util.Random;

public class CodeHelper {

    private static HashMap<String,String> codeMap=new HashMap<>();

    public static String createCode(String receiver){
        StringBuffer result = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i <4 ; i++) {
            result.append(random.nextInt(10));
        }
        String code=result.toString();
        codeMap.put(receiver,code);
        return code;
    }

    public static void remove(String receiver){
        codeMap.remove(receiver);
    }

    public static String find(String key){
        String code = codeMap.get(key);
        if (code!=null && !"".equals(code)){
            System.out.println("code: "+code);
            return code;
        }
        return null;
    }
}
