package com.jimei.xiaolumeimei.di.component;

import android.app.Activity;
import com.jimei.xiaolumeimei.di.module.ActivityModule;
import com.jimei.xiaolumeimei.di.scope.PerActivity;
import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

  Activity getActivity();
}
