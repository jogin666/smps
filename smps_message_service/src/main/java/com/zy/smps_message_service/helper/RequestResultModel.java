package com.zy.smps_message_service.helper;

import lombok.Data;

import java.util.List;

@Data
public class RequestResultModel<T> {

    private Integer count;
    private String msg;
    private Integer code;
    private List<T> result;

    public RequestResultModel(Integer count, String msg, Integer code, List<T> result) {
        this.count = count;
        this.msg = msg;
        this.code = code;
        this.result = result;
    }
}
