package com.lee.mvvmdemo;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.lee.mvvmdemo.utils.AppUtil;

public class App extends Application {

    private static App app;

    public static App getInstance() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;

        Utils.init(this);
        AppUtil.init(this);
    }
}
