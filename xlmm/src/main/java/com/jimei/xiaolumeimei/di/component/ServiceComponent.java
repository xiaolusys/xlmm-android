package com.jimei.xiaolumeimei.di.component;

import android.app.Service;
import com.gzsll.hupu.injector.PerService;
import com.gzsll.hupu.injector.module.ServiceModule;
import com.gzsll.hupu.service.MessageService;
import dagger.Component;

/**
 * Created by sll on 16/03/16.
 */
@PerService @Component(dependencies = ApplicationComponent.class, modules = { ServiceModule.class })
public interface ServiceComponent {

  Service getServiceContext();

  void inject(MessageService messageService);
}
