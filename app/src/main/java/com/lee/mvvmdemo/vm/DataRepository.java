package com.lee.mvvmdemo.vm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.lee.mvvmdemo.entity.CategoryResult;
import com.lee.mvvmdemo.http.ResponseObserver;
import com.lee.mvvmdemo.http.Transformer;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DataRepository extends BaseRepository {

    //    DataSource mDataSource;
    private static DataRepository instance;

    private DataRepository() {
    }

    public static DataRepository getInstance() {
        if (instance == null) {
            synchronized (DataRepository.class) {
                if (instance == null) {
                    instance = new DataRepository();
                }
            }
        }
        return instance;
    }

    public LiveData<Resource<CategoryResult>> getCategory(String category, int number, int curPage) {
        final MutableLiveData<Resource<CategoryResult>> mutableLiveData = new MutableLiveData<>();
        mutableLiveData.setValue(Resource.loading());
        getApiService().getCategory(category, number, curPage)
                .compose(Transformer.applyRemoteTransformer())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResponseObserver<CategoryResult>() {
                    @Override
                    public void onNotify(Resource<CategoryResult> notify) {
                        mutableLiveData.setValue(notify);
                    }
                });
        return mutableLiveData;
    }

//    public Observable<Resource<CategoryResult>> getCategory(String category, int number, int curPage) {
//        return getApiService().getCategory(category, number, curPage)
//                .compose(Transformer.applyRemoteTransformer())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread());
//    }
}
