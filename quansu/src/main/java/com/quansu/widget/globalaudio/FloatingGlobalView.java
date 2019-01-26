package com.quansu.widget.globalaudio;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.quansu.BaseApp;
import com.quansu.cons.Constants;
import com.quansu.ui.mvp.model.AudioSaveNews;
import com.quansu.utils.Msg;
import com.quansu.utils.glide.GlideUtils;
import com.quansu.utils.windowsbase.MSG;
import com.quansu.utils.windowsbase.NetEngine;
import com.ysnows.quansu.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

import butterknife.OnTouch;
import okhttp3.Call;

import static com.quansu.utils.RxBus.getDefault;

public class FloatingGlobalView extends LinearLayout  {
    public static final String TAG = "FloatingView";
    private final Context mContext;
   // private final WindowManager mWindowManager;
    private FrameLayout audioSwitch;
    private ImageView audioBackground;
    private ImageView audioIv;
    private TextView audioTitle;
    private TextView audioTime;
    private TextView audioAuthor;
    private ImageView audioClose;
    //private ImageView audiodetail;
    private LinearLayout llDetail;
    private RoundProgressBar progressBar;
    private AudioSaveNews audioSaveNews;

    private boolean isplay = true;
    private MediaPlayer player = null;
    private boolean isclose = true;
    SimpleDateFormat time = new SimpleDateFormat("mm:ss");
    private int currentPosition;
    private int duration;

    private LinearLayout linear;
    private LinearLayout ll;


    public FloatingGlobalView(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 0:

                    if (isclose) {

                        if (player != null) {
                            currentPosition = player.getCurrentPosition();

                            if (currentPosition / 1000 >= duration / 1000) {
                                pauseAudio();
                                return;
                            }
                            progressBar.setValue(currentPosition);
                        }


                        handler.sendEmptyMessageDelayed(0, 1000);

                    }


                    break;

                case 2://出现关闭按钮

                    audioClose.setVisibility(View.VISIBLE);
                    break;
                case 3://隐藏关闭按钮
                    audioClose.setVisibility(View.GONE);
                    break;


            }

        }
    };


    @SuppressLint("WrongViewCast")
    private void initView() {

        inflate(mContext, R.layout.audio_dialog_view, this);
        this.audioSaveNews = BaseApp.getInstance().audioSaveNews;

        player = BaseApp.getInstance().audioSaveNews.audioplayer;

        duration = BaseApp.getInstance().audioSaveNews.ztime;
        linear=findViewById(R.id.linear);
        llDetail = findViewById(R.id.ll_detail);
        ll=findViewById(R.id.ll);

        audioSwitch = findViewById(R.id.audio_switch);
        audioBackground = findViewById(R.id.audio_background);

        audioIv = findViewById(R.id.audio_iv);
        audioTitle = findViewById(R.id.audio_title);
        audioTime = findViewById(R.id.audio_time);
        audioAuthor = findViewById(R.id.audio_author);
        audioClose = findViewById(R.id.audio_close);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setMax(duration);
        progressBar.setMax(duration);
        if (audioSaveNews.isplay) {
            isplay = true;
            audioIv.setImageResource(R.drawable.audio_pause);
            handler.sendEmptyMessage(3);
        } else {
            isplay = false;
            audioIv.setImageResource(R.drawable.audio_play);
            handler.sendEmptyMessage(2);

        }

        GlideUtils.lImg(mContext, audioSaveNews.image, audioBackground, false);

        audioTitle.setText(audioSaveNews.tiltle);
        if (TextUtils.isEmpty(audioSaveNews.author) || audioSaveNews.author.equals("0")) {
            audioAuthor.setText("");
        } else {
            audioAuthor.setText(audioSaveNews.author);
        }
        audioTime.setText(time.format(audioSaveNews.ztime));


        audioClose.setOnClickListener(v -> {

            linear.setVisibility(GONE);
            BaseApp.getInstance().isaudio = false;
            setAudioClose();



        });


        audioSwitch.setOnClickListener(v -> {
            if (isplay) {
                BaseApp.getInstance().audioSaveNews.isplay = false;
                pauseAudio();
                //保存进度
                setSaveAudio();
                audioIv.setImageResource(R.drawable.audio_play);
                handler.sendEmptyMessage(2);

            } else {
                BaseApp.getInstance().audioSaveNews.isplay = true;
                playAudio();
                audioIv.setImageResource(R.drawable.audio_pause);
                handler.sendEmptyMessage(3);
            }

        });

        llDetail.setOnClickListener(v -> {
            Log.e("--shy-", "执行了");

            try {
                isclose = false;
                handler.removeCallbacksAndMessages(null);
                jumpClass();
                dismiss();

            } catch (Exception e) {
                Log.e("--shy-", "错误了");
            }
        });

        player.setOnCompletionListener(mp -> {
            //播放完毕
            Log.e("--66666-", "执行完了");

            //停止动画

            BaseApp.getInstance().audioSaveNews.isplay = false;
            pauseAudio();
            //progressBar.setProgress(duration);
            progressBar.setValue(duration);
            currentPosition = duration;
            audioIv.setImageResource(R.drawable.audio_play);
            handler.sendEmptyMessage(2);
            isclose = false;
            isplay = false;

        });

        handler.sendEmptyMessage(0);


        DisplayMetrics dm = getResources().getDisplayMetrics();

        linear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    /**
     * 跳转到详情页面
     */
    public void jumpClass() {
        if (audioSaveNews.typeclass == 1) {


            getDefault().post(new Msg(MSG.AUDIOCHOSE, audioSaveNews,"1"));




//            Intent intent = new Intent(mContext, ArticleAndCommentDetialActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            Bundle bundle = new Bundle();
//            bundle.putString(audioSaveNews.intentname, audioSaveNews.intentvalue);
//            bundle.putString("style", "1");
//            intent.putExtras(bundle);
//            mContext.startActivity(intent);




        } else if (audioSaveNews.typeclass == 2) {

            getDefault().post(new Msg(MSG.AUDIOCHOSE, audioSaveNews,"2"));


//            Intent intent = new Intent(mContext, ArticleDetailActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            Bundle bundle = new Bundle();
//            bundle.putString(audioSaveNews.intentname, audioSaveNews.intentvalue);
//            bundle.putString("style", "1");
//            intent.putExtras(bundle);
//            mContext.startActivity(intent);
        } else if (audioSaveNews.typeclass == 3) {

            getDefault().post(new Msg(MSG.AUDIOCHOSE, audioSaveNews,"3"));


//
//            Intent intent = new Intent(mContext, TrainCourseNewsActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            Bundle bundle = new Bundle();
//            //bundle.putString(audioSaveNews.intentname, audioSaveNews.intentvalue);
//            bundle.putString("zhuanti_id",audioSaveNews.type);
//            bundle.putString("style", "1");
//            bundle.putString("type", "trainCourse");
//            bundle.putString("folat","1");
//            intent.putExtras(bundle);
//            mContext.startActivity(intent);
        }


    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }




    /**
     * 播放音频
     */
    public void playAudio() {
        if (player != null) {
            isclose = true;
            isplay = true;
            player.start();
            handler.sendEmptyMessage(0);
        }
    }

    /**
     * 暂停播放音频
     */
    public void pauseAudio() {
        if (player != null) {
            player.pause();
            isplay = false;
            isclose = false;
            handler.removeCallbacksAndMessages(null);
        }
    }


    /**
     * 停止播放音频
     */
    public void stopAudio() {
        if (player != null) {
            player.stop();
            isclose = false;
            handler.removeCallbacksAndMessages(null);
        }
    }


    /**
     * 悬浮框消失
     */
    public void dismiss() {

       linear.setVisibility(GONE);

    }


    /**
     * 单纯的保存进度
     */
    public void setSaveAudio() {

        if (audioSaveNews.typeclass == 1) {

            //保存专栏进度
            if (currentPosition == audioSaveNews.ztime) {
                putPlayLesson(audioSaveNews.intentvalue, "0", "1");

                return;
            }
            putPlayLesson(audioSaveNews.intentvalue, String.valueOf(currentPosition / 1000), "0");

        } else if (audioSaveNews.typeclass == 2) {
            //保存播放进度
            if (currentPosition == audioSaveNews.ztime) {
                getData(audioSaveNews.intentvalue, "0", "1");
                return;
            }
            getData(audioSaveNews.intentvalue, String.valueOf(currentPosition / 1000), "0");


        } else if (audioSaveNews.typeclass == 3) {
            //保存课程的进度

            putPlayInfo(audioSaveNews.type, audioSaveNews.intentvalue, currentPosition / 1000);

        }


    }


    /**
     * 关闭悬浮窗--保存播放进度
     */
    public void setAudioClose() {

        if (audioSaveNews.typeclass == 1) {
            //专栏进度

            if (currentPosition == audioSaveNews.ztime) {
                putPlayLesson(audioSaveNews.intentvalue, "0", "1");
                dismiss();
                return;
            }
            putPlayLesson(audioSaveNews.intentvalue, String.valueOf(currentPosition / 1000), "0");
            dismiss();


        } else if (audioSaveNews.typeclass == 2) {
            //保存播放进度
            if (currentPosition == audioSaveNews.ztime) {
                getData(audioSaveNews.intentvalue, "0", "1");
                dismiss();
                return;
            }
            getData(audioSaveNews.intentvalue, String.valueOf(currentPosition / 1000), "0");
            dismiss();

        } else if (audioSaveNews.typeclass == 3) {
            //保存课程的进度

            putPlayInfo(audioSaveNews.type, audioSaveNews.intentvalue, currentPosition / 1000);
            dismiss();
        }

    }


    public void getData(String article_id, String value, String del) {
        //请求数据

        OkHttpUtils
                .post()
                .url(Constants.BASE_URL + "Article/play_log")
                .addParams("article_id", article_id)
                .addParams("value", value)
                .addParams("del", del)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        // MYNCLoginInfo myncLoginInfo = new Gson().fromJson(response, MYNCLoginInfo.class);

                        Log.e("---", "ss=" + response);

                        try {
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });


    }


    /**
     * @param zhuanti_id 课程id
     * @param course_id
     * @param currentPosition
     */
    public void putPlayInfo(String zhuanti_id, String course_id, int currentPosition) {


        OkHttpUtils okHttpUtils = new OkHttpUtils(NetEngine.getClient());
        OkHttpUtils
                .post()
                .url(Constants.BASE_URL + "Course/play_log")
                .addParams("zhuanti_id", zhuanti_id)
                .addParams("course_id", course_id)
                .addParams("value", String.valueOf(currentPosition))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }
                    @Override
                    public void onResponse(String response, int id) {
                        // MYNCLoginInfo myncLoginInfo = new Gson().fromJson(response, MYNCLoginInfo.class);

                        Log.e("---", "ss=" + response);

                        try {
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }


    public void putPlayLesson(String lesson_id, String value, String del) {
        OkHttpUtils okHttpUtils = new OkHttpUtils(NetEngine.getClient());
        OkHttpUtils
                .post()
                .url(Constants.BASE_URL + "Lessons/play_log")
                .addParams("lesson_id", lesson_id)
                .addParams("value", value)
                .addParams("del", del)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        // MYNCLoginInfo myncLoginInfo = new Gson().fromJson(response, MYNCLoginInfo.class);
                        Log.e("---", "ss=" + response);

                        try {
                            JSONObject obj = new JSONObject(response);
                            String status = obj.getString("status");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }




}
