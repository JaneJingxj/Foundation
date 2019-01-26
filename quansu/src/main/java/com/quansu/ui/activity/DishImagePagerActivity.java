package com.quansu.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.quansu.BaseApp;
import com.quansu.common.mvp.BasePresenter;
import com.quansu.common.ui.BaseActivity;
import com.quansu.ui.fragment.ImageDetailFragment;
import com.quansu.utils.FileUtils;
import com.quansu.utils.Toasts;
import com.quansu.utils.permission.OnGrantCallBack;
import com.quansu.utils.permission.OnPermissionCallBack;
import com.quansu.widget.Dialog;
import com.quansu.widget.shapview.ViewPagerFixed;
import com.ysnows.quansu.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
//菜谱的查看器
public class DishImagePagerActivity extends BaseActivity {

    private static final String STATE_POSITION = "STATE_POSITION";
    public static final String EXTRA_IMAGE_INDEX = "image_index";
    public static final String EXTRA_IMAGE_URLS = "image_urls";
    public static final String EXTRA_IS_NEED_DOWNLOAD = "is_need_download";

    public static final String EXTRA_IMAGE_URLS_CONTENT = "image_urls_content";


    private ViewPagerFixed mPager;
    private TextView indicator;
    private ImageView downloadTv;

    private int pagerPosition;
    private TextView tvPosition;

    private ImageView imgBack;
    private  TextView tvTitle;



    ImagePagerAdapter mAdapter;
    ArrayList<String> urls;

    ArrayList<String > urlcontent;
    private boolean is_need_download = true;

    @Override
    protected int provideContentViewId() {
        return R.layout.image_dish_pager;
    }

    @Override
    public void initListeners() {

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishActivity();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        RxPermissions rxPermissions = new RxPermissions(ImagePagerActivity.this); // where this is an Activity instance
//        rxPermissions
//                .request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                .subscribe(granted -> {
//                    if (granted) {
//                        download();
//                    } else {
//                        JumpPermissionManagement.GoToSetting(ImagePagerActivity.this);
//                    }
//                });
    }

    @Override
    protected void initThings(Bundle savedInstanceState) {


        mPager = (ViewPagerFixed) findViewById(R.id.pager);
        indicator = (TextView) findViewById(R.id.indicator);
        downloadTv = (ImageView) findViewById(R.id.img_down);
        imgBack= (ImageView) findViewById(R.id.img_back);
        tvTitle= (TextView) findViewById(R.id.tv_title);
        tvPosition= (TextView) findViewById(R.id.tv_position);
        pagerPosition = getIntent().getIntExtra(EXTRA_IMAGE_INDEX, 0);
        is_need_download = getIntent().getBooleanExtra(EXTRA_IS_NEED_DOWNLOAD, true);
        urls = getIntent().getStringArrayListExtra(EXTRA_IMAGE_URLS);//要展示的数据集合
        urlcontent=getIntent().getStringArrayListExtra(EXTRA_IMAGE_URLS_CONTENT);//图片对应的详情
        mAdapter = new ImagePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mAdapter);

         //默认第一张的简介
        tvTitle.setText(urlcontent.get(0));

        CharSequence text = getString(R.string.viewpager_indicator, 1, mPager.getAdapter().getCount());
        if (urls.size() == 1) {
            indicator.setVisibility(View.GONE);
        } else {
            indicator.setVisibility(View.VISIBLE);
        }
        indicator.setText(text);

        tvPosition.setText("1.");


        if (!is_need_download) {
            downloadTv.setVisibility(View.GONE);
        } else {
            downloadTv.setOnClickListener(v -> {
//                RxPermissions rxPermissions = new RxPermissions(ImagePagerActivity.this); // where this is an Activity instance
//                rxPermissions
//                        .request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                        .subscribe(granted -> {
//                            if (granted) {
                download();
//                            } else {
//                                JumpPermissionManagement.GoToSetting(ImagePagerActivity.this);
//                            }
//                        });
            });
        }


        // 更新下标
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int arg0) {

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageSelected(int arg0) {
                CharSequence text = getString(R.string.viewpager_indicator, arg0 + 1, mPager.getAdapter().getCount());
                indicator.setText(text);
                tvTitle.setText(urlcontent.get(arg0));
                tvPosition.setText(String.valueOf(arg0+1)+".");
            }
        });

        mPager.setCurrentItem(pagerPosition);
    }


    @Override
    public BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTranslucentForImageView(this, 0, mPager);
    }


    @Override
    protected FrameLayout getBody() {
        return null;
    }


    @Override
    public boolean getisUseAutoLayout() {
        return false;
    }

    @Override
    public void onErrorFail(int error, View.OnClickListener onclickListener) {

    }

    @Override
    public void onErrorFail(int error, String errorStr, View.OnClickListener onclickListener) {

    }

    @Override
    public void goToLoginActivity() {

    }

    @Override
    public void checkPer(Activity context, OnGrantCallBack callBack, String... permissions) {

    }

    @Override
    public void checkPer(Activity context, OnPermissionCallBack callBack, String... permissions) {

    }


    /**
     * Adapter
     */
    private class ImagePagerAdapter extends FragmentStatePagerAdapter {
        public ImagePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return urls.size();
        }

        @Override
        public Fragment getItem(int position) {
            String url = urls.get(position);
            return ImageDetailFragment.newInstance(url, "");
        }
    }

    private void download() {
        Dialog.showProgressingDialog(getContext(), getContext().getString(R.string.saving));
        String url = urls.get(mPager.getCurrentItem());
        okHttpDownloadPic(getApplicationContext(), url);
    }


    public static void okHttpDownloadPic(Context context, String url) {
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new FileCallBack(context.getCacheDir().getAbsolutePath(), System.currentTimeMillis() + ".png") {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 if (context == null) {
                                     return;
                                 }
                                 if(context!=null) {
                                     Dialog.dismissProgressDialog();
                                 }
                                 Toasts.show(context.getApplicationContext(), null, context.getString(R.string.download_failure));
                             }

                             @Override
                             public void onResponse(File response, int id) {
                                 if (context == null) {
                                     return;
                                 }
                                 new Thread(() -> {
                                     try {
                                         FileInputStream fileInputStream = new FileInputStream(response.getAbsolutePath());
                                         byte[] buffer = new byte[1024];

                                         File picFile = FileUtils.getPicFile(context);

                                         FileOutputStream fileOutputStream = new FileOutputStream(picFile);

                                         while (fileInputStream.read(buffer) != -1) {
                                             fileOutputStream.write(buffer);
                                         }

                                         fileInputStream.close();
                                         fileOutputStream.close();
                                         context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + picFile.getAbsolutePath())));

                                     } catch (FileNotFoundException e) {
                                         e.printStackTrace();
                                     } catch (IOException e) {
                                         e.printStackTrace();
                                     }

                                 }).start();
                                 Dialog.dismissProgressDialog();
                                 Toast.makeText(context.getApplicationContext(), context.getString(R.string.picture_has_saved), Toast.LENGTH_SHORT).show();
                             }

                             @Override
                             public void inProgress(float progress, long total, int id) {
                                 super.inProgress(progress, total, id);


                             }
                         }
                );
    }


    @Override
    protected void initButterKnife() {

    }

    @Override
    protected void destroyButterKnife() {

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {


            BaseApp.getInstance().removeActivity(this);
            finish();
            return false;
        }
        return false;
    }

}
