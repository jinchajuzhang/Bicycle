package com.example.juzhang.bicycle.Utils;

import android.content.Context;

import com.example.juzhang.bicycle.Bean.ServerResultJson;
import com.example.juzhang.bicycle.ContentValues.ContentValues;
import com.example.juzhang.bicycle.Bean.UserMessage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.net.HttpCookie;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能：用户信息的操作类
 * 作者：JuZhang
 * 时间：2017/5/17
 */

public class UserMessageUtils {
    private Context context;

    public UserMessageUtils(Context context) {
        this.context = context;
    }

    public interface OnUserMessageGetFinishListener{
        void onUserMessageGetFinish(UserMessage userMessage);
    }

    /**
     * 从服务器上获取用户数据
     */
    public void getUserMessageFromServer(final OnUserMessageGetFinishListener listener){
        Net.post(context, ContentValues.GETUSERMESSAGEDOMAIN, null, new Net.netCallBack() {
            @Override
            public void success(String data) {
                ServerResultJson result = JSON.parseToServerResult(data);
                UserMessage userMessage = JSON.map2Bean((Map<String, Object>) result.getData(), UserMessage.class);
                listener.onUserMessageGetFinish(userMessage);
            }

            @Override
            public void error(Throwable ex) {
                listener.onUserMessageGetFinish(null);
                ex.printStackTrace();
            }
        });
    }
}
