package com.example.pff;

import android.app.Application;
import android.content.res.Resources;
import android.content.res.Resources;


public class MyApp extends Application {
    private static MyApp mInstance;
    private static Resources res;


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        res = getResources();
    }

    public static MyApp getInstance() {
        return mInstance;
    }

    public static Resources getRes() {
        return res;
    }

}
