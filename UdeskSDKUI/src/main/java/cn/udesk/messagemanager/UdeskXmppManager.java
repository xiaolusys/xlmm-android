package cn.udesk.messagemanager;

import android.os.Handler;
import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

import udesk.core.UdeskCoreConst;
import udesk.core.event.InvokeEventContainer;
import udesk.core.xmpp.XmppInfo;
import udesk.org.jivesoftware.smack.ConnectionConfiguration;
import udesk.org.jivesoftware.smack.ConnectionListener;
import udesk.org.jivesoftware.smack.PacketListener;
import udesk.org.jivesoftware.smack.SmackException;
import udesk.org.jivesoftware.smack.XMPPConnection;
import udesk.org.jivesoftware.smack.filter.OrFilter;
import udesk.org.jivesoftware.smack.filter.PacketFilter;
import udesk.org.jivesoftware.smack.filter.PacketTypeFilter;
import udesk.org.jivesoftware.smack.packet.IQ;
import udesk.org.jivesoftware.smack.packet.Message;
import udesk.org.jivesoftware.smack.packet.Packet;
import udesk.org.jivesoftware.smack.packet.Presence;
import udesk.org.jivesoftware.smack.provider.ProviderManager;
import udesk.org.jivesoftware.smack.tcp.XMPPTCPConnection;
import udesk.org.jivesoftware.smack.util.StringUtils;
import udesk.org.jivesoftware.smackx.receipts.DeliveryReceipt;
import udesk.org.jivesoftware.smackx.receipts.DeliveryReceiptManager;

public class UdeskXmppManager implements ConnectionListener, PacketListener {

    private XMPPTCPConnection xmppConnection = null;
    private Message xmppMsg;
    private PacketFilter msgfilter = new PacketTypeFilter(Message.class);
    private PacketFilter presenceFilter = new PacketTypeFilter(Presence.class);
    private PacketFilter iQFilter = new PacketTypeFilter(IQ.class);
    ConnectionConfiguration mConfiguration;
    private Handler handler = new Handler();

    volatile boolean isConnecting = false;

    public UdeskXmppManager() {

    }


    public synchronized boolean startLoginXmpp() {

        if (TextUtils.isEmpty(XmppInfo.getInstance().getLoginServer())
                || TextUtils.isEmpty(XmppInfo.getInstance().getLoginPassword())
                || TextUtils.isEmpty(XmppInfo.getInstance().getLoginName())) {
            return false;
        }
        return this.startLoginXmpp(XmppInfo.getInstance().getLoginName(),
                XmppInfo.getInstance().getLoginPassword(),
                XmppInfo.getInstance().getLoginServer(),
                XmppInfo.getInstance().getLoginPort());
    }

    /**
     * @param loginName
     * @param loginPassword
     * @param loginServer
     * @param loginPort
     */
    public synchronized boolean startLoginXmpp(String loginName,
                                               String loginPassword, String loginServer, int loginPort) {
        if (!isConnecting) {
            isConnecting = true;
            try {
                if (TextUtils.isEmpty(loginServer) || TextUtils.isEmpty(loginName)
                        || TextUtils.isEmpty(loginPassword)) {
                    return false;
                }
                if (mConfiguration == null) {
                    init(loginServer, loginPort);
                }

                if (xmppConnection != null && !xmppConnection.isConnected()) {
                    ProviderManager.addExtensionProvider(
                            "action",
                            "udesk:action",
                            new ActionMsgReceive());
                    xmppConnection.removePacketListener(this);
                    xmppConnection.addPacketListener(this, new OrFilter(msgfilter,
                            presenceFilter, iQFilter));
                    xmppConnection.removeConnectionListener(this);
                    xmppConnection.addConnectionListener(this);
                    return connectXMPPServer(loginName, loginPassword);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                isConnecting = false;
            }
        }
        return false;
    }

    private void init(String domain, int port) {
        mConfiguration = new ConnectionConfiguration(domain, port, domain);
        mConfiguration
                .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
        mConfiguration.setDebuggerEnabled(UdeskCoreConst.xmppDebug);
        xmppConnection = new XMPPTCPConnection(mConfiguration);

    }

    /**
     * 连接xmpp服务器
     */
    private synchronized boolean connectXMPPServer(final String xmppLoginName,
                                                   final String xmppLoginPassword) {
        try {
            xmppConnection.connect();
            xmppConnection.login(xmppLoginName, xmppLoginPassword, UUID.randomUUID().toString());
            xmppConnection.sendPacket(new Presence(Presence.Type.available));
            if (handler != null) {
                handler.post(runnable);
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            sendSelfStatus();
            if (handler != null) {
                handler.postDelayed(this, 15000);
            }

        }
    };

    private void sendSelfStatus() {
        try {
            Presence statusPacket = new Presence(Presence.Type.available);
            statusPacket.setStatus("online");
            if (xmppConnection != null) {
                xmppConnection.sendPacket(statusPacket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param type  消息类型   文本 :message ;
     * @param text  消息内容语音和图片是个链接地址
     * @param msgId 消息的id
     * @param to    发给客服的jid
     */
    public void sendTxtMessage(String type, String text, String msgId, String to) {
        this.sendMessage(type, text, msgId, to, 0);
    }

    /**
     * @param type  图片:image;
     * @param text  消息内容语音和图片是个链接地址
     * @param msgId 消息的id
     * @param to    发给客服的jid
     */
    public void sendImgMessage(String type, String text, String msgId, String to) {
        this.sendMessage(type, text, msgId, to, 0);
    }

    /**
     * @param type  语音:audio
     * @param text  消息内容语音和图片是个链接地址
     * @param msgId 消息的id
     * @param to    发给客服的jid
     */
    public void sendAudioMessage(String type, String text, String msgId, String to, long duration) {
        this.sendMessage(type, text, msgId, to, duration);
    }

    /**
     * 发送广告信息 :
     *
     * @param text
     * @param to
     */
    public void sendComodityMessage(String text, String to) {
        xmppMsg = new Message(to, Message.Type.chat);
        text = StringUtils.escapeForXML(text).toString();
        ProductXmpp product = new ProductXmpp();
        product.setBody(text);
        xmppMsg.addExtension(product);
        if (xmppConnection != null) {
            try {
                xmppConnection.sendPacket(xmppMsg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void sendActionMessage(String to) {
        xmppMsg = new Message(to, Message.Type.chat);
        xmppMsg.setPacketID(" ");
        ActionMsgXmpp actionMsgXmpp = new ActionMsgXmpp(ActionMsgXmpp.elementName, ActionMsgXmpp.namespace);
        actionMsgXmpp.setActionText("overready");
        actionMsgXmpp.setType("isover");
        xmppMsg.addExtension(actionMsgXmpp);
        if (xmppConnection != null) {
            try {
                xmppConnection.sendPacket(xmppMsg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void sendPreMessage(String type, String text, String to) {
        try {
            xmppMsg = new Message(to, Message.Type.chat);
            text = StringUtils.escapeForXML(text).toString();
            PreMsgXmpp preMsgXmpp = new PreMsgXmpp();
            xmppMsg.addExtension(preMsgXmpp);
            xmppMsg.setPacketID(" ");
            JSONObject json = new JSONObject();
            json.put("type", type);
            JSONObject data = new JSONObject();
            data.put("content", text);
            json.put("data", data);
            json.put("platform", "android");
            json.put("version", UdeskCoreConst.sdkversion);
            xmppMsg.setBody(json.toString());
            xmppConnection.sendPacket(xmppMsg);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * @param type     消息类型   文本 :message ;图片:image;语音:audio
     * @param text     消息内容语音和图片是个链接地址
     * @param msgId    消息的id
     * @param to       发给客服的jid
     * @param duration 时长  默认传0,语音的发送语音的时长
     */
    public boolean sendMessage(String type, String text, String msgId, String to, long duration) {
        try {
            xmppMsg = new Message(to, Message.Type.chat);
            text = StringUtils.escapeForXML(text).toString();
            xmppMsg.setPacketID(msgId);
            JSONObject json = new JSONObject();
            json.put("type", type);
            JSONObject data = new JSONObject();
            data.put("content", text);
            data.put("duration", duration);
            json.put("data", data);
            json.put("platform", "android");
            json.put("version", UdeskCoreConst.sdkversion);
            xmppMsg.setBody(json.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        if (xmppConnection != null) {
            try {
                DeliveryReceiptManager.addDeliveryReceiptRequest(xmppMsg);
                xmppConnection.sendPacket(xmppMsg);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }


    private void processPresence(Presence pre) {

        if (pre.getType().equals(Presence.Type.subscribe)) {
            Presence presencePacket = new Presence(Presence.Type.subscribed);
            presencePacket.setTo(pre.getFrom());
            try {
                xmppConnection.sendPacket(presencePacket);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (pre.getType().equals(Presence.Type.unavailable)) {
            InvokeEventContainer.getInstance().event_OnNewPresence.invoke(pre.getFrom(), UdeskCoreConst.OFFLINEFLAG);
        } else if (!TextUtils.isEmpty(pre.getStatus())) {
            InvokeEventContainer.getInstance().event_OnNewPresence.invoke(pre.getFrom(), UdeskCoreConst.ONLINEFLAG);
        }
    }

    private void processMessage(Message message) {
        // 收到回执消息
        if (message.getExtension("received", "urn:xmpp:receipts") != null) {
            DeliveryReceipt received = (DeliveryReceipt) message.getExtension("received", "urn:xmpp:receipts");
            if (received != null && !TextUtils.isEmpty(received.getId())) {
                InvokeEventContainer.getInstance().event_OnMessageReceived.invoke(received.getId());
            }
            return;
        }

        if (message.getExtension("isreqsurvey", "survey") != null && message.getExtension("delay", "urn:xmpp:delay") == null) {
            InvokeEventContainer.getInstance().event_OnReqsurveyMsg.invoke(true);
            return;
        }

        if (message.getExtension("action", "udesk:action") != null) {
            ActionMsgXmpp actionMsgXmpp = (ActionMsgXmpp) message.getExtension("action", "udesk:action");
            if (actionMsgXmpp != null) {
                InvokeEventContainer.getInstance().event_OnActionMsg.invoke(actionMsgXmpp.getActionText(), message.getFrom());
            }
            return;
        }

        String id = message.getPacketID();
        if (TextUtils.isEmpty(id)) {
            return;
        }
        if (message.getExtension("request", "urn:xmpp:receipts") != null) {
            ReceivedXmpp newUserInfoXmpp = new ReceivedXmpp();
            newUserInfoXmpp.setMsgId(id);
            xmppMsg = new Message(message.getFrom(), Message.Type.chat);
            xmppMsg.addExtension(newUserInfoXmpp);
            try {
                xmppConnection.sendPacket(xmppMsg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (message.getBody() != null) {
            String type = "";
            String content = "";
            long duration = 0;
            try {
                JSONObject json = new JSONObject(message.getBody());
                if (json.has("type")) {
                    type = json.optString("type");
                }
                if (json.has("data")) {
                    JSONObject data = json.getJSONObject("data");
                    if (data.has("content")) {
                        content = data.optString("content");
                    }
                    if (data.has("duration")) {
                        duration = data.optLong("duration");
                    }

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (!TextUtils.isEmpty(type) && !TextUtils.isEmpty(content)) {
                InvokeEventContainer.getInstance().event_OnNewMessage.invoke(message.getFrom(), type, id, content, duration);
            }
        }
    }


    /**
     * 断开连接
     */
    public boolean cancel() {
        try {
            if (xmppConnection != null) {
                xmppConnection.removePacketListener(UdeskXmppManager.this);
                xmppConnection.removeConnectionListener(UdeskXmppManager.this);
                handler.removeCallbacks(runnable);
                xmppConnection.disconnect();
                xmppConnection = null;
            }
            if (mConfiguration != null) {
                mConfiguration = null;
            }
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public void connected(XMPPConnection arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void authenticated(XMPPConnection connection) {

    }

    @Override
    public void connectionClosed() {

    }

    @Override
    public void connectionClosedOnError(Exception arg0) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    cancel();
                    Thread.sleep(1000);
                    startLoginXmpp();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        }).start();
    }

    @Override
    public void reconnectingIn(int arg0) {

    }

    @Override
    public void reconnectionFailed(Exception arg0) {

    }

    @Override
    public void reconnectionSuccessful() {

    }

    @Override
    public void processPacket(Packet packet) throws SmackException.NotConnectedException {
        if (packet instanceof Message) {
            Message message = (Message) packet;
            processMessage(message);

        } else if (packet instanceof Presence) {
            Presence pre = (Presence) packet;
            processPresence(pre);
        }
    }

    public boolean isConnect() {
        if (xmppConnection != null) {
            return xmppConnection.isConnected();
        }
        return false;
    }

}
