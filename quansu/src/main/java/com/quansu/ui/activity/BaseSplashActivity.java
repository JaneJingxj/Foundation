package com.quansu.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.quansu.common.mvp.BasePresenter;
import com.quansu.common.ui.BaseActivity;
import com.quansu.utils.SPUtil;
import com.ysnows.quansu.R;


public abstract class BaseSplashActivity<P extends BasePresenter> extends BaseActivity<P> {
    ImageView _Img;

    protected boolean isFirstIn;


    @Override
    protected void initThings(Bundle savedInstanceState) {

        _Img = getImg();
        isFirstIn = SPUtil.netInstance().getBooleanWithTrue("isFirstIn");


        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.splash);
        _Img.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {


            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (!isFirstIn) {
                    goHome();
                } else {
                    goGuide();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }

    /**
     * 获取IMG
     *
     * @return
     */
    protected abstract ImageView getImg();

    protected void goHome() {
        intent = new Intent(this, getHomeClassName());
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finishActivity();
    }

    /**
     * MainActivity ClassName
     *
     * @return
     */
    protected abstract Class getHomeClassName();


    protected void goGuide() {
        intent = new Intent(this, getGuidClassName());
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(0, R.anim.fade_out);
        finishActivity();
    }


    @Override
    protected void setStatusBar() {
//        StatusBarUtil.setTranslucentForImageView(this,1,getImg());
    }

    /**
     * GuidAcitivity　ClassName
     *
     * @return
     */
    protected abstract Class getGuidClassName();

    @Override
    protected FrameLayout getBody() {
        return null;
    }

    @Override
    public void goToLoginActivity() {

    }

    @Override
    public P createPresenter() {
        return null;
    }


}
