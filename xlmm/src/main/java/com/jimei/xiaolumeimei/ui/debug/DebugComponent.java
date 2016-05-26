package com.jimei.xiaolumeimei.ui.debug;

import com.jimei.xiaolumeimei.di.component.ApplicationComponent;
import com.jimei.xiaolumeimei.di.module.ActivityModule;
import com.jimei.xiaolumeimei.di.scope.PerActivity;
import dagger.Component;

/**
 * Created by itxuye(www.itxuye.com) on 2016/05/25.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */

@PerActivity @Component(dependencies = ApplicationComponent.class, modules = {
    ActivityModule.class, DebugModule.class
}) public interface DebugComponent {
  void inject(DebugActivity debugActivity);
}
