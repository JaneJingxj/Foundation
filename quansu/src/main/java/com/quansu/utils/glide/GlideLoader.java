package com.quansu.utils.glide;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.yancy.imageselector.ImageLoader;
import com.ysnows.quansu.R;

/**
 * Created by xianguangjin on 16/1/23.
 */
public class GlideLoader implements ImageLoader {

    RequestOptions myOptions = new RequestOptions()
            .centerCrop()
            .placeholder(R.color.dim_gray)
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.ALL);


    @Override
    public void displayImage(Context context, String path, ImageView imageView) {


        Glide.with(context)
                .load(path)
                .apply(myOptions)
                .into(imageView);
    }

}
