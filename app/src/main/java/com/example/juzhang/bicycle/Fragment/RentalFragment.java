package com.example.juzhang.bicycle.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.Scroller;
import android.widget.TextView;

import com.example.juzhang.bicycle.Activity.BicycleDetailActivity;
import com.example.juzhang.bicycle.Activity.LoginActivity;
import com.example.juzhang.bicycle.Adapter.RentalGoodsAdapter;
import com.example.juzhang.bicycle.Adapter.MyViewPagerAdapter;
import com.example.juzhang.bicycle.Adapter.RentalMenuAdapter;
import com.example.juzhang.bicycle.ContentValues.ContentValues;
import com.example.juzhang.bicycle.Bean.MenuServerResultJson;
import com.example.juzhang.bicycle.R;
import com.example.juzhang.bicycle.Bean.RentalCarMessage;
import com.example.juzhang.bicycle.Bean.RentalHotMessage;
import com.example.juzhang.bicycle.Bean.RentalMenuMessage;
import com.example.juzhang.bicycle.Utils.JSON;
import com.example.juzhang.bicycle.Utils.L;
import com.example.juzhang.bicycle.Utils.Net;
import com.example.juzhang.bicycle.Utils.SharedPreferencesUtils;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.loopj.android.image.SmartImageView;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.circlenavigator.CircleNavigator;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * 功能：租车Fragment
 * 作者：JuZhang
 * 时间：2017/5/9
 */

public class RentalFragment extends Fragment {
    private View oldView = null;
    private RecyclerView rv_menu;
    private XRecyclerView rv_display,xlv_content;
    public ViewPager vp_hot;
    private MagicIndicator mi_indicator;

    private List<RentalMenuMessage> menuList = null;
    private List<RentalCarMessage> goodsList = null;

    private MyHandler myHandler = new MyHandler(this);
    private static class MyHandler extends Handler{

        public static final int CHANGEHOTPAGERITEM = 100;
        public static final int GETMENUTYPEFINISH = 101;
        private RentalFragment fragment;

        public MyHandler(RentalFragment fragement) {
            fragment = new WeakReference<>(fragement).get();
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what){
                case CHANGEHOTPAGERITEM:
                    fragment.vp_hot.setCurrentItem(fragment.vp_hot.getCurrentItem()+1,true);
                    this.sendEmptyMessageDelayed(CHANGEHOTPAGERITEM,2500);
                    break;
                case GETMENUTYPEFINISH:
                    fragment.reflashMenu(fragment.menuList);
                    break;
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        if(oldView == null){
            oldView = view = View.inflate(getContext(), R.layout.fragment_rental, null);
            initUI();
            initDate();
        }else{
            view = oldView;
        }
        return view;
    }

    /**
     * 初始化UI
     */
    private void initUI() {
        rv_menu = (RecyclerView) oldView.findViewById(R.id.rv_rental_menu);
        rv_display = (XRecyclerView) oldView.findViewById(R.id.xrv_rental_display);
        vp_hot = (ViewPager) oldView.findViewById(R.id.vp_rental_hot);
        mi_indicator = (MagicIndicator) oldView.findViewById(R.id.mi_rental_magicindicator);
    }

    /**
     * 初始化数据
     */
    public void initDate() {
        //刷新数据
        reflashHot();
        //getMenuFromServer();
        reflashCarMessage();
    }

    /**
     * 刷新热点
     */
    @SuppressLint("ClickableViewAccessibility")
    private void reflashHot() {
        //初始化数据，后期加为网络异步请求
        String desc1 = "单车的描述";
        String desc2 = "单车的描述";
        String desc3 = "单车的描述";
        RentalHotMessage rentalHotMessage1 = new RentalHotMessage();
        RentalHotMessage rentalHotMessage2 = new RentalHotMessage();
        RentalHotMessage rentalHotMessage3 = new RentalHotMessage();
        rentalHotMessage1.setDescription(desc1);
        rentalHotMessage1.setSmartImageViewUrl("");
        rentalHotMessage2.setDescription(desc2);
        rentalHotMessage2.setSmartImageViewUrl("");
        rentalHotMessage3.setDescription(desc3);
        rentalHotMessage3.setSmartImageViewUrl(ContentValues.HOST+"/easyBicycle/uploadPicture/41b5f297-5744-11e8-b8ad-f0761c2c2b4b2.jpg");
        rentalHotMessage1.setType(1);
        rentalHotMessage2.setType(2);

        //保存图片视图的容器
        final List<RentalHotMessage> dataList = new ArrayList<>();
        dataList.add(rentalHotMessage1);
        dataList.add(rentalHotMessage2);
        dataList.add(rentalHotMessage3);
        //设置指示器
        CircleNavigator circleNavigator = new CircleNavigator(getContext());
        circleNavigator.setCircleColor(Color.RED);
        circleNavigator.setCircleCount(dataList.size());
        mi_indicator.setNavigator(circleNavigator);

        //设置ViewPager
        MyViewPagerAdapter myViewPagerAdapteriew = new MyViewPagerAdapter(getContext(), dataList, vp_hot) {
            @Override
            public View getItemView(Object data) {
                RentalHotMessage tempData = (RentalHotMessage) data;
                View view = View.inflate(getContext(), R.layout.view_rental_viewpagerhot, null);
                //设置图片
                String imgUrl = tempData.getSmartImageViewUrl();
                SmartImageView smartImageView = view.findViewById(R.id.siv_rental_hotimage);
                if (imgUrl == null || imgUrl.equals("")) {
                    smartImageView.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.icon_imagefaild));
                } else {
                    //设置图片拉伸
                    smartImageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    smartImageView.setImageUrl(imgUrl);
                }
                //设置描述
                ((TextView) view.findViewById(R.id.tv_rental_item_hotdescription)).setText(tempData.getDescription());
                //设置类型图片
                switch (tempData.getType()) {
                    case 0://不设置类型
                        break;
                    case 1://设置为 new
                        view.findViewById(R.id.iv_rental_item_newtype).setVisibility(View.VISIBLE);
                        break;
                    case 2://设置为 hot
                        view.findViewById(R.id.iv_rental_item_hottype).setVisibility(View.VISIBLE);
                }
                return view;
            }
        };
        myViewPagerAdapteriew.setOnPageChangeListener(new MyViewPagerAdapter.PageChangeListener() {
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
        vp_hot.post(new Runnable() {
            @Override
            public void run() {
                myHandler.removeCallbacksAndMessages(null);
                myHandler.sendEmptyMessage(MyHandler.CHANGEHOTPAGERITEM);
            }
        });
        //设置ViewPager的滑动速度
        try {
            Field mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            Scroller scroller = new Scroller(getContext(), new AccelerateInterpolator()){
                @Override
                public void startScroll(int startX, int startY, int dx, int dy, int duration) {
                    super.startScroll(startX, startY, dx, dy, 500);
                }
            };
            mScroller.set(vp_hot, scroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
        vp_hot.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        myHandler.removeMessages(MyHandler.CHANGEHOTPAGERITEM);
                        break;
                    case MotionEvent.ACTION_UP:
                        myHandler.sendEmptyMessageDelayed(MyHandler.CHANGEHOTPAGERITEM,2500);
                        break;
                }
                return false;
            }
        });
        //设置adapter
        vp_hot.setAdapter(myViewPagerAdapteriew);
    }

    /**
     * 刷新车商品信息
     */
    private void reflashCarMessage() {
        Map<String,Object> params = new HashMap<>();
        params.put("currentPage",1);
        params.put("perPageItems",20);
        params.put("type",0);
        Net.post(this.getContext(), ContentValues.GETBICYCLEDOMAIN, params, new Net.netCallBack() {
            @Override
            public void success(String data) {
                List<Map<String,Object>> result = (List<Map<String, Object>>)JSON.parseToServerResult(data).getData();
                goodsList = new ArrayList<>();
                for(Map<String,Object> map : result){
                    goodsList.add(JSON.map2Bean(map, RentalCarMessage.class));
                }
                //刷新Adapter
                RentalGoodsAdapter goodsAdapter = new RentalGoodsAdapter(getContext(), goodsList);
                goodsAdapter.setOnItemClickListener(new RentalGoodsAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemClick(int postion) {
                        //Toast.makeText(getContext(),"点击了第"+postion+"个item",Toast.LENGTH_SHORT).show();
                        //这里传递单车信息
                        RentalCarMessage rentalCarMessage = goodsList.get(postion);
                        Intent intent = new Intent(getActivity(), BicycleDetailActivity.class);
                        intent.putExtra("carmessage", (Serializable) rentalCarMessage);
                        startActivity(intent);
                    }
                });

                rv_display.setLayoutManager(new LinearLayoutManager(getContext()));
                //设置商品间隙
                rv_display.addItemDecoration(new RecyclerView.ItemDecoration() {
                    @Override
                    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                        int childAdapterPosition = parent.getChildAdapterPosition(view);
                        if(childAdapterPosition<parent.getAdapter().getItemCount()&&childAdapterPosition>0)
                            outRect.bottom = 5;
                    }
                });
                //设置刷新监听
                rv_display.setLoadingListener(new XRecyclerView.LoadingListener() {
                    @Override
                    public void onRefresh() {
                        rv_display.refreshComplete();
                    }
                    @Override
                    public void onLoadMore() {
                        rv_display.loadMoreComplete();
                    }
                });
                rv_display.setLoadingMoreEnabled(false);
                rv_display.setAdapter(goodsAdapter);
            }

            @Override
            public void error(Throwable ex) {

            }
        });
    }

    /**
     * 检查用户是否登陆
     * @return 是否登陆
     */
    private boolean checkLogin() {
        return SharedPreferencesUtils.getObjectFromShare(getContext(), ContentValues.USERMESSAGE) != null;
    }

    /**
     * 刷新菜单类型
     * @param menuList
     */
    private void reflashMenu(List<RentalMenuMessage> menuList){
       if(menuList!=null){
            //刷新Adapter
            final RentalMenuAdapter rentalMenuAdapter = new RentalMenuAdapter(getContext(), this.menuList);
            rentalMenuAdapter.setOnItemClickListener(new RentalMenuAdapter.OnItemClickListener() {
                @Override
                public void OnItemClick(int position,int oldPosition,View view,List<RentalMenuMessage> list) {
                    ((TextView)rv_menu.getChildAt(oldPosition).findViewById(R.id.tv_rental_item_type)).setTextColor(ContextCompat.getColor(getContext(),R.color.rentalMenuTextColorNomal));
                    ((TextView)view.findViewById(R.id.tv_rental_item_type)).setTextColor(ContextCompat.getColor(getContext(),R.color.rentalMenuTextColorChecked));
                    //点击后进行刷新商品
                }
            });
            rv_menu.setLayoutManager(new LinearLayoutManager(getContext()));
            rv_menu.setAdapter(rentalMenuAdapter);

           /***
            * 添加栏目(未完成)
            */
            //栏目去重
           Set<String> typenameSet = new HashSet<>();
           for (RentalMenuMessage r:menuList){
               typenameSet.add(r.getNametype());
           }
           //栏目子数量统计
           Map<String,Integer> map = new HashMap<>();
           for(String type:typenameSet){
               int typeCount = 0;
               for(RentalMenuMessage rm:menuList){
                   if(rm.getNametype().equals(type)){
                       typeCount++;
                   }
               }
               map.put(type,typeCount);
           }
           //按子栏目数量添加栏目
           String[] type = typenameSet.toArray(new String[]{});
           int current = 0;
           for (int i=0;i<type.length;i++) {
               View division = View.inflate(getContext(), R.layout.item_rental_menudivision, null);
               ((TextView) division.findViewById(R.id.tv_rental_item_typedivision_text)).setText(type[i]);
               rentalMenuAdapter.addSelfView(division, current);
               current += map.get(type[i])+(i+1);
           }


           //异步等待初始化完毕
           rv_menu.post(new Runnable() {
               @Override
               public void run() {
                   rentalMenuAdapter.initItemClick(1,rv_menu.getChildAt(1));
               }
           });
       }
    }

    /**
     * 从服务器上获取菜单信息
     */
    private void getMenuFromServer() {
        RequestParams requestParams = new RequestParams(ContentValues.MENUTOTYPEDOMAIN);
        x.http().post(requestParams, new Callback.CacheCallback<String>() {
            @Override
            public boolean onCache(String result) {
                return false;
            }

            @Override
            public void onSuccess(String result) {
                L.v("reslutType:"+result);
                Gson gson = new Gson();
                MenuServerResultJson serverResultMenuJson = gson.fromJson(result, MenuServerResultJson.class);
                switch(serverResultMenuJson.getStatus()){
                    case 0://获取失败
                        break;
                    case 1://获取成功
                        menuList = serverResultMenuJson.getMsg();
                        myHandler.sendEmptyMessage(MyHandler.GETMENUTYPEFINISH);
                        break;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }
        });
    }

}
