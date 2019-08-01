package com.lee.mvvmdemo.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.lee.mvvmdemo.R;

import java.util.Timer;
import java.util.TimerTask;

public class FlexboxTestActivity extends AppCompatActivity {
    private HandlerThread handlerThread;
    private Handler mainHandler = new Handler();

    private Timer timer = new Timer();
    private TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            Log.e("Lee", "timerTask1 = " + Thread.currentThread().getName());
            mainHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.e("Lee", "timerTask2 = " + Thread.currentThread().getName());
                }
            }, 500);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_flex_layout);

        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                mainHandler.postDelayed(this, 1000);
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e("Lee", "currentThread1 = " + Thread.currentThread().getName());
            }
        }).start();

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                Log.e("Lee", "currentThread2 = " + Thread.currentThread().getName());
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.e("Lee", "currentThread3 = " + Thread.currentThread().getName());
            }
        }, 100);

        // Timer
        timer.schedule(timerTask, 1000);

        // HandlerThread
        handlerThread();
    }

    /**
     * 1.内存泄漏
     * 2.连续发送消息
     *
     *
     */
    private void handlerThread() {
        handlerThread = new HandlerThread("work");
        handlerThread.start();
        // 使用创建的HandlerThread.getLooper()，设置workHandler#handleMessage()在子线程
        Handler workHandler = new Handler(handlerThread.getLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Log.e("Lee", "handlerThread1 = " + Thread.currentThread().getName());
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("Lee", "mainHandler1 = " + Thread.currentThread().getName());
                    }
                });
            }
        };
        workHandler.sendEmptyMessage(1);
    }

    private void quitHandlerThread() {
        if (handlerThread != null) {
            handlerThread.quit();
        }
    }

}
