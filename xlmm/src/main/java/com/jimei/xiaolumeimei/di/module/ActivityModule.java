package com.jimei.xiaolumeimei.di.module;

import android.app.Activity;
import com.jimei.xiaolumeimei.di.scope.PerActivity;
import dagger.Module;
import dagger.Provides;

@Module public class ActivityModule {

  private final Activity mActivity;

  public ActivityModule(Activity mActivity) {
    this.mActivity = mActivity;
  }

  @Provides @PerActivity public Activity provideActivity() {
    return mActivity;
  }
}
