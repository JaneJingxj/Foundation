package com.quansu.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.ArrayRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.quansu.common.inter.OnDialogListClickListener;
import com.quansu.ui.adapter.KeyValueAdapter;
import com.quansu.ui.mvp.model.KeyValue;
import com.ysnows.quansu.R;

import java.util.ArrayList;


public class YListDialog extends YDialog {

    private int stringArr;
    private OnDialogListClickListener onDialogListClickListener;
    private boolean isShow;
    public Dialog mDialog;

    private TextView tvCancel;
    private RecyclerView iRecyclerView;

    public YListDialog(Context context, @ArrayRes int stringArr, OnDialogListClickListener onDialogListClickListener, boolean isShow) {
        super(context, BOTTOM);
        this.stringArr = stringArr;
        this.onDialogListClickListener = onDialogListClickListener;
        this.isShow = isShow;
        initView(_Layout);
    }

    @Override
    protected void initView(View view) {
        tvCancel = (TextView) view.findViewById(R.id.tv_cancel);
        iRecyclerView = (RecyclerView) view.findViewById(R.id.iRecyclerView);

        String[] stringArray = _context.getResources().getStringArray(stringArr);

        ArrayList<KeyValue> keyValues = new ArrayList<>();
        for (int i = 0; i < stringArray.length; i++) {
            KeyValue keyValue = new KeyValue(i + "", stringArray[i]);
            keyValues.add(keyValue);
        }

        KeyValueAdapter keyValueAdapter = new KeyValueAdapter(_context, KeyValueAdapter.DIALOG_LIST_NORMAL);
        iRecyclerView.setAdapter(keyValueAdapter);
        keyValueAdapter.setData(keyValues);
        keyValueAdapter.setmOnItemClickListener((position, o, v) -> {
            if (onDialogListClickListener != null) {
                onDialogListClickListener.onClick(v, YListDialog.this, position, (KeyValue) o);
                dismiss();
            }
        });

        tvCancel.setOnClickListener(v -> {
            dismiss();
        });

        if (isShow) {
            show();
        }

    }


    @Override
    public int provideLayoutId() {
        return R.layout.dialog_list;
    }
}
