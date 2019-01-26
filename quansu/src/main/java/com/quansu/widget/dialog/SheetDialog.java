package com.quansu.widget.dialog;

import android.content.Context;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.quansu.common.mvp.BasePresenter;
import com.quansu.common.mvp.BaseView;


/**
 * Created by xianguangjin on 16/5/18.
 */
public abstract class SheetDialog implements DialogInter {


    public Context _context;
    public View _Layout;

    public BaseView view;

    public final static int BOTTOM = 1;
    public final static int CENTER = 2;
    public final static int TOP = 3;
    private BasePresenter inter;
    private BottomSheetDialog _Dialog;

    public SheetDialog(Context context) {
        this._context = context;
        init();
    }

    private void init() {
        if (_context instanceof BaseView) {
            this.view = (BaseView) _context;
        }
        LayoutInflater layoutInflater = (LayoutInflater) _context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        _Layout = layoutInflater.inflate(provideLayoutId(), null);
        _Dialog = new BottomSheetDialog(_context);
        _Dialog.setContentView(_Layout);

    }

    /**
     * 初始化View,需要在构造函数里调用
     *
     * @param view
     */
    protected abstract void initView(View view);


    /**
     * 提供布局
     *
     * @return
     */
    public abstract int provideLayoutId();


    /**
     * 展示
     */
    @Override
    public DialogInter show() {
        _Dialog.show();
        return this;
    }

    /**
     * 消失
     */
    @Override
    public void dismiss() {
        if (_Dialog != null) {
            _Dialog.dismiss();
        }
    }

    /**
     * @param view
     */
    public SheetDialog setView(BaseView view) {
        this.view = view;
        return this;
    }


    public SheetDialog setInter(BasePresenter inter) {
        this.inter = inter;
        return this;
    }

}
