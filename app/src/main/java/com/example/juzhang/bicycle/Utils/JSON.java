package com.example.juzhang.bicycle.Utils;

import com.example.juzhang.bicycle.Bean.ServerResultJson;
import com.google.gson.Gson;

import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * 功能：JSON转换工具类
 * 作者：JuZhang
 * 时间：2018/5/16
 */

public class JSON {

    private static Gson gson = new Gson();
    /**
     * 将json字符串转为Map集合
     * @param jsonStr json字符串
     * @return  Map集合
     */
    public static HashMap parse(String jsonStr){
        return gson.fromJson(jsonStr, HashMap.class);
    }

    /**
     * 将json字符串转换为通用服务器返回结果
     * @param jsonStr json字符串
     * @return 通用服务器返回结果
     */
    public static ServerResultJson parseToServerResult(String jsonStr){
        return gson.fromJson(jsonStr,ServerResultJson.class);
    }

    /**
     * Map转Bean
     * @param beanMap map
     * @param tClass 目标bean类型
     * @return 目标类型
     */
    public static <T> T map2Bean(Map<String,Object> beanMap,Class<T> tClass){
        T bean = null;
        try {
            bean = tClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        try {
            BeanUtils.populate(bean,beanMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return bean;
    }

    /**
     * 将Map集合转为json字符串
     * @param beanMap Map集合
     * @return json 字符串
     */
    public static String stringify(Map<String,Object> beanMap){
        return gson.toJson(beanMap);
    }

    /**
     * 将json 转为指定的类型
     * @param jsonStr json字符串
     * @param tClass 指定类型的class
     * @return 指定类型
     */
    public static <T> T parseToBean(String jsonStr,Class<T> tClass){
        return gson.fromJson(jsonStr,tClass);
    }
}
