package com.quansu.common.mvp;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.quansu.utils.permission.OnGrantCallBack;
import com.quansu.utils.permission.OnPermissionCallBack;

import rx.Subscription;


/**
 * Activity 和 Fragment 中 共通方法
 * Created by xianguangjin on 15/12/14.
 */
public interface BaseView {


    public static final int ERROR_NORMAL = 0;
    public static final int ERROR_NO_NET = 1;
    public static final int ERROR_NO_DATA = 2;
    public static final int ERROR_LOADING = 3;
    public static final int ERROR_FAIL = 4;
    public static final int ERROR_NOT_LOGIN = 5;
    public static final int ERROR_CUSTOM = 6;
    public static final int ERROR_AGAIN_LOGIN = 10;

    public static final int ERROR_FIST_DATA = 7;//第一次加载就没有数据

    public static final int NO_DATA = 8;//没有数据


    Context getContext();

    void show(String text);

    void show(int strResId);

    void toast(int strResId);

    void toast(String text);

    void startActivity(Class clazz);

    void startActivityForResult(Class clazz, int requestCode);

    void startActivity(Class clazz, Bundle bundle);

    void startActivityForResult(Class clazz, Bundle bundle, int requestCode);

    void finishActivity();

    /**
     * 状态处理机制
     *
     * @param error 种类
     */
    void onError(int error);

    BasePresenter getP();


    /**
     * 状态处理机制
     *
     * @param error 种类
     * @param onclickListener 按钮监听
     */
    void onError(int error, View.OnClickListener onclickListener);

    void onErrorFail(int error, View.OnClickListener onclickListener);

    void onErrorFail(int error, String errorStr, View.OnClickListener onclickListener);

    /**
     * 状态处理机制
     *
     * @param error 种类
     * @param errorStr 提示信息
     * @param onclickListener 按钮监听
     */
    void onError(int error, @Nullable String errorStr, View.OnClickListener onclickListener);


    /**
     * 状态处理机制
     *
     * @param error 种类
     * @param errorStr 提示信息
     * @param errorDrawable
     * @param onclickListener 按钮监听
     */
    void onError(int error, @Nullable String errorStr, Drawable errorDrawable, View.OnClickListener onclickListener);

    /**
     * 状态处理机制
     *
     * @param error 种类
     * @param errorStr 提示信息
     * @param errorDrawable
     * @param btnText 按钮文字
     * @param onclickListener 按钮监听
     */
    void onError(int error, @Nullable String errorStr, Drawable errorDrawable, @Nullable String btnText, View.OnClickListener onclickListener);

    /**
     * 状态处理机制
     *
     * @param error 种类
     * @param errorStr 提示信息
     * @param descStr 描述信息
     * @param errorDrawable
     * @param btnText 按钮文字
     * @param onclickListener 按钮监听
     */
    void onError(int error, @Nullable String errorStr, @Nullable String descStr, Drawable errorDrawable, @Nullable String btnText, View.OnClickListener onclickListener);


    /**
     * 自定义错误
     */
    void onErrorCustom(@NonNull View view);


    /**
     * 添加 RxBux 监听者
     *
     * @param subscription
     */
    void addRxBus(Subscription subscription);

    /**
     * 添加 Interact
     *
     * @param integact
     */
    void addInteract(BasePresenter integact);

    /**
     * 跳转到登录页面
     */
    void goToLoginActivity();

    /**
     * 清空 EmptyView
     */
    void clearEmptyView();


    void checkPer(Activity context, OnGrantCallBack callBack, String... permissions);

    void checkPer(Activity context, OnPermissionCallBack callBack, String... permissions);



//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    void setUpWindowTransition();
}