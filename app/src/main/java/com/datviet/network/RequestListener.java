package com.datviet.network;

/**
 * Created by Phong Phan on 11-Nov-17.
 */

public interface RequestListener {
    void onStartRequest();
    void onResponse(String response);
    void onError();
}
