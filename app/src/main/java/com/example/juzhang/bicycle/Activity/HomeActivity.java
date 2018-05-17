package com.example.juzhang.bicycle.Activity;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.juzhang.bicycle.ContentValues.ContentValues;
import com.example.juzhang.bicycle.Fragment.GoodsFragment;
import com.example.juzhang.bicycle.Fragment.MyFragment;
import com.example.juzhang.bicycle.Fragment.RentalFragment;
import com.example.juzhang.bicycle.Fragment.ReturnFragment;
import com.example.juzhang.bicycle.R;
import com.example.juzhang.bicycle.Utils.L;
import com.example.juzhang.bicycle.Utils.SplashScreen;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class HomeActivity extends FragmentActivity implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    private List<Fragment> mFragmentList;

    private ViewPager vp_fragmentviewpager;
    private  MyFragementViewPagerAdapter myFragementViewPagerAdapter;
    private RadioGroup rg_bottom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initSplashWindow();
        initUI();
        initData();
    }

    /**
     * 初始化UI
     */
    private void initUI() {
        vp_fragmentviewpager = (ViewPager) findViewById(R.id.vp_home_fragmentviewpager);
        rg_bottom = (RadioGroup) findViewById(R.id.rg_home_bottom);

    }

    /**
     * 初始化数据
     */
    private void initData() {
        mFragmentList = new ArrayList<>();
        mFragmentList.addAll(Arrays.asList(new RentalFragment(),new ReturnFragment(),new MyFragment()));
        myFragementViewPagerAdapter = new MyFragementViewPagerAdapter(getSupportFragmentManager(),mFragmentList);
        vp_fragmentviewpager.setAdapter(myFragementViewPagerAdapter);
        vp_fragmentviewpager.addOnPageChangeListener(this);

        rg_bottom.setOnCheckedChangeListener(this);
        findViewById(R.id.rb_home_rental).setOnClickListener(this);
    }

    /**
     * 确认RadioButton是否选中
     */
    private void sureRadioButtonChecked(int position) {
        switch(position){
            case 0:
                RadioButton rb_rental = ((RadioButton)findViewById(R.id.rb_home_rental));
                if(!rb_rental.isChecked()){
                    rb_rental.setChecked(true);
                }
                break;
            case 1:
                RadioButton rb_return = ((RadioButton)findViewById(R.id.rb_home_return));
                if(!rb_return.isChecked()){
                    rb_return.setChecked(true);
                }
                break;
            case 2:
                RadioButton rb_my = ((RadioButton)findViewById(R.id.rb_home_my));
                if(!rb_my.isChecked()){
                    rb_my.setChecked(true);
                }
                break;
        }
    }

    /**
     * 初始化splash
     */
    private void initSplashWindow() {
        SplashScreen splash = new SplashScreen(this);
        splash.show(R.drawable.bg, SplashScreen.SLIDE_LEFT,3000);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {
/*        if(position==2){
            List<GoodsFragment.GoodsInfoBean> goodsInfoBeen = new ArrayList<>();
            for(int i=0;i<15;i++){
                GoodsFragment.GoodsInfoBean goodsInfoBean = new GoodsFragment.GoodsInfoBean();
                goodsInfoBean.price = new DecimalFormat("0").format((Math.random()+0.1)*900);
                goodsInfoBean.description="本车品描述只是用于展示";
                goodsInfoBean.name="测试用单车名";
                goodsInfoBean.count = new DecimalFormat("0").format((Math.random()+0.1)*900);
                goodsInfoBeen.add(goodsInfoBean);
            }*
            ((GoodsFragment)((MyFragementViewPagerAdapter)vp_fragmentviewpager.getAdapter()).getItem(position)).initData(goodsInfoBeen);

        }*/
        sureRadioButtonChecked(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * RadioButton 选择监听
     * @param group RadioGroup
     * @param checkedId checkedId
     */
    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch(checkedId){
            case R.id.rb_home_rental:
                vp_fragmentviewpager.setCurrentItem(0,false);
                break;
            case R.id.rb_home_return:
                vp_fragmentviewpager.setCurrentItem(1,false);
                break;
            case R.id.rb_home_my:
                vp_fragmentviewpager.setCurrentItem(3,false);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case ContentValues.MYTOLOGIN:
                MyFragment myFragment = (MyFragment) ((FragmentPagerAdapter) vp_fragmentviewpager.getAdapter()).getItem(2);
                myFragment.resultForActivity(resultCode);
                break;
            case ContentValues.RETURNTOLOGIN:
                ReturnFragment returnFragment = (ReturnFragment) ((FragmentPagerAdapter) vp_fragmentviewpager.getAdapter()).getItem(1);
                returnFragment.resultForLoginActivity(resultCode);
                break;
            case ContentValues.MYTOSETUSERMESSAGE:
                MyFragment myFragment2 = (MyFragment) ((FragmentPagerAdapter) vp_fragmentviewpager.getAdapter()).getItem(2);
                myFragment2.resultForActivity(resultCode);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.rb_home_rental){
            if(vp_fragmentviewpager.getCurrentItem()==0){
                if(myFragementViewPagerAdapter!=null){
                    ((RentalFragment)myFragementViewPagerAdapter.getItem(0)).initDate();
                }
            }
        }
    }

    /**
     * ViewPager适配器
     */
    private class MyFragementViewPagerAdapter extends FragmentPagerAdapter{

        List<Fragment> fl;

        MyFragementViewPagerAdapter(FragmentManager fm, List<Fragment> fl) {
            super(fm);
            this.fl = fl;
        }

        @Override
        public Fragment getItem(int position) {
            return fl.get(position);
        }

        @Override
        public int getCount() {
            return fl.size();
        }

        public void setItemList(List<Fragment> fl){
            this.fl = fl;
        }
    }
}
