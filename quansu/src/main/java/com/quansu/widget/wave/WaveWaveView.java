package com.quansu.widget.wave;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by weihuagu on 2017/7/28.
 */

public class WaveWaveView extends View {

    //画笔
    private Paint mPaint;
    //抗锯齿
    private DrawFilter mDrawFilter;
    //屏宽
    private int screenWidth;
    //屏高
    private int screenHeight;
    //波浪点的列表
    private List<Float> postions;
    //临时列表
    private List<Float> temps = new ArrayList<Float>();
    //循环周期
    private float mCycle;
    //浪高
    private int WAVEHEIGHT = 20;
    //速度
    private int mSpeed = 1;

    public WaveWaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //初始化画笔
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor("#ff6977"));
        mDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //获取屏幕宽高
        screenWidth = getMeasuredWidth();
        screenHeight = getMeasuredHeight();
        //初始化周期
        mCycle = (float) (2 * Math.PI / screenWidth);
        postions = new ArrayList<Float>();
        for (int i = 0; i < screenWidth; i++) {
            //初始化波浪点
            float position = (float) (WAVEHEIGHT * Math.sin(mCycle * i) + 0);
            postions.add(position);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //draw
        canvas.setDrawFilter(mDrawFilter);
        drawWave(canvas);
    }

    public void drawWave(Canvas canvas) {
        for (int i = 0; i < postions.size(); i++) {
            //话竖线
            canvas.drawLine(i, screenHeight - postions.get(i) - 2 * WAVEHEIGHT, i, screenHeight, mPaint);
        }
        //清空临时数据
        temps.clear();
        int nowPosition = 0;
        Iterator<Float> iterator = postions.iterator();
        while (iterator.hasNext()) {
            //交换临时点的位置
            temps.add(iterator.next());
            iterator.remove();
            nowPosition = nowPosition + 1;
            if (nowPosition == mSpeed) {
                break;
            }
        }
        postions.addAll(temps);
        //重绘，会调用OnDraw方法
        invalidate();
    }
}
