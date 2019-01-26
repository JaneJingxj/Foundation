package com.quansu.ui.activity;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.quansu.common.mvp.BasePresenter;
import com.quansu.common.ui.BaseActivity;
import com.quansu.utils.FileUtils;
import com.quansu.utils.Toasts;
import com.quansu.utils.glide.GlideUtils;
import com.quansu.utils.permission.OnGrantCallBack;
import com.quansu.utils.permission.OnPermissionCallBack;
import com.quansu.widget.Dialog;
import com.quansu.widget.aboutimages.DragPhotoView;
import com.wang.avi.AVLoadingIndicatorView;
import com.ysnows.quansu.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class ImageDragActivity extends BaseActivity {


    private ViewPager mViewPager;
    private DragPhotoView[] mPhotoViews;
    private FrameLayout linearLayout;
    private TextView indicator;


    int mOriginLeft;
    int mOriginTop;
    int mOriginHeight;
    int mOriginWidth;
    int mOriginCenterX;
    int mOriginCenterY;
    private float mTargetHeight;
    private float mTargetWidth;
    private float mScaleX;
    private float mScaleY;
    private float mTranslationX;
    private float mTranslationY;

    public static final String EXTRA_IMAGE_INDEX = "image_index";
    public static final String EXTRA_IMAGE_URLS = "image_urls";
    public static final String EXTRA_IS_NEED_DOWNLOAD = "is_need_download";
    private int pagerPosition;

    ArrayList<String> urls;
    private boolean is_need_download = true;
    private ImageView downloadTv;


    @Override
    protected int provideContentViewId() {
        return R.layout.activity_image_drag;
    }

    @Override
    public void initListeners() {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void initThings(Bundle savedInstanceState) {
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_image_drag);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.BLACK);
        }


        pagerPosition = getIntent().getIntExtra(EXTRA_IMAGE_INDEX, 0);

        is_need_download = getIntent().getBooleanExtra(EXTRA_IS_NEED_DOWNLOAD, true);
        urls = getIntent().getStringArrayListExtra(EXTRA_IMAGE_URLS);


        initView();


        if (!is_need_download) {
            downloadTv.setVisibility(View.GONE);
        } else {
            downloadTv.setOnClickListener(v -> {
                download();
            });
        }


    }


    private void download() {
        Dialog.showProgressingDialog(getContext(), getContext().getString(R.string.saving));
        String url = urls.get(mViewPager.getCurrentItem());
        okHttpDownloadPic(getApplicationContext(), url);
    }

    private void initView() {

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        indicator = (TextView) findViewById(R.id.indicator);

        downloadTv = (ImageView) findViewById(R.id.img_down);
        linearLayout = (FrameLayout) findViewById(R.id.lay_load);

        mViewPager.setOffscreenPageLimit(1);
        mPhotoViews = new DragPhotoView[urls.size()];
//        linearLayout.setVisibility(View.VISIBLE);


        for (int i = 0; i < mPhotoViews.length; i++) {
            mPhotoViews[i] = (DragPhotoView) View.inflate(this, R.layout.item_viewpager, null);

            GlideUtils.lGifTo(getContext(), urls.get(i), mPhotoViews[i]); //lGif  lImg3 换成加载gif的



//            mPhotoViews[i].setImageResource(R.drawable.wugeng);


            mPhotoViews[i].setOnTapListener(new DragPhotoView.OnTapListener() {
                @Override
                public void onTap(DragPhotoView view) {
                    finishWithAnimation();
                }
            });

            mPhotoViews[i].setOnExitListener(new DragPhotoView.OnExitListener() {
                @Override
                public void onExit(DragPhotoView view, float x, float y, float w, float h) {
                    performExitAnimation(view, x, y, w, h);
                }
            });
        }

        mViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return urls.size();
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(mPhotoViews[position]);
                return mPhotoViews[position];
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mPhotoViews[position]);
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        });


        // 更新下标
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int arg0) {

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageSelected(int arg0) {
                CharSequence text = getString(R.string.viewpager_indicator, arg0 + 1, mViewPager.getAdapter().getCount());
                indicator.setText(text);
            }
        });

        CharSequence text = getString(R.string.viewpager_indicator, 1, mViewPager.getAdapter().getCount());
        if (urls.size() == 1) {
            indicator.setVisibility(View.GONE);
        } else {
            indicator.setVisibility(View.VISIBLE);
        }
        indicator.setText(text);

        //获得控件的高度和宽度
        mViewPager.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onGlobalLayout() {
                        mViewPager.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                        mOriginLeft = getIntent().getIntExtra("left", 0);
                        mOriginTop = getIntent().getIntExtra("top", 0);
                        mOriginHeight = getIntent().getIntExtra("height", 0);
                        mOriginWidth = getIntent().getIntExtra("width", 0);
                        mOriginCenterX = mOriginLeft + mOriginWidth / 2;
                        mOriginCenterY = mOriginTop + mOriginHeight / 2;

                        int[] location = new int[2];

                        final DragPhotoView photoView = mPhotoViews[0];
                        photoView.getLocationOnScreen(location);

                        mTargetHeight = (float) photoView.getHeight();
                        mTargetWidth = (float) photoView.getWidth();
                        mScaleX = (float) mOriginWidth / mTargetWidth;
                        mScaleY = (float) mOriginHeight / mTargetHeight;

                        float targetCenterX = location[0] + mTargetWidth / 2;
                        float targetCenterY = location[1] + mTargetHeight / 2;

                        mTranslationX = mOriginCenterX - targetCenterX;
                        mTranslationY = mOriginCenterY - targetCenterY;
                        photoView.setTranslationX(mTranslationX);
                        photoView.setTranslationY(mTranslationY);

                        photoView.setScaleX(mScaleX);
                        photoView.setScaleY(mScaleY);
                        performEnterAnimation();
                        for (int i = 0; i < mPhotoViews.length; i++) {

                            mPhotoViews[i].setMinScale(mScaleX);

                        }


                    }
                });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                mViewPager.setCurrentItem(pagerPosition, false);

                linearLayout.setVisibility(View.GONE);

            }
        }, 1000);


    }


    private void performExitAnimation(final DragPhotoView view, float x, float y, float w, float h) {
        view.finishAnimationCallBack();
        float viewX = mTargetWidth / 2 + x - mTargetWidth * mScaleX / 2;
        float viewY = mTargetHeight / 2 + y - mTargetHeight * mScaleY / 2;
        view.setX(viewX);
        view.setY(viewY);

        float centerX = view.getX() + mOriginWidth / 2;
        float centerY = view.getY() + mOriginHeight / 2;

        float translateX = mOriginCenterX - centerX;
        float translateY = mOriginCenterY - centerY;


        ValueAnimator translateXAnimator = ValueAnimator.ofFloat(view.getX(), view.getX() + translateX);
        translateXAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                view.setX((Float) valueAnimator.getAnimatedValue());
            }
        });
        translateXAnimator.setDuration(300);
        translateXAnimator.start();
        ValueAnimator translateYAnimator = ValueAnimator.ofFloat(view.getY(), view.getY() + translateY);
        translateYAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                view.setY((Float) valueAnimator.getAnimatedValue());
            }
        });
        translateYAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                animator.removeAllListeners();
                finish();
                overridePendingTransition(0, 0);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        translateYAnimator.setDuration(300);
        translateYAnimator.start();
    }


    private void finishWithAnimation() {

        final DragPhotoView photoView = mPhotoViews[0];
        ValueAnimator translateXAnimator = ValueAnimator.ofFloat(0, mTranslationX);
        translateXAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                photoView.setX((Float) valueAnimator.getAnimatedValue());
            }
        });
        translateXAnimator.setDuration(300);
        translateXAnimator.start();

        ValueAnimator translateYAnimator = ValueAnimator.ofFloat(0, mTranslationY);
        translateYAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                photoView.setY((Float) valueAnimator.getAnimatedValue());
            }
        });
        translateYAnimator.setDuration(300);
        translateYAnimator.start();

        ValueAnimator scaleYAnimator = ValueAnimator.ofFloat(1, mScaleY);
        scaleYAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                photoView.setScaleY((Float) valueAnimator.getAnimatedValue());
            }
        });
        scaleYAnimator.setDuration(300);
        scaleYAnimator.start();

        ValueAnimator scaleXAnimator = ValueAnimator.ofFloat(1, mScaleX);
        scaleXAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                photoView.setScaleX((Float) valueAnimator.getAnimatedValue());
            }
        });

        scaleXAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                animator.removeAllListeners();
                finish();
                overridePendingTransition(0, 0);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        scaleXAnimator.setDuration(300);
        scaleXAnimator.start();
    }

    private void performEnterAnimation() {
        final DragPhotoView photoView = mPhotoViews[0];
        ValueAnimator translateXAnimator = ValueAnimator.ofFloat(photoView.getX(), 0);
        translateXAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                photoView.setX((Float) valueAnimator.getAnimatedValue());
            }
        });
        translateXAnimator.setDuration(300);
        translateXAnimator.start();

        ValueAnimator translateYAnimator = ValueAnimator.ofFloat(photoView.getY(), 0);
        translateYAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                photoView.setY((Float) valueAnimator.getAnimatedValue());
            }
        });
        translateYAnimator.setDuration(300);
        translateYAnimator.start();

        ValueAnimator scaleYAnimator = ValueAnimator.ofFloat(mScaleY, 1);
        scaleYAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                photoView.setScaleY((Float) valueAnimator.getAnimatedValue());
            }
        });
        scaleYAnimator.setDuration(300);
        scaleYAnimator.start();

        ValueAnimator scaleXAnimator = ValueAnimator.ofFloat(mScaleX, 1);
        scaleXAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                photoView.setScaleX((Float) valueAnimator.getAnimatedValue());
            }
        });
        scaleXAnimator.setDuration(300);
        scaleXAnimator.start();
    }


    public static void okHttpDownloadPic(Context context, String url) {

        if (url.contains("http") || url.contains("https")) {

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

                                     Dialog.dismissProgressDialog();
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
                                     if(context!=null) {
                                         Dialog.dismissProgressDialog();
                                     }
                                     Toast.makeText(context.getApplicationContext(), context.getString(R.string.picture_has_saved), Toast.LENGTH_SHORT).show();
                                 }

                                 @Override
                                 public void inProgress(float progress, long total, int id) {
                                     super.inProgress(progress, total, id);


                                 }
                             }
                    );


        } else {

            if(context!=null) {
                Dialog.dismissProgressDialog();
            }
            Toasts.toast(context, context.getString(R.string.download_failure));

        }


    }


    @Override
    public BasePresenter createPresenter() {
        return null;
    }


    @Override
    protected void setStatusBar() {

    }

    @Override
    protected ViewGroup getBody() {
        return null;
    }

    @Override
    protected void initButterKnife() {

    }

    @Override
    protected void destroyButterKnife() {

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
}
