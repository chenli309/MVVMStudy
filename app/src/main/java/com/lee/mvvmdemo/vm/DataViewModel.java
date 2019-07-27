package com.lee.mvvmdemo.vm;

import android.app.Application;

import androidx.annotation.NonNull;

public class DataViewModel extends BaseViewModel<DataRepository> {
    public DataViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public DataRepository getRepository() {
        return null;
    }
//    public DataViewModel(@NonNull Application application, DataRepository repository) {
//        super(application, repository);
//    }
}
