package com.quansu.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.quansu.common.mvp.BaseView;
import com.quansu.widget.Dialog;
import com.ysnows.quansu.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneUtils {

    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }


    /**
     * 拨打电话
     *
     * @param context
     * @param phone
     */
    public static void call(Activity context, String phone) {
        Dialog.showRadioDialog(context, context.getString(R.string.are_you_sure_you_want_to_make_a_phone_call), new Dialog.DialogClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void confirm() {
                ((BaseView) context).checkPer(context, () -> {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
                    context.startActivity(intent);
                }, Manifest.permission.CALL_PHONE);
            }

            @Override
            public void cancel() {

            }
        });


    }

    public static void dial(Context context, String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
