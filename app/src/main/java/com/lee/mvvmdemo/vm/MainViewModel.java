package com.lee.mvvmdemo.vm;

import android.app.Application;
import android.os.SystemClock;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.Timer;
import java.util.TimerTask;

public class MainViewModel extends AndroidViewModel {

    private static final int ONE_SECOND = 1000;
    private MutableLiveData<Long> mutableLiveData = new MutableLiveData<>();
    private Timer mTimer;
    private long time;

    public MainViewModel(@NonNull Application application) {
        super(application);

        time = SystemClock.elapsedRealtime();
        mTimer = new Timer();
        mTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                final long newValue = (SystemClock.elapsedRealtime() - time) / 1000;
                mutableLiveData.postValue(newValue);
            }
        }, ONE_SECOND, ONE_SECOND);
    }

    public MutableLiveData<Long> getMutableLiveData() {
        return mutableLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (mTimer != null) {
            mTimer.cancel();
        }
        mTimer = null;
    }
}
