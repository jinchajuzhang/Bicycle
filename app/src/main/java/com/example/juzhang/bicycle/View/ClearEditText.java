package com.example.juzhang.bicycle.View;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;

import com.example.juzhang.bicycle.R;

import org.xutils.common.util.DensityUtil;

public class ClearEditText extends android.support.v7.widget.AppCompatEditText implements OnFocusChangeListener, TextWatcher {
    /**  
     * 删除按钮的引用  
     */  
    private Drawable mClearDrawable;   
  
    public ClearEditText(Context context) {   
        this(context, null);   
    }   
  
    public ClearEditText(Context context, AttributeSet attrs) {   
        //这里构造方法也很重要，不加这个很多属性不能再XML里面定义  
        this(context, attrs, android.R.attr.editTextStyle);   
    }   
  
    public ClearEditText(Context context, AttributeSet attrs, int defStyle) {  
        super(context, attrs, defStyle);  
        init();  
    }  
  
  
    @SuppressWarnings("deprecation")
	private void init() {   
        //获取EditText的DrawableRight,假如没有设置我们就使用默认的图片  
        mClearDrawable = getCompoundDrawables()[2];   
        if (mClearDrawable == null) {   
            mClearDrawable = getResources().getDrawable(R.drawable.icon_delete);
        }   
        mClearDrawable.setBounds(0, 0, DensityUtil.dip2px(20), DensityUtil.dip2px(20));
        setClearIconVisible(false);   
        setOnFocusChangeListener(this);   
        addTextChangedListener(this);   
    }   
  
  
    /**  
     * 因为我们不能直接给EditText设置点击事件，所以我们用记住我们按下的位置来模拟点击事件  
     * 当我们按下的位置 在  EditText的宽度 - 图标到控件右边的间距 - 图标的宽度  和  
     * EditText的宽度 - 图标到控件右边的间距之间我们就算点击了图标，竖直方向没有考虑  
     */  
    @Override   
    public boolean onTouchEvent(MotionEvent event) { 
    	if (event.getAction() == MotionEvent.ACTION_DOWN) {
			performClick();
		}
        if (getCompoundDrawables()[2] != null) {   
            if (event.getAction() == MotionEvent.ACTION_UP) {   
                boolean touchable = event.getX() > (getWidth()   
                        - getPaddingRight() - mClearDrawable.getIntrinsicWidth())   
                        && (event.getX() < ((getWidth() - getPaddingRight())));  
                if (touchable) {   
                    this.setText("");   
                }   
            }   
        }   
  
        return super.onTouchEvent(event);   
    }
    
    @Override
    public boolean performClick() {
    	return super.performClick();
    }
    /**  
     * 当ClearEditText焦点发生变化的时候，判断里面字符串长度设置清除图标的显示与隐藏  
     */  
    @Override   
    public void onFocusChange(View v, boolean hasFocus) {   
        if (hasFocus) {   
            setClearIconVisible(getText().length() > 0);   
        } else {   
            setClearIconVisible(false);   
        }   
    }   
  
  
    /**  
     * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去  
     * @param visible  
     */  
    protected void setClearIconVisible(boolean visible) {   
        Drawable right = visible ? mClearDrawable : null;   
        setCompoundDrawables(getCompoundDrawables()[0],   
                getCompoundDrawables()[1], right, getCompoundDrawables()[3]);   
    }   
  
  
    /**  
     * 当输入框里面内容发生变化的时候回调的方法  
     */  
    @Override   
    public void onTextChanged(CharSequence s, int start, int count,   
            int after) {   
        setClearIconVisible(s.length() > 0);   
    }   
  
    @Override   
    public void beforeTextChanged(CharSequence s, int start, int count,   
            int after) {   
  
    }   
  
    @Override   
    public void afterTextChanged(Editable s) {   
  
    }   
  
  
    /**  
     * 设置晃动动画  
     */  
    public void startShakeAnimation(AnimationListener listener){ 
    	Animation shakeAnimation = shakeAnimation(5);
    	shakeAnimation.setAnimationListener(listener);
        this.startAnimation(shakeAnimation);
    }

    /**
     * 设置晃动动画
     */
    public void startShakeAnimation(AnimationListener listener, int times, final String shakingText){
        final String oldText = this.getHint().toString();
        final int oldTextColor = this.getCurrentHintTextColor();
        Animation shakeAnimation = shakeAnimation(times);
        if(listener==null){
            shakeAnimation.setAnimationListener(new AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    ClearEditText.this.setHint(shakingText);
                    ClearEditText.this.setText("");
                    ClearEditText.this.setHintTextColor(Color.RED);
                    ClearEditText.this.requestFocus();
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    ClearEditText.this.setHint(oldText);
                    ClearEditText.this.setHintTextColor(oldTextColor);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
        }else{
            shakeAnimation.setAnimationListener(listener);
        }
        this.startAnimation(shakeAnimation);
    }


    /**  
     * 晃动动画  
     * @param counts 1秒钟晃动多少下  
     * @return  
     */  
    public static Animation shakeAnimation(int counts){  
        Animation translateAnimation = new TranslateAnimation(0, 10, 0, 0);  
        translateAnimation.setInterpolator(new CycleInterpolator(counts));  
        translateAnimation.setDuration(1000);  
        return translateAnimation;  
    }
}  
