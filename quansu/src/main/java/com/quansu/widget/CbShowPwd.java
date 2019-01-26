package com.quansu.widget;

import android.content.Context;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.EditText;

import com.quansu.common.mvp.BaseView;

/**
 * Created by xianguangjin on 16/6/1.
 * 自定义
 */

public class CbShowPwd extends android.support.v7.widget.AppCompatCheckBox {


    private BaseView view;


    public CbShowPwd(Context context) {
        super(context);
        init(context, null, 0);
    }

    public CbShowPwd(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);

    }

    public CbShowPwd(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);

    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {


    }

    /**
     * 设置密码
     *
     * @param _LoginPwd
     */
    public void setEditText(EditText _LoginPwd) {
        setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                _LoginPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                _LoginPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
            _LoginPwd.postInvalidate();
            //切换后将EditText光标置于末尾
            CharSequence charSequence = _LoginPwd.getText();
            if (charSequence instanceof Spannable) {
                Spannable spanText = (Spannable) charSequence;
                Selection.setSelection(spanText, charSequence.length());
            }
        });

    }

    public void setView(BaseView view) {
        this.view = view;
    }

}
