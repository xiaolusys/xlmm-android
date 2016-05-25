package com.jimei.xiaolumeimei.di.component;

import android.content.Context;
import com.jimei.xiaolumeimei.XlmmApp;
import com.jimei.xiaolumeimei.base.BaseActivity;
import com.jimei.xiaolumeimei.base.BaseAppCompatActivity;
import com.jimei.xiaolumeimei.base.BaseAppCompatActivityForDetail;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.di.module.ApplicationModule;
import dagger.Component;
import javax.inject.Singleton;

@Singleton @Component(modules = { ApplicationModule.class })
public interface ApplicationComponent {
  Context getContext();

  void inject(XlmmApp mApplication);

  void inject(BaseActivity mBaseActivity);

  void inject(BaseAppCompatActivity mBaseActivity);

  void inject(BaseAppCompatActivityForDetail mBaseActivity);

  void inject(BaseSwipeBackCompatActivity mBaseActivity);
}
