package com.quansu.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.quansu.common.ui.BaseAdapter;
import com.quansu.utils.UiSwitch;
import com.quansu.utils.UiUtils;
import com.quansu.widget.irecyclerview.IViewHolder;
import com.ysnows.quansu.R;

import java.util.ArrayList;


/**
 * Created by xianguangjin on 16/6/2.
 */

public class VPicsAdapter extends BaseAdapter {

    public VPicsAdapter(Context context) {
        super(context);
    }

    public VPicsAdapter(Context context, ArrayList data) {
        super(context, data);
    }


    @Override
    public IViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_item_v_pics, null);
        VHolder vHolder = new VHolder(itemView);

        return vHolder;
    }

    @Override
    public void onBindViewHolder(IViewHolder holder, int position) {
        if (holder != null) {
            VHolder vHolder = (VHolder) holder;

            String item = (String) data.get(position);

           /* Glide.with(context)
                    .load(item)
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .fitCenter()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            float scale = (resource.getHeight() * 1.0f) / (resource.getWidth() * 1.0f);

                            int widthPixels = UiUtils.getScreenWidthPixels(context);
                            int heightPixels = (int) (widthPixels * scale);

                            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, heightPixels);
                            vHolder._Img.setLayoutParams(layoutParams);

                            vHolder._Img.setImageBitmap(resource);
                        }
                    });*/
            RequestOptions myOptions = new RequestOptions()
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL);



            Glide.with(context)
                    .load(item)
                    .apply(myOptions)
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(Drawable drawable, Transition<? super Drawable> transition) {

                            BitmapDrawable bd= (BitmapDrawable) drawable;
                            Bitmap bm=bd.getBitmap();
                            float scale = (bm.getHeight() * 1.0f) / (bm.getWidth() * 1.0f);

                            int widthPixels = UiUtils.getScreenWidthPixels(context);
                            int heightPixels = (int) (widthPixels * scale);

                            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, heightPixels);
                            vHolder._Img.setLayoutParams(layoutParams);

                            vHolder._Img.setImageBitmap(bm);
                        }
                    });


            vHolder.itemView.setOnClickListener(v -> {
                UiSwitch.imageBrowser(view.getContext(), this.data, position);

                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(position, item, v);
                }
            });

        }
    }

    static class VHolder extends IViewHolder {
        ImageView _Img;

        VHolder(View view) {
            super(view);
            _Img = (ImageView) view.findViewById(R.id.img);
        }
    }

}
