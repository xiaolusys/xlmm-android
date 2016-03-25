package com.jimei.library.rx;

import com.jimei.library.event.NetWorkEvent;
import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by 优尼世界 on 15/12/29.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */

public class RxNetWorkBus {
private static final String rx = "test";
  private static final Subject<NetWorkEvent, NetWorkEvent> bus =
      new SerializedSubject<>(PublishSubject.create());

  public static void send(NetWorkEvent o) {
    bus.onNext(o);
  }

  public static Observable<NetWorkEvent> toObserverable() {
    return bus;
  }
}
