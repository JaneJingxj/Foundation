package com.quansu.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.quansu.common.mvp.BaseView;

/**
 * Created by xianguangjin on 16/6/1.
 * 自定义
 */

public class WaveView extends View {


    private BaseView view;

    private int mWidth = 0;
    private int mHeight = 0;
    private Path path;
    private Paint paint;
    private int waveHeight = 100; //波峰
    private int waveWidth = 500;  //波长
    private int originalY = 550;
    private Region region;
    private int dx = 0;
    private Bitmap mBitmap;

    private Path mPath;


    public WaveView(Context context) {
        this(context, null);

    }

    public WaveView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);


    }

    public WaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);

    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        if (context instanceof BaseView) {
            this.view = (BaseView) context;
        }
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.parseColor("#ff6977"));
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(5);
        path = new Path();
        mPath = new Path();
//        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_new_loading_animation);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;
        //依据模式获取预设的width和height
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            int desired = (int) (getPaddingLeft() + getPaddingRight());
            width = desired;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            int desired = (int) (getPaddingTop() + getPaddingBottom());
            height = desired;
        }
        mWidth = width;
        mHeight = height;
        waveWidth = mWidth / 2;
        //殊途同归，传递我们自定义的数据
        setMeasuredDimension(width, height);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setColor(Color.parseColor("#ff6977"));
        canvas.drawPath(path, paint);
        setDrwawData();
        /*Rect bounds = region.getBounds();
        if (bounds.top < originalY) {
            canvas.drawBitmap(mBitmap, bounds.right - mBitmap.getWidth() / 2, bounds.top - mBitmap.getHeight() / 2, paint);
        } else {
            canvas.drawBitmap(mBitmap, bounds.right - mBitmap.getWidth() / 2, bounds.bottom - mBitmap.getHeight() / 2, paint);
        }*/
    }

    /**
     * 绘制波浪
     */
    private void setDrwawData() {
        path.reset();
        int halfWaveWidth = waveWidth / 2; //半个波长
        path.moveTo(-waveWidth + dx, getHeight()/2-20);
        for (int i = -waveWidth; i < mWidth + waveWidth; i = i + waveWidth) {
            path.rQuadTo(halfWaveWidth / 2, -waveHeight, halfWaveWidth, 0);
            path.rQuadTo(halfWaveWidth / 2, waveHeight, halfWaveWidth, 0);
        }
        region = new Region();
        Region clip = new Region((int) (mWidth / 2 - 0.1), 0, mWidth / 2, mHeight * 2);
        region.setPath(path, clip);

        path.lineTo(mWidth, mHeight);
        path.lineTo(0, mHeight);
        path.close();
    }

    /**
     * 波浪移动属性动画
     */
    public void startAimate() {
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float factor = (float) valueAnimator.getAnimatedValue();
                dx = (int) ((waveWidth) * factor);
                invalidate();
            }
        });
        animator.setDuration(2000);
        animator.start();
    }

}
