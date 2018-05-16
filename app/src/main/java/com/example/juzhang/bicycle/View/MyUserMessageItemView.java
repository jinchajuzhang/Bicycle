package com.example.juzhang.bicycle.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.juzhang.bicycle.R;
import com.example.juzhang.bicycle.Utils.L;
import com.loopj.android.image.SmartImageView;

import org.xutils.common.util.DensityUtil;

/**
 * 功能：用户信息修改 自定义item
 * 作者：JuZhang
 * 时间：2017/5/24
 */

public class MyUserMessageItemView extends RelativeLayout {
    private SmartImageView siv_pic;
    private TextView tv_name;
    private TextView tv_text;

    public MyUserMessageItemView(Context context) {
        this(context,null);
    }

    public MyUserMessageItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyUserMessageItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initUI(context,attrs);
    }

    /**
     * 初始化UI
     * @param context 上下文
     * @param attrs 属性
     */
    private void initUI(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyUserMessageItemView);
        Drawable drawable = typedArray.getDrawable(R.styleable.MyUserMessageItemView_userpicsrc);
        CharSequence name = typedArray.getText(R.styleable.MyUserMessageItemView_name);
        CharSequence text = typedArray.getText(R.styleable.MyUserMessageItemView_usertext);
        float picwidth = typedArray.getDimension(R.styleable.MyUserMessageItemView_userpicwidth, 90);
        float picheight = typedArray.getDimension(R.styleable.MyUserMessageItemView_userpicheight, 90);
        boolean noline = typedArray.getBoolean(R.styleable.MyUserMessageItemView_noline,false);
        typedArray.recycle();

        View view = View.inflate(getContext(), R.layout.item_usermaessage_item,this);
        siv_pic = (SmartImageView) view.findViewById(R.id.siv_usermessage_item_pic);
        tv_name = (TextView) view.findViewById(R.id.tv_usermessage_item_name);
        tv_text = (TextView) view.findViewById(R.id.tv_usermessage_item_text);

        if(noline){
            view.findViewById(R.id.v_itemusermessage_line).setVisibility(View.GONE);
        }
        if(drawable!=null) {
            ViewGroup.LayoutParams layoutParams = siv_pic.getLayoutParams();
            layoutParams.width = DensityUtil.dip2px(picwidth);
            layoutParams.height = DensityUtil.dip2px(picheight);
            siv_pic.setLayoutParams(layoutParams);
            siv_pic.setImageDrawable(drawable);
            tv_text.setVisibility(View.GONE);
        }else{
            siv_pic.setVisibility(View.GONE);
        }
        tv_text.setText(text);
        tv_name.setText(name);
    }

    /**
     * 设置用户图片
     * @param picResouce 图片资源文件
     */
    public void setUserPic(int picResouce){
        siv_pic.setImageDrawable(ContextCompat.getDrawable(getContext(),picResouce));
        siv_pic.setVisibility(View.VISIBLE);
        tv_text.setVisibility(View.GONE);
    }

    /**
     * 设置用户图片
     * @param picUrl 图片地址
     */
    public void setUserPic(String picUrl){
        siv_pic.setImageUrl(picUrl);
        siv_pic.setVisibility(View.VISIBLE);
        tv_text.setVisibility(View.GONE);
    }

    /**
     * 设置用户信息
     * @param text 信息
     */
    public void setUserText(String text){
        tv_text.setText(text);
        tv_text.setVisibility(View.VISIBLE);
        siv_pic.setVisibility(View.GONE);
    }

    /**
     * 获取用户信息
     * @return 用户信息
     */
    public String getUserText(){
        return (String) tv_text.getText();
    }

    /**
     * 获取栏目名
     * @return 栏目名
     */
    public String getName(){
        return (String) tv_name.getText();
    }
}
