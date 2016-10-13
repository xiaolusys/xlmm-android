package com.jimei.xiaolumeimei.ui.mminfo;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.TabLayout;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.MamaTabAdapter;
import com.jimei.xiaolumeimei.base.BaseLazyFragment;
import com.jimei.xiaolumeimei.base.BaseMVVMActivity;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.databinding.ActivityMamaBinding;
import com.jimei.xiaolumeimei.entities.MiPushOrderCarryBean;
import com.jimei.xiaolumeimei.entities.UserInfoBean;
import com.jimei.xiaolumeimei.entities.WxQrcode;
import com.jimei.xiaolumeimei.event.HideOrderEvent;
import com.jimei.xiaolumeimei.event.SetOrderEvent;
import com.jimei.xiaolumeimei.event.ShowOrderEvent;
import com.jimei.xiaolumeimei.model.MMProductModel;
import com.jimei.xiaolumeimei.ui.mminfo.fragment.MamaFirstFragment;
import com.jimei.xiaolumeimei.ui.mminfo.fragment.MamaSecondFragment;
import com.jimei.xiaolumeimei.ui.mminfo.fragment.MamaThirdFragment;
import com.jimei.xiaolumeimei.utils.RxUtils;
import com.jimei.xiaolumeimei.utils.ViewUtils;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.udesk.UdeskConst;
import cn.udesk.UdeskSDKManager;
import rx.schedulers.Schedulers;

public class MamaActivity extends BaseMVVMActivity<ActivityMamaBinding> {

    private List<BaseLazyFragment> fragments = new ArrayList<>();
    private boolean isDestroy = false;
    private ExecutorService service;

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        showIndeterminateProgressDialog(true);
        UdeskSDKManager.getInstance().initApiKey(this, XlmmConst.UDESK_URL, XlmmConst.UDESK_KEY);
        addSubscription(MMInfoModel.getInstance()
                .getUserInfo()
                .retryWhen(new RxUtils.RetryWhenNoInternet(100, 2000))
                .subscribe(this::fillDataToView, Throwable::printStackTrace));
        addSubscription(MMInfoModel.getInstance()
                .getLatestOrderCarry()
                .subscribe(list -> {
                    service = Executors.newSingleThreadExecutor();
                    service.execute(() -> {
                        for (int i = 0; i < list.size() && !isDestroy; i++) {
                            SystemClock.sleep(new Random().nextInt(5000));
                            if (!isDestroy)
                                EventBus.getDefault().post(new SetOrderEvent(list.get(list.size() - 1 - i)));
                            SystemClock.sleep(1000);
                            if (!isDestroy) EventBus.getDefault().post(new ShowOrderEvent());
                            SystemClock.sleep(3000);
                            if (!isDestroy) EventBus.getDefault().post(new HideOrderEvent());
                            SystemClock.sleep(3000);
                        }
                    });
                }, Throwable::printStackTrace));
        addSubscription(MMProductModel.getInstance()
                .getWxCode()
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<WxQrcode>(){
                    @Override
                    public void onNext(WxQrcode wxQrcode) {
                        super.onNext(wxQrcode);
                    }
                }));
    }

    private void fillDataToView(UserInfoBean userInfoBean) {
        String id = userInfoBean.getId() + "";
        int mamaId = userInfoBean.getXiaolumm().getId();
        Map<String, String> info = new HashMap<>();
        info.put(UdeskConst.UdeskUserInfo.USER_SDK_TOKEN, id);
        info.put(UdeskConst.UdeskUserInfo.NICK_NAME, userInfoBean.getNick() + "(ID:" + id + ")");
        info.put(UdeskConst.UdeskUserInfo.CELLPHONE, userInfoBean.getMobile());
        UdeskSDKManager.getInstance().setUserInfo(this, id, info);
        fragments.add(MamaFirstFragment.newInstance("我要赚钱", mamaId));
        fragments.add(MamaSecondFragment.newInstance("社交活动", mamaId));
        fragments.add(MamaThirdFragment.newInstance("我的", mamaId));
        MamaTabAdapter mAdapter = new MamaTabAdapter(getSupportFragmentManager(), fragments);
        b.viewPager.setAdapter(mAdapter);
        b.viewPager.setOffscreenPageLimit(2);
        b.tabLayout.setupWithViewPager(b.viewPager);
        b.tabLayout.setTabMode(TabLayout.MODE_FIXED);
    }


    @Override
    protected void getBundleExtras(Bundle extras) {
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_mama;
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_kefu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.kefu:
                UdeskSDKManager.getInstance().showRobotOrConversation(this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setOrderCarry(SetOrderEvent event) {
        MiPushOrderCarryBean bean = event.getBean();
        try {
            ViewUtils.loadImgToHead(this, b.miHead, bean.getAvatar());
        } catch (Exception ignored) {
        }
        b.miInfo.setText(bean.getContent());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showOrder(ShowOrderEvent event) {
        b.miLayout.setVisibility(View.VISIBLE);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void hideOrder(HideOrderEvent event) {
        b.miLayout.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (fragments.size() == 3) {
            if (b.viewPager.getCurrentItem() == 2) {
                ((MamaThirdFragment) fragments.get(2)).refreshFortune();
            } else if (b.viewPager.getCurrentItem() == 0) {
                ((MamaFirstFragment) fragments.get(0)).refreshNotice();
            }
        }
        MobclickAgent.onPageStart(this.getClass().getSimpleName());
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
        MobclickAgent.onPause(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (fragments.size() == 3) {
            WebView webView = ((MamaSecondFragment) fragments.get(1)).getWebView();
            if (keyCode == KeyEvent.KEYCODE_BACK && webView != null) {
                if (b.viewPager.getCurrentItem() == 1 && webView.canGoBack()) {
                    webView.goBack();
                } else {
                    finish();
                }
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        if (service != null) {
            service.shutdownNow();
        }
        isDestroy = true;
        super.onDestroy();
    }
}
