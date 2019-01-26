package com.quansu.utils;

/**
 * Created by xianguangjin on 2016/9/27.
 * <p>
 * 我的GitHub: https://github.com/ysnows
 * <p>
 * 加油,做一个真的汉子
 */

public class Msg {
    public int msgId;
    public String title;
    public String subTitle;
    public Object content;


    public int from;
    public int to;
    public Object subContent;
    public int position;
    public String parmer;

    public Msg(int msgId, Object content) {
        this.msgId = msgId;
        this.content = content;
    }

    public Msg(int msgId, String title) {
        this.msgId = msgId;
        this.title = title;
    }

    public Msg(int msgId, String title, String subTitle) {
        this.msgId = msgId;
        this.title = title;
        this.subTitle = subTitle;
    }

    public Msg(int msgId, String title, int position) {
        this.msgId = msgId;
        this.title = title;
        this.position = position;
    }

    public Msg(Object content, int msgId, int to) {
        this.content = content;
        this.msgId = msgId;
        this.to = to;
    }

    public Msg(int msgId, Object content, String title) {
        this.msgId = msgId;
        this.content = content;
        this.title = title;
    }

    public Msg(int msgId, String title, String subTitle, Object content) {
        this.msgId = msgId;
        this.title = title;
        this.subTitle = subTitle;
        this.content = content;
    }


    public Msg(int msgId, String title, String subTitle, Object content, int to ) {
        this.msgId = msgId;
        this.title = title;
        this.subTitle = subTitle;
        this.content = content;
        this.to = to;

    }

    public Msg(int msgId, String title, String subTitle, Object content, int to,String parmer ) {
        this.msgId = msgId;
        this.title = title;
        this.subTitle = subTitle;
        this.content = content;
        this.to = to;
        this.parmer=parmer;

    }

    public Msg(int msgId, String title, Object content, Object subContent) {
        this.msgId = msgId;
        this.title = title;
        this.content = content;
        this.subContent = subContent;
    }



    public Msg(int msgId, String title, String subTitle, Object content, Object subContent) {
        this.msgId = msgId;
        this.title = title;
        this.subTitle = subTitle;
        this.content = content;
        this.subContent = subContent;
    }

    public Msg(int msgId, String title, int from, int to, String subTitle) {
        this.msgId = msgId;
        this.title = title;
        this.from = from;
        this.to = to;
        this.subTitle = subTitle;
    }

   /* public Msg(int msgId, int position,Object content){
        this.msgId = msgId;
        this.position = position;
        this.content = content;
    }*/


}
