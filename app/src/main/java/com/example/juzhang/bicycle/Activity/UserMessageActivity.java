package com.example.juzhang.bicycle.Activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.juzhang.bicycle.AppData;
import com.example.juzhang.bicycle.ContentValues.ContentValues;
import com.example.juzhang.bicycle.Bean.ServerResultJson;
import com.example.juzhang.bicycle.Bean.UserMessage;
import com.example.juzhang.bicycle.R;
import com.example.juzhang.bicycle.Utils.DialogUtils;
import com.example.juzhang.bicycle.Utils.L;
import com.example.juzhang.bicycle.Utils.SharedPreferencesUtils;
import com.example.juzhang.bicycle.Utils.UserMessageUtils;
import com.example.juzhang.bicycle.View.MyUserMessageItemView;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class UserMessageActivity extends AppCompatActivity {

    private MyUserMessageItemView mmiv_usermessage_headicon;
    private MyUserMessageItemView mmiv_usermessage_nickname;
    private MyUserMessageItemView mmiv_usermessage_username;
    private MyUserMessageItemView mmiv_usermessage_qq;
    private MyUserMessageItemView mmiv_usermessage_email;
    private MyUserMessageItemView mmiv_usermessage_realname;
    private MyUserMessageItemView mmiv_usermessage_sex;
    private MyUserMessageItemView mmiv_usermessage_phone;
    private MyUserMessageItemView mmiv_usermessage_wechat;

    private DialogUtils mDialogUtils;

    private UserMessageActivity.MyHandler myHandler = new UserMessageActivity.MyHandler(this);
    private static class MyHandler extends Handler {
        private static final int SUCCESS = 100;
        private static final int FAILD = 101;
        private static final int CLOSEWAITINGDIALOG = 102;
        private static final int CLOSEWAITINGDIALOGANDFINISH = 103;
        private UserMessageActivity activity;

        MyHandler(UserMessageActivity temp) {
            this.activity = new WeakReference<>(temp).get();
        }
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case SUCCESS://修改成功
                    //保存修改后的信息
                    View successView = View.inflate(activity, R.layout.dialog_success, null);
                    ((TextView)successView.findViewById(R.id.tv_dialog_success_msg)).setText("修改完成！");
                    activity.mDialogUtils.startWaitingDialog(successView);
                    activity.initData();
                    this.sendEmptyMessageDelayed(CLOSEWAITINGDIALOG,1000);
                    break;
                case FAILD://修改失败
                    View faildView = View.inflate(activity, R.layout.dialog_faild, null);
                    ((TextView)faildView.findViewById(R.id.tv_dialog_faild_msg)).setText((String)msg.obj);
                    activity.mDialogUtils.startWaitingDialog(faildView);
                    this.sendEmptyMessageDelayed(CLOSEWAITINGDIALOG,2000);
                    break;
                case CLOSEWAITINGDIALOG://关闭waitingdialog
                    activity.mDialogUtils.closeDialog();
                    break;
                case CLOSEWAITINGDIALOGANDFINISH://关闭waitingdialog并finish
                    break;
            }
            super.handleMessage(msg);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_message);
        mDialogUtils = new DialogUtils(this);
        setResult(ContentValues.FIXFINISH);
        initUI();
        initData();
    }

    /**
     * 初始化UI
     */
    private void initUI() {
        mmiv_usermessage_headicon = (MyUserMessageItemView)findViewById( R.id.mmiv_usermessage_headicon );
        mmiv_usermessage_nickname = (MyUserMessageItemView)findViewById( R.id.mmiv_usermessage_nickname );
        mmiv_usermessage_username = (MyUserMessageItemView)findViewById( R.id.mmiv_usermessage_username );
        mmiv_usermessage_qq = (MyUserMessageItemView)findViewById( R.id.mmiv_usermessage_qq );
        mmiv_usermessage_email = (MyUserMessageItemView)findViewById( R.id.mmiv_usermessage_email );
        mmiv_usermessage_realname = (MyUserMessageItemView)findViewById( R.id.mmiv_usermessage_realname );
        mmiv_usermessage_sex = (MyUserMessageItemView)findViewById( R.id.mmiv_usermessage_sex );
        mmiv_usermessage_phone = (MyUserMessageItemView)findViewById( R.id.mmiv_usermessage_phone );
        mmiv_usermessage_wechat = (MyUserMessageItemView)findViewById( R.id.mmiv_usermessage_wechat );
    }

    /**
     * 初始化数据
     */
    private void initData() {
        UserMessage userMessage = ((AppData)getApplication()).getUserMessage();
        mmiv_usermessage_headicon.setUserPic(userMessage.getUserImageUrl());
        mmiv_usermessage_nickname.setUserText(userMessage.getUserNickName());
        mmiv_usermessage_username.setUserText(userMessage.getUserName());
        mmiv_usermessage_qq.setUserText(userMessage.getQq());
        mmiv_usermessage_email.setUserText(userMessage.getEmail());
        mmiv_usermessage_realname.setUserText(userMessage.getRealName());
        mmiv_usermessage_sex.setUserText(userMessage.getSex());
        mmiv_usermessage_phone.setUserText(userMessage.getPhone());
        mmiv_usermessage_wechat.setUserText(userMessage.getWeChat());
    }

    /**
     * 点击返回键事件响应
     * @param view 控件
     */
    public void backPress(View view) {
        this.finish();
    }

    /**
     * Item点击事件响应
     * @param view item
     */
    public void buttonClick(View view) {
        switch(view.getId()){
            case R.id.mmiv_usermessage_username://不支持修改
                break;
            case R.id.mmiv_usermessage_headicon:

                break;
            case R.id.mmiv_usermessage_nickname:
                mDialogUtils.startUserMessageChangeDialog("修改昵称", mmiv_usermessage_nickname.getUserText(), new DialogUtils.OnOkClickedListener() {
                    @Override
                    public void onOkClicked(String message) {
                        prepareData("nickname",message);
                    }
                });
                break;
            case R.id.mmiv_usermessage_qq:
                mDialogUtils.startUserMessageChangeDialog("修改QQ", mmiv_usermessage_qq.getUserText(), new DialogUtils.OnOkClickedListener() {
                    @Override
                    public void onOkClicked(String message) {
                        prepareData("qq",message);
                    }
                });
                break;
            case R.id.mmiv_usermessage_email:
                mDialogUtils.startUserMessageChangeDialog("修改Email", mmiv_usermessage_email.getUserText(), new DialogUtils.OnOkClickedListener() {
                    @Override
                    public void onOkClicked(String message) {
                        prepareData("email",message);
                    }
                });
                break;
            case R.id.mmiv_usermessage_realname:
                mDialogUtils.startUserMessageChangeDialog("修改真实姓名", mmiv_usermessage_realname.getUserText(), new DialogUtils.OnOkClickedListener() {
                    @Override
                    public void onOkClicked(String message) {
                        prepareData("realname",message);
                    }
                });
                break;
            case R.id.mmiv_usermessage_sex:
                mDialogUtils.startUserMessageChangeDialog("修改性别", mmiv_usermessage_sex.getUserText(), new DialogUtils.OnOkClickedListener() {
                    @Override
                    public void onOkClicked(String message) {
                        prepareData("sex",message);
                    }
                });
                break;
            case R.id.mmiv_usermessage_phone:
                mDialogUtils.startUserMessageChangeDialog("修改电话", mmiv_usermessage_phone.getUserText(), new DialogUtils.OnOkClickedListener() {
                    @Override
                    public void onOkClicked(String message) {
                        prepareData("tel",message);
                    }
                });
                break;
            case R.id.mmiv_usermessage_wechat:
                mDialogUtils.startUserMessageChangeDialog("修改微信", mmiv_usermessage_wechat.getUserText(), new DialogUtils.OnOkClickedListener() {
                    @Override
                    public void onOkClicked(String message) {
                        prepareData("wechat",message);
                    }
                });
                break;
        }
    }

    /**
     * 准备提交数据
     * @param key 键
     * @param value 值
     */
    private void prepareData(String key, String value) {
        Map<String,String> dataMap = new HashMap<>();
        dataMap.put(key,value);
        submitMessage(dataMap);
    }

    /**
     * 提交信息
     * @param map 信息map
     */
    private void submitMessage(Map<String, String> map) {
        Set<Map.Entry<String, String>> entries = map.entrySet();
        RequestParams requestParams = new RequestParams(ContentValues.SETUSERMESSAGEDOMAIN);
        for (Map.Entry<String, String> next : entries) {
            requestParams.addParameter(next.getKey(),next.getValue());
        }
        final Message msg  = Message.obtain();
        mDialogUtils.startWaitingDialog(View.inflate(this,R.layout.dialog_waiting,null));
        x.http().post(requestParams, new Callback.CacheCallback<String>() {
            @Override
            public boolean onCache(String result) {
                return false;
            }
            @Override
            public void onSuccess(String result) {
                L.v("result1:"+result);
                Gson gson = new Gson();
                ServerResultJson serverResultJson = gson.fromJson(result, ServerResultJson.class);
                switch(serverResultJson.getCode()){
                    case 0:
                        msg.what = MyHandler.FAILD;
                        msg.obj = serverResultJson.getMessage();
                        myHandler.sendMessage(msg);
                        break;
                    case 1:
                        UserMessageUtils userMessageUtils = new UserMessageUtils(UserMessageActivity.this);
                        userMessageUtils.getUserMessageFromServer(new UserMessageUtils.OnUserMessageGetFinishListener() {
                            @Override
                            public void onUserMessageGetFinish(UserMessage userMessage) {
                                if(userMessage!=null){
                                    SharedPreferencesUtils.putObjectToShare(UserMessageActivity.this,userMessage,ContentValues.USERMESSAGE);
                                    ((AppData)getApplication()).setUserMessage(userMessage);
                                    msg.what = MyHandler.SUCCESS;
                                    myHandler.sendMessage(msg);
                                }
                            }
                        });
                        break;
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                msg.what = MyHandler.FAILD;
                msg.obj = "网络错误，请稍后再试";
                myHandler.sendMessage(msg);
            }
            @Override
            public void onCancelled(CancelledException cex) {}

            @Override
            public void onFinished() {}
        });
    }
}
