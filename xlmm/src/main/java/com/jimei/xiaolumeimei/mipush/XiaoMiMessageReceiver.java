package com.jimei.xiaolumeimei.mipush;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import com.google.gson.Gson;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.XlmmApp;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.entities.UserAccountBean;
import com.jimei.xiaolumeimei.entities.XiaoMiPushContent;
import com.jimei.xiaolumeimei.model.UserModel;
import com.jimei.xiaolumeimei.ui.activity.main.WebViewActivity;
import com.jimei.xiaolumeimei.utils.LoginUtils;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;
import rx.schedulers.Schedulers;

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
    if (!message.getContent().isEmpty()) {
      try {
        Gson mgson = new Gson();
        miPushContent = mgson.fromJson(
            message.getContent(), XiaoMiPushContent.class);
        JUtils.Log(TAG, "target url "+ miPushContent.getTargetUrl());
      } catch (Exception e) {
        e.printStackTrace();

      }
    }

    if((miPushContent!= null) && (!miPushContent.getTargetUrl().isEmpty())) {
      push_jump_proc(context, miPushContent.getTargetUrl());
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

        //register xiaomi push
        JUtils.Log("XlmmApp", "regid: " + mRegId + " "
            + MiPushClient.getRegId(context.getApplicationContext())
            + " devid:"+((TelephonyManager) context.getSystemService( Context.TELEPHONY_SERVICE ))
            .getDeviceId());
        new UserModel().getUserAccount("android", mRegId,
            ((TelephonyManager)context.getSystemService( Context.TELEPHONY_SERVICE ))
                .getDeviceId())
            .subscribeOn(Schedulers.newThread())
            .subscribe(new ServiceResponse<UserAccountBean>() {
              @Override public void onNext(UserAccountBean user) {
                JUtils.Log("XlmmApp", "UserAccountBean:, " + user.toString());
                MiPushClient.setUserAccount(context.getApplicationContext(), user.getUserAccount(), null);
              }
            });
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
      } else {
        log = context.getString(R.string.set_account_fail, message.getReason());
      }
    } else if (MiPushClient.COMMAND_UNSET_ACCOUNT.equals(command)) {
      if (message.getResultCode() == ErrorCode.SUCCESS) {
        mAccount = cmdArg1;
        log = context.getString(R.string.unset_account_success, mAccount);
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

        //register xiaomi push
        JUtils.Log("XlmmApp", "regid: " + mRegId
            + " devid:"+((TelephonyManager) context.getSystemService( Context.TELEPHONY_SERVICE ))
            .getDeviceId());
        new UserModel().getUserAccount("android", mRegId,
            ((TelephonyManager)context.getSystemService( Context.TELEPHONY_SERVICE ))
                .getDeviceId())
            .subscribeOn(Schedulers.newThread())
            .subscribe(new ServiceResponse<UserAccountBean>() {
              @Override public void onNext(UserAccountBean user) {
                JUtils.Log("XlmmApp", "UserAccountBean:, " + user.toString());
                MiPushClient.setUserAccount(context.getApplicationContext(), user
                    .getUserAccount(), null);
              }

              @Override public void onCompleted() {
                super.onCompleted();
              }

              @Override public void onError(Throwable e) {
                Log.e("XlmmApp", "error:, " + e.toString());
                super.onError(e);
              }
            });
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

  private void push_jump_proc(Context context,String recvContent){
    JUtils.Log(TAG, "push_jump_proc:" + recvContent);
    JUtils.Log(TAG, "push_jump_proc:" + (recvContent== null) + " " +  (recvContent.isEmpty()));
    if((recvContent== null) || (recvContent.isEmpty()))
      return;

    JumpInfo jumpInfo = get_jump_info(recvContent);

    switch (jumpInfo.getType()){
      case XlmmConst.JUMP_WEBVIEW:
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        SharedPreferences sharedPreferences =
            context.getSharedPreferences("COOKIESxlmm",
                Context.MODE_PRIVATE);
        String cookies = sharedPreferences.getString("Cookies", "");
        Bundle bundle = new Bundle();
        bundle.putString("cookies", cookies);
        bundle.putString("actlink", jumpInfo.getUrl());
        intent.putExtras(bundle);
        context.startActivity(intent);
        break;
    }
  }

  private JumpInfo get_jump_info(String recvContent){

    JumpInfo jumpInfo = new JumpInfo();
    String[] content = recvContent.split(XlmmConst.JUMP_PREFIX);
    if(! content[1].isEmpty()) {
      if (content[1].contains("promote_today")) {
        jumpInfo.setType(XlmmConst.JUMP_PROMOTE_TODAY);
        jumpInfo.setUrl(content[1]);
      }
      else if(content[1].contains("promote_previous")) {
        jumpInfo.setType(XlmmConst.JUMP_PROMOTE_PREVIOUS);
        jumpInfo.setUrl(content[1]);
      }
      else if(content[1].contains("childlist")) {
        jumpInfo.setType(XlmmConst.JUMP_PRODUCT_CHILDLIST);
        jumpInfo.setUrl(content[1]);
      }
      else if(content[1].contains("ladylist")) {
        jumpInfo.setType(XlmmConst.JUMP_PRODUCT_LADYLIST);
        jumpInfo.setUrl(content[1]);
      }
      else if(content[1].contains("modelist")) {
        jumpInfo.setType(XlmmConst.JUMP_PRODUCT_MODELLIST);
        jumpInfo.setUrl(content[1]);
      }
      else if(content[1].contains("product_id")) {
        jumpInfo.setType(XlmmConst.JUMP_PRODUCT_DETAIL);
        jumpInfo.setUrl(content[1]);
      }
      else if(content[1].contains("trade_id")) {
        jumpInfo.setType(XlmmConst.JUMP_TRADE_DETAIL);
        jumpInfo.setUrl(content[1]);
      }
      else if(content[1].contains("usercoupons")) {
        jumpInfo.setType(XlmmConst.JUMP_USER_COUPON);
        jumpInfo.setUrl(content[1]);
      }
      else if(content[1].contains("webview")) {
        jumpInfo.setType(XlmmConst.JUMP_WEBVIEW);
        String url = content[1].substring(content[1].lastIndexOf("http"));
        if(url.contains("is_native")) {
          String temp[] = url.split("&is_native=");
          url = temp[0];
        }
        try {
          jumpInfo.setUrl(URLDecoder.decode(url, "utf-8"));
        }
        catch (Exception e){
          e.printStackTrace();
        }
      }
      else if(content[1].contains("vip_home")) {
        jumpInfo.setType(XlmmConst.JUMP_XIAOLUMAMA);
        jumpInfo.setUrl(content[1]);
      }
    }

    JUtils.Log(TAG, jumpInfo.toString());
    return  jumpInfo;
  }

  class JumpInfo{
    int type;
    String url;

    public int getType() {
      return type;
    }

    public String getUrl() {
      return url;
    }

    public void setType(int type) {
      this.type = type;
    }

    public void setUrl(String url) {
      this.url = url;
    }

    @Override public String toString() {
      return "JumpInfo{" +
          "type=" + type +
          ", url='" + url + '\'' +
          '}';
    }
  }
}
