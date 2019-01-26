package com.quansu.utils.inter;

public interface IWebsocket {

    void onMessage(String msg);

    void onClose();

}
