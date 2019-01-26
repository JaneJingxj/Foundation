package com.quansu.widget;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.quansu.utils.UiUtils;
import com.quansu.widget.shapview.RectButton;
import com.ysnows.quansu.R;

public class TextField extends android.support.v7.widget.AppCompatEditText {
    Drawable drawable = null;
    Context context;
    boolean mClickable;
    boolean nClickable;

    String nameUser = "";

    RectButton loginRectButtonmm;
    RectButton loginRectButtonname;
    float heightView= UiUtils.dp2px(getContext(),50);
    public TextField(Context context) {
        super(context);
        init(context);
    }

    public TextField(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttributeSet(context, attrs);
        init(context);
    }

    public TextField(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttributeSet(context, attrs);
        init(context);
    }

//	@SuppressLint("NewApi")
//	public TextField(Context context, AttributeSet attrs, int defStyleAttr,
//			int defStyleRes) {
//		super(context, attrs, defStyleAttr, defStyleRes);
//		initAttributeSet(context, attrs);
//		netInstance(context);
//	}

    /**
     * 配置属性初始化
     *
     * @param context
     * @param attrs
     */
    private void initAttributeSet(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextField);
        isDelete = typedArray.getBoolean(R.styleable.TextField_isDelete, true);
        typedArray.recycle();
    }

    /**
     * 初始化
     *
     * @param context
     */
    @SuppressWarnings("deprecation")
    private void init(Context context) {
        this.context = context;
        this.setOnFocusChangeListener((view, isFocus) -> {
            if (isFocus) {
                setVisibleDelete(getText().toString().length() > 0);
            } else {
                setVisibleDelete(false);
            }
        });

        this.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
                Log.e("onTextChanged", arg1 + "~" + arg2 + "~" + arg3 + "!" + arg0.toString().length());

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub
                Log.e("beforeTextChanged", arg1 + "~" + arg2 + "!" + arg0.toString().length());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.e("TextChanged", nameUser + "~" + getText().toString().length());
                if (loginRectButtonmm != null) {
                    if (nameUser.equals("mm") && getText().toString().length() > 5) {
//                        setUserMsg(true);
                        loginRectButtonmm.setClickable(true);
                        loginRectButtonmm.setBackgroundResource(R.color.text_color_pink);
                    } else {
//                        setUserMsg(false);
                        loginRectButtonmm.setClickable(false);
                        loginRectButtonmm.setBackgroundResource(R.color.text_color_pink_unclick);
                    }
                }
                if (loginRectButtonname != null) {
                    if (nameUser.equals("name") && getText().toString().length() > 0) {
//                        setUserMsg(true);
                        loginRectButtonname.setClickable(true);
                        loginRectButtonname.setBackgroundResource(R.color.text_color_pink);
                    } else {
//                        setUserMsg(false);
                        loginRectButtonname.setClickable(false);
                        loginRectButtonname.setBackgroundResource(R.color.text_color_pink_unclick);
                    }
                }
                setVisibleDelete(getText().toString().length() > 0);
            }
        });
    }

    private void setUserMsg(boolean click) {
//        if (loginRectButton!=null){
//            if (click){
//                loginRectButton.setClickable(true);
//                loginRectButton.setBackgroundResource(R.color.text_color_pink);
//            }else {
//                loginRectButton.setClickable(false);
//                loginRectButton.setBackgroundResource(R.color.text_color_pink_unclick);
//            }
//        }
    }

    private void setnCilckable(boolean clickable) {
        nClickable = clickable;
    }

    private void setCilckable(boolean clickable) {
        mClickable = clickable;
    }

    public void setLoginloginRectButton(RectButton mRectButton, String name) {
        if (name.equals("mm")) {
            loginRectButtonmm = mRectButton;
        } else if (name.equals("name")) {
            loginRectButtonname = mRectButton;
        }

        nameUser = name;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                boolean isClean = (event.getX() > (getWidth() - getTotalPaddingRight())) &&
                        (event.getX() < (getWidth() - getPaddingRight()));
                if (isClean) {
                    setText("");
                }
                break;

            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void finalize() throws Throwable {
        // TODO Auto-generated method stub
        super.finalize();
    }

    private boolean isDelete = true;

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean isDelete) {
        this.isDelete = isDelete;
    }

    private void setVisibleDelete(boolean isShow) {
        if (isShow && isDelete) {
            setCompoundDrawablesWithIntrinsicBounds(null, null, zoomDrawable(), null);
        } else {
            setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        }
    }

    @SuppressWarnings("deprecation")
    private Drawable zoomDrawable() {
        drawable = context.getResources().getDrawable(R.drawable.view_textfield_delete);
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        android.util.Log.e("sdfs", width + "s");
        android.util.Log.e("sdfs", height + "sd");
        Bitmap oldbmp = drawableToBitmap(); // drawable 转换成 bitmap
        Matrix matrix = new Matrix(); // 创建操作图片用的 Matrix 对象
//		float scaleWidth = ((float) getWidth() / width); // 计算缩放比例
        float scaleHeight = (heightView / height);
        matrix.postScale(scaleHeight, scaleHeight); // 设置缩放比例
        if (width < 0) {
            return drawable;
        }

        Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height,
                matrix, true); // 建立新的 bitmap ，其内容是对原 bitmap 的缩放后的图
        android.util.Log.e("sdfs2", width + "s");
        android.util.Log.e("sdfs2", height + "sd");
        return new BitmapDrawable(newbmp); // 把 bitmap 转换成 drawable 并返回
//        return drawable;
    }

    private Bitmap drawableToBitmap() // drawable 转换成 bitmap
    {
        int width = drawable.getIntrinsicWidth(); // 取 drawable 的长宽
        int height = drawable.getIntrinsicHeight();
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565; // 取 drawable 的颜色格式
        Bitmap bitmap = Bitmap.createBitmap(width, height, config); // 建立对应
        // bitmap
        Canvas canvas = new Canvas(bitmap); // 建立对应 bitmap 的画布
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas); // 把 drawable 内容画到画布中
        return bitmap;
    }
}
