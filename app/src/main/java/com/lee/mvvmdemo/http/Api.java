package com.lee.mvvmdemo.http;

import com.lee.mvvmdemo.constant.Constants;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * @author by sunfusheng on 2018/4/8.
 */
public class Api {
    private static final Api sInstance = new Api();
    private static Map<String, Retrofit> mRetrofitMap = new HashMap<>();

    public static Api getInstance() {
        return sInstance;
    }

    private Api() {
    }

    private static <T> T getService(String baseUrl, Class<T> service, Interceptor... interceptors) {
        StringBuilder key = new StringBuilder();
        key.append(baseUrl);
        key.append("_").append(service.getSimpleName());
        Retrofit retrofit = mRetrofitMap.get(key.toString());
        if (retrofit == null) {
            OkHttpClient okHttpClient = OkHttpClientFactory.create(interceptors);
            retrofit = RetrofitFactory.create(okHttpClient, baseUrl);
            mRetrofitMap.put(key.toString(), retrofit);
        }
        return retrofit.create(service);
    }

    public static CommonService getCommonService() {
        return getCommonService(CommonService.class, (Interceptor[]) null);
    }

    public static <T> T getCommonService(Class<T> service, Interceptor... interceptors) {
        return getService(Constants.BASE_URL, service, interceptors);
    }
}
