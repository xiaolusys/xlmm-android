package com.jimei.xiaolumeimei.utils;

import android.util.Log;
import com.jimei.xiaolumeimei.XlmmApp;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by itxuye on 2016/7/20.
 */
public class RxUtils {
  public static final class RetryWhenNoInternet
      implements Func1<Observable<? extends Throwable>, Observable<?>> {

    private final int _maxRetries;
    private final int _retryDelayMillis;
    private int _retryCount;

    public RetryWhenNoInternet(final int maxRetries, final int retryDelayMillis) {
      _maxRetries = maxRetries;
      _retryDelayMillis = retryDelayMillis;
      _retryCount = 0;
    }

    @Override public Observable<?> call(Observable<? extends Throwable> inputObservable) {
      return inputObservable.flatMap(new Func1<Throwable, Observable<?>>() {
        @Override public Observable<?> call(Throwable throwable) {
          if (++_retryCount < _maxRetries && !NetUtil.isNetworkAvailable(XlmmApp.getmContext())) {
            Log.e("RetryWhenNoInternet", "call: " + _retryCount);
            return Observable.timer(_retryCount * _retryDelayMillis, TimeUnit.MILLISECONDS);
          }
          return Observable.error(throwable);
        }
      });
    }
  }
}
