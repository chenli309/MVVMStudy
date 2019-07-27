package com.lee.mvvmdemo.http;

import com.lee.mvvmdemo.vm.Resource;

import io.reactivex.ObservableTransformer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Request;
import retrofit2.Response;

public class Transformer {
    public static <T> ObservableTransformer<Response<T>, Resource<T>> applyRemoteTransformer() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .map(it -> {
                    if (it == null) {
                        return Resource.empty();
                    }

                    Request request = it.raw().request();
                    Resource<T> responseData = Resource.response(it);
//                    if (it.isSuccessful()) {
//                        if (it.body() == null) {
//                            responseData = Resource.empty(it.code());
//                        } else {
//                            responseData = Resource.success(it);
//                        }
//                    } else {
//                        responseData = Resource.error(it.code());
//                    }
                    responseData.url = request.url().toString();
                    return responseData;
                });
    }
}
