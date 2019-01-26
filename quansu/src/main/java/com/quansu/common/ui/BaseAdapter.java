package com.quansu.common.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import com.quansu.common.inter.BaseAdapterInter;
import com.quansu.common.mvp.BasePresenter;
import com.quansu.common.mvp.BaseView;
import com.quansu.ui.adapter.OnItemClickListener;
import com.quansu.widget.irecyclerview.IViewHolder;

import java.util.ArrayList;

/**
 * Created by xianguangjin on 16/6/2.
 */

public abstract class BaseAdapter extends RecyclerView.Adapter<IViewHolder> implements BaseAdapterInter {

    public LayoutInflater inflater;
    public Context context;

    public ArrayList data = new ArrayList();

    public BaseView view;

    public OnItemClickListener mOnItemClickListener;
    protected BasePresenter presenter;

    public BaseAdapter(Context context) {
        this.context = context;
        this.view = (BaseView) context;
        inflater = LayoutInflater.from(context);
    }

    public BaseAdapter(Context context, BasePresenter presenter) {
        this.context = context;
        this.presenter = presenter;
        inflater = LayoutInflater.from(context);
    }

    public BaseAdapter(Context context, ArrayList data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    public BaseAdapter(Context context, ArrayList data, BasePresenter presenter) {
        this.context = context;
        this.data = data;
        this.presenter = presenter;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {

        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public void setData(ArrayList data) {
        clear();
        this.data.addAll(data);
        notifyItemRangeInserted(0, data.size());
    }

    @Override
    public void addData(ArrayList data) {
        this.data.addAll(data);
        notifyItemRangeInserted(this.data.size() - 1, data.size());
    }

    @Override
    public void changeData(ArrayList data) {
        this.data.addAll(data);
        notiDataSetChanged();
    }


    @Override
    public void clear() {
        this.data.clear();
        notifyDataSetChanged();
    }

    @Override
    public void remove(int position) {
        this.data.remove(position);
        notifyItemRemoved(position);

        if (position != data.size()) {
            notifyItemRangeChanged(position, data.size() - position);
        }

//        notifyDataSetChanged();

    }


    @Override
    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }


    @Override
    public void insertToFirst(Object item) {
        this.getData().add(0, item);
        this.getAdapter().notifyItemInserted(0);
        notifyItemRangeChanged(0, getItemCount());
    }


    /**
     * 获取数据
     *
     * @return
     */
    @Override
    public ArrayList getData() {
        return data;
    }


    public Context getContext() {
        return context;
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return this;
    }

    public LayoutInflater getInflater() {
        return inflater;
    }

    public BaseView getView() {
        return view;
    }

    @Override
    public void notiDataSetChanged() {
        this.notifyDataSetChanged();
    }


    @Override
    public void notiItemChanged(int pos) {
        this.notifyItemChanged(pos);
    }


    @Override
    public void notiItemChanged(int pos, Object payload) {
        this.notifyItemChanged(pos, payload);
    }
}
