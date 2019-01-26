package com.quansu.common.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
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
public abstract class BaseVMFragment<P extends BasePresenter, VM> extends Fragment implements BaseView {


    public P presenter;

    @Nullable
    private FrameLayout _Body;

    private ArrayList<BasePresenter> interacts = new ArrayList<>();
    private TEmptyView emptyView;
    private CompositeSubscription rxbuses = null;

    public VM bd;




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


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = initContentView(inflater, provideLayoutId(), container);

        this.presenter = createPresenter();

        if (presenter != null) {
            presenter.attachView(this);
        }

        _Body = getBody(view);
        initThings(view, savedInstanceState);
        initListeners();


        return view;
    }

    protected abstract View initContentView(LayoutInflater inflater, int layoutId, ViewGroup container);

    protected abstract FrameLayout getBody(View view);

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


    }

    /**
     * @param error
     * @param onclickListener
     */
    @Override
    public void onError(int error, View.OnClickListener onclickListener) {
        switch (error) {
            case ERROR_NO_NET:

                bindAfresh(onclickListener);
                //bindEmptyView("网络错误,请检查您的网络", getString(R.string.reload), onclickListener);
                break;
            case ERROR_NO_DATA:
                bindEmptyView(getString(R.string.no_data), "", onclickListener);
                break;
            case ERROR_LOADING:
                bindEmptyView(getString(R.string.loading), "", onclickListener);
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
            case ERROR_FAIL:
                bindEmptyView(getContext().getString(R.string.load_failed_and_please_reload), getString(R.string.reload), onclickListener);
                break;
        }

    }

    public P getPresenter() {
        return this.presenter;
    }

    /**
     * @param title
     * @param buttonText
     * @param onclickListener
     */
    public void bindEmptyView(String title, String buttonText, View.OnClickListener onclickListener) {
        if (_Body == null) {
            return;
        }
        if (emptyView == null) {
            emptyView = new EmptyViewUtils().getEmptyView(_Body);
        } else {
            emptyView.setVisibility(View.VISIBLE);
        }

        emptyView.setShowButton(!TextUtils.isEmpty(buttonText));
        emptyView.setEmptyText(title);
        emptyView.setActionText(buttonText);
        emptyView.setAction(v -> {
            emptyView.setVisibility(View.GONE);
            if (onclickListener != null) {
                onclickListener.onClick(v);
            }
        });
    }


    /**
     * 没有网时，重新加载，统一界面统一样式
     */
    public void bindAfresh(View.OnClickListener onclickListener){

        if (_Body == null) {
            return;
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





    }

}
