package com.jimei.xiaolumeimei.ui.debug;

import com.jimei.xiaolumeimei.di.scope.PerActivity;
import com.jimei.xiaolumeimei.model.UserModel;
import dagger.Module;
import dagger.Provides;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye(www.itxuye.com) on 2016/05/25.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
@Module public class DebugModule {
  @Provides @PerActivity Subscription openDebug(String pass) {

    return UserModel.getInstance()
        .openDebug(pass)
        .subscribeOn(Schedulers.io())
        .subscribe();
  }
}
