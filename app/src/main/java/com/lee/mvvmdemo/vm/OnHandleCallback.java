package com.lee.mvvmdemo.vm;

public interface OnHandleCallback<T> {
    void onLoading();

    void onSuccess(T data);

    void onFailure(String msg);

    void onError(Throwable error);

    void onCompleted();
}
