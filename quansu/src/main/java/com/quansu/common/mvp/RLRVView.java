package com.quansu.common.mvp;

import com.quansu.common.inter.BaseAdapterInter;

/**
 * Created by xianguangjin on 16/6/3.
 * 带有RL的Recycleview
 */

public interface RLRVView<P extends RLRVPresenter, T> extends RLView<P>, BindView<T> {
    BaseAdapterInter getAdapter();

    P getRP();
}
