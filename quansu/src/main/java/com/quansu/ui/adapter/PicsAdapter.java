package com.quansu.ui.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.quansu.common.ui.BaseAdapter;
import com.quansu.cons.Constants;
import com.quansu.ui.mvp.model.Pic;
import com.quansu.utils.Msg;
import com.quansu.utils.UiSwitch;
import com.quansu.utils.UiUtils;
import com.quansu.utils.glide.GlideUtils;
import com.quansu.widget.LVCircularCD;
import com.quansu.widget.PicsRecyclerView;
import com.quansu.widget.dialog.AvatarUploadDialog;
import com.quansu.widget.irecyclerview.IViewHolder;
import com.ysnows.quansu.R;

import java.util.ArrayList;

import static com.quansu.utils.RxBus.getDefault;

/**
 * Created by xianguangjin on 16/6/2.
 */

public class PicsAdapter extends BaseAdapter {

    private PicsRecyclerView picsRecyclerView;
    private boolean isNeedDownLoad = true;
    private String BASE_IMG = "";
    private int itemHeight = 0;
    private Pic seletedItem;
    private int selectedPos = 0;


    public PicsAdapter(Context context, ArrayList<Pic> data, boolean isNeedDownLoad, PicsRecyclerView picsRecyclerView) {
        super(context, data);
        this.isNeedDownLoad = isNeedDownLoad;
        this.picsRecyclerView = picsRecyclerView;
        itemHeight = UiUtils.dp2px(context, 90);
        Log.d("PicsAdapter", "itemHeight:" + itemHeight);
    }

    @Override
    public IViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.pics, null);
        VHolder vHolder = new VHolder(itemView);
        return vHolder;
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(IViewHolder holder, int position) {
        if (holder != null) {
            VHolder vHolder = (VHolder) holder;
            Pic item = (Pic) data.get(position);
            ViewGroup.LayoutParams layoutParams = vHolder._Img.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = itemHeight;
            vHolder._Img.setLayoutParams(layoutParams);

            if (item.type == Pic.NORMAL) {
                if (item.mode == 1) {
                    vHolder._TvSelect.setVisibility(View.GONE);
                    vHolder._TvSelect.setOnClickListener(null);
                } else if (item.mode == 2) {
                    vHolder._TvSelect.setVisibility(View.VISIBLE);
                    vHolder._TvSelect.setOnClickListener(v -> {
                        setSelected(position);
                    });

                    if (item.status == Pic.SELECTED) {
                        vHolder._TvSelect.setBackgroundResource(R.color.colorPrimary);
                    } else {
                        vHolder._TvSelect.setBackgroundResource(R.color.mine_shaf);
                    }
                }

                vHolder._LayLoading.setVisibility(View.GONE);




                Glide.with(context)
                        .load(BASE_IMG + item.url)
                        .apply(GlideUtils.picOptions)
                        .into(vHolder._Img);

                vHolder._TvDel.setVisibility(View.VISIBLE);
                vHolder._TvDel.setOnClickListener(v -> {
                    this.data.remove(position);
                    if (this.picsRecyclerView.uploadedImgList.size() > position) {
                        this.picsRecyclerView.uploadedImgList.remove(position);
                        //其他的删除
                        getDefault().post(new Msg(position, 4000, -1));
                    }
                    notifyDataSetChanged();
                });

                vHolder._Img.setOnClickListener(v -> {
                    ArrayList<String> paths = new ArrayList<>();
                    for (int i = 0; i < this.data.size() - 1; i++) {
                        Pic pic = (Pic) data.get(i);
                        paths.add(pic.url);
                    }
                    if (isNeedDownLoad) {
                        UiSwitch.imageBrowser(context, paths, position);
                    } else {
                        UiSwitch.imageBrowserNoDownload(context, paths, position);
                    }
                });
            } else if (item.type == Pic.LOADING) {
                vHolder._TvSelect.setVisibility(View.GONE);
                vHolder._LayLoading.setVisibility(View.VISIBLE);
                vHolder._Loading.startAnim();
            } else if (item.type == Pic.TO_ADD) {
                vHolder._TvSelect.setVisibility(View.GONE);
                vHolder._LayLoading.setVisibility(View.GONE);
                vHolder._TvDel.setVisibility(View.GONE);
                vHolder._Img.setImageResource(R.drawable.img_add_pic);

                vHolder._Img.setOnClickListener(v -> {
                    int maxCount = Constants.IMG_COUNT - data.size() - 1;
                    if (maxCount <= 0) {
                        if(view!=null) {
                            Toast.makeText(context, view.getContext().getString(R.string.at_best) + (Constants.IMG_COUNT - 2) + view.getContext().getString(R.string.sheet), Toast.LENGTH_SHORT).show();
                        }
                        return;
                    }


                    AvatarUploadDialog avatarUploadDialog = new AvatarUploadDialog((Activity) context, true, maxCount);
                    //TrendsUploadDialog avatarUploadDialog=new TrendsUploadDialog((Activity) context, true, maxCount);


                    Window window = ((Activity) context).getWindow();
                    window.setGravity(Gravity.CENTER);

                    window.setWindowAnimations(R.style.dialogtextAnim);
//                    window.setWindowAnimations(R.anim.rotate);
//                    WindowManager.LayoutParams attributes = window.getAttributes();
//                    attributes.x = (int) (0.9 * VersionTools.getTargetWidth());
//                    attributes.y = 0.9 * UiSwitch.

                    avatarUploadDialog.show();
                });
            }

            vHolder.itemView.setOnClickListener(v -> {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(position, item, v);
                }
            });
        }
    }

    public void setItemHeight(int itemHeight) {
        this.itemHeight = itemHeight;
    }

    public void setSelected(int pos) {
        this.selectedPos = pos;
        for (Pic item : (ArrayList<Pic>) this.data) {
            item.status = Pic.NORMAL;
        }

        Pic item = (Pic) this.data.get(pos);
        this.seletedItem = item;
        item.status = Pic.SELECTED;
        notifyDataSetChanged();
    }


    public static class VHolder extends IViewHolder {

        public ImageView _Img;
        public TextView _TvDel;
        public TextView _TvSelect;
        public LVCircularCD _Loading;
        public RelativeLayout _LayLoading;

        public VHolder(View view) {
            super(view);
            _Img = view.findViewById(R.id.img);
            _TvDel = view.findViewById(R.id.tv_del);
            _TvSelect = view.findViewById(R.id.tv_select);
            _Loading = view.findViewById(R.id.loading);
            _LayLoading = view.findViewById(R.id.lay_loading);
        }
    }

    /**
     * 设置图片BASEURL
     *
     * @param baseImg
     */
    public void setBaseImg(String baseImg) {
        BASE_IMG = baseImg;
    }


    public Pic getSeletedItem() {
        return seletedItem;
    }

    public int getSeletedItemPos() {
        return selectedPos;
    }


}
