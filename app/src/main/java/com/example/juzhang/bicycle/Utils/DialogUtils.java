package com.example.juzhang.bicycle.Utils;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.juzhang.bicycle.R;
import com.example.juzhang.bicycle.View.ClearEditText;

import org.xutils.common.util.DensityUtil;

/**
 * 功能：Dialog的工具类
 * 作者：JuZhang
 * 时间：2017/5/16
 */

public class DialogUtils {
    private AlertDialog mDialog;
    private Context context;
    private Animation animationStart;
    private Animation animationEnd;
    private View contentView;
    private OnOkClickedListener listener;

    public DialogUtils(Context context) {
        this.context = context;
    }

    /**
     * 开启或刷新等待Dialog
     * @param v 视图
     */
    public void startWaitingDialog(View v) {
        if(mDialog !=null){
            mDialog.setContentView(v);
            return ;
        }
        mDialog = new AlertDialog.Builder(context).create();
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
        mDialog.setContentView(v);
        WindowManager.LayoutParams attributes;
        try {
            attributes = mDialog.getWindow().getAttributes();
            mDialog.getWindow().setWindowAnimations(R.style.WaitingDialogAnimation);
        }catch (NullPointerException e){
            e.printStackTrace();
            return;
        }
        attributes.width = DensityUtil.dip2px(300);
        attributes.height = DensityUtil.dip2px(130);
        mDialog.getWindow().setAttributes(attributes);
    }

    /**
     * 关闭对话框
     */
    public void closeDialog(){
        if(mDialog !=null){
            if(animationEnd!=null&&contentView!=null){
                animationEnd.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {}
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        mDialog.dismiss();
                        mDialog = null;
                    }
                    @Override
                    public void onAnimationRepeat(Animation animation) {}
                });
                contentView.startAnimation(animationEnd);
            }else{
                mDialog.dismiss();
                mDialog = null;
            }
        }
    }

    /**
     * 开启全屏动画Dialog
     * @param v 视图或布局
     * @param animStart 进入动画
     * @param animEnd 退出动画
     */
    public void startFullScreenDialogWithAnim(View v, int animStart, int animEnd){
        contentView = v;
        animationStart = AnimationUtils.loadAnimation(context,animStart);
        animationEnd = AnimationUtils.loadAnimation(context,animEnd);

        ViewGroup parent = (ViewGroup) v.getParent();
        if(parent!=null)
            parent .removeAllViews();

        //构建Dialog
        mDialog = new AlertDialog.Builder(context, R.style.BottomPopDialog).create();
        mDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if(keyCode==KeyEvent.KEYCODE_BACK){
                    closeDialog();
                }
                return true;
            }
        });
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();

        //调整Dialog位置和大小
        Point point = new Point();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getSize(point);
        WindowManager.LayoutParams attributes = mDialog.getWindow().getAttributes();
        attributes.width = point.x;
        attributes.height = DensityUtil.dip2px(300);
        mDialog.getWindow().setGravity(Gravity.BOTTOM);
        mDialog.getWindow().setAttributes(attributes);

        //显示Dialog且执行动画
        mDialog.setContentView(v);
        v.startAnimation(animationStart);
    }

    /**
     * 开启修改信息的对话框
     * @param title 标题
     * @param listener 确定监听
     */
    public void startUserMessageChangeDialog(String title, final String oldText, final OnOkClickedListener listener){
        this.listener = listener;
        mDialog = new AlertDialog.Builder(context).create();
        final View view = View.inflate(context, R.layout.dialog_usermessagechange, null);
        ((TextView)view.findViewById(R.id.tv_dialog_changemessage_title)).setText(title);
        final ClearEditText editText = (ClearEditText) view.findViewById(R.id.cet_dialog_changemessage_message);
        editText.setText(oldText);
        view.findViewById(R.id.btn_dialog_changemessage_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText.getText().toString();
                if(text.equals("")){
                    editText.startShakeAnimation(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            editText.setHint("还没有填写信息！");
                            editText.setHintTextColor(Color.RED);
                        }
                        @Override
                        public void onAnimationEnd(Animation animation) {
                            editText.setHint("");
                            editText.setText(oldText);
                        }
                        @Override
                        public void onAnimationRepeat(Animation animation) {}
                    });
                    return;
                }
                listener.onOkClicked(text);
                closeDialog();
            }
        });
        mDialog.show();
        mDialog.setContentView(view);
        Window window = mDialog.getWindow();
        assert window != null;
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = DensityUtil.dip2px(400);
        attributes.height = DensityUtil.dip2px(250);
        window.setAttributes(attributes);
    }

    public interface OnOkClickedListener{
        void onOkClicked(String message);
    }
}
