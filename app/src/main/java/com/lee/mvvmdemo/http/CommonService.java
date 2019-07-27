package com.lee.mvvmdemo.http;

import com.lee.mvvmdemo.entity.CategoryResult;
import com.lee.mvvmdemo.entity.User;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CommonService {
    @GET("users/{username}")
    Observable<Response<User>> fetchUserDetail(
            @Path("username") String username
    );

    @GET("data/{category}/{number}/{page}")
    Observable<Response<CategoryResult>> getCategory(@Path("category") String category, @Path("number") int number, @Path("page") int page);
}
