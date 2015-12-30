package com.jimei.xiaolumeimei.utils;

import android.app.Activity;
import java.util.LinkedList;

/**
 * Author  : itxuye(itxuye@gmail.com)|(http://itxuye.com)
 * Date    : 2015-12-01
 * Time    : 15:42
 * FIXME
 */
public class ActivityUtils {

  private static LinkedList<Activity> activities = new LinkedList<>();

  /*   添加activity
   *   @param actvity
   */
  public static void addActivity(Activity activity) {

    activities.add(activity);
  }

  /*   删除activity
   *   @param actvity
   */
  public static void removeActivity(Activity activity) {

    activities.remove(activity);
  }

  /*   销毁activity
   *   @param actvity
   */
  public static void destroyActivity() {

    for (Activity activity : activities) {
      activity.finish();
    }
  }

  public static void exitApp() {

    destroyActivity();

    android.os.Process.killProcess(android.os.Process.myPid());
    System.exit(0);
  }
}
