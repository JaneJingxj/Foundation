package com.quansu.common.inter;

import android.widget.Checkable;

import com.quansu.widget.OnCheckedListener;

/**
 * Created by xianguangjin on 16/7/11.
 * <p>
 * 我的GitHub: https://github.com/ysnows
 * <p>
 * 加油,做一个真的汉子
 */

public interface CheckBoxLayoutInter extends Checkable {

    public String getTvText();

    public void setOnCheckedListener(OnCheckedListener onCheckedListener);
}
