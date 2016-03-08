package com.jimei.library.rx;

import com.jude.utils.JUtils;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by 优尼世界 on 15/12/29.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class DefaultTransform<T> implements Observable.Transformer<T, T> {
  @Override public Observable<T> call(Observable<T> tObservable) {
    return tObservable.doOnError(throwable -> JUtils.Log(throwable.getLocalizedMessage()))
        .observeOn(AndroidSchedulers.mainThread());
  }
}

