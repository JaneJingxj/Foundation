package com.quansu.common.inter;

public class TaskPost {

    public interface DataListenerPost {
        public void onFailpost(Exception e, String from);

        public void onSuccesspost(String json, String from);
    }
}

