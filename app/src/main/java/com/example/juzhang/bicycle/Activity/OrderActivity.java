package com.example.juzhang.bicycle.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.juzhang.bicycle.Bean.RentalCarMessage;
import com.example.juzhang.bicycle.Bean.SerializableList;
import com.example.juzhang.bicycle.ContentValues.ContentValues;
import com.example.juzhang.bicycle.R;
import com.example.juzhang.bicycle.Utils.DialogUtils;
import com.example.juzhang.bicycle.View.ClearEditText;
import com.example.juzhang.bicycle.View.MyOrderExtraItem;
import com.loopj.android.image.SmartImageView;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 订单的类
 */
public class OrderActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private View layout_choiseridetype;

    private TextView tv_carname;

    private TextView tv_cardescription;
    private TextView tv_carnumber;
    private TextView tv_price;
    private TextView tv_cartype;

    private TextView tv_title;
    private TextView tv_sumprice;
    private TextView tv_rentaltime;
    private TextView tv_purpose;
    private TextView tv_distributiontype;

    private SmartImageView siv_pic;
    private MyOrderExtraItem moei_ridetoyou;
    private MyOrderExtraItem moei_ridetoyouself;
    private TextView tv_contact_ridetoyouself;
    private ClearEditText cet_inputaddress;

    //定义的骑送上门的额外金额
    private float mPayforRide = 1f;
    //选择收货方式的对话框
    private DialogUtils mChoiseRideTypeDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        initUI();
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        tv_title.setText(getString(R.string.tv_startrental));
        SerializableList serializableList = (SerializableList) getIntent().getSerializableExtra("carmessages");
        List<Map<String, Object>> carList = serializableList.getList();
        LinearLayout root = findViewById(R.id.ll_order_bicycledetail);
        for(Map<String,Object> listMap : carList){
            RentalCarMessage rentalCarMessage = (RentalCarMessage) listMap.get("carmessage");
            Map<String,Object> specMap = (Map<String, Object>) listMap.get("specMessage");
            View tempItem = View.inflate(this, R.layout.item_order_bicycledetail, null);
            tempItem.setContentDescription("cardetailitem");
            ((SmartImageView)tempItem.findViewById(R.id.siv_order_pic)).setImageUrl(ContentValues.HOST+rentalCarMessage.getBicycleMessageMainPictureSrc(),R.drawable.ic_imagefaild);
            ((TextView)tempItem.findViewById(R.id.tv_order_carname)).setText(rentalCarMessage.getBicycleMessageName());

            StringBuilder specStr = new StringBuilder();
            for(String specName : specMap.keySet()){
                specStr.append(specName).append("：").append(specMap.get(specName)).append("     ");
            }
            ((TextView)tempItem.findViewById(R.id.tv_order_spec)).setText(specStr);
            ((TextView)tempItem.findViewById(R.id.tv_order_price)).setText((rentalCarMessage.getBicycleMessageStorePrice()+""));
            ((TextView)tempItem.findViewById(R.id.tv_order_deposit)).setText((rentalCarMessage.getBicycleMessageMarketPrice()+"元"));
            root.addView(tempItem);
        }
        mChoiseRideTypeDialog = new DialogUtils(this);
        countSumPrice();
    }

    /**
     * 检查送货方式并返回额外金额
     * @return 额外金额
     */
    private float checkDistributionType() {
        if(tv_distributiontype.getText().equals(getString(R.string.tv_ridetoyou))){ //骑送上门
            ((TextView)findViewById(R.id.tv_order_payforride)).setText("（含 "+new DecimalFormat("0.00").format(mPayforRide)+" 元骑送费）");
            return mPayforRide;
        }else if(tv_distributiontype.getText().equals(getString(R.string.tv_ridetoyouself))){//上店自提
            ((TextView)findViewById(R.id.tv_order_payforride)).setText("");
            return 0;
        }
        return 0;
    }

    /**
     * 初始化UI
     */
    private void initUI() {
        layout_choiseridetype = View.inflate(this, R.layout.view_choiseridetype, null);
        tv_sumprice = (TextView) findViewById(R.id.tv_order_sumprice);
        tv_distributiontype = (TextView) findViewById(R.id.tv_order_distributiontype);
        tv_title  = (TextView) findViewById(R.id.tv_order_head_title);
        moei_ridetoyou = (MyOrderExtraItem) layout_choiseridetype.findViewById(R.id.moei_order_ridetoyou);
        moei_ridetoyouself = (MyOrderExtraItem) layout_choiseridetype.findViewById(R.id.moei_order_ridetoyouself);
        cet_inputaddress = (ClearEditText) findViewById(R.id.cet_order_inputaddress);
        tv_contact_ridetoyouself = (TextView) findViewById(R.id.tv_order_contact_ridetoyouself);
        moei_ridetoyou.setOnCheckBoxCheckedListener(this);
        moei_ridetoyouself.setOnCheckBoxCheckedListener(this);
    }

    /**
     * 返回响应
     * @param view 控件
     */
    public void backPress(View view) {
        this.finish();
    }

    /**
     * 增加数量
     * @param v
     * @param offset 增加值
     */
    private void addNum(View v, int offset){
        TextView carNumber = ((ViewGroup)v.getParent()).findViewById(R.id.tv_carnumber);
        String temp = (Integer.parseInt((String) carNumber.getText())+offset)+"";
        carNumber.setText(temp);
        countSumPrice();
    }

    /**
     * 减少数量
     * @param v
     * @param offset 减少值
     */
    private void reduceNum(View v, int offset){
        TextView carNumber = ((ViewGroup)v.getParent()).findViewById(R.id.tv_carnumber);
        int num = Integer.parseInt((String) carNumber.getText());
        num = num-offset>=0?num-offset:num;
        String temp1 = num+"";
        carNumber.setText(temp1);
        countSumPrice();
    }

    /**
     * 计算总价
     */
    private void countSumPrice() {
        Float sumPrice = 0.0F;
        ArrayList<View> viewGroupList = new ArrayList<>();
        findViewById(R.id.ll_order_bicycledetail).findViewsWithText(viewGroupList,"cardetailitem",View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION);
        if(viewGroupList.size()>=1){
            for(View view : viewGroupList){
                Float deposit = Float.parseFloat(((TextView)view.findViewById(R.id.tv_order_deposit)).getText().toString().replace("元",""));
                Integer carNumber = Integer.parseInt(((TextView)view.findViewById(R.id.tv_carnumber)).getText().toString());
                sumPrice += (deposit*carNumber);
            }
        }
        ((TextView)findViewById(R.id.tv_order_sumprice)).setText((sumPrice+""));
    }

    /**
     * 关闭选择配送方式的布局
     */
    private void closeChoiseRideTypeLayout() {
        mChoiseRideTypeDialog.closeDialog();
    }

    /**
     * 开启选择配送方式的布局
     */
    private void startChoiseRideTypeLayout() {
        mChoiseRideTypeDialog.startFullScreenDialogWithAnim(layout_choiseridetype,R.anim.ll_choiseridertype_enter,R.anim.ll_choiseridertype_exit);
    }

    /**
     * 点击事件响应
     * @param v 控件
     */
    public void buttonClick(View v) {
        switch(v.getId()){
            case R.id.btn_order_reduce:
                reduceNum(v,1);
                countSumPrice();
                break;
            case R.id.btn_order_add:
                addNum(v,1);
                countSumPrice();
                break;
            case R.id.rl_order_distributiontype:
                startChoiseRideTypeLayout();
                break;
            case R.id.tv_order_chooseridetype_close:
                closeChoiseRideTypeLayout();
                break;
            case R.id.btn_order_submit:
                //联网提交订单
                Intent intent = new Intent(this, PayActivity.class);
                intent.putExtra("countPrice",Float.parseFloat(((TextView)findViewById(R.id.tv_order_sumprice)).getText().toString()));
                startActivity(intent);
                break;
        }
    }

    /**
     * 显示提交对话框
     */
    private void showSubmitDialog() {
        String msg = "";
        if(tv_distributiontype.getText().equals(getString(R.string.tv_ridetoyou))){
            if(!checkAddress())return;
            msg = "下单成功，请耐心等待骑送人员送货！";
        }else if(tv_distributiontype.getText().equals(getString(R.string.tv_ridetoyouself))){
            msg = "下单成功，"+getString(R.string.tv_shopaddress);
        }
        View view = View.inflate(this, R.layout.dialog_success, null);
        ((TextView)view.findViewById(R.id.tv_dialog_success_msg)).setText(msg);

        final DialogUtils successDialog = new DialogUtils(this);
        successDialog.startWaitingDialog(view);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                successDialog.closeDialog();
            }
        },2000);
    }

    /**
     * 检查地址
     * @return 是否通过
     */
    private boolean checkAddress() {
        if(cet_inputaddress.getText().toString().equals("")&&tv_title.getText().equals(getString(R.string.tv_startrental))){
            cet_inputaddress.startShakeAnimation(new Animation.AnimationListener() {
                int currentHintTextColor;
                @Override
                public void onAnimationStart(Animation animation) {
                    currentHintTextColor = cet_inputaddress.getCurrentHintTextColor();
                    cet_inputaddress.setHintTextColor(Color.RED);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    cet_inputaddress.setHintTextColor(currentHintTextColor);
                }
                @Override
                public void onAnimationRepeat(Animation animation) {}
            });
            return false;
        }
        return true;
    }

    /**
     *  Checkbox 联动
     * @param buttonView CheckBox组
     * @param isChecked 是否选中
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch(((View)buttonView.getParent().getParent()).getId()){
            case R.id.moei_order_ridetoyou:
                if(isChecked){
                    moei_ridetoyouself.setChecked(false);
                    tv_distributiontype.setText(getString(R.string.tv_ridetoyou));

                    tv_contact_ridetoyouself.setVisibility(View.GONE);
                    cet_inputaddress.setVisibility(View.VISIBLE);

                    countSumPrice();
                    closeChoiseRideTypeLayout();
                }else{
                    if(!moei_ridetoyouself.getChecked()&&!moei_ridetoyou.getChecked()){
                        moei_ridetoyou.setChecked(true);
                        countSumPrice();
                        closeChoiseRideTypeLayout();
                    }
                }
                break;
            case R.id.moei_order_ridetoyouself:
                if(isChecked){
                    moei_ridetoyou.setChecked(false);
                    tv_distributiontype.setText(getString(R.string.tv_ridetoyouself));

                    tv_contact_ridetoyouself.setVisibility(View.VISIBLE);
                    cet_inputaddress.setVisibility(View.GONE);

                    countSumPrice();
                    closeChoiseRideTypeLayout();
                }else{
                    if(!moei_ridetoyouself.getChecked()&&!moei_ridetoyou.getChecked()){
                        moei_ridetoyouself.setChecked(true);
                        countSumPrice();
                        closeChoiseRideTypeLayout();
                    }
                }
                break;
        }
    }
}
