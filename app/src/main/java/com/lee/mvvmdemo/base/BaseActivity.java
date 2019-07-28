package com.lee.mvvmdemo.base;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.ColorInt;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.lee.mvvmdemo.R;
import com.lee.mvvmdemo.dialog.ProgressDialogHelper;
import com.lee.mvvmdemo.entity.RequestPageBean;
import com.lee.mvvmdemo.utils.SystemUtils;
import com.lee.mvvmdemo.utils.ToastUtils;
import com.lee.mvvmdemo.view.VZTitleView;
import com.lee.mvvmdemo.vm.BaseViewModel;
import com.lee.mvvmdemo.vm.OnHandleCallback;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

public abstract class BaseActivity<VM extends BaseViewModel, DB extends ViewDataBinding> extends AppCompatActivity {

    protected VM viewModel;
    protected DB dataBinding;

    protected RequestPageBean mPageBean = new RequestPageBean();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = initViewModel();
        initObserve();
        int layoutId = onCreate();
        setContentView(layoutId);
        dataBinding = initDataBinding(layoutId);
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

    /**
     * 初始化ViewModel
     */
//    private VM initViewModel() {
//        return ViewModelProviders.of(this).get(getViewModelClass());
//    }
//
//    protected abstract Class<VM> getViewModelClass();
    protected abstract VM initViewModel();

    /**
     * 初始化DataBinding
     */
    protected DB initDataBinding(@LayoutRes int layoutId) {
        return DataBindingUtil.setContentView(this, layoutId);
    }

    protected abstract void initView();

    protected abstract void initData();

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

    @Override
    protected void onDestroy() {
        if (dataBinding != null) {
            dataBinding.unbind();
        }
        dismissProgressDialog();
        super.onDestroy();
    }

    public abstract class OnHttpCallback<T> implements OnHandleCallback<T> {
        @Override
        public void onLoading() {
            showProgressDialog();
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
            dismissProgressDialog();
        }
    }

    // ====================================================================
    protected ProgressDialogHelper progressDialogHelper;

    protected void showProgressDialog() {
        showProgressDialog(R.string.com_waiting);
    }

    protected void showProgressDialog(int resId) {
        if (progressDialogHelper == null) {
            progressDialogHelper = new ProgressDialogHelper(this);
        }
        progressDialogHelper.setMessage(resId);
        progressDialogHelper.show();
    }

    protected void dismissProgressDialog() {
        if (progressDialogHelper != null && progressDialogHelper.isShowing()) {
            progressDialogHelper.dismiss();
        }
    }

    // =====================================标题栏设置==============================================
    protected VZTitleView initTitleView(@StringRes int titleResId) {
        return initTitleView(getString(titleResId), Color.WHITE, false);
    }

    protected VZTitleView initTitleView(@StringRes int titleResId, boolean isPaddingTop) {
        return initTitleView(getString(titleResId), Color.WHITE, isPaddingTop);
    }

    protected VZTitleView initTitleView(String title) {
        return initTitleView(title, Color.WHITE, false);
    }

    protected VZTitleView initTitleView(String title, @ColorInt int bgColor) {
        return initTitleView(title, bgColor, false);
    }

    protected VZTitleView initTitleView(@StringRes int titleResId, @ColorInt int bgColor, boolean isPaddingTop) {
        return initTitleView(getString(titleResId), bgColor, isPaddingTop);
    }

    protected VZTitleView initTitleView(String title, @ColorInt int bgColor, boolean isPaddingTop) {
        VZTitleView mTitleView = findViewById(R.id.titleView);
        if (mTitleView == null) {
            return null;
        }

        mTitleView.setToolbarBackgroundColor(bgColor);
        mTitleView.setTitleText(title);
        if (isPaddingTop) {
            mTitleView.setPaddingTop();
        }

        return mTitleView;
    }
}
