package com.example.juzhang.bicycle;

import android.app.Application;

import com.example.juzhang.bicycle.Bean.UserMessage;

import org.xutils.x;

/**
 * 功能：App全局信息类
 * 作者：JuZhang
 * 时间：2017/5/15
 */

public class AppData extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }

    private UserMessage userMessage;
    public UserMessage getUserMessage() {
        return userMessage;
    }
    public void setUserMessage(UserMessage userMessage) {
        this.userMessage = userMessage;
    }
}
