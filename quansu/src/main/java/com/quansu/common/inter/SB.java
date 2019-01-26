package com.quansu.common.inter;

import rx.Subscriber;

/**
 * Subscriber的封装类，打印一些信息等。。
 * Created by xianguangjin on 15/12/15.
 */
public abstract class SB<T> extends Subscriber<T> {



    @Override
    public void onNext(T t) {
        next(t);
    }

    @Override
    public void onError(Throwable e) {
        error(e);
    }


    @Override
    public void onCompleted() {
        completed();
    }

    public void completed() {

    }

    public void error(Throwable e) {

//        if (e is SocketTimeoutException || e is SocketException) {
//            view?.getContext()?.toast(R.string.time_out)
//        }

    }

    public abstract void next(T res);


}

