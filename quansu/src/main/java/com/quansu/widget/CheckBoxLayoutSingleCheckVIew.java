package com.quansu.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.quansu.common.mvp.BaseView;

import java.util.ArrayList;

/**
 * Created by xianguangjin on 16/6/1.
 * 自定义
 */

public class CheckBoxLayoutSingleCheckVIew extends RelativeLayout {


    private BaseView view;
    private ArrayList<CheckBoxLayout> allCheckBoxLayout = new ArrayList<>();
    private String checkedText = "";


    public CheckBoxLayoutSingleCheckVIew(Context context) {
        super(context);
        init(context, null, 0);
    }

    public CheckBoxLayoutSingleCheckVIew(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);

    }

    public CheckBoxLayoutSingleCheckVIew(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);

    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        for (CheckBoxLayout checkBoxLayout : allCheckBoxLayout) {
            checkBoxLayout.setOnCheckedListener((view1, isChecked) -> {
                if (isChecked) {
                    checkedText = checkBoxLayout.getTvText().getText().toString().trim();
                    for (CheckBoxLayout boxLayout : allCheckBoxLayout) {
                        if (!boxLayout.equals(checkBoxLayout)) {
                            boxLayout.setChecked(false);
                        }
                    }
                } else {
                    boolean isAllFalse = true;
                    for (CheckBoxLayout boxLayout : allCheckBoxLayout) {
                        if (boxLayout.isChecked()) {
                            isAllFalse = false;
                            break;
                        }
                    }
                    Log.d("CheckBoxLayoutSingleChe", "isAllFalse:" + isAllFalse);
                    if (isAllFalse) {
                        checkedText = "";
                    }

                }
            });
        }
    }


    @Override
    public void onViewAdded(View child) {
        super.onViewAdded(child);
        if (child instanceof CheckBoxLayout) {
            allCheckBoxLayout.add((CheckBoxLayout) child);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

    }

    /**
     * 获取选择的文本
     *
     * @return
     */
    public String getChecked() {
        return checkedText;
    }

    public void setView(BaseView view) {
        this.view = view;
    }

    public void clearChecked() {
        for (CheckBoxLayout checkBoxLayout : allCheckBoxLayout) {
            checkBoxLayout.setChecked(false);
        }


    }
}
