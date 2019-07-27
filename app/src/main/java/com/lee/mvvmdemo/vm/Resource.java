package com.lee.mvvmdemo.vm;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lee.mvvmdemo.utils.ExceptionUtil;

import retrofit2.Response;

public class Resource<T> {
    @HttpStatus
    public int status;

    public int code;

    @Nullable
    public String message;

    @Nullable
    public T data;

    public Throwable error;

    public String url;

    public Resource(@HttpStatus int status, @Nullable T data, int code, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.code = code;
        this.message = message;
    }

    public Resource(@HttpStatus int status, Throwable t) {
        this.status = status;
        this.code = -999;
        this.error = t;
    }

    public Resource(@HttpStatus int status, int code, Throwable t) {
        this.status = status;
        this.code = code;
        this.error = t;
    }

    public Resource(Response<T> response, int status) {
        this(status, response.body(), response.code(), response.message());
    }

    public static <T> Resource<T> loading() {
        return new Resource<>(HttpStatus.LOADING, null, HttpStatus.LOADING, null);
    }

    public static <T> Resource<T> loading(@Nullable T data) {
        return new Resource<>(HttpStatus.LOADING, data, HttpStatus.LOADING, null);
    }

//    public static <T> Resource<T> success(@Nullable T data) {
//        return new Resource<>(HttpStatus.SUCCESS, data, HttpStatus.SUCCESS, null);
//    }

    public static <T> Resource<T> success(T t) {
        return new Resource<>(HttpStatus.SUCCESS, t, HttpStatus.SUCCESS, "OK");
    }

    public static <T> Resource<T> success(Response<T> response) {
        return new Resource<>(response, HttpStatus.SUCCESS);
    }

    public static <T> Resource<T> response(@NonNull Response<T> data) {
        if (data.isSuccessful()) {
            if (data.body() == null) {
                return Resource.empty(data.code());
            } else {
                return Resource.success(data);
            }
        } else {
            return Resource.error(data.code());
        }
    }

//    public static <T> Resource<T> response(@Nullable BaseResult<T> data) {
//        if (data != null) {
//            if (data.isSuccess()) {
//                return new Resource<>(HttpStatus.SUCCESS, data.getData(), null);
//            }
//            return new Resource<>(HttpStatus.FAILURE, null, data.getErrorMsg());
//        }
//        return new Resource<>(HttpStatus.ERROR, null, null);
//    }

    public static <T> Resource<T> failure(String msg) {
        return new Resource<>(HttpStatus.FAILURE, null, HttpStatus.FAILURE, msg);
    }

    public static <T> Resource<T> empty(int code) {
        return new Resource<>(HttpStatus.EMPTY, null, code, "暂无数据");
    }

    public static <T> Resource<T> empty(String msg) {
        return new Resource<>(HttpStatus.EMPTY, null, HttpStatus.EMPTY, !TextUtils.isEmpty(msg) ? msg : "暂无数据");
    }

    public static <T> Resource<T> empty() {
        return empty(HttpStatus.EMPTY);
    }

    public static <T> Resource<T> error(int errorCode) {
        return error(ExceptionUtil.getResponseExceptionByErrorCode(errorCode));
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
