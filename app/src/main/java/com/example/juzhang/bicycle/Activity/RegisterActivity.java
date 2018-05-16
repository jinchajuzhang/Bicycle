package com.example.juzhang.bicycle.Activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.juzhang.bicycle.ContentValues.ContentValues;
import com.example.juzhang.bicycle.Bean.ServerResultJson;
import com.example.juzhang.bicycle.Bean.UserMessage;
import com.example.juzhang.bicycle.R;
import com.example.juzhang.bicycle.Utils.DialogUtils;
import com.example.juzhang.bicycle.Utils.JSON;
import com.example.juzhang.bicycle.View.ClearEditText;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.ref.WeakReference;
import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    private DialogUtils mDialogUtils;

    private ClearEditText cet_username;
    private ClearEditText cet_password;
    private ClearEditText cet_qq;
    private ClearEditText cet_email;
    private Button btn_register;
    private CheckBox cb_agree;
    private MyHandler myHandler = new MyHandler(this);

    private static class MyHandler extends Handler{
        private static final int REGISTERSUCCESS = 100;
        private static final int REGISTERFAILD = 101;
        private static final int CLOSEWAITINGDIALOG = 102;
        private static final int CLOSEWAITINGDIALOGANDFINISH = 103;
        private RegisterActivity activity;

        MyHandler(RegisterActivity temp) {
            this.activity = new WeakReference<>(temp).get();
        }
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case REGISTERSUCCESS:
                    View viewSuccess = View.inflate(activity, R.layout.dialog_success, null);
                    ((TextView)viewSuccess.findViewById(R.id.tv_dialog_success_msg)).setText("注册成功！");
                    activity.mDialogUtils.startWaitingDialog(viewSuccess);
                    this.sendEmptyMessageDelayed(CLOSEWAITINGDIALOGANDFINISH,1000);
                    break;
                case REGISTERFAILD:
                    View viewFaild = View.inflate(activity, R.layout.dialog_faild, null);
                    ((TextView)viewFaild.findViewById(R.id.tv_dialog_faild_msg)).setText((String)msg.obj);
                    activity.mDialogUtils.startWaitingDialog(viewFaild);
                    this.sendEmptyMessageDelayed(CLOSEWAITINGDIALOG,2000);
                    break;
                case CLOSEWAITINGDIALOG://关闭Dialog
                    activity.mDialogUtils.closeDialog();
                    break;
                case CLOSEWAITINGDIALOGANDFINISH://关闭Dialog并finish
                    activity.mDialogUtils.closeDialog();
                    Intent intent = new Intent();
                    intent.putExtra("username",activity.cet_username.getText().toString());
                    activity.setResult(ContentValues.REGISTERSECCESS,intent);
                    activity.finish();
                    break;
            }
            super.handleMessage(msg);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mDialogUtils = new DialogUtils(this);
        initUI();
    }

    /**
     * 返回按钮
     * @param view 控件
     */
    public void backPress(View view) {
        this.finish();
    }

    /**
     * 初始化UI
     */
    private void initUI() {
        cet_username = (ClearEditText) findViewById(R.id.cet_register_username);
        cet_password = (ClearEditText) findViewById(R.id.cet_register_password);
        cet_qq = (ClearEditText) findViewById(R.id.cet_register_qq);
        cet_email = (ClearEditText) findViewById(R.id.cet_register_email);
        btn_register = (Button) findViewById(R.id.btn_register_register);
        cb_agree = (CheckBox) findViewById(R.id.cb_register_agree);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkMessageFinish()){
                    //获取填写数据
                    UserMessage userMessage = new UserMessage();
                    userMessage.setUserName(cet_username.getText().toString());
                    userMessage.setPassword(cet_password.getText().toString());
                    userMessage.setQq(cet_qq.getText().toString());
                    userMessage.setEmail(cet_email.getText().toString());
                    //开始注册
                    startRegister(userMessage);
                }
            }
        });
        cb_agree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                btn_register.setEnabled(isChecked);
            }
        });
    }

    /**
     * 开始注册
     * @param userMessage 注册信息
     */
    private void startRegister(UserMessage userMessage) {
        mDialogUtils.startWaitingDialog(View.inflate(this,R.layout.dialog_waiting,null));
        RequestParams requestParams = new RequestParams(ContentValues.REGISTERDOMAIN);
        requestParams.addParameter("username",userMessage.getUserName());
        requestParams.addParameter("password",userMessage.getPassword());
        requestParams.addParameter("qq",userMessage.getQq());
        requestParams.addParameter("email",userMessage.getEmail());
        x.http().post(requestParams, new Callback.CacheCallback<String>() {
            @Override
            public boolean onCache(String result) {
                return false;
            }
            @Override
            public void onSuccess(String result) {
                HashMap resultMap = JSON.parse(result);
                switch((Integer) resultMap.get("code")){
                    case 0://失败
                        Message msg = Message.obtain();
                        msg.what = MyHandler.REGISTERFAILD;
                        msg.obj = resultMap.get("message");
                        myHandler.sendMessage(msg);
                        break;
                    case 1://成功
                        myHandler.sendEmptyMessage(MyHandler.REGISTERSUCCESS);
                        break;
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Message msg = Message.obtain();
                msg.what = MyHandler.REGISTERFAILD;
                msg.obj = "网络错误！请稍后再试";
                myHandler.sendMessage(msg);
            }
            @Override
            public void onCancelled(CancelledException cex) {

            }
            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 检查并响应信息是否填写完整
     * @return 是否完整
     */
    private boolean checkMessageFinish() {
        if(cet_username.getText().toString().equals("")){
            cet_username.startShakeAnimation(null,3,"还没有输入用户名！");
            return false;
        }
        if(cet_password.getText().toString().equals("")){
            cet_password.startShakeAnimation(null,3,"还没有输入密码！");
            return false;
        }
        if(cet_qq.getText().toString().equals("")){
            cet_qq.startShakeAnimation(null,3,"还没有输入QQ！");
            return false;
        }
        if(cet_email.getText().toString().equals("")){
            cet_email.startShakeAnimation(null,3,"还没有输入邮箱！");
            return false;
        }
        return true;
    }


}
