package com.quansu.common.inter;


import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by xianguangjin on 15/12/14.
 */


public abstract class BaseNetEngine {

    private static OkHttpClient client;

    private static Retrofit retrofit;

    public static void init(String BaseUrl) {
        if (retrofit == null) {
            if (client == null) {
                client = new OkHttpClient();
            }

            retrofit = new Retrofit.Builder()
                    .baseUrl(BaseUrl)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    //.addCallAdapterFactory(RxJava2CallAdapterFactory.create())

                    .build();
        }
    }

    public static OkHttpClient getClient() {
        return client;
    }

    public static Retrofit getRetrofit() {
        return retrofit;
    }
}

