package cn.udesk.messagemanager;

import android.text.TextUtils;

import java.util.concurrent.ExecutorService;

import cn.udesk.UdeskConst;
import cn.udesk.UdeskSDKManager;
import cn.udesk.db.UdeskDBManager;
import cn.udesk.model.MsgNotice;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;
import udesk.core.event.InvokeEventContainer;
import udesk.core.event.ReflectInvokeMethod;
import udesk.core.model.MessageInfo;


public class UdeskMessageManager {

    private volatile static UdeskMessageManager instance;
    private UdeskXmppManager mUdeskXmppManager;
    private ExecutorService messageExecutor;

    public ReflectInvokeMethod eventui_OnMessageReceived = new ReflectInvokeMethod(new Class<?>[]{String.class});
    public ReflectInvokeMethod eventui_OnNewMessage = new ReflectInvokeMethod(new Class<?>[]{MessageInfo.class});
    public ReflectInvokeMethod eventui_OnNewPresence = new ReflectInvokeMethod(new Class<?>[]{String.class, Integer.class});
    public ReflectInvokeMethod eventui_OnReqsurveyMsg = new ReflectInvokeMethod(new Class<?>[]{Boolean.class});
    public ReflectInvokeMethod event_OnNewMsgNotice = new ReflectInvokeMethod(new Class<?>[]{MsgNotice.class});

    private UdeskMessageManager() {
        bindEvent();
        mUdeskXmppManager = new UdeskXmppManager();
        ensureMessageExecutor();
    }

    public static UdeskMessageManager getInstance() {
        if (instance == null) {
            synchronized (UdeskMessageManager.class) {
                if (instance == null) {
                    instance = new UdeskMessageManager();
                }
            }
        }
        return instance;
    }

    private void ensureMessageExecutor() {
        if (messageExecutor == null) {
            messageExecutor = Concurrents
                    .newSingleThreadExecutor("messageExecutor");
        }
    }

    public Observable<Boolean> loginXmppService() {

        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                Boolean isConetted = mUdeskXmppManager.startLoginXmpp();
                subscriber.onNext(isConetted);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io());


    }

    public Observable<Boolean> cancelXmppConnect() {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                boolean isCancel = mUdeskXmppManager.cancel();
                subscriber.onNext(isCancel);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io());
    }

    public void sendMessage(String type, String text, String msgId, String to, long duration) {
        mUdeskXmppManager.sendMessage(type, text, msgId, to, duration);
    }

    public void sendComodityMessage(String text, String to) {
        mUdeskXmppManager.sendComodityMessage(text, to);
    }

    public void sendPreMsg(String type, String text, String to) {
        mUdeskXmppManager.sendPreMessage(type, text, to);
    }

    private void bindEvent() {
        InvokeEventContainer.getInstance().event_OnNewMessage.bind(this, "onNewMessage");
        InvokeEventContainer.getInstance().event_OnMessageReceived.bind(this, "onMessageReceived");
        InvokeEventContainer.getInstance().event_OnNewPresence.bind(this, "onNewPresence");
        InvokeEventContainer.getInstance().event_OnReqsurveyMsg.bind(this, "onReqsurveyMsg");
        InvokeEventContainer.getInstance().event_OnActionMsg.bind(this, "onActionMsg");
    }


    public void clean() {
        InvokeEventContainer.getInstance().event_OnNewMessage.unBind(this);
        InvokeEventContainer.getInstance().event_OnMessageReceived.unBind(this);
        InvokeEventContainer.getInstance().event_OnNewPresence.unBind(this);
        InvokeEventContainer.getInstance().event_OnReqsurveyMsg.unBind(this);
        InvokeEventContainer.getInstance().event_OnActionMsg.unBind(this);
    }

    public void onMessageReceived(final String msgId) {

        ensureMessageExecutor();
        messageExecutor.submit(new Runnable() {
            @Override
            public void run() {
                UdeskDBManager.getInstance().updateMsgSendFlag(msgId, UdeskConst.SendFlag.RESULT_SUCCESS);
                UdeskDBManager.getInstance().deleteSendingMsg(msgId);
                eventui_OnMessageReceived.invoke(msgId);
            }
        });
    }


    public void onNewMessage(String agentJid, final String type, final String msgId, final String content,
                             final Long duration) {
        String jid[] = agentJid.split("/");
        final MessageInfo msginfo = buildReceiveMessage(jid[0], type, msgId, content, duration);
        if (UdeskDBManager.getInstance().hasReceviedMsg(msgId)) {
            return;
        }
        ensureMessageExecutor();
        if (!type.equals(UdeskConst.ChatMsgTypeString.TYPE_REDIRECT)) {
            messageExecutor.submit(new Runnable() {

                @Override
                public void run() {

                    UdeskDBManager.getInstance().addMessageInfo(msginfo);
                }
            });
        }
        eventui_OnNewMessage.invoke(msginfo);

        if (type.equals(UdeskConst.ChatMsgTypeString.TYPE_REDIRECT)) {
            return;
        }
        if (UdeskSDKManager.getInstance().isNeedMsgNotice()) {
            MsgNotice msgNotice = new MsgNotice(msgId, type, content);
            event_OnNewMsgNotice.invoke(msgNotice);
        }

    }


    public MessageInfo buildReceiveMessage(String agentJid, String msgType, String msgId,
                                           String content, long duration) {
        MessageInfo msg = new MessageInfo();
        msg.setMsgtype(msgType);
        msg.setTime(System.currentTimeMillis());
        msg.setMsgId(msgId);
        msg.setDirection(UdeskConst.ChatMsgDirection.Recv);
        msg.setSendFlag(UdeskConst.SendFlag.RESULT_SUCCESS);
        msg.setReadFlag(UdeskConst.ChatMsgReadFlag.unread);
        msg.setMsgContent(content);
        msg.setPlayflag(UdeskConst.PlayFlag.NOPLAY);
        msg.setLocalPath("");
        msg.setDuration(duration);
        msg.setmAgentJid(agentJid);
        return msg;
    }

    public void onNewPresence(String jid, Integer onlineflag) {
        eventui_OnNewPresence.invoke(jid, onlineflag);

    }

    public void onReqsurveyMsg(Boolean isSurvey) {
//        if (!UdeskSDKManager.getInstance().isNeedMsgNotice()) {
            eventui_OnReqsurveyMsg.invoke(isSurvey);
//        }

    }

    public void onActionMsg(String actionText, String agentJId) {
        if (TextUtils.isEmpty(actionText)) {
            return;
        }
        if (actionText.equals("overtest")) {
            mUdeskXmppManager.sendActionMessage(agentJId);
        } else if (actionText.equals("over")) {
            wrapCancelXmppConnect();
            InvokeEventContainer.getInstance().event_OnOverConversation.invoke(true);
        }

    }

    public boolean isConnected() {
        return mUdeskXmppManager.isConnect();
    }

    private void wrapCancelXmppConnect() {
        UdeskMessageManager.getInstance().cancelXmppConnect().subscribe(new Subscriber<Boolean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Boolean aBoolean) {

            }
        });
    }

}
