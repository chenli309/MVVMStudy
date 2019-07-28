package com.lee.mvvmdemo.vm.impl;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.lee.mvvmdemo.entity.CategoryResult;
import com.lee.mvvmdemo.vm.BaseViewModel;
import com.lee.mvvmdemo.vm.DataRepository;
import com.lee.mvvmdemo.vm.Resource;

public class CategoryViewModel extends BaseViewModel<DataRepository> {

    public CategoryViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public DataRepository getRepository() {
        return DataRepository.getInstance();
    }

    public LiveData<Resource<CategoryResult>> getCategory(String category, int number, int curPage) {
        return getRepository().getCategory(category, number, curPage);
    }
}
