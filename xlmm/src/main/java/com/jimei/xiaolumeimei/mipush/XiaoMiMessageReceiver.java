package com.jimei.xiaolumeimei.mipush;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.XlmmApp;
import com.jimei.xiaolumeimei.base.BaseAutoLayoutActivity;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.entities.MiPushProductDetailBean;
import com.jimei.xiaolumeimei.entities.XiaoMiPushContent;
import com.jimei.xiaolumeimei.ui.mminfo.MMInfoActivity;
import com.jimei.xiaolumeimei.utils.JumpUtils;
import com.jimei.xiaolumeimei.utils.LoginUtils;
import com.jimei.xiaolumeimei.utils.ViewUtils;
import com.jude.utils.JUtils;
import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;

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

        if (!TextUtils.isEmpty(message.getTopic())) {
            mTopic = message.getTopic();
        } else if (!TextUtils.isEmpty(message.getAlias())) {
            mAlias = message.getAlias();
        }
        Message msg = Message.obtain();
        XiaoMiPushHandler handler = null;
        String content = message.getContent();
        String description = message.getDescription();
        if (content != null && content.contains("mama_ordercarry_broadcast")) {
            msg.obj = content;
            msg.what = XlmmConst.MI_MAMA_ORDER_CARRY_BROADCAST;
            handler = MMInfoActivity.getHandler();
        } else if (content != null && content.contains("product_detail")) {
            msg.obj = content;
            msg.what = XlmmConst.MI_PRODUCT_DETAIL;
            handler = BaseAutoLayoutActivity.getHandler();
        } else if (description != null && description.contains("mama_ordercarry_broadcast")) {
            msg.obj = description;
            msg.what = XlmmConst.MI_MAMA_ORDER_CARRY_BROADCAST;
            handler = MMInfoActivity.getHandler();
        } else if (description != null && description.contains("product_detail")) {
            msg.obj = description;
            msg.what = XlmmConst.MI_PRODUCT_DETAIL;
            handler = BaseAutoLayoutActivity.getHandler();
        }
        if (handler != null) {
            handler.sendMessage(msg);
        }
    }

    @Override
    public void onNotificationMessageClicked(Context context, MiPushMessage message) {
        JUtils.Log(TAG, "onNotificationMessageClicked is called. " + message.toString());
        JUtils.Log(TAG, "content:" + message.getContent());

        XiaoMiPushContent miPushContent = null;
        if ((message.getContent() != null) && (!message.getContent().isEmpty())) {
            try {
                Gson mgson = new Gson();
                miPushContent = mgson.fromJson(
                        message.getContent(), XiaoMiPushContent.class);
                JUtils.Log(TAG, "target url " + miPushContent.getTargetUrl());
            } catch (Exception e) {
                e.printStackTrace();

            }
        }

        if ((miPushContent != null)
                && (miPushContent.getTargetUrl() != null)
                && (!miPushContent.getTargetUrl().isEmpty())) {
            JumpUtils.push_jump_proc(context, miPushContent.getTargetUrl());
        }


//        String log = context.getString(R.string.click_notification_message, message.getContent());
//        MainActivity.logList.add(0, getSimpleDate() + " " + log);
//        if (!TextUtils.isEmpty(message.getTopic())) {
//            mTopic = message.getTopic();
//        } else if (!TextUtils.isEmpty(message.getAlias())) {
//            mAlias = message.getAlias();
//        }
//        Message msg = Message.obtain();
//        if (message.isNotified()) {
//            msg.obj = log;
//        }
//        XlmmApp.getHandler().sendMessage(msg);
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

    @Override
    public void onCommandResult(Context context, MiPushCommandMessage message) {
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

    @SuppressLint("SimpleDateFormat")
    public static String getSimpleDate() {
        return new SimpleDateFormat("MM-dd hh:mm:ss").format(new Date());
    }

    public static class XiaoMiPushHandler extends Handler {

        private Context context;
        private boolean flag;

        public XiaoMiPushHandler(Context context) {
            this.context = context;
            flag = true;
        }

        @Override
        public void handleMessage(Message msg) {
            String s = (String) msg.obj;
            Gson gson = new Gson();
            if (!TextUtils.isEmpty(s)) {
                switch (msg.what) {
                    case XlmmConst.MI_PRODUCT_DETAIL:
                        MiPushProductDetailBean miPushProductDetailBean = gson.fromJson(s, new TypeToken<MiPushProductDetailBean>() {
                        }.getType());
                        View view = LayoutInflater.from(context).inflate(R.layout.push_dialog, null);
                        ((TextView) view.findViewById(R.id.name)).setText(miPushProductDetailBean.getName());
                        ImageView imageView = (ImageView) view.findViewById(R.id.img);
                        ViewUtils.loadImgToImgView(context, imageView, miPushProductDetailBean.getImg());
                        view.setOnClickListener(v -> {
                            Intent intent = new Intent();
                            intent.setClassName(miPushProductDetailBean.getPackageName(), miPushProductDetailBean.getClassName());
                            Bundle bundle = new Bundle();
                            bundle.putInt("model_id", miPushProductDetailBean.getModel_id());
                            intent.putExtras(bundle);
                            context.startActivity(intent);
                        });
                        Dialog dialog = new Dialog(context, R.style.CustomDialog);
                        dialog.setContentView(view);
                        dialog.setCancelable(true);
                        dialog.show();
                        break;
                    case XlmmConst.MI_MAMA_ORDER_CARRY_BROADCAST:
//                        MiPushOrderCarryBean miPushOrderCarryBean = gson.fromJson(s, new TypeToken<MiPushOrderCarryBean>() {
//                        }.getType());
//                        while (flag) {
//                            flag = false;
//                            if (context instanceof MMInfoActivity) {
//                                MMInfoActivity activity = (MMInfoActivity) context;
//                                View layout = activity.findViewById(R.id.mi_layout);
//                                ImageView headImg = (ImageView) activity.findViewById(R.id.mi_head);
//                                TextView infoTv = (TextView) activity.findViewById(R.id.mi_info);
//                                if (infoTv != null && headImg != null && layout != null) {
//                                    infoTv.setText(miPushOrderCarryBean.getContent());
//                                    ViewUtils.loadImgToImgViewWithTransformCircle(this.context, headImg, miPushOrderCarryBean.getAvatar());
//                                    layout.setVisibility(View.VISIBLE);
//                                    new Thread(() -> {
//                                        SystemClock.sleep(3000);
//                                        activity.runOnUiThread(() -> layout.setVisibility(View.GONE));
//                                    }).start();
//                                    new Thread(() -> {
//                                        SystemClock.sleep(5000);
//                                        flag = true;
//                                    }).start();
//                                }
//                            }
//                        }
                        break;
                }
            }
        }
    }
}
