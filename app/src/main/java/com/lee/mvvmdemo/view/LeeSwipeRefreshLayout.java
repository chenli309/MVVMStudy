package com.lee.mvvmdemo.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.lee.mvvmdemo.R;


public class LeeSwipeRefreshLayout extends SwipeRefreshLayout {
    public LeeSwipeRefreshLayout(@NonNull Context context) {
        super(context);
        init();
    }

    public LeeSwipeRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        // 设置下拉进度的背景颜色，默认就是白色的
        setProgressBackgroundColorSchemeResource(android.R.color.white);
        // 设置下拉进度的主题颜色
        setColorSchemeResources(R.color.colorPrimary);
    }
}
