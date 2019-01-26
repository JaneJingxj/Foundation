package com.quansu.ui.mvp.model;

import static android.text.TextUtils.isEmpty;

/**
 * Created by xianguangjin on 16/6/24.
 * <p>
 * 我的GitHub: https://github.com/ysnows
 * <p>
 * 加油,做一个真的汉子
 */

public class Pic {
    public static final int NORMAL = 1;
    public static final int TO_ADD = 2;
    public static final int LOADING = 3;
    public static final int SELECTED = 4;

    public String url;
    public int type = NORMAL;
    public int status = NORMAL;
    public int mode = 1;

    public Pic(String url, int type) {
        this.url = url;
        this.type = type;
    }

    public Pic(String url, int type, int mode) {
        this.url = url;
        this.type = type;
        this.mode = mode;
    }

    public boolean isPath() {
        return !(isEmpty(url) || url.startsWith("http") || url.startsWith("https"));
    }

}
