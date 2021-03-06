package cn.udesk.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tbruyelle.rxpermissions.RxPermissions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.udesk.R;
import cn.udesk.StatusBarUtil;
import cn.udesk.UdeskConst;
import cn.udesk.UdeskSDKManager;
import cn.udesk.UdeskUtil;
import cn.udesk.activity.MessageAdatper.AudioViewHolder;
import cn.udesk.adapter.UDEmojiAdapter;
import cn.udesk.config.UdekConfigUtil;
import cn.udesk.config.UdeskConfig;
import cn.udesk.db.UdeskDBManager;
import cn.udesk.messagemanager.UdeskMessageManager;
import cn.udesk.model.SurveyOptionsModel;
import cn.udesk.model.UdeskCommodityItem;
import cn.udesk.presenter.ChatActivityPresenter;
import cn.udesk.presenter.IChatActivityView;
import cn.udesk.voice.RecordFilePlay;
import cn.udesk.voice.RecordPlay;
import cn.udesk.voice.RecordPlayCallback;
import cn.udesk.voice.RecordStateCallback;
import cn.udesk.voice.RecordTouchListener;
import cn.udesk.widget.HorVoiceView;
import cn.udesk.widget.UDPullGetMoreListView;
import cn.udesk.widget.UdeskConfirmPopWindow;
import cn.udesk.widget.UdeskConfirmPopWindow.OnPopConfirmClick;
import cn.udesk.widget.UdeskExpandableLayout;
import cn.udesk.widget.UdeskMultiMenuHorizontalWindow;
import cn.udesk.widget.UdeskMultiMenuHorizontalWindow.OnPopMultiMenuClick;
import cn.udesk.widget.UdeskTitleBar;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import udesk.core.UdeskCoreConst;
import udesk.core.UdeskHttpFacade;
import udesk.core.event.InvokeEventContainer;
import udesk.core.model.AgentInfo;
import udesk.core.model.MessageInfo;
import udesk.core.utils.UdeskUtils;

public class UdeskChatActivity extends Activity implements IChatActivityView,
        OnClickListener, OnTouchListener, OnLongClickListener,
        OnItemClickListener, RecordStateCallback, HorVoiceView.UdeskTimeCallback {

    private UdeskTitleBar mTitlebar;
    private RecordFilePlay mRecordFilePlay;
    private RecordPlayCallback mPlayCallback;
    // 标记当前是否有客服在线，客服不在线状态是不能发送消息的，
    private boolean currentStatusIsOnline = false;
    private int historyCount = 0; // 记录数据库中总的记录数
    private int offset = -1; // 标记偏移值
    private Uri photoUri;
    private AgentInfo mAgentInfo;  // 保存客服信息的实例
    private final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 101;
    private final int SELECT_IMAGE_ACTIVITY_REQUEST_CODE = 102;
    private final int SELECT_SURVY_OPTION_REQUEST_CODE = 103;
    private final long QUEUE_RETEY_TIME = 10 * 1000; // 客服繁忙时  轮询的间隔时间
    private final int initViewMode = 1;
    private final int pullRefreshModel = 2;
    private UdeskExpandableLayout expandableLayout = null;  //动画显示上线离线提醒的控件
    private boolean isNeedStartExpandabLyout = false;
    private UdeskConfirmPopWindow formWindow = null;
    private UdeskConfirmPopWindow bolckedWindow = null;
    private int agentFlag = UdeskConst.AgentFlag.NoAgent;
    private MessageInfo redirectMsg;
    private String groupId = "";
    private String agentId = "";
    private boolean isNeedRelogin = false;
    boolean hasSendCommodity = false;
    private long preMsgSendTime = 0;
    private String isbolcked = "";
    private Boolean isOverConversation = false;
    //控件重构
    private Button sendBtn;
    private EditText mInputEditView;
    private View showEmjoImg;//表情选择图片，用户可根据自己的需求自行设置。
    private GridView emjoGridView;
    private View emojisPannel;
    private UDEmojiAdapter mEmojiAdapter;
    private View btnPhoto, btnCamera;
    private UDPullGetMoreListView mListView;
    private MessageAdatper mChatAdapter;
    private View showVoiceImg;
    private View audioPanel;
    private HorVoiceView mHorVoiceView;
    private TextView udesk_audio_tips;
    private View audioCancle;
    private ImageView audioPop;


    private ChatActivityPresenter mPresenter = new ChatActivityPresenter(this);


    public static class MessageWhat {
        public static final int loadHistoryDBMsg = 1;
        public static final int NoAgent = 2;
        public static final int HasAgent = 3;
        public static final int WaitAgent = 4;
        public static final int refreshAdapter = 5;
        public static final int changeImState = 6;
        public static final int onNewMessage = 7;
        public static final int RECORD_ERROR = 8;
        public static final int RECORD_Too_Short = 10;
        public static final int UPDATE_VOCIE_STATUS = 12;
        public static final int recordllegal = 13;
        public static final int status_notify = 14;
        public static final int redirectSuccess = 15;
        public static final int surveyNotify = 16;
        public static final int IM_STATUS = 17;
        public static final int IM_BOLACKED = 18;
    }

    private BroadcastReceiver mConnectivityChangedReceiver = null;

    class ConnectivtyChangedReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (!ConnectivityManager.CONNECTIVITY_ACTION.equals(intent
                    .getAction()))
                return;
            boolean bNetWorkAvailabl = UdeskUtils.isNetworkConnected(context);
            if (bNetWorkAvailabl) {
                if (!currentStatusIsOnline && isNeedRelogin) {
                    if (isbolcked.equals("true")) {
                        return;
                    }
                    mPresenter.createIMCustomerInfo();
                }
            } else {
                isNeedRelogin = true;
                UdeskUtils.showToast(
                        context,
                        context.getResources().getString(
                                R.string.udesk_has_wrong_net));
                setNoAgentStatus(context.getResources().getString(
                        R.string.udesk_agent_connecting_error_net_uavailabl));
                currentStatusIsOnline = false;
            }
        }
    }

    private void registerNetWorkReceiver() {
        if (mConnectivityChangedReceiver == null) {
            mConnectivityChangedReceiver = new ConnectivtyChangedReceiver();
            UdeskChatActivity.this.registerReceiver(
                    mConnectivityChangedReceiver, new IntentFilter(
                            ConnectivityManager.CONNECTIVITY_ACTION));

        }
    }

    private void unRegister() {
        if (mConnectivityChangedReceiver != null) {
            UdeskChatActivity.this
                    .unregisterReceiver(mConnectivityChangedReceiver);
            mConnectivityChangedReceiver = null;
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (UdeskChatActivity.this.isFinishing()) {
                return;
            }
            switch (msg.what) {
                case MessageWhat.loadHistoryDBMsg:
                    if (mChatAdapter != null && mListView != null) {
                        List<MessageInfo> msgs = (ArrayList<MessageInfo>) msg.obj;
                        if (UdeskSDKManager.getInstance().getCommodity() != null) {
                            msgs.add(UdeskSDKManager.getInstance().getCommodity());
                        }
                        mChatAdapter.listAddItems(msgs);
                        mListView.onRefreshComplete();
                        if (msg.arg1 == initViewMode) {
                            mListView.setSelection(msgs.size());
                        } else {
                            mListView.setSelection(0);
                        }
                    }
                    break;
                case MessageWhat.NoAgent:
                    mAgentInfo = (AgentInfo) msg.obj;
                    setNoAgentStatus(mAgentInfo.getMessage());
                    agentFlag = UdeskConst.AgentFlag.NoAgent;
                    confirmToForm();
                    break;
                case MessageWhat.HasAgent:
                    mAgentInfo = (AgentInfo) msg.obj;
                    currentStatusIsOnline = true;
                    showOnlieStatus(mAgentInfo);
                    if (mPresenter != null) {
                        mPresenter.SelfretrySendMsg();
                    }
                    break;
                case MessageWhat.WaitAgent:
                    mAgentInfo = (AgentInfo) msg.obj;
                    setNoAgentStatus(mAgentInfo.getMessage());
                    agentFlag = UdeskConst.AgentFlag.WaitAgent;
                    this.postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            if (mPresenter != null) {
                                mPresenter.getAgentInfo();
                            }
                        }
                    }, QUEUE_RETEY_TIME);
                    break;
                case MessageWhat.refreshAdapter:
                    if (mChatAdapter != null) {
                        notifyRefresh();
                    }
                    break;
                case MessageWhat.changeImState:
                    String msgId = (String) msg.obj;
                    int flag = msg.arg1;
                    changeImState(msgId, flag);
                    break;
                case MessageWhat.onNewMessage:
                    MessageInfo msgInfo = (MessageInfo) msg.obj;
                    if (msgInfo.getMsgtype().equals(UdeskConst.ChatMsgTypeString.TYPE_REDIRECT)) {
                        try {
                            if (mPresenter != null) {
                                redirectMsg = msgInfo;
                                JSONObject json = new JSONObject(msgInfo.getMsgContent());
                                String agent_id = json.optString("agent_id");
                                String group_id = json.optString("group_id");
                                mPresenter.getRedirectAgentInfo(agent_id, group_id);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        if (mChatAdapter != null) {
                            if (mAgentInfo != null) {
                                msgInfo.setAgentUrl(mAgentInfo.getHeadUrl());
                                msgInfo.setNickName(mAgentInfo.getAgentNick());
                            }
                            mChatAdapter.addItem(msgInfo);
                            notifyRefresh();
                        }
                    }
                    break;
                case MessageWhat.RECORD_ERROR:
                    UdeskUtils.showToast(UdeskChatActivity.this, getResources()
                            .getString(R.string.udesk_im_record_error));
                    break;
                case MessageWhat.RECORD_Too_Short:
                    UdeskUtils.showToast(UdeskChatActivity.this, getResources()
                            .getString(R.string.udesk_label_hint_too_short));
                    break;
                case MessageWhat.UPDATE_VOCIE_STATUS:
                    updateRecordStatus(msg.arg1);
                    break;
                case MessageWhat.recordllegal:
                    UdeskUtils.showToast(UdeskChatActivity.this, getResources()
                            .getString(R.string.udesk_im_record_error));
                    break;
                case MessageWhat.status_notify:
                    int onlineflag = msg.arg1;
                    String jid = (String) msg.obj;
                    if (onlineflag == UdeskCoreConst.ONLINEFLAG) {
                        if (isbolcked.equals("true")) {
                            return;
                        }

                        if (mAgentInfo == null || !jid.contains(mAgentInfo.getAgentJid())) {
                            return;
                        }
                        if (!currentStatusIsOnline && isNeedStartExpandabLyout) {
                            expandableLayout.startAnimation(true);
                            currentStatusIsOnline = true;
                            isNeedStartExpandabLyout = false;
                        }
                        showOnlieStatus(mAgentInfo);
                        if (formWindow != null) {
                            formWindow.cancle();
                        }
                        if (!hasSendCommodity) {
                            hasSendCommodity = true;
                            sendCommodityMsg(UdeskSDKManager.getInstance().getCommodity());
                        }
                    } else if (onlineflag == UdeskCoreConst.OFFLINEFLAG) {
                        if (mPresenter != null) {
                            mPresenter.getIMStatus(mAgentInfo);
                        }
                    }
                    break;
                case MessageWhat.IM_STATUS:
                    String imStatus = (String) msg.obj;
                    if (imStatus.equals("off")) {
                        if (mAgentInfo != null) {
                            if (mTitlebar != null) {
                                mTitlebar
                                        .setLeftTextSequence(mAgentInfo.getAgentNick());
                                mTitlebar.getudeskStateImg().setImageResource(R.drawable.udesk_offline_status);
                            }
                        } else {
                            setNoAgentStatus(getResources().getString(
                                    R.string.udesk_label_customer_offline));
                        }
                        if (currentStatusIsOnline) {
                            expandableLayout.startAnimation(false);
                            currentStatusIsOnline = false;
                            isNeedStartExpandabLyout = true;
                        }
                        confirmToForm();
                    }
                    break;
                case MessageWhat.IM_BOLACKED:
                    isbolcked = (String) msg.obj;
                    if (isbolcked != null && isbolcked.equals("true")) {
                        setNoAgentStatus(
                                UdeskChatActivity.this
                                        .getString(R.string.add_bolcked_tips));
                        toBolckedView();
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
                    break;
                case MessageWhat.redirectSuccess:
                    MessageInfo redirectSuccessmsg = (MessageInfo) msg.obj;
                    if (mChatAdapter != null) {
                        mChatAdapter.addItem(redirectSuccessmsg);
                        notifyRefresh();
                    }
                    currentStatusIsOnline = true;
                    showOnlieStatus(mAgentInfo);

                    break;
                case MessageWhat.surveyNotify:

                    SurveyOptionsModel surveyOptions = (SurveyOptionsModel) msg.obj;
                    if (surveyOptions != null) {
                        toLuanchSurveyActivity(surveyOptions);
                    }
                    break;

            }
        }
    };

    private void toBolckedView() {
        try {

            String positiveLabel = this.getString(R.string.udesk_sure);
            String negativeLabel = this.getString(R.string.udesk_cancel);
            String title = this.getString(R.string.add_bolcked_tips);
            if (UdeskChatActivity.this.isFinishing()) {
                return;
            }
            if (!bolckedWindow.isShowing()) {
                bolckedWindow.show(this, this.getWindow().getDecorView(),
                        positiveLabel, negativeLabel, title,
                        new OnPopConfirmClick() {
                            public void onPositiveClick() {
                                finish();
                            }

                            @Override
                            public void onNegativeClick() {
                            }

                        });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //发送广告消息
    private void sendCommodityMsg(UdeskCommodityItem commodity) {
        if (commodity != null && mPresenter != null) {
            mPresenter.sendCommodityMessage(commodity);
        }
    }

    //启动满意度调查
    private void toLuanchSurveyActivity(SurveyOptionsModel surveyOptions) {
        Intent intent = new Intent();
        intent.setClass(UdeskChatActivity.this, SurvyDialogActivity.class);
        intent.putExtra(UdeskConst.SurvyDialogKey, surveyOptions);
        startActivityForResult(intent, SELECT_SURVY_OPTION_REQUEST_CODE);
    }

    //设置客服状态
    private void showOnlieStatus(AgentInfo mAgentInfo) {
        if (mAgentInfo == null || !currentStatusIsOnline) {
            return;
        }
        if (mTitlebar != null) {
            mTitlebar
                    .setLeftTextSequence(mAgentInfo.getAgentNick());
            mTitlebar.getudeskStateImg().setImageResource(R.drawable.udesk_online_status);
        }

    }

    public void onOverconversation(Boolean isOver) {
        isOverConversation = isOver;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.udesk_activity_im);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.udesk_titlebar_bg2), 0);
        InvokeEventContainer.getInstance().event_OnOverConversation.bind(this, "onOverconversation");
        UdeskUtil.initCrashReport(this);
        UdeskSDKManager.getInstance().setIsNeedMsgNotice(false);
        initIntent();
        initView();
        settingTitlebar();


    }

    //在指定客服组ID  或者指定客服ID  换传入值  其它的方式进入不会传值
    private void initIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            groupId = intent.getStringExtra(UdeskConst.UDESKGROUPID);
            agentId = intent.getStringExtra(UdeskConst.UDESKAGENTID);
        }
    }


    private void initView() {
        try {
            formWindow = new UdeskConfirmPopWindow(this);
            bolckedWindow = new UdeskConfirmPopWindow(this);
            sendBtn = (Button) findViewById(R.id.udesk_bottom_send);
            sendBtn.setOnClickListener(this);
            mInputEditView = (EditText) findViewById(R.id.udesk_bottom_input);
            mInputEditView.addTextChangedListener(new EditChangedListener());
            mInputEditView.setOnTouchListener(this);
            emojisPannel = findViewById(R.id.udesk_bottom_emojis);
            showEmjoImg = findViewById(R.id.udesk_bottom_show_emoji);
            showEmjoImg.setOnClickListener(this);
            mEmojiAdapter = new UDEmojiAdapter(this);
            emjoGridView = (GridView) findViewById(R.id.udesk_bottom_emoji_pannel);
            emjoGridView.setAdapter(mEmojiAdapter);
            emjoGridView.setOnItemClickListener(this);
            btnCamera = findViewById(R.id.udesk_bottom_option_camera);
            btnCamera.setOnClickListener(this);
            btnPhoto = findViewById(R.id.udesk_bottom_option_photo);
            btnPhoto.setOnClickListener(this);
            mListView = (UDPullGetMoreListView) findViewById(R.id.udesk_conversation);
            expandableLayout = (UdeskExpandableLayout) findViewById(R.id.udesk_change_status_info);

            showVoiceImg = findViewById(R.id.udesk_bottom_voice_rl);
            showVoiceImg.setOnClickListener(this);
            audioPanel = findViewById(R.id.udesk_bottom_audios);
            mHorVoiceView = (HorVoiceView) findViewById(R.id.udesk_horvoiceview);
            udesk_audio_tips = (TextView) findViewById(R.id.udesk_audio_tips);
            audioCancle = findViewById(R.id.udesk_audio_cancle_image);
            audioPop = (ImageView) findViewById(R.id.udesk_audio_pop);

            setListView();
            initDatabase();
            mPresenter.createIMCustomerInfo();
            if (UdeskUtils.isNetworkConnected(this)) {
                isNeedRelogin = false;
            } else {
                isNeedRelogin = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        registerNetWorkReceiver();
    }

    /**
     * titlebar 的设置
     */
    private void settingTitlebar() {
        mTitlebar = (UdeskTitleBar) findViewById(R.id.udesktitlebar);
        if (mTitlebar != null) {
            UdekConfigUtil.setUITextColor(UdeskConfig.udeskTitlebarTextLeftRightResId, mTitlebar.getLeftTextView(), mTitlebar.getRightTextView());
            UdekConfigUtil.setUIbgDrawable(UdeskConfig.udeskTitlebarBgResId, mTitlebar.getRootView());
            if (UdeskConfig.DEFAULT != UdeskConfig.udeskbackArrowIconResId) {
                mTitlebar.getUdeskBackImg().setImageResource(UdeskConfig.udeskbackArrowIconResId);
            }
            mTitlebar
                    .setLeftTextSequence(getString(R.string.udesk_agent_connecting));
            mTitlebar.setLeftLinearVis(View.VISIBLE);
            mTitlebar.setLeftViewClick(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }


    private void setNoAgentStatus(String message) {
        if (mTitlebar != null) {
            mTitlebar.setLeftTextSequence(message);
        }
    }

    private void initaduioPannel() {
        try {
            setUdeskAudioPanelVis(View.VISIBLE);
            setHorVoiceViewVis(View.GONE);
            setAudioCancleViewVis(View.GONE);
            setAudiotipsVis(View.VISIBLE);
            setAudiotiptext(getString(R.string.udesk_voice_init));
            audioPop.setOnLongClickListener(this);
            audioPop.setOnTouchListener(null);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setUdeskAudioPanelVis(int vis) {
        audioPanel.setVisibility(vis);
    }

    private void setHorVoiceViewVis(int vis) {
        mHorVoiceView.setVisibility(vis);
    }

    private void setAudiotipsVis(int vis) {
        udesk_audio_tips.setVisibility(vis);
    }

    private void setAudioCancleViewVis(int vis) {
        audioCancle.setVisibility(vis);
    }

    private void setAudiotiptext(String s) {
        udesk_audio_tips.setText(s);
    }


    private void setUdeskEmojisPannel(int vis) {
        emojisPannel.setVisibility(vis);
        emjoGridView.setVisibility(View.VISIBLE);
    }

    private void setUdeskEditClickabled(EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
    }

    private void setListView() {
        mChatAdapter = new MessageAdatper(this);
        mListView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        mListView.setAdapter(mChatAdapter);
        mListView
                .setOnRefreshListener(new UDPullGetMoreListView.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        loadHistoryRecords(pullRefreshModel);
                    }
                });

        mListView.setRecyclerListener(new AbsListView.RecyclerListener() {
            public void onMovedToScrapHeap(View view) {
                if (mRecordFilePlay != null) {
                    checkRecoredView(view);
                }
            }
        });
    }

    private void initDatabase() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                UdeskDBManager.getInstance().setContext(UdeskChatActivity.this);
                historyCount = UdeskDBManager.getInstance().getMessageCount();
                UdeskDBManager.getInstance().updateSendFlagToFail();
                UdeskDBManager.getInstance().updateAllMsgRead();
                loadHistoryRecords(initViewMode);
            }
        });

    }

    /**
     * 读取数据库中的历史数据
     */
    private void loadHistoryRecords(int mode) {
        mListView.setTranscriptMode(ListView.TRANSCRIPT_MODE_NORMAL);
        // 已经没有更早的数据了
        if (offset == 0) {
            UdeskUtils.showToast(this,
                    getString(R.string.udesk_no_more_history));
            mListView.onRefreshComplete();
            mListView.setSelection(0);
        } else {
            // 还有老数据
            if (offset == -1) {
                offset = historyCount - UdeskConst.UDESK_HISTORY_COUNT;
            } else {
                offset = offset - UdeskConst.UDESK_HISTORY_COUNT;
            }
            offset = (offset < 0 ? 0 : offset);
            List<MessageInfo> list = UdeskDBManager.getInstance().getMessages(
                    offset, UdeskConst.UDESK_HISTORY_COUNT);
            Message msg = Message.obtain();
            msg.what = MessageWhat.loadHistoryDBMsg;
            msg.arg1 = mode;
            msg.obj = list;
            mHandler.sendMessage(msg);
        }

    }

    private void notifyRefresh() {
        mChatAdapter.notifyDataSetChanged();
        mListView.smoothScrollToPosition(mChatAdapter.getCount());
    }

    private void checkRecoredView(View view) {
        Object tag = view.getTag();
        if (tag == null || !(tag instanceof AudioViewHolder)) {
            return;
        }

        AudioViewHolder holder = (AudioViewHolder) tag;
        final RecordFilePlay recordFilePlay = mRecordFilePlay;
        if (recordFilePlay != null) {
            String path = recordFilePlay.getMediaPath();
            if (path != null
                    && (path.equalsIgnoreCase(holder.message.getLocalPath()) || path
                    .equalsIgnoreCase(holder.message.getMsgContent()))) {
                recordFilePlay.recycleCallback();
            }
        }
    }

    /**
     * 根据消息的ID 修改发送状态
     */
    private void changeImState(String msgId, int state) {
        if (!TextUtils.isEmpty(msgId) && mListView != null
                && mChatAdapter != null) {
            for (int i = mListView.getChildCount() - 1; i >= 0; i--) {
                View child = mListView.getChildAt(i);
                if (child != null) {
                    if (mChatAdapter.changeImState(child, msgId, state)) {
                        return;
                    }
                }
            }
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showFailToast(final String failMsg) {

        this.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                UdeskUtils.showToast(UdeskChatActivity.this, failMsg);
            }
        });
    }

    /**
     * 处理请求获取到客服信息
     *
     * @param agentInfo
     */
    @Override
    public void dealAgentInfo(final AgentInfo agentInfo) {

        switch (agentInfo.getAgentCode()) {
            case UdeskConst.AgentReponseCode.NoAgent:
                Message msgNoAgent = mHandler.obtainMessage(MessageWhat.NoAgent);
                msgNoAgent.obj = agentInfo;
                mHandler.sendMessage(msgNoAgent);
                break;
            case UdeskConst.AgentReponseCode.HasAgent:
                // 有客服连接xmpp, titlebar上显示
                UdeskMessageManager.getInstance().cancelXmppConnect().flatMap(new Func1<Boolean, Observable<Boolean>>() {
                    @Override
                    public Observable<Boolean> call(Boolean aBoolean) {
                        return UdeskMessageManager.getInstance().loginXmppService();
                    }
                }).subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {
                            UdeskDBManager.getInstance().addAgentInfo(agentInfo);
                            Message msgHasAgent = mHandler.obtainMessage(MessageWhat.HasAgent);
                            msgHasAgent.obj = agentInfo;
                            mHandler.sendMessage(msgHasAgent);
                        }
                    }
                });
                break;
            case UdeskConst.AgentReponseCode.WaitAgent:
                Message msgWaitAgent = mHandler
                        .obtainMessage(MessageWhat.WaitAgent);
                msgWaitAgent.obj = agentInfo;
                mHandler.sendMessage(msgWaitAgent);
                break;
            case UdeskConst.AgentReponseCode.NonExistentAgent:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(UdeskChatActivity.this, getString(R.string.udesk_nonexistent_agent), Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case UdeskConst.AgentReponseCode.NonExistentGroupId:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(UdeskChatActivity.this, getString(R.string.udesk_nonexistent_groupId), Toast.LENGTH_SHORT).show();
                    }
                });
                break;

            default:
                break;
        }
    }

    /**
     * 处理请求转移客服获取到客服信息
     *
     * @param agentInfo
     */
    @Override
    public void dealRedirectAgentInfo(AgentInfo agentInfo) {
        switch (agentInfo.getAgentCode()) {
            case UdeskConst.AgentReponseCode.NoAgent:
                Message msgNoAgent = mHandler.obtainMessage(MessageWhat.NoAgent);
                msgNoAgent.obj = agentInfo;
                mHandler.sendMessage(msgNoAgent);
                break;
            case UdeskConst.AgentReponseCode.HasAgent:
                String redirectTip = getString(R.string.udesk_transfer_success) + agentInfo.getAgentNick() + getString(R.string.udesk_service);
                if (redirectMsg != null) {
                    redirectMsg.setMsgContent(redirectTip);
                    UdeskDBManager.getInstance().addMessageInfo(redirectMsg);
                }
                mAgentInfo = agentInfo;
                UdeskDBManager.getInstance().addAgentInfo(mAgentInfo);
                Message msgHasRedirect = mHandler.obtainMessage(MessageWhat.redirectSuccess);
                msgHasRedirect.obj = redirectMsg;
                mHandler.sendMessage(msgHasRedirect);
                break;
            case UdeskConst.AgentReponseCode.WaitAgent:
                Message msgWaitAgent = mHandler
                        .obtainMessage(MessageWhat.WaitAgent);
                msgWaitAgent.obj = agentInfo;
                mHandler.sendMessage(msgWaitAgent);
                break;
            default:
                break;
        }

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v == mInputEditView && event.getAction() == MotionEvent.ACTION_DOWN) {
            bottomoPannelBegginStatus();
        }
        return false;
    }

    @Override
    public CharSequence getInputContent() {
        return mInputEditView.getText();
    }

    @Override
    public void clearInputContent() {
        mInputEditView.setText("");
    }

    @Override
    public void onClick(View v) {
        //检查是否处在可发消息的状态
        if (!isShowNotSendMsg()) {
            UdeskUtils.hideSoftKeyboard(this, mInputEditView);
            return;
        }

        if (R.id.udesk_bottom_send == v.getId()) { //发送文本消息
            if (TextUtils.isEmpty(mInputEditView.getText().toString())) {
                UdeskUtils.showToast(UdeskChatActivity.this,
                        getString(R.string.udesk_send_message_empty));
                return;
            }
            arfreshAgent();
            mPresenter.sendTxtMessage();
        } else if (R.id.udesk_bottom_show_emoji == v.getId()) { // 显示表情面板

            if (emojisPannel.getVisibility() == View.VISIBLE) {
                bottomoPannelBegginStatus();
            } else {
                bottomoPannelBegginStatus();
                setUdeskEmojisPannel(View.VISIBLE);
            }
            setUdeskEditClickabled(mInputEditView);
            UdeskUtils.hideSoftKeyboard(this, mInputEditView);
        } else if (R.id.udesk_bottom_option_photo == v.getId()) {  //选择本地的图片
            arfreshAgent();
            RxPermissions.getInstance(this)
                    .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe(new Action1<Boolean>() {
                        @Override
                        public void call(Boolean aBoolean) {
                            if (aBoolean) {
                                selectPhoto();
                                bottomoPannelBegginStatus();
                            } else {
                                Toast.makeText(UdeskChatActivity.this,
                                        getResources().getString(R.string.photo_denied),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else if (R.id.udesk_bottom_option_camera == v.getId()) { // 拍照发送图片
            arfreshAgent();
            RxPermissions.getInstance(this)
                    .request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe(new Action1<Boolean>() {
                        @Override
                        public void call(Boolean aBoolean) {
                            if (aBoolean) {
                                takePhoto();
                                bottomoPannelBegginStatus();
                            } else {
                                Toast.makeText(UdeskChatActivity.this,
                                        getResources().getString(R.string.camera_denied),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else if (R.id.udesk_bottom_voice_rl == v.getId()) {  //录音 发送语音
            arfreshAgent();
            RxPermissions.getInstance(this)
                    .request(Manifest.permission.RECORD_AUDIO,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe(new Action1<Boolean>() {
                        @Override
                        public void call(Boolean aBoolean) {
                            if (aBoolean) {

                                if (audioPanel.getVisibility() == View.VISIBLE) {
                                    bottomoPannelBegginStatus();
                                } else {
                                    bottomoPannelBegginStatus();
                                    initaduioPannel();
                                }
                            } else {
                                Toast.makeText(UdeskChatActivity.this,
                                        getResources().getString(R.string.aduido_denied),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }

    }


    private void bottomoPannelBegginStatus() {
        UdeskUtils.hideSoftKeyboard(this, mInputEditView);
        setUdeskAudioPanelVis(View.GONE);
        setUdeskEmojisPannel(View.GONE);
        emjoGridView.setVisibility(View.GONE);
    }

    // 判断可发送消息
    private boolean isShowNotSendMsg() {
        if (isbolcked != null && isbolcked.equals("true")) {
            toBolckedView();
            return false;
        }
        if (!UdeskUtils.isNetworkConnected(this)) {
            UdeskUtils.showToast(this,
                    getResources().getString(R.string.udesk_has_wrong_net));
            return false;
        }

        if (!currentStatusIsOnline) {
            confirmToForm();
            return false;
        }

        return true;
    }

    // 长按录音
    @Override
    public boolean onLongClick(View v) {
        if (v.getId() == R.id.udesk_audio_pop) {
            if (!UdeskUtils.checkSDcard()) {
                Toast.makeText(this,
                        getResources().getString(R.string.udesk_label_no_sd),
                        Toast.LENGTH_LONG).show();
                return false;
            }

            RxPermissions.getInstance(this)
                    .request(Manifest.permission.RECORD_AUDIO,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe(new Action1<Boolean>() {
                        @Override
                        public void call(Boolean aBoolean) {
                            if (aBoolean) {
                                recordVoiceStart();
                            } else {
                                Toast.makeText(UdeskChatActivity.this,
                                        getResources().getString(R.string.aduido_denied),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            return true;
        }
        return false;
    }


    private void recordVoiceStart() {
        if (mRecordFilePlay != null) {
            showStartOrStopAnaimaition(
                    mRecordFilePlay.getPlayAduioMessage(), false);
            recycleVoiceRes();
        }
        setAudiotipsVis(View.GONE);
        setHorVoiceViewVis(View.VISIBLE);
        setAudioCancleViewVis(View.VISIBLE);

        if (audioCancle != null) {
            audioPop.setOnTouchListener(new RecordTouchListener(this,
                    UdeskChatActivity.this, audioCancle));
            if (mPresenter != null) {
                // 开始录音
                mPresenter.recordStart();
                if (mHorVoiceView != null) {
                    mHorVoiceView.startRecording(this);
                }
            }

        }

    }

    //弹出表单留言的提示框
    private void confirmToForm() {
        try {

            String positiveLabel = this.getString(R.string.udesk_ok);

            String negativeLabel = this.getString(R.string.udesk_cancel);
            String title = this.getString(R.string.udesk_msg_offline_to_form);
            if (agentFlag == UdeskConst.AgentFlag.WaitAgent) {
                title = this.getString(R.string.udesk_msg_busyline_to_form);
            }
            if (UdeskChatActivity.this.isFinishing()) {
                return;
            }
            if (!formWindow.isShowing()) {
                formWindow.show(this, this.getWindow().getDecorView(),
                        positiveLabel, negativeLabel, title,
                        new OnPopConfirmClick() {
                            public void onPositiveClick() {
                                goToForm();
                            }

                            @Override
                            public void onNegativeClick() {
                            }

                        });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //启动留言界面
    protected void goToForm() {
        UdeskSDKManager.getInstance().goToForm(UdeskChatActivity.this);
        dismissFormWindow();
    }

    private void dismissFormWindow() {
        if (formWindow != null) {
            formWindow.dismiss();
        }
    }

    @Override
    public void addMessage(MessageInfo message) {
        mChatAdapter.addItem(message);
        Message msgWaitAgent = mHandler
                .obtainMessage(MessageWhat.refreshAdapter);
        mHandler.sendMessage(msgWaitAgent);
    }

    @Override
    public AgentInfo getAgentInfo() {
        if (mAgentInfo != null) {
            return mAgentInfo;
        }
        return new AgentInfo();
    }

    @Override
    public void setAgentInfo(AgentInfo agentInfo) {
        mAgentInfo = agentInfo;
    }

    @Override
    public Handler getHandler() {
        return mHandler;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        if (parent == emjoGridView) {
            if (mPresenter != null) {
                mPresenter.clickEmoji(id, mEmojiAdapter.getCount(),
                        mEmojiAdapter.getItem((int) id));
            }
        }

    }

    @Override
    public void refreshInputEmjio(String s) {
        mInputEditView.setText(UDEmojiAdapter.replaceEmoji(this, s,
                (int) mInputEditView.getTextSize()));

    }

    @Override
    public List<String> getEmotionStringList() {
        List<String> emotionList = new ArrayList<String>();
        int emojiSum = mEmojiAdapter.getCount();
        for (int i = 0; i < emojiSum; i++) {
            if (mEmojiAdapter.getItem(i) != null) {
                emotionList.add(mEmojiAdapter.getItem(i));
            }
        }
        return emotionList;
    }

    //启动手机默认的选择照片
    private void selectPhoto() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, SELECT_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    // 启动手机相机拍照
    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        photoUri = UdeskUtil.getOutputMediaFileUri(UdeskChatActivity.this);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE == requestCode) { //拍照后发生图片
                if (Activity.RESULT_OK == resultCode) {
                    if (data != null) {
                        if (data.hasExtra("data")) {
                            Bitmap thumbnail = data.getParcelableExtra("data");
                            if (thumbnail != null) {
                                if (mPresenter != null) {
                                    mPresenter.sendBitmapMessage(thumbnail);
                                    return;
                                }
                            }
                        }
                    }
                    if (mPresenter != null && photoUri != null && photoUri.getPath() != null) {
                        mPresenter.sendBitmapMessage(photoUri.getPath());
                    }

                }
            } else if (SELECT_IMAGE_ACTIVITY_REQUEST_CODE == requestCode) { //选择图片后发送

                if (resultCode != Activity.RESULT_OK || data == null) {
                    return;
                }

                Uri mImageCaptureUri = data.getData();
                if (mImageCaptureUri != null) {
                    ContentResolver cr = UdeskChatActivity.this
                            .getContentResolver();
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(cr
                                .openInputStream(mImageCaptureUri));
                        if (mPresenter != null) {
                            mPresenter.sendBitmapMessage(bitmap);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    } catch (OutOfMemoryError error) {
                        error.printStackTrace();
                    }
                } else {
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        Bitmap bitmap = extras.getParcelable("data");
                        if (bitmap != null) {
                            if (mPresenter != null) {
                                mPresenter.sendBitmapMessage(bitmap);
                            }

                        }
                    }
                }

            } else if (SELECT_SURVY_OPTION_REQUEST_CODE == requestCode) {
                if (resultCode != Activity.RESULT_OK || data == null) {
                    return;
                }
                Toast.makeText(UdeskChatActivity.this, getString(R.string.udesk_thanks_survy), Toast.LENGTH_SHORT).show();
                String optionId = data.getStringExtra(UdeskConst.SurvyOptionIDKey);
                mPresenter.putIMSurveyResult(optionId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }


    }


    @Override
    public void onTimeOver() {
        if (mPresenter != null) {
            mPresenter.doRecordStop(false);
        }
        initaduioPannel();
    }

    @Override
    public void readyToCancelRecord() {

        setHorVoiceViewVis(View.GONE);
        setAudiotipsVis(View.VISIBLE);
        setAudiotiptext(getString(R.string.udesk_voice_cancle));
    }

    @Override
    public void doCancelRecord() {
        if (mPresenter != null) {
            mPresenter.doRecordStop(true);
        }
        initaduioPannel();
    }

    @Override
    public void readyToContinue() {
        setHorVoiceViewVis(View.VISIBLE);
        setAudiotipsVis(View.GONE);

    }

    @Override
    public void endRecord() {
        if (mPresenter != null) {
            mPresenter.doRecordStop(false);
        }
        initaduioPannel();
    }


    // @Override
    public void showStartOrStopAnaimaition(final MessageInfo info,
                                           final boolean isstart) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if (info == null) {
                    return;
                }
                for (int i = 0, count = mListView.getChildCount(); i < count; i++) {
                    View child = mListView.getChildAt(i);
                    if (child == null || child.getTag() == null
                            || !(child.getTag() instanceof AudioViewHolder)) {
                        continue;
                    }
                    AudioViewHolder holder = (AudioViewHolder) child.getTag();
                    MessageInfo msgTemp = holder.message;
                    holder.endAnimationDrawable();
                    if (msgTemp != info) {
                        msgTemp = info;
                        continue;
                    } else {
                        if (isstart) {
                            holder.startAnimationDrawable();
                        } else {
                            holder.endAnimationDrawable();
                        }

                    }

                }

            }
        });

    }

    // 点击播放录音及动画

    public void clickRecordFile(MessageInfo message) {

        if (mRecordFilePlay == null) {
            mRecordFilePlay = new RecordPlay(UdeskChatActivity.this);

        }
        if (mPlayCallback == null) {
            mPlayCallback = new RecordPlayCallback() {
                public void onPlayComplete(MessageInfo message) {
                    showStartOrStopAnaimaition(message, false);
                    recycleVoiceRes();
                }

                public void onPlayStart(MessageInfo message) {
                    showStartOrStopAnaimaition(message, true);
                }

                public void onPlayPause(MessageInfo message) {
                    showStartOrStopAnaimaition(message, false);
                    recycleVoiceRes();
                }

                public void onPlayEnd(MessageInfo message) {
                    showStartOrStopAnaimaition(message, false);
                    recycleVoiceRes();// 新添加
                }

                @Override
                public void endAnimation() {
                    if (mChatAdapter != null) {
                        List<MessageInfo> list = mChatAdapter.getList();
                        int size = list.size();
                        for (int i = 0; i < size; i++) {
                            MessageInfo message = list.get(i);
                            if (message.isPlaying) {
                                showStartOrStopAnaimaition(message, false);
                            }
                        }
                    }

                }

            };

        }
        mRecordFilePlay.click(message, mPlayCallback);

    }

    private void recycleVoiceRes() {
        if (mRecordFilePlay != null) {
            mRecordFilePlay.recycleRes();
            mRecordFilePlay.recycleCallback();
            mRecordFilePlay = null;
        }

        mPlayCallback = null;
    }

    @Override
    public void onRecordSuccess(String filePath, long duration) {
//        mHandler.sendEmptyMessage(MessageWhat.RECORD_Success);
        if (mPresenter != null) {
            mPresenter.sendRecordAudioMsg(filePath, duration);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        recycleVoiceRes();
        hasSendCommodity = false;
        UdeskSDKManager.getInstance().setIsNeedMsgNotice(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        new Thread(new Runnable() {
            @Override
            public void run() {
                UdeskDBManager.getInstance().updateAllMsgRead();
            }
        }).start();
    }

    //发送广告的连接地址消息
    public void sentLink(String linkMsg) {
        if (mPresenter != null) {
            mPresenter.sendTxtMessage(linkMsg);
        }

    }


    @Override
    protected void onDestroy() {
        unRegister();
        UdeskHttpFacade.getInstance().cancel();
        InvokeEventContainer.getInstance().event_OnOverConversation.unBind(this);
        if (mPresenter != null) {
            mPresenter.unBind();
            mPresenter.removeCallBack();
            mPresenter = null;
        }

        UdeskUtil.closeCrashReport();
        super.onDestroy();

    }

    private void updateRecordStatus(int status) {
//        if (mVoicePopWindow != null) {
//            mVoicePopWindow.updateRecordStatus(status);
//        }
        if (mHorVoiceView != null) {
            mHorVoiceView.addElement(status * 10);
        }
    }

    public void handleText(final MessageInfo message, View targetView) {
        String[] menuLabel = new String[]{getResources().getString(
                R.string.udesk_copy)};
        UdeskMultiMenuHorizontalWindow menuWindow = new UdeskMultiMenuHorizontalWindow(
                UdeskChatActivity.this);
        menuWindow.show(UdeskChatActivity.this, targetView, menuLabel,
                new OnPopMultiMenuClick() {
                    public void onMultiClick(int MenuIndex) {
                        if (MenuIndex == 0) {
                            doCopy(message.getMsgContent());
                        }
                    }
                });
    }

    @SuppressLint("NewApi")
    private void doCopy(String content) {
        if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.content.ClipboardManager c = (android.content.ClipboardManager) UdeskChatActivity.this
                    .getSystemService(Activity.CLIPBOARD_SERVICE);
            c.setPrimaryClip(ClipData.newPlainText(null, content));
        } else {
            android.text.ClipboardManager c = (android.text.ClipboardManager) UdeskChatActivity.this
                    .getSystemService(Activity.CLIPBOARD_SERVICE);
            c.setText(content);
        }
    }

    //重试发送消息
    public void retrySendMsg(MessageInfo message) {
        if (mPresenter != null && message != null) {
            changeImState(message.getMsgId(), UdeskConst.SendFlag.RESULT_RETRY);
            mPresenter.startRetryMsg(message);
        }
    }

    //预览大图
    public void previewPhoto(MessageInfo message) {
        try {
            if (message == null) {
                return;
            }
            String sourceImagePath = "";
            if (!TextUtils.isEmpty(message.getLocalPath())) {
                sourceImagePath = message.getLocalPath();
            } else {
                sourceImagePath = ImageLoader.getInstance().getDiscCache()
                        .get(message.getMsgContent()).getPath();
            }
            File sourceFile = new File(sourceImagePath);
            if (sourceFile.exists()) {
                Intent intent = new Intent(UdeskChatActivity.this,
                        UdeskZoomImageActivty.class);
                Bundle data = new Bundle();
                data.putParcelable("image_path", Uri.fromFile(sourceFile));
                intent.putExtras(data);
                startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void arfreshAgent() {
        if (mPresenter != null && isOverConversation) {
            isOverConversation = false;
//            mPresenter.getAgentInfo();
            mPresenter.createIMCustomerInfo();
        }
    }

    @Override
    public String getAgentId() {
        return agentId;
    }

    @Override
    public String getGroupId() {
        return groupId;
    }

    class EditChangedListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            if (!isShowNotSendMsg()) {
                UdeskUtils.hideSoftKeyboard(UdeskChatActivity.this, mInputEditView);
                return;
            }
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) { //发送输入预支消息
            if (isbolcked.equals("true") || !currentStatusIsOnline || isOverConversation) {
                return;
            }
            if (TextUtils.isEmpty(mInputEditView.getText().toString())) {
                mPresenter.sendPreMessage();
                return;
            }
            long currentTime = System.currentTimeMillis();
            if (currentTime - preMsgSendTime > 500) {
                if (mPresenter != null) {
                    preMsgSendTime = currentTime;
                    mPresenter.sendPreMessage();
                }
            }


        }
    }
}
