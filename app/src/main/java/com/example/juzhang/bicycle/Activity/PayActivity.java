package com.example.juzhang.bicycle.Activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.PayTask;
import com.example.juzhang.bicycle.Bean.ServerResultJson;
import com.example.juzhang.bicycle.ContentValues.ContentValues;
import com.example.juzhang.bicycle.R;
import com.example.juzhang.bicycle.Utils.AliPay.PayResult;
import com.example.juzhang.bicycle.Utils.JSON;
import com.example.juzhang.bicycle.Utils.Net;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PayActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int SDK_PAY_FLAG = 1 ;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG:{
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(PayActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(PayActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        initUI();
        initData();
    }

    private void initData() {

    }

    private void initUI() {
        RadioButton rb_alipay = findViewById(R.id.rb_paytype_alipay);
        RadioButton rb_wx = findViewById(R.id.rb_paytype_wx);
        ((TextView)findViewById(R.id.tv_paytype_countprice)).setText((getIntent().getFloatExtra("countPrice",0.0F)+""));
        rb_alipay.setContentDescription("paygroup");
        rb_wx.setContentDescription("paygroup");
        rb_alipay.setOnClickListener(this);
        rb_wx.setOnClickListener(this);
        findViewById(R.id.btn_paytype_confirm).setOnClickListener(this);

    }


    public void backPress(View view) {
        this.finish();
    }

    @Override
    public void onClick(View v) {
        ArrayList<View> views = new ArrayList<>();
        findViewById(R.id.ll_paytype_content).findViewsWithText(views,"paygroup",View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION);
        switch(v.getId()){
            case R.id.btn_paytype_confirm:
                //判断支付方式
                for(View radio:views){
                    if(((RadioButton)radio).isChecked()){
                        switch(radio.getId()){
                            case R.id.rb_paytype_wx: //当前支付方式为微信支付
                                wxPay();
                                break;
                            case R.id.rb_paytype_alipay://当前支付方式为支付宝支付
                                aliPay();
                                break;
                        }
                        break;
                    }
                }
                break;
            default:
                for(View radio:views){
                    if(radio.getId()!=v.getId()){
                        ((RadioButton)radio).setChecked(false);
                    }
                }
        }
    }

    /**
     * 微信支付
     */
    private void wxPay() {
    }

    /**
     * 支付宝支付
     */
    private void aliPay() {
        Map<String,Object> params = new HashMap<>();
        Net.post(this, ContentValues.GETALIPAYSIGN, params, new Net.netCallBack() {
            @Override
            public void success(String data) {
                ServerResultJson result = JSON.parseToServerResult(data);
                final String orderInfo = (String) result.getData();
                Runnable payRunnable = new Runnable() {
                    @Override
                    public void run() {
                        //支付完成，回调，用于验证是否支付 成功
                        PayTask alipay = new PayTask(PayActivity.this);
                        Map<String, String> result = alipay.payV2(orderInfo, true);
                        Message msg = new Message();
                        msg.what = SDK_PAY_FLAG;
                        msg.obj = result;
                        mHandler.sendMessage(msg);
                    }
                };
                Thread payThread = new Thread(payRunnable);
                payThread.start();
            }

            @Override
            public void error(Throwable ex) {

            }
        });
    }

    /**
     * get the sdk version. 获取SDK版本号
     *
     */
    public void getSDKVersion() {
        PayTask payTask = new PayTask(this);
        String version = payTask.getVersion();
        Toast.makeText(this, version, Toast.LENGTH_SHORT).show();
    }

}
