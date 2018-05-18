package com.example.juzhang.bicycle.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.juzhang.bicycle.R;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import net.lucode.hackware.magicindicator.MagicIndicator;

/**
 * 功能：ContentView
 * 作者：JuZhang
 * 时间：2017/5/14
 */

public class RentalContentAdapter extends RecyclerView.Adapter{
    private Context context;
    private View view;
    public RentalContentAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //View view = View.inflate(context, R.layout.item_rental_content,null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 1;
    }
    public View getView(){
        return view;
    }
    private class MyViewHolder extends RecyclerView.ViewHolder{
        MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;
        }
    }
}
