package com.quansu.common.inter;

/**
 * Created by xianguangjin on 16/7/31.
 * <p>
 * 我的GitHub: https://github.com/ysnows
 * <p>
 * 加油,做一个真的汉子
 */
@Deprecated
public interface OnAcceptDataListener<D> {


    /**
     * @param d
     * @param msg
     * @param point
     *
     * @return
     */
//    boolean onAcceptData(D d, String msg);
    @Deprecated
    boolean onAcceptData(D d, String msg, int point);
}
