package com.quansu.common.inter;

import java.io.File;

import okhttp3.Call;


public class Task {

    public interface DataListener {
        public void onSuccess(String json, String from);

        public void onFail(Exception e);
    }

    public interface FileListener {
        public void onError(Call call, Exception e, int id);

        public void onResponse(File response, int id);

        public void inProgress(float progress, long total, int id);

    }

    public interface progressListner extends DataListener {
        public void inProgress(float progress, long total);
    }

}
