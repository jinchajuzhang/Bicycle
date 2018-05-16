package com.example.juzhang.bicycle.Bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 功能：租车界面菜单的模型
 * 作者：JuZhang
 * 时间：2017/5/14
 */

public class RentalMenuMessage implements Serializable{
    @SerializedName("name")
    private String menuName;
    private String nametype;
    public static final int type = 0;
    public static final int purpose = 1;


    public RentalMenuMessage(String menuName, int sort) {
        this.menuName = menuName;
    }

    public RentalMenuMessage() {

    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getNametype() {
        return nametype;
    }

    public void setNametype(String nametype) {
        this.nametype = nametype;
    }
}
