package com.lee.mvvmdemo.activity;

import android.graphics.Color;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.blankj.utilcode.util.ObjectUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lee.mvvmdemo.R;
import com.lee.mvvmdemo.adapter.CategoryAdapter;
import com.lee.mvvmdemo.base.BaseActivity;
import com.lee.mvvmdemo.constant.Constants;
import com.lee.mvvmdemo.databinding.ActivityCategoryLayoutBinding;
import com.lee.mvvmdemo.entity.CategoryResult;
import com.lee.mvvmdemo.view.VZTitleView;
import com.lee.mvvmdemo.vm.Resource;
import com.lee.mvvmdemo.vm.impl.CategoryViewModel;

public class CategoryActivity extends BaseActivity<CategoryViewModel, ActivityCategoryLayoutBinding> implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener, View.OnClickListener {

    private CategoryAdapter mAdapter;

    @Override
    protected int onCreate() {
        return R.layout.activity_category_layout;
    }

    @Override
    protected CategoryViewModel initViewModel() {
        return ViewModelProviders.of(this).get(CategoryViewModel.class);
    }

    @Override
    protected void initView() {
        VZTitleView mTitleView = initTitleView(R.string.main_title, Color.TRANSPARENT, false);
        mTitleView.setBackVisibility(View.INVISIBLE);
        mTitleView.setToolbarBackgroundResource(R.color.color_cblue1);
        mTitleView.setViewOnClickListener(this);

        dataBinding.swipeRefreshLayout.setOnRefreshListener(this);
        dataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dataBinding.recyclerView.setHasFixedSize(true);
    }

    @Override
    protected void initData() {
        mAdapter = new CategoryAdapter();
        dataBinding.recyclerView.setAdapter(mAdapter, this);

        onRefresh();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onRefresh() {
        mPageBean.firstPage();
        mAdapter.setEnableLoadMore(false);
        viewModel.getCategory(Constants.HTTP_CATEGORY_ANDROID, mPageBean.size, mPageBean.no).observe(this, this::handlerLoadRequest);
    }

    private void handlerLoadRequest(@NonNull Resource<CategoryResult> resource) {
        resource.handle(new OnHttpCallback<CategoryResult>() {
            @Override
            public void onSuccess(CategoryResult data) {
                mAdapter.setNewData(data.results);
                mAdapter.setEnableLoadMore(true);
                mAdapter.setLoadMoreStatus(data.results.size());
            }

            @Override
            public void onCompleted() {
                super.onCompleted();
                dataBinding.swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onLoadMoreRequested() {
        viewModel.getCategory(Constants.HTTP_CATEGORY_ANDROID, mPageBean.size, mPageBean.nextPage()).observe(this, this::handlerMoreRequest);
    }

    private void handlerMoreRequest(@NonNull Resource<CategoryResult> resource) {
        resource.handle(new OnHttpCallback<CategoryResult>() {
            @Override
            public void onSuccess(CategoryResult data) {
                if (ObjectUtils.isEmpty(data.results)) {
                    mAdapter.setLoadMoreStatus(0);
                    return;
                }

                mPageBean.addPageNo();
                mAdapter.addData(data.results);
                mAdapter.setLoadMoreStatus(data.results.size());
            }

            @Override
            public void onFailure(String msg) {
                super.onFailure(msg);
                mAdapter.loadMoreFail();
            }
        });
    }
}
