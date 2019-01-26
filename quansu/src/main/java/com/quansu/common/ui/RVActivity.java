package com.quansu.common.ui;

import android.widget.FrameLayout;

import com.quansu.common.mvp.RLRVPresenter;
import com.quansu.common.mvp.RLRVView;
import com.quansu.ui.adapter.OnItemClickListener;
import com.quansu.widget.irecyclerview.IRecyclerView;
import com.quansu.widget.irecyclerview.OnLoadMoreListener;
import com.quansu.widget.irecyclerview.OnRefreshListener;
import com.ysnows.quansu.R;

/**
 * Created by xianguangjin on 16/6/20.
 * <p>
 * 我的GitHub: https://github.com/ysnows
 * <p>
 * 加油,做一个真的汉子
 */

public abstract class RVActivity<P extends RLRVPresenter> extends BaseRVActivity<P> implements OnRefreshListener, OnLoadMoreListener, RLRVView, OnItemClickListener {


    @Override
    protected IRecyclerView getIrecyclerView() {
        return (IRecyclerView) findViewById(R.id.iRecyclerView);
    }

    @Override
    public void goToLoginActivity() {
    }

    @Override
    protected FrameLayout getBody() {
        return (FrameLayout) findViewById(R.id.lay_body);
    }

    @Override
    public boolean getisUseAutoLayout() {
        return true;
    }

}
