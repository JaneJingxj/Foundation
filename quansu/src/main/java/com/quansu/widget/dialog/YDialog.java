package com.quansu.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.quansu.common.mvp.BasePresenter;
import com.quansu.common.mvp.BaseView;
import com.ysnows.quansu.R;


/**
 * Created by xianguangjin on 16/5/18.
 */
public abstract class YDialog implements DialogInter {


    public Context _context;
    private int style = BOTTOM;
    public Dialog _Dialog;
    public View _Layout;

    public BaseView view;

    public final static int BOTTOM = 1;
    public final static int CENTER = 2;
    public final static int TOP = 3;
    private BasePresenter inter;

    public YDialog(Context context) {
        this._context = context;

        init();
    }

    public YDialog(Context context, int style) {
        this._context = context;
        this.style = style;
        init();
    }

    private void init() {
        if (_context instanceof BaseView) {
            this.view = (BaseView) _context;
        }
        LayoutInflater layoutInflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        _Layout = layoutInflater.inflate(provideLayoutId(), null);


        _Dialog = new Dialog(_context, getStypeRes());
        _Dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        _Dialog.setContentView(_Layout);

        Window win = _Dialog.getWindow();
        win.getDecorView().setPadding(getPaddingLeft(), getPaddingTop(), getPaddingLeft(), getPaddingBotoom());
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = getGravity();
        lp.windowAnimations = getAnimation();
        win.setAttributes(lp);
        _Dialog.setCanceledOnTouchOutside(false);

    }

    protected int getPaddingBotoom() {
        return 0;
    }

    protected int getPaddingTop() {
        return 0;
    }

    /**
     * 获取左右边距
     *
     * @return
     */
    protected int getPaddingLeft() {
        return 20;
    }

    /**
     * 初始化View,需要在构造函数里调用
     *
     * @param view
     */
    protected abstract void initView(View view);


    /**
     * 位置
     *
     * @return
     */
    private int getGravity() {
        switch (style) {
            case BOTTOM:
                return Gravity.BOTTOM;
            case CENTER:
                return Gravity.CENTER;
            case TOP:
                return Gravity.TOP;
        }
        return Gravity.BOTTOM;
    }

    /**
     * 获取动画
     *
     * @return
     */
    private int getAnimation() {
        switch (style) {
            case BOTTOM:
                return R.style.dialogAnim;
            case CENTER:
                return R.style.dialogAnimCenter;
            case TOP:
                return R.style.dialogAnimTop;
        }

        return R.style.dialogAnim;
    }

    /**
     * 获取类型
     *
     * @return
     */
    private int getStypeRes() {

        switch (style) {
            case BOTTOM:
                return R.style.dialog;
            case CENTER:
                return R.style.dialog_progress;
            case TOP:
                return R.style.dialog_top;
        }

        return R.style.dialog;
    }

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
    public YDialog setView(BaseView view) {
        this.view = view;
        return this;
    }

    /**
     */
    public YDialog setInter(BasePresenter inter) {
        this.inter = inter;
        return this;
    }

}
