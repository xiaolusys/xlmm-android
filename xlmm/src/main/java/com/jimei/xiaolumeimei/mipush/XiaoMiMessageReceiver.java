package com.jimei.xiaolumeimei.mipush;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import com.google.gson.Gson;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.XlmmApp;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.entities.XiaoMiPushContent;
import com.jimei.xiaolumeimei.ui.activity.main.MainActivity;
import com.jimei.xiaolumeimei.ui.activity.main.WebViewActivity;
import com.jimei.xiaolumeimei.ui.activity.product.ProductDetailActvityWeb;
import com.jimei.xiaolumeimei.ui.activity.product.TongkuanActivity;
import com.jimei.xiaolumeimei.ui.activity.trade.OrderDetailActivity;
import com.jimei.xiaolumeimei.ui.activity.user.CouponActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MMNinePicActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MamaInfoActivity;
import com.jimei.xiaolumeimei.utils.JumpUtils;
import com.jimei.xiaolumeimei.utils.LoginUtils;
import com.jude.utils.JUtils;
import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 1、PushMessageReceiver是个抽象类，该类继承了BroadcastReceiver。
 * 2、需要将自定义的DemoMessageReceiver注册在AndroidManifest.xml文件中 <receiver
 * android:exported="true"
 * android:name="com.xiaomi.mipushdemo.DemoMessageReceiver"> <intent-filter>
 * <action android:name="com.xiaomi.mipush.RECEIVE_MESSAGE" /> </intent-filter>
 * <intent-filter> <action android:name="com.xiaomi.mipush.ERROR" />
 * </intent-filter> <intent-filter>
 * <action android:name="com.xiaomi.mipush.MESSAGE_ARRIVED" /></intent-filter>
 * </receiver>
 * 3、DemoMessageReceiver的onReceivePassThroughMessage方法用来接收服务器向客户端发送的透传消息
 * 4、DemoMessageReceiver的onNotificationMessageClicked方法用来接收服务器向客户端发送的通知消息，
 * 这个回调方法会在用户手动点击通知后触发
 * 5、DemoMessageReceiver的onNotificationMessageArrived方法用来接收服务器向客户端发送的通知消息，
 * 这个回调方法是在通知消息到达客户端时触发。另外应用在前台时不弹出通知的通知消息到达客户端也会触发这个回调函数
 * 6、DemoMessageReceiver的onCommandResult方法用来接收客户端向服务器发送命令后的响应结果
 * 7、DemoMessageReceiver的onReceiveRegisterResult方法用来接收客户端向服务器发送注册命令后的响应结果
 * 8、以上这些方法运行在非UI线程中
 *
 * @author mayixiang
 */
public class XiaoMiMessageReceiver extends PushMessageReceiver {

  private String mRegId;
  private long mResultCode = -1;
  private String mReason;
  private String mCommand;
  private String mMessage;
  private String mTopic;
  private String mAlias;
  private String mAccount;
  private String mStartTime;
  private String mEndTime;

  public static final String TAG = "XiaoMiMessageReceiver";

  @Override
  public void onReceivePassThroughMessage(Context context, MiPushMessage message) {
    Log.i(TAG, "onReceivePassThroughMessage is called. " + message.toString());
    String log =
        context.getString(R.string.recv_passthrough_message, message.getContent());
    //MainActivity.logList.add(0, getSimpleDate() + " " + log);

    if (!TextUtils.isEmpty(message.getTopic())) {
      mTopic = message.getTopic();
    } else if (!TextUtils.isEmpty(message.getAlias())) {
      mAlias = message.getAlias();
    }

    Message msg = Message.obtain();
    msg.obj = log;
    XlmmApp.getHandler().sendMessage(msg);
  }

  @Override
  public void onNotificationMessageClicked(Context context, MiPushMessage message) {
    JUtils.Log(TAG, "onNotificationMessageClicked is called. " + message.toString());
    JUtils.Log(TAG,"content:"+ message.getContent());

    XiaoMiPushContent miPushContent = null;
    if ((message.getContent() != null)&& (!message.getContent().isEmpty())) {
      try {
        Gson mgson = new Gson();
        miPushContent = mgson.fromJson(
            message.getContent(), XiaoMiPushContent.class);
        JUtils.Log(TAG, "target url "+ miPushContent.getTargetUrl());
      } catch (Exception e) {
        e.printStackTrace();

      }
    }

    if((miPushContent!= null)
        && (miPushContent.getTargetUrl() != null)
        && (!miPushContent.getTargetUrl().isEmpty())) {
      JumpUtils.push_jump_proc(context, miPushContent.getTargetUrl());
    }

    /*String log =
        context.getString(R.string.click_notification_message, message.getContent());
    MainActivity.logList.add(0, getSimpleDate() + " " + log);

    if (!TextUtils.isEmpty(message.getTopic())) {
      mTopic = message.getTopic();
    } else if (!TextUtils.isEmpty(message.getAlias())) {
      mAlias = message.getAlias();
    }

    Message msg = Message.obtain();
    if (message.isNotified()) {
      msg.obj = log;
    }
    XlmmApp.getHandler().sendMessage(msg);*/
  }

  @Override
  public void onNotificationMessageArrived(Context context, MiPushMessage message) {
    Log.i(TAG, "onNotificationMessageArrived is called. " + message.toString());
    String log =
        context.getString(R.string.arrive_notification_message, message.getContent());
    //MainActivity.logList.add(0, getSimpleDate() + " " + log);

    if (!TextUtils.isEmpty(message.getTopic())) {
      mTopic = message.getTopic();
    } else if (!TextUtils.isEmpty(message.getAlias())) {
      mAlias = message.getAlias();
    }

    Message msg = Message.obtain();
    msg.obj = log;
    XlmmApp.getHandler().sendMessage(msg);
  }

  @Override public void onCommandResult(Context context, MiPushCommandMessage message) {
    Log.i(TAG, "onCommandResult is called. " + message.toString());
    String command = message.getCommand();
    List<String> arguments = message.getCommandArguments();
    String cmdArg1 =
        ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);
    String cmdArg2 =
        ((arguments != null && arguments.size() > 1) ? arguments.get(1) : null);
    String log = "";
    if (MiPushClient.COMMAND_REGISTER.equals(command)) {
      if (message.getResultCode() == ErrorCode.SUCCESS) {
        mRegId = cmdArg1;
        log = context.getString(R.string.register_success);

        LoginUtils.setPushUserAccount(context, mRegId);
      } else {
        log = context.getString(R.string.register_fail);
      }
    } else if (MiPushClient.COMMAND_SET_ALIAS.equals(command)) {
      if (message.getResultCode() == ErrorCode.SUCCESS) {
        mAlias = cmdArg1;
        log = context.getString(R.string.set_alias_success, mAlias);
      } else {
        log = context.getString(R.string.set_alias_fail, message.getReason());
      }
    } else if (MiPushClient.COMMAND_UNSET_ALIAS.equals(command)) {
      if (message.getResultCode() == ErrorCode.SUCCESS) {
        mAlias = cmdArg1;
        log = context.getString(R.string.unset_alias_success, mAlias);
      } else {
        log = context.getString(R.string.unset_alias_fail, message.getReason());
      }
    } else if (MiPushClient.COMMAND_SET_ACCOUNT.equals(command)) {
      if (message.getResultCode() == ErrorCode.SUCCESS) {
        mAccount = cmdArg1;
        log = context.getString(R.string.set_account_success, mAccount);

        LoginUtils.saveUserAccount(context, mAccount);
      } else {
        log = context.getString(R.string.set_account_fail, message.getReason());
      }
    } else if (MiPushClient.COMMAND_UNSET_ACCOUNT.equals(command)) {
      if (message.getResultCode() == ErrorCode.SUCCESS) {
        mAccount = cmdArg1;
        log = context.getString(R.string.unset_account_success, mAccount);
        LoginUtils.delUserAccount(context, mAccount);
      } else {
        log = context.getString(R.string.unset_account_fail, message.getReason());
      }
    } else if (MiPushClient.COMMAND_SUBSCRIBE_TOPIC.equals(command)) {
      if (message.getResultCode() == ErrorCode.SUCCESS) {
        mTopic = cmdArg1;
        log = context.getString(R.string.subscribe_topic_success, mTopic);
      } else {
        log = context.getString(R.string.subscribe_topic_fail, message.getReason());
      }
    } else if (MiPushClient.COMMAND_UNSUBSCRIBE_TOPIC.equals(command)) {
      if (message.getResultCode() == ErrorCode.SUCCESS) {
        log = context.getString(R.string.unsubscribe_topic_success, mTopic);
      } else {
        log = context.getString(R.string.unsubscribe_topic_fail, message.getReason());
      }
    } else if (MiPushClient.COMMAND_SET_ACCEPT_TIME.equals(command)) {
      if (message.getResultCode() == ErrorCode.SUCCESS) {
        mStartTime = cmdArg1;
        mEndTime = cmdArg2;
        log = context.getString(R.string.set_accept_time_success, mStartTime, mEndTime);
      } else {
        log = context.getString(R.string.set_accept_time_fail, message.getReason());
      }
    } else {
      log = message.getReason();
    }
    //MainActivity.logList.add(0, getSimpleDate() + "    " + log);

    Message msg = Message.obtain();
    msg.obj = log;
    XlmmApp.getHandler().sendMessage(msg);
  }

  @Override
  public void onReceiveRegisterResult(Context context, MiPushCommandMessage message) {
    Log.i(TAG, "onReceiveRegisterResult is called. " + message.toString());
    String command = message.getCommand();
    List<String> arguments = message.getCommandArguments();
    String cmdArg1 =
        ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);
    String log;
    if (MiPushClient.COMMAND_REGISTER.equals(command)) {
      if (message.getResultCode() == ErrorCode.SUCCESS) {
        mRegId = cmdArg1;
        log = context.getString(R.string.register_success);

        LoginUtils.setPushUserAccount(context, mRegId);
      } else {
        log = context.getString(R.string.register_fail);
      }
    } else {
      log = message.getReason();
    }

    Message msg = Message.obtain();
    msg.obj = log;
    XlmmApp.getHandler().sendMessage(msg);
  }

  @SuppressLint("SimpleDateFormat") public static String getSimpleDate() {
    return new SimpleDateFormat("MM-dd hh:mm:ss").format(new Date());
  }

  public static class XiaoMiPushHandler extends Handler {

    private Context context;

    public XiaoMiPushHandler(Context context) {
      this.context = context;
    }

    @Override public void handleMessage(Message msg) {
      String s = (String) msg.obj;
      //if (MainActivity.sMainActivity != null) {
      //    MainActivity.sMainActivity.refreshLogInfo();
      //}
      if (!TextUtils.isEmpty(s)) {
        //Toast.makeText(context, s, Toast.LENGTH_LONG).show();
      }
    }
  }


}
