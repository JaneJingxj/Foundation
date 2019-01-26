package com.quansu.common.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.quansu.common.inter.BaseAdapterInter;
import com.quansu.common.mvp.RLRVPresenter;
import com.quansu.common.mvp.RLRVView;
import com.quansu.ui.adapter.OnItemClickListener;
import com.quansu.utils.EmptyViewUtils;
import com.quansu.widget.footer.LoadAllMoreFooterView;
import com.quansu.widget.footer.LoadMoreFooterView;
import com.quansu.widget.irecyclerview.IRecyclerView;
import com.quansu.widget.irecyclerview.OnLoadMoreListener;
import com.quansu.widget.irecyclerview.OnRefreshListener;
import com.quansu.widget.temptyview.TEmptyView;
import com.umeng.analytics.MobclickAgent;
import com.ysnows.quansu.R;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by xianguangjin on 16/6/20.
 * <p>
 * 我的GitHub: https://github.com/ysnows
 * <p>
 * 加油,做一个真的汉子
 */

public abstract class BaseRVActivity<P extends RLRVPresenter> extends BaseActivity<P> implements OnRefreshListener, OnLoadMoreListener, RLRVView, OnItemClickListener {

    @Nullable
    public IRecyclerView iRecyclerView;


    protected LoadMoreFooterView loadMoreFooterView;
    protected BaseAdapterInter adapter;
    private SwipeRefreshLayout refresh_layout;

    protected LoadAllMoreFooterView loadMoreFooterViewTo;

    boolean isFrist = true;




    @Override
    protected void initThings(Bundle savedInstanceState) {

        iRecyclerView = getIrecyclerView();


        refresh_layout = getRefreshLayout();
        if (refresh_layout != null) {
            refresh_layout.setColorSchemeResources(R.color.refresh_colorPrimary, R.color.refresh_colorAccent, R.color.refresh_colorPrimaryDark);
            refresh_layout.setOnRefreshListener(() -> presenter.requestDataRefresh());
        }

        addHeader();
        addFooter();

        try {
            loadMoreFooterView = (LoadMoreFooterView) iRecyclerView.getLoadMoreFooterView();

        }catch (Exception e){
            loadMoreFooterViewTo= (LoadAllMoreFooterView) iRecyclerView.getLoadMoreFooterView();
        }




        adapter = getAdapter();

//        SlideInBottomAnimationAdapter slideInBottomAnimationAdapter = new SlideInBottomAnimationAdapter(adapter.getAdapter());

        iRecyclerView.setIAdapter(adapter.getAdapter());

        iRecyclerView.setOnRefreshListener(this);
        iRecyclerView.setOnLoadMoreListener(this);
        adapter.setmOnItemClickListener(this);

        MobclickAgent.setDebugMode(true);
    }


    public void addItemAnimator() {
//        iRecyclerView.setItemAnimator(new SlideInUpAnimator());
    }

    protected abstract IRecyclerView getIrecyclerView();

    protected abstract SwipeRefreshLayout getRefreshLayout();




    /**
     * 添加头部
     */
    protected void addHeader() {


    }

    /**
     * 添加头部
     */
    protected void addFooter() {


    }


    @Override
    public void onRefresh() {//保证两秒内只执行一次


//        presenter.requestDataRefresh();
//        if (loadMoreFooterView != null) {
//            loadMoreFooterView.setStatus(LoadMoreFooterView.Status.GONE);
//        }else if(loadMoreFooterViewTo!=null){
//            loadMoreFooterViewTo.setStatus(LoadAllMoreFooterView.Status.GONE);
//        }


            Timer tExit = null;
            if (!isFrist) {
            } else {
                tExit = new Timer();
                tExit.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if(this!=null) {
                            runOnUiThread(() -> {
                                isFrist = true;
                                refreshing(false);
                            });
                        }
                    }
                }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
                isFrist = false;
                setOnRefresh();
            }



    }


    public void  setOnRefresh() {
        presenter.requestDataRefresh();
        if (loadMoreFooterView != null) {
            loadMoreFooterView.setStatus(LoadMoreFooterView.Status.GONE);
        }else if(loadMoreFooterViewTo!=null){
            loadMoreFooterViewTo.setStatus(LoadAllMoreFooterView.Status.GONE);
        }
    }


    @Override
    public void onLoadMore() {
        if (loadMoreFooterView != null) {
            if (loadMoreFooterView.canLoadMore() && adapter.getItemCount() > 0) {
                loading(true);
                presenter.loadMore();
            }
        }else if(loadMoreFooterViewTo!=null){

            if (loadMoreFooterViewTo.canLoadMore() && adapter.getItemCount() > 0) {
                loading(true);
                presenter.loadMore();
            }
          }
    }

    @Override
    public void bindData(Object o, boolean isHasMore) {
        ArrayList datas = (ArrayList) o;
        if (presenter.page == 1) {
            adapter.setData(datas);
        } else {
            adapter.addData((ArrayList) o);
        }
        hasMore(isHasMore);
    }

    @Override
    public void refreshing(boolean refreshing) {
        if (iRecyclerView != null) {
            iRecyclerView.post(() -> iRecyclerView.setRefreshing(refreshing));
        }

        if (refresh_layout != null) {
            refresh_layout.post(() -> refresh_layout.setRefreshing(refreshing));
        }
    }

    @Override
    public void loading(boolean loading) {
        if (loadMoreFooterView != null) {
            loadMoreFooterView.setStatus(loading ? LoadMoreFooterView.Status.LOADING : LoadMoreFooterView.Status.GONE);
        }else if(loadMoreFooterViewTo!=null){

            loadMoreFooterViewTo.setStatus(loading ? LoadAllMoreFooterView.Status.LOADING : LoadAllMoreFooterView.Status.GONE);
            }
    }


    @Override
    public void hasMore(boolean isHasMore) {
        if (loadMoreFooterView != null) {
            loadMoreFooterView.setStatus(isHasMore ? LoadMoreFooterView.Status.GONE : LoadMoreFooterView.Status.THE_END);
        }else if(loadMoreFooterViewTo!=null){

            loadMoreFooterViewTo.setStatus(isHasMore ? LoadAllMoreFooterView.Status.GONE : LoadAllMoreFooterView.Status.THE_END);

        }


    }


    @Override
    public void onError(String s) {
        adapter.clear();
        bindEmptyView();
    }


    public void bindEmptyView() {
        final TEmptyView emptyView = EmptyViewUtils.genSimpleEmptyView(iRecyclerView);
        emptyView.setShowButton(false);
        emptyView.setAction(v -> {
            iRecyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
            refreshing(true);
            presenter.requestDataRefresh();
        });

        if (adapter != null) {
            RecyclerView.AdapterDataObserver observer = new RecyclerView.AdapterDataObserver() {
                @Override
                public void onChanged() {
                    super.onChanged();
                    if (adapter.getData().size() > 0) {
                        iRecyclerView.setVisibility(View.VISIBLE);
                        emptyView.setVisibility(View.GONE);
                    } else {
                        iRecyclerView.setVisibility(View.GONE);
                        emptyView.setVisibility(View.VISIBLE);
                    }
                }
            };
            adapter.getAdapter().registerAdapterDataObserver(observer);
            observer.onChanged();
        } else {
            throw new RuntimeException("This RecyclerView has no adapter, you must call setAdapter first!");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.enableEncrypt(true);
        MobclickAgent.onResume(getContext());


    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(getContext());
    }


    @Override
    public P getRP() {
        return presenter;
    }


//    @Override
//    public void finishActivity() {
//        this.finish();
//        overridePendingTransition(0, R.anim.acitivity_hide_out_amination);
//    }
//
//    @Override
//    public void onBackPressed() {
//        finishActivity();
//
//    }


}
