package com.quansu.widget.baseview;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.v4.graphics.ColorUtils;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;

import com.quansu.cons.Constants;

/**
 * Created by xianguangjin on 16/6/1.
 * 自定义
 */

public class BaseTextView extends android.support.v7.widget.AppCompatTextView {

    public BaseTextView(Context context) {
        this(context, null);

    }

    public BaseTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);


    }

    public BaseTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);

    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        Drawable background = getBackground();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setBackground(new RippleDrawable(ColorStateList.valueOf(Constants.COLOR_TRANSPARENT_DEEP), background, new ColorDrawable(Constants.COLOR_ACCENT)));
        } else {
            if (background instanceof ColorDrawable) {
                int color = ((ColorDrawable) background).getColor();
                ColorDrawable newColorDrawable = new ColorDrawable(ColorUtils.setAlphaComponent(color, 160));
                StateListDrawable stateListDrawable = new StateListDrawable();
                stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, newColorDrawable);
                stateListDrawable.addState(new int[]{}, background);
                ViewCompat.setBackground(this, stateListDrawable);
                setClickable(true);
            } else if (background instanceof BitmapDrawable) {
                Bitmap bitmap = ((BitmapDrawable) background).getBitmap();
                BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bitmap);
                bitmapDrawable.setAlpha(160);
                StateListDrawable stateListDrawable = new StateListDrawable();
                stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, bitmapDrawable);
                stateListDrawable.addState(new int[]{}, background);
                ViewCompat.setBackground(this, stateListDrawable);
                setClickable(true);
            } else {
                ColorStateList textColors = getTextColors();
                int defaultColor = textColors.getDefaultColor();
                int pressed = ColorUtils.setAlphaComponent(defaultColor, 160);
                setTextColor(createColorStateList(defaultColor, pressed, pressed, pressed));
            }
        }
    }


    /**
     * 对TextView设置不同状态时其文字颜色。
     */
    private ColorStateList createColorStateList(int normal, int pressed, int focused, int unable) {
        int[] colors = new int[]{pressed, focused, normal, focused, unable, normal};
        int[][] states = new int[6][];
        states[0] = new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled};
        states[1] = new int[]{android.R.attr.state_enabled, android.R.attr.state_focused};
        states[2] = new int[]{android.R.attr.state_enabled};
        states[3] = new int[]{android.R.attr.state_focused};
        states[4] = new int[]{android.R.attr.state_window_focused};
        states[5] = new int[]{};
        ColorStateList colorList = new ColorStateList(states, colors);
        return colorList;
    }


}
