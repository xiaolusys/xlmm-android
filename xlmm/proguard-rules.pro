# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/ye.xu/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.

-optimizationpasses 5          # 指定代码的压缩级别
-dontusemixedcaseclassnames   # 是否使用大小写混合
-dontpreverify           # 混淆时是否做预校验
-verbose                # 混淆时是否记录日志

-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*  # 混淆时所采用的算法

-keep public class * extends android.app.Activity      # 保持哪些类不被混淆
-keep public class * extends android.app.Application   # 保持哪些类不被混淆
-keep public class * extends android.app.Service       # 保持哪些类不被混淆
-keep public class * extends android.content.BroadcastReceiver  # 保持哪些类不被混淆
-keep public class * extends android.content.ContentProvider    # 保持哪些类不被混淆
-keep public class * extends android.app.backup.BackupAgentHelper # 保持哪些类不被混淆
-keep public class * extends android.preference.Preference        # 保持哪些类不被混淆
-keep public class com.android.vending.licensing.ILicensingService    # 保持哪些类不被混淆

-keepclasseswithmembernames class * {  # 保持 native 方法不被混淆
    native <methods>;
}
-keepclasseswithmembers class * {   # 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {# 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers class * extends android.app.Activity { # 保持自定义控件类不被混淆
    public void *(android.view.View);
}
-keepclassmembers enum * {     # 保持枚举 enum 类不被混淆
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep class * implements android.os.Parcelable { # 保持 Parcelable 不被混淆
    public static final android.os.Parcelable$Creator *;
}

-keepattributes Signature


# Gson specific classes
-keep class sun.misc.Unsafe { *; }
#-keep class com.google.gson.stream.** { *; }


# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.examples.android.model.** { *; }


##---------------End: proguard configuration for Gson  ----------

##-------------------------ping ++
-dontwarn com.alipay.**
-keep class com.alipay.** {*;}

-dontwarn  com.ta.utdid2.**
-keep class com.ta.utdid2.** {*;}

-dontwarn  com.ut.device.**
-keep class com.ut.device.** {*;}

-dontwarn  com.tencent.**
-keep class com.tencent.** {*;}

-dontwarn  com.unionpay.**
-keep class com.unionpay.** {*;}

-dontwarn com.pingplusplus.**
-keep class com.pingplusplus.** {*;}

-dontwarn com.baidu.**
-keep class com.baidu.**{*;}

-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

##--------------------------end

##umeng---------------
-keep public class com.jimei.xiaolumeimei.R$*{
public static final int *;
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}##-----------------end


# Support library
-keep class android.support.** { *; }
-keep interface android.support.** { *; }

# Retrofit
-dontwarn retrofit.**
-keep class retrofit.** { *; }
-keepattributes Signature
-keepattributes Exceptions

# OkHttp
-dontwarn okio.**
-dontwarn com.squareup.okhttp.**

# RxJava
# RxAndroid will soon ship with rules so this may not be needed in the future
# https://github.com/ReactiveX/RxAndroid/issues/219
-dontwarn sun.misc.Unsafe
-keep class rx.internal.util.unsafe.** { *; }

# Gson
-keep class com.google.gson.** { *; }
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
-keepattributes *Annotation*

# JSONObject
-keepclassmembers class * {
   public <init>(org.json.JSONObject);
}

# Serializable Parcelable
-keepnames class * implements java.io.Serializable
-keep public class * implements android.os.Parcelable {*;}
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# Glide
-keep class com.github.bumptech.glide.** {*;}
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
    **[] $VALUES;
    public *;
}


# butterknife
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}