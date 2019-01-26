package com.quansu.widget.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.quansu.utils.UiUtils;
import com.ysnows.quansu.R;

/**
 * Created by xianguangjin on 16/8/16.
 * <p>
 * 我的GitHub: https://github.com/ysnows
 * <p>
 * 加油,做一个真的汉子
 */

public class YEditDialog extends YDialog {
    private final String title;
    private final String defaultContent;
    private final String placeHolder;
    private final OnEditDialogClickListener ensureClickListener;


    private TextView tvTitle;
    private TextView btnCancel;
    private TextView btnEnsure;
    private EditText edtContent;


    public YEditDialog(Context context, int style, String title, String defaultContent, String placeHolder, OnEditDialogClickListener ensureClickListener) {
        super(context, style);
        this.title = title;
        this.defaultContent = defaultContent;
        this.placeHolder = placeHolder;
        this.ensureClickListener = ensureClickListener;
        initView(_Layout);

    }

    public YEditDialog(Context context, int style, int titleId, String defaultContent, String placeHolder, OnEditDialogClickListener ensureClickListener) {
        super(context, style);
        this.title = context.getString(titleId);
        this.defaultContent = defaultContent;
        this.placeHolder = placeHolder;
        this.ensureClickListener = ensureClickListener;
        initView(_Layout);
    }

    @Override
    protected void initView(View view) {

        tvTitle = view.findViewById(R.id.tv_title);
        btnCancel = view.findViewById(R.id.btn_cancel);
        btnEnsure = view.findViewById(R.id.btn_ensure);
        edtContent = view.findViewById(R.id.edt_content);


        if (!TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
        }
        if (!TextUtils.isEmpty(defaultContent)) {
            edtContent.setText(defaultContent);
            edtContent.setSelection(defaultContent.length());
        }
        if (!TextUtils.isEmpty(placeHolder)) {
            edtContent.setHint(placeHolder);
        }


        btnCancel.setOnClickListener(v -> dismiss());

        btnEnsure.setOnClickListener(v -> {
            dismiss();
            if (ensureClickListener != null) {
                ensureClickListener.onEditClick(edtContent, edtContent.getText().toString());
            }

        });
    }


    @Override
    protected int getPaddingLeft() {
        return UiUtils.dp2px(_context, 20);
    }

    @Override
    public int provideLayoutId() {

        return R.layout.dialog_edit;
    }

}
