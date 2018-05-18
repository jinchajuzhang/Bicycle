package com.example.juzhang.bicycle.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.juzhang.bicycle.Activity.BicycleDetailActivity;
import com.example.juzhang.bicycle.Utils.L;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：自定义Pager适配器(无限循环)
 * 作者：JuZhang
 * 时间：2017/5/14
 */

public abstract class MyViewPagerAdapter<T> extends PagerAdapter implements ViewPager.OnPageChangeListener {
    private int currentPosition = 0;
    private Context mContext;
    private ArrayList<View> views;
    private ViewPager mViewPager;

    private PageChangeListener listener;

    protected MyViewPagerAdapter(Context context, List<Object> datas, final ViewPager viewPager) {
        mContext = context;
        views = new ArrayList<>();
            //如果数据大于一条
        if(datas.size() > 1) {
            //添加最后一页到第一页
            datas.add(0,datas.get(datas.size()-1));
            //添加第一页(经过上行的添加已经是第二页了)到最后一页
            datas.add(datas.get(1));
        }
        for (Object data:datas) {
            views.add(getItemView(data));
        }
        mViewPager = viewPager;
        viewPager.setAdapter(this);
        viewPager.addOnPageChangeListener(this);
        viewPager.post(new Runnable() {
            @Override
            public void run() {
                viewPager.setCurrentItem(1,false);
            }
        });
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(views.get(position));
        return views.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }

    public abstract View getItemView(Object data);

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if(listener!=null){
//            L.v("current:"+currentPosition+",positon:"+position+",offset:"+positionOffset);
//            if(position==0){
//                position = views.size()-2;
//            }else if(currentPosition==views.size()-2&&position==views.size()-1){
//                positionOffset = 1-positionOffset;
//                position = 1;
//            }
            if(position-1<0)return;
            listener.onPageScrolled(position-1,positionOffset,positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        currentPosition = position;
        if(views.size()<2)return;
        if(position==0){
            position = views.size()-2;
        }else if(position==views.size()-1){
            position = 1;
        }
        if (listener!=null)
            listener.onPageSelected(position-1);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //若viewpager滑动未停止，直接返回
        if (state != ViewPager.SCROLL_STATE_IDLE) return;
        //若当前为第一张，设置页面为倒数第二张
        if (currentPosition == 0) {
            mViewPager.setCurrentItem(views.size()-2,false);
        } else if (currentPosition == views.size()-1) {
            //若当前为倒数第一张，设置页面为第二张
            mViewPager.setCurrentItem(1,false);
        }
        if (listener!=null)
            listener.onPageScrollStateChanged(state);
    }

    public interface PageChangeListener{
        void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);
        void onPageSelected(int position);
        void onPageScrollStateChanged(int state);
    }

    public void setOnPageChangeListener(PageChangeListener listener){
        this.listener = listener;
    }
}

