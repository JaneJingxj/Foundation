package com.quansu.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.Checkable;

import com.quansu.widget.baseview.BaseLinearLayout;
import com.ysnows.quansu.R;


public class CheckItemLayoutView extends BaseLinearLayout implements Checkable {


    public CheckItemLayoutView(Context context) {
        super(context);
        init(null, 0);
    }

    public CheckItemLayoutView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public CheckItemLayoutView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        inflate(getContext(), R.layout.widget_check_item_layout_view, this);
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
    }


    @Override
    public void setChecked(boolean checked) {

    }

    @Override
    public boolean isChecked() {
        return false;
    }

    @Override
    public void toggle() {

    }
}
