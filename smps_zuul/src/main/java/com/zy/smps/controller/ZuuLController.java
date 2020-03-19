package com.zy.smps.controller;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ZuuLController {

    @RequestMapping("/test")
    public String test(){
        return "zuul test";
    }

//    @CrossOrigin(value = "http://localhost:9008",allowCredentials = "true")
    @PostMapping("/login")
    public Map<String,String> testMap(){
        HashMap<String, String> map = new HashMap<>();
        map.put("msg","请求成功");
        return map;
    }
}
