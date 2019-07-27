package com.lee.mvvmdemo.vm;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({HttpStatus.LOADING, HttpStatus.SUCCESS, HttpStatus.FAILURE, HttpStatus.ERROR, HttpStatus.EMPTY})
@Retention(RetentionPolicy.SOURCE)
public @interface HttpStatus {
    int LOADING = 0;
    int SUCCESS = 1;
    int FAILURE = 2;
    int ERROR = 3;
    int EMPTY = 4;
}
