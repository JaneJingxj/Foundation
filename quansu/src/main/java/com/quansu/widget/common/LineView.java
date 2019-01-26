package com.quansu.widget.common;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.quansu.common.mvp.BaseView;
import com.quansu.utils.UiUtils;
import com.quansu.widget.baseview.BaseLinearLayout;
import com.ysnows.quansu.R;

import static android.text.TextUtils.isEmpty;

/**
 * Created by xianguangjin on 16/6/1.
 * 自定义
 */

public class LineView extends BaseLinearLayout {

    private BaseView view;
    private TextView tvName;
    private EditText tvValue;
    private ImageView imgLeft;
    private ImageView imgRight;
    private com.quansu.widget.LineView line;


    public LineView(Context context) {
        this(context, null);

    }

    public LineView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);


    }

    public LineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);

    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        if (context instanceof BaseView) {
            this.view = (BaseView) context;
        }

        inflate(context, R.layout.widget_line, this);
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);

        tvName = findViewById(R.id.tv_name);
        tvValue = findViewById(R.id.tv_value);
        imgLeft = findViewById(R.id.img_left);
        imgRight = findViewById(R.id.img_right);
        line = findViewById(R.id.line);

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.LineView);
        String title = attributes.getString(R.styleable.LineView_t_title);
        String value = attributes.getString(R.styleable.LineView_t_value);
        String valueGravity = attributes.getString(R.styleable.LineView_t_value_gravity);
        String valueHint = attributes.getString(R.styleable.LineView_t_value_hint);
        Drawable leftImgSrc = attributes.getDrawable(R.styleable.LineView_img_left_src);
        Drawable rightImgSrc = attributes.getDrawable(R.styleable.LineView_img_right_src);
        int rightImgPadding = (int) attributes.getDimension(R.styleable.LineView_img_right_padding, 0f);
        int tValueMargin = (int) attributes.getDimension(R.styleable.LineView_t_value_margin, 0f);
        boolean editable = attributes.getBoolean(R.styleable.LineView_edit_able, false);
        boolean lineAble = attributes.getBoolean(R.styleable.LineView_line_able, true);

        int inputType = attributes.getInt(R.styleable.LineView_t_value_input_type, InputType.TYPE_CLASS_TEXT);

        setTitle(title);
        setInputType(inputType);
        setLeftImg(leftImgSrc);
        setRightImg(rightImgSrc);
        setRightImgPadding(rightImgPadding);
        setEditAble(editable);
        setValueHint(valueHint);
        setValue(value);
        setTValueMargin(tValueMargin);
        setLineAble(lineAble);
        attributes.recycle();

        if (!TextUtils.isEmpty(valueGravity) && valueGravity.equals("2")) {
            tvValue.setGravity(Gravity.LEFT);
            tvValue.setPadding(UiUtils.dp2px(getContext(), 20), 0, 0, 0);
        }

    }

    public void setLineAble(boolean lineAble) {
        line.setVisibility(lineAble ? VISIBLE : GONE);
    }

    public void setInputType(int inputType) {
        if (inputType != 0) {
            tvValue.setInputType(inputType);
        }
    }

    public void setTValueMargin(int tValueMargin) {
        if (tValueMargin > 0) {
            tvValue.setPadding(0, 0, tValueMargin, 0);
        }
    }

    public void setValueHint(String valueHint) {
        if (!isEmpty(valueHint)) {
            tvValue.setHint(valueHint);
        }
    }

    public void setEditAble(boolean editable) {
        tvValue.setFocusable(editable);
        tvValue.setFocusableInTouchMode(editable);
        tvValue.setClickable(editable);
    }

    public void setRightImgPadding(int rightImgPadding) {
        if (rightImgPadding > 0) {
            imgRight.setPadding(rightImgPadding, rightImgPadding, rightImgPadding, rightImgPadding);
        }
    }

    public void setRightImg(Drawable rightImgSrc) {
        if (rightImgSrc != null) {
            imgRight.setVisibility(VISIBLE);
            imgRight.setImageDrawable(rightImgSrc);
        }
    }

    public void setLeftImg(Drawable leftImgSrc) {
        if (leftImgSrc != null) {
            imgLeft.setVisibility(VISIBLE);
            imgLeft.setImageDrawable(leftImgSrc);
        }
    }


    public void setValue(String value) {
        if (!isEmpty(value)) {
            imgRight.setVisibility(VISIBLE);
            tvValue.setText(value);
        }
    }

    public void setTitle(String title) {
        if (!isEmpty(title)) {
            tvName.setText(title);
        }
    }


    public TextView getTvName() {
        return tvName;
    }

    public EditText getTvValue() {
        return tvValue;
    }

    public ImageView getImgLeft() {
        return imgLeft;
    }

    public ImageView getImgRight() {
        return imgRight;
    }
}
