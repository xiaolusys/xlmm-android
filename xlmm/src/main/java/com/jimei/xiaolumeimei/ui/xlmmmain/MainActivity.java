package com.jimei.xiaolumeimei.ui.xlmmmain;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
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
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.ActivityListAdapter;
import com.jimei.xiaolumeimei.base.BasePresenterActivity;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.entities.AddressDownloadResultBean;
import com.jimei.xiaolumeimei.entities.CartsNumResultBean;
import com.jimei.xiaolumeimei.entities.CategoryDownBean;
import com.jimei.xiaolumeimei.entities.IsGetcoupon;
import com.jimei.xiaolumeimei.entities.PortalBean;
import com.jimei.xiaolumeimei.entities.UserInfoBean;
import com.jimei.xiaolumeimei.entities.UserTopic;
import com.jimei.xiaolumeimei.event.LogOutEmptyEvent;
import com.jimei.xiaolumeimei.event.SetMiPushEvent;
import com.jimei.xiaolumeimei.event.UserChangeEvent;
import com.jimei.xiaolumeimei.event.UserInfoEmptyEvent;
import com.jimei.xiaolumeimei.receiver.UpdateBroadReceiver;
import com.jimei.xiaolumeimei.ui.activity.main.ActivityWebViewActivity;
import com.jimei.xiaolumeimei.ui.activity.main.ComplainActivity;
import com.jimei.xiaolumeimei.ui.activity.product.CollectionActivity;
import com.jimei.xiaolumeimei.ui.activity.product.ProductListActivity;
import com.jimei.xiaolumeimei.ui.activity.trade.AllOrdersActivity;
import com.jimei.xiaolumeimei.ui.activity.trade.AllRefundsActivity;
import com.jimei.xiaolumeimei.ui.activity.trade.CartActivity;
import com.jimei.xiaolumeimei.ui.activity.user.AllCouponActivity;
import com.jimei.xiaolumeimei.ui.activity.user.CustomProblemActivity;
import com.jimei.xiaolumeimei.ui.activity.user.InformationActivity;
import com.jimei.xiaolumeimei.ui.activity.user.LoginActivity;
import com.jimei.xiaolumeimei.ui.activity.user.MembershipPointActivity;
import com.jimei.xiaolumeimei.ui.activity.user.WalletActivity;
import com.jimei.xiaolumeimei.ui.fragment.v1.view.MastFragment;
import com.jimei.xiaolumeimei.ui.fragment.v2.FirstFragment;
import com.jimei.xiaolumeimei.ui.fragment.v2.GetCouponFragment;
import com.jimei.xiaolumeimei.ui.fragment.v2.ProductListFragment;
import com.jimei.xiaolumeimei.ui.mminfo.MamaActivity;
import com.jimei.xiaolumeimei.utils.DisplayUtils;
import com.jimei.xiaolumeimei.utils.FileUtils;
import com.jimei.xiaolumeimei.utils.JumpUtils;
import com.jimei.xiaolumeimei.utils.LoginUtils;
import com.jimei.xiaolumeimei.utils.StatusBarUtil;
import com.jimei.xiaolumeimei.utils.ViewUtils;
import com.jimei.xiaolumeimei.widget.AutoToolbar;
import com.jimei.xiaolumeimei.widget.BrandView;
import com.jimei.xiaolumeimei.widget.VersionManager;
import com.jimei.xiaolumeimei.widget.badgelib.BadgeView;
import com.jimei.xiaolumeimei.widget.banner.SliderLayout;
import com.jimei.xiaolumeimei.widget.banner.SliderTypes.BaseSliderView;
import com.jimei.xiaolumeimei.widget.banner.SliderTypes.DefaultSliderView;
import com.jimei.xiaolumeimei.widget.scrolllayout.ScrollableLayout;
import com.jimei.xiaolumeimei.xlmmService.UpdateService;
import com.jude.utils.JUtils;
import com.umeng.analytics.MobclickAgent;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import okhttp3.Call;
import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * Created by itxuye on 2016/7/4.
 */
public class MainActivity extends BasePresenterActivity<MainPresenter, MainModel>
        implements MainContract.View, View.OnClickListener, ViewPager.OnPageChangeListener,
        NavigationView.OnNavigationItemSelectedListener, ScrollableLayout.OnScrollListener,
        SwipeRefreshLayout.OnRefreshListener {

    private static final String POST_URL = "?imageMogr2/format/jpg/quality/70";
    public static String TAG = "MainActivity";
    Map<String, String> map = new HashMap<>();
//    List<ImageView> imageViewList = new ArrayList<>();
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
    @Bind(R.id.image_1)
    ImageView image1;
    @Bind(R.id.image_2)
    ImageView image2;
    @Bind(R.id.lv_activity)
    ListView activityLv;
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
//    @Bind(R.id.post_mainactivity)
//    LinearLayout post_activity_layout;
    @Bind(R.id.category_layout)
    LinearLayout categoryLayout;
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
    List<PortalBean.PostersBean> posters = new ArrayList<>();
    SharedPreferences sharedPreferencesTime;
    private int mask;
    private List<ProductListFragment> list = new ArrayList<>();
    private int num;
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
    private boolean upadteFlag;
    private ActivityListAdapter adapter;

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
        mPresenter.getTopic();
        mPresenter.getUserInfoBean();
        mPresenter.getAddressVersionAndUrl();
        mPresenter.getCategoryDown();
        mPresenter.getVersion();
        initMainView(null);
        if (LoginUtils.isJumpToLogin(getApplicationContext())) {
            FirstFragment firstFragment = FirstFragment.newInstance("first");
            firstFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.Translucent_NoTitle);
            firstFragment.show(getFragmentManager(), "first");
        }

        if (LoginUtils.checkLoginState(getApplicationContext())) {
            mPresenter.isCouPon();
        }
        LoginUtils.deleteIsMamaRenwulist(getApplicationContext());
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
        mPresenter.getTopic();
        mPresenter.getUserInfoBean();
        mPresenter.getAddressVersionAndUrl();
        mPresenter.getCategoryDown();
        mPresenter.getVersion();
        initMainView(null);
        vp.setCurrentItem(1);
        scrollableLayout.getHelper().setCurrentScrollableContainer(list.get(1));
        if (LoginUtils.checkLoginState(getApplicationContext())) {
            mPresenter.isCouPon();
        }
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

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
                startActivity(new Intent(MainActivity.this, AllRefundsActivity.class));
            } else if (id == R.id.nav_orders) {
                bundle = new Bundle();
                bundle.putInt("fragment", 1);
                readyGo(AllOrdersActivity.class, bundle);
            } else if (id == R.id.nav_problem) {
                JumpUtils.jumpToWebViewWithCookies(this, "http://m.xiaolumeimei.com/mall/faq", -1,
                        CustomProblemActivity.class);
            } else if (id == R.id.nav_complain) {
                readyGo(ComplainActivity.class);
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
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorAccent), 0);
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
            rl_mmentry.setVisibility(View.INVISIBLE);
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
            } else {
                rl_mmentry.setVisibility(View.INVISIBLE);
            }
        } else {
            rl_mmentry.setVisibility(View.INVISIBLE);
            loginFlag.setVisibility(View.GONE);
            imgUser.setImageResource(R.drawable.img_head);
            rl_mmentry.setVisibility(View.INVISIBLE);
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
        mPresenter.getPortalBean(swipeRefreshLayout);
    }

    @Override
    public void initSliderLayout(PortalBean postBean) throws NullPointerException {
        JUtils.Log(TAG, "refreshSliderLayout");

        posters.clear();
        map.clear();

        posters.addAll(postBean.getPosters());

        for (int i = 0; i < posters.size(); i++) {
            map.put(posters.get(i).getPic_link(), posters.get(i).getApp_link());
        }
        if (mSliderLayout != null) {
            mSliderLayout.removeAllSliders();
        }

        for (String name : map.keySet()) {
            DefaultSliderView textSliderView = new DefaultSliderView(MainActivity
                    .this);
            // initialize a SliderLayout
            textSliderView.image(name + POST_URL).setScaleType(BaseSliderView.ScaleType.CenterInside);
            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle().putString("extra", map.get(name));
            mSliderLayout.addSlider(textSliderView);
            mSliderLayout.setDuration(3000);
            mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Left_Bottom);
            textSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(BaseSliderView slider) {
                    if (slider.getBundle() != null) {
                        MobclickAgent.onEvent(MainActivity.this, "BannerID");
                        String extra = slider.getBundle().getString("extra");
                        if (!TextUtils.isEmpty(extra)) {
                            JumpUtils.JumpInfo jump_info = JumpUtils.get_jump_info(extra);
                            if (extra.startsWith("http://")) {
                                JumpUtils.jumpToWebViewWithCookies(MainActivity
                                        .this, extra, -1, ActivityWebViewActivity.class);
                            } else {
                                if (jump_info.getType() == XlmmConst.JUMP_PRODUCT_CHILDLIST) {
                                    Bundle childBundle = new Bundle();
                                    childBundle.putString("type", XlmmConst.TYPE_CHILD);
                                    childBundle.putString("title", "萌娃专区");
                                    readyGo(ProductListActivity.class, childBundle);
                                } else if (jump_info.getType() == XlmmConst.JUMP_PRODUCT_LADYLIST) {
                                    Bundle ladyBundle = new Bundle();
                                    ladyBundle.putString("type", XlmmConst.TYPE_LADY);
                                    ladyBundle.putString("title", "女装专区");
                                    readyGo(ProductListActivity.class, ladyBundle);
                                } else {
                                    JumpUtils.push_jump_proc(MainActivity.this, extra);
                                }
                            }
                        }
                    }
                }
            });
        }
    }

    @Override
    public void initCategory(PortalBean postBean) throws NullPointerException {
        JUtils.Log(TAG, "refreshCategory");
        categoryLayout.removeAllViews();
        List<PortalBean.CategorysBean> categorys = postBean.getCategorys();
        List<LinearLayout> layoutList = new ArrayList<>();
        int count = categorys.size() > 4 ? 4 : categorys.size();
        for (int i = 0; i < categorys.size(); i++) {
            if (i % count == 0) {
                LinearLayout layout = new LinearLayout(this);
                layout.setLayoutParams(new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                layout.setOrientation(LinearLayout.HORIZONTAL);
                layoutList.add(layout);
                categoryLayout.addView(layout);
            }
            ImageView imageView = new ImageView(this);
            imageView.setAdjustViewBounds(true);
            imageView.setScaleType(ImageView.ScaleType.CENTER);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            layoutList.get(i / count).addView(imageView);
            String cat_link = categorys.get(i).getCat_link();
            String name = categorys.get(i).getName();
            if (cat_link != null && cat_link.contains(XlmmConst.JUMP_PREFIX)) {
                imageView.setOnClickListener(v ->
                        JumpUtils.push_jump_proc(
                                MainActivity.this, cat_link, name));
            }
            ViewUtils.loadImageWithOkhttp(categorys.get(i).getCat_img(), MainActivity.this, imageView, count);
        }
    }

    @Override
    public void initBrand(PortalBean postBean) throws NullPointerException {
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
                    brandViews.get(i).setBrandtitleImage(brandPromotionEntities.get(i).getActLogo());
                    brandViews.get(i)
                            .setBrandDesText(
                                    brandPromotionEntities.get(i).getExtras().getBrandinfo().getTailTitle());
                    brandViews.get(i).setBrandTitle(brandPromotionEntities.get(i).getTitle());
                    brandViews.get(i).setBrandListImage(brandPromotionEntities.get(i).getActImg());
                    final int finalI1 = i;
                    brandViews.get(i).setOnClickListener(v -> {
                        if (!TextUtils.isEmpty(brandPromotionEntities.get(finalI1).getActApplink())) {
                            JumpUtils.push_jump_proc(mContext,
                                    brandPromotionEntities.get(finalI1).getActApplink());
                        }
                    });
                }
            }
        } else {
            brand.setVisibility(View.GONE);
        }
    }

    @Override
    public void initPost(PortalBean postBean) throws NullPointerException {
        JUtils.Log(TAG, "refreshPost");
//        if (post_activity_layout != null) {
//            post_activity_layout.removeAllViews();
//        }
//        imageViewList.clear();
        List<PortalBean.ActivitysBean> postActivityBean = postBean.getActivitys();
        if (null != postActivityBean && postActivityBean.size() > 0) {
//            post_activity_layout.setVisibility(View.VISIBLE);
//
//            ImageView imageView;
//
//
//            for (int i = 0; i < postActivityBean.size(); i++) {
//                imageView = new ImageView(MainActivity.this);
//                imageView.setScaleType(ImageView.ScaleType.CENTER);
//                imageView.setPadding(0, 12, 0, 12);
//                imageViewList.add(imageView);
//                post_activity_layout.addView(imageView);
//            }
//            for (int i = 0; i < postActivityBean.size(); i++) {
//                final int finalI = i;
//                OkHttpUtils.get()
//                        .url(postActivityBean.get(i).getAct_img() + POST_URL)
//                        .build()
//                        .execute(new BitmapCallback() {
//                            @Override
//                            public void onError(Call call, Exception e, int id) {
//                            }
//
//                            @Override
//                            public void onResponse(Bitmap response, int id) {
//                                if (response != null) {
//                                    imageViewList.get(finalI).setAdjustViewBounds(true);
//                                    int screenWidth = DisplayUtils.getScreenW(MainActivity.this);
//                                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(screenWidth,
//                                            LinearLayout.LayoutParams.WRAP_CONTENT);
//                                    imageViewList.get(finalI).setLayoutParams(lp);
//                                    imageViewList.get(finalI).setMaxWidth(screenWidth);
//                                    imageViewList.get(finalI).setMaxHeight(screenWidth * 5);
//                                    imageViewList.get(finalI).setImageBitmap(response);
//                                    if (postActivityBean.get(finalI).getAct_type().equals("coupon")) {
//                                        imageViewList.get(finalI).setOnClickListener(v -> mPresenter.getUsercoupons(
//                                                postActivityBean.get(finalI).getExtras().getTemplateId()));
//                                    } else {
//                                        imageViewList.get(finalI).setOnClickListener(v -> {
//                                            MobclickAgent.onEvent(MainActivity.this, "ActivityID");
//                                            if (postActivityBean.get(finalI).isLogin_required()) {
//                                                if (LoginUtils.checkLoginState(MainActivity.this) && (null
//                                                        != mPresenter.userInfoNewBean
//                                                        && (null
//                                                        != mPresenter.userInfoNewBean.getMobile())
//                                                        && !mPresenter.userInfoNewBean.getMobile().isEmpty())) {
//                                                    JumpUtils.jumpToWebViewWithCookies(MainActivity.this,
//                                                            postActivityBean.get(finalI).getAct_link(),
//                                                            postActivityBean.get(finalI).getId(), ActivityWebViewActivity.class,
//                                                            postActivityBean.get(finalI).getTitle());
//                                                } else {
//                                                    if (!LoginUtils.checkLoginState(MainActivity.this)) {
//                                                        JUtils.Toast("登录并绑定手机号后才可参加活动");
//                                                        Bundle bundle = new Bundle();
//                                                        bundle.putString("login", "goactivity");
//                                                        bundle.putString("actlink",
//                                                                postActivityBean.get(finalI).getAct_link());
//                                                        bundle.putInt("id", postActivityBean.get(finalI).getId());
//                                                        bundle.putString("title", postActivityBean.get(finalI).getTitle());
//
//                                                        readyGo(LoginActivity.class, bundle);
//                                                    } else {
//                                                        JUtils.Toast("登录成功,前往绑定手机号后才可参加活动");
//                                                        if (null != mPresenter.userInfoNewBean) {
//                                                            Bundle bundle = new Bundle();
//                                                            bundle.putString("headimgurl",
//                                                                    mPresenter.userInfoNewBean.getThumbnail());
//                                                            bundle.putString("nickname", mPresenter.userInfoNewBean.getNick());
//                                                            readyGo(WxLoginBindPhoneActivity.class, bundle);
//                                                        }
//                                                    }
//                                                }
//                                            } else {
//                                                JumpUtils.jumpToWebViewWithCookies(MainActivity.this,
//                                                        postActivityBean.get(finalI).getAct_link(),
//                                                        postActivityBean.get(finalI).getId(), ActivityWebViewActivity.class,
//                                                        postActivityBean.get(finalI).getTitle());
//                                            }
//                                        });
//                                    }
//                                }
//                            }
//                        });
//            }
            if (adapter==null) {
                adapter = new ActivityListAdapter(this);
                activityLv.setAdapter(adapter);
            }
            adapter.updateWithClear(postActivityBean);
            if (mask != postActivityBean.get(0).getId() && !TextUtils.isEmpty(
                    postActivityBean.get(0).getMask_link())) {
                MastFragment mastFragment = MastFragment.newInstance("mask");
                mastFragment.show(getFragmentManager(), "mask");
            }
        }
//        else {
//            post_activity_layout.setVisibility(View.GONE);
//        }
    }

    @Override
    public void clickGetCounpon(ResponseBody responseBody) {
        if (null != responseBody) {
            try {
                JUtils.Log("TodayListView", responseBody.string());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void downLoaAddressFile(AddressDownloadResultBean addressDownloadResultBean) {
        JUtils.Log(TAG, addressDownloadResultBean.toString());
        String downloadUrl = addressDownloadResultBean.getDownloadUrl();
        String hash = addressDownloadResultBean.getHash();
        if (!FileUtils.isAddressFileHashSame(getApplicationContext(), hash)) {
            OkHttpUtils.get()
                    .url(downloadUrl)
                    .build()
                    .execute(new FileCallBack(
                            Environment.getExternalStorageDirectory().getAbsolutePath() + "/xlmmaddress/",
                            "areas.json") {
                        @Override
                        public void inProgress(float progress, long total, int id) {
                            JUtils.Log(TAG, "                          " + (int) (100 * (progress / total)) + "");
                        }

                        @Override
                        public void onError(Call call, Exception e, int id) {
                            if (FileUtils.isFolderExist(
                                    Environment.getExternalStorageDirectory().getAbsolutePath() + "/xlmmaddress/" +
                                            "areas.json")) {
                                FileUtils.deleteFile(
                                        Environment.getExternalStorageDirectory().getAbsolutePath() + "/xlmmaddress/" +
                                                "areas.json");
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
        if (!FileUtils.isCategorySame(getApplicationContext(), sha1)) {
            OkHttpUtils.get()
                    .url(downloadUrl)
                    .build()
                    .execute(new FileCallBack(
                            Environment.getExternalStorageDirectory().getAbsolutePath() + "/xlmmcategory/",
                            "category.json") {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            if (FileUtils.isFolderExist(
                                    Environment.getExternalStorageDirectory().getAbsolutePath()
                                            + "/xlmmcategory/"
                                            + "category.json")) {
                                FileUtils.deleteFile(Environment.getExternalStorageDirectory().getAbsolutePath()
                                        + "/xlmmcategory/"
                                        + "category.json");
                            }
                        }

                        @Override
                        public void onResponse(File response, int id) {
                            JUtils.Log(TAG, response.getAbsolutePath());
                            FileUtils.saveCategoryFile(getApplicationContext(), sha1);
                            FileUtils.saveCategoryImg(Environment.getExternalStorageDirectory().getAbsolutePath()
                                    + "/xlmmcategory/" + "category.json");
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
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return list.get(position).getTitle();
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                finishAffinity();
            } else {
                super.onBackPressed();
            }
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
        rl_mmentry.setVisibility(View.INVISIBLE);
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
        //resumeData();
        mPresenter.getCartsNum();
        //    mPresenter.getUserInfoBean();

        JUtils.Log(TAG, "resume");
    }

    @Override
    public void iniCartsNum(CartsNumResultBean cartsNumResultBean) {
        if (cartsNumResultBean != null) {
            num = cartsNumResultBean.getResult();
            badge.setBadgeCount(num);
            if (mPresenter.calcLefttowTime(cartsNumResultBean.getLastCreated()) != 0) {
                image1.setVisibility(View.INVISIBLE);
                image2.setVisibility(View.VISIBLE);
            } else {
                image1.setVisibility(View.VISIBLE);
                image2.setVisibility(View.INVISIBLE);
                badge.setBadgeCount(0);
            }
        } else {
            image1.setVisibility(View.VISIBLE);
            image2.setVisibility(View.INVISIBLE);
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
        upadteFlag = false;
        aDefault.unregister(this);
        UserChangeEvent stickyEvent = aDefault.getStickyEvent(UserChangeEvent.class);
        if (stickyEvent != null) {
            aDefault.removeStickyEvent(stickyEvent);
        }
        super.onDestroy();
    }

    @Override
    public void checkVersion(int versionCode, String content, String downloadUrl,
                             boolean isAutoUpdate) {
        upadteFlag = true;
        new Thread(() -> {
            try {
                Thread.sleep(500 * 10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            runOnUiThread(() -> {
                VersionManager versionManager = new VersionManager() {

                    @Override
                    public int getServerVersion() {
                        return versionCode;
                    }

                    @Override
                    public String getUpdateContent() {
                        return content;
                    }

                    @Override
                    public boolean showMsg() {
                        return false;
                    }
                };
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
                    if (update && upadteFlag) {
                        versionManager.checkVersion(MainActivity.this);
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
}
