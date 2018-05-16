package com.example.juzhang.bicycle.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 功能：SP的工具类
 * 作者：JuZhang
 * 时间：2017/5/15
 */

public class SharedPreferencesUtils {

    /**
     * 将对象保存到SP中
     * @param context 上下文
     * @param object 对象
     * @param key 保存的key
     * @return 是否成功
     */
    public static boolean putObjectToShare(Context context, Object object, String key) {
        SharedPreferences share = PreferenceManager
                .getDefaultSharedPreferences(context);
        if (object == null) {
            SharedPreferences.Editor editor = share.edit().remove(key);
            return editor.commit();
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos;
        try {
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        // 将对象放到OutputStream中
        // 将对象转换成byte数组，并将其进行base64编码
        String objectStr = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
        try {
            baos.close();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        SharedPreferences.Editor editor = share.edit();
        // 将编码后的字符串写到base64.xml文件中
        editor.putString(key, objectStr);
        return editor.commit();
    }

    /**
     * 从SP中获取对象
     * @param context 上下文
     * @param key 保存的key
     * @return 对象
     */
    public static Object getObjectFromShare(Context context, String key) {
        SharedPreferences sharePre = PreferenceManager
                .getDefaultSharedPreferences(context);
        try {
            String wordBase64 = sharePre.getString(key, "");
            // 将base64格式字符串还原成byte数组
            if (wordBase64.equals("")) { // 不可少，否则在下面会报java.io.StreamCorruptedException
                return null;
            }
            byte[] objBytes = Base64.decode(wordBase64.getBytes(),
                    Base64.DEFAULT);
            ByteArrayInputStream bais = new ByteArrayInputStream(objBytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            // 将byte数组转换成product对象
            Object obj = ois.readObject();
            bais.close();
            ois.close();
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 保存字符串到SP
     * @param context 上下文
     * @param name 文件名
     * @param key key
     * @param value 字符串
     * @return 是否成功
     */
    public static boolean putStringToShare(Context context,String name,String key,String value){
        SharedPreferences share = context.getSharedPreferences(name,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = share.edit();
        editor.putString(key,value);
        return editor.commit();
    }

    /**
     * 获取字符串
     * @param context 上下文
     * @param name 文件名
     * @param key key
     * @param defaultString 默认返回
     * @return 字符串
     */
    public static String getStringFromShare(Context context,String name,String key,String defaultString){
        SharedPreferences share = context.getSharedPreferences(name,Context.MODE_PRIVATE);
        return share.getString(key,defaultString);
    }
}
