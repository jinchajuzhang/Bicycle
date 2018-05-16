package com.example.juzhang.bicycle.Utils;

import android.content.Context;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.Map;

/**
 * 功能：网络请求通用类
 * 作者：JuZhang
 * 时间：2018/5/16
 */

public class Net {
    /**
     * 通用公共返回接口
     */
    public interface netCallBack{
        void success(String data);
        void error(Throwable ex);
    }

    /**
     * 进行POST请求
     * @param context 上下文
     * @param url 请求地址
     * @param params 参数
     * @param callBack 回调函数
     */
    public static void post(Context context, String url, Map<String,Object> params, final netCallBack callBack){
        RequestParams requestParams = new RequestParams(url);
        requestParams.addParameter("userId", SharedPreferencesUtils.getObjectFromShare(context,"userId"));
        requestParams.addParameter("token",  SharedPreferencesUtils.getObjectFromShare(context,"token"));
        if(params!=null){
            for ( String key : params.keySet()) {
                requestParams.addParameter(key,params.get(key));
            }
        }
        x.http().post(requestParams, new Callback.CacheCallback<String>() {

            @Override
            public boolean onCache(String result) {
                return false;
            }

            @Override
            public void onSuccess(String result) {
                callBack.success(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                callBack.error(ex);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}
