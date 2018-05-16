package com.example.juzhang.bicycle.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.juzhang.bicycle.R;
import com.loopj.android.image.SmartImageView;

/**
 * 功能：我的页面 自定义Item
 * 作者：JuZhang
 * 时间：2017/5/17
 */

public class MyItemView extends LinearLayout {
    private SmartImageView siv_pic;
    private TextView tv_context;
    public MyItemView(Context context) {
        this(context,null);
    }

    public MyItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initUI(context,attrs);
    }

    /**
     * 初始化UI
     * @param context 上下文
     * @param attrs 属性
     */
    private void initUI(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyItemView);
        Drawable drawable = typedArray.getDrawable(R.styleable.MyItemView_picsrc);
        CharSequence text = typedArray.getText(R.styleable.MyItemView_text);
        typedArray.recycle();

        View view = View.inflate(getContext(), R.layout.item_my_item,this);
        siv_pic = (SmartImageView) view.findViewById(R.id.siv_my_item_pic);
        tv_context = (TextView) view.findViewById(R.id.tv_my_item_context);

        siv_pic.setImageDrawable(drawable);
        tv_context.setText(text);

    }
}
