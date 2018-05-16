package com.example.juzhang.bicycle.Bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 功能：获取
 * 作者：JuZhang
 * 时间：2017/6/2
 */

public class MenuServerResultJson implements Serializable{
    private int status;
    private List<RentalMenuMessage> msg;

    public MenuServerResultJson() {
        this.msg = new ArrayList<>();
    }

    public List<RentalMenuMessage> getMsg() {
        return msg;
    }

    public void setMsg(List<RentalMenuMessage> msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }



}
