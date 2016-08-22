package com.jimei.xiaolumeimei.ui.activity.user;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.entities.UserInfoBean;
import com.jimei.xiaolumeimei.model.UserModel;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import cn.udesk.UdeskConst;
import cn.udesk.UdeskSDKManager;
import cn.udesk.xmpp.UdeskMessageManager;
import rx.schedulers.Schedulers;

public class CustomerServiceActivity extends BaseSwipeBackCompatActivity implements View.OnClickListener {
    @Bind(R.id.ask_layout)
    LinearLayout layout;
    @Bind(R.id.phone_layout)
    LinearLayout phoneLayout;

    @Override
    protected void setListener() {
        layout.setOnClickListener(this);
        phoneLayout.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        UdeskSDKManager.getInstance().initApiKey(this, XlmmConst.UDESK_URL, XlmmConst.UDESK_KEY);
        UserModel.getInstance()
                .getUserInfo()
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<UserInfoBean>() {
                    @Override
                    public void onNext(UserInfoBean userInfoBean) {
                        String id = userInfoBean.getId() + "";
                        Map<String, String> info = new HashMap<>();
                        info.put(UdeskConst.UdeskUserInfo.USER_SDK_TOKEN, id);
                        info.put(UdeskConst.UdeskUserInfo.NICK_NAME, userInfoBean.getNick() + "(ID:" + id + ")");
                        info.put(UdeskConst.UdeskUserInfo.CELLPHONE, userInfoBean.getMobile());
                        UdeskSDKManager.getInstance().setUserInfo(CustomerServiceActivity.this, id, info);
                        UdeskMessageManager.getInstance().event_OnNewMsgNotice.bind(this, "OnNewMsgNotice");
                    }
                });
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_customer_service;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ask_layout:
                UdeskSDKManager.getInstance().toLanuchChatAcitvity(this);
                break;
            case R.id.phone_layout:
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:021-60927905"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(this.getClass().getSimpleName());
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
        MobclickAgent.onPause(this);
    }
}
