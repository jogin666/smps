package com.zy.smps_message_service.page;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileNotFoundException;

@SpringBootTest
class HtmlUtilTest {

    @Test
    void createHtml() throws FileNotFoundException {
//        HtmlUtil.createHtml();
    }

    @Test
    void test(){
        String str="\"<h3><p style=\"text-align: center\" id=\"fTitle\">--start-- 这里写标题且标题的长度大于1 --end--</p></h3><h4>\n" +
                "<p style=\"text-align: center\" id=\"sTitle\"> 这里写副标题 </p></h4><h3>\n" +
                "<p id=\"content\">--start--</p>\n" +
                "<p id=\"content\">&nbsp;这里写内容&nbsp;</p>\n" +
                "<p id=\"content\">--end--</p></h3><p><br></p>\"";
        String str1=str.replace("--start--","")
                .replace("--end--","");
        System.out.println(str1);
    }
}