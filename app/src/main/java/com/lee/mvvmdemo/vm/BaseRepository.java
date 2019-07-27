package com.lee.mvvmdemo.vm;

import com.lee.mvvmdemo.http.Api;
import com.lee.mvvmdemo.http.CommonService;

class BaseRepository {

//    private CommonService apiService;
//
//    public BaseRepository(CommonService apiService) {
//        this.apiService = apiService;
//    }
//
//    public CommonService getApiService() {
//        return apiService;
//    }
//
//    public boolean isSuccess(Response result) {
//        return result != null && result.isSuccessful();
//    }

    CommonService getApiService() {
        return Api.getCommonService();
    }
}
