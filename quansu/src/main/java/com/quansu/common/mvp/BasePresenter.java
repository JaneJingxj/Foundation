package com.quansu.common.mvp;

import android.util.Log;

import com.quansu.common.inter.OnAcceptDataListener;
import com.quansu.common.inter.OnAcceptResListener;
import com.quansu.common.inter.OnBindLocalListener;
import com.quansu.common.inter.Res;
import com.quansu.common.inter.SB;
import com.quansu.utils.NetUtils;
import com.quansu.utils.Toasts;
import com.quansu.widget.Dialog;
import com.ysnows.quansu.R;

import java.net.SocketException;
import java.net.SocketTimeoutException;

import rx.Observable;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

import static com.quansu.utils.NetUtils.isNetAvailable;
import static rx.android.schedulers.AndroidSchedulers.mainThread;
import static rx.schedulers.Schedulers.io;


/**
 * Created by xianguangjin on 15/12/14.
 */
public class BasePresenter<V extends BaseView> {


    public V view;
    protected CompositeSubscription mCompositeSubscription;

    public void attachView(V view) {
        this.view = view;
    }

    public BasePresenter build(V view) {
        this.view = view;
        return this;
    }

    public void detachView() {
        this.view = null;
        if (this.mCompositeSubscription != null) {
            this.mCompositeSubscription.unsubscribe();
        }
    }

    public void addSubscription(Subscription s) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }

        this.mCompositeSubscription.add(s);
    }


    /**
     * 请求列表数据,带有页面加载图
     *
     * @param observable
     * @param onAcceptDataListener
     */

    public void requestPageNormalData(Observable<? extends Res> observable, OnAcceptDataListener onAcceptDataListener) {

        Subscription subscription =
                observable
                        .subscribeOn(io())
                        .doOnSubscribe(() -> {
                            if (isNetAvailable(view.getContext())) {
                                view.onError(BaseView.ERROR_LOADING, null);
                            }
                        })
                        .subscribeOn(mainThread())
                        .observeOn(mainThread())
                        .subscribe(new SB<Res>() {
                            @Override
                            public void onError(Throwable e) {
                                super.onError(e);

                                if (view == null) {
                                    return;
                                }
                                if (!NetUtils.isNetAvailable(view.getContext())) {
                                    view.onErrorFail(BaseView.ERROR_NO_NET, v -> requestPageNormalData(observable, onAcceptDataListener));
                                } else if (e instanceof SocketTimeoutException || e instanceof SocketException) {
                                    view.toast(R.string.time_out);
                                    view.onErrorFail(BaseView.ERROR_NO_DATA, v -> requestPageNormalData(observable, onAcceptDataListener));
                                } else {
                                    view.onErrorFail(BaseView.ERROR_FAIL, v -> requestPageNormalData(observable, onAcceptDataListener));
                                }
                            }

                            @Override
                            public void next(Res res) {

                                if (res.isOk(view.getContext())) {
                                    view.onError(BaseView.ERROR_NORMAL, null);
                                    if (onAcceptDataListener != null) {
                                        onAcceptDataListener.onAcceptData(res.getData(), res.getMsg(), res.getPoint());


                                       /* //获得粮票
                                        if (res.getPoint() != 0 && res.getPoint() != -1) {
                                            Toasts.toast(view.getContext(), "requestPageNormalData" + res.getPoint());

                                            new CheckInAnimaYDialog(view.getContext(), res.getPoint());
                                        }*/
                                    }
                                } else {

                                    if (res.getStatus() == 0) {


                                        if (res.getMsg().contains(view.getContext().getString(R.string.frozen_account))) {
                                            Toasts.toast(view.getContext(), res.getMsg());
                                            view.onError(10, "", "", null, "", null);
                                        } else {

                                            if (onAcceptDataListener != null) {
                                                onAcceptDataListener.onAcceptData(res.getData(), res.getMsg(), res.getPoint());
                                            }

                                        }


                                    } else if (res.getStatus() == 10) {
                                        Toasts.toast(view.getContext(), res.getMsg());
                                        view.onError(10, "", "", null, "", null);
                                    } else {
                                        // view.onErrorFail(ERROR_NO_DATA, v -> requestPageNormalData(observable, onAcceptDataListener));

                                    }
                                }
                            }
                        });

        addSubscription(subscription);
    }


    public void requestNormalData(Observable<? extends Res> observable, OnAcceptDataListener onAcceptDataListener) {
        requestNormalData(observable, onAcceptDataListener, false, -1);
    }

    public void requestNormalData(Observable<? extends Res> observable, OnAcceptResListener onAcceptResListener) {
        requestNormalData(observable, null, onAcceptResListener, null, false, -1);
    }

    public void requestNormalData(Observable<? extends Res> observable, OnBindLocalListener onBindLocalListener) {
        requestNormalData(observable, null, null, onBindLocalListener, false, -1);
    }

    public void requestNormalData(Observable<? extends Res> observable) {
        requestNormalData(observable, null, null, null, false, -1);
    }


    public void requestNormalData(Observable<? extends Res> observable, OnAcceptDataListener onAcceptDataListener, boolean isNeedProgress) {
        requestNormalData(observable, onAcceptDataListener, isNeedProgress, -1);
    }

    public void requestNormalData(Observable<? extends Res> observable, OnAcceptDataListener onAcceptDataListener, boolean isNeedProgress, int strResId) {
        requestNormalData(observable, onAcceptDataListener, null, null, isNeedProgress, strResId);
    }

    public void requestNormalData(Observable<? extends Res> observable, OnAcceptResListener onAcceptResListener, boolean isNeedProgress, int strResId) {
        requestNormalData(observable, null, onAcceptResListener, null, isNeedProgress, strResId);
    }

    public void requestNormalData(Observable<? extends Res> observable, OnAcceptResListener onAcceptResListener, boolean isNeedProgress) {
        requestNormalData(observable, null, onAcceptResListener, null, isNeedProgress, R.string.loading);
    }

    public void requestNormalData(Observable<? extends Res> observable, OnAcceptDataListener onAcceptDataListener, OnAcceptResListener onAcceptResListener, OnBindLocalListener onBindLocalListener, boolean isNeedProgress, int strResId) {
        Subscription subscription =
                observable
                        .subscribeOn(io())
                        .doOnSubscribe(() -> {
                            if (view == null) {
                                return;
                            }
                            if (isNetAvailable(view.getContext())) {
                                if (isNeedProgress) {
                                    Dialog.showProgressingDialog(view.getContext(), strResId == -1 ? view.getContext().getString(R.string.loading) : view.getContext().getString(strResId));
                                }
                            }
                        })
                        .subscribeOn(mainThread())
                        .observeOn(mainThread())
                        .subscribe(new SB<Res>() {
                            @Override
                            public void onError(Throwable e) {
                                super.onError(e);
                                if (view == null) {
                                    return;
                                }
                                if (isNeedProgress) {
                                    Dialog.dismissProgressDialog();
                                }
                               /* if (!isNetAvailable(view.getContext())) {
                                    if (isNeedProgress) {

                                        new YNormalDialog(view.getContext(), "监测到您的网络不可用,是否去设置打开?", "去设置", (view1, yDialog) -> {
                                            view.getContext().startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                                        }, true);

                                        view.onErrorFail(ERROR_NO_NET, v -> requestPageNormalData(observable, onAcceptDataListener));

                                    }
                                } else if (e instanceof SocketTimeoutException || e instanceof SocketException) {
                                    view.toast(R.string.time_out);
//                                  view.onError(ERROR_NO_DATA, v -> requestPageNormalData(observable, onAcceptDataListener));
                                } else {
//                                  view.onError(ERROR_FAIL, v -> requestPageNormalData(observable, onAcceptDataListener));
                                }*/




                                if (!NetUtils.isNetAvailable(view.getContext())) {

                                    Log.e("ASFasf","1111");

                                    view.onErrorFail(BaseView.ERROR_NO_NET, v -> requestPageNormalData(observable, onAcceptDataListener));
                                } else if (e instanceof SocketTimeoutException || e instanceof SocketException) {
                                    view.toast(R.string.time_out);


                                    Log.e("ASFasf","2222");

                                    view.onErrorFail(BaseView.ERROR_NO_DATA, v -> requestPageNormalData(observable, onAcceptDataListener));
                                } else {

                                    Log.e("ASFasf","3333");

                                    view.onErrorFail(BaseView.ERROR_FAIL, v -> requestPageNormalData(observable, onAcceptDataListener));
                                }
                            }

                            @Override
                            public void next(Res res) {
                                if (isNeedProgress) {
                                    if(view!=null&&view.getContext()!=null) {
                                        Dialog.dismissProgressDialog();
                                    }
                                }

                                /** 说明:
                                 *如果要自己处理,就不执行以下操作了
                                 */
                                if (onAcceptResListener != null) {

                                    //登录状态  出现问题  被举报
                                    if (res.getStatus() == 0) {
                                        if (res.getMsg().contains(view.getContext().getString(R.string.frozen_account))) {
                                            if (view == null) {
                                                return;
                                            }
                                            Toasts.toast(view.getContext(), res.getMsg());
                                            view.onError(10, "", "", null, "", null);
                                        }
                                    } else if (res.getStatus() == 10) {
                                        if (view == null) {
                                            return;
                                        }
                                        Toasts.toast(view.getContext(), res.getMsg());
                                        view.onError(10, "", "", null, "", null);
                                    }

                                    onAcceptResListener.onResAccept(res);

                                } else {
                                    if (view == null) {
                                        return;
                                    }

                                    if (res.isOk(view.getContext())) {
                                        if (onAcceptDataListener != null) {
                                            // Log.e("---luo--",""+res.getData()+res.getMsg()+res.getPoint());
                                            onAcceptDataListener.onAcceptData(res.getData(), res.getMsg(), res.getPoint());
                                            //获得粮票
                                        }
                                        if (onBindLocalListener != null) {
                                            onBindLocalListener.onSaveLocal(res.getData());
                                        }
                                    } else {
                                        if (view == null) {
                                            return;
                                        }

                                        if (res.getStatus() == 0) {
                                            if (res.getMsg().contains(view.getContext().getString(R.string.frozen_account))) {
                                                Toasts.toast(view.getContext(), res.getMsg());
                                                view.onError(10, "", "", null, "", null);
                                            }
                                        } else if (res.getStatus() == 10) {
                                            Toasts.toast(view.getContext(), res.getMsg());
                                            view.onError(10, "", "", null, "", null);
                                        }
                                    }
                                }
                            }
                        });

        addSubscription(subscription);
    }


}
