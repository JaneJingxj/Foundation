package com.quansu.widget.dialog;

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
import java.util.List;


public class SheetListDialog extends SheetDialog {

    private List<String> stringList;
    private OnDialogListClickListener onDialogListClickListener;

    private TextView tvCancel;
    private RecyclerView iRecyclerView;

    public SheetListDialog(Context context, @ArrayRes int stringArr, OnDialogListClickListener onDialogListClickListener) {
        super(context);
        stringList = new ArrayList<>();
        String[] stringArray = _context.getResources().getStringArray(stringArr);
        for (String s : stringArray) {
            stringList.add(s);
        }
        this.onDialogListClickListener = onDialogListClickListener;
        initView(_Layout);
    }

    public SheetListDialog(Context context, List<String> stringList, OnDialogListClickListener onDialogListClickListener) {
        super(context);
        this.stringList = stringList;
        this.onDialogListClickListener = onDialogListClickListener;
        initView(_Layout);
    }

    @Override
    protected void initView(View view) {
        tvCancel = (TextView) view.findViewById(R.id.tv_cancel);
        iRecyclerView = (RecyclerView) view.findViewById(R.id.iRecyclerView);

        ArrayList<KeyValue> keyValues = new ArrayList<>();

        if (stringList != null && stringList.size() > 0) {

            for (int i = 0; i < stringList.size(); i++) {
                KeyValue keyValue = new KeyValue(i + "", stringList.get(i));
                keyValues.add(keyValue);
            }
        }

        KeyValueAdapter keyValueAdapter = new KeyValueAdapter(_context, KeyValueAdapter.DIALOG_LIST_NORMAL);
        iRecyclerView.setAdapter(keyValueAdapter);
        keyValueAdapter.setData(keyValues);
        keyValueAdapter.setmOnItemClickListener((position, o, v) -> {
            if (onDialogListClickListener != null) {
                onDialogListClickListener.onClick(v, SheetListDialog.this, position, (KeyValue) o);
                dismiss();
            }
        });

        tvCancel.setOnClickListener(v -> {
            dismiss();
        });
    }


    @Override
    public int provideLayoutId() {
        return R.layout.dialog_list;
    }
}
