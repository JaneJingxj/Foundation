package com.quansu.common.mvp;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by xianguangjin on 16/6/3.
 */

public class TAB {

    public String name;
    public Fragment fragment;
    public Bundle bundle;

    public TAB(String name, Fragment fragment, Bundle bundle) {
        this.name = name;
        this.fragment = fragment;
        this.bundle = bundle;
    }

    public TAB(String name) {
        this.name = name;
    }
}
