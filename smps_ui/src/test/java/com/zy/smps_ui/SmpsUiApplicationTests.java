package com.zy.smps_ui;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SmpsUiApplicationTests {

    @Test
    void contextLoads() {
        String str="1234";
        String[] array = str.split(",");
        for (String s:array){
            System.out.println(s);
        }
    }

}
