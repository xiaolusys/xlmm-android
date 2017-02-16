package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.view.KeyEvent;
import android.webkit.WebView;

import com.jimei.library.utils.JUtils;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.BaseTabAdapter;
import com.jimei.xiaolumeimei.base.BaseFragment;
import com.jimei.xiaolumeimei.base.BaseMVVMActivity;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.databinding.ActivityMamaBinding;
import com.jimei.xiaolumeimei.entities.UserInfoBean;
import com.jimei.xiaolumeimei.model.MamaInfoModel;
import com.jimei.xiaolumeimei.ui.fragment.mminfo.MamaBoutiqueFragment;
import com.jimei.xiaolumeimei.ui.fragment.mminfo.MamaFirstFragment;
import com.jimei.xiaolumeimei.util.pay.PayUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.udesk.UdeskConst;
import cn.udesk.UdeskSDKManager;
import rx.Observable;

public class MamaActivity extends BaseMVVMActivity<ActivityMamaBinding> {

    private List<BaseFragment> fragments = new ArrayList<>();

    @Override
    protected void initViews() {
        fragments.add(MamaBoutiqueFragment.newInstance("精品汇"));
        fragments.add(MamaFirstFragment.newInstance("我要赚钱"));
        BaseTabAdapter mAdapter = new BaseTabAdapter(getSupportFragmentManager(), fragments);
        b.viewPager.setAdapter(mAdapter);
        b.viewPager.setOffscreenPageLimit(2);
        b.tabLayout.setupWithViewPager(b.viewPager);
        b.tabLayout.setTabMode(TabLayout.MODE_FIXED);
    }


    @Override
    protected void initData() {
        UdeskSDKManager.getInstance().initApiKey(this, XlmmConst.UDESK_URL, XlmmConst.UDESK_KEY);
        showIndeterminateProgressDialog(true);
        addSubscription(Observable.mergeDelayError(MamaInfoModel.getInstance().getUserInfo(),
            MamaInfoModel.getInstance().getWxCode())
            .subscribe(o -> {
                    if (o instanceof UserInfoBean) {
                        fillDataToView((UserInfoBean) o);
                    }
                }, e -> hideIndeterminateProgressDialog()
                , this::hideIndeterminateProgressDialog));
    }

    private void fillDataToView(UserInfoBean userInfoBean) {
        String id = userInfoBean.getId() + "";
        Map<String, String> info = new HashMap<>();
        info.put(UdeskConst.UdeskUserInfo.USER_SDK_TOKEN, id);
        info.put(UdeskConst.UdeskUserInfo.NICK_NAME, userInfoBean.getNick() + "(ID:" + id + ")");
        info.put(UdeskConst.UdeskUserInfo.CELLPHONE, userInfoBean.getMobile());
        UdeskSDKManager.getInstance().setUserInfo(this, id, info);
        hideIndeterminateProgressDialog();
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_mama;
    }

    @Override
    public boolean isNeedShow() {
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (fragments.size() == 2) {
            WebView webView = null;
            if (b.viewPager.getCurrentItem() == 0) {
                webView = ((MamaBoutiqueFragment) fragments.get(0)).getWebView();
            }
            if (keyCode == KeyEvent.KEYCODE_BACK && webView != null) {
                if (webView.canGoBack()) {
                    webView.goBack();
                    return true;
                }
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == PayUtils.REQUEST_CODE_PAYMENT) {
            if (resultCode == RESULT_OK) {
                String result = intent.getExtras().getString("pay_result", "");
                String errorMsg = intent.getExtras().getString("error_msg"); // 错误信息
                String extraMsg = intent.getExtras().getString("extra_msg"); // 错误信息
                if (result != null) {
                    switch (result) {
                        case "cancel":
                            JUtils.Toast("已取消支付!");
                            break;
                        case "success":
                            JUtils.Toast("支付成功！");
                            break;
                        default:
                            showMsg(result, errorMsg, extraMsg);
                            break;
                    }
                }
            }
        }
    }
}
