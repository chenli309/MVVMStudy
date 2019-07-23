package com.lee.mvvmdemo.vm;

import com.lee.mvvmdemo.utils.ToastUtils;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

public abstract class OnVMHttpCallback<T> implements OnHandleCallback<T> {
    @Override
    public void onLoading() {
//        showToast("开始请求...");
    }

    @Override
    public void onError(Throwable e) {
//        if (!SystemUtils.isNetWorkActive(getContext())) {
//            showToast(R.string.result_network_unavailable_error);
//            return;
//        }
//        if (e instanceof ConnectException) {
//            showToast(R.string.result_connect_failed_error);
//        } else if (e instanceof SocketTimeoutException) {
//            showToast(R.string.result_connect_timeout_error);
//        } else {
//            showToast(R.string.result_empty_error);
//        }
    }

    @Override
    public void onFailure(String msg) {
//        showToast(msg);
    }

    @Override
    public void onCompleted() {
//        showToast("结束请求...");
    }
}
