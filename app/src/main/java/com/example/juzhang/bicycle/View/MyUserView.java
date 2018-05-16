package com.example.juzhang.bicycle.View;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.juzhang.bicycle.ContentValues.ContentValues;
import com.example.juzhang.bicycle.R;
import com.example.juzhang.bicycle.Bean.UserMessage;
import com.example.juzhang.bicycle.Utils.SharedPreferencesUtils;
import com.loopj.android.image.SmartImageView;

/**
 * 功能：自定义用户View
 * 作者：JuZhang
 * 时间：2017/5/15
 */

public class MyUserView extends LinearLayout {
    private SmartImageView siv_userimage;
    private TextView tv_usernickname;
    private TextView tv_username;
    private ImageView iv_rightpic;
    private View ll_unlogin,ll_logined;

    public MyUserView(Context context) {
        this(context,null);
    }

    public MyUserView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyUserView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initUI();
    }
    /**
     * 初始化UI
     */
    private void initUI() {
        View view = View.inflate(getContext(),R.layout.item_my_user,this);
        siv_userimage = (SmartImageView) view.findViewById(R.id.siv_item_my_userimage);
        tv_username = (TextView) view.findViewById(R.id.tv_item_my_username);
        tv_usernickname = (TextView) view.findViewById(R.id.tv_item_my_usernickname);
        iv_rightpic = (ImageView) view.findViewById(R.id.iv_item_my_userrightpic);
        ll_unlogin = view.findViewById(R.id.ll_item_my_userunlogin);
        ll_logined = view.findViewById(R.id.ll_item_my_userlogined);
    }

    /**
     * 设置未登陆
     */
    public void setUnLogin(){
        if(ll_logined.getVisibility()==View.VISIBLE)ll_logined.setVisibility(View.GONE);
        ll_unlogin.setVisibility(View.VISIBLE);
    }

    /**
     * 设置已经登陆
     * @param userMessage 登陆的用户信息
     */
    public void setLogined(UserMessage userMessage){
        if(ll_unlogin.getVisibility()==View.VISIBLE)ll_unlogin.setVisibility(View.GONE);
        if(userMessage.getUserImageUrl()!=null&&!userMessage.getUserImageUrl().equals(""))
            siv_userimage.setImageUrl(userMessage.getUserImageUrl());
        tv_username.setText(userMessage.getUserName()==null?"未知账号":userMessage.getUserName());
        tv_usernickname.setText(userMessage.getUserNickName()==null?"还未设置昵称":userMessage.getUserNickName());
        ll_logined.setVisibility(View.VISIBLE);
    }

    /**
     * 获取是否已经登陆
     * @return 是否
     */
    public boolean getLogined(){
        if(SharedPreferencesUtils.getObjectFromShare(getContext(), ContentValues.USERMESSAGE)!=null)
            return true;
        else
            return false;
    }

    /**
     * 设置信息
     * @param userMessage 用户信息Bean
     */
    public void setMessage(UserMessage userMessage){
        tv_username.setText(userMessage.getUserName());
        tv_usernickname.setText(userMessage.getUserNickName());
    }
}
