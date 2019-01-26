package com.quansu.widget.shapview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.RectF;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.ysnows.quansu.R;


/**
 * Created by xianguangjin on 2017/3/4.
 */

public class RectTextView extends AppCompatTextView {

    private float radius;
    private RectF rectF;

    public RectTextView(Context context) {
        this(context, null);
    }

    public RectTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RectTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.RectView);
        radius = attributes.getDimension(R.styleable.RectView_rv_radius, 1f);
        int strokeWidth = (int) attributes.getDimension(R.styleable.RectView_rv_stroke_width, 2f);
        int color = attributes.getColor(R.styleable.RectView_rv_stroke_color, Color.BLACK);
        int color1 = attributes.getColor(R.styleable.RectView_rv_bg_color, Color.WHITE);

        GradientDrawable drawable = (GradientDrawable) getResources().getDrawable(R.drawable.bg_rect);
        drawable.setCornerRadius(radius);
        drawable.setStroke((int) strokeWidth, color);
        drawable.setColor(color1);

        setBackground(drawable);

        attributes.recycle();
    }


}
