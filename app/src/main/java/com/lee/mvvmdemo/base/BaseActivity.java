package com.lee.mvvmdemo.base;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.lee.mvvmdemo.R;
import com.lee.mvvmdemo.utils.SystemUtils;
import com.lee.mvvmdemo.utils.ToastUtils;
import com.lee.mvvmdemo.vm.BaseViewModel;
import com.lee.mvvmdemo.vm.OnHandleCallback;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

public abstract class BaseActivity<VM extends BaseViewModel> extends AppCompatActivity {

    protected VM viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = initViewModel();
        initObserve();
        setContentView(onCreate());
        initView();
        initData();
    }

    public Context getContext() {
        return this;
    }

    public Application getApp() {
        return getApplication();
    }

    /**
     * 初始化要加载的布局资源ID
     * 此函数优先执行于onCreate()可以做window操作
     */
    protected abstract int onCreate();

    protected abstract void initView();

    protected abstract void initData();

    /**
     * 初始化ViewModel
     */
    private VM initViewModel() {
        return ViewModelProviders.of(this).get(getViewModelClass());
    }

    protected abstract Class<VM> getViewModelClass();

    /**
     * 监听当前ViewModel中 showDialog和error的值
     */
    private void initObserve() {
//        if (viewModel == null) return;
//        viewModel.getShowDialog(this, new Observer<DialogBean>() {
//            @Override
//            public void onChanged(DialogBean bean) {
//                if (bean.isShow()) {
//                    showDialog(bean.getMsg());
//                } else {
//                    dismissDialog();
//                }
//            }
//        });
//        viewModel.getError(this, new Observer<Object>() {
//            @Override
//            public void onChanged(Object obj) {
//                showError(obj);
//            }
//        });
    }

    public void showToast(CharSequence text) {
        if (TextUtils.isEmpty(text)) {
            ToastUtils.showToast(getContext(), text);
        }
    }

    public void showToast(@StringRes int resId) {
        ToastUtils.showToast(getContext(), resId);
    }

    public abstract class OnHttpCallback<T> implements OnHandleCallback<T> {
        @Override
        public void onLoading() {
            showToast("开始请求...");
        }

        @Override
        public void onError(Throwable e) {
            if (!SystemUtils.isNetWorkActive(getContext())) {
                showToast(R.string.result_network_unavailable_error);
                return;
            }
            if (e instanceof ConnectException) {
                showToast(R.string.result_connect_failed_error);
            } else if (e instanceof SocketTimeoutException) {
                showToast(R.string.result_connect_timeout_error);
            } else {
                showToast(R.string.result_empty_error);
            }
        }

        @Override
        public void onFailure(String msg) {
            showToast(msg);
        }

        @Override
        public void onCompleted() {
            showToast("结束请求...");
        }
    }
}
