package com.quansu.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.quansu.common.inter.BaseAdapterInter;

import java.util.ArrayList;

/**
 * Created by xianguangjin on 16/8/2.
 * <p>
 * 我的GitHub: https://github.com/ysnows
 * <p>
 * 加油,做一个真的汉子
 */

public abstract class BaseSwipeAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerSwipeAdapter<VH> implements BaseAdapterInter {

    public ArrayList data = new ArrayList();
    public OnItemClickListener mOnItemClickListener;
    public Context context;


    public BaseSwipeAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void setData(ArrayList data) {
        clear();
        addData(data);
    }

    @Override
    public void addData(ArrayList data) {
        int positionStart = data.size();
        int itemCount = data.size();
        this.data.addAll(data);
        notifyDataSetChanged();

    }

    @Override
    public void clear() {
        this.data.clear();
        notifyDataSetChanged();
    }


    @Override
    public void remove(int position) {
        this.data.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public ArrayList getData() {
        return data;
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return this;
    }

    @Override
    public void notiDataSetChanged() {
        notifyDataSetChanged();
    }

    @Override
    public void changeData(ArrayList data) {

    }

    @Override
    public void insertToFirst(Object item) {
        this.getData().add(0, item);
        this.getAdapter().notifyItemInserted(0);
    }
}
