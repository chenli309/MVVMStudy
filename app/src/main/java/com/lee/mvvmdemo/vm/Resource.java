package com.lee.mvvmdemo.vm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Resource<T> {
    @HttpStatus
    public int status;

    @Nullable
    public String message;

    @Nullable
    public T data;

    public Throwable error;

    public Resource(@HttpStatus int status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public Resource(@HttpStatus int status, Throwable t) {
        this.status = status;
        this.error = t;
    }

    public static <T> Resource<T> loading() {
        return new Resource<>(HttpStatus.LOADING, null, null);
    }

    public static <T> Resource<T> loading(@Nullable T data) {
        return new Resource<>(HttpStatus.LOADING, data, null);
    }

    public static <T> Resource<T> success(@Nullable T data) {
        return new Resource<>(HttpStatus.SUCCESS, data, null);
    }

    public static <T> Resource<T> response(@Nullable BaseResult<T> data) {
        if (data != null) {
            if (data.isSuccess()) {
                return new Resource<>(HttpStatus.SUCCESS, data.getData(), null);
            }
            return new Resource<>(HttpStatus.FAILURE, null, data.getErrorMsg());
        }
        return new Resource<>(HttpStatus.ERROR, null, null);
    }

    public static <T> Resource<T> failure(String msg) {
        return new Resource<>(HttpStatus.FAILURE, null, msg);
    }

    public static <T> Resource<T> error(Throwable t) {
        return new Resource<>(HttpStatus.ERROR, t);
    }

    public void handle(@NonNull OnHandleCallback<T> callback) {
        switch (status) {
            case HttpStatus.LOADING:
                callback.onLoading();
                break;
            case HttpStatus.SUCCESS:
                callback.onSuccess(data);
                break;
            case HttpStatus.FAILURE:
                callback.onFailure(message);
                break;
            case HttpStatus.ERROR:
                callback.onError(error);
                break;
        }
        if (status != HttpStatus.LOADING) {
            callback.onCompleted();
        }
    }

    @Override
    public String toString() {
        return "Resource{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
