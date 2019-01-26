/*
 * Copyright (C) 2016 CaMnter yuanyu.camnter@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.quansu.utils.glide;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.quansu.utils.UiUtils;
import com.quansu.widget.wave.Constant;
import com.ysnows.quansu.R;

import java.io.File;
import java.util.concurrent.ExecutionException;

/**
 * Description：GlideUtils
 * Created by：CaMnter
 * Time：2016-01-04 22:19
 */
public class GlideUtils {


    private static final String TAG = "GlideUtils";

    private static RequestOptions myOptions = new RequestOptions()
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .error(R.drawable.ic_default_avatar);


    private static RequestOptions adversOption = new RequestOptions()
            .centerCrop()
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .error(R.drawable.bg_need_priority);

    private static RequestOptions gifOption = new RequestOptions()
            .centerCrop()
            .skipMemoryCache(true)
            .priority(Priority.HIGH)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .error(R.drawable.bg_need_priority);


    public static RequestOptions myOptionAdapter4 = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .skipMemoryCache(true)
            .priority(Priority.HIGH)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .error(R.drawable.bg_need_priority);



    private static RequestOptions gifOptionTo = new RequestOptions()
            .centerCrop()
            .skipMemoryCache(true)
            .priority(Priority.HIGH)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .error(R.drawable.bg_need_priority);


    private static RequestOptions myOption = new RequestOptions()
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.bg_need_priority)
            .error(R.drawable.bg_need_priority)
            .skipMemoryCache(true);

    private static RequestOptions myOptionAdapter = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.bg_need_priority)
            .error(R.drawable.bg_need_priority)
            .skipMemoryCache(true);



    private static RequestOptions myOptionAdapter3 = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .error(R.drawable.ic_new_load_nothing)
            .skipMemoryCache(true);


    public static RequestOptions adsOptions = new RequestOptions()
            .centerCrop()
            .placeholder(R.drawable.bg_need_priority)
            .skipMemoryCache(true)
            .error(R.drawable.bg_need_priority)
            .diskCacheStrategy(DiskCacheStrategy.ALL);

    public static RequestOptions adsOptionsNo = new RequestOptions()
            .centerCrop()
            .placeholder(R.drawable.bg_need_priority)
//            .skipMemoryCache(true)
            .error(R.drawable.bg_need_priority)
            .diskCacheStrategy(DiskCacheStrategy.ALL);


    public static RequestOptions alsOptions = new RequestOptions()
            .centerCrop()
            .placeholder(R.drawable.bg_need_priority)
            .skipMemoryCache(true)
            .error(R.drawable.bg_need_priority)
            .diskCacheStrategy(DiskCacheStrategy.ALL);


    public static RequestOptions crsOptions = new RequestOptions()
            .centerCrop()
            .placeholder(R.drawable.bg_need_priority)
            .skipMemoryCache(true)
            .error(R.drawable.bg_need_priority)
            .diskCacheStrategy(DiskCacheStrategy.ALL);


    public static RequestOptions picOptions = new RequestOptions()
            .centerCrop()
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.ALL);

    public static RequestOptions picOptionsto = new RequestOptions()
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.ALL);


    public static RequestOptions animOptions = new RequestOptions()
            .centerCrop()
            .skipMemoryCache(true)
            .dontAnimate()
            .diskCacheStrategy(DiskCacheStrategy.ALL);


    public static RequestOptions CirmyOptions = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.bg_need_priority)
            .skipMemoryCache(true)
            .error(R.drawable.bg_need_priority);


    public static RequestOptions getoptions(Context context) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .transform(new GlideCircleTransform(context))
                .skipMemoryCache(true)
                .override(100, 100)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        return options;
    }

    public static RequestOptions getoptionsto(Context context) {
        RequestOptions CirmyOptions = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.ic_default_avatar)
                .bitmapTransform(new GlideCircleTransform(context))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(true)
                .override(100, 100)
                .error(R.drawable.ic_default_avatar);
        return CirmyOptions;
    }


    public static RequestOptions fixedOptions = new RequestOptions()
//            .override(200, 200)
            .override(Target.SIZE_ORIGINAL)
            .error(R.drawable.bg_need_priority)
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.ALL);


    public static RequestOptions fixedavator = new RequestOptions()
            .override(200, 200)
            .placeholder(R.drawable.ic_default_avatar)
            .override(Target.SIZE_ORIGINAL)
            .skipMemoryCache(false)
            .error(R.drawable.ic_default_avatar)
            .diskCacheStrategy(DiskCacheStrategy.ALL);


    public static RequestOptions getoptionstest(int a, int b) {
        RequestOptions testOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(true)
                .centerCrop()
                .override(a, b)
                .error(R.drawable.bg_need_priority);
        return testOptions;

    }

    public static RequestOptions myOptionAdapter2 = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.bg_need_error_small_3)
            .error(R.drawable.ic_new_load_nothing)
            .skipMemoryCache(true);




    public static RequestOptions fixedOptionsHome = new RequestOptions()
            .override(Target.SIZE_ORIGINAL)
            .error(R.drawable.bg_need_priority)
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.ALL);



    public static RequestOptions re_ControlsChange2 = new RequestOptions()
            .override(Target.SIZE_ORIGINAL)
            .error(R.drawable.bg_need_priority)
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.ALL);


    public static RequestOptions getoptionstwo(Context context, Drawable error_drawable, boolean isCircle) {
        RequestOptions myOptions;
        if (null != error_drawable) {
            if (isCircle) {//圆形
                myOptions = new RequestOptions()
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .skipMemoryCache(true)
                        .override(50, 50)
                        .transform(new GlideCircleTransform(context))
                        .error(error_drawable);
            } else {
                myOptions = new RequestOptions()
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .skipMemoryCache(true)
                        .override(100, 100)
                        .error(error_drawable);
            }
        } else {
            if (isCircle) {//圆形
                myOptions = new RequestOptions()
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.ic_default_avatar)
                        .override(100, 100)
                        .skipMemoryCache(true)
                        .transform(new GlideCircleTransform(context))
                        .error(R.drawable.ic_default_avatar);
            } else {
                myOptions = new RequestOptions()
                        .centerCrop()
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .override(100, 100)
                        .transform(new GlideCircleTransform(context))
                        .error(R.drawable.ic_default_avatar);

            }

        }
        return myOptions;

    }

    /**
     * 加载GIF
     *
     * @param context
     * @param
     * @param imageVie
     */
    public static void lGif(Context context, String url, ImageView imageVie) {
        Glide.with(context)
                .load(url)
                .apply(gifOption)
                .into(imageVie);
    }


    /**
     * @param context
     * @param url
     * @param imageVie
     * 动态加载动态图
     */
    public static void lGifTo(Context context, String url, ImageView imageVie) {
        Glide.with(context)
                .load(url)
                .apply(myOptionAdapter4)
                .into(imageVie);
    }


    public static void lGifto(Context context, String url, ImageView imageVie) {
        Glide.with(context)
                .load(url)
                .apply(gifOptionTo)
                .into(imageVie);
    }




    /**
     * 加载广告
     *
     * @param context
     * @param url
     * @param imageVie
     * @param i
     */
    public static void lImg(Context context, String url, ImageView imageVie, int i) {

        Glide.with(context).asBitmap().load(getRealImg(url)).apply(myOptions).into(imageVie);

    }


    /**
     * @param context
     * @param url
     * @param imageVie 不设置缓存
     */
    public static void NoLImg(Context context, String url, ImageView imageVie) {


        Glide.with(context)
                .load(getRealImg(url))
                .apply(adversOption)
                .transition(new DrawableTransitionOptions().crossFade(150))//渐显效果
                .into(imageVie);
    }

    //不是圆，没有限定大小
    public static void lImg(Context context, String url, ImageView imageVie) {
        Glide.with(context)
                .load(getRealImg(url))
                .apply(myOption)
                .transition(new DrawableTransitionOptions().crossFade(150))//渐显效果
                .into(imageVie);
    }




    public static void lImg1(Context context, String url, ImageView imageVie) {

        Glide.with(context)
                .load(getRealImg(url))
                .apply(myOptionAdapter2)
                .into(imageVie);
    }


    /**
     * 指定大小
     * @param context
     * @param url
     * @param imageVie
     * @param Width
     * @param height
     */
    public static void lImgHot(Context context, String url, ImageView imageVie,int Width ,int height) {

          RequestOptions myOptionAdapter2 = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.bg_need_error_small_3)
                .error(R.drawable.ic_new_load_nothing)
                .override(Width, height)
                .skipMemoryCache(true);

        Glide.with(context)
                .load(getRealImg(url))
                .apply(myOptionAdapter2)
                .into(imageVie);
    }

    public static void lImg2(Context context, String url, ImageView imageVie) {
        Glide.with(context)

                .load(getRealImg(url))
                .apply(myOptionAdapter)
                .transition(new DrawableTransitionOptions().crossFade(150))//渐显效果
                .into(imageVie);
    }



    public static void lImgAdapterAll(Context context, String url, ImageView imageVie) {
        Glide.with(context)

                .load(getRealImg(url))
                .apply(myOptionAdapter)
                .transition(new DrawableTransitionOptions().crossFade(150))//渐显效果
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        int intrinsicWidth = resource.getIntrinsicWidth();
                        int intrinsicHeight = resource.getIntrinsicHeight();


                        int height = UiUtils.getScreenWidthPixels(context) * intrinsicHeight / intrinsicWidth;
                        ViewGroup.LayoutParams para = imageVie.getLayoutParams();
                        para.height = height;
                        para.width = UiUtils.getScreenWidthPixels(context) - 100;
                        imageVie.setImageDrawable(resource);


                    }
                });
    }


    private static Drawable zoomDrawable(Drawable drawable, float w, float h) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap oldbmp = drawableToBitmap(drawable);
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) w / width);
        float scaleHeight = ((float) h / height);

        Log.e("Adakda", "scaleWidth:" + scaleWidth + "--setHeight1:" + scaleHeight);


        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height, matrix, true);
        return new BitmapDrawable(null, newbmp);
    }


    private static Bitmap drawableToBitmap(Drawable drawable) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(width, height, config);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;
    }


    //不是圆，没有限定大小，没有centerCrop()
    public static void lImgNOCenterCp(Context context, String url, ImageView imageVie) {

        Glide.with(context)
                .load(getRealImg(url))
                .apply(CirmyOptions)
                //.transition(new DrawableTransitionOptions().crossFade(150))//渐显效果
                .into(imageVie);

    }


    /**
     *
     * 只针对头像
     * @param context
     * @param url
     * @param imageView
     * @param Width
     * @param height
     */
    public static void lImgAvator(Context context, String url, ImageView imageView,int Width ,int height){

        RequestOptions myOptions = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.ic_default_avatar)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(true)
                .override(Width, height)
                .transform(new GlideCircleTransform(context))
                .error(R.drawable.ic_default_avatar);


        if (context != null) {
            Glide.with(context)
                    .load(getRealImg(url))
                    .apply(myOptions)
                    //.transition(new DrawableTransitionOptions().crossFade(150))//渐显效果
                    .into(imageView);
        }


    }



    //大小100X100
    public static void lImg(Context context, String url, ImageView imageView, Drawable error_drawable, boolean isCircle) {

        if (!TextUtils.isEmpty(url)) {
            if (context != null) {
                Glide.with(context)
                        .load(getRealImg(url))
                        .apply(getoptionstwo(context, error_drawable, isCircle))
                        .transition(new DrawableTransitionOptions().crossFade(150))//渐显效果
                        .into(imageView);

            }
        }
    }


    //是否是圆,不是圆，大小没有限定
    public static void lImg(Context context, String url, ImageView imageView, boolean isCircle) {

        if (!TextUtils.isEmpty(url)) {
            if (context != null) {
                RequestOptions CirmyOptions;

                if (isCircle) {//圆形图片
                    CirmyOptions = new RequestOptions()
                            .centerCrop()
                            .circleCrop()
                            .skipMemoryCache(true)
                            .placeholder(R.drawable.ic_default_avatar)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .skipMemoryCache(true);
                } else {
                    CirmyOptions = new RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .skipMemoryCache(true)
                            .placeholder(R.drawable.bg_need_priority)
                            .centerCrop()
                            .error(R.drawable.bg_need_priority)
                            .skipMemoryCache(true);

                }

                Glide.with(context)
                        .load(getRealImg(url))
                        //这个是设置渐显的效果
                        .transition(new DrawableTransitionOptions().crossFade(150))//渐显效果
                        .apply(CirmyOptions)
                        .into(imageView);

            }
        }
    }

    public static void lImg(Context context, String url, ImageView imageView, boolean isCircle, boolean hasPlaceHolder) {

        if (!TextUtils.isEmpty(url)) {
            if (context != null) {
                RequestOptions CirmyOptions;

                if (isCircle) {//圆形图片
                    CirmyOptions = new RequestOptions()
                            .centerCrop()
                            .circleCrop()
                            .skipMemoryCache(true)
                            .placeholder(R.drawable.bg_need_priority)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .skipMemoryCache(true);
                } else {
                    CirmyOptions = new RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .skipMemoryCache(true)
                            .placeholder(R.drawable.bg_need_priority)
                            .centerCrop()
                            .error(R.drawable.bg_need_priority)
                            .skipMemoryCache(true);

                }

                Glide.with(context)
                        .load(getRealImg(url))
                        //这个是设置渐显的效果
                        .transition(new DrawableTransitionOptions().crossFade(150))//渐显效果
                        .apply(CirmyOptions)
                        .into(imageView);

            }
        }
    }


    //need小测里的图片的设置,图片是全屏 设置480X800,列表是480X200(主要这两种)
    //不是圆
    //2.素材
    public static void testLimg(Context context, String url, ImageView imageView, int a, int b) {

        Glide.with(context)
                .load(getRealImg(url))
                .apply(getoptionstest(a, b))
                .into(imageView);
    }


    //大小200X200，不是圆
    public static void fixedLimgN(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(getRealImg(url))
                .apply(fixedOptions)
                .into(imageView);
    }

    //大小200X200，头像
    public static void fixedLimgavator(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(getRealImg(url))
                .apply(fixedavator)
                .into(imageView);
    }




    //大小112X66，不是圆
    public static void fixedLimgNHome(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(getRealImg(url))
                .apply(fixedOptionsHome)
                .into(imageView);
    }


    //可以设置错误的加载图片  不是圆  没有限制大小
    public static void lImgN(Context context, String url, ImageView imageView, Drawable errorDrawable) {

        if (!TextUtils.isEmpty(url)) {
            if (context != null) {
                RequestOptions options;
                if (null != errorDrawable) {

                    options = new RequestOptions()
                            .centerCrop()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .error(errorDrawable);
                } else {

                    options = new RequestOptions()
                            .centerCrop()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .error(R.drawable.bg_need_priority);

                }

                Glide.with(context)
                        .load(getRealImg(url))
                        .apply(options)
                        .into(imageView);

            }
        }
    }

    //大小100X100,不是圆
    public static void lImg(Context context, int resourceId, ImageView imageView) {
        if (context != null) {

            Glide.with(context)
                    .load(resourceId)
                    .apply(myOptions)
                    .into(imageView);

        }
    }

    //大小100X100,不是圆
    public static void lImgNoScale(Context context, String url, ImageView imageView) {

        if (!TextUtils.isEmpty(url)) {
            if (context != null) {
                Glide.with(context)
                        .load(getRealImg(url))
                        .apply(myOptions)
                        .into(imageView);


            }
        }
    }



    //大小100X100, 圆
    public static void lImgCircle(Context context, String url, ImageView imageView) {

        if (!TextUtils.isEmpty(url)) {


            if (context != null) {
                Glide.with(context)
                        .load(getRealImg(url))
                        .apply(getoptionsto(context))
                        .into(imageView);
            }
        }
    }

    //大小100X100, 圆
    public static void lImgCircle(Context context, Integer resourceId, ImageView imageView) {

//        if (!TextUtils.isEmpty(url)) {
        if (context != null) {


            Glide.with(context)
                    .load(resourceId)
                    .apply(getoptionsto(context))
                    .into(imageView);

        }
    }

    //大小100X100, 圆角
    public static void lImgRound(Context context, String url, ImageView imageView) {
        if (!TextUtils.isEmpty(url)) {
            if (context != null) {

                RequestOptions myOptions = new RequestOptions()
                        .centerCrop()
                        .bitmapTransform(new GlideCircleTransform(context))
                        .transform(new GlideRoundTransform(context, 6))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .skipMemoryCache(true)
                        .override(100, 100)
                        .error(R.drawable.ic_default_avatar);

                Glide.with(context)
                        .load(getRealImg(url))
                        .apply(myOptions)
                        .into(imageView);


            }
        }
    }


    /**
     * @param context
     * @param url
     * @param imageView
     * @param iscenterCrop  true：centerCrop()
     * 没有截取
     */
    public static void lImgRound(Context context, String url, ImageView imageView,boolean iscenterCrop){
        if (context != null) {
            RequestOptions myOptions;
            if(iscenterCrop){
                myOptions = new RequestOptions()
                        .centerCrop()
                        .bitmapTransform(new GlideCircleTransform(context))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .skipMemoryCache(true)
                        .error(R.drawable.ic_default_avatar);
            }else{
                myOptions = new RequestOptions()
                        .bitmapTransform(new GlideCircleTransform(context))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .skipMemoryCache(true)
                        .error(R.drawable.ic_default_avatar);

            }

            Glide.with(context)
                    .load(getRealImg(url))
                    .apply(myOptions)
                    .into(imageView);


        }


    }



    //给定角度 大小没有限制
    public static void lImgRound(Context context, String url, ImageView imageView, int radius,int whdth,int height) {
        if (!TextUtils.isEmpty(url)) {
            if (context != null) {
                RequestOptions myOptions = new RequestOptions()
                        .centerCrop()
                        .bitmapTransform(new GlideCircleTransform(context))
                        .transform(new GlideRoundTransform(context, radius))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .skipMemoryCache(true)
                        .override(whdth, height)
                        .error(R.drawable.bg_need_priority);


                Glide.with(context)
                        .load(getRealImg(url))
                        .apply(myOptions)
                        .into(imageView);

            }
        }
    }


    //给定角度 大小没有限制
    public static void lImgRound(Context context, String url, ImageView imageView, int radius) {
        if (!TextUtils.isEmpty(url)) {
            if (context != null) {
                RequestOptions myOptions = new RequestOptions()
                        .centerCrop()
                        .bitmapTransform(new GlideCircleTransform(context))
                        .transform(new GlideRoundTransform(context, radius))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .skipMemoryCache(true)
                        .error(R.drawable.bg_need_priority);


                Glide.with(context)
                        .load(getRealImg(url))
                        .apply(myOptions)
                        .into(imageView);

            }
        }
    }


    public static void displayNative(final ImageView view, @DrawableRes int resId) {
        // 不能崩
        if (view == null) {
            Log.e(TAG, "GlideUtils -> display -> imageView is null");
            return;
        }
        Context context = view.getContext();
        // View你还活着吗？
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }
        try {
            Glide.with(context)
                    .load(resId)
                    .apply(myOptions)
                    .into(view)
                    .getSize(new SizeReadyCallback() {
                        @Override
                        public void onSizeReady(int width, int height) {
                            view.setVisibility(View.VISIBLE);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void cleanData(Context context) {
//        new Thread(() -> Glide.netInstance(context).clearDiskCache()).start();
//        Glide.netInstance(context).clearMemory();
    }




    public static String getRealImg(String url) {
        //&& !url.contains(".gif")
        if (!url.contains("http") && !url.contains("https")) {
            return url;
        } else if (url.contains("x-oss-process=image")) {
            return url + "/format,webp/quality,Q_75";
        } else {
            return url + "?x-oss-process=image/format,webp/quality,Q_75";
        }
    }


    //指定圆角

    public static void specifyFillet(Context context, String imgurl, ImageView imageView) {
        RequestOptions myOptions = new RequestOptions()
                .fitCenter()
                .bitmapTransform(new GlideCircleTransform(context))
                .transform(new GlideTransform(context))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(true)
                .centerCrop()
//                .override(200, 200)
                .override(Target.SIZE_ORIGINAL)
                .error(R.drawable.bg_need_priority);

        Glide.with(context)
                .load(getRealImg(imgurl))
                .apply(myOptions)
                .into(imageView);
    }


    private static RequestOptions myOptionAdapter_one = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
//            .placeholder(R.drawable.bg_need_priority)
            //.error(R.drawable.bg_need_priority)
            .skipMemoryCache(true);

    //不是圆，没有限定大小
    public static void lImgAdapter(Context context, String url, ImageView imageVie) {
        Glide.with(context)
                .load(getRealImg(url))
                .apply(myOptionAdapter_one)
                .transition(new DrawableTransitionOptions().crossFade(300))//渐显效果
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        int intrinsicWidth = resource.getIntrinsicWidth();
                        int intrinsicHeight = resource.getIntrinsicHeight();

                        int setWidth = 0;
                        int setHeight = 0;

                        ViewGroup.LayoutParams para = imageVie.getLayoutParams();


                        int screenWidthPixels = UiUtils.getScreenWidthPixels(context) - UiUtils.dp2Px(context, 30);//屏幕的宽度减去两个间隙

                        float scale;
                        Drawable drawable;
                        //宽 > 高
                        if (intrinsicWidth > intrinsicHeight) {

                            setWidth = (int) (screenWidthPixels * 0.6);//固定的宽度
                            //计算高度
                            int b = (intrinsicWidth / setWidth);
                            if (b == 0) {
                                setHeight = (int) (setWidth / 1.5);
                            } else {
                                setHeight = (int) (intrinsicHeight / b);//计算出高度

                            }

                            Log.e("-BBB--", "setWidth=: " + setWidth);

                            Log.e("-BBB--", "setHeight=: " + setHeight);

                            drawable = zoomDrawable(resource, setWidth, setHeight);

                            para.height = (int) (setHeight);
                            para.width = (int) (setWidth);
                            imageVie.setImageDrawable(drawable);


                        } else {
                            //宽 < 高
                            //高度
                            setHeight = (int) (screenWidthPixels * 0.7);

                            setWidth = intrinsicWidth * setHeight / intrinsicHeight;

                            para.height = (int) (setHeight);
                            para.width = (int) (setWidth);

                            Log.e("adsasd", "setWidth:" + setWidth + "");
                            Log.e("adsasd", "setHeight:" + setHeight + "");

                            drawable = zoomDrawable(resource, setWidth, setHeight);
                            imageVie.setImageDrawable(drawable);
                        }
                  }
                });
    }



    public static void ControlsChange2(Context constant, String url, ImageView imageView) {

        Glide.with(constant)
                .load(getRealImg(url))
                .apply(re_ControlsChange2)
                .into(imageView);

    }




}
