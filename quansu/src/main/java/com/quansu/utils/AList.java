package com.quansu.utils;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by xianguangjin on 15/12/25.
 */
public class AList<E> {

    public ArrayList<E> list = new ArrayList<>();


    public AList add(E obj) {
        list.add(obj);
        return this;
    }

    public AList addAll(Collection<? extends E> objs) {
        list.addAll(objs);
        return this;
    }

    public AList remove(int index) {
        list.remove(index);
        return this;
    }

    public ArrayList<E> ok() {
        return list;
    }

    public static ArrayList toArray(Object[] objects) {
        ArrayList arrayList = new ArrayList<>();
        for (Object object : objects) {
            arrayList.add(object);
        }

        return arrayList;

    }
}
