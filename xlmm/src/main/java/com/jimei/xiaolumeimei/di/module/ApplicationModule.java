package com.jimei.xiaolumeimei.di.module;

import android.content.Context;
import com.jimei.xiaolumeimei.XlmmApp;
import com.jimei.xiaolumeimei.di.scope.ContextLife;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class ApplicationModule {

    private final XlmmApp context;

    public ApplicationModule(XlmmApp context) {
        this.context = context;
    }

    @Provides
    @Singleton
    @ContextLife("Application")
    public Context provideApplicationContext() {
        return context.getApplicationContext();
    }
}
