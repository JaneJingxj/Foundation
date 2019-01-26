package com.quansu.ui.mvp.model;

import android.media.MediaPlayer;

/**
 * Created by Shi on 2018/1/10.
 * 音频保存的信息
 */

public class AudioSaveNews {

    /**
     *  audioplayer:音频的播放器
     * image:背景图片
     * tiltle:标题
     * author:作者
     * ztime:音频总的时间
     *isplay:是否是在播放
     */

    public MediaPlayer audioplayer;
    public String image;
    public String tiltle;
    public String author;
    public int ztime;
    public boolean isplay;//默认播放  为true的时候是播放
    public int typeclass;//要跳转到那个类中 1：ArticleAndCommentDetialActivity  2:ArticleDetailActivity 3:TrainCourseNewsActivity
    public String intentname;//要跳转页面的参数名
    public String intentvalue;//要跳转页面的参数值
    public String type;//课程里的  当typeclass=3的时候 type=zhuanti_id（专栏id）；


    public AudioSaveNews() {

    }

    public AudioSaveNews(MediaPlayer audioplayer, String image, String tiltle, String author,
                         int ztime, int typeclass, String intentname , String intentvalue, String type) {
        this.audioplayer=audioplayer;
        this.image = image;
        this.tiltle = tiltle;
        this.author = author;
        this.ztime = ztime;
        this.isplay=true;
        this.typeclass=typeclass;
        this.intentname=intentname;
        this.intentvalue=intentvalue;
        this.type=type;
    }
}
