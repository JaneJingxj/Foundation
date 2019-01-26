package com.quansu.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.quansu.common.mvp.BaseView;
import com.quansu.utils.glide.GlideUtils;
import com.ysnows.quansu.R;

import java.util.ArrayList;

/**
 * Created by xianguangjin on 16/6/1.
 * 自定义
 */

public class CircleImgsView extends LinearLayout {


    private BaseView view;
    private LinearLayout layOne;
    private ImageView imgOne;
    private TextView lineTwo;
    private ImageView imgTwo;
    private TextView lineOne;
    private LinearLayout layTwo;
    private ImageView imgThree;
    private TextView lineThree;
    private ImageView imgFour;


    public CircleImgsView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public CircleImgsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public CircleImgsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        inflate(context, R.layout.widget_circle_imgs, this);
        setOrientation(HORIZONTAL);
        setBackgroundColor(Color.WHITE);

        layOne = findViewById(R.id.lay_one);
        imgOne = findViewById(R.id.img_one);
        lineTwo = findViewById(R.id.line_two);
        imgTwo = findViewById(R.id.img_two);
        lineOne = findViewById(R.id.line_one);
        layTwo = findViewById(R.id.lay_two);
        imgThree = findViewById(R.id.img_three);
        lineThree = findViewById(R.id.line_three);
        imgFour = findViewById(R.id.img_four);


    }

    public void setData(ArrayList<String> imgs) {

        switch (imgs.size()) {
            case 0:
                this.setVisibility(GONE);
                break;
            case 1:
                this.setVisibility(VISIBLE);
                layTwo.setVisibility(GONE);

                lineOne.setVisibility(GONE);
                lineTwo.setVisibility(GONE);
                lineThree.setVisibility(GONE);

                imgTwo.setVisibility(GONE);
                imgOne.setVisibility(VISIBLE);
                imgThree.setVisibility(GONE);
                imgFour.setVisibility(GONE);

                GlideUtils.lImg(getContext(), imgs.get(0), imgOne,true);

                break;
            case 2:
                this.setVisibility(VISIBLE);
                layTwo.setVisibility(VISIBLE);

                lineOne.setVisibility(VISIBLE);
                lineTwo.setVisibility(GONE);
                lineThree.setVisibility(GONE);

                imgOne.setVisibility(VISIBLE);
                imgTwo.setVisibility(GONE);
                imgFour.setVisibility(GONE);
                imgThree.setVisibility(VISIBLE);

                GlideUtils.lImg(getContext(), imgs.get(0), imgOne,true);
                GlideUtils.lImg(getContext(), imgs.get(1), imgThree,true);
                break;
            case 3:
                this.setVisibility(VISIBLE);
                layTwo.setVisibility(VISIBLE);

                lineOne.setVisibility(VISIBLE);
                lineTwo.setVisibility(GONE);
                lineThree.setVisibility(VISIBLE);


                imgOne.setVisibility(VISIBLE);
                imgTwo.setVisibility(GONE);
                imgFour.setVisibility(VISIBLE);
                imgThree.setVisibility(VISIBLE);

                GlideUtils.lImg(getContext(), imgs.get(0), imgOne,true);
                GlideUtils.lImg(getContext(), imgs.get(1), imgThree,true);
                GlideUtils.lImg(getContext(), imgs.get(2), imgFour,true);
                break;
            case 4:
                this.setVisibility(VISIBLE);
                layTwo.setVisibility(VISIBLE);

                lineOne.setVisibility(VISIBLE);
                lineTwo.setVisibility(VISIBLE);
                lineThree.setVisibility(VISIBLE);

                imgOne.setVisibility(VISIBLE);
                imgTwo.setVisibility(VISIBLE);
                imgFour.setVisibility(VISIBLE);
                imgThree.setVisibility(VISIBLE);

                GlideUtils.lImg(getContext(), imgs.get(0), imgOne,true);
                GlideUtils.lImg(getContext(), imgs.get(1), imgThree,true);
                GlideUtils.lImg(getContext(), imgs.get(2), imgFour,true);
                GlideUtils.lImg(getContext(), imgs.get(3), imgTwo,true);
                break;
        }
    }

    public void setView(BaseView view) {
        this.view = view;
    }

}
