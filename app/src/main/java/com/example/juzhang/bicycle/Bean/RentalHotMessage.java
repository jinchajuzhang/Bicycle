package com.example.juzhang.bicycle.Bean;


/**
 * 功能：租车界面的热点模型
 * 作者：JuZhang
 * 时间：2017/5/13
 */

public class RentalHotMessage {
    private String description;
    private String smartImageViewUrl;
    //热点类型:0代表 不设置，1代表 new，2代表 hot
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSmartImageViewUrl() {
        return smartImageViewUrl;
    }

    public void setSmartImageViewUrl(String smartImageViewUrl) {
        this.smartImageViewUrl = smartImageViewUrl;
    }
}
