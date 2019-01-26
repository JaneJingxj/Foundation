package com.quansu.common.mvp;


import android.content.Intent;
import android.provider.Settings;
import android.util.Log;

import com.google.gson.JsonSyntaxException;
import com.quansu.common.inter.OnAcceptDataListener;
import com.quansu.common.inter.OnAcceptResListener;
import com.quansu.common.inter.OnBindLocalListener;
import com.quansu.common.inter.Res;
import com.quansu.common.inter.SB;
import com.quansu.utils.NetUtils;
import com.quansu.utils.Toasts;
import com.quansu.widget.dialog.YNormalDialog;
import com.ysnows.quansu.R;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

import static rx.android.schedulers.AndroidSchedulers.mainThread;
import static rx.schedulers.Schedulers.io;

/**
 * Created by xianguangjin on 16/5/18.
 * 带有RL的RecycleView
 */
public abstract class RLRVPresenter<V extends RLRVView> extends RLPresenter<V> {

    public int page = 1;
    public int count = 10;
    private boolean isEnterOutSide = true;

    public abstract void getData();

    public String listid = "";//默认第一页

    /**
     * 加载首页数据，不显示正在加载图...
     */
    @Override
    public void requestDataRefresh() {
        isEnterOutSide = false;
        page = 1;
        this.getData();
    }

    @Override
    public void requestFirstRefresh() {
        isEnterOutSide = true;
        page = 1;
        this.getData();
        isEnterOutSide = true;
    }

    @Override
    public void loadMore() {
        page++;
        getData();
    }

    public void requestPageListData(Observable<? extends Res> observable) {
        requestPageListData(observable, null, null, null, false);
    }

    public void requestPageListData(Observable<? extends Res> observable, OnAcceptDataListener onAcceptDataListener) {
        requestPageListData(observable, onAcceptDataListener, null, null, false);
    }

    public void requestPageListData(Observable<? extends Res> observable, OnAcceptResListener onAcceptResListener) {
        requestPageListData(observable, null, null, onAcceptResListener, false);
    }

    public void requestPageListIdData(Observable<? extends Res> observable, OnAcceptResListener onAcceptResListener) {
        requestPageListIdData(observable, onAcceptResListener, false);
    }


    /**
     * 是否在没有数据或者加载失败的时候显示Header
     *
     * @param observable
     * @param isHasHeader
     */
    public void requestPageListData(Observable<? extends Res> observable, boolean isHasHeader) {
        requestPageListData(observable, null, null, null, isHasHeader);
    }

    public void requestPageListData(Observable<? extends Res> observable, OnAcceptDataListener onAcceptDataListener, boolean isHasHeader) {
        requestPageListData(observable, onAcceptDataListener, null, null, isHasHeader);
    }

    public void requestNormalListData(Observable<? extends Res> observable) {

        requestNormalListData(observable, null, null, null, false);
    }

    public void requestNormalListData(Observable<? extends Res> observable, OnAcceptDataListener onAcceptDataListener) {
        requestNormalListData(observable, onAcceptDataListener, null, null, false);
    }

    public void requestNormalListData(Observable<? extends Res> observable, OnAcceptResListener onAcceptResListener) {
        requestNormalListData(observable, null, null, onAcceptResListener, false);
    }

    public void requestNormalListData(Observable<? extends Res> observable, boolean isHasHeader) {
        requestNormalListData(observable, null, null, null, isHasHeader);
    }

    public void requestNormalListData(Observable<? extends Res> observable, OnAcceptDataListener onAcceptDataListener, boolean isHasHeader) {
        requestNormalListData(observable, onAcceptDataListener, null, null, isHasHeader);
    }


    /**
     * @param observable
     * @param onAcceptResListener
     * @param isHasHeader
     * 根据listid来加载分页
     * 注意 ：动态来用，其他列表不要随意用
     * 因为这里的total是递减的  其他列表是不变的
     */

    public void requestPageListIdData(Observable<? extends Res> observable,
                                      OnAcceptResListener onAcceptResListener,
                                      boolean isHasHeader) {
        Subscription subscription =
                observable
                        .subscribeOn(io())
                        .doOnSubscribe(() -> {
                            if (NetUtils.isNetAvailable(view.getContext())) {
                                if (page == 1) {
                                    if (isEnterOutSide) {
                                        view.onError(BaseView.ERROR_LOADING, null);
                                    }
                                }
                            }
                        })
                        .observeOn(mainThread())
                        .subscribe(new SB<Res>() {
                            @Override
                            public void onError(Throwable e) {
                                super.onError(e);
                                if (view == null) {
                                    return;
                                }
                                if (page == 1) {//第一页
                                    view.refreshing(false);
                                    if (!NetUtils.isNetAvailable(view.getContext())) {//当前网络不可用
                                        view.onErrorFail(BaseView.ERROR_NO_NET, view.getContext().getString(R.string.reload), (v) -> requestFirstRefresh());

                                    } else {
                                        if (e instanceof IllegalStateException) {
                                            if (isHasHeader) {
                                                view.hasMore(false);
                                                view.onError(BaseView.ERROR_NORMAL, null);
                                            } else {
                                                view.onErrorFail(BaseView.ERROR_NO_DATA, view.getContext().getString(R.string.no_data), v -> requestFirstRefresh());
                                            }
                                        } else if (e instanceof JsonSyntaxException) {
                                            if (isHasHeader) {
                                                view.hasMore(false);
                                                view.onError(BaseView.ERROR_NORMAL, null);
                                            } else {
                                                view.onErrorFail(BaseView.ERROR_NO_DATA, view.getContext().getString(R.string.no_data), v -> requestFirstRefresh());
                                            }
                                        } else if (e instanceof SocketTimeoutException || e instanceof SocketException) {
                                            view.toast(R.string.time_out);
                                            if (isHasHeader) {
                                                view.hasMore(false);
                                            } else {
                                                view.onError(BaseView.ERROR_CUSTOM, view.getContext().getString(R.string.time_out), view.getContext().getString(R.string.the_network_is_not_good), view.getContext().getResources().getDrawable(R.drawable.ic_new_load_nothing), "重新加载", v -> requestFirstRefresh());
                                            }
                                        } else {
                                            view.onErrorFail(BaseView.ERROR_FAIL, view.getContext().getString(R.string.no_data), v -> requestFirstRefresh());
                                        }
                                    }
                                } else {
                                    view.refreshing(false);
                                    if (!NetUtils.isNetAvailable(view.getContext())) {
                                        new YNormalDialog(view.getContext(), view.getContext().getString(R.string.is_to_set_up), view.getContext().getString(R.string.to_set_up), (view1, yDialog) -> {
                                            view.getContext().startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                                        }, true);
                                    } else {
                                        view.onErrorFail(BaseView.ERROR_FAIL, view.getContext().getString(R.string.no_data), v -> requestFirstRefresh());
//                                      view.onError(view.getContext().getString(R.string.load_error));
                                    }

                                }
                            }

                            @Override
                            public void next(Res res) {
                                if (view == null) {
                                    return;
                                }
                                if (onAcceptResListener != null) {
                                    if (onAcceptResListener.onResAccept(res)) {
                                        return;
                                    }
                                }

                                if (res.isOk(view.getContext())) {


                                    if (page == 1) {
                                        view.refreshing(false);
                                    }

                                    ArrayList list = (ArrayList) res.getData();


                                    if (list != null && list.size() > 0) {

                                        Log.e("--shy-", "4444444");
                                        view.bindData(list, res.isHasMoreListId(page));//这里注意total是递减的故 判断total>0;

                                        view.onError(BaseView.ERROR_NORMAL, null);
                                    } else {

                                        if (page == 1) {

                                            if (isHasHeader == true) {

                                                Log.e("--shy-", "3333333");

                                                view.hasMore(false);
                                                view.onError(BaseView.ERROR_NORMAL, null);

                                                //view.refreshing(false);
                                            } else {
                                                Log.e("--shy-", "22222222");

                                                view.onError(BaseView.ERROR_NO_DATA, null);
                                            }
                                        } else {

                                            Log.e("--shy-", "1111111");
                                            view.hasMore(false);
                                        }


                                    }

                                } else {

                                    if (res.getStatus() == 0) {
                                        if (res.getMsg().contains(view.getContext().getString(R.string.frozen_account))) {
                                            Toasts.toast(view.getContext(), res.getMsg());
                                            view.onError(10, "", "", null, "", null);
                                        } else if (res.getStatus() == 10) {
                                            Toasts.toast(view.getContext(), res.getMsg());
                                            view.onError(10, "", "", null, "", null);
                                        } else {
                                            if (page == 1) {
                                                view.refreshing(false);
                                                view.onError(BaseView.ERROR_FAIL, v -> requestFirstRefresh());
                                            } else {
                                                view.onError(view.getContext().getString(R.string.load_error));
                                            }
                                        }
                                    }

                                }
                            }
                        });

        addSubscription(subscription);
    }


    public void requestPageListData(Observable<? extends Res> observable, OnAcceptDataListener onAcceptDataListener, OnBindLocalListener onBindLocalListener, OnAcceptResListener onAcceptResListener, boolean isHasHeader) {

        Subscription subscription =
                observable
                        .subscribeOn(io())
                        .doOnSubscribe(() -> {
                            if (NetUtils.isNetAvailable(view.getContext())) {
                                if (page == 1) {
                                    if (isEnterOutSide) {
                                        view.onError(BaseView.ERROR_LOADING, null);
                                    }
                                }
                            }
                        })

                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new SB<Res>() {
                            @Override
                            public void onError(Throwable e) {
                                super.onError(e);
                                if (view == null) {
                                    return;
                                }
                                if (page == 1) {
                                    view.refreshing(false);


                                    if (!NetUtils.isNetAvailable(view.getContext())) {//当前网络不可用
                                        if (onBindLocalListener != null) {
                                            ArrayList localList = (ArrayList) onBindLocalListener.onBindLocal();
                                            if (localList != null && localList.size() > 0) {
                                                view.bindData(localList, true);
                                                view.onError(BaseView.ERROR_NORMAL, null);
                                            } else {

                                                view.onErrorFail(BaseView.ERROR_NO_NET, view.getContext().getString(R.string.reload), (v) -> requestFirstRefresh());

                                            }
                                        } else {
                                            view.onErrorFail(BaseView.ERROR_NO_NET, view.getContext().getString(R.string.reload), (v) -> requestFirstRefresh());

                                        }
                                    } else {

                                        if (e instanceof IllegalStateException) {
                                            if (isHasHeader) {
                                                view.hasMore(false);
                                                view.onError(BaseView.ERROR_NORMAL, null);
                                            } else {
                                                view.onErrorFail(BaseView.ERROR_NO_DATA, view.getContext().getString(R.string.no_data), v -> requestFirstRefresh());
                                            }
                                        } else if (e instanceof JsonSyntaxException) {
                                            if (isHasHeader) {
                                                view.hasMore(false);
                                                view.onError(BaseView.ERROR_NORMAL, null);
                                            } else {
                                                view.onErrorFail(BaseView.ERROR_NO_DATA, view.getContext().getString(R.string.no_data), v -> requestFirstRefresh());
                                            }
                                        } else if (e instanceof SocketTimeoutException || e instanceof SocketException) {
                                            view.toast(R.string.time_out);
                                            if (isHasHeader) {
                                                view.hasMore(false);
                                            } else {
                                                view.onError(BaseView.ERROR_CUSTOM, view.getContext().getString(R.string.time_out), view.getContext().getString(R.string.the_network_is_not_good), view.getContext().getResources().getDrawable(R.drawable.ic_new_load_nothing), "重新加载", v -> requestFirstRefresh());
                                            }
                                        } else {
                                            view.onErrorFail(BaseView.ERROR_FAIL, view.getContext().getString(R.string.no_data), v -> requestFirstRefresh());
                                        }
                                    }
                                } else {
                                    view.refreshing(false);
                                    if (!NetUtils.isNetAvailable(view.getContext())) {
                                        new YNormalDialog(view.getContext(), view.getContext().getString(R.string.is_to_set_up), view.getContext().getString(R.string.to_set_up), (view1, yDialog) -> {
                                            view.getContext().startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                                        }, true);
                                    } else {
                                        view.onErrorFail(BaseView.ERROR_FAIL, view.getContext().getString(R.string.no_data), v -> requestFirstRefresh());
//                                      view.onError(view.getContext().getString(R.string.load_error));
                                    }

                                }
                            }

                            @Override
                            public void next(Res res) {
                                if (view == null) {
                                    return;
                                }
                                if (onAcceptResListener != null) {
                                    if (onAcceptResListener.onResAccept(res)) {
                                        return;
                                    }
                                }

                                if (res.isOk(view.getContext())) {

                                    if (page == 1) {
                                        view.refreshing(false);
                                    }

                                    ArrayList list = (ArrayList) res.getData();

                                    if (onAcceptDataListener != null) {

                                        if (onAcceptDataListener.onAcceptData(list, res.getMsg(), res.getPoint())) {

                                           /* //获得粮票
                                            if (res.getPoint() != 0 && res.getPoint() != -1) {
                                                Toasts.toast(view.getContext(), "requestPageListData"+res.getPoint());
                                                new CheckInAnimaYDialog(view.getContext(), res.getPoint());
                                            }
*/
                                            return;
                                        }
                                    }


                                    if (list != null && list.size() > 0) {
                                        view.bindData(list, res.isHasMore(page));

                                        if (page == 1 && onBindLocalListener != null) {
//                                            存储
                                            onBindLocalListener.onSaveLocal(list);
                                        }
                                        view.onError(BaseView.ERROR_NORMAL, null);
                                    } else {

                                        if (page == 1) {

                                            if (isHasHeader == true) {
                                                view.hasMore(false);
                                                view.onError(BaseView.ERROR_NORMAL, null);

                                                //view.refreshing(false);
                                            } else {

                                                view.hasMore(false);

                                                if (!NetUtils.isNetAvailable(view.getContext())) {//没网
                                                    //重新加载数据
                                                    view.onError(BaseView.ERROR_NO_DATA, null);
                                                }else{
                                                    //没有数据
                                                    view.onError(BaseView.NO_DATA, null);
                                                }


                                            }
                                        } else {
                                            view.hasMore(false);
                                        }


                                    }

                                } else {

                                    if (res.getStatus() == 0) {
                                        if (res.getMsg().contains(view.getContext().getString(R.string.frozen_account))) {
                                            Toasts.toast(view.getContext(), res.getMsg());
                                            view.onError(10, "", "", null, "", null);
                                        } else if (res.getStatus() == 10) {
                                            Toasts.toast(view.getContext(), res.getMsg());
                                            view.onError(10, "", "", null, "", null);
                                        } else {
                                            if (page == 1) {
                                                view.refreshing(false);
                                                view.onError(BaseView.ERROR_FAIL, v -> requestFirstRefresh());
                                            } else {
                                                view.onError(view.getContext().getString(R.string.load_error));
                                            }
                                        }
                                    }

                                }
                            }
                        });

        addSubscription(subscription);
    }


    /**
     * 添加 onAcceptResListener参数
     * @param observable
     * @param onAcceptDataListener
     * @param onBindLocalListener
     * @param onAcceptResListener
     * @param isHasHeader
     */
    public void requestNormalListData(Observable<? extends Res> observable, OnAcceptDataListener onAcceptDataListener, OnBindLocalListener onBindLocalListener, OnAcceptResListener onAcceptResListener, boolean isHasHeader) {
        Subscription subscription =
                observable
                        .subscribeOn(io())
                        .doOnSubscribe(() -> {
                            if (NetUtils.isNetAvailable(view.getContext())) {
                                Log.d("RLRVPresenter", "page:" + page);
                                if (page == 1) {
//                                    if (isEnterOutSide) {
//                                        view.onError(ERROR_LOADING, null);
//                                    }
                                }
                            }
                        })
                        .observeOn(mainThread())
                        .subscribe(new SB<Res>() {
                            @Override
                            public void onError(Throwable e) {
                                super.onError(e);
                                if (view == null) {
                                    return;
                                }
                                if (page == 1) {
                                    view.refreshing(false);
                                    if (!NetUtils.isNetAvailable(view.getContext())) {
                                        if (onBindLocalListener != null) {
                                            ArrayList localList = (ArrayList) onBindLocalListener.onBindLocal();
                                            if (localList != null && localList.size() > 0) {
                                                view.bindData(localList, true);
//                                                view.onError(ERROR_NORMAL, null);
                                            } else {
                                                view.onError(BaseView.ERROR_NO_NET, (v) -> requestFirstRefresh());

//                                                new YNormalDialog(view.getContext(), "监测到您的网络不可用,是否去设置打开?", "去设置", (view1, yDialog) -> {
//                                                    view.getContext().startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
//                                                }, true);
                                            }
                                        } else {
                                            view.onError(BaseView.ERROR_NO_NET, (v) -> requestFirstRefresh());

//                                            new YNormalDialog(view.getContext(), "监测到您的网络不可用,是否去设置打开?", "去设置", (view1, yDialog) -> {
//                                                view.getContext().startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
//                                            }, true);
                                        }
                                    } else {
                                        if (e instanceof IllegalStateException) {

//                                            if (isHasHeader) {
                                            view.hasMore(false);
//                                            } else {
//                                                view.onError(ERROR_NO_DATA, v -> requestFirstRefresh());
//                                            }

                                        } else if (e instanceof JsonSyntaxException) {
                                            view.hasMore(false);
                                        } else if (e instanceof SocketTimeoutException || e instanceof SocketException) {
                                            view.toast(R.string.time_out);
//                                            view.onError(ERROR_NO_DATA, v -> requestFirstRefresh());
                                            view.hasMore(false);

                                        } else {

//                                          view.onError(ERROR_FAIL, v -> requestFirstRefresh());
                                        }
                                    }
                                } else {
                                    view.refreshing(false);
                                    if (!NetUtils.isNetAvailable(view.getContext())) {
                                        new YNormalDialog(view.getContext(), view.getContext().getString(R.string.is_to_set_up), view.getContext().getString(R.string.to_set_up), (view1, yDialog) -> {
                                            view.getContext().startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                                        }, true);
                                    } else {
//                                        view.onError(view.getContext().getString(R.string.load_error));
                                        //
                                    }

                                }
                            }

                            @Override
                            public void next(Res res) {
                                if (view == null) {
                                    return;
                                }
                                if (res.isOk(view.getContext())) {
                                    if (page == 1) {
                                        view.refreshing(false);
                                    }

                                    if (onAcceptResListener != null) {
                                        onAcceptResListener.onResAccept(res);
                                        ArrayList list = (ArrayList) res.getData();

                                        if (list != null && list.size() > 0) {
                                            view.bindData(list, res.isHasMore(page));

                                            if (page == 1 && onBindLocalListener != null) {
//                                            存储
                                                onBindLocalListener.onSaveLocal(list);
                                            }
                                            view.onError(BaseView.ERROR_NORMAL, null);
                                        } else {

                                            if (page == 1) {

                                                if (isHasHeader == true) {
                                                    view.hasMore(false);
                                                    view.onError(BaseView.ERROR_NORMAL, null);
                                                    //view.refreshing(false);
                                                } else {
                                                    Log.e("Asafk", "ERROR_NO_DATA");
//                                                    view.onError(BaseView.ERROR_NO_DATA, null);
                                                }
                                            } else {
                                                view.hasMore(false);
                                            }
                                        }
                                        return;
                                    }
                                    ArrayList list = (ArrayList) res.getData();
                                    if (onAcceptDataListener != null) {
                                        if (onAcceptDataListener.onAcceptData(list, res.getMsg(), res.getPoint())) {
                                            return;
                                        }
                                    }

                                    if (list != null && list.size() > 0) {
                                        view.bindData(list, res.isHasMore(page));

                                        if (page == 1 && onBindLocalListener != null) {
//                                            存储
                                            onBindLocalListener.onSaveLocal(list);
                                        }
//                                        view.onError(ERROR_NORMAL, null);
                                    } else {
                                        if (page == 1) {
                                            view.hasMore(false);

//                                            view.onError(ERROR_NO_DATA, null);
                                        }
                                    }
                                } else {

                                    Log.d("RLRVPresenter", "onAcceptDataListener is not ok");

                                    if (res.getStatus() == 0) {
                                        if (res.getMsg().contains(view.getContext().getString(R.string.frozen_account))) {
                                            Toasts.toast(view.getContext(), res.getMsg());
                                            view.onError(10, "", "", null, "", null);
                                        } else if (res.getStatus() == 10) {
                                            Toasts.toast(view.getContext(), res.getMsg());
                                            view.onError(10, "", "", null, "", null);
                                        } else {
                                            if (page == 1) {
                                                view.refreshing(false);
//                                        view.onError(ERROR_FAIL, v -> requestFirstRefresh());
                                                view.hasMore(false);

                                            } else {
                                                view.onError(view.getContext().getString(R.string.load_error));
                                            }
                                        }
                                    }
                                }
                            }
                        });


        addSubscription(subscription);
    }

    public void saveList(ArrayList list, int saveType) {

    }

    public ArrayList getList(int saveType) {

        return null;
    }
}
