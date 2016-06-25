package com.jimei.xiaolumeimei.base;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye on 2016/6/24.
 */
public interface BaseModel {
  static <T> Observable.Transformer<T, T> io_main() {
    return tObservable -> tObservable.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }
}
