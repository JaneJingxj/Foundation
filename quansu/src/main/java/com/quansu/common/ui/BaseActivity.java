package com.quansu.common.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.githang.statusbar.StatusBarCompat;
import com.quansu.BaseApp;
import com.quansu.common.mvp.BasePresenter;
import com.quansu.common.mvp.BaseView;
import com.quansu.slideback.SwipeBackCloseActivity;
import com.quansu.utils.EmptyViewUtils;
import com.quansu.utils.MultiLanguageUtil;
import com.quansu.utils.Toasts;
import com.quansu.widget.temptyview.TEmptyView;
import com.umeng.analytics.MobclickAgent;
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
 * @param <P> Presenter   SwipeBackActivity-侧滑
 *
 */
//SwipeBackActivity   SwipeBackCloseActivity
public abstract class BaseActivity<P extends BasePresenter> extends SwipeBackCloseActivity implements BaseView {

    private static final String LAYOUT_LINEARLAYOUT = "LinearLayout";
    private static final String LAYOUT_FRAMELAYOUT = "FrameLayout";
    private static final String LAYOUT_RELATIVELAYOUT = "RelativeLayout";
    protected String TAG = getClass().getSimpleName();
    protected Intent intent;
    public P presenter;
    private ArrayList<BasePresenter> interacts = new ArrayList<>();
    private CompositeSubscription rxbuses = null;
    @Nullable
    protected ViewGroup _Body;
    private TEmptyView emptyView;
    private View customEmptyView;



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
     * <p>
     * 初始化一些事情
     */
    protected abstract void initThings(Bundle savedInstanceState);

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
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        StatusBarCompat.setStatusBarColor(this, Color.WHITE);


        beforeSetCView(savedInstanceState);

        if (BaseApp.getInstance().webSocketFail) {
            BaseApp.getInstance().initWebsocket();
        }

        BaseApp.getInstance().addActivity(this);
        MobclickAgent.setDebugMode(true);

        //该方法是SwipeBackActivity的侧滑
        //TODO:暂时隐藏
       // setSwipeBackEnable(true);
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {

            finish();
            return;
        }

        setContentView(provideContentViewId());

        setStatusBar();
        initButterKnife();
        _Body = getBody();
        this.presenter = createPresenter();

        if (presenter != null) {
            presenter.attachView(this);
        }

        initThings(savedInstanceState);
        initListeners();


        setStatusBarColor(getResources().getColor(R.color.transparent));


    }




    protected abstract void setStatusBar();

    protected abstract ViewGroup getBody();

    public void beforeSetCView(Bundle savedInstanceState) {

    }


    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.enableEncrypt(true);
        MobclickAgent.onResume(getContext());




    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(getContext());

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


    public void RemoveRxBus(Subscription subscription) {

        if (rxbuses == null) {
            new CompositeSubscription();
        }

        if (rxbuses != null) {
            rxbuses.remove(subscription);
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
        BaseApp.getInstance().removeActivity(this);

        /** 说明:
         *销毁Presenter
         */
        killPresenter();

        /** 说明:
         *销毁Interact
         */
        if (interacts != null) {

            for (BasePresenter interact : interacts) {
                interact.detachView();
            }
        }

        if (rxbuses != null) {
            if (!rxbuses.isUnsubscribed()) {
                rxbuses.unsubscribe();
                //rxbuses.clear();

            }

        }
        destroyButterKnife();
    }


    protected abstract void initButterKnife();

    protected abstract void destroyButterKnife();


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
        overridePendingTransition(0, R.anim.acitivity_hide_out_amination);
    }

    @Override
    public void onBackPressed() {
        finishActivity();

    }

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
            case ERROR_NO_NET://没有网时，重新加载
                bindAfresh(onclickListener);
                // bindEmptyView(TextUtils.isEmpty(errorStr) ? "网络连接失败!" : errorStr, descStr, errorDrawable, getString(R.string.reload), onclickListener);
                break;
            case ERROR_NO_DATA:

                bindEmptyView(TextUtils.isEmpty(errorStr) ? getString(R.string.no_data) : errorStr, descStr, errorDrawable, getString(R.string.reload), onclickListener);
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
                bindEmptyView(TextUtils.isEmpty(errorStr) ? getContext().getString(R.string.not_logged_in) : errorStr, descStr, errorDrawable, getString(R.string.login_first), v -> goToLoginActivity());
                break;

            case NO_DATA:
                  bindFistNoData(onclickListener,"2");
              break;

            case ERROR_FAIL:
                bindFistNoData(onclickListener,"1");//第一次就没有数据
                //bindEmptyView(TextUtils.isEmpty(errorStr) ? getContext().getString(R.string.load_failed_and_please_reload) : errorStr, descStr, errorDrawable, getString(R.string.reload), onclickListener);
                break;
            case ERROR_CUSTOM:
                bindEmptyView(errorStr, descStr, errorDrawable, btnText, onclickListener);
                break;
            case ERROR_AGAIN_LOGIN:
                bindEmptyView(TextUtils.isEmpty(errorStr) ? getContext().getString(R.string.login_again) : errorStr, descStr, errorDrawable, getString(R.string.login_first), v -> goToLoginActivity());
                break;


        }
    }


    /**
     * 加载数据没有数据时候界面的统一
     * @param onclickListener
     * @param type  1:有重新加载  2：没有重新加载
     */
    public void bindFistNoData(View.OnClickListener onclickListener,String type) {

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


        if(type.equals("1")) {
            emptyView.setAgainData();
        }else{
            emptyView.setNoData();
        }

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

        setEmptyView(emptyView);


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

                finish();
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
     * 设置 emptyView 的一些东西
     *
     * @param emptyView
     */
    public void setEmptyView(TEmptyView emptyView) {


    }

    /**
     * AutoLayout 用的
     *
     * @param name
     * @param context
     * @param attrs
     *
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

    public P getPresenter() {
        return presenter;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        MultiLanguageUtil.setLocal(newBase);

    }


}
