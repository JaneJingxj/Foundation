package com.quansu.utils;

import android.os.Bundle;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 对Bundle进行响应式的封装
 * Created by xianguangjin on 15/12/23.
 */
public class BUN {

    private Bundle bundle;

    public BUN() {
        this.bundle = new Bundle();
    }


    public BUN putString(String name, String value) {
        this.bundle.putString(name, value);
        return this;
    }

    public BUN putStringArray(String name, String[] value) {
        this.bundle.putStringArray(name, value);
        return this;
    }

    public BUN putInt(String name, int value) {
        this.bundle.putInt(name, value);
        return this;
    }

    public BUN putBoolean(String name, boolean value) {
        this.bundle.putBoolean(name, value);
        return this;
    }

    public BUN putSR(String name, Serializable value) {
        this.bundle.putSerializable(name, value);
        return this;
    }

    public BUN putP(String name, Parcelable value) {
        this.bundle.putParcelable(name, value);
        return this;
    }

    public BUN putPARR(String name, ArrayList<? extends Parcelable> value) {
        this.bundle.putParcelableArrayList(name, value);
        return this;
    }

    public BUN PutSARR(String name, ArrayList<Serializable> value) {
        this.bundle.putSerializable(name, value);
        return this;
    }

    /**
     * @return 操作完成
     */
    public Bundle ok() {
        return this.bundle;
    }

}
