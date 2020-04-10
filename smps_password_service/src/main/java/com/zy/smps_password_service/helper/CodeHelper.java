package com.zy.smps_password_service.helper;

import java.util.HashMap;
import java.util.Random;

public class CodeHelper {



    public static String createCode(){
        StringBuffer result = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i <4 ; i++) {
            result.append(random.nextInt(10));
        }
        String code=result.toString();
        return code;
    }


}
