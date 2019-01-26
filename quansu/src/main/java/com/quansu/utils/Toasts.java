package com.quansu.utils;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

/**
 * Created by xianguangjin on 16/7/13.
 * <p>
 * 我的GitHub: https://github.com/ysnows
 * <p>
 * 加油,做一个真的汉子
 */

public class Toasts {

    private static Toast toast;

    public static void show(Context context, View view, String string) {

//        if (view == null) {
        toast(context, string);
//        } else {
//            snack(view, string);
//        }

    }

    public static void show(Context context, View view, int strResId) {
        String str = context.getString(strResId);
        show(context, view, str);

    }


    public static void toast(Context context, String string) {

        if (context != null) {
            if (toast == null) {
                toast = Toast.makeText(context, string, Toast.LENGTH_SHORT);
            }
            toast.setText(string);
            toast.show();
        }

    }

    public static void toast(Context context, int ResId) {

        toast(context, context.getString(ResId));
    }

}
