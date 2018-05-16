package com.example.juzhang.bicycle.Activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.juzhang.bicycle.AppData;
import com.example.juzhang.bicycle.ContentValues.ContentValues;
import com.example.juzhang.bicycle.Bean.ServerResultJson;
import com.example.juzhang.bicycle.Bean.UserMessage;
import com.example.juzhang.bicycle.R;
import com.example.juzhang.bicycle.Utils.DialogUtils;
import com.example.juzhang.bicycle.Utils.JSON;
import com.example.juzhang.bicycle.Utils.L;
import com.example.juzhang.bicycle.Utils.Net;
import com.example.juzhang.bicycle.Utils.SharedPreferencesUtils;
import com.example.juzhang.bicycle.Utils.UserMessageUtils;
import com.example.juzhang.bicycle.View.ClearEditText;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private ClearEditText cet_username;
    private ClearEditText cet_password;
    private Button btn_login;
    private TextView tv_forgetpassword;
    private DialogUtils mDialogUtils;
    private LoginActivity.MyHandler myHandler = new LoginActivity.MyHandler(this);
    private static class MyHandler extends Handler {
        private static final int LOGINSUCCESS = 100;
        private static final int LOGINFAILD = 101;
        private static final int CLOSEWAITINGDIALOG = 102;
        private static final int CLOSEWAITINGDIALOGANDFINISH = 103;
        private LoginActivity activity;

        MyHandler(LoginActivity temp) {
            this.activity = new WeakReference<>(temp).get();
        }
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case LOGINSUCCESS://登陆成功
                    //L.v(((UserMessage)msg.obj).toString());
                    //将登陆成功的信息保存
                    UserMessage userMessage = (UserMessage)msg.obj;
                    activity.saveLoginMessageToApp(userMessage);
                    activity.saveLoginMessageToSP(userMessage);
                    View successView = View.inflate(activity, R.layout.dialog_success, null);
                    ((TextView)successView.findViewById(R.id.tv_dialog_success_msg)).setText("登陆成功!");
                    activity.mDialogUtils.startWaitingDialog(successView);
                    this.sendEmptyMessageDelayed(CLOSEWAITINGDIALOGANDFINISH,1000);
                    break;
                case LOGINFAILD://登陆失败
                    View faildView = View.inflate(activity, R.layout.dialog_faild, null);
                    ((TextView)faildView.findViewById(R.id.tv_dialog_faild_msg)).setText((String)msg.obj);
                    activity.mDialogUtils.startWaitingDialog(faildView);
                    this.sendEmptyMessageDelayed(CLOSEWAITINGDIALOG,2000);
                    break;
                case CLOSEWAITINGDIALOG://关闭waitingdialog
                    activity.mDialogUtils.closeDialog();
                    break;
                case CLOSEWAITINGDIALOGANDFINISH://关闭waitingdialog并finish
                    activity.setResult(ContentValues.LOGINSUCCESS);
                    activity.finish();
                    break;
            }
            super.handleMessage(msg);
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mDialogUtils = new DialogUtils(this);
        initUI();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(resultCode){
            case ContentValues.REGISTERSECCESS:
                cet_username.setText(data.getStringExtra("username"));
                cet_password.requestFocus();
                break;
        }
    }

    /**
     * 初始化UI
     */
    private void initUI() {
        cet_username = findViewById(R.id.cet_login_username);
        cet_password = findViewById(R.id.cet_login_password);
        btn_login = findViewById(R.id.btn_login_login);
        TextView tv_register = findViewById(R.id.tv_login_register);
        tv_forgetpassword = findViewById(R.id.tv_login_forgetpassword);

        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(LoginActivity.this, RegisterActivity.class), ContentValues.REGISTERSECCESS);
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //提交登陆信息同时获取信息
                startLogin();
            }
        });

        cet_username.addTextChangedListener(new MyTextWatcher());
        cet_password.addTextChangedListener(new MyTextWatcher());
    }

    /**
     * 返回按钮
     * @param view 控件
     */
    public void backPress(View view) {
        this.finish();
    }

    /**
     * 开始登陆
     */
    private void startLogin() {
        mDialogUtils.startWaitingDialog(View.inflate(this,R.layout.dialog_waiting,null));
        Map<String,Object> params = new HashMap<>();
        params.put("username",cet_username.getText().toString());
        params.put("password",cet_password.getText().toString());
        Net.post(this, ContentValues.LOGINDOMAIN, params, new Net.netCallBack() {
            @Override
            public void success(String data) {
                ServerResultJson result = JSON.parseToServerResult(data);
                Message msg = Message.obtain();
                switch(result.getCode()){
                    case 0://登陆失败
                        msg.what = MyHandler.LOGINFAILD;
                        msg.obj = result.getMessage();
                        break;
                    case 200://登陆成功
                        if(saveSessionMessage(result)){
                            //登陆成功后获取用户信息
                            new UserMessageUtils(LoginActivity.this).getUserMessageFromServer(new UserMessageUtils.OnUserMessageGetFinishListener() {
                                @Override
                                public void onUserMessageGetFinish(UserMessage userMessage) {
                                   // L.v(userMessage.toString());
                                    Message msg = Message.obtain();
                                    if(userMessage==null){
                                        msg.what = MyHandler.LOGINFAILD;
                                        msg.obj = "系统错误，请稍后再试！";
                                    }else{
                                        msg.obj = userMessage;
                                        msg.what = MyHandler.LOGINSUCCESS;
                                    }
                                    myHandler.sendMessage(msg);
                                }
                            });
                        }else{
                            msg.what = MyHandler.LOGINFAILD;
                            msg.obj = "系统错误，请稍后再试！";
                        }
                        break;
                }
                myHandler.sendMessage(msg);
            }

            @Override
            public void error(Throwable ex) {
                L.v(ex.toString());
                ex.printStackTrace();
                Message msg = Message.obtain();
                msg.what = MyHandler.LOGINFAILD;
                msg.obj = "系统错误，请稍后再试！";
                myHandler.sendMessage(msg);
            }
        });
    }

    /**
     * 保存会话信息
     * @return 是否成功
     */
    private boolean saveSessionMessage(ServerResultJson data){
        Map<String, Object> result = (Map<String, Object>) data.getData();
        String userId = (String)result.get("userId");
        String token = (String)result.get("token");
        SharedPreferencesUtils.putObjectToShare(this,userId,"userId");
        SharedPreferencesUtils.putObjectToShare(this,token,"token");
        return true;
    }

    /**
     * 检查并响应信息是否填写完整
     */
    private void checkMessageFinish() {
        if(!cet_username.getText().toString().equals("")&&!cet_password.getText().toString().equals("")){
            if(!btn_login.isEnabled())
                btn_login.setEnabled(true);
        }else{
            if(btn_login.isEnabled())
                btn_login.setEnabled(false);
        }
    }

    /**
     * 保存登陆信息到SP
     * @param userMessage 登陆信息
     */
    private void saveLoginMessageToSP(UserMessage userMessage) {
        SharedPreferencesUtils.putObjectToShare(this,userMessage,ContentValues.USERMESSAGE);
    }

    /**
     * 保存登陆信息到App
     * @param userMessage 登陆信息
     */
    private void saveLoginMessageToApp(UserMessage userMessage) {
        ((AppData)getApplication()).setUserMessage(userMessage);
    }

    /**
     * 自定义TextWatcher
     */
    private class MyTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {checkMessageFinish();}
        @Override
        public void afterTextChanged(Editable s) {}
    }
}
