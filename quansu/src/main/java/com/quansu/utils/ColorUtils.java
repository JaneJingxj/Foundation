package com.quansu.utils;

import android.graphics.Color;

/**
 * Created by xianguangjin on 16/8/19.
 * <p>
 * 我的GitHub: https://github.com/ysnows
 * <p>
 * 加油,做一个真的汉子
 */

public class ColorUtils {

    public static int getColorWithAlpha(int color, float ratio) {
        int newColor = 0;

        int alpha = Math.round(Color.alpha(color) * ratio);

        int r = Color.red(color);

        int g = Color.green(color);

        int b = Color.blue(color);

        newColor = Color.argb(alpha, r, g, b);

        return newColor;

    }


}
