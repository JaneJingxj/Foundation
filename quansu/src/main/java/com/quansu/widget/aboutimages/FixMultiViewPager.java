package com.quansu.widget.aboutimages;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.AccelerateInterpolator;
import android.widget.Scroller;

import java.lang.reflect.Field;

public class FixMultiViewPager extends ViewPager {

    private static final String TAG = FixMultiViewPager.class.getSimpleName();
    private ViewPageHelper helper;

    public FixMultiViewPager(Context context) {
        super(context);
    }

    public FixMultiViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);


        helper = new ViewPageHelper(this);


    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException ex) {
            Log.w(TAG, "onInterceptTouchEvent() ", ex);
            ex.printStackTrace();
        }
        return false;
    }


    @Override
    public void setCurrentItem(int item) {
        setCurrentItem(item, false);
    }


    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        ViewPageHelper.MScroller scroller = helper.getScroller();
        if (Math.abs(getCurrentItem() - item) > 1) {
            scroller.setNoDuration(true);
            super.setCurrentItem(item, smoothScroll);
            scroller.setNoDuration(false);
        } else {
            scroller.setNoDuration(false);
            super.setCurrentItem(item, smoothScroll);
        }
    }


}
