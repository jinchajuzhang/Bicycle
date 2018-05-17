package com.example.juzhang.bicycle.Utils;

import com.example.juzhang.bicycle.Bean.ServerResultJson;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能：JSON转换工具类
 * 作者：JuZhang
 * 时间：2018/5/16
 */

public class JSON {

    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 将json 转为指定的类型
     * @param jsonStr json字符串
     * @param tClass 指定类型的class
     * @return 指定类型
     */
    public static <T> T parseToBean(String jsonStr,Class<T> tClass){
        T bean = null;
        try {
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            bean = objectMapper.readValue(jsonStr, tClass);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bean;
    }

    /**
     * 将json字符串转为Map集合
     * @param jsonStr json字符串
     * @return  Map集合
     */
    public static HashMap parse(String jsonStr){
        return parseToBean(jsonStr,HashMap.class);
    }

    /**
     * 将Map集合转为json字符串
     * @param beanMap Map集合
     * @return json 字符串
     */
    public static String stringify(Map<String,Object> beanMap){
        String serverResultJson = null;
        try {
            serverResultJson = objectMapper.writeValueAsString(beanMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return serverResultJson;
    }

    /**
     * 将json字符串转换为通用服务器返回结果
     * @param jsonStr json字符串
     * @return 通用服务器返回结果
     */
    public static ServerResultJson parseToServerResult(String jsonStr){
        return parseToBean(jsonStr,ServerResultJson.class);
    }

    /**
     * Map转Bean
     * @param beanMap map
     * @param tClass 目标bean类型
     * @return 目标类型
     */
    public static <T> T map2Bean(Map<String,Object> beanMap,Class<T> tClass){
        String json = stringify(beanMap);
        return parseToBean(json,tClass);
    }

    /**
     *
     * @param bean
     * @param <T>
     * @return
     */
    public static<T> Map<String,Object> bean2Map(T bean){
        Map<String,Object> resultMap = null;
        try {
            resultMap = objectMapper.readValue(objectMapper.writeValueAsString(bean),HashMap.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultMap;
    }

    /**
     * 将实体Bean转成json字符串
     * @param bean 实体Bean
     * @return json字符串
     */
    public static<T> String bean2String(T bean){
        String result = "{}";
        try {
            result = objectMapper.writeValueAsString(bean);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }
}
