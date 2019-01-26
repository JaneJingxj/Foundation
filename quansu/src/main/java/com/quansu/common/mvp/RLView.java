package com.quansu.common.mvp;

/**
 * Created by xianguangjin on 16/5/18.
 */
public interface RLView<P> extends BaseView {

    void refreshing(boolean refreshing);

    void onError(String s);

    void loading(boolean loading);

    P getParams();

    void hasMore(boolean isHasMore);
}
