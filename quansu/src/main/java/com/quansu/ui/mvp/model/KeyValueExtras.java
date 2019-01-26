package com.quansu.ui.mvp.model;

/**
 * Created by xianguangjin on 16/8/26.
 * <p>
 * 我的GitHub: https://github.com/ysnows
 * <p>
 * 加油,做一个真的汉子
 */

public class KeyValueExtras extends KeyValue {

    public KeyValueExtras(String value, String name, String extra) {
        super(value, name);
        this.extra = extra;
    }

    public KeyValueExtras(String value, String name, int extraInt) {
        super(value, name);
        this.extraInt = extraInt;
    }

    public KeyValueExtras(String value, String name, int extraInt, String extra) {
        super(value, name);
        this.extraInt = extraInt;
        this.extra = extra;
    }

    public String extra;
    public int extraInt;

}
