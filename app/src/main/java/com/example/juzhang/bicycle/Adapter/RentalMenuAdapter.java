package com.example.juzhang.bicycle.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.juzhang.bicycle.R;
import com.example.juzhang.bicycle.Bean.RentalMenuMessage;

import java.util.List;

/**
 * 功能：租车菜单的adapter
 * 作者：JuZhang
 * 时间：2017/5/12
 */

public class RentalMenuAdapter extends RecyclerView.Adapter implements View.OnClickListener {
    private List<RentalMenuMessage> dataList;
    private SparseArray<View> viewSparseArray;
    private Context context;
    private OnItemClickListener listener;
    private int currentPosition = 0;

    public RentalMenuAdapter(Context context, List<RentalMenuMessage> menuTypeNameList) {
        dataList = menuTypeNameList;
        viewSparseArray = new SparseArray<>();
        this.context = context;
    }

    /**
     * 设置item起始高亮
     * @param position item索引
     * @param v item
     */
    public void initItemClick(int position,View v) {
        if(listener!=null){
            listener.OnItemClick(position,position,v, dataList);
            currentPosition = position;
        }
    }

    /**
     * 添加自定义View到指定位置
     * @param view 自定义View
     * @param postion 位置索引
     */
    public void addSelfView(View view ,int postion){
        dataList.add(postion,null);
        viewSparseArray.put(postion,view);
        notifyItemInserted(postion);
    }

    public interface OnItemClickListener{
        void OnItemClick(int position ,int oldPosition,View view,List<RentalMenuMessage> list);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        if(viewSparseArray.get(position)!=null){
            return position;
        }
        return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(viewType==-1){
            view = View.inflate(context, R.layout.item_rental_typemenu,null);
        }else{
            view = viewSparseArray.get(viewType);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(params);
        }
        return new MyViewHolder(view,viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(((MyViewHolder)holder).viewType!=-1)return;
        ((MyViewHolder)holder).tv_type.setTag(position);
        ((MyViewHolder)holder).tv_type.setText(dataList.get(position).getMenuName());
        ((MyViewHolder)holder).tv_type.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public void onClick(View v) {
        listener.OnItemClick((int)v.getTag(),currentPosition,v, dataList);
        currentPosition = (int)v.getTag();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder{
        int viewType;
        TextView tv_type;
        MyViewHolder(View itemView, int viewType) {
            super(itemView);
            this.viewType = viewType;
            if (viewType==-1)
                tv_type = (TextView) itemView.findViewById(R.id.tv_rental_item_type);
        }
    }
}
