package com.quansu.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by xianguangjin on 15/12/30.
 */
public class LinearItemDecoration extends RecyclerView.ItemDecoration {

    public int space;
    private int position = 0;

    public LinearItemDecoration(int space) {
        super();
        this.space = space;
    }

    public LinearItemDecoration(int space, int position) {
        super();
        this.space = space;
        this.position = position;
    }


    /**
     * Retrieve any offsets for the given item. Each field of <code>outRect</code> specifies
     * the number of pixels that the item view should be inset by, similar to padding or margin.
     * The default implementation sets the bounds of outRect to 0 and returns.
     * <p>
     * <p>
     * If this ItemDecoration does not affect the positioning of item views, it should set
     * all four fields of <code>outRect</code> (left, top, right, bottom) to zero
     * before returning.
     * <p>
     * <p>
     * If you need to access Adapter for additional data, you can call
     * {@link RecyclerView#getChildAdapterPosition(View)} to netInstance the adapter position of the
     * View.
     *
     * @param outRect Rect to receive the output.
     * @param view The child view to decorate
     * @param parent RecyclerView this ItemDecoration is decorating
     * @param state The current state of RecyclerView.
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view); // item position

        if (position <= this.position) {
            return;
        }

        outRect.bottom = space;
    }
}
