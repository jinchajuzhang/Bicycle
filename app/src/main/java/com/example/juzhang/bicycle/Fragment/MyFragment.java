package com.example.juzhang.bicycle.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.juzhang.bicycle.Activity.AroundActivity;
import com.example.juzhang.bicycle.Activity.HotPointActivity;
import com.example.juzhang.bicycle.Activity.LoginActivity;
import com.example.juzhang.bicycle.Activity.SettingActivity;
import com.example.juzhang.bicycle.Activity.UserMessageActivity;
import com.example.juzhang.bicycle.AppData;
import com.example.juzhang.bicycle.ContentValues.ContentValues;
import com.example.juzhang.bicycle.Bean.UserMessage;
import com.example.juzhang.bicycle.R;
import com.example.juzhang.bicycle.Utils.SharedPreferencesUtils;
import com.example.juzhang.bicycle.View.MyItemView;
import com.example.juzhang.bicycle.View.MyUserView;

/**
 * 功能：我的Fragment
 * 作者：JuZhang
 * 时间：2017/5/9
 */

public class MyFragment extends Fragment implements View.OnClickListener {
    private View oldView = null;
    private MyUserView muv_user;
    private MyItemView miv_shopcar;
    private MyItemView miv_history;
    private MyItemView miv_around;
    private MyItemView miv_hotpoint;
    private MyItemView miv_setting;

    private View ll_logoff;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        if(oldView == null){
            oldView = view = View.inflate(getContext(), R.layout.fragment_my, null);
            initUI();
            initData();
        }else{
            view = oldView;
        }
        checkLoginMessage();
        return view;
    }

    /**
     * 初始化数据
     */
    private void initData() {
    }

    /**
     * 检查登陆信息
     */
    private void checkLoginMessage() {
        UserMessage userMessage = (UserMessage) SharedPreferencesUtils.getObjectFromShare(getContext(),ContentValues.USERMESSAGE);
        if(userMessage!=null){
            //保存用户信息到APP
            ((AppData)getActivity().getApplication()).setUserMessage(userMessage);
            loginSuccessDo();
        }else{
            loginFaildOrLogoffDo();
        }
    }

    /**
     * 初始化UI
     */
    private void initUI() {
        muv_user = (MyUserView) oldView.findViewById(R.id.muv_my_useritem);
        miv_shopcar = (MyItemView) oldView.findViewById(R.id.miv_my_shopcar);
        miv_history = (MyItemView) oldView.findViewById(R.id.miv_my_history);
        miv_around = (MyItemView) oldView.findViewById(R.id.miv_my_around);
        miv_hotpoint = (MyItemView) oldView.findViewById(R.id.miv_my_hotpoint);
        miv_setting = (MyItemView) oldView.findViewById(R.id.miv_my_setting);
        ll_logoff = oldView.findViewById(R.id.ll_my_logoff);

        muv_user.setOnClickListener(this);
        miv_shopcar.setOnClickListener(this);
        miv_history.setOnClickListener(this);
        miv_around.setOnClickListener(this);
        miv_hotpoint.setOnClickListener(this);
        miv_setting.setOnClickListener(this);

        ll_logoff.setOnClickListener(this);
    }

    /**
     * 接受Activity的返回
     * @param resultCode 返回码
     */
    public void resultForActivity(int resultCode){
        switch(resultCode){
            case ContentValues.LOGINSUCCESS:
                loginSuccessDo();
                break;
            case ContentValues.LOGINFAILD:
                loginFaildOrLogoffDo();
                break;
            case ContentValues.FIXFINISH:
                setMessageFinishDo();
                break;
        }
    }

    /**
     * 信息设置完成后做的事
     */
    private void setMessageFinishDo() {
        muv_user.setMessage(((AppData)getActivity().getApplication()).getUserMessage());
    }

    /**
     * 登陆成功后做的事
     */
    private void loginSuccessDo(){
        muv_user.setLogined(((AppData)getActivity().getApplication()).getUserMessage());
        ll_logoff.setVisibility(View.VISIBLE);
    }

    /**
     * 登陆失败或者注销后做的事
     */
    private void loginFaildOrLogoffDo(){
        muv_user.setUnLogin();
        if(ll_logoff.getVisibility()==View.VISIBLE) {
            ll_logoff.setVisibility(View.GONE);
            SharedPreferencesUtils.putObjectToShare(getContext(),null,ContentValues.USERMESSAGE);
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.muv_my_useritem://登陆点击
                if(((MyUserView)v).getLogined()){//进入用户信息设置界面
                    getActivity().startActivityForResult(new Intent(getContext(), UserMessageActivity.class), ContentValues.MYTOSETUSERMESSAGE);
                }else{
                    getActivity().startActivityForResult(new Intent(getContext(),LoginActivity.class), ContentValues.MYTOLOGIN);
                }
                break;
            case R.id.ll_my_logoff://注销点击
                //开启网络注销
                loginFaildOrLogoffDo();
                break;
            case R.id.miv_my_shopcar:
                break;
            case R.id.miv_my_history:
                break;
            case R.id.miv_my_hotpoint:
                startActivity(new Intent(getActivity(), HotPointActivity.class));
                break;
            case R.id.miv_my_around:
                startActivity(new Intent(getActivity(), AroundActivity.class));
                break;
            case R.id.miv_my_setting:
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
        }
    }
}
