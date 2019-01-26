package com.quansu.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;

import com.quansu.common.mvp.BaseView;

/**
 * Created by xianguangjin on 16/6/1.
 * 自定义
 */

public class ScrollToTopView extends android.support.v7.widget.AppCompatImageView {


    private BaseView view;


    public ScrollToTopView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public ScrollToTopView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);

    }

    public ScrollToTopView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);

    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {


    }

    public void attachToRecyclerView(RecyclerView recyclerView) {
        setOnClickListener(v -> recyclerView.smoothScrollToPosition(0));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


                if (dy > 0) {
                    setVisibility(View.VISIBLE);
                } else {
                    LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    if (layoutManager.findFirstVisibleItemPosition() <= 2) {
                        setVisibility(View.GONE);
                    }

                }

            }
        });
    }


    public void attachToListView(ListView listView) {
        setOnClickListener(v -> listView.smoothScrollToPosition(0));

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                if (!listView.canScrollVertically(-1)) {
                    setVisibility(View.GONE);
                }

                if (firstVisibleItem >= 1) {
                    setVisibility(View.VISIBLE);
                }

            }
        });


    }


    public void setView(BaseView view) {
        this.view = view;
    }

}
