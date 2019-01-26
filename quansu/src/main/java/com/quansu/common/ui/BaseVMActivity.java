package com.quansu.common.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import com.quansu.common.mvp.BasePresenter;
import com.quansu.common.mvp.BaseView;
import com.quansu.utils.EmptyViewUtils;
import com.quansu.utils.Toasts;
import com.quansu.widget.temptyview.TEmptyView;
import com.ysnows.quansu.R;
import com.zhy.autolayout.AutoFrameLayout;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.ArrayList;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by xianguangjin on 15/12/4.
 *
 * @param <P> Presenter
 */
public abstract class BaseVMActivity<P extends BasePresenter, VM> extends AppCompatActivity implements BaseView {


    private static final String LAYOUT_LINEARLAYOUT = "LinearLayout";
    private static final String LAYOUT_FRAMELAYOUT = "FrameLayout";
    private static final String LAYOUT_RELATIVELAYOUT = "RelativeLayout";


    protected String TAG = getClass().getSimpleName();
    protected Intent intent;


    public P presenter;
    private ArrayList<BasePresenter> interacts = new ArrayList<>();
    private TEmptyView emptyView;

    public VM bd;
    private CompositeSubscription rxbuses = null;


    @Nullable
    protected FrameLayout _Body;



    /**
     * @return 提供LayoutId
     */
    abstract protected int provideContentViewId();


    /**
     * 初始化事件监听者
     */
    public abstract void initListeners();

    /**
     * @param savedInstanceState 缓存数据
     *                           <p>
     *                           初始化一些事情
     */
    protected abstract void initThings(Bundle savedInstanceState);

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforeSetCView(savedInstanceState);
        initContentView(provideContentViewId());
        _Body = getBody();
        this.presenter = createPresenter();
        if (presenter != null) {
            presenter.attachView(this);
        }

        initThings(savedInstanceState);
        initListeners();

    }

    protected abstract void initContentView(int layoutId);

    protected abstract FrameLayout getBody();

    public void beforeSetCView(Bundle savedInstanceState) {


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
    public void addInteract(BasePresenter inteact) {
        interacts.add(inteact);
    }


    @Override
    public Context getContext() {
        return this;
    }


    @Override
    public void show(String text) {
        Toasts.show(getContext(), _Body, text);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();

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

        if (rxbuses != null) {
            if (!rxbuses.isUnsubscribed()) {
                rxbuses.unsubscribe();
            }
        }

    }

    @Override
    public void startActivity(Class clazz) {
        startActivity(new Intent(getContext(), clazz));

    }

    @Override
    public void startActivity(Class clazz, Bundle bundle) {
        startActivity(new Intent(getContext(), clazz).putExtra("data", bundle));
    }

    @Override
    public void startActivityForResult(Class clazz, int requestCode) {
        startActivityForResult(new Intent(getContext(), clazz), requestCode);


    }

    @Override
    public void startActivityForResult(Class clazz, Bundle bundle, int requestCode) {
        Intent intent = new Intent(getContext(), clazz);
        intent.putExtra("data", bundle);
        startActivityForResult(intent, requestCode);
    }


    @Override
    public void finishActivity() {
        this.finish();
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
               // bindEmptyView("网络错误,请检查您的网络", getString(R.string.reload), onclickListener);
                break;
            case ERROR_NO_DATA:
                bindEmptyView(getString(R.string.no_data), "", onclickListener);
                break;
            case ERROR_LOADING:
                bindEmptyView(getString(R.string.loading), "", onclickListener);
                break;
            case ERROR_NORMAL:
                if (emptyView != null) {
                    if (emptyView.getVisibility() == View.VISIBLE) {

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
                }
                break;
            case ERROR_FAIL:
                bindEmptyView(getContext().getString(R.string.load_failed_and_please_reload), getString(R.string.reload), onclickListener);
                break;

            default:
                bindEmptyView(getString(error), "", onclickListener);
                break;


        }
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

                finish();
                if (onclickListener != null) {
                    onclickListener.onClick(v);
                }
            }
        });





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
     * AutoLayout 用的
     *
     * @param name
     * @param context
     * @param attrs
     * @return
     */
    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        View view = null;
        if (getisUseAutoLayout()) {
            if (name.equals(LAYOUT_FRAMELAYOUT)) {
                view = new AutoFrameLayout(context, attrs);
            }

            if (name.equals(LAYOUT_LINEARLAYOUT)) {
                view = new AutoLinearLayout(context, attrs);
            }

            if (name.equals(LAYOUT_RELATIVELAYOUT)) {
                view = new AutoRelativeLayout(context, attrs);
            }

            if (view != null) return view;
        }

        return super.onCreateView(name, context, attrs);
    }


    public abstract boolean getisUseAutoLayout();

}
