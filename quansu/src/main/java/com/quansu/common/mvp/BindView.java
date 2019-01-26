package com.quansu.common.mvp;

/**
 * Created by xianguangjin on 16/6/3.
 *
 * @param <T> 绑定数据的View
 */

public interface BindView<T> extends BaseView {
    void bindData(T t, boolean isHaseMore);
}
