package com.jimei.xiaolumeimei.di.module;

import android.app.Application;
import com.jimei.xiaolumeimei.XlmmApp;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module public class ApplicationModule {

  private final XlmmApp context;

  public ApplicationModule(XlmmApp context) {
    this.context = context;
  }

  @Provides @Singleton public Application provideApplicationContext() {
    return context;
  }
}
