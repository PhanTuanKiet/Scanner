package com.datviet.network;

public interface RequestListener {
    void onStartRequest();
    void onResponse(String response);
    void onError();
}
