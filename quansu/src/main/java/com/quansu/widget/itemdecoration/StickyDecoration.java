package com.quansu.widget.itemdecoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.view.View;

import com.quansu.utils.UiUtils;
import com.quansu.utils.pinyin.PinyinModel;
import com.quansu.widget.irecyclerview.IRecyclerView;

import java.util.ArrayList;

/**
 * Created by tangyangkai on 2016/12/27.
 */

public class StickyDecoration extends RecyclerView.ItemDecoration {

    private TextPaint textPaint;
    private Paint paint;
    private int topHeight;

    public StickyDecoration(Context context) {
        paint = new Paint();
        paint.setColor(Color.parseColor("#E6E6E6"));
        textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(45);
        textPaint.setColor(Color.parseColor("#AAAAAA"));
        topHeight = UiUtils.dp2Px(context, 30);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view) - 2;
        ArrayList<PinyinModel> data = ((IRecyclerView) parent).getIAdapter().getData();
        if (isFirstInGroup(data, position)) {
            outRect.top = topHeight;
        } else {
            outRect.top = 0;
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(view) - 2;
            ArrayList<PinyinModel> data = ((IRecyclerView) parent).getIAdapter().getData();
            if (isFirstInGroup(data, position)) {
                String textLine = data.get(position).getFirstPinyin();
                float top = view.getTop() - topHeight;
                float bottom = view.getTop();
                c.drawRect(left, top, right, bottom, paint);//绘制红色矩形
                Rect bounds = new Rect();
                textPaint.getTextBounds(textLine, 0, textLine.length(), bounds);
                int height = bounds.height();
                int i1 = (topHeight - height) / 2;
                c.drawText(textLine, left + 30, bottom - i1, textPaint);//绘制文本
            }
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        int position = ((LinearLayoutManager) (parent.getLayoutManager())).findFirstVisibleItemPosition() - 2;
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        c.drawRect(left, 0, right, topHeight, paint);//绘制红色矩形
        ArrayList<PinyinModel> data = ((IRecyclerView) parent).getIAdapter().getData();
        String textLine = data.get(position).getFirstPinyin();
        Rect bounds = new Rect();
        textPaint.getTextBounds(textLine, 0, textLine.length(), bounds);
        int height = bounds.height();
        int i1 = (topHeight - height) / 2;
        c.drawText(textLine, left + 30, topHeight - i1, textPaint);//绘制文本
    }


    private boolean isFirstInGroup(ArrayList<PinyinModel> data, int position) {
        boolean isFirst;
        if (position < 0) {
            isFirst = false;
        } else if (position == 0) {
            isFirst = true;
        } else if (position >= data.size()) {
            isFirst = false;
        } else {
            if (data.get(position).getFirstPinyin().equals(data.get(position - 1).getFirstPinyin())) {
                isFirst = false;
            } else {
                isFirst = true;
            }
        }
        return isFirst;
    }
}
