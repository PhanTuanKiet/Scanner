package com.datviet.scanner;

import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * Created by Phong Phan on 27-Oct-17.
 */

public class CoreApplication extends android.support.multidex.MultiDexApplication {

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
    }
}
