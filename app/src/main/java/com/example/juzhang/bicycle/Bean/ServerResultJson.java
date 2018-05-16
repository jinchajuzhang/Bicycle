package com.example.juzhang.bicycle.Bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 功能：用于接受服务器返回的普通数据
 * 作者：JuZhang
 * 时间：2017/5/16
 */

public class ServerResultJson implements Serializable {
    private Integer code;
    private String message;
    private Object data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
