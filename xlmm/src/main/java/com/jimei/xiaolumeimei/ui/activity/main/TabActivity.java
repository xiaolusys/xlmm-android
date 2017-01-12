package com.jimei.xiaolumeimei.ui.activity.main;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jimei.library.utils.FileUtils;
import com.jimei.library.utils.JUtils;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseActivity;
import com.jimei.xiaolumeimei.base.BaseFragment;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.entities.UserInfoBean;
import com.jimei.xiaolumeimei.entities.VersionBean;
import com.jimei.xiaolumeimei.entities.event.LogOutEmptyEvent;
import com.jimei.xiaolumeimei.entities.event.RefreshPersonalEvent;
import com.jimei.xiaolumeimei.entities.event.SetMiPushEvent;
import com.jimei.xiaolumeimei.entities.event.ShowShopEvent;
import com.jimei.xiaolumeimei.model.MainModel;
import com.jimei.xiaolumeimei.receiver.UpdateBroadReceiver;
import com.jimei.xiaolumeimei.ui.fragment.main.BoutiqueTabFragment;
import com.jimei.xiaolumeimei.ui.fragment.main.CarTabFragment;
import com.jimei.xiaolumeimei.ui.fragment.main.CollectTabFragment;
import com.jimei.xiaolumeimei.ui.fragment.main.FirstFragment;
import com.jimei.xiaolumeimei.ui.fragment.main.GetCouponFragment;
import com.jimei.xiaolumeimei.ui.fragment.main.MainTabFragment;
import com.jimei.xiaolumeimei.ui.fragment.main.MyTabFragment;
import com.jimei.xiaolumeimei.utils.FragmentTabUtils;
import com.jimei.xiaolumeimei.utils.LoginUtils;
import com.jimei.xiaolumeimei.widget.VersionManager;
import com.jimei.xiaolumeimei.xlmmService.UpdateService;
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
import okhttp3.Call;


public class TabActivity extends BaseActivity {
    @Bind(R.id.radio_group)
    RadioGroup radioGroup;
    @Bind(R.id.text_car)
    TextView textView;
    private int num = 1;
    private long firstTime = 0;
    private boolean updateFlag = true;
    private UserInfoBean userInfoNewBean;
    private UpdateBroadReceiver mUpdateBroadReceiver;

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
                if (flag.equals("car")) {
                    radioGroup.check(R.id.rb_car);
                } else if (flag.equals("collect")) {
                    radioGroup.check(R.id.rb_collect);
                } else if (flag.equals("my")) {
                    radioGroup.check(R.id.rb_my);
                } else {
                    radioGroup.check(R.id.rb_main);
                }
            }
        }
    }

    @Override
    protected void initData() {
        LoginUtils.clearCacheEveryWeek(this);
        LoginUtils.setMamaInfo(this);
        showNewCoupon();
        downLoadAddress();
        downLoadCategory();
        setTopic();
        checkVersion();
    }

    public void showCarNum() {
        if (LoginUtils.checkLoginState(this)) {
            addSubscription(MainModel.getInstance()
                    .getCartsNum()
                    .subscribe(cartsNumResultBean -> {
                        if (cartsNumResultBean != null) {
                            int num = cartsNumResultBean.getResult();
                            textView.setText(num + "");
                            if (num > 0) {
                                textView.setVisibility(View.VISIBLE);
                            } else {
                                textView.setVisibility(View.GONE);
                            }
                        }
                    }, Throwable::printStackTrace));
        } else {
            textView.setText("0");
            textView.setVisibility(View.GONE);
        }
    }

    private void showNewCoupon() {
        if (LoginUtils.isJumpToLogin(getApplicationContext())) {
            FirstFragment firstFragment = FirstFragment.newInstance("first");
            firstFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.Translucent_NoTitle);
            firstFragment.show(getFragmentManager(), "first");
        }
        if (LoginUtils.checkLoginState(getApplicationContext())) {
            addSubscription(MainModel.getInstance()
                    .isCouPon()
                    .subscribe(getCoupon -> {
                        if (getCoupon != null && getCoupon.getIsPicked() == 0) {
                            GetCouponFragment firstFragment = GetCouponFragment.newInstance("getCoupon ");
                            firstFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.Translucent_NoTitle);
                            firstFragment.show(getFragmentManager(), "getCoupon");
                        }
                    }, Throwable::printStackTrace));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        showCarNum();
        EventBus.getDefault().post(new ShowShopEvent());
        EventBus.getDefault().post(new RefreshPersonalEvent());
    }

    @Override
    protected void onPause() {
        super.onPause();
        updateFlag = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void initViews() {
        EventBus.getDefault().register(this);
        List<BaseFragment> fragments = new ArrayList<>();
        fragments.add(MainTabFragment.newInstance());
        fragments.add(BoutiqueTabFragment.newInstance());
        fragments.add(CarTabFragment.newInstance());
        fragments.add(CollectTabFragment.newInstance());
        fragments.add(MyTabFragment.newInstance());
        new FragmentTabUtils(getSupportFragmentManager(), radioGroup, fragments, R.id.container, this);
        setSwipeBackEnable(false);
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
    public void initLogOutInfo(LogOutEmptyEvent event) {
        radioGroup.check(R.id.rb_main);
    }

    private void setTopic() {
        addSubscription(MainModel.getInstance()
                .getTopic()
                .subscribe(userTopic -> {
                    List<String> topics = userTopic.getTopics();
                    if (topics != null) {
                        for (int i = 0; i < topics.size(); i++) {
                            MiPushClient.subscribe(this, topics.get(i), null);
                        }
                    }
                }, Throwable::printStackTrace));
    }

    private void checkVersion() {
        addSubscription(MainModel.getInstance()
                .getVersion()
                .subscribe(versionBean -> {
                    if (versionBean != null) {
                        new Thread(() -> {
                            SystemClock.sleep(2500);
                            runOnUiThread(() -> checkVersion(versionBean));
                        }).start();
                    }
                }, Throwable::printStackTrace));
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

    private void downLoadCategory() {
        addSubscription(MainModel.getInstance()
                .getCategoryDown()
                .subscribe(categoryDownBean -> {
                    if (categoryDownBean != null) {
                        String downloadUrl = categoryDownBean.getDownload_url();
                        String sha1 = categoryDownBean.getSha1();
                        if (!FileUtils.isCategorySame(getApplicationContext(), sha1)
                                || !FileUtils.isFileExist(XlmmConst.CATEGORY_JSON)) {
                            if (FileUtils.isFolderExist(XlmmConst.CATEGORY_JSON)) {
                                FileUtils.deleteFile(XlmmConst.CATEGORY_JSON);
                            }
                            OkHttpUtils.get().url(downloadUrl).build()
                                    .execute(new FileCallBack(XlmmConst.XLMM_DIR, "category.json") {
                                        @Override
                                        public void onError(Call call, Exception e, int id) {
                                        }

                                        @Override
                                        public void onResponse(File response, int id) {
                                            FileUtils.saveCategoryFile(getApplicationContext(), sha1);
                                            FileUtils.saveCategoryImg(XlmmConst.CATEGORY_JSON);
                                        }
                                    });
                        }
                    }
                }, Throwable::printStackTrace));
    }

    private void downLoadAddress() {
        addSubscription(MainModel.getInstance()
                .getAddressVersionAndUrl()
                .subscribe(addressDownloadResultBean -> {
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
                }, Throwable::printStackTrace));
    }

    public UserInfoBean getUserInfoNewBean() {
        return userInfoNewBean;
    }

    public void setUserInfoNewBean(UserInfoBean userInfoNewBean) {
        this.userInfoNewBean = userInfoNewBean;
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
