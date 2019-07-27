package com.lee.mvvmdemo.constant;

import com.lee.mvvmdemo.utils.SdCardUtil;

import java.io.File;

public class Constants {

    public final static String BASE_URL = "http://gank.io/api/";
    public final static String HTTP_CATEGORY_ANDROID = "Android";

    public static class CacheDir {
        public static final File OKHTTP = SdCardUtil.getDiskCacheDir("okhttp");
        public static final File CONTRIBUTION = SdCardUtil.getDiskCacheDir("contribution");
        public static final File README = SdCardUtil.getDiskCacheDir("readme");
    }

    public static class PreferenceKey {
        public static final String USERNAME = "preference_key_username";
        public static final String PASSWORD = "preference_key_password";
        public static final String AUTH = "preference_key_auth";
        public static final String TOKEN = "preference_key_token";
    }
}
