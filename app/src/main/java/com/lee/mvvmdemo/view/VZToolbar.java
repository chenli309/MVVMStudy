package com.lee.mvvmdemo.view;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.lee.mvvmdemo.statusbar.VZStatusBarUtil;

/**
 * Func:    Toolbar封装.<br/>
 * Date:    2018/8/2 16:19<br/>
 * Author:  chenli@variflight.com<br/>
 * Version: 1.0.0<br/>
 */
public class VZToolbar extends Toolbar {

    public VZToolbar(Context context) {
        super(context);
    }

    public VZToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public VZToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 沉浸状态栏图片模式下，设置Toolbar的paddingTop，解决布局内容显示到状态栏的问题
     */
    public void setPaddingTop() {
        int compatPaddingTop = 0;
        // android 4.4以上将Toolbar添加状态栏高度的上边距，沉浸到状态栏下方
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            compatPaddingTop = VZStatusBarUtil.getStatusBarHeight(getContext());
        }
        this.setPadding(getPaddingLeft(), getPaddingTop() + compatPaddingTop, getPaddingRight(), getPaddingBottom());
    }
}
