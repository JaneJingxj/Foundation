package com.quansu.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.quansu.common.inter.OnDialogClickListener;
import com.quansu.widget.CheckBoxLayout;
import com.ysnows.quansu.R;


public class YNormalDialog extends YDialog {

    private String title;
    private String content;
    private String ensure;
    private String cancel;
    private boolean isCheckShow = false;
    private OnDialogClickListener ensureListener;
    private OnDialogClickListener cancelListener;
    public boolean sigleBtn;
    private boolean isShow;
    private boolean isNoCancel = false;
    private String other;
    private OnDialogClickListener otherListener;
    public TextView tvTitle;
    private Context context;
    public Dialog mDialog;
    public Button btnCancel;
    public Button btnEnsure;
    public TextView tvContent;
    @Nullable
    public Button btnOther;
    @Nullable
    public CheckBoxLayout cbIsCheck;


    public YNormalDialog(Context context, String content, OnDialogClickListener ensureListener, boolean isShow) {
        super(context, CENTER);
        this.content = content;
        this.ensureListener = ensureListener;
        this.isShow = isShow;
        initView(_Layout);
    }


    public YNormalDialog(Context context, String content, String ensure, OnDialogClickListener ensureListener, boolean isShow) {
        super(context, CENTER);
        this.content = content;
        this.ensure = ensure;
        this.ensureListener = ensureListener;
        this.isShow = isShow;
        initView(_Layout);
    }

    public YNormalDialog(Context context, String content, String ensure, OnDialogClickListener ensureListener, boolean sigleBtn, boolean isShow) {
        super(context, CENTER);
        this.content = content;
        this.ensure = ensure;
        this.ensureListener = ensureListener;
        this.sigleBtn = sigleBtn;
        this.isShow = isShow;
        initView(_Layout);
    }

    public YNormalDialog(Context context, String title, String content, String ensure, String cancel, OnDialogClickListener ensureListener, OnDialogClickListener cancelListener, boolean isShow) {
        super(context, CENTER);
        this.title = title;
        this.content = content;
        this.ensure = ensure;
        this.cancel = cancel;
        this.ensureListener = ensureListener;
        this.cancelListener = cancelListener;
        this.isShow = isShow;
        initView(_Layout);
    }


    public YNormalDialog(Context context, String title, String content, String ensure, String cancel, String other, OnDialogClickListener ensureListener, OnDialogClickListener cancelListener, OnDialogClickListener otherListener, boolean isShow) {
        super(context, CENTER);
        this.title = title;
        this.content = content;
        this.ensure = ensure;
        this.cancel = cancel;
        this.other = other;
        this.ensureListener = ensureListener;
        this.cancelListener = cancelListener;
        this.otherListener = otherListener;
        this.isShow = isShow;
        initView(_Layout);
    }

    public YNormalDialog(Context context, String title, String content, String ensure, String cancel, boolean isCheckShow, OnDialogClickListener ensureListener, OnDialogClickListener cancelListener, boolean isShow) {
        super(context, CENTER);
        this.title = title;
        this.content = content;
        this.ensure = ensure;
        this.cancel = cancel;
        this.isCheckShow = isCheckShow;
        this.ensureListener = ensureListener;
        this.cancelListener = cancelListener;
        this.isShow = isShow;
        initView(_Layout);
    }

    @Override
    protected void initView(View layout) {

        tvTitle = _Layout.findViewById(R.id.tv_title);
        tvContent = _Layout.findViewById(R.id.tv_text);
        btnEnsure = _Layout.findViewById(R.id.btn_ensure);
        btnCancel = _Layout.findViewById(R.id.btn_cancel);
        btnOther = _Layout.findViewById(R.id.btn_other);
        cbIsCheck = _Layout.findViewById(R.id.checkbox_is_check);


        if (!TextUtils.isEmpty(title)) {
            tvTitle.setVisibility(View.VISIBLE);
            tvTitle.setText(title);
        }
        if (!TextUtils.isEmpty(content)) {
            tvContent.setVisibility(View.VISIBLE);
            tvContent.setText(content);
        }
        if (!TextUtils.isEmpty(ensure)) {
            btnEnsure.setVisibility(View.VISIBLE);
            btnEnsure.setText(ensure);
            btnEnsure.setBackgroundColor(Color.parseColor("#F9AFBD"));
        }
        if (!TextUtils.isEmpty(cancel)) {
            btnCancel.setVisibility(View.VISIBLE);
            btnCancel.setText(cancel);
        }

        if (!TextUtils.isEmpty(other)) {
            if (btnOther != null) {
                btnOther.setVisibility(View.VISIBLE);
                btnOther.setText(other);
            }
        }


        if (isCheckShow) {
            if (cbIsCheck != null) {

                cbIsCheck.setVisibility(View.VISIBLE);
                cbIsCheck.getTvText().setTextColor(Color.parseColor("#F9AFBD"));
                cbIsCheck.setChecked(false);
            }
        }

        if (sigleBtn) {
            btnCancel.setVisibility(View.GONE);
        }

        btnEnsure.setOnClickListener(view -> {
            dismiss();
            if (ensureListener != null) {
                ensureListener.onClick(view, YNormalDialog.this);
            }
        });

        btnCancel.setOnClickListener(view -> {
            dismiss();
            if (cancelListener != null) {
                cancelListener.onClick(view, YNormalDialog.this);
            }
        });

        btnOther.setOnClickListener(view -> {
            dismiss();
            if (otherListener != null) {
                otherListener.onClick(view, YNormalDialog.this);
            }
        });

        if (isShow) {
            show();
        }


    }


    public void setBtnEnsureVisible(boolean isVisible) {
        btnEnsure.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    public void setBtnCancelVisible(boolean isVisible) {
        btnCancel.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    public void setBtnsVisible(boolean isVisible) {
        btnCancel.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        btnEnsure.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    @Override
    protected int getPaddingLeft() {
        return 50;
    }

    public boolean isChecked() {
        return cbIsCheck.isChecked();
    }

    @Override
    public int provideLayoutId() {
        return R.layout.dialog_exit;
    }
}
