package com.quansu.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.quansu.common.mvp.BaseView;
import com.quansu.utils.BUN;
import com.quansu.utils.UiSwitch;
import com.quansu.utils.glide.GlideUtils;

import static android.text.TextUtils.isEmpty;

/**
 * Created by xianguangjin on 16/6/1.
 * 自定义
 */

public class NetImageView extends android.support.v7.widget.AppCompatImageView {

    private BaseView view;

    public NetImageView(Context context) {
        this(context, null);


    }

    public NetImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);


    }

    public NetImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        if (context instanceof BaseView) {
            this.view = (BaseView) context;
        }

        setScaleType(ScaleType.FIT_XY);
    }


    /**
     * 设置图片数据
     *
     * @param img
     * @param url
     */
    public void setData(String img, String url) {
        if (!isEmpty(img)) {
            GlideUtils.lImgNoScale(getContext(), img, this);
        }

    }

    /**
     * 设置图片数据
     *
     * @param img
     * @param url
     */
    public void setData(String img, String url, Class clazz) {
        if (!isEmpty(img)) {
            GlideUtils.lImgNoScale(getContext(), img, this);
        }

        if (!isEmpty(url)) {
            setOnClickListener(v -> UiSwitch.bundle(getContext(), clazz, new BUN().putString("url", url).ok()));
        }

    }


    public void setView(BaseView view) {
        this.view = view;
    }

}
