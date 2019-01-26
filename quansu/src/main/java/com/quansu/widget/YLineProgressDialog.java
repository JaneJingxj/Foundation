package com.quansu.widget;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.quansu.common.inter.OnDialogClickListener;
import com.quansu.widget.dialog.YDialog;
import com.ysnows.quansu.R;

/**
 * Created by xianguangjin on 16/7/13.
 * <p>
 * 我的GitHub: https://github.com/ysnows
 * <p>
 * 加油,做一个真的汉子
 */

public class YLineProgressDialog extends YDialog {

    public String title;
    public String cancelText;
    public String ensureText;
    public OnDialogClickListener cancelListener;
    public OnDialogClickListener ensureListener;
    public TextView tvTitle;
    public LVLineWithText lvline;
    public Button btnEnsure;
    public Button btnCancel;

    public YLineProgressDialog(Context context, OnDialogClickListener cancelListener, OnDialogClickListener ensureListener) {
        super(context, CENTER);
        this.cancelListener = cancelListener;
        this.ensureListener = ensureListener;
        initView(_Layout);
    }

    public YLineProgressDialog(Context context, String title) {
        super(context, CENTER);
        this.title = title;
        initView(_Layout);
    }

    public YLineProgressDialog(Context context, String title, String cancelText, String ensureText, OnDialogClickListener cancelListener, OnDialogClickListener ensureListener) {
        super(context, CENTER);
        this.title = title;
        this.cancelText = cancelText;
        this.ensureText = ensureText;
        this.cancelListener = cancelListener;
        this.ensureListener = ensureListener;
        initView(_Layout);
    }

    @Override
    protected void initView(View view) {
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        lvline = (LVLineWithText) view.findViewById(R.id.lvline);
        btnEnsure = (Button) view.findViewById(R.id.btn_ensure);
        btnCancel = (Button) view.findViewById(R.id.btn_cancel);

        if (cancelListener == null) {
            btnCancel.setVisibility(View.GONE);
        }
        if (ensureListener == null) {
            btnEnsure.setVisibility(View.GONE);
        }

        btnCancel.setOnClickListener(v -> {
            dismiss();
            cancelListener.onClick(v, YLineProgressDialog.this);
        });

        btnEnsure.setOnClickListener(v -> {
            dismiss();
            ensureListener.onClick(v, YLineProgressDialog.this);
        });

        if (!TextUtils.isEmpty(cancelText)) {
            btnCancel.setText(cancelText);
        }
        if (!TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
        }
        if (!TextUtils.isEmpty(ensureText)) {
            btnEnsure.setText(ensureText);
            btnEnsure.setBackgroundColor(Color.parseColor("#777777"));


        }

        _Dialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public int provideLayoutId() {
        return R.layout.dialog_line_progress;
    }

    public void setValue(int value) {
        lvline.setValue(value);
    }
}
