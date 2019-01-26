package com.quansu;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.quansu.common.mvp.BasePresenter;
import com.quansu.common.mvp.BaseView;
import com.quansu.utils.MultiLanguageUtil;
import com.quansu.utils.MyStatusBarUtil;
import com.quansu.utils.permission.JumpPermissionManagement;
import com.quansu.utils.permission.OnGrantCallBack;
import com.quansu.utils.permission.OnPermissionCallBack;
import com.quansu.widget.globalaudio.GlobalAudioView;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.ysnows.quansu.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by xianguangjin on 15/12/4.
 *
 * @param <P> Presenter
 */

public abstract class BaseActivity<P extends BasePresenter> extends com.quansu.common.ui.BaseActivity<P> implements BaseView {
    private Unbinder unBinder;


    @Override
    protected ViewGroup getBody() {
        return (ViewGroup) findViewById(R.id.lay_body);
    }


    @Override
    protected void setStatusBar() {
        MyStatusBarUtil.myStatusBar(this);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        return super.dispatchTouchEvent(event);
    }

    /**
     * 跳转到登录页面
     */
    @Override
    public void goToLoginActivity() {
//        UiSwitch.single(getContext(), LoginActivity.class);
    }


    @Override
    public boolean getisUseAutoLayout() {
        return true;
    }

    @Override
    protected void initButterKnife() {
        unBinder = ButterKnife.bind(this);
    }

    @Override
    protected void destroyButterKnife() {
        if (unBinder != null) {
            unBinder.unbind();
        }
    }

    @Override
    public void onErrorFail(int error, View.OnClickListener onclickListener) {
        super.onError(error, null, getContext().getResources().getDrawable(R.drawable.ic_new_loading_animation), onclickListener);
    }

    @Override
    public void onErrorFail(int error, String errorStr, View.OnClickListener onclickListener) {
        super.onError(error, errorStr, getContext().getResources().getDrawable(R.drawable.ic_new_load_nothing), onclickListener);
    }

    @Override
    public void onError(int error, @Nullable String errorStr, View.OnClickListener onclickListener) {
        super.onError(error, null, getContext().getResources().getDrawable(R.drawable.ic_new_loading_animation), onclickListener);
    }


    /**
     * 检查权限
     */
    public void checkPer(Activity context, OnGrantCallBack callBack, String... permissions) {
        RxPermissions rxPermissions = new RxPermissions(context); // where this is an Activity instance
        rxPermissions
                .request(permissions)
                .subscribe(granted -> {
                    if (granted) {
                        callBack.onGranted();
                    } else {
                        JumpPermissionManagement.GoToSetting(context);
                    }
                });
    }


    /**
     * 检查权限
     */
    public void checkPer(Activity context, OnPermissionCallBack callBack, String... permissions) {
        new RxPermissions(context)
                .request(permissions)
                .subscribe(granted -> {
                    if (granted) {
                        callBack.onGranted();
                    } else {
                        callBack.onDenyed();
                    }
                });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            BaseApp.getInstance().removeActivity(this);
            finish();
            return false;
        }
        return false;
    }


    @Override
    protected void onResume() {
        super.onResume();

        if (BaseApp.getInstance().isaudio) {
            if (BaseApp.getInstance().audioSaveNews != null && BaseApp.getInstance().audioSaveNews.audioplayer != null) {
                GlobalAudioView.setShowTopView(this);
            }
        }
    }





}
