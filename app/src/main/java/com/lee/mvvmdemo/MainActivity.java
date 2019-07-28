package com.lee.mvvmdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.lee.mvvmdemo.activity.CategoryActivity;
import com.lee.mvvmdemo.activity.FlexboxTestActivity;
import com.lee.mvvmdemo.databinding.ActivityMainBinding;
import com.lee.mvvmdemo.vm.MainViewModel;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private MainViewModel mMainViewModel;
    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mMainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mMainViewModel.getMutableLiveData().observe(this, new Observer<Long>() {
            @Override
            public void onChanged(Long aLong) {
                mBinding.tvName.setText(String.valueOf(aLong));
            }
        });

        mBinding.tvNext.setOnClickListener(this);
        mBinding.tvFlexbox.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tvNext) {
            ActivityUtils.startActivity(this, CategoryActivity.class);
        } else if (view.getId() == R.id.tvFlexbox) {
            ActivityUtils.startActivity(this, FlexboxTestActivity.class);
        }
    }
}
