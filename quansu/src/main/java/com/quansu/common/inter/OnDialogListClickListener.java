package com.quansu.common.inter;

import android.view.View;

import com.quansu.ui.mvp.model.KeyValue;
import com.quansu.widget.dialog.DialogInter;

/**
 * Created by xianguangjin on 16/3/11.
 */
public interface OnDialogListClickListener {
    void onClick(View vw, DialogInter dialog, int position, KeyValue keyValue);
}
