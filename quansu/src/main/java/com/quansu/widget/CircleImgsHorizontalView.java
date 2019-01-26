package com.quansu.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.quansu.common.mvp.BaseView;
import com.quansu.utils.glide.GlideUtils;
import com.ysnows.quansu.R;

import java.util.ArrayList;

/**
 * Created by xianguangjin on 16/6/1.
 * 自定义
 */

public class CircleImgsHorizontalView extends LinearLayout {

    private BaseView view;
    private ImageView imgOne;
    private ImageView imgTwo;
    private ImageView imgThree;


    public CircleImgsHorizontalView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public CircleImgsHorizontalView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);

    }

    public CircleImgsHorizontalView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);

    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        inflate(context, R.layout.widget_circle_imgs_horizontal, this);


        imgOne = (ImageView) findViewById(R.id.img_one);
        imgTwo = (ImageView) findViewById(R.id.img_two);
        imgThree = (ImageView) findViewById(R.id.img_three);

    }

    public void setView(BaseView view) {
        this.view = view;
    }

    public void setData(ArrayList<String> imgs) {
        switch (imgs.size()) {
            case 0:
                this.setVisibility(GONE);
                break;
            case 1:
                this.setVisibility(VISIBLE);
                imgOne.setVisibility(VISIBLE);
                imgTwo.setVisibility(INVISIBLE);
                imgThree.setVisibility(INVISIBLE);
                GlideUtils.lImg(getContext(), imgs.get(0), imgOne,false);
                break;
            case 2:
                this.setVisibility(VISIBLE);
                imgOne.setVisibility(VISIBLE);
                imgTwo.setVisibility(VISIBLE);
                imgThree.setVisibility(INVISIBLE);

                GlideUtils.lImg(getContext(), imgs.get(0), imgOne,false);
                GlideUtils.lImg(getContext(), imgs.get(1), imgTwo,false);

                break;
            case 3:
                this.setVisibility(VISIBLE);

                imgOne.setVisibility(VISIBLE);
                imgTwo.setVisibility(VISIBLE);
                imgThree.setVisibility(VISIBLE);

                GlideUtils.lImg(getContext(), imgs.get(0), imgOne,false);
                GlideUtils.lImg(getContext(), imgs.get(1), imgTwo,false);
                GlideUtils.lImg(getContext(), imgs.get(2), imgThree,false);

                break;


        }
    }

    public void setDataResId(ArrayList<Integer> imgs) {
        switch (imgs.size()) {
            case 0:
                this.setVisibility(GONE);
                break;
            case 1:
                this.setVisibility(VISIBLE);
                imgOne.setVisibility(VISIBLE);
                imgTwo.setVisibility(INVISIBLE);
                imgThree.setVisibility(INVISIBLE);
                GlideUtils.lImg(getContext(), imgs.get(0), imgOne);
                break;
            case 2:
                this.setVisibility(VISIBLE);
                imgOne.setVisibility(VISIBLE);
                imgTwo.setVisibility(VISIBLE);
                imgThree.setVisibility(INVISIBLE);

                GlideUtils.lImg(getContext(), imgs.get(0), imgOne);
                GlideUtils.lImg(getContext(), imgs.get(1), imgTwo);

                break;
            case 3:
                this.setVisibility(VISIBLE);

                imgOne.setVisibility(VISIBLE);
                imgTwo.setVisibility(VISIBLE);
                imgThree.setVisibility(VISIBLE);

                GlideUtils.lImg(getContext(), imgs.get(0), imgOne);
                GlideUtils.lImg(getContext(), imgs.get(1), imgTwo);
                GlideUtils.lImg(getContext(), imgs.get(2), imgThree);

                break;


        }
    }
}
