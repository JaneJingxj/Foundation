package com.quansu.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

import com.quansu.utils.UiUtils;
import com.quansu.widget.shapview.RectLinearLayout;
import com.ysnows.quansu.R;

import static android.text.TextUtils.isEmpty;

/**
 * Created by xianguangjin on 2016/12/26.
 */

public class IconTvView extends RectLinearLayout {
    private LayoutParams layoutParams;
    private ImageView imageView;
    private TextView textView;

    public IconTvView(Context context) {
        this(context, null);
    }

    public IconTvView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IconTvView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.IconTvView);
        int iconWidth = typedArray.getInt(R.styleable.IconTvView_icon_width, 15);
        int iconHeight = typedArray.getInt(R.styleable.IconTvView_icon_height, 15);
        Drawable iconSrc = typedArray.getDrawable(R.styleable.IconTvView_icon_src);

        int tvSize = typedArray.getInt(R.styleable.IconTvView_tv_size, 15);
        int tvColor = typedArray.getColor(R.styleable.IconTvView_tv_color, Color.BLACK);
        String tvText = typedArray.getString(R.styleable.IconTvView_tv_text);

        imageView = new ImageView(context);
        layoutParams = new LayoutParams(UiUtils.dp2Px(context, iconWidth), UiUtils.dp2Px(context, iconHeight));
        layoutParams.gravity = Gravity.CENTER_VERTICAL;
        addView(imageView, layoutParams);
        setIconSrc(iconSrc);

        textView = new TextView(context);
        layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER_VERTICAL;
        addView(textView, layoutParams);
        textView.setTextSize(tvSize);
        textView.setTextColor(tvColor);
        setTvText(tvText);

        typedArray.recycle();
    }

    public void setIconSrc(Drawable iconSrc) {
        if (iconSrc != null) {
            imageView.setImageDrawable(iconSrc);
        }
    }

    public void setTvText(String tvText) {
        if (!isEmpty(tvText)) {
            textView.setText(tvText);
        }
    }
}
