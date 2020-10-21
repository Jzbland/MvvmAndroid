package com.jzb.mvvmdemo;

import android.app.Application;

import com.jzb.mvvmdemo.util.LogUtil;

/**
 * created by jzb
 * on 2020/10/21
 **/
public class App extends Application {
    public static App app;

    @Override
    public void onCreate() {
        super.onCreate();
        app=this;
        LogUtil.isDebug=true;
    }
    public static App getApp() {
        return app;
    }
}
