package com.zy.smps_message_service.controller;

import com.zy.smps_message_service.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/message")
public class ViewController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/view/{messId}")
    public String getView(@PathVariable("messId")String messId){
        messageService.updateViewNum(messId);
        String viewName="/view/"+messId;
        return viewName;
    }

    @GetMapping("/view/check/{messId}")
    public String getCheckView(@PathVariable("messId")String messId, @RequestParam("account")String account){
//        DataMap.put(account,"messId",messId);
        return "/view/check_message";
    }

    @GetMapping("/view/cancel/{mId}")
    public String getCancelView(@PathVariable("mId")String mId){
        return "/view/cancle"+mId;
    }

}
