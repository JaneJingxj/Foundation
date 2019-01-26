package com.quansu.utils.windowsbase;


import android.content.SharedPreferences;
import android.os.Build;

import com.quansu.BaseApp;
import com.quansu.cons.Constants;
import com.quansu.utils.PackageUtils;
import com.readystatesoftware.chuck.ChuckInterceptor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xianguangjin on 15/12/14.
 */

public class NetEngine {

    private static Retrofit retrofit;
    private static OkHttpClient client = null;

    public static String Url1 = Constants.BASE_URL;

    public static ApiService getService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Url1)
                    .client(getClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }

        return retrofit.create(ApiService.class);
    }

    public static Retrofit initRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Url1)
                    .client(getClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }

        return retrofit;
    }


    public static OkHttpClient getClient() {
        if (client == null) {
            int versionCode = PackageUtils.getAppVersionCode(BaseApp.getInstance().getApplicationContext());
            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .retryOnConnectionFailure(false)
                    .addInterceptor(new ChuckInterceptor(BaseApp.getInstance().getApplicationContext()))
                    .readTimeout(18676, TimeUnit.MILLISECONDS)
                    .writeTimeout(18676, TimeUnit.MILLISECONDS)
                    .connectTimeout(15676, TimeUnit.MILLISECONDS)
                    .addInterceptor(new LoggingInterceptor())
                    .cookieJar(new CookieJar() {
                        @Override
                        public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
                            //保存cookie
                            SharedPreferences session = BaseApp.getInstance().getApplicationContext().getSharedPreferences("session", 0);
                            for (Cookie cookie : list) {
                                session.edit().putString(cookie.name(), cookie.toString()).apply();
                            }
                        }

                        @Override
                        public List<Cookie> loadForRequest(HttpUrl httpUrl) {
                            //读取cookie
                            List<Cookie> cookies = new ArrayList<>();

                            SharedPreferences session = BaseApp.getInstance().getApplicationContext().getSharedPreferences("session", 0);
                            Map<String, String> all = (Map<String, String>) session.getAll();

                            for (Map.Entry<String, String> stringStringEntry : all.entrySet()) {
                                Cookie cookie = Cookie.parse(httpUrl, stringStringEntry.getValue());


                                if ((!httpUrl.host().equals(Constants.BASE_HOST) || !httpUrl.host().equals(Constants.BASE_HOST_FANGWEI)) && httpUrl.host().contains("quansuwangluo.com") && (cookie.name().equals("SERVERID") || cookie.name().equals("PHPSESSID"))) {
                                    continue;
                                }

                                cookies.add(cookie);
                            }
                            return cookies;
                        }
                    })
                    .addInterceptor(chain -> {
                        Request request = chain.request().newBuilder()
                                .addHeader("User-Agent", "type/android;app_version_code/" + versionCode + ";app_version_name/" + PackageUtils.getAppVersionName(BaseApp.getInstance().getApplicationContext()) + ";phone_brand/" + Build.BRAND + ";phone_model/" + Build.MODEL + ";system_version_code/" + Build.VERSION.SDK_INT + ";system_version_name/" + Build.VERSION.RELEASE)
                                .build();
                        return chain.proceed(request);
                    });

            return client = builder.build();
        }
        return client;
    }


    public static void changeApiBaseUrl(String newApiBaseUrl) {
        Url1 = newApiBaseUrl;

        retrofit = new Retrofit.Builder()
                .baseUrl(Url1)
                .client(getClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    static class LoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            //这个chain里面包含了request和response，所以你要什么都可以从这里拿
            Request request = chain.request();
            long t1 = System.nanoTime();//请求发起的时间

            String method = request.method();
            if ("POST".equals(method)) {
                StringBuilder sb = new StringBuilder();
                if (request.body() instanceof FormBody) {
                    FormBody body = (FormBody) request.body();
                    for (int i = 0; i < body.size(); i++) {
                        sb.append(body.encodedName(i) + "=" + body.encodedValue(i) + ",");
                    }
                    sb.delete(sb.length() - 1, sb.length());
//                    Log.e("zzy",String.format("发送请求 %s on %s %n%s %nRequestParams:{%s}",  request.url(), chain.connection(), request.headers(), sb.toString()));
                }
            } else {
                //Log.e("zzy",String.format("发送请求 %s on %s%n%s", request.url(), chain.connection(), request.headers()));
            }
            Response response = chain.proceed(request);
            long t2 = System.nanoTime();//收到响应的时间
            //这里不能直接使用response.body().string()的方式输出日志
            //因为response.body().string()之后，response中的流会被关闭，程序会报错，我们需要创建出一
            //个新的response给应用层处理
            ResponseBody responseBody = response.peekBody(1024 * 1024);
//            Log.e("zzy",
//                    String.format("接收响应: [%s] %n返回json:【%s】 %.1fms %n%s",
//                            response.request().url(),
//                            responseBody.string(),
//                            (t2 - t1) / 1e6d,
//                            response.headers()
//                    ));
            return response;
        }
    }
}
