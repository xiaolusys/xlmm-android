package com.jimei.xiaolumeimei.di.module;

import android.content.Context;

import com.jimei.xiaolumeimei.XlmmApp;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final XlmmApp context;

    public ApplicationModule(XlmmApp context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public Context provideApplicationContext() {
        return context.getApplicationContext();
    }
}
