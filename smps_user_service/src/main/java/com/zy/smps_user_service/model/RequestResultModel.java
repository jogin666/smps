package com.zy.smps_user_service.model;

import java.io.Serializable;
import java.util.List;

public class RequestResultModel<T> implements Serializable {

    private String msg;
    private Integer code;
    private Integer count;
    private List<T> result;

    public RequestResultModel(String msg, Integer code, Integer count, List<T> result) {
        this.msg = msg;
        this.code = code;
        this.count = count;
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }
}
