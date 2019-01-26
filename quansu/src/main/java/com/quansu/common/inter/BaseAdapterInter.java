package com.quansu.common.inter;

import android.support.v7.widget.RecyclerView;

import com.quansu.ui.adapter.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xianguangjin on 16/8/3.
 * <p>
 * 我的GitHub: https://github.com/ysnows
 * <p>
 * 加油,做一个真的汉子
 */
public interface BaseAdapterInter {

    void setData(ArrayList data);


    void insertToFirst(Object item);

    void addData(ArrayList data);

    void changeData(ArrayList data);

    void clear();

    void remove(int position);

    void setmOnItemClickListener(OnItemClickListener mOnItemClickListener);

    ArrayList getData();

    RecyclerView.Adapter getAdapter();

    int getItemCount();

    void notiDataSetChanged();

    void notiItemChanged(int pos);

    void notiItemChanged(int pos, Object pay);


}
