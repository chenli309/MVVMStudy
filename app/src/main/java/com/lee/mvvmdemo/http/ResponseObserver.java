package com.lee.mvvmdemo.http;

import android.app.ProgressDialog;
import android.util.Log;

import com.lee.mvvmdemo.vm.Resource;
import java.lang.ref.WeakReference;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author sunfusheng on 2018/4/17.
 */
public abstract class ResponseObserver<T> implements Observer<Resource<T>> {

    private WeakReference<Disposable> mDisposableWeakRef;
    private boolean isOnNext;

    private ProgressDialog pd;

    @Override
    public void onSubscribe(Disposable disposable) {
        isOnNext = false;
        mDisposableWeakRef = new WeakReference<>(disposable);
    }

    @Override
    public void onNext(Resource<T> t) {
        isOnNext = true;
        if (t == null) {
            Log.w("sfs", "ResponseObserver onNext(): EMPTY");
            onNotify(Resource.empty(t.message));
        } else {
            onNotify(t);
        }
    }

    @Override
    public void onError(Throwable throwable) {
        release();
        Log.e("sfs", "ResponseObserver onError(): " + Resource.error(throwable).toString());
        onNotify(Resource.error(throwable));
    }

    @Override
    public void onComplete() {
        release();
        if (!isOnNext) {
            Log.w("sfs", "ResponseObserver onComplete(): EMPTY");
            onNotify(Resource.empty());
        }
    }

    private void release() {
        if (mDisposableWeakRef != null) {
            Disposable disposable = mDisposableWeakRef.get();
            if (disposable != null && !disposable.isDisposed()) {
                disposable.dispose();
            }
            mDisposableWeakRef = null;
        }
    }

    public abstract void onNotify(Resource<T> notify);
}
