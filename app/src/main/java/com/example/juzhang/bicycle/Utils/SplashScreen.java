package com.example.juzhang.bicycle.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.example.juzhang.bicycle.R;

/**
 * 功能：创建并显示Splash
 * 作者：JuZhang
 * 时间：2017/5/8
 */

public class SplashScreen {
    public final static int SLIDE_LEFT = 1;
//    public final static int SLIDE_UP = 2;
//    public final static int FADE_OUT = 3;

    private Dialog splashDialog;

    private Activity activity;

    public SplashScreen(Activity activity){
        this.activity = activity;
    }

    private static class MyHandler extends Handler {
        private SplashScreen temp;
        MyHandler(SplashScreen splashScreen) {
            this.temp = splashScreen;
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    if(temp!=null) {
                        temp.removeSplashScreen();
                    }
                    break;
            }

        }
    }
    private MyHandler mHandler = new MyHandler(this);


    /**
     * 显示SplashScreen
     * @param imageResource 图片资源
     * @param animation 消失时的动画效果，取值可以是：SplashScreen.SLIDE_LEFT, SplashScreen.SLIDE_UP, SplashScreen.FADE
     * @param stillTime 持续时间
     */
    public void show(final int imageResource, final int animation, final int stillTime){
        Runnable runnable = new Runnable() {
            public void run() {
                DisplayMetrics metrics = new DisplayMetrics();
                LinearLayout root = new LinearLayout(activity);
                root.setMinimumHeight(metrics.heightPixels);
                root.setMinimumWidth(metrics.widthPixels);
                root.setOrientation(LinearLayout.VERTICAL);
                root.setBackgroundColor(Color.BLACK);
                root.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT, 0.0F));
                root.setBackgroundResource(imageResource);

                splashDialog = new Dialog(activity, android.R.style.Theme_Translucent_NoTitleBar);
                if ((activity.getWindow().getAttributes().flags & WindowManager.LayoutParams.FLAG_FULLSCREEN)
                        == WindowManager.LayoutParams.FLAG_FULLSCREEN) {
                    Window window = splashDialog.getWindow();
                    if(window==null)return;
                    window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                            WindowManager.LayoutParams.FLAG_FULLSCREEN);
                }

                Window window = splashDialog.getWindow();
                if(window==null)return;
                switch(animation){
                    case SLIDE_LEFT:
                        window.setWindowAnimations(R.style.DialogAnimationExitLeft);
                        break;
//                    case SLIDE_UP:
//                        window.setWindowAnimations(R.style.dialog_anim_slide_up);
//                        break;
//                    case FADE_OUT:
//                        window.setWindowAnimations(R.style.dialog_anim_fade_out);
//                        break;
                }

                splashDialog.setContentView(root);
                splashDialog.setCancelable(false);
                splashDialog.show();
                mHandler.removeMessages(0);
                mHandler.sendEmptyMessageDelayed(0,stillTime);
            }
        };
        activity.runOnUiThread(runnable);
    }

    /**
     * 移除SplashScreen
     */
    private void removeSplashScreen(){
        if (splashDialog != null && splashDialog.isShowing()) {
            splashDialog.dismiss();
            splashDialog = null;
        }
    }
}
