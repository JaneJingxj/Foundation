package com.quansu.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.billy.cc.core.component.CC;
import com.quansu.cons.CP;
import com.quansu.cons.Config;
import com.quansu.ui.activity.DishImagePagerActivity;
import com.quansu.ui.activity.ImageDragActivity;
import com.quansu.ui.activity.ImagePagerActivity;
import com.ysnows.quansu.R;

import java.util.ArrayList;

/**
 * Created by xianguangjin on 16/6/25.
 * <p>
 * 我的GitHub: https://github.com/ysnows
 * <p>
 * 加油,做一个真的汉子
 */

public class UiSwitch {
    /**
     * 进入动画
     */
    public static final int enterAnim = R.anim.activity_show_in_amination;
    /**
     * 退出动画
     */
    public static final int exitAnim = R.anim.acitivity_show_out_amination;

    public static void single(Context context, Class cls) {
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);

        if (!(context instanceof Activity)) {
//            调用方没有设置context或app间组件跳转，context为application
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }

        if (context instanceof Activity) {
            ((Activity) context).overridePendingTransition(enterAnim, exitAnim);
        }
    }

    public static void bundle(Context context, Class cls, Bundle bundle) {
        Intent intent = new Intent(context, cls);
        intent.putExtras(bundle);

        if (!(context instanceof Activity)) {
//            调用方没有设置context或app间组件跳转，context为application
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
        if (context instanceof Activity) {
            ((Activity) context).overridePendingTransition(enterAnim, exitAnim);
        }

    }

    public static void bundle(Activity context, Class cls, Bundle bundle, View v) {
        Intent intent = new Intent(context, cls);
        intent.putExtras(bundle);

        if (!(context instanceof Activity)) {
//            调用方没有设置context或app间组件跳转，context为application
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
        context.overridePendingTransition(enterAnim, exitAnim);

//        } else {
//            ActivityOptionsCompat options = ActivityOptionsCompat.
//                    makeSceneTransitionAnimation(context, v, transitionName);
//            context.startActivity(intent, options.toBundle());
//        }

    }

    public static void singleRes(Activity context, Class cls, int requestCode) {
        Intent intent = new Intent(context, cls);
        context.startActivityForResult(intent, requestCode);
        if (context instanceof Activity) {
            context.overridePendingTransition(Config.enterAnim, Config.exitAnim);
        }

    }

    public static void singleRes(Fragment context, Class cls, int requestCode) {
        Intent intent = new Intent(context.getContext(), cls);
        context.startActivityForResult(intent, requestCode);

    }

    public static void bundleRes(Activity context, Class cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent(context, cls);
        intent.putExtras(bundle);
        context.startActivityForResult(intent, requestCode);
        if (context instanceof Activity) {
            context.overridePendingTransition(Config.enterAnim, Config.exitAnim);
        }

    }

    public static void bundleRes(Fragment context, Class cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent(context.getContext(), cls);
        intent.putExtras(bundle);
        context.startActivityForResult(intent, requestCode);

    }

    public static void imageBrowser(Context context, ArrayList<String> paths, int position) {
        ArrayList<String> pathss = new ArrayList<>();
        if (paths != null) {
            for (int i = 0; i < paths.size(); i++) {
                String s = paths.get(i);
                if (s.contains("?")) {
                    String substring = s.substring(0, s.indexOf("?"));
                    pathss.add(substring);
                } else {
                    pathss.add(s);
                }
            }
        }


        Intent intent = new Intent(context, ImagePagerActivity.class);
        intent.putStringArrayListExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, pathss);
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
        intent.putExtra(ImagePagerActivity.EXTRA_IS_NEED_DOWNLOAD, true);
        context.startActivity(intent);


//        CC.obtainBuilder(CP.CP_PICTURE)
//                .setActionName(CP.ACTION_PICTURE)
//                .addParam("image_urls", paths)
//                .addParam("image_index", position)
//                .addParam("is_need_download", true)
//                .build()
//                .call();


        if (context instanceof Activity) {
            ((Activity) context).overridePendingTransition(Config.enterAnim, Config.exitAnim);
        }

    }


    public static void imageBrowser(Context context, ArrayList<String> paths, int position, int[] location, int height, int width) {
        ArrayList<String> pathss = new ArrayList<>();
        if (paths != null) {
            for (int i = 0; i < paths.size(); i++) {
                String s = paths.get(i);
                if (s.contains("?")) {
                    String substring = s.substring(0, s.indexOf("?"));
                    pathss.add(substring);
                } else {
                    pathss.add(s);
                }
            }
        }


//        Intent intent = new Intent(context, ImagePagerActivity.class);

        Intent intent = new Intent(context, ImageDragActivity.class);
        intent.putExtra("left", location[0]);
        intent.putExtra("top", location[1]);
        intent.putExtra("height", height);
        intent.putExtra("width", width);
        intent.putStringArrayListExtra(ImageDragActivity.EXTRA_IMAGE_URLS, pathss);
        intent.putExtra(ImageDragActivity.EXTRA_IMAGE_INDEX, position);
        intent.putExtra(ImageDragActivity.EXTRA_IS_NEED_DOWNLOAD, true);
        context.startActivity(intent);


//        CC.obtainBuilder(CP.CP_PICTURE)
//                .setActionName(CP.ACTION_PICTURE)
//                .addParam("image_urls", paths)
//                .addParam("image_index", position)
//                .addParam("is_need_download", true)
//
//                .addParam("left", location[0])
//                .addParam("top", location[1])
//                .addParam("height",  height)
//                .addParam("width", width)
//                .build()
//                .call();
//






        if (context instanceof Activity) {
            ((Activity) context).overridePendingTransition(Config.enterAnim, Config.exitAnim);
        }

    }


    public static void imageBrowserDish(Context context, ArrayList<String> paths, ArrayList<String> content, int position) {
        ArrayList<String> pathss = new ArrayList<>();
        if (paths != null) {
            for (int i = 0; i < paths.size(); i++) {
                String s = paths.get(i);
                if (s.contains("?")) {
                    String substring = s.substring(0, s.indexOf("?"));
                    pathss.add(substring);
                } else {
                    pathss.add(s);
                }
            }
        }

        Intent intent = new Intent(context, DishImagePagerActivity.class);
//        intent.putStringArrayListExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, paths);

        intent.putStringArrayListExtra(DishImagePagerActivity.EXTRA_IMAGE_URLS, pathss);
        intent.putStringArrayListExtra(DishImagePagerActivity.EXTRA_IMAGE_URLS_CONTENT, content);
        intent.putExtra(DishImagePagerActivity.EXTRA_IMAGE_INDEX, position);
        intent.putExtra(DishImagePagerActivity.EXTRA_IS_NEED_DOWNLOAD, true);
        context.startActivity(intent);
        if (context instanceof Activity) {
            ((Activity) context).overridePendingTransition(Config.enterAnim, Config.exitAnim);
        }

    }


    public static void imageBrowserNoDownload(Context context, ArrayList<String> paths, int position) {
        ArrayList<String> pathss = new ArrayList<>();

        if (paths != null) {
            for (int i = 0; i < paths.size(); i++) {
                String s = paths.get(i);
                try {
                    if (s.contains("?")) {
                        String substring = s.substring(0, s.indexOf("?"));
                        pathss.add(substring);
                    } else {
                        pathss.add(s);
                    }
                } catch (Exception e) {

                }
            }
        }

        Intent intent = new Intent(context, ImagePagerActivity.class);
        intent.putStringArrayListExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, pathss);
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
        intent.putExtra(ImagePagerActivity.EXTRA_IS_NEED_DOWNLOAD, false);
        context.startActivity(intent);


//        CC.obtainBuilder(CP.CP_PICTURE)
//                .setActionName(CP.ACTION_PICTURE)
//                .addParam("image_urls", paths)
//                .addParam("image_index", position)
//                .addParam("is_need_download", true)
//                .build()
//                .call();


        if (context instanceof Activity) {
            ((Activity) context).overridePendingTransition(Config.enterAnim, Config.exitAnim);
        }

    }

    public static void imageBrowserNoDownload(Context context, ArrayList<String> paths, int position, int[] location, int height, int width) {
        ArrayList<String> pathss = new ArrayList<>();

        if (paths != null) {
            for (int i = 0; i < paths.size(); i++) {
                String s = paths.get(i);
                try {
                    if (s.contains("?")) {
                        String substring = s.substring(0, s.indexOf("?"));
                        pathss.add(substring);
                    } else {
                        pathss.add(s);
                    }
                } catch (Exception e) {

                }
            }
        }

        Intent intent = new Intent(context, ImageDragActivity.class);
        intent.putExtra("left", location[0]);
        intent.putExtra("top", location[1]);
        intent.putExtra("height", height);
        intent.putExtra("width", width);
        intent.putStringArrayListExtra(ImageDragActivity.EXTRA_IMAGE_URLS, pathss);
        intent.putExtra(ImageDragActivity.EXTRA_IMAGE_INDEX, position);
        intent.putExtra(ImageDragActivity.EXTRA_IS_NEED_DOWNLOAD, false);
        context.startActivity(intent);


//        CC.obtainBuilder(CP.CP_PICTURE)
//                .setActionName(CP.ACTION_PICTURE)
//                .addParam("image_urls", paths)
//                .addParam("image_index", position)
//                .addParam("is_need_download", true)
//
//                .addParam("left", location[0])
//                .addParam("top", location[1])
//                .addParam("height",  height)
//                .addParam("width", width)
//                .build()
//                .call();




        if (context instanceof Activity) {
            ((Activity) context).overridePendingTransition(Config.enterAnim, Config.exitAnim);
        }

    }





}
