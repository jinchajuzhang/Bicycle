package com.example.juzhang.bicycle.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.juzhang.bicycle.R;

/**
 * 功能：自定义订单额外item
 * 作者：JuZhang
 * 时间：2017/5/22
 */

public class MyOrderExtraItem extends RelativeLayout {
    private CheckBox cb_checkbox;
    private TextView tv_content;

    public MyOrderExtraItem(Context context) {
        this(context,null);
    }

    public MyOrderExtraItem(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyOrderExtraItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initUI(context,attrs);
    }

    /**
     * 初始化UI
     * @param context 上下文
     * @param attrs 属性
     */
    private void initUI(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyOrderExtraItem);
        String text = typedArray.getString(R.styleable.MyOrderExtraItem_extraitemtext);
        boolean checked = typedArray.getBoolean(R.styleable.MyOrderExtraItem_checked,false);
        typedArray.recycle();

        View view = View.inflate(context, R.layout.item_order_extraitem, this);
        cb_checkbox = (CheckBox) view.findViewById(R.id.cb_item_orderitem_checkbox);
        tv_content = (TextView) view.findViewById(R.id.tv_item_orderitem_contenttext);

        tv_content.setText(text);
        cb_checkbox.setChecked(checked);
    }

    /**
     * 设置CheckBox
     * @param checked 是否选中
     */
    public void setChecked(boolean checked){
        cb_checkbox.setChecked(checked);
    }

    /**
     * 获取CheckBox是否选中
     * @return 是否选中
     */
    public boolean getChecked(){
        return cb_checkbox.isChecked();
    }

    /**
     * 设置CheckBox选择事件监听
     * @param listener 监听
     */
    public void setOnCheckBoxCheckedListener(CompoundButton.OnCheckedChangeListener listener){
        cb_checkbox.setOnCheckedChangeListener(listener);
    }
}
