package com.quansu.common.mvp;


/**
 * Created by xianguangjin on 16/5/18.
 * RL
 */
public abstract class RLPresenter<V extends RLView> extends BasePresenter<V> {

    /**
     * 获取新数据
     */
    public abstract void requestDataRefresh();

    public abstract void requestFirstRefresh();

    /**
     * 加载更多数据
     */
    public abstract void loadMore();



}
