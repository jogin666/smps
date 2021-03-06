package com.zy.smps_message_service.page;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;

import java.io.*;

@Slf4j
public class HtmlHelper {

    public static void createHtml(String messId,String content,boolean publish){
        String filePath="";
        String fileName="";
        try {
            String target_path=ResourceUtils.getURL("classpath:").getPath()+ "static/view/"; //路径
            log.info("path--{}",target_path);
            if (publish){
                filePath = target_path+"template.html"; //模板
                fileName =target_path+ messId + ".html"; //生成的html文件保存路径。
            }else{
                filePath=target_path+"canceltempalte.html";
                fileName=target_path+messId+"cancel.html";
            }
            // 读取模板文件
            FileInputStream fileinputstream = new FileInputStream(filePath);
            int len = fileinputstream.available();
            byte bytes[] = new byte[len];
            fileinputstream.read(bytes);
            fileinputstream.close();

            //把模板页面上的 ###text### 替换成 content
            String templateContent = new String(bytes);
            templateContent = templateContent.replaceAll("###text###", content);

            //生成文件
            FileOutputStream fileoutputstream = new FileOutputStream(fileName);// 建立文件输出流
            byte tag_bytes[] = templateContent.getBytes();
            fileoutputstream.write(tag_bytes);
            fileoutputstream.close();

        } catch (Exception e) {
            System.out.print(e.toString());
        }
    }

    public static void deleteHtml(String messId,boolean cancel) throws FileNotFoundException {
        String target_path = ResourceUtils.getURL("classpath:").getPath()+ "static/view/";
        String fileName="";
        if (cancel){
            fileName=target_path+messId+"cancel.html";
        }else{
            fileName=target_path+messId+".html";
        }
        File file = new File(fileName);
        if (file.exists()){
            file.delete();
        }
    }
}
