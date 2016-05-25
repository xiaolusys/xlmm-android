package com.jimei.xiaolumeimei.di.scope;

import java.lang.annotation.Retention;
import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

//声明自己的作用域，保证与activity的声明周期一致
@Scope @Retention(RUNTIME)
public @interface PerActivity {
}
