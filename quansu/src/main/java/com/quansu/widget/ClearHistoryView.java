package com.quansu.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

import com.ysnows.quansu.R;


/**
 * Created by xianguangjin on 16/6/1.
 * 自定义
 */

public class ClearHistoryView extends android.support.v7.widget.AppCompatTextView {


    public ClearHistoryView(Context context) {
        this(context, null);

    }

    public ClearHistoryView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);


    }

    public ClearHistoryView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);

    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        setText(context.getString(R.string.empty_the_search_history));
        setGravity(Gravity.CENTER);
        setTextSize(16f);
        setTextColor(getResources().getColor(android.R.color.darker_gray));
    }


}
