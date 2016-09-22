package cn.udesk.activity;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.udesk.JsonUtils;
import cn.udesk.R;
import cn.udesk.UdeskSDKManager;
import cn.udesk.UdeskUtil;
import cn.udesk.adapter.OptionsAgentGroupAdapter;
import cn.udesk.config.UdekConfigUtil;
import cn.udesk.config.UdeskConfig;
import cn.udesk.model.AgentGroupNode;
import cn.udesk.widget.UdeskDialog;
import cn.udesk.widget.UdeskTitleBar;
import udesk.core.UdeskCallBack;
import udesk.core.UdeskHttpFacade;


/**
 * 选择客服组
 */
public class OptionsAgentGroupActivity extends Activity implements AdapterView.OnItemClickListener {

    private UdeskTitleBar mTitlebar;
    private TextView title;
    private ListView listView;
    private UdeskDialog dialog;
    private List<AgentGroupNode> groups;
    private List<AgentGroupNode> adapterData = new ArrayList<AgentGroupNode>();
    private OptionsAgentGroupAdapter adapter;
    private String rootId = "item_0";
    private AgentGroupNode backMode = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.udesk_options_agentgroup_view);
        UdeskUtil.initCrashReport(this);
        initView();
        getImGroupInfo();
    }

    private void initView() {
        title = (TextView) findViewById(R.id.udesk_title);
        listView = (ListView) findViewById(R.id.udesk_options_listview);
        mTitlebar = (UdeskTitleBar) findViewById(R.id.udesktitlebar);
        adapter = new OptionsAgentGroupAdapter(this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backParentView();
            }
        });
    }

    /**
     * 请求客服组信息，没有客服组则直接进入会话界面
     */
    private void getImGroupInfo() {
        showLoading();
        UdeskHttpFacade.getInstance().getImGroupApi(UdeskSDKManager.getInstance().getDomain(this),
                UdeskSDKManager.getInstance().getSecretKey(this),
                UdeskSDKManager.getInstance().getSdkToken(this),
                new UdeskCallBack() {

                    @Override
                    public void onSuccess(String message) {
                        dismiss();
                        try {
                            JSONObject resultJson = new JSONObject(message);
                            if (resultJson.optInt("code") == 1000) {
                                groups = JsonUtils.parseIMGroup(message);
                                if (groups == null || groups.isEmpty()) {
                                    luanchChat();
                                    return;
                                }
                                settingTitlebar();
                                drawView(rootId);
                            } else {
                                luanchChat();
                            }
                        } catch (Exception e) {
                            luanchChat();
                        }

                    }

                    @Override
                    public void onFail(String message) {
                        dismiss();
                        luanchChat();
                    }
                });
    }

    //进入会话界面
    private void luanchChat() {
        UdeskSDKManager.getInstance().toLanuchChatAcitvity(OptionsAgentGroupActivity.this);
        finish();
    }

    /**
     * 设置标题栏的title和返回事件
     */
    private void settingTitlebar() {

        if (mTitlebar != null) {
            UdekConfigUtil.setUITextColor(UdeskConfig.udeskTitlebarTextLeftRightResId,mTitlebar.getLeftTextView(),mTitlebar.getRightTextView());
            UdekConfigUtil.setUIbgDrawable(UdeskConfig.udeskTitlebarBgResId ,mTitlebar.getRootView());
            if (UdeskConfig.DEFAULT != UdeskConfig.udeskbackArrowIconResId) {
                mTitlebar.getUdeskBackImg().setImageResource(UdeskConfig.udeskbackArrowIconResId);
            }
            mTitlebar
                    .setLeftTextSequence(getString(R.string.udesk_options_agentgroup));
            mTitlebar.setLeftLinearVis(View.VISIBLE);
            mTitlebar.setLeftViewClick(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }


    /**
     * 返回到上一级
     */
    private void backParentView() {
        if (backMode == null) {
            finish();
        } else {
            drawView(backMode.getParentId());
            backMode = filterModel(backMode.getParentId());
        }
    }

    /**
     * 客服组下还有客服组则进入下一级客服组，是客服，值获取到客服的ID，进行会话连接
     */
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        AgentGroupNode groupNode = adapter.getItem(i);
        backMode = groupNode;
        if (groupNode != null) {
            if (TextUtils.isEmpty(groupNode.getGroup_id())) {
                drawView(groupNode.getId());
            } else {
                UdeskSDKManager.getInstance().lanuchChatByGroupId(OptionsAgentGroupActivity.this, groupNode.getGroup_id());
                finish();
            }

        }

    }

    private void showLoading() {
        try {
            dialog = new UdeskDialog(OptionsAgentGroupActivity.this, R.style.udesk_dialog);
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void dismiss() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    //根据id 画出相应的UI显示
    private void drawView(String currentId) {
        adapterData.clear();
        if (currentId.equals("item_0")) {
            title.setVisibility(View.GONE);
        } else {
            String currentTempId = currentId;
            List<AgentGroupNode> temps = new ArrayList<AgentGroupNode>();
            boolean isHasParent = true;
            while (isHasParent) {
                AgentGroupNode model = filterModel(currentTempId);
                if (model == null) {
                    isHasParent = false;
                } else {
                    currentTempId = model.getParentId();
                    temps.add(model);
                }
            }
            title.setVisibility(View.VISIBLE);
            title.setText(buildTitleName(temps));
        }
        for (AgentGroupNode model : groups) {
            if (model.getParentId().equals(currentId)) {
                adapterData.add(model);
            }
        }
        adapter.setList(adapterData);
    }

    /**
     * 获取相应ID的客服组model
     */
    private AgentGroupNode filterModel(String currentId) {
        for (AgentGroupNode model : groups) {
            if (model.getId().equals(currentId)) {
                return model;
            }
        }
        return null;
    }

    /**
     * 构造界面显示的组名 > 的字符串
     */
    private String buildTitleName(List<AgentGroupNode> temps) {
        StringBuilder builder = new StringBuilder();
        for (int i = temps.size() - 1; i >= 0; i--) {
            builder.append(temps.get(i).getItem_name()).append(" > ");
        }
        String temp = builder.toString();
        return temp.substring(0, temp.length() - 2);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UdeskUtil.closeCrashReport();
    }
}
