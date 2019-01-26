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

public class BaseButton extends android.support.v7.widget.AppCompatButton {

    public BaseButton(Context context) {
        this(context, null);

    }

    public BaseButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);


    }

    public BaseButton(Context context, AttributeSet attrs, int defStyleAttr) {
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
            }
        }
    }


}
