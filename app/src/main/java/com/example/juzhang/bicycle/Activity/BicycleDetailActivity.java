package com.example.juzhang.bicycle.Activity;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.juzhang.bicycle.Adapter.MyViewPagerAdapter;
import com.example.juzhang.bicycle.Bean.RentalCarMessage;
import com.example.juzhang.bicycle.ContentValues.ContentValues;
import com.example.juzhang.bicycle.R;
import com.loopj.android.image.SmartImageView;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.circlenavigator.CircleNavigator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 功能：单车详情页
 * 作者：JuZhang
 * 时间：2018/5/18
 */

public class BicycleDetailActivity extends AppCompatActivity {

    private RentalCarMessage rentalCarMessage;

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
        }
    }

    /**
     * 返回响应
     * @param view 控件
     */
    public void backPress(View view) {
        this.finish();
    }
}
