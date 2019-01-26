package com.quansu.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.widget.Toast;

import com.quansu.common.mvp.BaseView;
import com.quansu.utils.glide.GlideLoader;
import com.yancy.imageselector.ImageConfig;
import com.yancy.imageselector.ImageSelector;
import com.yancy.imageselector.utils.Utils;
import com.ysnows.quansu.R;

import java.io.File;
import java.util.ArrayList;

import static android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION;
import static android.content.Intent.FLAG_GRANT_WRITE_URI_PERMISSION;

/**
 * Created by xianguangjin on 16/6/23.
 * <p>
 * 我的GitHub: https://github.com/ysnows
 * <p>
 * 加油,做一个真的汉子
 * <p>
 * 选择照片和拍摄照片的工具类
 * 需要ImageSelector库的支持 https://github.com.ysnows.externalYe/GalleryPick
 */

public class ImageSelectorUtils {

    public static final int REQUEST_CAMERA = 100;
    public static File tempFile = null;

    public static String filePath = "/ImageSelector/Pictures";
    public static String cropImagePath;

    /**
     * 单选
     *
     * @param context
     * @param isCrop
     */
    public static void singleSelect(Activity context, boolean isCrop) {
        if (isCrop) {
            ImageConfig imageConfig
                    = new ImageConfig.Builder(new GlideLoader())
                    .steepToolBarColor(context.getResources().getColor(R.color.colorPrimaryDark))
                    .titleBgColor(context.getResources().getColor(R.color.colorPrimary))
                    .titleSubmitTextColor(context.getResources().getColor(R.color.black))
                    .titleTextColor(context.getResources().getColor(R.color.black))
                    // (截图默认配置：关闭    比例 1：1    输出分辨率  500*500)
                    .crop()
                    // 开启单选   （默认为多选）
                    .singleSelect()
                    // 开启拍照功能 （默认关闭）
//                    .showCamera()
                    // 拍照后存放的图片路径（默认 /temp/picture） （会自动创建）
                    .filePath(filePath)
                    .build();
            ImageSelector.open(context, imageConfig);   // 开启图片选择器
        } else {
            ImageConfig imageConfig
                    = new ImageConfig.Builder(new GlideLoader())
                    .steepToolBarColor(context.getResources().getColor(R.color.colorPrimaryDark))
                    .titleBgColor(context.getResources().getColor(R.color.colorPrimary))
                    .titleSubmitTextColor(context.getResources().getColor(R.color.black))
                    .titleTextColor(context.getResources().getColor(R.color.black))
                    .singleSelect()
                    // 开启拍照功能 （默认关闭）
//                    .showCamera()

                    // 拍照后存放的图片路径（默认 /temp/picture） （会自动创建）
                    .filePath(filePath)
                    .build();
            ImageSelector.open(context, imageConfig);   // 开启图片选择器
        }
    }

    /**
     * 多选
     *
     * @param context
     */
    public static void multiSelect(Activity context, int maxCount) {
        ImageConfig imageConfig
                = new ImageConfig.Builder(new GlideLoader())
                .steepToolBarColor(context.getResources().getColor(R.color.colorPrimaryDark))
                .titleBgColor(context.getResources().getColor(R.color.colorPrimary))
                .titleSubmitTextColor(context.getResources().getColor(R.color.black))
                .titleTextColor(context.getResources().getColor(R.color.black))
                // 开启多选   （默认为多选）
                .mutiSelect()
                // 多选时的最大数量   （默认 9 张）
                .mutiSelectMaxSize(maxCount)

                // 开启拍照功能 （默认关闭）
//                .showCamera()
                // 已选择的图片路径
                .pathList(new ArrayList<>())
                // 拍照后存放的图片路径（默认 /temp/picture） （会自动创建）
                .filePath(filePath)
                .build();

        ImageSelector.open(context, imageConfig);   // 开启图片选择器


    }

    /**
     * 选择相机
     */

    public static void showCameraAction(Activity context, boolean isCrop) {


        ((BaseView) context).checkPer(context, () -> {
            // 跳转到系统照相机
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (cameraIntent.resolveActivity(context.getPackageManager()) != null) {
                // 设置系统相机拍照后的输出路径
                // 创建临时文件
                tempFile = FileUtils.getPicFile(context);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//7.0及以上
                    Uri uriForFile = FileProvider.getUriForFile(context, "com.muxi.ant.provider", tempFile);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriForFile);
                    cameraIntent.addFlags(FLAG_GRANT_READ_URI_PERMISSION);
                    cameraIntent.addFlags(FLAG_GRANT_WRITE_URI_PERMISSION);

                } else {
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
                }



                cameraIntent.addFlags(FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
//                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                context.startActivityForResult(cameraIntent, REQUEST_CAMERA);
            } else {
                Toast.makeText(context, R.string.msg_no_camera, Toast.LENGTH_SHORT).show();
            }
        }, Manifest.permission.CAMERA);


    }

    /**
     * 截图
     *
     * @param context
     * @param imagePath
     * @param aspectX
     * @param aspectY
     * @param outputX
     * @param outputY
     */
    public static void crop(Activity context, String imagePath, int aspectX, int aspectY, int outputX, int outputY) {
        File file = null;
        if (Utils.existSDCard()) {
            file = new File(Environment.getExternalStorageDirectory() + filePath, Utils.getImageName());
        } else {
            file = new File(context.getCacheDir(), Utils.getImageName());
        }

        cropImagePath = file.getAbsolutePath();
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(Uri.fromFile(new File(imagePath)), "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", aspectX);
        intent.putExtra("aspectY", aspectY);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        context.startActivityForResult(intent, ImageSelector.IMAGE_CROP_CODE);
    }


    /**
     * 说明:
     * OnActivity中的调用
     */

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
////        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == ImageSelector.IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
//            // Get Image Path List
//            /** 说明:
//             *选择图片
//             */
//            List<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);
//            for (String path : pathList) {
//                Log.d("ImageSelector", path);
//            }
//        } else if (requestCode == ImageSelectorUtils.REQUEST_CAMERA && resultCode == RESULT_OK) {
//            Log.d("ImageSelector", ImageSelectorUtils.tempFile.getAbsolutePath());
//            /** 说明:
//             *截图
//             */
//            ImageSelectorUtils.crop(this, ImageSelectorUtils.tempFile.getAbsolutePath(), 2, 2, 500, 500);
//        } else if (requestCode == ImageSelector.IMAGE_CROP_CODE && resultCode == RESULT_OK) {
//            /** 说明:
//             *拍照最终结果
//             */
//            String cropImagePath = ImageSelectorUtils.cropImagePath;
//            Log.d("ImageSelector", cropImagePath);
//        }
//    }


}
