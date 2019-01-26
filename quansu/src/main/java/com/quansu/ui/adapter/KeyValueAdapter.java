package com.quansu.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.quansu.common.ui.BaseAdapter;
import com.quansu.ui.mvp.model.KeyValue;
import com.quansu.utils.glide.GlideUtils;
import com.quansu.widget.irecyclerview.IViewHolder;
import com.ysnows.quansu.R;

import java.util.ArrayList;

/**
 * Created by xianguangjin on 16/6/2.
 */

public class KeyValueAdapter extends BaseAdapter {


    private int type = WITHLINE;

    public static final int WITHLINE = 1;
    public static final int WITHARROW = 2;
    public static final int DIALOG_LIST_NORMAL = 3;
    public static final int IMG_NAME = 4;
    public static final int ITEM_LINE = 5;
    public static final int ITEM_LINE_SEARCH = 6;


    public KeyValueAdapter(Context context) {
        super(context);
    }

    public KeyValueAdapter(Context context, int type) {
        super(context);
        this.type = type;
    }


    public KeyValueAdapter(Context context, ArrayList data) {
        super(context, data);
    }


    @Override
    public IViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = null;

        switch (type) {
            case WITHLINE:
                itemView = LayoutInflater.from(context).inflate(R.layout.item_key_value_withline, null);
                break;
            case WITHARROW:
                itemView = LayoutInflater.from(context).inflate(R.layout.item_key_value, null);
                break;
            case DIALOG_LIST_NORMAL:
                itemView = LayoutInflater.from(context).inflate(R.layout.item_dialog_list_normal, null);
                break;
            case IMG_NAME:
                itemView = LayoutInflater.from(context).inflate(R.layout.product_gridviewitem, null);
                break;
            case ITEM_LINE:
                itemView = LayoutInflater.from(context).inflate(R.layout.item_area_select, null);
                break;
            case ITEM_LINE_SEARCH:
                itemView = LayoutInflater.from(context).inflate(R.layout.item_search_select, null);
                break;

        }

        return new VHolder(itemView);
    }

    @Override
    public void onBindViewHolder(IViewHolder holder, int position) {
        if (holder != null) {
            VHolder vHolder = (VHolder) holder;

            KeyValue item = (KeyValue) data.get(position);
            if (type == ITEM_LINE || type == ITEM_LINE_SEARCH) {
                if (vHolder._TvName != null) {
                    vHolder._TvName.setText(item.name);
                }
            } else {
                if (vHolder._Btn != null) {
                    vHolder._Btn.setText(item.name);
                }
            }

            if (vHolder._Img != null) {
                GlideUtils.lImg(context, item.value, vHolder._Img,true);
            }

            vHolder.itemView.setOnClickListener(v -> {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(position, item, v);
                }
            });
        }
    }

    static class VHolder extends IViewHolder {
        @Nullable
        TextView _TvName;

        @Nullable
        TextView _Btn;

        @Nullable
        ImageView _Img;

        VHolder(View view) {
            super(view);
            _Btn = view.findViewById(R.id.btn);
            _TvName = view.findViewById(R.id.tv_name);
            _Img = view.findViewById(R.id.img);

        }
    }
}
