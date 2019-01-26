package com.quansu.widget.flowlayout;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public abstract class TagAdapter<T> {
    protected ArrayList<T> mTagDatas = new ArrayList<T>();
    private OnDataChangedListener mOnDataChangedListener;
    private HashSet<Integer> mCheckedPosList = new HashSet<Integer>();
    protected LayoutInflater layoutInflater;

    public TagAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
    }

    public TagAdapter(ArrayList<T> datas) {
        mTagDatas = datas;

    }

    public TagAdapter(T[] datas) {
        mTagDatas = new ArrayList<T>(Arrays.asList(datas));
    }

    static interface OnDataChangedListener {
        void onChanged();
    }

    void setOnDataChangedListener(OnDataChangedListener listener) {
        mOnDataChangedListener = listener;
    }

    public void setSelectedList(int... pos) {
        for (int i = 0; i < pos.length; i++)
            mCheckedPosList.add(pos[i]);
        notifyDataChanged();
    }

    public void setSelectedList(Set<Integer> set) {
        mCheckedPosList.clear();
        mCheckedPosList.addAll(set);
        notifyDataChanged();
    }

    HashSet<Integer> getPreCheckedList() {
        return mCheckedPosList;
    }


    public int getCount() {
        return mTagDatas == null ? 0 : mTagDatas.size();
    }

    public void notifyDataChanged() {
        mOnDataChangedListener.onChanged();
    }

    public T getItem(int position) {
        return mTagDatas.get(position);
    }

    public abstract View getView(FlowLayout parent, int position, T t);

    public boolean setSelected(int position, T t) {
        return false;
    }


    public void addTag(T tag) {
        this.mTagDatas.add(tag);
        notifyDataChanged();
    }

    public void setTagDatas(ArrayList<T> datas) {
        this.mTagDatas.clear();
        addTagDatas(datas);
    }

    public void addTagDatas(ArrayList<T> datas) {
        this.mTagDatas.addAll(datas);
        notifyDataChanged();
    }

    public ArrayList<T> getmTagDatas() {
        return mTagDatas;
    }

    public String getmTagDatasStr() {
        StringBuilder builder = new StringBuilder();

        for (T mTagData : mTagDatas) {
            builder.append(mTagData);
            builder.append(",");
        }

        String substring = builder.toString();

        if (!TextUtils.isEmpty(substring)) {
            substring = substring.substring(0, builder.length() - 1);
        }

        return substring;
    }
}