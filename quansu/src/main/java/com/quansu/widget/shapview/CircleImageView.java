package com.quansu.widget.shapview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.util.AttributeSet;

/**
 * Created by xianguangjin on 2017/3/4.
 */

public class CircleImageView extends android.support.v7.widget.AppCompatImageView {


    public CircleImageView(Context context) {
        this(context, null);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWith = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        int max = Math.max(measuredHeight, measuredWith);
        setMeasuredDimension(max, max);
    }

    @Override
    public void draw(Canvas canvas) {
        Path clipPath = new Path();
        clipPath.addCircle(getWidth() / 2, getHeight() / 2, Math.max(getWidth(), getHeight()) / 2, Path.Direction.CW);
        canvas.clipPath(clipPath);
        super.draw(canvas);
    }

}
