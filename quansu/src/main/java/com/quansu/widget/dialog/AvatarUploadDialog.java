package com.quansu.widget.dialog;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.quansu.common.mvp.BaseView;
import com.quansu.cons.Constants;
import com.quansu.utils.ImageSelectorUtils;
import com.ysnows.quansu.R;

public class AvatarUploadDialog {

    private int aspectX = 4;
    private int aspectY = 4;
    private int picX = 600;
    private int picY = 600;
    public TextView tvTitle;
    public TextView tvTakePic;
    public TextView tvChoosePic;
    public TextView tvCancel;
    private Activity context;
    private boolean isCrop;
    /**
     * 是否是多选
     */
    private boolean isMultiPic = false;
    /**
     * 最多还可以选择多少
     */
    private int maxCount = Constants.IMG_COUNT;
    private BottomSheetDialog bottomSheetDialog;
    private View layout;

    public AvatarUploadDialog(Activity context, boolean isCrop) {
        this.context = context;
        this.isCrop = isCrop;
        init();
    }


    public AvatarUploadDialog(Activity context, boolean isMultiPic, int maxCount) {
        this.context = context;
        this.isMultiPic = isMultiPic;
        this.maxCount = maxCount;
        init();
    }

    public AvatarUploadDialog(Activity context, int picX, int picY, int aspectX, int aspectY) {
        this.context = context;
        this.picX = picX;
        this.picX = picY;
        this.aspectX = aspectX;
        this.aspectY = aspectY;
        init();

    }


    private void init() {


        bottomSheetDialog = new BottomSheetDialog(context);


        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layout = layoutInflater.inflate(R.layout.captureto_img_view, null);

        bottomSheetDialog.setContentView(layout);
        //设置透明度背景
        ((View) layout.getParent()).setBackgroundColor(Color.TRANSPARENT);

        tvTitle = layout.findViewById(R.id.tv_title);
        tvTakePic = layout.findViewById(R.id.tv_take_pic);
        tvChoosePic = layout.findViewById(R.id.tv_choose_pic);
        tvCancel = layout.findViewById(R.id.tv_cancel);

        tvTakePic.setOnClickListener(v -> {
            dismiss();
            ((BaseView) context).checkPer(context, () -> ImageSelectorUtils.showCameraAction(context, isCrop), Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
        });

        tvChoosePic.setOnClickListener(v -> {
            dismiss();
            ((BaseView) context).checkPer(context, () -> {
                if (isMultiPic) {
                    ImageSelectorUtils.multiSelect(context, maxCount);
                } else {
                    ImageSelectorUtils.singleSelect(context, isCrop);
                }
            }, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
        });


        tvCancel.setOnClickListener(v -> dismiss());
    }


    public void showDialog() {


    }


    public void show() {
        if (bottomSheetDialog != null) {
            bottomSheetDialog.show();
        }
    }


    public void dismiss() {
        if (bottomSheetDialog != null) {
            bottomSheetDialog.dismiss();
        }
    }


}
