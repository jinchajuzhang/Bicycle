package com.example.juzhang.bicycle.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.juzhang.bicycle.Adapter.MyViewPagerAdapter;
import com.example.juzhang.bicycle.Bean.RentalCarMessage;
import com.example.juzhang.bicycle.Bean.SerializableList;
import com.example.juzhang.bicycle.Bean.ServerResultJson;
import com.example.juzhang.bicycle.ContentValues.ContentValues;
import com.example.juzhang.bicycle.R;
import com.example.juzhang.bicycle.Utils.JSON;
import com.example.juzhang.bicycle.Utils.L;
import com.example.juzhang.bicycle.Utils.Net;
import com.loopj.android.image.SmartImageView;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.circlenavigator.CircleNavigator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * 功能：单车详情页
 * 作者：JuZhang
 * 时间：2018/5/18
 */

public class BicycleDetailActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    private RentalCarMessage rentalCarMessage;
    private Map<String,Object> specMessage;

    private ViewPager vp_bicycleextraimg;
    private MagicIndicator mi_indicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bicycle_detail);
        initUI();
        initData();
    }

    private void initUI(){
        vp_bicycleextraimg = findViewById(R.id.vp_bicycledetail_bicycleextraimg);
        mi_indicator = findViewById(R.id.mi_bicycledetail_magicindicator);
    }

    private void initData(){
        Bundle extras = getIntent().getExtras();
        if(extras!=null) {
            rentalCarMessage = (RentalCarMessage) extras.get("carmessage");
            String bicycleMessageExtraPictureSrc = rentalCarMessage!=null?rentalCarMessage.getBicycleMessageExtraPictureSrc():null;
            String[] srcs = rentalCarMessage!=null?bicycleMessageExtraPictureSrc.split("~!"):null;
            assert srcs != null;
            List<String> srcList = new ArrayList<>(Arrays.asList(srcs));

            //设置指示器
            CircleNavigator circleNavigator = new CircleNavigator(this);
            circleNavigator.setCircleColor(Color.RED);
            circleNavigator.setCircleCount(srcList.size());
            mi_indicator.setNavigator(circleNavigator);
            //设置单车详情图适配器
            MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter(this,srcList,vp_bicycleextraimg){
                @Override
                public View getItemView(Object data) {
                    String imgUrl = (String)data;
                    View view = View.inflate(BicycleDetailActivity.this,R.layout.view_image_parent,null);
                    SmartImageView smartImageView =  view.findViewById(R.id.siv_image);
                    if (imgUrl == null || imgUrl.equals("")) {
                        smartImageView.setImageDrawable(ContextCompat.getDrawable(BicycleDetailActivity.this, R.drawable.icon_imagefaild));
                    } else {
                        //设置图片拉伸
                        smartImageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        smartImageView.setImageUrl(ContentValues.HOST+imgUrl);
                    }
                    return view;
                }
            };
            //设置单车详情图适配器滑动配置
            myViewPagerAdapter.setOnPageChangeListener(new MyViewPagerAdapter.PageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    mi_indicator.onPageScrolled(position,positionOffset,positionOffsetPixels);
                }
                @Override
                public void onPageSelected(int position) {
                    mi_indicator.onPageSelected(position);
                }
                @Override
                public void onPageScrollStateChanged(int state) {
                    mi_indicator.onPageScrollStateChanged(state);
                }
            });
            //添加图片到viewpager中
            vp_bicycleextraimg.setAdapter(myViewPagerAdapter);
            //获取单车规格
            Map<String,Object> params = new HashMap<>();
            params.put("bicycleStoreId",rentalCarMessage.getBicycleMessageStoreId());
            params.put("bicycleMesageId",rentalCarMessage.getBicycleMessageId());
            params.put("currentPage",1);
            params.put("perPageItems",999);
            //获取单车规格
            Net.post(this, ContentValues.GETBICYCLESPEC, params, new Net.netCallBack() {
                @Override
                public void success(String data) {
                    L.d(data);
                    ServerResultJson result = JSON.parseToServerResult(data);
                    List<Map<String,Object>> dataList = (List<Map<String, Object>>) result.getData();
                    Map<String,List<Map<String,Object>>> specMap = new HashMap<>();
                    //获取所有规格类型
                    Set<String> specSet = new TreeSet<>();
                    for (Map<String,Object> map : dataList){
                        specSet.add((String) map.get("bicycleAttributeName"));
                    }
                    //获取所有规格值
                    for(String spec : specSet){
                        List<Map<String,Object>> list = new ArrayList<>();
                        for (Map<String,Object> map : dataList){
                            if (map.get("bicycleAttributeName").equals(spec)){
                                list.add(map);
                            }
                            specMap.put(spec,list);
                        }
                    }
                    //设置UI
                    //获取已存在的模板
                    for(String key : specMap.keySet()){
                        View tempItem = View.inflate(BicycleDetailActivity.this,R.layout.item_bicycledetail_spec,null);
                        //设置规格名null
                        ((TextView)tempItem.findViewById(R.id.tv_bicycle_specName)).setText(key);
                        for(Map<String,Object> map : specMap.get(key)){
                            RadioButton radioButton = new RadioButton(BicycleDetailActivity.this);
                            Map<String,Object> tagMap = new HashMap<>();
                            tagMap.put("bicycleSpecName",map.get("bicycleAttributeName"));
                            tagMap.put("bicycleSpecValue",map.get("bicycleAttributeValue"));
                            tagMap.put("bicycleSpecStorePrice",map.get("bicycleSpecStorePrice"));
                            tagMap.put("bicycleSpecStock", map.get("bicycleSpecStock"));
                            radioButton.setTag(tagMap);
                            radioButton.setBackgroundResource(R.drawable.selector_checkbox_self);
                            radioButton.setText((CharSequence) map.get("bicycleAttributeValue"));
                            radioButton.setButtonDrawable(null);
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            layoutParams.setMargins(0,0,10,0);
                            RadioGroup group = tempItem.findViewById(R.id.rg_bicycle_specgroup);
                            group.setContentDescription("specRadioGroup");
                            group.setOnCheckedChangeListener(BicycleDetailActivity.this);
                            group.addView(radioButton,layoutParams);
                        }
                        //添加item到页面中
                        ViewGroup layout = (ViewGroup) ((ViewGroup) ((ViewGroup) BicycleDetailActivity.this.findViewById(android.R.id.content)).getChildAt(0)).getChildAt(0);
                        layout.addView(tempItem);
                    }
                }

                @Override
                public void error(Throwable ex) {

                }
            });
            //设置单车信息
            ((TextView)findViewById(R.id.tv_bicycle_name)).setText(rentalCarMessage.getBicycleMessageName());
            ((TextView)findViewById(R.id.tv_bicycle_deposit)).setText((rentalCarMessage.getBicycleMessageMarketPrice()+" 元"));
            ((TextView)findViewById(R.id.tv_bicycle_typename)).setText(rentalCarMessage.getBicycleMessageTypeName());
            ((TextView)findViewById(R.id.tv_bicycle_brand)).setText(rentalCarMessage.getBicycleMessageBrandName());
            ((SmartImageView)findViewById(R.id.siv_bicycle_brand)).setImageUrl(ContentValues.HOST+rentalCarMessage.getBicycleMessageBrandSrc(),R.drawable.icon_imagefaild);
            ((TextView)findViewById(R.id.tv_bicycle_promotion)).setText(rentalCarMessage.getBicycleMessagePromotion());
            ((TextView)findViewById(R.id.tv_bicycle_discription)).setText(rentalCarMessage.getBicycleMessageDiscription());
            ((TextView)findViewById(R.id.tv_bicycle_rentalnumber)).setText((rentalCarMessage.getBicycleRentalNumber()+""));
            findViewById(R.id.btn_bicycle_submitorder).setOnClickListener(this);
            findViewById(R.id.btn_bicycle_addcarts).setOnClickListener(this);
         }
    }

    /**
     * 返回响应
     * @param view 控件
     */
    public void backPress(View view) {
        this.finish();
    }

    /**
     * 规格参数RadioButton切换事件
     * @param group
     * @param checkedId
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        Map<String, Object> tag = (Map<String, Object>) group.findViewById(checkedId).getTag();
        if(specMessage == null) specMessage = new HashMap<>();
        specMessage.put((String) tag.get("bicycleSpecName"), tag.get("bicycleSpecValue"));
        changePriceAndStock();
    }

    /**
     * 检查单价格与库存
     */
    private void changePriceAndStock() {
        ArrayList<View> viewList = new ArrayList<>();
        ViewGroup rootLayout = (ViewGroup) (((ViewGroup)findViewById(android.R.id.content)).getChildAt(0));
        rootLayout.findViewsWithText(viewList,"specRadioGroup",View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION);
        Double price = null;
        Integer stock = null;
        for(View view : viewList){
            RadioGroup group = (RadioGroup)view;
            RadioButton button = group.findViewById(group.getCheckedRadioButtonId());
            if(button==null)continue;
            Map<String,Object> tagMap = (Map<String, Object>) button.getTag();
            Double bicycleSpecStorePrice = (Double) tagMap.get("bicycleSpecStorePrice");
            Integer bicycleSpecStock = (Integer) tagMap.get("bicycleSpecStock");
            if(price==null||price<bicycleSpecStorePrice) {
                price = bicycleSpecStorePrice;
                rentalCarMessage.setBicycleMessageStorePrice(Float.parseFloat(price+""));
            }
            if(stock==null||stock>bicycleSpecStock)
                stock = bicycleSpecStock;
        }

        if(price!=null)((TextView)findViewById(R.id.tv_bicycle_price)).setText((price+""));
        if(stock!=null)((TextView)findViewById(R.id.tv_bicycle_stock)).setText((stock+""));

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_bicycle_submitorder://跳转订单页面
                Intent intent = new Intent(this,OrderActivity.class);
                List <Map<String,Object>> list = new ArrayList<>();
                Map<String,Object> map = new HashMap<>();
                map.put("carmessage",rentalCarMessage);
                map.put("specMessage",specMessage);
                list.add(map);
                intent.putExtra("carmessages",new SerializableList(list));
                startActivity(intent);
                break;
            case R.id.btn_bicycle_addcarts://加入购物车
                Map<String, Object> params = new HashMap<>(JSON.bean2Map(rentalCarMessage));
                StringBuilder specStr = new StringBuilder();
                for(String specName : specMessage.keySet()){
                    specStr.append(specName).append("：").append(specMessage.get(specName)).append("     ");
                }
                params.put("specStr",specStr.toString());

                Net.post(this, ContentValues.ADDTOCARTS, params, new Net.netCallBack() {
                    @Override
                    public void success(String data) {
                        ServerResultJson result = JSON.parseToServerResult(data);
                        if(result.getCode()==200){
                            Toast.makeText(BicycleDetailActivity.this,"添加成功！",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(BicycleDetailActivity.this,"添加失败！",Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void error(Throwable ex) {}
                });
                break;
        }
    }
}
