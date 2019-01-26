package com.quansu.widget;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;

import com.quansu.common.mvp.BaseView;


/**
 * Created by xianguangjin on 16/6/1.
 * 自定义
 */

public class VerticalScrollView extends NestedScrollView {


    private BaseView view;
    private float curX;
    private float curY;
    private float oldX;
    private float oldY;


    public VerticalScrollView(Context context) {
        this(context, null);

    }

    public VerticalScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);


    }

    public VerticalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);

    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {


    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);


    }

    public void setView(BaseView view) {
        this.view = view;
    }


//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        boolean intercepted = false;
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                Log.d("VerticalScrollView", "ACTION_DOWN");
//
//                intercepted = false;
//                break;
//            case MotionEvent.ACTION_MOVE:
//                Log.d("VerticalScrollView", "ACTION_MOVE");
//                curX = ev.getX();
//                curY = ev.getY();
//                float xDelta = Math.abs(curX - oldX);
//                float yDelta = Math.abs(curY - oldY);
//
//                Log.d("VerticalScrollView", "yDelta:" + xDelta);
//                Log.d("VerticalScrollView", "yDelta:" + yDelta);
//
//                if (yDelta > xDelta) {
//                    //纵向滑动
//                    intercepted = false;
//                } else {
//                    //横向滑动
//                    intercepted = false;
//                }
//
//                oldY = curY;
//                oldX = curX;
//                break;
//            case MotionEvent.ACTION_UP:
//                Log.d("VerticalScrollView", "ACTION_UP");
//
//                intercepted = false;
//                break;
//        }
//
//        return intercepted;
//    }
}
