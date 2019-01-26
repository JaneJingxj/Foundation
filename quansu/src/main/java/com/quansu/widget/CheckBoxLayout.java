package com.quansu.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ysnows.quansu.R;


/**
 * Created by xianguangjin on 16/6/1.
 * 自定义
 */

public class CheckBoxLayout extends LinearLayout implements View.OnClickListener, Checkable {


    private CheckBox cb;


    private TextView tvText;
    private OnCheckedListener listener;

    public CheckBoxLayout(Context context) {
        super(context);
        init(context, null, 0);
    }

    public CheckBoxLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);

    }

    public CheckBoxLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);

    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CheckBoxLayout);
        String text = a.getString(R.styleable.CheckBoxLayout_text_content);
        boolean checked = a.getBoolean(R.styleable.CheckBoxLayout_cb_checked, false);
        int text_color = a.getColor(R.styleable.CheckBoxLayout_text_color, -1);
        int text_size = a.getInt(R.styleable.CheckBoxLayout_text_size, -1);

        inflate(context, R.layout.widget_checkbox_mine, this);
        cb = findViewById(R.id.cb);
        tvText = findViewById(R.id.tv_text);

        setTvColor(text_color);
        setTvSize(text_size);

        setOnClickListener(this);

        if (!TextUtils.isEmpty(text)) {
            setTvText(text);
        }

        cb.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (listener != null) {
                listener.onChecked(this, isChecked);
            }
        });

        if (checked) {
            cb.setChecked(checked);
        }

        a.recycle();

    }

    private void setTvSize(int text_size) {
        tvText.setTextSize(text_size);


    }

    private void setTvColor(int text_color) {
        tvText.setTextColor(text_color);
    }

    /**
     * 设置文本文字
     *
     * @param text
     */
    public void setTvText(String text) {
        tvText.setText(text);
    }


    public void setOnCheckedListener(OnCheckedListener listener) {
        this.listener = listener;

    }


    @Override
    public void onClick(View v) {
        toggle();
    }

    @Override
    public void setChecked(boolean checked) {
        if (isChecked() != checked) {
            cb.setChecked(checked);
        }
        if (listener != null) {
            listener.onChecked(this, checked);
        }
    }

    @Override
    public boolean isChecked() {
        return cb.isChecked();
    }

    @Override
    public void toggle() {
        if (isChecked()) {
            setChecked(false);
        } else {
            setChecked(true);
        }
    }

    public TextView getTvText() {
        return tvText;
    }



}
