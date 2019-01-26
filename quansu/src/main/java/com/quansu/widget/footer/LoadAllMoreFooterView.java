package com.quansu.widget.footer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.ysnows.quansu.R;

//注意：一次性加载完全部数据，如果列表页是数据做了缓存一次性加载完毕，请用这个
public class LoadAllMoreFooterView extends FrameLayout {

    private LoadAllMoreFooterView.Status mStatus;

    private View mLoadingView;

    private View mErrorView;

    private View mTheEndView;

    private LoadAllMoreFooterView.OnRetryListener mOnRetryListener;

    public LoadAllMoreFooterView(Context context) {
        this(context, null);
    }

    public LoadAllMoreFooterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadAllMoreFooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_irecyclerview_load_more_footer_view, this, true);

        mLoadingView = findViewById(R.id.loadingView);
        mErrorView = findViewById(R.id.errorView);
        mTheEndView = findViewById(R.id.theEndView);

        mErrorView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnRetryListener != null) {
                    mOnRetryListener.onRetry(LoadAllMoreFooterView.this);
                }
            }
        });

        setStatus(LoadAllMoreFooterView.Status.THE_END);//默认加载完成
    }

    public void setOnRetryListener(LoadAllMoreFooterView.OnRetryListener listener) {
        this.mOnRetryListener = listener;
    }

    public LoadAllMoreFooterView.Status getStatus() {
        return mStatus;
    }

    public void setStatus(LoadAllMoreFooterView.Status status) {
        this.mStatus = status;
        change();
    }

    public boolean canLoadMore() {
        return mStatus == LoadAllMoreFooterView.Status.GONE || mStatus == LoadAllMoreFooterView.Status.ERROR;
    }

    private void change() {
        switch (mStatus) {

            case LOADING:
                mLoadingView.setVisibility(VISIBLE);
                mErrorView.setVisibility(GONE);
                mTheEndView.setVisibility(GONE);
                break;
            case GONE:
                mLoadingView.setVisibility(VISIBLE);
                mErrorView.setVisibility(GONE);
                mTheEndView.setVisibility(GONE);
                break;
            case ERROR:
                mLoadingView.setVisibility(GONE);
                mErrorView.setVisibility(VISIBLE);
                mTheEndView.setVisibility(GONE);
                break;
            case THE_END:
                mLoadingView.setVisibility(GONE);
                mErrorView.setVisibility(GONE);
                mTheEndView.setVisibility(VISIBLE);
                break;
        }
    }

    public enum Status {
        GONE, LOADING, ERROR, THE_END
    }

    public interface OnRetryListener {
        void onRetry(LoadAllMoreFooterView view);
    }


    public View getmTheEndView() {
        return mTheEndView;
    }
}
