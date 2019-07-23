package com.lee.mvvmdemo.vm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BaseViewModel<T extends BaseRepository> extends AndroidViewModel {

    private T repository;

    public BaseViewModel(@NonNull Application application, T repository) {
        super(application);
        this.repository = repository;
    }

    public Application getApp() {
        return getApplication();
    }

    public T getRepository() {
        return repository;
    }

    /**
     * 管理RxJava请求
     */
    private CompositeDisposable compositeDisposable;

    /**
     * 添加 rxJava 发出的请求
     */
    protected void addDisposable(Disposable disposable) {
        if (compositeDisposable == null || compositeDisposable.isDisposed()) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    /**
     * ViewModel销毁同时也取消请求
     */
    @Override
    protected void onCleared() {
        super.onCleared();
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
            compositeDisposable = null;
        }
    }

//    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
//
//    public BaseViewModel() {
//        App.inject(this);
//    }
//
//    @Override
//    public void onAttach() {
//    }
//
//    @Override
//    public void onDetach() {
//    }
//
//    public final void onViewAttachedToWindow(View view) {
//        onAttach();
//    }
//
//    public final void onViewDetachedFromWindow(View view) {
//        compositeDisposable.clear();
//        onDetach();
//    }
//
//    protected void addToAutoDispose(Disposable... disposables) {
//        compositeDisposable.addAll(disposables);
//    }
}
