package com.quansu.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.quansu.utils.SPUtil;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {

    private List<View> mViews;
    private Activity mActivity;
    private Class className;


    public ViewPagerAdapter(List<View> mViews, Activity mActivity, Class className) {
        this.mViews = mViews;
        this.mActivity = mActivity;
        this.className = className;
    }

    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        ((ViewPager) arg0).removeView(mViews.get(arg1));
    }

    @Override
    public void finishUpdate(View arg0) {
    }

    @Override
    public int getCount() {
        if (mViews != null) {
            return mViews.size();
        }
        return 0;
    }

//    @Override
//    public Object instantiateItem(View arg0, int arg1) {
//        View itemView = mViews.get(arg1);
//        ((ViewPager) arg0).addView(itemView, 0);
//
//        if (arg1 == mViews.size() - 1) {
//            Button mStartWeiboImageButton = (Button) ((RelativeLayout) itemView).getChildAt(1);
//            if (mStartWeiboImageButton != null) {
//
//                mStartWeiboImageButton.setOnClickListener(v -> {
//                    setGuided();
//                    goHome(className);
//                });
//            }
//        }
//
//        return mViews.get(arg1);
//    }

    private void goHome(Class className) {
        Intent intent = new Intent(mActivity, className);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mActivity.startActivity(intent);

        mActivity.finish();
    }

    private void setGuided() {
       // SPUtil.netInstance().putBoolean("isFirstIn", false);
        Log.e("setFirstIn",SPUtil.netInstance().getBoolean("isFirstIn")+"");
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return (arg0 == arg1);
    }

    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void startUpdate(View arg0) {
    }

}