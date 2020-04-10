package com.zy.smps_password_service.helper;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@ConfigurationProperties(value = "custom.mail")
public class MailHelper {

    private static String from;  //发送者
    private static String title; //标题
    private static String message; //邮件主题
    private static String tail; //尾部

    //构建邮件
    public static SimpleMailMessage builderMailMessage(String to,String code){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(from);
        mailMessage.setTo(to);
        mailMessage.setSubject(title);
        mailMessage.setText(buildMessage(code));
        mailMessage.setSentDate(new Date());
        return mailMessage;
    }

    //构建邮件主题内容
    private static String buildMessage(String code){
        StringBuffer sb=new StringBuffer();
        sb.append(message+" ");
        sb.append(code);
        sb.append(" ,"+tail);
        return sb.toString();
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTail() {
        return tail;
    }

    public void setTail(String tail) {
        this.tail = tail;
    }
}
