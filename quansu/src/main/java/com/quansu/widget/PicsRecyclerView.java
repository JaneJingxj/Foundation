package com.quansu.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.LayoutAnimationController;

import com.quansu.common.mvp.BaseView;
import com.quansu.ui.adapter.PicsAdapter;
import com.quansu.ui.mvp.model.Pic;
import com.quansu.utils.UiUtils;
import com.ysnows.quansu.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xianguangjin on 16/6/1.
 * 添加图片的RecyclerView
 */

public class PicsRecyclerView extends RecyclerView {


    public String BASE_URL = "";
    private BaseView view;

    public int needUploadImgCount = 0;
    public ArrayList<String> uploadedImgList = new ArrayList<>();
    ArrayList<Pic> pics = new ArrayList<>();
    private PicsAdapter picsAdapter;
    private LayoutAnimationController mLac;

    public PicsRecyclerView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public PicsRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public PicsRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PicsRecyclerView);
        boolean isNeedDownLoad = typedArray.getBoolean(R.styleable.PicsRecyclerView_isNeedDownload, true);


        pics.add(pics.size(), new Pic("", Pic.TO_ADD));
        picsAdapter = new PicsAdapter(getContext(), pics, isNeedDownLoad, this);
        setAdapter(picsAdapter);
//        this.setItemAnimator(new ScaleInBottomAnimator());
        typedArray.recycle();
    }


    public void setPics(ArrayList<Pic> pics) {

        if (picsAdapter.getData().size() >= 1) {
            picsAdapter.getData().remove(picsAdapter.getData().size() - 1);
            pics.add(pics.size(), new Pic("", Pic.TO_ADD));
        }

        picsAdapter.changeData(pics);

        int spanCount = ((GridLayoutManager) getLayoutManager()).getSpanCount();
        Log.d("PicsRecyclerView", "spanCount:" + spanCount);


        int height = (UiUtils.getScreenWidthPixels(getContext()) - spanCount * UiUtils.dp2Px(getContext(), 3)) / spanCount;
        Log.d("PicsRecyclerView", "height:" + height);
        int heightDp = UiUtils.px2dip(getContext(), height);

        picsAdapter.setItemHeight(height);
        UiUtils.setRVHeith(getContext(), picsAdapter.getData().size(), spanCount, heightDp + UiUtils.dp2Px(getContext(), 3), this);

    }

    public void addPic(Pic pic) {
        ArrayList<Pic> pics = new ArrayList<>();
        pics.add(pic);
        setPics(pics);
    }

    public void setView(BaseView view) {
        this.view = view;
    }


    /**
     * 数据
     *
     * @return
     */
    public ArrayList<Pic> getPics() {
        return picsAdapter.getData();
    }


    public ArrayList<String> getPaths() {
        ArrayList<String> strings = new ArrayList<>();
        for (Pic pic : getPics()) {
            if (!TextUtils.isEmpty(pic.url) && !pic.url.startsWith("http://")) {
                strings.add(pic.url);
            }
        }
        return strings;
    }

    /**
     * 添加单张已上传的图片
     *
     * @param url
     */
    public void addUploadedImg(String url) {
        uploadedImgList.add(url);
        addPic(new Pic(url, Pic.NORMAL));
    }

    /**
     * 添加已上传的图片数字
     *
     * @param urls
     */
    public void addUploadedImgs(ArrayList<String> urls) {
        uploadedImgList.addAll(urls);
        for (String url : urls) {
            addPic(new Pic(BASE_URL + url, Pic.NORMAL));
        }
    }

    /**
     * 添加已上传的图片数字
     *
     * @param urls
     */
    public void addUploadedImgs(ArrayList<String> urls, int mode) {
        uploadedImgList.addAll(urls);
        for (String url : urls) {
            addPic(new Pic(BASE_URL + url, Pic.NORMAL, mode));
        }
    }

    public void addPic(String url) {
        Pic pic = new Pic(url, Pic.NORMAL);
        addPic(pic);
    }

    public void addPics(List<String> pathList) {
        ArrayList<Pic> pics = new ArrayList<>();
        for (String s : pathList) {
            Pic pic = new Pic(s, Pic.NORMAL);
            pics.add(pic);
        }

        setPics(pics);

    }

    public void clear() {
        picsAdapter.clear();
    }

    public PicsAdapter getPicsAdapter() {
        return picsAdapter;
    }
}


