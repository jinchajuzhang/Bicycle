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
import com.example.juzhang.bicycle.Utils.L;
import com.example.juzhang.bicycle.Utils.Net;
import com.example.juzhang.bicycle.View.ClearEditText;
import com.geetest.sdk.Bind.GT3GeetestBindListener;
import com.geetest.sdk.Bind.GT3GeetestUtilsBind;
import com.geetest.sdk.GT3GeetestButton;
import com.geetest.sdk.GT3GeetestListener;
import com.geetest.sdk.GT3GeetestUtils;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private DialogUtils mDialogUtils;

    private ClearEditText cet_username;
    private ClearEditText cet_password;
    private ClearEditText cet_comfirmpassword;
    private ClearEditText cet_mobile;
    private ClearEditText cet_email;
    private Button btn_register;
    private CheckBox cb_agree;
    private GT3GeetestButton ggb_verify;
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
        initData();
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
        cet_comfirmpassword = (ClearEditText) findViewById(R.id.cet_register_comfirmpassword);
        cet_mobile = (ClearEditText) findViewById(R.id.cet_register_mobile);
        cet_email = (ClearEditText) findViewById(R.id.cet_register_email);
        btn_register = (Button) findViewById(R.id.btn_register_register);
        cb_agree = (CheckBox) findViewById(R.id.cb_register_agree);
        ggb_verify = (GT3GeetestButton) findViewById(R.id.ggb_verify);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkMessageFinish()){
                    //获取填写数据
                    UserMessage userMessage = new UserMessage();
                    userMessage.setUserName(cet_username.getText().toString());
                    userMessage.setPassword(cet_password.getText().toString());
                    userMessage.setPhone(cet_mobile.getText().toString());
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
     * 初始化数据
     */
    private void initData() {
        GT3GeetestUtils instance = GT3GeetestUtils.getInstance(RegisterActivity.this);
        instance.getGeetest(ContentValues.CAPTCHAURL,ContentValues.VALIDATEURL, null, new GT3GeetestListener() {
            @Override
            public void gt3FirstResult(JSONObject jsonObject) {
                super.gt3FirstResult(jsonObject);
                String test  =jsonObject.toString();
                L.v(test);
            }

            @Override
            public void gt3DialogSuccessResult(String s) {
                super.gt3DialogSuccessResult(s);
                L.v(s);
            }
        });
    }

    /**
     * 开始注册
     * @param userMessage 注册信息
     */
    private void startRegister(UserMessage userMessage) {
        mDialogUtils.startWaitingDialog(View.inflate(this,R.layout.dialog_waiting,null));
        Map<String,Object> params = new HashMap<>();
        params.put("username",userMessage.getUserName());
        params.put("password",userMessage.getPassword());
        params.put("mobile",userMessage.getPhone());
        params.put("email",userMessage.getEmail());
        Net.post(this, ContentValues.REGISTERDOMAIN, params, new Net.netCallBack() {
            @Override
            public void success(String data) {
                ServerResultJson result = JSON.parseToServerResult(data);
                switch(result.getCode()){
                    case 0://失败
                        Message msg = Message.obtain();
                        msg.what = MyHandler.REGISTERFAILD;
                        msg.obj = result.getMessage();
                        myHandler.sendMessage(msg);
                        break;
                    case 1://成功
                        myHandler.sendEmptyMessage(MyHandler.REGISTERSUCCESS);
                        break;
                }
            }

            @Override
            public void error(Throwable ex) {
                Message msg = Message.obtain();
                msg.what = MyHandler.REGISTERFAILD;
                msg.obj = "网络错误！请稍后再试";
                myHandler.sendMessage(msg);
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
        if(!cet_comfirmpassword.getText().toString().equals(cet_password.getText().toString())){
            cet_comfirmpassword.startShakeAnimation(null,3,"两次密码输入不同！");
            return false;
        }
        if(cet_mobile.getText().toString().equals("")){
            cet_mobile.startShakeAnimation(null,3,"还没有输入手机号！");
            return false;
        }
        if(cet_email.getText().toString().equals("")){
            cet_email.startShakeAnimation(null,3,"还没有输入邮箱！");
            return false;
        }
        return true;
    }


}
