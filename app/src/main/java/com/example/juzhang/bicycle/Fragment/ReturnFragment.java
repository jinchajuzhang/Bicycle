package com.example.juzhang.bicycle.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.juzhang.bicycle.Activity.LoginActivity;
import com.example.juzhang.bicycle.AppData;
import com.example.juzhang.bicycle.ContentValues.ContentValues;
import com.example.juzhang.bicycle.Bean.UserMessage;
import com.example.juzhang.bicycle.R;
import com.example.juzhang.bicycle.Utils.SharedPreferencesUtils;
import com.loopj.android.image.SmartImageView;

/**
 * 功能：还车Fragment
 * 作者：JuZhang
 * 时间：2017/5/9
 */

public class ReturnFragment extends Fragment {
    private View oldView = null;
    private View ll_return_login;
    private View ll_return_return;
    private ImageView iv_login_bg1;
    private ImageView iv_login_bg2;
    private ImageView iv_login_bg3;
//    private RecyclerView rv_display;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        if(oldView == null){
            oldView = view = View.inflate(getContext(), R.layout.fragment_return, null);
            initUI();
        }else{
            view = oldView;
        }
        if(!checkLogin()){
            startLoginImageAnimation();
        }
        return view;
    }

    /**
     * 初始化UI
     */
    private void initUI() {
        iv_login_bg1 = (ImageView) oldView.findViewById(R.id.iv_return_login_bg1);
        iv_login_bg2= (ImageView) oldView.findViewById(R.id.iv_return_login_bg2);
        iv_login_bg3= (ImageView) oldView.findViewById(R.id.iv_return_login_bg3);
//        rv_display = (RecyclerView) oldView.findViewById(R.id.rv_return_display);
        SmartImageView siv_login = (SmartImageView) oldView.findViewById(R.id.siv_return_login);

        ll_return_login = oldView.findViewById(R.id.ll_return_login);
        ll_return_return = oldView.findViewById(R.id.ll_return_return);


        siv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivityForResult(new Intent(getContext(), LoginActivity.class), ContentValues.RETURNTOLOGIN);
            }
        });
    }

    /**
     * 检查用户是否登陆并做一些事
     * @return 是否登陆
     */
    private boolean checkLogin() {
        UserMessage userMessage = (UserMessage) SharedPreferencesUtils.getObjectFromShare(getContext(),ContentValues.USERMESSAGE);
        if(userMessage!=null){//有登陆
            //保存用户信息到APP
            ((AppData)getActivity().getApplication()).setUserMessage(userMessage);
            loginSuccessDo();
            return true;
        }else{//没有登陆
            loginFaildOrLogoffDo();
        }
        return false;
    }

    /**
     * 开始登陆图片的动画
     */
    private void startLoginImageAnimation() {
        if(ll_return_login.getVisibility()==View.VISIBLE){
            iv_login_bg1.startAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.iv_scaleanimation1));
            iv_login_bg2.startAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.iv_scaleanimation2));
            iv_login_bg3.startAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.iv_scaleanimation3));
        }
    }

    /**
     * 登陆界面的返回
     * @param resultCode 返回码
     */
    public void resultForLoginActivity(int resultCode){
        switch(resultCode){
            case ContentValues.LOGINSUCCESS:
                loginSuccessDo();
                break;
            case ContentValues.LOGINFAILD:
                loginFaildOrLogoffDo();
                break;
        }
    }

    /**
     * 登陆失败或者取消后做的
     */
    private void loginFaildOrLogoffDo() {
        ll_return_login.setVisibility(View.VISIBLE);
        if(ll_return_return.getVisibility()==View.VISIBLE)
            ll_return_return.setVisibility(View.GONE);
    }

    /**
     * 登陆成功后做的
     */
    private void loginSuccessDo() {
        ll_return_return.setVisibility(View.VISIBLE);
        ll_return_return.findViewById(R.id.btn_return_return).setVisibility(View.GONE);
        if(ll_return_login.getVisibility()==View.VISIBLE)
            ll_return_login.setVisibility(View.GONE);
    }
}
class MyXRecycleViewAdapter extends RecyclerView.Adapter{

    private Context context;

    MyXRecycleViewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_my_item, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
    private class MyViewHolder extends RecyclerView.ViewHolder{
        MyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
