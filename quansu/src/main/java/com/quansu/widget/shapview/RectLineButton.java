package com.quansu.widget.shapview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;

import com.ysnows.quansu.R;


/**
 * Created by xianguangjin on 16/6/1.
 * 自定义
 */

public class RectLineButton extends android.support.v7.widget.AppCompatButton {

    private float radius;
    private float strokeWidth;
    private int strokeColor;

    public RectLineButton(Context context) {
        this(context, null);

    }

    public RectLineButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);


    }

    public RectLineButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);

    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.RectView);
        radius = attributes.getDimension(R.styleable.RectView_rv_radius, 1f);
        strokeWidth = attributes.getDimension(R.styleable.RectView_rv_stroke_width, 2f);
        strokeColor = attributes.getColor(R.styleable.RectView_rv_stroke_color, Color.BLACK);

        GradientDrawable drawable = (GradientDrawable) getResources().getDrawable(R.drawable.bg_rect);
        drawable.setCornerRadius(radius);
        drawable.setStroke((int) strokeWidth, strokeColor);

        setBackground(drawable);

        attributes.recycle();
    }

    public int getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
    }
}
