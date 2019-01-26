package com.quansu.widget;


import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;


import com.ysnows.quansu.R;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


/**
 * 就是自定义的Dialog,不可back或点击外部销
 */
public class Dialog {
    public final static int SELECT_DIALOG = 1;
    public final static int RADIO_DIALOG = 2;
    private static android.app.Dialog mDialog;
    private static Context contexts;


    /**
     * 创建个内容多选对话框
     *
     * @param context
     * @param title 标题
     * @param items 数组
     * @param dialogItemClickListener 监听点击的内容结
     *
     * @return
     */
    public static android.app.Dialog showListDialog(Context context, String title, ArrayList<String> items, final DialogItemClickListener dialogItemClickListener) {


        contexts=context;
        return ShowDialog(context, title, items, dialogItemClickListener);
    }


    /**
     * 展示正在加载对话框
     *
     * @param context
     *
     * @return
     */
    public static android.app.Dialog showProgressingDialog(Context context) {

        contexts=context;
        if (context != null) {
            return showProgressingDialog(context, context.getString(R.string.please_wait_for_a_while));
        }

        return null;

    }


    public static android.app.Dialog showProgressingDialog(Context context, int ResId) {

        contexts=context;
        if (context != null) {
            return showProgressingDialog(context, context.getString(ResId));
        }

        return null;
    }


    /**
     * 展示正在加载对话框
     *
     * @param context
     *
     * @return
     */
    public static android.app.Dialog showProgressingDialog(Context context, String tip) {
        if (context == null) {
            return null;
        }
        contexts=context;

        dismissProgressDialog();
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.dialog_progress, null);

        mDialog = new android.app.Dialog(context, R.style.dialog_progress);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(layout);

        Window win = mDialog.getWindow();
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        lp.windowAnimations = R.style.dialogAnimCenter;
        win.setAttributes(lp);

        TextView tvTip = (TextView) layout.findViewById(R.id.tv_tip);
        if (!TextUtils.isEmpty(tip)) {
            tvTip.setVisibility(View.VISIBLE);
            tvTip.setText(tip);
        } else {
            tvTip.setVisibility(View.GONE);
        }
        mDialog.setCanceledOnTouchOutside(true);
        if (!mDialog.isShowing())

        {
            mDialog.show();
        }

        return mDialog;
    }

    public static void showProgressDialogDelay(Context context, String tip, long millis, final DialogDismissListener dialogDismissListener) {

        contexts=context;
        final android.app.Dialog dialog = showProgressingDialog(context, tip);

        final Handler mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
                switch (message.what) {
                    case 10:
                        if (dialog != null) {
                            dialog.dismiss();
                        }

                        if (dialogDismissListener != null) {
                            dialogDismissListener.onDismiss();
                        }

                        break;
                }
            }
        };


        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(10);
            }
        }, millis);

    }

    /**
     * 隐藏ProgressDialog
     */
    public static void dismissProgressDialog() {
        if (mDialog != null&&contexts!=null) {
            mDialog.dismiss();
            mDialog = null;
        }

        if(mDialog!=null){//说明所承载的activity窗口已经被销毁
            mDialog = null;
        }
    }

    /**
     * 创建个单选对话框
     *
     * @param context
     * @param toast 提示消息
     * @param dialogClickListener 点击监听
     *
     * @return
     */
    public static android.app.Dialog showRadioDialog(Context context, String toast, final DialogClickListener dialogClickListener) {
        contexts=context;
        return ShowDialog(context, context.getString(R.string.prompt_information), toast, dialogClickListener, RADIO_DIALOG);
    }

    /**
     * 创建择对话
     *
     * @param context
     * @param toast 提示消息
     * @param dialogClickListener 点击监听
     *
     * @return
     */
    public static android.app.Dialog showSelectDialog(Context context, String toast, final DialogClickListener dialogClickListener) {
        contexts=context;
        return ShowDialog(context, "", toast, dialogClickListener, SELECT_DIALOG);
    }

    /**
     * 创建择对话
     *
     * @param context
     * @param title 提示标题
     * @param toast 提示消息
     * @param dialogClickListener 点击监听
     *
     * @return
     */
    public static android.app.Dialog showSelectDialog(Context context, String title, String toast, final DialogClickListener dialogClickListener) {
        contexts=context;
        return ShowDialog(context, title, toast, dialogClickListener, SELECT_DIALOG);
    }

    private static android.app.Dialog ShowDialog(Context context, String title, String toast, final DialogClickListener dialogClickListener, int DialogType) {
        if (context == null) {
            return null;
        }

        contexts=context;
        final android.app.Dialog dialog = new android.app.Dialog(context, R.style.DialogStyle);
        dialog.setCancelable(false);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog, null);
        dialog.setContentView(view);
        ((TextView) view.findViewById(R.id.point)).setText(title);
        ((TextView) view.findViewById(R.id.toast)).setText(toast);
        if (DialogType == RADIO_DIALOG) {

            view.findViewById(R.id.divider).setVisibility(View.VISIBLE);


        } else {
            view.findViewById(R.id.ok).setVisibility(View.VISIBLE);
            view.findViewById(R.id.divider).setVisibility(View.VISIBLE);
        }

        view.findViewById(R.id.cancel).setOnClickListener(arg0 -> {
            dialog.dismiss();
            new Handler().postDelayed(() -> {
                if (dialogClickListener != null) {
                    dialogClickListener.cancel();
                }
            }, 200);
        });
        view.findViewById(R.id.confirm).setOnClickListener(arg0 -> {
            dialog.dismiss();
            new Handler().postDelayed(() -> {
                if (dialogClickListener != null) {
                    dialogClickListener.confirm();
                }
            }, 200);
        });
        view.findViewById(R.id.ok).setOnClickListener(arg0 -> {
            dialog.dismiss();
            new Handler().postDelayed(() -> {
                if (dialogClickListener != null) {
                    dialogClickListener.confirm();
                }
            }, 200);
        });
        Window mWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = mWindow.getAttributes();
        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {//横屏
            lp.width = getScreenHeight(context) / 10 * 8;
        } else {
            lp.width = getScreenWidth(context) / 10 * 8;
        }
        mWindow.setAttributes(lp);
        dialog.show();

        return dialog;
    }

    private static android.app.Dialog ShowDialog(Context context, String title, ArrayList<String> items, final DialogItemClickListener dialogClickListener) {
        contexts=context;

        final android.app.Dialog dialog = new android.app.Dialog(context, R.style.DialogStyle);
        dialog.setCancelable(true);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_radio, null);
        dialog.setContentView(view);
        ((TextView) view.findViewById(R.id.title)).setText(title);
        //根据items动
        LinearLayout parent = (LinearLayout) view.findViewById(R.id.dialogLayout);

        parent.removeAllViews();
        int length = items.size();
        for (int i = 0; i < items.size(); i++) {
            LayoutParams params1 = new LayoutParams(-1, -2);
            params1.rightMargin = 1;
            final TextView tv = new TextView(context);
            tv.setLayoutParams(params1);
            tv.setTextSize(18);
            tv.setText(items.get(i));
            tv.setTextColor(Color.parseColor("#0048fc"));
//			int pad=context.getResources().getDimensionPixelSize(R.dimen.distance_10);
            int pad = 10;
            tv.setPadding(pad, pad, pad, pad);
            tv.setSingleLine(true);
            tv.setGravity(Gravity.CENTER);
            if (i != length - 1)
                tv.setBackgroundResource(R.drawable.menudialog_center_selector);
            else
                tv.setBackgroundResource(R.drawable.menudialog_bottom2_selector);

            int position = i;
            tv.setOnClickListener(arg0 -> {
                dialog.dismiss();
                dialogClickListener.confirm(tv.getText().toString(), position);
            });
            parent.addView(tv);
            if (i != length - 1) {
                TextView divider = new TextView(context);
                LayoutParams params = new LayoutParams(-1, 1);
                divider.setLayoutParams(params);
                divider.setBackgroundResource(android.R.color.darker_gray);
                parent.addView(divider);
            }
        }
        view.findViewById(R.id.ok).setOnClickListener(arg0 -> dialog.dismiss());
        Window mWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = mWindow.getAttributes();
        lp.width = getScreenWidth(context);
        mWindow.setGravity(Gravity.BOTTOM);
        //添加动画
        mWindow.setWindowAnimations(R.style.dialogAnim);
        mWindow.setAttributes(lp);
        dialog.show();
        return dialog;
    }

    /**
     * 获取屏幕分辨率宽
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 获取屏幕分辨率高
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    public interface DialogClickListener {
        public abstract void confirm();

        public abstract void cancel();
    }

    public interface DialogDismissListener {
        public abstract void onDismiss();
    }

    public interface DialogItemClickListener {
        public abstract void confirm(String result, int position);
    }





}