package com.datviet.scanner;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.datviet.utils.Constant;
import com.datviet.utils.KEY;
import com.datviet.utils.SharedPreferenceUtil;


public class CoreApplication extends android.support.multidex.MultiDexApplication {
    public static final String TAG = CoreApplication.class.getSimpleName();
    private RequestQueue mRequestQueue;
    private static CoreApplication sInstance;

    public static CoreApplication getsInstance(){
        return sInstance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;

        boolean isRunned = SharedPreferenceUtil.getInstance().getBoolean(KEY.IS_RUN);
        if(!isRunned){
            initDefaultValue();
        }
    }

    private void initDefaultValue(){
        SharedPreferenceUtil.getInstance().saveBoolean(Constant.LOADING_IMAGE, true);
        SharedPreferenceUtil.getInstance().saveBoolean(Constant.SOUND, false);
        SharedPreferenceUtil.getInstance().saveBoolean(Constant.VIBRATE, false);
        SharedPreferenceUtil.getInstance().saveBoolean(KEY.IS_RUN, true);
    }


    public RequestQueue getRequestQueue(){
        if(mRequestQueue == null){
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }


    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
