package com.lee.mvvmdemo.http;

import com.lee.mvvmdemo.BuildConfig;
import com.lee.mvvmdemo.constant.Constants;
import com.lee.mvvmdemo.utils.CollectionUtil;

import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class OkHttpClientFactory {
    private static final int TIMEOUT = 60; // 60s
    private static final int MAX_CACHE_SIZE = 1024 * 1024 * 20; // 20MB

    public static OkHttpClient create(Interceptor... interceptors) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(new CommonInterceptor())
                .addNetworkInterceptor(new CommonNetworkInterceptor())
                .cache(new Cache(Constants.CacheDir.OKHTTP, MAX_CACHE_SIZE));

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC));
        }

        if (!CollectionUtil.isEmpty(interceptors)) {
            for (Interceptor interceptor : interceptors) {
                builder.addInterceptor(interceptor);
            }
        }
        return builder.build();
    }
}
