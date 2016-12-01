package com.jimei.xiaolumeimei.ui.activity.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.jimei.xiaolumeimei.base.BaseWebViewFragment;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.entities.VersionBean;
import com.jimei.xiaolumeimei.entities.event.LogOutEmptyEvent;
import com.jimei.xiaolumeimei.entities.event.RefreshCarNumEvent;
import com.jimei.xiaolumeimei.entities.event.RefreshPersonalEvent;
import com.jimei.xiaolumeimei.entities.event.SetMiPushEvent;
import com.jimei.xiaolumeimei.ui.fragment.main.CarTabFragment;
import com.jimei.xiaolumeimei.ui.fragment.main.CollectTabFragment;
import com.jimei.xiaolumeimei.ui.fragment.main.FirstFragment;
import com.jimei.xiaolumeimei.ui.fragment.main.GetCouponFragment;
import com.jimei.xiaolumeimei.ui.fragment.main.MainTabFragment;
import com.jimei.xiaolumeimei.ui.fragment.main.MyTabFragment;
import com.jimei.xiaolumeimei.ui.xlmmmain.MainModel;
import com.jimei.xiaolumeimei.utils.FragmentTabUtils;
import com.jimei.xiaolumeimei.utils.LoginUtils;
import com.jimei.xiaolumeimei.widget.VersionManager;
import com.jimei.xiaolumeimei.xlmmService.UpdateService;
import com.umeng.analytics.MobclickAgent;
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


public class TabActivity extends BaseActivity implements FragmentTabUtils.OnTabCheckListener {
    @Bind(R.id.radio_group)
    RadioGroup radioGroup;
    @Bind(R.id.text_car)
    TextView textView;
    private int check_id = -1;
    private long firstTime = 0;
    private boolean updateFlag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        setContentView(getContentViewLayoutID());
        super.onCreate(savedInstanceState);
        initViews();
        initData();
    }

    @Override
    protected void initData() {
        if (check_id != -1) {
            radioGroup.check(check_id);
        }
        showNewCoupon();
        downLoadAddress();
        downLoadCategory();
        setTopic();
        checkVersion();
    }

    public void showCarNum() {
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
                            GetCouponFragment firstFragment = GetCouponFragment.newInstance("getCoupon");
                            firstFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.Translucent_NoTitle);
                            firstFragment.show(getFragmentManager(), "getCoupon");
                        }
                    }, Throwable::printStackTrace));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (LoginUtils.checkLoginState(this)) {
            showCarNum();
        }
        MobclickAgent.onPageStart(this.getClass().getSimpleName());
        MobclickAgent.onResume(this);
        EventBus.getDefault().post(new RefreshCarNumEvent());
        EventBus.getDefault().post(new RefreshPersonalEvent());
    }

    @Override
    protected void onPause() {
        super.onPause();
        updateFlag = false;
        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
        MobclickAgent.onPause(this);
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
        fragments.add(BaseWebViewFragment.newInstance("精品券", "http://m.xiaolumeimei.com/mall/activity/topTen/model/2?id=379"));
        fragments.add(CarTabFragment.newInstance());
        fragments.add(CollectTabFragment.newInstance());
        fragments.add(MyTabFragment.newInstance());
        new FragmentTabUtils(getSupportFragmentManager(), radioGroup, fragments, R.id.container, this, this);
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        check_id = extras.getInt("check_id", -1);
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
    public void onTabCheckListener(RadioGroup group, int checkedId, int index) {

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
        if (radioGroup != null) {
            radioGroup.check(radioGroup.getChildAt(0).getId());
        }
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
                            SystemClock.sleep(5000);
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
                SharedPreferences updatePreferences =
                        getSharedPreferences("update", Context.MODE_PRIVATE);
                boolean update = updatePreferences.getBoolean("update", true);
                if (update && updateFlag) {
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
}
