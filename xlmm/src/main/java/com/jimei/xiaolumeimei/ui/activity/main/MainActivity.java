package com.jimei.xiaolumeimei.ui.activity.main;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jimei.library.utils.FileUtils;
import com.jimei.library.utils.JUtils;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.XlmmApp;
import com.jimei.xiaolumeimei.adapter.BaseTabAdapter;
import com.jimei.xiaolumeimei.base.BaseActivity;
import com.jimei.xiaolumeimei.base.BaseFragment;
import com.jimei.xiaolumeimei.base.CommonWebViewActivity;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.entities.AddressDownloadResultBean;
import com.jimei.xiaolumeimei.entities.CartsNumResultBean;
import com.jimei.xiaolumeimei.entities.PortalBean;
import com.jimei.xiaolumeimei.entities.UserInfoBean;
import com.jimei.xiaolumeimei.entities.UserTopic;
import com.jimei.xiaolumeimei.entities.VersionBean;
import com.jimei.xiaolumeimei.receiver.UpdateBroadReceiver;
import com.jimei.xiaolumeimei.service.ServiceResponse;
import com.jimei.xiaolumeimei.service.UpdateService;
import com.jimei.xiaolumeimei.ui.activity.product.CategoryListActivity;
import com.jimei.xiaolumeimei.ui.activity.trade.CartActivity;
import com.jimei.xiaolumeimei.ui.activity.user.LoginActivity;
import com.jimei.xiaolumeimei.ui.activity.user.UserActivity;
import com.jimei.xiaolumeimei.ui.fragment.product.ActivityFragment;
import com.jimei.xiaolumeimei.ui.fragment.product.ProductFragment;
import com.jimei.xiaolumeimei.ui.fragment.product.TodayNewFragment;
import com.jimei.xiaolumeimei.util.JumpUtils;
import com.jimei.xiaolumeimei.util.LoginUtils;
import com.jimei.library.widget.MainViewPager;
import com.jimei.xiaolumeimei.widget.VersionManager;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import okhttp3.Call;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.search_layout)
    RelativeLayout searchLayout;
    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.view_pager)
    MainViewPager viewPager;
    @Bind(R.id.btn_my)
    RelativeLayout btnMy;
    @Bind(R.id.btn_car)
    RelativeLayout btnCar;
    @Bind(R.id.text_num)
    TextView textNum;
    @Bind(R.id.btn_shop)
    RelativeLayout btnShop;
    @Bind(R.id.boutique_in)
    TextView boutiqueIn;
    @Bind(R.id.reload_text)
    TextView reloadText;
    @Bind(R.id.btn_layout)
    LinearLayout btnLayout;
    private int num = 1;
    private long firstTime = 0;
    private boolean updateFlag = true;
    private UpdateBroadReceiver mUpdateBroadReceiver;
    private boolean showBoutique;
    private String boutiqueUrl;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        showIndeterminateProgressDialog(false);
        addSubscription(XlmmApp.getVipInteractor(this)
            .getWxCode(new ServiceResponse<>()));
        showBoutique = false;
        List<BaseFragment> fragments = new ArrayList<>();
        fragments.add(TodayNewFragment.newInstance("今日特卖"));
        fragments.add(ActivityFragment.newInstance("精品活动"));
        addSubscription(XlmmApp.getMainInteractor(this)
            .getPortalBean("activitys,posters", new ServiceResponse<PortalBean>() {
                @Override
                public void onNext(PortalBean portalBean) {
                    List<PortalBean.CategorysBean> categorys = portalBean.getCategorys();
                    if (categorys != null && categorys.size() > 0) {
                        for (int i = 0; i < categorys.size(); i++) {
                            PortalBean.CategorysBean bean = categorys.get(i);
                            fragments.add(ProductFragment.newInstance(bean.getId(), bean.getName()));
                        }
                        BaseTabAdapter mAdapter = new BaseTabAdapter(getSupportFragmentManager(), fragments);
                        viewPager.setAdapter(mAdapter);
                        viewPager.setOffscreenPageLimit(fragments.size());
                        tabLayout.setupWithViewPager(viewPager);
                        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                    }
                    hideIndeterminateProgressDialog();
                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                    hideIndeterminateProgressDialog();
                    reloadText.setVisibility(View.VISIBLE);
                }
            }));
        LoginUtils.clearCacheEveryWeek(this);
        LoginUtils.setMamaInfo(this);
        downLoadAddress();
        setTopic();
        checkVersion();
    }

    private void showCartsNum() {
        addSubscription(XlmmApp.getMainInteractor(this)
            .getCartsNum(new ServiceResponse<CartsNumResultBean>() {
                @Override
                public void onNext(CartsNumResultBean bean) {
                    if (bean != null && bean.getResult() > 0) {
                        if (bean.getResult() > 99) {
                            textNum.setText("99+");
                        } else {
                            textNum.setText("" + bean.getResult());
                        }
                        textNum.setVisibility(View.VISIBLE);
                    } else {
                        textNum.setVisibility(View.GONE);
                    }
                }
            }));
    }

    private void showBoutique() {
        showBoutique = false;
        boutiqueIn.setVisibility(View.GONE);
        addSubscription(XlmmApp.getMainInteractor(this)
            .getProfile(new ServiceResponse<UserInfoBean>() {
                @Override
                public void onNext(UserInfoBean userInfoBean) {
                    if ((userInfoBean.getXiaolumm() != null) && (userInfoBean.getXiaolumm().getId() != 0)) {
                        addSubscription(XlmmApp.getVipInteractor(MainActivity.this)
                            .getMamaUrl()
                            .subscribe(mamaUrl -> {
                                boutiqueUrl = mamaUrl.getResults().get(0).getExtra().getBoutique();
                                showBoutique = true;
                                boutiqueIn.setVisibility(View.VISIBLE);
                            }, Throwable::printStackTrace));
                    }
                }
            }));
    }

    @Override
    protected void setListener() {
        boutiqueIn.setOnClickListener(this);
        searchLayout.setOnClickListener(this);
        btnMy.setOnClickListener(this);
        btnCar.setOnClickListener(this);
        btnShop.setOnClickListener(this);
        reloadText.setOnClickListener(this);
    }

    @Override
    protected void initViews() {
        setSwipeBackEnable(false);
    }

    public void setTabLayoutMarginTop(double percent) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, tabLayout.getHeight());
        if (percent > 0) {
            viewPager.setScrollable(false);
            double height = percent * tabLayout.getHeight();
            params.setMargins(0, (int) -height, 0, 0);
            tabLayout.setLayoutParams(params);
            if (percent == 1) {
                btnLayout.setVisibility(View.GONE);
            } else {
                btnLayout.setVisibility(View.VISIBLE);
            }
        } else {
            viewPager.setScrollable(true);
            params.setMargins(0, 0, 0, 0);
            btnLayout.setVisibility(View.VISIBLE);
        }
        tabLayout.setLayoutParams(params);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_layout:
                readyGo(CategoryListActivity.class);
                break;
            case R.id.btn_my:
                if (!LoginUtils.checkLoginState(getApplicationContext())) {
                    Bundle myBundle = new Bundle();
                    myBundle.putString("login", "my");
                    readyGo(LoginActivity.class, myBundle);
                } else {
                    readyGo(UserActivity.class);
                }
                break;
            case R.id.btn_car:
                if (!LoginUtils.checkLoginState(getApplicationContext())) {
                    Bundle carBundle = new Bundle();
                    carBundle.putString("login", "car");
                    readyGo(LoginActivity.class, carBundle);
                } else {
                    readyGo(CartActivity.class);
                }
                break;
            case R.id.btn_shop:
                if (!LoginUtils.checkLoginState(getApplicationContext())) {
                    Bundle carBundle = new Bundle();
                    carBundle.putString("login", "main");
                    readyGo(LoginActivity.class, carBundle);
                } else {
                    if (showBoutique) {
                        JumpUtils.jumpToWebViewWithCookies(MainActivity.this, boutiqueUrl, -1, CommonWebViewActivity.class, false, true);
                    } else {
                        new AlertDialog.Builder(this)
                            .setTitle("提示")
                            .setMessage("您暂时不是小鹿妈妈,请关注\"小鹿美美\"公众号,获取更多信息哦!")
                            .setPositiveButton("确定", (dialog, which) -> dialog.dismiss())
                            .show();
                    }
                }
                break;
            case R.id.boutique_in:
                JumpUtils.jumpToWebViewWithCookies(MainActivity.this, boutiqueUrl, -1, CommonWebViewActivity.class, false, true);
                break;
            case R.id.reload_text:
                reloadText.setVisibility(View.GONE);
                initData();
                showBoutique();
                showCartsNum();
                break;
            default:
                break;
        }
    }

    private void setTopic() {
        addSubscription(XlmmApp.getMainInteractor(this)
            .getTopic(new ServiceResponse<UserTopic>() {
                @Override
                public void onNext(UserTopic userTopic) {
                    List<String> topics = userTopic.getTopics();
                    if (topics != null) {
                        for (int i = 0; i < topics.size(); i++) {
                            MiPushClient.subscribe(MainActivity.this, topics.get(i), null);
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
                    Intent intent = new Intent(MainActivity.this, UpdateService.class);
                    intent.putExtra(UpdateService.EXTRAS_DOWNLOAD_URL, versionBean.getDownload_link());
                    startService(intent);
                    versionManager.getDialog().dismiss();
                    JUtils.Toast("应用正在后台下载!");
                });
                if (num == 1 && updateFlag) {
                    num = 2;
                    versionManager.checkVersion(MainActivity.this);
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
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
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

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        filter.addAction(UpdateBroadReceiver.ACTION_RETRY_DOWNLOAD);
        mUpdateBroadReceiver = new UpdateBroadReceiver();
        registerReceiver(mUpdateBroadReceiver, filter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (LoginUtils.checkLoginState(this)) {
            showCartsNum();
            showBoutique();
        } else {
            textNum.setVisibility(View.GONE);
            boutiqueIn.setVisibility(View.GONE);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        updateFlag = false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mUpdateBroadReceiver);
    }
}
