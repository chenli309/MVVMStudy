package com.lee.mvvmdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import com.lee.mvvmdemo.databinding.ActivityMainBinding;
import com.lee.mvvmdemo.vm.MainViewModel;

public class MainActivity extends AppCompatActivity {
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
    }
}
