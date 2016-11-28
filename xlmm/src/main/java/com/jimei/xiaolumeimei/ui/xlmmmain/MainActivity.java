package com.jimei.xiaolumeimei.ui.xlmmmain;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jimei.library.utils.DisplayUtils;
import com.jimei.library.utils.FileUtils;
import com.jimei.library.utils.JUtils;
import com.jimei.library.utils.ViewUtils;
import com.jimei.library.widget.AutoToolbar;
import com.jimei.library.widget.BrandView;
import com.jimei.library.widget.SpaceItemDecoration;
import com.jimei.library.widget.badgelib.BadgeView;
import com.jimei.library.widget.banner.SliderLayout;
import com.jimei.library.widget.banner.SliderTypes.BaseSliderView;
import com.jimei.library.widget.banner.SliderTypes.DefaultSliderView;
import com.jimei.library.widget.scrolllayout.ScrollableLayout;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.MainActivityAdapter;
import com.jimei.xiaolumeimei.adapter.MainCategoryAdapter;
import com.jimei.xiaolumeimei.base.BasePresenterActivity;
import com.jimei.xiaolumeimei.base.CommonWebViewActivity;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.entities.AddressDownloadResultBean;
import com.jimei.xiaolumeimei.entities.CartsNumResultBean;
import com.jimei.xiaolumeimei.entities.CategoryDownBean;
import com.jimei.xiaolumeimei.entities.IsGetcoupon;
import com.jimei.xiaolumeimei.entities.PortalBean;
import com.jimei.xiaolumeimei.entities.UserInfoBean;
import com.jimei.xiaolumeimei.entities.UserTopic;
import com.jimei.xiaolumeimei.entities.event.LogOutEmptyEvent;
import com.jimei.xiaolumeimei.entities.event.SetMiPushEvent;
import com.jimei.xiaolumeimei.entities.event.UserChangeEvent;
import com.jimei.xiaolumeimei.entities.event.UserInfoEmptyEvent;
import com.jimei.xiaolumeimei.receiver.UpdateBroadReceiver;
import com.jimei.xiaolumeimei.ui.activity.main.ComplainActivity;
import com.jimei.xiaolumeimei.ui.activity.product.CollectionActivity;
import com.jimei.xiaolumeimei.ui.activity.trade.AllOrdersActivity;
import com.jimei.xiaolumeimei.ui.activity.trade.AllRefundsActivity;
import com.jimei.xiaolumeimei.ui.activity.trade.CartActivity;
import com.jimei.xiaolumeimei.ui.activity.user.AllCouponActivity;
import com.jimei.xiaolumeimei.ui.activity.user.InformationActivity;
import com.jimei.xiaolumeimei.ui.activity.user.LoginActivity;
import com.jimei.xiaolumeimei.ui.activity.user.MembershipPointActivity;
import com.jimei.xiaolumeimei.ui.activity.user.WalletActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MamaActivity;
import com.jimei.xiaolumeimei.ui.fragment.main.FirstFragment;
import com.jimei.xiaolumeimei.ui.fragment.main.GetCouponFragment;
import com.jimei.xiaolumeimei.ui.fragment.main.MastFragment;
import com.jimei.xiaolumeimei.ui.fragment.product.ProductListFragment;
import com.jimei.xiaolumeimei.utils.JumpUtils;
import com.jimei.xiaolumeimei.utils.LoginUtils;
import com.jimei.xiaolumeimei.widget.NoDoubleClickListener;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import okhttp3.Call;
import retrofit2.Response;

/**
 * Created by itxuye on 2016/7/4.
 */
public class MainActivity extends BasePresenterActivity<MainPresenter, MainModel>
        implements MainContract.View, View.OnClickListener, ViewPager.OnPageChangeListener,
        NavigationView.OnNavigationItemSelectedListener, ScrollableLayout.OnScrollListener
        , SwipeRefreshLayout.OnRefreshListener {
    private static final String POST_URL = "?imageMogr2/format/jpg/quality/70";
    public static String TAG = "MainActivity";
    Map<String, String> map = new HashMap<>();
    TextView tvNickname;
    ImageView imgUser;
    TextView tvPoint;
    TextView tvCoupon;
    TextView tvMoney;
    @Bind(R.id.tool_bar)
    AutoToolbar toolbar;
    @Bind(R.id.rv_cart)
    RelativeLayout carts;
    @Bind(R.id.rl_mmentry)
    RelativeLayout rl_mmentry;
    @Bind(R.id.collect)
    ImageView collectIv;
    @Bind(R.id.scrollableLayout)
    ScrollableLayout scrollableLayout;
    @Bind(R.id.swipe_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawer;
    @Bind(R.id.brand)
    LinearLayout brand;
    @Bind(R.id.cart_view)
    View cart_view;
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.nav_view)
    NavigationView navigationView;
    @Bind(R.id.slider)
    SliderLayout mSliderLayout;
    @Bind(R.id.viewPager)
    ViewPager vp;
    @Bind(R.id.rv_top)
    ImageView rvTop;
    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.show_content)
    RelativeLayout showContent;
    @Bind(R.id.activity_rv)
    RecyclerView activityRv;
    SharedPreferences sharedPreferencesTime;
    private int mask;
    private List<ProductListFragment> list = new ArrayList<>();
    private BadgeView badge;
    private TextView msg1;
    private TextView msg2;
    private TextView msg3;
    private ImageView loginFlag;
    private View llayout;
    private int rvTopHeight;
    private EventBus aDefault;
    private UpdateBroadReceiver mUpdateBroadReceiver;
    private String mamaid;
    private boolean updateFlag;
    private long firstTime;
    private MainCategoryAdapter mMainCategoryAdapter;
    private boolean mamaFlag = false;
    private long lastClickTime = 0;
    private MainActivityAdapter mainActivityAdapter;

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        filter.addAction(UpdateBroadReceiver.ACTION_RETRY_DOWNLOAD);
        mUpdateBroadReceiver = new UpdateBroadReceiver();
        registerReceiver(mUpdateBroadReceiver, filter);
    }

    @Override
    protected View getLoadingView() {
        return showContent;
    }

    @Override
    protected void initData() {
        showIndeterminateProgressDialog(false);
        initMainView(null);
        mPresenter.getUserInfoBean();
        mPresenter.getCategoryDown();
        mPresenter.getVersion();
        mPresenter.getAddressVersionAndUrl();
        mPresenter.getTopic();
        if (LoginUtils.isJumpToLogin(getApplicationContext())) {
            FirstFragment firstFragment = FirstFragment.newInstance("first");
            firstFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.Translucent_NoTitle);
            firstFragment.show(getFragmentManager(), "first");
        }
        if (LoginUtils.checkLoginState(getApplicationContext())) {
            mPresenter.isCouPon();
        }
        LoginUtils.clearCacheEveryWeek(this);
    }

    @Override
    protected void setListener() {
        carts.setOnClickListener(this);
        collectIv.setOnClickListener(this);
        rl_mmentry.setOnClickListener(this);
        vp.addOnPageChangeListener(this);
        navigationView.setNavigationItemSelectedListener(this);
        llayout.findViewById(R.id.ll_money).setOnClickListener(this);
        llayout.findViewById(R.id.ll_score).setOnClickListener(this);
        llayout.findViewById(R.id.ll_discount).setOnClickListener(this);
        llayout.findViewById(R.id.imgUser).setOnClickListener(this);
        scrollableLayout.setOnScrollListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void getDataCallBack() {
        initData();
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        aDefault = EventBus.getDefault();
        aDefault.register(this);
        findById();
        initSlide();
        showBadge();
        initViewsForTab();
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        mRecyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(15, 15, 25, 25));
        mMainCategoryAdapter = new MainCategoryAdapter(MainActivity.this);
        mRecyclerView.setAdapter(mMainCategoryAdapter);
        activityRv.setLayoutManager(new LinearLayoutManager(this));
        activityRv.setOverScrollMode(View.OVER_SCROLL_NEVER);
        activityRv.addItemDecoration(new SpaceItemDecoration(0, 0, 6, 6));
        mainActivityAdapter = new MainActivityAdapter(this);
        activityRv.setAdapter(mainActivityAdapter);
    }

    @Override
    public void onClick(View v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > NoDoubleClickListener.MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            drawer.closeDrawers();
            String flag = "main";
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            switch (v.getId()) {
                case R.id.rv_cart:
                    intent = new Intent(MainActivity.this, CartActivity.class);
                    flag = "cart";
                    break;
                case R.id.collect:
                    intent = new Intent(MainActivity.this, CollectionActivity.class);
                    flag = "collect";
                    break;
                case R.id.rl_mmentry:
                    JUtils.Log(TAG, "xiaolu mama entry");
                    intent = new Intent(MainActivity.this, MamaActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("mamaid", mamaid);
                    intent.putExtras(bundle);
                    break;
                case R.id.ll_money:
                    intent = new Intent(MainActivity.this, WalletActivity.class);
                    flag = "money";
                    break;
                case R.id.ll_score:
                    flag = "point";
                    intent = new Intent(MainActivity.this, MembershipPointActivity.class);
                    break;
                case R.id.ll_discount:
                    flag = "coupon";
                    intent = new Intent(MainActivity.this, AllCouponActivity.class);
                    break;
                case R.id.imgUser:
                    intent = new Intent(MainActivity.this, InformationActivity.class);
                    break;
            }
            if (!(LoginUtils.checkLoginState(getApplicationContext()))) {
                login(flag);
            } else {
                startActivity(intent);
            }
        }
    }

    public void login(String flag) {
        Bundle bundle = new Bundle();
        bundle.putString("login", flag);
        readyGo(LoginActivity.class, bundle);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        swipeRefreshLayout.setEnabled(false);
    }

    @Override
    public void onPageSelected(int position) {
        try {
            scrollableLayout.getHelper().setCurrentScrollableContainer(list.get(position));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        swipeRefreshLayout.setEnabled(true);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Bundle bundle;
        int id = item.getItemId();
        if (!LoginUtils.checkLoginState(getApplicationContext())) {
            /*未登录进入登录界面*/
            bundle = new Bundle();
            bundle.putString("login", "main");
            readyGo(LoginActivity.class, bundle);
        } else {
            if (id == R.id.nav_tobepaid) {
                bundle = new Bundle();
                bundle.putInt("fragment", 2);
                readyGo(AllOrdersActivity.class, bundle);
            } else if (id == R.id.nav_tobereceived) {
                bundle = new Bundle();
                bundle.putInt("fragment", 3);
                readyGo(AllOrdersActivity.class, bundle);
            } else if (id == R.id.nav_returned) {
                readyGo(AllRefundsActivity.class);
            } else if (id == R.id.nav_orders) {
                bundle = new Bundle();
                bundle.putInt("fragment", 1);
                readyGo(AllOrdersActivity.class, bundle);
            } else if (id == R.id.nav_problem) {
                JumpUtils.jumpToWebViewWithCookies(this, "https://m.xiaolumeimei.com/mall/faq", -1,
                        CommonWebViewActivity.class, "常见问题", false);
            } else if (id == R.id.nav_complain) {
                readyGo(ComplainActivity.class);
            } else if (id == R.id.my_shop) {
                if (mamaFlag) {
                    bundle = new Bundle();
                    bundle.putString("mamaid", mamaid);
                    readyGo(MamaActivity.class, bundle);
                } else {
                    new AlertDialog.Builder(this)
                            .setTitle("提示")
                            .setMessage("您暂时不是小鹿妈妈,请关注\"小鹿美美\"公众号,获取更多信息哦!")
                            .setPositiveButton("确定", (dialog, which) -> dialog.dismiss())
                            .show();
                }
            }
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onScroll(int transY, int maxY) {
        JUtils.Log(TAG, "onScroll");
        transY = -transY;
        if (rvTopHeight == (DisplayUtils.getScreenH(this) * 2 - 16)) {
            rvTopHeight = carts.getTop();
        }
        if (0 > rvTopHeight + transY) {
            rvTop.setVisibility(View.VISIBLE);
            rvTop.setOnClickListener(v -> {
                scrollableLayout.scrollTo(0, 0);
                list.get(vp.getCurrentItem()).goToTop();
            });
        } else {
            rvTop.setVisibility(View.GONE);
        }

        if ((-transY) > 0) {
            swipeRefreshLayout.setEnabled(false);
        } else {
            swipeRefreshLayout.setEnabled(true);
        }
    }

    @Override
    public void onRefresh() {
        initMainView(swipeRefreshLayout);
        try {
            switch (vp.getCurrentItem()) {
                case 0:
                    list.get(0).load(swipeRefreshLayout);
                    break;
                case 1:
                    list.get(1).load(swipeRefreshLayout);
                    break;
                case 2:
                    list.get(2).load(swipeRefreshLayout);
                    break;
            }
            scrollableLayout.getHelper().setCurrentScrollableContainer(list.get(vp.getCurrentItem()));
            mPresenter.getAddressVersionAndUrl();
            mPresenter.getCategoryDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void findById() {
        llayout = navigationView.getHeaderView(0);
        tvCoupon = (TextView) llayout.findViewById(R.id.tvDiscount);
        tvMoney = (TextView) llayout.findViewById(R.id.tvMoney);
        tvPoint = (TextView) llayout.findViewById(R.id.tvScore);
        imgUser = (ImageView) llayout.findViewById(R.id.imgUser);
        loginFlag = (ImageView) llayout.findViewById(R.id.login_flag);
        tvNickname = (TextView) llayout.findViewById(R.id.tvNickname);
        LinearLayout nav_tobepaid =
                (LinearLayout) navigationView.getMenu().findItem(R.id.nav_tobepaid).getActionView();
        msg1 = (TextView) nav_tobepaid.findViewById(R.id.msg);
        LinearLayout nav_tobereceived =
                (LinearLayout) navigationView.getMenu().findItem(R.id.nav_tobereceived).getActionView();
        msg2 = (TextView) nav_tobereceived.findViewById(R.id.msg);
        LinearLayout nav_refund =
                (LinearLayout) navigationView.getMenu().findItem(R.id.nav_returned).getActionView();
        msg3 = (TextView) nav_refund.findViewById(R.id.msg);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
    }

    @Override
    public void initSlide() {
        ActionBarDrawerToggle toggle =
                new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
                        R.string.navigation_drawer_close) {
                    @Override
                    public void onDrawerClosed(View drawerView) {
                        invalidateOptionsMenu();
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        invalidateOptionsMenu();
                    }
                };
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toggle.setHomeAsUpIndicator(R.drawable.icon_nav);
        toolbar.setNavigationIcon(R.drawable.icon_nav);
        if (!(LoginUtils.checkLoginState(getApplicationContext()))) {
            tvNickname.setText("点击登录");
        }
    }

    @Override
    public void showBadge() {
        badge = new BadgeView(this);
        badge.setTextSizeOff(7);
        badge.setBackground(4, Color.parseColor("#FF3840"));
        badge.setGravity(Gravity.END | Gravity.TOP);
        badge.setPadding(dip2Px(4), dip2Px(1), dip2Px(4), dip2Px(1));
        badge.setTargetView(cart_view);
    }

    @Override
    public void initViewsForTab() {
        SharedPreferences sharedPreferencesMask = getSharedPreferences("maskActivity", 0);
        sharedPreferencesTime = getSharedPreferences("resumeTime", 0);
        mask = sharedPreferencesMask.getInt("mask", 0);
        MyFragmentAdapter adapter = new MyFragmentAdapter(getSupportFragmentManager());
        list.add(ProductListFragment.newInstance(XlmmConst.TYPE_YESTERDAY, "昨天热卖"));
        list.add(ProductListFragment.newInstance(XlmmConst.TYPE_TODAY, "今天特卖"));
        list.add(ProductListFragment.newInstance(XlmmConst.TYPE_TOMORROW, "即将上新"));
        vp.setAdapter(adapter);
        vp.setOffscreenPageLimit(3);
        vp.setCurrentItem(1);
        tabLayout.setupWithViewPager(vp);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        scrollableLayout.getHelper().setCurrentScrollableContainer(list.get(1));
    }

    @Override
    public void initUserView(UserInfoBean userNewBean) {
        initUserNewView(userNewBean);
    }

    @Override
    public void initUserViewChange(UserInfoBean userNewBean) {
        if (null != userNewBean) {
            mamaid = userNewBean.getXiaolumm().getId() + "";
        } else {
            rl_mmentry.setVisibility(View.INVISIBLE);
            loginFlag.setVisibility(View.GONE);
            imgUser.setImageResource(R.drawable.img_head);
            mamaFlag = false;
        }

        if (null != userNewBean) {
            tvNickname.setText(userNewBean.getNick());
            if (userNewBean.getWaitpayNum() > 0) {
                msg1.setVisibility(View.VISIBLE);
                msg1.setText(userNewBean.getWaitpayNum() + "");
            } else {
                msg1.setVisibility(View.INVISIBLE);
            }
            if (userNewBean.getWaitgoodsNum() > 0) {
                msg2.setVisibility(View.VISIBLE);
                msg2.setText(userNewBean.getWaitgoodsNum() + "");
            } else {
                msg2.setVisibility(View.INVISIBLE);
            }
            if (userNewBean.getRefundsNum() > 0) {
                msg3.setVisibility(View.VISIBLE);
                msg3.setText(userNewBean.getRefundsNum() + "");
            } else {
                msg3.setVisibility(View.INVISIBLE);
            }
            String pointStr = userNewBean.getScore() + "";
            tvPoint.setText(pointStr);
            if (userNewBean.getUserBudget() != null) {
                String moneyStr =
                        (float) (Math.round(userNewBean.getUserBudget().getBudgetCash() * 100)) / 100 + "";
                tvMoney.setText(moneyStr);
            }
            String couponStr = userNewBean.getCouponNum() + "";
            tvCoupon.setText(couponStr);
        } else {
            tvPoint.setText("0.00");
            tvMoney.setText("0.00");
            tvCoupon.setText("0.00");
            imgUser.setImageResource(R.drawable.img_head);
            tvNickname.setText("点击登录");
            msg1.setVisibility(View.INVISIBLE);
            msg2.setVisibility(View.INVISIBLE);
            msg3.setVisibility(View.INVISIBLE);
        }
    }

    private void initUserNewView(UserInfoBean userNewBean) {
        if (null != userNewBean) {
            if (!TextUtils.isEmpty(userNewBean.getThumbnail())) {
                ViewUtils.loadImgToImgView(MainActivity.this, imgUser, userNewBean.getThumbnail());
            }
            if (userNewBean.isHasUsablePassword() && userNewBean.getMobile() != "") {
                loginFlag.setVisibility(View.GONE);
            } else {
                loginFlag.setVisibility(View.VISIBLE);
            }
            mamaid = userNewBean.getXiaolumm().getId() + "";
            if ((userNewBean.getXiaolumm() != null) && (userNewBean.getXiaolumm().getId() != 0)) {
                rl_mmentry.setVisibility(View.VISIBLE);
                mamaFlag = true;
            } else {
                rl_mmentry.setVisibility(View.INVISIBLE);
                mamaFlag = false;
            }
        } else {
            rl_mmentry.setVisibility(View.INVISIBLE);
            loginFlag.setVisibility(View.GONE);
            imgUser.setImageResource(R.drawable.img_head);
            mamaFlag = false;
        }
    }

    public int dip2Px(float dip) {
        return (int) (dip * getResources().getDisplayMetrics().density + 0.5f);
    }

    @Override
    public void initDrawer(UserInfoBean userInfoBean) {
        initSlideDraw(userInfoBean);
    }

    private void initSlideDraw(UserInfoBean userInfoBean) {
        if (null != userInfoBean) {
            tvNickname.setText(userInfoBean.getNick());
            if (!TextUtils.isEmpty(userInfoBean.getThumbnail())) {
                ViewUtils.loadImgToImgView(MainActivity.this, imgUser, userInfoBean.getThumbnail());
            }
            if (userInfoBean.getWaitpayNum() > 0) {
                msg1.setVisibility(View.VISIBLE);
                msg1.setText(userInfoBean.getWaitpayNum() + "");
            } else {
                msg1.setVisibility(View.INVISIBLE);
            }
            Log.i(TAG, "" + userInfoBean.getWaitpayNum());
            if (userInfoBean.getWaitgoodsNum() > 0) {
                msg2.setVisibility(View.VISIBLE);
                msg2.setText(userInfoBean.getWaitgoodsNum() + "");
            } else {
                msg2.setVisibility(View.INVISIBLE);
            }
            if (userInfoBean.getRefundsNum() > 0) {
                msg3.setVisibility(View.VISIBLE);
                msg3.setText(userInfoBean.getRefundsNum() + "");
            } else {
                msg3.setVisibility(View.INVISIBLE);
            }
            String pointStr = userInfoBean.getScore() + "";
            tvPoint.setText(pointStr);
            if (userInfoBean.getUserBudget() != null) {
                String moneyStr =
                        (float) (Math.round(userInfoBean.getUserBudget().getBudgetCash() * 100)) / 100 + "";
                tvMoney.setText(moneyStr);
            }
            String couponStr = userInfoBean.getCouponNum() + "";
            tvCoupon.setText(couponStr);
        } else {
            tvPoint.setText("0.00");
            tvMoney.setText("0.00");
            tvCoupon.setText("0.00");
            imgUser.setImageResource(R.drawable.img_head);
            tvNickname.setText("点击登录");
            msg1.setVisibility(View.INVISIBLE);
            msg2.setVisibility(View.INVISIBLE);
            msg3.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void initShowCoiuponWindow(Response<IsGetcoupon> isGetcouponResponse) {
        if (isGetcouponResponse.isSuccessful()) {
            if (isGetcouponResponse.body().getIsPicked() == 0) {
                GetCouponFragment firstFragment = GetCouponFragment.newInstance("getCoupon");
                firstFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.Translucent_NoTitle);
                firstFragment.show(getFragmentManager(), "getCoupon");
            }
        }
    }

    @Override
    public void initMainView(SwipeRefreshLayout swipeRefreshLayout)
            throws NullPointerException {
        showNetworkError();
        mPresenter.getPortalBean(swipeRefreshLayout);
    }

    @Override
    public void initSliderLayout(PortalBean postBean) throws RuntimeException {
        JUtils.Log(TAG, "refreshSliderLayout");
        map.clear();
        List<PortalBean.PostersBean> posters = postBean.getPosters();
        for (int i = 0; i < posters.size(); i++) {
            map.put(posters.get(i).getPic_link(), posters.get(i).getApp_link());
        }
        if (mSliderLayout != null) {
            mSliderLayout.removeAllSliders();
        }
        for (String name : map.keySet()) {
            DefaultSliderView textSliderView = new DefaultSliderView(MainActivity.this);
            textSliderView.image(name + POST_URL).setScaleType(BaseSliderView.ScaleType.CenterInside);
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle().putString("extra", map.get(name));
            mSliderLayout.addSlider(textSliderView);
            textSliderView.setOnSliderClickListener(slider -> {
                if (slider.getBundle() != null) {
                    MobclickAgent.onEvent(MainActivity.this, "BannerID");
                    String link = slider.getBundle().getString("extra");
                    JumpUtils.push_jump_proc(MainActivity.this, link);
                }
            });
        }
        mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Left_Bottom);
        mSliderLayout.setDuration(3000);
    }

    @Override
    public void initCategory(PortalBean postBean) throws RuntimeException {
        JUtils.Log(TAG, "refreshCategory");
        List<PortalBean.CategorysBean> categorys = postBean.getCategorys();
        mMainCategoryAdapter.updateWithClear(categorys);
    }

    @Override
    public void initBrand(PortalBean postBean) throws RuntimeException {
        List<BrandView> brandViews = new ArrayList<>();
        brand.removeAllViews();
        brandViews.clear();
        if (postBean.getPromotion_brands() != null) {
            brand.setVisibility(View.VISIBLE);
            List<PortalBean.PromotionBrandsBean> brandPromotionEntities = postBean.getPromotion_brands();
            if (brandPromotionEntities.size() != 0) {
                BrandView brandView;
                for (int i = 0; i < brandPromotionEntities.size(); i++) {
                    brandView = new BrandView(MainActivity.this);
                    brandViews.add(brandView);
                    brand.addView(brandView);
                }
                for (int i = 0; i < brandPromotionEntities.size(); i++) {
                    brandViews.get(i).setBrandtitleImage(brandPromotionEntities.get(i).getAct_logo());
                    brandViews.get(i)
                            .setBrandDesText(
                                    brandPromotionEntities.get(i).getExtras().getBrandinfo().getTail_title());
                    brandViews.get(i).setBrandTitle(brandPromotionEntities.get(i).getTitle());
                    brandViews.get(i).setBrandListImage(brandPromotionEntities.get(i).getAct_img());
                    final int finalI1 = i;
                    brandViews.get(i).setOnClickListener(v -> {
                        if (!TextUtils.isEmpty(brandPromotionEntities.get(finalI1).getAct_applink())) {
                            JumpUtils.push_jump_proc(mContext,
                                    brandPromotionEntities.get(finalI1).getAct_applink());
                        }
                    });
                }
            }
        } else {
            brand.setVisibility(View.GONE);
        }
    }

    @Override
    public void initActivity(PortalBean postBean) throws RuntimeException {
        JUtils.Log(TAG, "refreshPost");
        List<PortalBean.ActivitysBean> postActivityBean = postBean.getActivitys();
        if (null != postActivityBean && postActivityBean.size() > 0) {
            mainActivityAdapter.updateWithClear(postActivityBean);
            if (mask != postActivityBean.get(0).getId() && !TextUtils.isEmpty(
                    postActivityBean.get(0).getMask_link())) {
                MastFragment mastFragment = MastFragment.newInstance("mask");
                mastFragment.show(getFragmentManager(), "mask");
            }
        }
    }

    @Override
    public void downLoaAddressFile(AddressDownloadResultBean addressDownloadResultBean) {
        JUtils.Log(TAG, addressDownloadResultBean.toString());
        String downloadUrl = addressDownloadResultBean.getDownloadUrl();
        String hash = addressDownloadResultBean.getHash();
        if (!FileUtils.isAddressFileHashSame(getApplicationContext(), hash) ||
                !FileUtils.isFileExist(XlmmConst.XLMM_DIR + "areas.json")) {
            OkHttpUtils.get()
                    .url(downloadUrl)
                    .build()
                    .execute(new FileCallBack(XlmmConst.XLMM_DIR, "areas.json") {
                        @Override
                        public void inProgress(float progress, long total, int id) {
                            JUtils.Log(TAG, (int) (100 * (progress / total)) + "");
                        }

                        @Override
                        public void onError(Call call, Exception e, int id) {
                            if (FileUtils.isFolderExist(XlmmConst.XLMM_DIR + "areas.json")) {
                                FileUtils.deleteFile(XlmmConst.XLMM_DIR + "areas.json");
                            }
                        }

                        @Override
                        public void onResponse(File response, int id) {
                            JUtils.Log(TAG, response.getAbsolutePath());
                            FileUtils.saveAddressFile(getApplicationContext(), hash);
                        }
                    });
        }
    }

    @Override
    public void downCategoryFile(CategoryDownBean categoryDownBean) {
        JUtils.Log(TAG, categoryDownBean.toString());
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
                            JUtils.Log(TAG, response.getAbsolutePath());
                            FileUtils.saveCategoryFile(getApplicationContext(), sha1);
                            FileUtils.saveCategoryImg(XlmmConst.CATEGORY_JSON);
                        }
                    });
        }
    }

    private class MyFragmentAdapter extends FragmentPagerAdapter {

        public MyFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return getCount() > position ? list.get(position) : null;
        }

        @Override
        public int getCount() {
            return list == null ? 0 : list.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return list.get(position).getTitle();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void initLoginInfo(UserInfoEmptyEvent event) {
        JUtils.Log(TAG, "=======initLoginInfo");
        mPresenter.getUserInfoBean();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void initLogOutInfo(LogOutEmptyEvent event) {
        tvPoint.setText("0.00");
        tvMoney.setText("0.00");
        tvCoupon.setText("0.00");
        imgUser.setImageResource(R.drawable.img_head);
        tvNickname.setText("点击登录");
        msg1.setVisibility(View.INVISIBLE);
        msg2.setVisibility(View.INVISIBLE);
        msg3.setVisibility(View.INVISIBLE);

        rl_mmentry.setVisibility(View.INVISIBLE);
        loginFlag.setVisibility(View.GONE);
        imgUser.setImageResource(R.drawable.img_head);
        mamaFlag = false;
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void initUserinfoInfoChange(UserChangeEvent event) {
        JUtils.Log(TAG, "initUserinfoInfoChange()");
        mPresenter.getUserInfoBeanChange();
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void setMipush(SetMiPushEvent event) {
        JUtils.Log("regid", MiPushClient.getRegId(getApplicationContext()));
        LoginUtils.setPushUserAccount(this, MiPushClient.getRegId(getApplicationContext()));
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
        MobclickAgent.onPause(this);
        JUtils.Log(TAG, "onPause()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(this.getClass().getSimpleName());
        MobclickAgent.onResume(this);
        mPresenter.getCartsNum();
        mPresenter.getUserInfoBeanChange();
        JUtils.Log(TAG, "resume");
    }

    @Override
    public void iniCartsNum(CartsNumResultBean cartsNumResultBean) {
        if (cartsNumResultBean != null) {
            int num = cartsNumResultBean.getResult();
            badge.setBadgeCount(num);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mUpdateBroadReceiver);
        JUtils.Log(TAG, "onStop()");
    }

    @Override
    protected void onDestroy() {
        updateFlag = false;
        aDefault.unregister(this);
        UserChangeEvent stickyEvent = aDefault.getStickyEvent(UserChangeEvent.class);
        if (stickyEvent != null) {
            aDefault.removeStickyEvent(stickyEvent);
        }
        mPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void checkVersion(int versionCode, String content, String downloadUrl, boolean isAutoUpdate) {
        updateFlag = true;
        new Thread(() -> {
            try {
                Thread.sleep(500 * 10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            runOnUiThread(() -> {
                if (updateFlag) {
                    VersionManager versionManager = VersionManager.newInstance(versionCode, content, false);
                    if (isAutoUpdate) {
                        versionManager.setPositiveListener(v -> {
                            Intent intent = new Intent(MainActivity.this, UpdateService.class);
                            intent.putExtra(UpdateService.EXTRAS_DOWNLOAD_URL, downloadUrl);
                            startService(intent);
                            versionManager.getDialog().dismiss();
                            JUtils.Toast("应用正在后台下载!");
                        });
                        SharedPreferences updatePreferences =
                                getSharedPreferences("update", Context.MODE_PRIVATE);
                        boolean update = updatePreferences.getBoolean("update", true);
                        if (update && updateFlag) {
                            versionManager.checkVersion(MainActivity.this);
                        }
                    }
                }
            });
        }).start();
    }

    @Override
    public void setTopic(UserTopic userTopic) {
        List<String> topics = userTopic.getTopics();
        if (topics != null) {
            for (int i = 0; i < topics.size(); i++) {
                MiPushClient.subscribe(this, topics.get(i), null);
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
                return true;
            } else {
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 1000) {
                    firstTime = secondTime;
                    JUtils.Toast("再按一次退出程序!");
                    return true;
                }
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
