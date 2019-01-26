package com.quansu.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.ysnows.quansu.R;


/**
 * Created by xianguangjin on 16/6/1.
 * 自定义
 */

public class LineView extends View {

    public LineView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public LineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }


    public LineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        Drawable background = getBackground();
        if (background == null) {
            setBackgroundResource(R.color.color_bg);
        }
    }


}
