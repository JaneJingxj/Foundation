package com.quansu.widget.temptyview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.quansu.widget.WaveView;
import com.quansu.widget.baseview.BaseImageView;
import com.quansu.widget.wave.WaveWaveView;
import com.ysnows.quansu.R;

import static android.text.TextUtils.isEmpty;

/**
 * Created by Barry on 16/5/28.
 */
public class TEmptyView extends FrameLayout {

    private static TViewUtil.EmptyViewBuilder mConfig = null;

    private float mTextSize;
    private int mTextColor;
    private String mEmptyText;
    private int mIconSrc;
    private OnClickListener mOnClickListener;
    private String actionText;

    private boolean mShowIcon = true;
    private boolean mShowText = true;
    private boolean mShowButton = false;

    private ImageView mImageView;//图片 imageview
    private WaveWaveView wavewave; // 图片动画
    private TextView mTextView;//提示信息 tv
    private TextView mTextDescView;//描述信息tv
    private Button mButton; //操作 btn

    private LinearLayout linearout1,linearout2;
    private Button buttonagain;
    private BaseImageView imgleft;
    private  TextView tvDaili;



    /**
     * 描述信息
     */
    private String mEmptyDescText;
    private WaveView waveView;

    public static void init(TViewUtil.EmptyViewBuilder defaultConfig) {
        TEmptyView.mConfig = defaultConfig;
    }

    public static boolean hasDefaultConfig() {
        return TEmptyView.mConfig != null;
    }

    public static TViewUtil.EmptyViewBuilder getConfig() {
        return mConfig;
    }

    public TEmptyView(Context context) {
        this(context, null);
    }

    public TEmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.simple_empty_view, this);
        setBackgroundColor(Color.WHITE);
        linearout1=findViewById(R.id.linearout1);
        linearout2=findViewById(R.id.linearout2);
        mTextView = findViewById(R.id.t_emptyTextView);
        mTextDescView = findViewById(R.id.t_emptyDescTextView);
        mImageView = findViewById(R.id.t_emptyImageIcon);
        mButton = findViewById(R.id.t_emptyButton);
        wavewave = findViewById(R.id.wavewave);
        buttonagain=findViewById(R.id.button_again);
        imgleft=findViewById(R.id.img_left);
        tvDaili=findViewById(R.id.tv_daili);


    }

    public void setShowIcon(boolean mShowIcon) {
        this.mShowIcon = mShowIcon;
        mImageView.setVisibility(mShowIcon ? VISIBLE : GONE);
        wavewave.setVisibility(mShowIcon ? VISIBLE : GONE);
    }

    public void setShowText(boolean showText) {
        this.mShowText = showText;
        mTextView.setVisibility(showText ? VISIBLE : GONE);
    }

    public void setShowButton(boolean showButton) {
        this.mShowButton = showButton;
        mButton.setVisibility(showButton ? VISIBLE : GONE);
    }

    public float getTextSize() {
        return mTextSize;
    }

    public void setTextSize(float mTextSize) {
        this.mTextSize = mTextSize;
        mTextView.setTextSize(mTextSize);
    }

    public int getTextColor() {
        return mTextColor;
    }

    public void setTextColor(int mTextColor) {
        this.mTextColor = mTextColor;
        mTextView.setTextColor(mTextColor);
    }

    public String getEmptyText() {
        return mEmptyText;
    }

    /**
     * 设置提示信息
     *
     * @param mEmptyText
     */
    public void setEmptyText(String mEmptyText) {
        if (!isEmpty(mEmptyText)) {
            mTextView.setVisibility(VISIBLE);
        } else {
            mTextView.setVisibility(GONE);
        }

        this.mEmptyText = mEmptyText;
        mTextView.setText(mEmptyText);
    }

    /**
     * 设置描述信息
     *
     * @param mEmptyDescText
     */
    public void setEmptyDescText(String mEmptyDescText) {
        if (!isEmpty(mEmptyDescText)) {
            mTextDescView.setVisibility(VISIBLE);
        } else {
            mTextDescView.setVisibility(GONE);
        }

        this.mEmptyDescText = mEmptyDescText;
        mTextDescView.setText(mEmptyDescText);
    }

    /**
     * 设置图片
     *
     * @param mIconSrc
     */
    public void setIcon(int mIconSrc) {
        ((Activity)getContext()).runOnUiThread(() -> {
            if (mIconSrc != 0) {
                mImageView.setVisibility(VISIBLE);
//            wavewave.setVisibility(VISIBLE);

            } else {
                mImageView.setVisibility(GONE);
//            wavewave.setVisibility(GONE);
            }
            this.mIconSrc = mIconSrc;
            mImageView.setImageResource(mIconSrc);
        });
    }


    /**
     * 设置图片
     *
     * @param drawable
     */
    public void setIcon(Drawable drawable) {
        ((Activity)getContext()).runOnUiThread(() -> {
            this.mIconSrc = 0;
            if (drawable != null) {
                mImageView.setVisibility(VISIBLE);

                if (drawable.equals(R.drawable.ic_new_loading_animation)) {
                    wavewave.setVisibility(VISIBLE);
                }

            } else {
                mImageView.setVisibility(GONE);
//            waveView.setVisibility(GONE);
            }

            mImageView.setImageDrawable(drawable);
        });
    }

    /**
     * 设置按钮监听
     *
     * @param onClickListener
     */
    public void setAction(OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
        mButton.setOnClickListener(onClickListener);
    }

    /**
     * 设置按钮文字
     *
     * @param actionText
     */
    public void setActionText(String actionText) {
        if (!isEmpty(actionText)) {
            mButton.setVisibility(VISIBLE);
        } else {
            mButton.setVisibility(GONE);
        }

        this.actionText = actionText;
        mButton.setText(actionText);
    }

    public WaveWaveView getWavewave() {
        return wavewave;
    }

    public void setWavewave(WaveWaveView wavewave) {
        this.wavewave = wavewave;
    }


    /**
     * 没网时候重新加载，界面统一，文字统一
     */
    public void setAgain(){
        linearout1.setVisibility(GONE);
        linearout2.setVisibility(VISIBLE);



    }

    /**
     * 第一次加载列表的时候没有数据
     */
    public void setFirstData(){
        linearout1.setVisibility(GONE);
        linearout2.setVisibility(VISIBLE);
        buttonagain.setVisibility(GONE);
        tvDaili.setText(R.string.no_content_information);
    }

    public void setAgainData(){
        linearout1.setVisibility(GONE);
        linearout2.setVisibility(VISIBLE);
        buttonagain.setVisibility(VISIBLE);
        tvDaili.setText(R.string.no_content_information);
    }

    public void setNoData(){
        linearout1.setVisibility(GONE);
        linearout2.setVisibility(VISIBLE);
        buttonagain.setVisibility(GONE);
        tvDaili.setText(R.string.no_content_information);
    }





    /**
     * 点击没网时候重新加载的按钮
     *
     * @param onClickListener
     */
    public void setAgainButton(OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
        buttonagain.setOnClickListener(onClickListener);
    }

    /**
     * 点击没网时候返回的按钮
     * @param onClickListener
     */
    public void setImgleft(OnClickListener onClickListener){
        this.mOnClickListener = onClickListener;
        imgleft.setOnClickListener(onClickListener);
    }



}
