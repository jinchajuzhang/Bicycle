package com.example.juzhang.bicycle.Adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.juzhang.bicycle.R;
import com.example.juzhang.bicycle.Bean.RentalCarMessage;
import com.loopj.android.image.SmartImageView;

import java.text.DecimalFormat;
import java.util.List;

/**
 * 功能：租车商品的adapter
 * 作者：JuZhang
 * 时间：2017/5/12
 */

public class RentalGoodsAdapter extends RecyclerView.Adapter implements View.OnClickListener {
    private List<RentalCarMessage> carMessageList;
    private Context context;
    private OnItemClickListener listener;

    public RentalGoodsAdapter(Context context, List<RentalCarMessage> carMessageList) {
        this.carMessageList = carMessageList;
        this.context = context;
    }

    public interface OnItemClickListener{
        void OnItemClick(int postion);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_rental_carmessage,null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MyViewHolder)holder).itemView.setTag(position);
        ((MyViewHolder)holder).itemView.setOnClickListener(this);

        RentalCarMessage rentalCarMessage = carMessageList.get(position);
        String imgUrl = rentalCarMessage.getBicycleMessageMainPictureSrc();

        //设置图片
        if(imgUrl==null||imgUrl.equals("")){
            ((MyViewHolder)holder).iv_carpicture.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.icon_imagefaild));
        }else{
            ((MyViewHolder)holder).iv_carpicture.setImageUrl(imgUrl);
        }

        ((MyViewHolder)holder).tv_carname.setText(rentalCarMessage.getBicycleMessageName());                    //设置车名
        ((MyViewHolder)holder).tv_discription.setText(rentalCarMessage.getBicycleMessageDiscription());         //设置描述
        String price = new DecimalFormat(".00").format(rentalCarMessage.getBicycleMessageStorePrice())+"/时";
        ((MyViewHolder)holder).tv_price.setText(price);                  //设置价格

        switch (rentalCarMessage.getHotType()){
            case 0://不设置
                break;
            case 1://设置为new
                ((MyViewHolder)holder).itemView.findViewById(R.id.iv_rental_item_newtype).setVisibility(View.VISIBLE);
                break;
            case 2://设置为hot
                ((MyViewHolder)holder).itemView.findViewById(R.id.iv_rental_item_hottype).setVisibility(View.VISIBLE);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return carMessageList.size();
    }

    @Override
    public void onClick(View v) {
        listener.OnItemClick((int)v.getTag());
    }

    private class MyViewHolder extends RecyclerView.ViewHolder{
        View itemView;
        SmartImageView iv_carpicture;
        TextView tv_carname;
        TextView tv_discription;
        TextView tv_price;
        MyViewHolder(View itemView) {
            super(itemView);
            this.itemView =itemView;
            iv_carpicture = (SmartImageView) itemView.findViewById(R.id.iv_rental_item_carpicture);
            tv_carname = (TextView) itemView.findViewById(R.id.tv_rental_item_carname);
            tv_discription = (TextView) itemView.findViewById(R.id.tv_rental_item_discription);
            tv_price = (TextView) itemView.findViewById(R.id.tv_rental_item_price);
        }
    }
}
