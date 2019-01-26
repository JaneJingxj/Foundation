package com.quansu.utils;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.quansu.widget.temptyview.TEmptyView;

/**
 * Created by xianguangjin on 16/7/8.
 * <p>
 * 我的GitHub: https://github.com/ysnows
 * <p>
 * 加油,做一个真的汉子
 * <p></p>
 */

public class EmptyViewUtils {

    /**
     * @param view
     * @return
     */
    public static TEmptyView genSimpleEmptyView(View view) {
        TEmptyView emptyView = new TEmptyView(view.getContext(), null);
        ViewParent parent = view.getParent();
        if (parent instanceof ViewGroup) {
            ((ViewGroup) parent).addView(emptyView);
            if (parent instanceof LinearLayout) {
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) emptyView.getLayoutParams();
                lp.height = LinearLayout.LayoutParams.MATCH_PARENT;
                lp.width = LinearLayout.LayoutParams.MATCH_PARENT;
                lp.gravity = Gravity.CENTER;
                emptyView.setLayoutParams(lp);

            } else if (parent instanceof RelativeLayout) {
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) emptyView.getLayoutParams();
                lp.height = RelativeLayout.LayoutParams.MATCH_PARENT;
                lp.width = RelativeLayout.LayoutParams.MATCH_PARENT;
                lp.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
                emptyView.setLayoutParams(lp);
            } else if (parent instanceof FrameLayout) {
                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) emptyView.getLayoutParams();
                lp.height = FrameLayout.LayoutParams.MATCH_PARENT;
                lp.width = FrameLayout.LayoutParams.MATCH_PARENT;
                lp.gravity = Gravity.CENTER;
                emptyView.setLayoutParams(lp);
            }
        }
        return emptyView;
    }


    /**
     * 获取嵌在 $view中的  emptyView
     *
     * @param view
     * @return
     */
    public TEmptyView getEmptyView(ViewGroup view) {
        TEmptyView emptyView = new TEmptyView(view.getContext(), null);
        ViewParent parent = view;
        if (parent instanceof ViewGroup) {
            ((ViewGroup) parent).addView(emptyView);
            if (parent instanceof LinearLayout) {
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) emptyView.getLayoutParams();
                lp.height = LinearLayout.LayoutParams.MATCH_PARENT;
                lp.width = LinearLayout.LayoutParams.MATCH_PARENT;
                lp.gravity = Gravity.CENTER;
                emptyView.setLayoutParams(lp);

            } else if (parent instanceof RelativeLayout) {
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) emptyView.getLayoutParams();
                lp.height = RelativeLayout.LayoutParams.MATCH_PARENT;
                lp.width = RelativeLayout.LayoutParams.MATCH_PARENT;
                lp.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
                emptyView.setLayoutParams(lp);
            } else if (parent instanceof FrameLayout) {
                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) emptyView.getLayoutParams();
                lp.height = FrameLayout.LayoutParams.MATCH_PARENT;
                lp.width = FrameLayout.LayoutParams.MATCH_PARENT;
                lp.gravity = Gravity.CENTER;
                emptyView.setLayoutParams(lp);
            }
        }
        return emptyView;
    }

    /**
     * 根据传入的 emptyView,将其嵌入到 view 中,并返回
     *
     * @param view
     * @param emptyView
     * @return
     */
    public View getEmptyView(ViewGroup view, View emptyView) {
        ViewParent parent = view;
        if (parent instanceof ViewGroup) {
            ((ViewGroup) parent).addView(emptyView);
            if (parent instanceof LinearLayout) {
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) emptyView.getLayoutParams();
                lp.height = LinearLayout.LayoutParams.MATCH_PARENT;
                lp.width = LinearLayout.LayoutParams.MATCH_PARENT;
                lp.gravity = Gravity.CENTER;
                emptyView.setLayoutParams(lp);

            } else if (parent instanceof RelativeLayout) {
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) emptyView.getLayoutParams();
                lp.height = RelativeLayout.LayoutParams.MATCH_PARENT;
                lp.width = RelativeLayout.LayoutParams.MATCH_PARENT;
                lp.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
                emptyView.setLayoutParams(lp);
            } else if (parent instanceof FrameLayout) {
                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) emptyView.getLayoutParams();
                lp.height = FrameLayout.LayoutParams.MATCH_PARENT;
                lp.width = FrameLayout.LayoutParams.MATCH_PARENT;
                lp.gravity = Gravity.CENTER;
                emptyView.setLayoutParams(lp);
            }
        }
        return emptyView;
    }


}
