package com.jimei.xiaolumeimei.ui.activity.main;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jimei.library.utils.FileUtils;
import com.jimei.library.utils.JUtils;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.XlmmApp;
import com.jimei.xiaolumeimei.base.BaseActivity;
import com.jimei.xiaolumeimei.base.BaseFragment;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.entities.AddressDownloadResultBean;
import com.jimei.xiaolumeimei.entities.CartsNumResultBean;
import com.jimei.xiaolumeimei.entities.UserInfoBean;
import com.jimei.xiaolumeimei.entities.UserTopic;
import com.jimei.xiaolumeimei.entities.VersionBean;
import com.jimei.xiaolumeimei.entities.event.BoutiqueEvent;
import com.jimei.xiaolumeimei.entities.event.CartNumEvent;
import com.jimei.xiaolumeimei.entities.event.LoginEvent;
import com.jimei.xiaolumeimei.entities.event.LogoutEvent;
import com.jimei.xiaolumeimei.entities.event.PersonalEvent;
import com.jimei.xiaolumeimei.entities.event.SetMiPushEvent;
import com.jimei.xiaolumeimei.receiver.UpdateBroadReceiver;
import com.jimei.xiaolumeimei.service.ServiceResponse;
import com.jimei.xiaolumeimei.service.UpdateService;
import com.jimei.xiaolumeimei.ui.fragment.main.BoutiqueTabFragment;
import com.jimei.xiaolumeimei.ui.fragment.main.CartTabFragment;
import com.jimei.xiaolumeimei.ui.fragment.main.CategoryTabFragment;
import com.jimei.xiaolumeimei.ui.fragment.main.MainTabFragment;
import com.jimei.xiaolumeimei.ui.fragment.main.UserTabFragment;
import com.jimei.xiaolumeimei.util.FragmentTabUtils;
import com.jimei.xiaolumeimei.util.LoginUtils;
import com.jimei.xiaolumeimei.widget.VersionManager;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.sharesdk.framework.ShareSDK;
import okhttp3.Call;
import retrofit2.adapter.rxjava.HttpException;


public class TabActivity extends BaseActivity {
    @Bind(R.id.radio_group)
    RadioGroup radioGroup;
    @Bind(R.id.text_car)
    TextView textView;
    private int num = 1;
    private long firstTime = 0;
    private boolean updateFlag = true;
    private UpdateBroadReceiver mUpdateBroadReceiver;
    public Handler mHandler;
    private BoutiqueTabFragment boutiqueTabFragment;
    private FragmentTabUtils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initContentView() {
        setContentView(getContentViewLayoutID());
    }

    @Override
    public boolean isNeedShow() {
        return false;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (intent.getExtras() != null) {
            String flag = intent.getExtras().getString("flag");
            if (flag != null && !"".equals(flag)) {
                switch (flag) {
                    case "car":
                        radioGroup.check(R.id.rb_car);
                        break;
                    case "boutique":
                        radioGroup.check(R.id.rb_main);
                        break;
                    case "my":
                        radioGroup.check(R.id.rb_my);
                        break;
                    default:
                        radioGroup.check(R.id.rb_main);
                        break;
                }
            }
        }
    }

    @Override
    protected void initData() {
        EventBus.getDefault().post(new SetMiPushEvent());
        initLogin(new LoginEvent());
        addSubscription(XlmmApp.getVipInteractor(this)
            .getWxCode(new ServiceResponse<>()));
        LoginUtils.setMamaInfo(this);
        downLoadAddress();
        setTopic();
        checkVersion();
        LoginUtils.clearCacheEveryWeek(this);
    }

    public void showCarNum() {
        if (LoginUtils.checkLoginState(this)) {
            addSubscription(XlmmApp.getMainInteractor(this)
                .getCartsNum(new ServiceResponse<CartsNumResultBean>() {
                    @Override
                    public void onNext(CartsNumResultBean cartsNumResultBean) {
                        if (cartsNumResultBean != null) {
                            int num = cartsNumResultBean.getResult();
                            textView.setText(num + "");
                            if (num > 0) {
                                textView.setVisibility(View.VISIBLE);
                            } else {
                                textView.setVisibility(View.GONE);
                            }
                        }
                    }
                }));
        } else {
            textView.setText("0");
            textView.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        showCarNum();
        EventBus.getDefault().post(new PersonalEvent());
    }

    @Override
    protected void onPause() {
        super.onPause();
        updateFlag = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ShareSDK.stopSDK();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void initViews() {
        EventBus.getDefault().register(this);
        setSwipeBackEnable(false);
        List<BaseFragment> fragments = new ArrayList<>();
        fragments.add(MainTabFragment.newInstance());
        fragments.add(CategoryTabFragment.newInstance());
        boutiqueTabFragment = BoutiqueTabFragment.newInstance();
        fragments.add(boutiqueTabFragment);
        fragments.add(CartTabFragment.newInstance());
        fragments.add(UserTabFragment.newInstance());
        utils = new FragmentTabUtils(getSupportFragmentManager(), radioGroup, fragments, R.id.container, TabActivity.this);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_tab;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (radioGroup.getCheckedRadioButtonId() == R.id.rb_boutique &&
                boutiqueTabFragment.getWebView().canGoBack()) {
                boutiqueTabFragment.getWebView().goBack();
                return true;
            }
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 1000) {
                firstTime = secondTime;
                JUtils.Toast("再按一次退出程序!");
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void setMipush(SetMiPushEvent event) {
        JUtils.Log("regid", MiPushClient.getRegId(getApplicationContext()));
        LoginUtils.setPushUserAccount(this, MiPushClient.getRegId(getApplicationContext()));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void initLogOutInfo(LogoutEvent event) {
        radioGroup.check(R.id.rb_main);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void initLogin(LoginEvent event) {
        showIndeterminateProgressDialog(false);
        initBoutique(new BoutiqueEvent());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void initBoutique(BoutiqueEvent event) {
        addSubscription(XlmmApp.getMainInteractor(this)
            .getProfile(new ServiceResponse<UserInfoBean>() {
                @Override
                public void onNext(UserInfoBean userInfoBean) {
                    if ((userInfoBean.getXiaolumm() != null) && (userInfoBean.getXiaolumm().getId() != 0)) {
                        utils.setVip(true);
                    } else {
                        utils.setVip(false);
                    }
                    hideIndeterminateProgressDialog();
                }

                @Override
                public void onError(Throwable e) {
                    if (e instanceof HttpException) {
                        if (((HttpException) e).code() == 403) {
                            if (LoginUtils.checkLoginState(TabActivity.this)) {
                                JUtils.Toast("登录已过期，请重新登录!");
                                LoginUtils.delLoginInfo(TabActivity.this);
                            }
                        }
                    }
                    if (LoginUtils.checkLoginState(TabActivity.this)) {
                        JUtils.Toast("获取妈妈信息失败,请下拉刷新主页重试!");
                    }
                    hideIndeterminateProgressDialog();
                }
            }));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void initCart(CartNumEvent event) {
        showCarNum();
    }

    private void setTopic() {
        addSubscription(XlmmApp.getMainInteractor(this)
            .getTopic(new ServiceResponse<UserTopic>() {
                @Override
                public void onNext(UserTopic userTopic) {
                    List<String> topics = userTopic.getTopics();
                    if (topics != null) {
                        for (int i = 0; i < topics.size(); i++) {
                            MiPushClient.subscribe(TabActivity.this, topics.get(i), null);
                        }
                    }
                }
            }));
    }

    private void checkVersion() {
        addSubscription(XlmmApp.getMainInteractor(this)
            .getVersion(new ServiceResponse<VersionBean>() {
                @Override
                public void onNext(VersionBean versionBean) {
                    if (versionBean != null) {
                        new Thread(() -> {
                            SystemClock.sleep(2500);
                            runOnUiThread(() -> checkVersion(versionBean));
                        }).start();
                    }
                }
            }));
    }

    private void checkVersion(VersionBean versionBean) {
        if (updateFlag) {
            VersionManager versionManager = VersionManager.newInstance(versionBean.getVersion_code(),
                ("最新版本:" + versionBean.getVersion() + "\n\n更新内容:\n" + versionBean.getMemo()), false);
            if (versionBean.isAuto_update()) {
                versionManager.setPositiveListener(v -> {
                    Intent intent = new Intent(TabActivity.this, UpdateService.class);
                    intent.putExtra(UpdateService.EXTRAS_DOWNLOAD_URL, versionBean.getDownload_link());
                    startService(intent);
                    versionManager.getDialog().dismiss();
                    JUtils.Toast("应用正在后台下载!");
                });
                if (num == 1 && updateFlag) {
                    num = 2;
                    versionManager.checkVersion(TabActivity.this);
                }
            }
        }
    }

    private void downLoadAddress() {
        addSubscription(XlmmApp.getMainInteractor(this)
            .getAddressVersionAndUrl(new ServiceResponse<AddressDownloadResultBean>() {
                @Override
                public void onNext(AddressDownloadResultBean addressDownloadResultBean) {
                    if (addressDownloadResultBean != null) {
                        String downloadUrl = addressDownloadResultBean.getDownloadUrl();
                        String hash = addressDownloadResultBean.getHash();
                        if (!FileUtils.isAddressFileHashSame(getApplicationContext(), hash) ||
                            !FileUtils.isFileExist(XlmmConst.XLMM_DIR + "areas.json")) {
                            OkHttpUtils.get().url(downloadUrl).build()
                                .execute(new FileCallBack(XlmmConst.XLMM_DIR, "areas.json") {
                                    @Override
                                    public void onError(Call call, Exception e, int id) {
                                        if (FileUtils.isFolderExist(XlmmConst.XLMM_DIR + "areas.json")) {
                                            FileUtils.deleteFile(XlmmConst.XLMM_DIR + "areas.json");
                                        }
                                    }

                                    @Override
                                    public void onResponse(File response, int id) {
                                        FileUtils.saveAddressFile(getApplicationContext(), hash);
                                    }
                                });
                        }
                    }
                }
            }));
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        filter.addAction(UpdateBroadReceiver.ACTION_RETRY_DOWNLOAD);
        mUpdateBroadReceiver = new UpdateBroadReceiver();
        registerReceiver(mUpdateBroadReceiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mUpdateBroadReceiver);
    }
}
