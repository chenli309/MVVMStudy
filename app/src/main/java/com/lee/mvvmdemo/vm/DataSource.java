package com.lee.mvvmdemo.vm;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

public class DataSource {
    private Context mContext;

    public DataSource(@NonNull Application application) {
        this.mContext = application;
    }
}
