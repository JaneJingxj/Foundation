package com.quansu.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import com.quansu.common.mvp.BaseView;
import com.quansu.utils.DataCleanManager;
import com.quansu.utils.SPUtil;
import com.quansu.utils.Toasts;
import com.quansu.widget.baseview.BaseLinearLayout;
import com.ysnows.quansu.R;

/**
 * Created by xianguangjin on 16/6/1.
 * 自定义
 */

public class DataCleanView extends BaseLinearLayout {


    private BaseView view;
    private TextView tvClear;
    private TextView tvSize;

    public DataCleanView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public DataCleanView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);

    }

    public DataCleanView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);

    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        inflate(context, R.layout.widget_dataclean, this);

        tvClear = findViewById(R.id.tv_clear);
        tvSize = findViewById(R.id.tv_size);

        setSize();
        setOnClickListener(v -> {

            SPUtil spUtil = SPUtil.netInstance();
            SPUtil.putString("citys", "");
            SPUtil.putString("area", "");

            DataCleanManager.clearAllCache(context);
            setSize();
            Toasts.show(context, this, context.getString(R.string.clear_success));

//            Dialog.showRadioDialog(getContext(), "确定清除缓存吗?", new Dialog.DialogClickListener() {
//                @Override
//                public void confirm() {
//
//                }
//
//                @Override
//                public void cancel() {
//
//                }
//            });
        });
    }

    private void setSize() {

        try {
            String totalCacheSize = DataCleanManager.getTotalCacheSize(getContext());
            if (!TextUtils.isEmpty(totalCacheSize)) {
                tvSize.setText(totalCacheSize);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setView(BaseView view) {
        this.view = view;
    }

}
