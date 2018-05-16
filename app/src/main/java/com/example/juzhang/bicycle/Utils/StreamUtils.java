package com.example.juzhang.bicycle.Utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 功能：流转换类
 * 作者：JuZhang
 * 时间：2017/5/28
 */

public class StreamUtils {
    /**
     * 输入流转字符串
     * @param is 输入流
     * @return 字符串
     */
    public static String Stream2String(InputStream is){
        String result = "";
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        try {
            String temp = br.readLine();
            while(temp!=null&&!temp.equals("")){
                result += temp;
                temp = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
