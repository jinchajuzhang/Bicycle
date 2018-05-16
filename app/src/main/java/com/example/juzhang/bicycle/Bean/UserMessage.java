package com.example.juzhang.bicycle.Bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 功能：用户信息类
 * 作者：JuZhang
 * 时间：2017/5/15
 */

public class UserMessage implements Serializable{
    @SerializedName("username")
    private String userName;
    @SerializedName("password")
    private String password;
    @SerializedName("nickName")
    private String userNickName;
    @SerializedName("headPictureSrc")
    private String userImageUrl;
    private String qq;
    private String email;
    private String realName;
    private String sex;
    @SerializedName("mobile")
    private String phone;
    @SerializedName("weChart")
    private String weChat;
    private String isused;


    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWeChat() {
        return weChat;
    }

    public void setWeChat(String weChat) {
        this.weChat = weChat;
    }

    public String getIsused() {
        return isused;
    }

    public void setIsused(String isused) {
        this.isused = isused;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserImageUrl() {
        return userImageUrl;
    }

    public void setUserImageUrl(String userImageUrl) {
        this.userImageUrl = userImageUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    @Override
    public String toString() {
        return "UserMessage{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", userNickName='" + userNickName + '\'' +
                ", userImageUrl='" + userImageUrl + '\'' +
                ", qq='" + qq + '\'' +
                ", email='" + email + '\'' +
                ", isused='" + isused + '\'' +
                '}';
    }
}
