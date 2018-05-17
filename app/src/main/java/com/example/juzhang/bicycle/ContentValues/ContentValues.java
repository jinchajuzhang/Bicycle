package com.example.juzhang.bicycle.ContentValues;

/**
 * 功能：此类用于保存全局的常量
 * 作者：JuZhang
 * 时间：2017/5/15
 */

public class ContentValues {
    public static final String HOST = "http://"+"10.0.2.2:8080";
    private static final String HOSTNAME = HOST+"/easyBicycle";
    public static final String LOGINDOMAIN = HOSTNAME +"/api/commonLogin";
    public static final String REGISTERDOMAIN = HOSTNAME +"/api/commonRegist";
    public static final String GETUSERMESSAGEDOMAIN = HOSTNAME +"/api/getUserDetail";
    public static final String SETUSERMESSAGEDOMAIN = HOSTNAME +"/api/updateUserDetail";
    public static final String GETBICYCLEDOMAIN = HOSTNAME +"/api/getBicycleMessage";
    public static final String MENUTOTYPEDOMAIN = HOSTNAME+"/API/bike/getBikeType";

    //极验验证第一次验证URL
    public static final String CAPTCHAURL = "http://www.geetest.com/demo/gt/register-slide";
    //极验验证第二次验证URL
    public static final String VALIDATEURL = HOSTNAME +"/api/getGeeVerify";

    public static final String USERMESSAGE = "usermessage";
    public static final String COOKIE = "cookie";
    public static final String LOGINCOOKIE = "logincookie";
    public static final String MD5PSDAFTER = "juzhang";

    public static final int MYTOLOGIN = 200;
    public static final int RETURNTOLOGIN = 201;
    public static final int MYTOSETUSERMESSAGE = 202;
    public static final int LOGINSUCCESS = 203;
    public static final int LOGINFAILD = 204;
    public static final int REGISTERSECCESS = 205;
    public static final int FIXFINISH = 206;



}
