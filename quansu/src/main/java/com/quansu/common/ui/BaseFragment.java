package com.quansu.common.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.quansu.common.mvp.BasePresenter;
import com.quansu.common.mvp.BaseView;
import com.quansu.utils.EmptyViewUtils;
import com.quansu.utils.Toasts;
import com.quansu.widget.temptyview.TEmptyView;
import com.ysnows.quansu.R;

import java.util.ArrayList;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by xianguangjin on 15/12/4.
 */
public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements BaseView {

    public P presenter;

    @Nullable
    protected ViewGroup _Body;
    private ArrayList<BasePresenter> interacts = new ArrayList<>();
    private TEmptyView emptyView;
    private CompositeSubscription rxbuses = null;
    private View customEmptyView;


    /**
     * 初始化事件监听者
     */
    abstract public void initListeners();

    /**
     * 初始化一些事情
     */
    protected abstract void initThings(View view, Bundle savedInstanceState);

    /**
     * @return 提供LayoutId
     */
    abstract public int provideLayoutId();

    /**
     * 绑定Presenter
     */
    public abstract P createPresenter();

    @Override
    public P getP() {
        return presenter;
    }

    /**
     * 解绑Presenter
     */
    public void killPresenter() {
        if (presenter != null) {
            presenter.detachView();
        }
    }

    @Override
    public void addInteract(BasePresenter inteact) {
        interacts.add(inteact);
    }

    /**
     * @param text Toast
     */
    @Override
    public void show(String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void addRxBus(Subscription subscription) {
        if (rxbuses == null) {
            new CompositeSubscription();
        }

        if (rxbuses != null) {
            rxbuses.add(subscription);
        }
    }


    @Override
    public void show(int strResId) {
        show(getString(strResId));
    }

    @Override
    public void toast(String text) {
        Toasts.toast(getContext(), text);
    }

    @Override
    public void toast(int strResId) {
        toast(getString(strResId));
    }



    //View mView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(provideLayoutId(), container, false);
        initButterKnife(view);
        this.presenter = createPresenter();
        if (presenter != null) {
            presenter.attachView(this);
        }

        _Body = getBody(view);
        initThings(view, savedInstanceState);
        initListeners();


        return view;
    }


    protected abstract ViewGroup getBody(View view);

    @Override
    public void startActivity(Class clazz) {
        startActivity(new Intent(getContext(), clazz));

    }

    @Override
    public void startActivityForResult(Class clazz, int requestCode) {


    }

    @Override
    public void startActivity(Class clazz, Bundle bundle) {
        startActivity(new Intent(getContext(), clazz).putExtra("data", bundle));

    }


    @Override
    public void startActivityForResult(Class clazz, Bundle bundle, int requestCode) {
        Intent intent = new Intent(getContext(), clazz);
        intent.putExtra("data", bundle);
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void finishActivity() {
        getActivity().finish();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        /** 说明:
         *销毁Presenter
         */
        killPresenter();

        /** 说明:
         *销毁Interact
         */
        for (BasePresenter interact : interacts) {
            interact.detachView();
        }
        destroyButterKnife();
    }

    protected abstract void initButterKnife(View view);

    protected abstract void destroyButterKnife();


    @Override
    public void onError(int error) {
        this.onError(error, null);
    }

    @Override
    public void onError(int error, View.OnClickListener onclickListener) {
        this.onError(error, null, onclickListener);
    }

    @Override
    public void onError(int error, @Nullable String errorStr, View.OnClickListener onclickListener) {
        this.onError(error, errorStr, null, onclickListener);
    }

    @Override
    public void onError(int error, @Nullable String errorStr, Drawable errorDrawable, View.OnClickListener onclickListener) {
        this.onError(error, errorStr, errorDrawable, null, onclickListener);
    }

    @Override
    public void onError(int error, @Nullable String errorStr, Drawable errorDrawable, @Nullable String btnText, View.OnClickListener onclickListener) {
        this.onError(error, errorStr, null, errorDrawable, null, onclickListener);
    }

    @Override
    public void onError(int error, @Nullable String errorStr, @Nullable String descStr, Drawable errorDrawable, @Nullable String btnText, View.OnClickListener onclickListener) {


        switch (error) {
            case ERROR_NO_NET:
                bindAfresh(onclickListener);
                // bindEmptyView(TextUtils.isEmpty(errorStr) ? "网络错误,请检查您的网络" : errorStr, descStr, errorDrawable, getString(R.string.reload), onclickListener);
                break;
            case ERROR_NO_DATA:

                bindFistNoData(onclickListener);

                // bindEmptyView(TextUtils.isEmpty(errorStr) ? getString(R.string.no_data) : errorStr, descStr, errorDrawable, "重新加载", onclickListener);
                break;
            case ERROR_LOADING:
                bindEmptyView(TextUtils.isEmpty(errorStr) ? getString(R.string.loading) : errorStr, descStr, errorDrawable, "", onclickListener);
                break;
            case ERROR_NORMAL:
                if (emptyView != null) {
                    if (emptyView.getVisibility() == View.GONE) {
                        return;
                    }

                    Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out);
                    animation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            emptyView.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    emptyView.startAnimation(animation);
                }
                break;
            case ERROR_NOT_LOGIN:

                //TODO
                //之后打开
//                bindEmptyView(TextUtils.isEmpty(errorStr) ? "您还没有登录" : errorStr, descStr, errorDrawable, getString(R.string.login_first), v -> goToLoginActivity());
                bindEmptyView(TextUtils.isEmpty(errorStr) ? getContext().getString(R.string.not_logged_in) : errorStr, descStr, errorDrawable, getString(R.string.login_first), onclickListener);
                break;
            case ERROR_FAIL:
                bindEmptyView(TextUtils.isEmpty(errorStr) ? getContext().getString(R.string.load_failed_and_please_reload) : errorStr, descStr, errorDrawable, getString(R.string.reload), onclickListener);
                break;
            case ERROR_CUSTOM:
                bindEmptyView(errorStr, descStr, errorDrawable, btnText, onclickListener);
                break;
            case ERROR_AGAIN_LOGIN:
                bindEmptyView(TextUtils.isEmpty(errorStr) ? getContext().getString(R.string.login_again) : errorStr, descStr, errorDrawable, getString(R.string.login_first), v -> goToLoginActivity());
                break;
            case ERROR_FIST_DATA://第一次就没有数据
                bindFistNoData(onclickListener);
                break;

        }
    }


    @Override
    public void onErrorCustom(@NonNull View view) {
        if (_Body == null) {
            return;
        }

        //如果 emptyView 在显示状态,就把它隐藏
        if (emptyView != null && emptyView.getVisibility() == View.VISIBLE) {
            emptyView.setVisibility(View.GONE);
        }

        if (customEmptyView == null) {
            customEmptyView = new EmptyViewUtils().getEmptyView(_Body, view);
        } else if (view.equals(customEmptyView)) {//是同一个 view
            customEmptyView.setVisibility(View.VISIBLE);
        } else {//不是同一个 View,先移除之前的,再添加
            _Body.removeView(customEmptyView);
            customEmptyView = new EmptyViewUtils().getEmptyView(_Body, view);
        }

    }


    public P getPresenter() {
        return this.presenter;
    }


    /**
     * 没有网时，重新加载，统一界面统一样式
     */
    public void bindAfresh(View.OnClickListener onclickListener) {

        if (_Body == null) {
            return;
        }


        //如果 customEmptyView 在显示状态,就把它隐藏
        if (customEmptyView != null && customEmptyView.getVisibility() == View.VISIBLE) {
            customEmptyView.setVisibility(View.GONE);
        }


        if (emptyView == null) {
            emptyView = new EmptyViewUtils().getEmptyView(_Body);
        } else {
            emptyView.setVisibility(View.VISIBLE);
        }


        emptyView.setAgain();
        emptyView.setAgainButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emptyView.setVisibility(View.GONE);
                if (onclickListener != null) {
                    onclickListener.onClick(v);
                }
            }
        });
        emptyView.setImgleft(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
                if (onclickListener != null) {
                    onclickListener.onClick(v);
                }
            }
        });

        setEmptyView(emptyView);


    }


    /**
     * 列表第一次加载数据没有数据时候界面的统一
     */
    public void bindFistNoData(View.OnClickListener onclickListener) {

        if (_Body == null) {
            return;
        }


        //如果 customEmptyView 在显示状态,就把它隐藏
        if (customEmptyView != null && customEmptyView.getVisibility() == View.VISIBLE) {
            customEmptyView.setVisibility(View.GONE);
        }


        if (emptyView == null) {
            emptyView = new EmptyViewUtils().getEmptyView(_Body);
        } else {
            emptyView.setVisibility(View.VISIBLE);
        }


        emptyView.setFirstData();
//        emptyView.setAgainButton(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                emptyView.setVisibility(View.GONE);
//                if (onclickListener != null) {
//                    onclickListener.onClick(v);
//                }
//            }
//        });
        emptyView.setImgleft(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
                if (onclickListener != null) {
                    onclickListener.onClick(v);
                }
            }
        });

        setEmptyView(emptyView);


    }


    /**
     * @param title
     * @param buttonText
     * @param onclickListener
     */

    public void bindEmptyView(String title, String desc, Drawable drawable, String buttonText, View.OnClickListener onclickListener) {
        if (_Body == null) {
            return;
        }

        //如果 customEmptyView 在显示状态,就把它隐藏
        if (customEmptyView != null && customEmptyView.getVisibility() == View.VISIBLE) {
            customEmptyView.setVisibility(View.GONE);
        }

        if (emptyView == null) {
            emptyView = new EmptyViewUtils().getEmptyView(_Body);
        } else {
            emptyView.setVisibility(View.VISIBLE);
        }

        emptyView.setIcon(drawable);
        emptyView.setEmptyText(title);
        emptyView.setEmptyDescText(desc);
        emptyView.setActionText(buttonText);
        emptyView.setAction(v -> {
            emptyView.setVisibility(View.GONE);
            if (onclickListener != null) {
                onclickListener.onClick(v);
            }
        });

        setEmptyView(emptyView);
    }

    /**
     * 设置 EmptyView 的一些东西
     *
     * @param emptyView
     */
    public void setEmptyView(TEmptyView emptyView) {

    }

    @Override
    public void clearEmptyView() {
        //如果 emptyView 在显示状态,就把它隐藏
        if (emptyView != null && emptyView.getVisibility() == View.VISIBLE) {
            emptyView.setVisibility(View.GONE);
        }

        //如果 customEmptyView 在显示状态,就把它隐藏
        if (customEmptyView != null && customEmptyView.getVisibility() == View.VISIBLE) {
            customEmptyView.setVisibility(View.GONE);
        }
    }


//    @Override
//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    public void setUpWindowTransition() {
////        Slide slide = new Slide();
////        slide.setSlideEdge(Gravity.RIGHT);
////        slide.setDuration(getResources().getInteger(R.integer.anim_duration_medium));
////        getActivity().getWindow().setEnterTransition(slide);
//    }
}
