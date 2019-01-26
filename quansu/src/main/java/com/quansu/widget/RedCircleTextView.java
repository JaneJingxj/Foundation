package com.quansu.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

import com.quansu.common.mvp.BaseView;
import com.ysnows.quansu.R;

/**
 * Created by xianguangjin on 16/6/1.
 * 自定义
 */

public class RedCircleTextView extends android.support.v7.widget.AppCompatTextView {


    private BaseView view;


    public RedCircleTextView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public RedCircleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);

    }

    public RedCircleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);

    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        setGravity(Gravity.CENTER);
        setTextColor(Color.WHITE);
        setBackgroundResource(R.drawable.circle_red);
    }


    public void setView(BaseView view) {
        this.view = view;
    }

}
