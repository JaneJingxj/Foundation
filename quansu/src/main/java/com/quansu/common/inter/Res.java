package com.quansu.common.inter;

import android.content.Context;

/**
 * Created by xianguangjin on 16/7/29.
 * <p>
 * 我的GitHub: https://github.com/ysnows
 * <p>
 * 加油,做一个真的汉子
 */

public interface Res<T> {

    /**
     * 获取数据
     *
     * @return
     */
    T getData();

    /**
     * 获取说明信息
     *
     * @return
     */
    String getMsg();


    /**
     * 获取积分
     *
     * @return
     */
    int getPoint();


    /**
     * 获取 是否成功
     *
     * @param context
     *
     * @return
     */
    boolean isOk(Context context);



    boolean isOk();

    /**
     * 获取 是否成功
     *
     * @param context
     *
     * @return
     */
    boolean isOk(Context context, OnFailListener onFailListener);

    /**
     * 是否有更多
     *
     * @param page
     *
     * @return
     */
    boolean isHasMore(int page);


    boolean isHasMoreListId(int page);


    /**
     * 是否有更多
     *
     * @return
     */
    boolean isHasMore();

    int getStatus();

    String geturl();

    String getLoginStatus();

    int getToTal();


    String getAndroidurl();


}
