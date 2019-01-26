package com.quansu.widget.globalaudio;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.ysnows.quansu.R;


//新的
//全局音频的
public class GlobalAudioView {

    private static View mFloatingView;
    private static boolean isshow=true;//默认没有初始化

    private static ViewGroup viewParent;


    public static void setShowTopView(Activity activity ){



        if(isshow) {

             viewParent = (ViewGroup) activity.getWindow().getDecorView();
            if (mFloatingView == null) {
                mFloatingView = new FloatingGlobalView(activity);
                mFloatingView.setId(R.id.myview);
            }

            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);

            // 在窗口的底部
            layoutParams.gravity = Gravity.BOTTOM;
            // 整个窗口
            // 将layout添加到窗口上层
            viewParent.addView(mFloatingView, layoutParams);
            isshow=false;
        }else{
            viewParent.removeView(mFloatingView);
            mFloatingView=null;
            isshow=true;
            setShowTopView(activity);



        }


    }

    //关闭悬浮框
    public static void setCloseTopView(){

        if (viewParent != null) {

            viewParent.removeView(mFloatingView);
            mFloatingView=null;
            isshow=true;


        }

    }


}
