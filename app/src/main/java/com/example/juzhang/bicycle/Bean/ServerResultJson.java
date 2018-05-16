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
    private Integer sumItems;
    private Integer currentPage;
    private Integer sumPage;
    private Integer perPageItems;
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

    public Integer getSumItems() {
        return sumItems;
    }

    public void setSumItems(Integer sumItems) {
        this.sumItems = sumItems;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getSumPage() {
        return sumPage;
    }

    public void setSumPage(Integer sumPage) {
        this.sumPage = sumPage;
    }

    public Integer getPerPageItems() {
        return perPageItems;
    }

    public void setPerPageItems(Integer perPageItems) {
        this.perPageItems = perPageItems;
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
