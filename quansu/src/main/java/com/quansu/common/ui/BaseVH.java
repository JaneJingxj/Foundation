package com.quansu.common.ui;

import android.view.View;

import com.quansu.widget.irecyclerview.IViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Created by xianguangjin on 2017/2/24.
 */

public class BaseVH extends IViewHolder {
    public BaseVH(View itemView) {
        super(itemView);
        AutoUtils.autoSize(itemView);
    }
}
