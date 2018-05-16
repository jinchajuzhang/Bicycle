package com.example.juzhang.bicycle.Fragment;//打包对应包名
//package cn.a52nanami.mod1.fragment;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.juzhang.bicycle.R;

import org.xutils.x;

import java.util.List;


/**
 * Created by 97161 on 2017/5/23.
 */

public class GoodsFragment extends Fragment {

    private ListView lvGoods;
    private View mRoot;
    private List<GoodsInfoBean> mData;
    private OnItemClickListener clickListener = null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.fragment_goods, null);
        initUI();
        return mRoot;
    }
    private void initUI() {
        lvGoods = (ListView) mRoot.findViewById(R.id.lv_goods);
        if (mData != null) {
            lvGoods.setAdapter(new DataAdapter());
        }

        TextView emptyView = new TextView(getContext());
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        emptyView.setText("没有上架用于购买的商品！");
        emptyView.setTextSize(17);
        emptyView.setTextColor(Color.parseColor("#fe8300"));
        emptyView.setGravity(Gravity.CENTER);
        emptyView.setVisibility(View.GONE);
        ((ViewGroup)lvGoods.getParent()).addView(emptyView);
        lvGoods.setEmptyView(emptyView);


        lvGoods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (clickListener != null) {
                    GoodsInfoBean goodsInfoBean = mData.get(position);
                    clickListener.onClick(goodsInfoBean.id);
                }
            }
        });
    }
    class DataAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public GoodsInfoBean getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v;
            ViewHolder holder = null;
            if (convertView != null) {
                v = convertView;
                holder = (ViewHolder) v.getTag();
            } else {
                v = View.inflate(getContext(), R.layout.layout_item_goods, null);
                holder = new ViewHolder();
                holder.goodsIcon = (ImageView) v.findViewById(R.id.iv_goods_thumb);
                holder.goodsName = (TextView) v.findViewById(R.id.tv_goods_name);
                holder.goodsDesc = (TextView) v.findViewById(R.id.tv_goods_desc);
                holder.goodsPrice = (TextView) v.findViewById(R.id.tv_goods_price);
                holder.goodsCount = (TextView) v.findViewById(R.id.tv_goods_conut);
                v.setTag(holder);
            }
            GoodsInfoBean infoBean = getItem(position);
            x.image().bind(holder.goodsIcon,infoBean.pictureUrl);
            holder.goodsName.setText(infoBean.name);
            holder.goodsDesc.setText(infoBean.description);
            holder.goodsPrice.setText("￥" + infoBean.price);
            holder.goodsCount.setText(infoBean.count + "人付款");
            return v;
        }
    }
    public void initData(List<GoodsInfoBean> datas) {
        lvGoods.setEmptyView(View.inflate(getContext(),R.layout.dialog_waiting,null));
        mData = datas;
        if (lvGoods != null) {
            lvGoods.setAdapter(new DataAdapter());
        }
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        if (mData == null) {
            Log.w("warning","设置点击回调后,尚未初始化列表数据！");
        }
        clickListener = listener;
    }
    public interface OnItemClickListener{
        public void onClick(String id);
    }
    public static class ViewHolder {
        public ImageView goodsIcon;
        public TextView goodsName;
        public TextView goodsDesc;
        public TextView goodsPrice;
        public TextView goodsCount;

    }
    /**
     * 商品信息Bean
     */
    public static class GoodsInfoBean {
        public String id;
        public String pictureUrl;
        public String name;
        public String description;
        public String price;
        public String count;
    }
}
