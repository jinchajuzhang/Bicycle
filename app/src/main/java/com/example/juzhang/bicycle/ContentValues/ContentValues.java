package com.example.juzhang.bicycle.ContentValues;

/**
 * 功能：此类用于保存全局的常量
 * 作者：JuZhang
 * 时间：2017/5/15
 */

public class ContentValues {
    public static final String HOSTNAME = "10.0.2.2:8080/easyBicycle";
    public static final String LOGINDOMAIN = "http://"+ HOSTNAME +"/api/commonLogin";
    public static final String REGISTERDOMAIN = "http://"+ HOSTNAME +"/api/commonRegist";
    public static final String GETUSERMESSAGEDOMAIN = "http://"+ HOSTNAME +"/api/getUserDetail";
    public static final String SETUSERMESSAGEDOMAIN = "http://"+ HOSTNAME +"/api/updateUserDetail";
    public static final String MENUTOTYPEDOMAIN = "http://"+HOSTNAME+"/API/bike/getBikeType";

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
