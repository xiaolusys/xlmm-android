package com.jimei.xiaolumeimei.ui.xlmmmain;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseFragment;
import com.jimei.xiaolumeimei.base.BasePresenterActivity;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.entities.CartsNumResultBean;
import com.jimei.xiaolumeimei.entities.IsGetcoupon;
import com.jimei.xiaolumeimei.entities.PortalBean;
import com.jimei.xiaolumeimei.entities.UserInfoBean;
import com.jimei.xiaolumeimei.event.UserInfoEmptyEvent;
import com.jimei.xiaolumeimei.ui.activity.main.ActivityWebViewActivity;
import com.jimei.xiaolumeimei.ui.activity.main.ComplainActivity;
import com.jimei.xiaolumeimei.ui.activity.product.ChildListActivity;
import com.jimei.xiaolumeimei.ui.activity.product.LadyListActivity;
import com.jimei.xiaolumeimei.ui.activity.trade.AllOrdersActivity;
import com.jimei.xiaolumeimei.ui.activity.trade.AllRefundsActivity;
import com.jimei.xiaolumeimei.ui.activity.trade.CartActivity;
import com.jimei.xiaolumeimei.ui.activity.user.AllCouponActivity;
import com.jimei.xiaolumeimei.ui.activity.user.CustomProblemActivity;
import com.jimei.xiaolumeimei.ui.activity.user.InformationActivity;
import com.jimei.xiaolumeimei.ui.activity.user.LoginActivity;
import com.jimei.xiaolumeimei.ui.activity.user.MembershipPointActivity;
import com.jimei.xiaolumeimei.ui.activity.user.WalletActivity;
import com.jimei.xiaolumeimei.ui.activity.user.WxLoginBindPhoneActivity;
import com.jimei.xiaolumeimei.ui.fragment.v1.view.MastFragment;
import com.jimei.xiaolumeimei.ui.fragment.v2.FirstFragment;
import com.jimei.xiaolumeimei.ui.fragment.v2.GetCouponFragment;
import com.jimei.xiaolumeimei.ui.fragment.v2.TodayV2Fragment;
import com.jimei.xiaolumeimei.ui.fragment.v2.TomorrowV2Fragment;
import com.jimei.xiaolumeimei.ui.fragment.v2.YesterdayV2Fragment;
import com.jimei.xiaolumeimei.ui.mminfo.MMInfoActivity;
import com.jimei.xiaolumeimei.utils.DisplayUtils;
import com.jimei.xiaolumeimei.utils.JumpUtils;
import com.jimei.xiaolumeimei.utils.LoginUtils;
import com.jimei.xiaolumeimei.utils.StatusBarUtil;
import com.jimei.xiaolumeimei.utils.ViewUtils;
import com.jimei.xiaolumeimei.widget.AutoToolbar;
import com.jimei.xiaolumeimei.widget.BrandView;
import com.jimei.xiaolumeimei.widget.badgelib.BadgeView;
import com.jimei.xiaolumeimei.widget.banner.SliderLayout;
import com.jimei.xiaolumeimei.widget.banner.SliderTypes.BaseSliderView;
import com.jimei.xiaolumeimei.widget.banner.SliderTypes.DefaultSliderView;
import com.jimei.xiaolumeimei.widget.scrolllayout.ScrollableLayout;
import com.jude.utils.JUtils;
import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import okhttp3.Call;
import okhttp3.ResponseBody;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import retrofit2.Response;

/**
 * Created by itxuye on 2016/7/4.
 */
public class MainActivity extends BasePresenterActivity<MainPresenter, MainModel>
    implements MainContract.View, View.OnClickListener, ViewPager.OnPageChangeListener,
    NavigationView.OnNavigationItemSelectedListener, ScrollableLayout.OnScrollListener,
    SwipeRefreshLayout.OnRefreshListener {

  private static final String POST_URL = "?imageMogr2/format/jpg/quality/80";
  public static String TAG = "MainActivity";
  Map<String, String> map = new HashMap<>();
  List<ImageView> imageViewList = new ArrayList<>();
  TextView tvNickname;
  ImageView imgUser;
  TextView tvPoint;
  TextView tvCoupon;
  TextView tvMoney;
  @Bind(R.id.tool_bar) AutoToolbar toolbar;
  @Bind(R.id.rv_cart) RelativeLayout carts;
  @Bind(R.id.rl_mmentry) RelativeLayout rl_mmentry;
  @Bind(R.id.image_1) ImageView image1;
  @Bind(R.id.image_2) ImageView image2;
  @Bind(R.id.scrollableLayout) ScrollableLayout scrollableLayout;
  @Bind(R.id.swipe_layout) SwipeRefreshLayout swipeRefreshLayout;
  @Bind(R.id.drawer_layout) DrawerLayout drawer;
  @Bind(R.id.brand) LinearLayout brand;
  @Bind(R.id.post_mainactivity) LinearLayout post_activity_layout;
  @Bind(R.id.text_yesterday) TextView textYesterday;
  @Bind(R.id.text_today) TextView textToday;
  @Bind(R.id.text_tomorror) TextView textTomorror;
  @Bind(R.id.child_img) ImageView childImage;
  @Bind(R.id.lady_img) ImageView ladyImage;
  @Bind(R.id.nav_view) NavigationView navigationView;
  @Bind(R.id.slider) SliderLayout mSliderLayout;
  @Bind(R.id.viewPager) ViewPager vp;
  @Bind(R.id.rv_top) RelativeLayout rvTop;
  List<PortalBean.PostersBean> posters = new ArrayList<>();
  SharedPreferences sharedPreferencesTime;
  private SharedPreferences sharedPreferencesMask;
  private int mask;
  private List<BaseFragment> list = new ArrayList<>();
  private int num;
  private BadgeView badge;
  private double budgetCash;
  private TextView msg1;
  private TextView msg2;
  private TextView msg3;
  private ImageView loginFlag;
  private View llayout;
  private String newTime;
  private int rvTopHeight;
  private EventBus aDefault;

  @Override protected void initData() {
    mPresenter.getUserInfoBean();
    initMainView(null);
    if (LoginUtils.isJumpToLogin(getApplicationContext())) {
      FirstFragment firstFragment = FirstFragment.newInstance("first");
      firstFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.Translucent_NoTitle);
      firstFragment.show(getFragmentManager(), "first");
    }

    if (LoginUtils.checkLoginState(getApplicationContext())) {
      mPresenter.isCouPon();
    }

    new Thread(() -> {
      try {
        Thread.sleep(500 * 60);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      UmengUpdateAgent.update(MainActivity.this);
    }).start();
  }


  @Override protected void setListener() {
    carts.setOnClickListener(this);
    rl_mmentry.setOnClickListener(this);
    textYesterday.setOnClickListener(this);
    textTomorror.setOnClickListener(this);
    textToday.setOnClickListener(this);
    childImage.setOnClickListener(this);
    ladyImage.setOnClickListener(this);
    vp.addOnPageChangeListener(this);
    navigationView.setNavigationItemSelectedListener(this);
    llayout.findViewById(R.id.ll_money).setOnClickListener(this);
    llayout.findViewById(R.id.ll_score).setOnClickListener(this);
    llayout.findViewById(R.id.ll_discount).setOnClickListener(this);
    llayout.findViewById(R.id.imgUser).setOnClickListener(this);
    scrollableLayout.setOnScrollListener(this);
    swipeRefreshLayout.setOnRefreshListener(this);
  }

  @Override protected void getBundleExtras(Bundle extras) {

  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.activity_main;
  }

  @Override protected void initViews() {
    aDefault = EventBus.getDefault();
    aDefault.register(this);
    findById();
    initSlide();
    showBadge();
    initViewsForTab();
  }

  @Override protected boolean toggleOverridePendingTransition() {
    return true;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return TransitionMode.BOTTOM;
  }

  @Override public void onClick(View v) {
    int currentNum = 0;
    drawer.closeDrawers();
    String flag = "main";
    Intent intent = new Intent(MainActivity.this, MainActivity.class);
    switch (v.getId()) {
      case R.id.rv_cart:
        intent = new Intent(MainActivity.this, CartActivity.class);
        flag = "cart";
        break;
      case R.id.rl_mmentry:
        JUtils.Log(TAG, "xiaolu mama entry");
        intent = new Intent(MainActivity.this, MMInfoActivity.class);
        break;
      case R.id.ll_money:
        intent = new Intent(MainActivity.this, WalletActivity.class);
        Bundle bundle = new Bundle();
        bundle.putDouble("money", budgetCash);
        intent.putExtras(bundle);
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
      case R.id.child_img:
        MobclickAgent.onEvent(MainActivity.this, "ChildID");
        readyGo(ChildListActivity.class);
        break;
      case R.id.lady_img:
        MobclickAgent.onEvent(MainActivity.this, "LadyID");
        readyGo(LadyListActivity.class);
        break;

      case R.id.text_yesterday:
        MobclickAgent.onEvent(this, "YesterdayID");
        currentNum = 0;
        setYesterday();
        break;
      case R.id.text_today:
        MobclickAgent.onEvent(this, "TodayID");
        currentNum = 1;
        setToday();
        break;
      case R.id.text_tomorror:
        MobclickAgent.onEvent(this, "TomorrorID");
        currentNum = 2;
        setTommrror();
        break;
    }
    vp.setCurrentItem(currentNum);
    scrollableLayout.getHelper().setCurrentScrollableContainer(list.get(currentNum));
    if (v.getId() != R.id.text_today
        && v.getId() != R.id.text_tomorror
        && v.getId() != R.id.text_yesterday
        && v.getId() != R.id.lady_img
        && v.getId() != R.id.child_img) {
      if (!(LoginUtils.checkLoginState(getApplicationContext()))) {
        login(flag);
      } else {
        startActivity(intent);
      }
    }
  }

  private void setTommrror() {
    textYesterday.setTextColor(Color.parseColor("#3C3C3C"));
    textToday.setTextColor(Color.parseColor("#3C3C3C"));
    textTomorror.setTextColor(Color.parseColor("#FAAA14"));
  }

  private void setToday() {
    textYesterday.setTextColor(Color.parseColor("#3C3C3C"));
    textToday.setTextColor(Color.parseColor("#FAAA14"));
    textTomorror.setTextColor(Color.parseColor("#3C3C3C"));
  }

  private void setYesterday() {
    textYesterday.setTextColor(Color.parseColor("#FAAA14"));
    textToday.setTextColor(Color.parseColor("#3C3C3C"));
    textTomorror.setTextColor(Color.parseColor("#3C3C3C"));
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

  @Override public void onPageSelected(int position) {
    try {
      scrollableLayout.getHelper().setCurrentScrollableContainer(list.get(position));
    } catch (Exception e) {
      e.printStackTrace();
    }
    switch (position) {
      case 0:
        setYesterday();
        break;
      case 1:
        setToday();
        break;
      case 2:
        setTommrror();
        break;
    }
  }

  @Override public void onPageScrollStateChanged(int state) {
    swipeRefreshLayout.setEnabled(true);
  }

  @Override public boolean onNavigationItemSelected(MenuItem item) {
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

  @Override public void onScroll(int transY, int maxY) {
    JUtils.Log(TAG, "onScroll");
    transY = -transY;
    if (rvTopHeight == (DisplayUtils.getScreenH(this) * 2 - 16)) {
      rvTopHeight = carts.getTop();
    }
    if (0 > rvTopHeight + transY) {
      rvTop.setVisibility(View.VISIBLE);
      rvTop.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          scrollableLayout.scrollTo(0, 0);
          switch (vp.getCurrentItem()) {
            case 0:
              ((YesterdayV2Fragment) list.get(0)).goToTop();
              break;
            case 1:
              ((TodayV2Fragment) list.get(1)).goToTop();
              break;
            case 2:
              ((TomorrowV2Fragment) list.get(2)).goToTop();
              break;
          }
        }
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

  @Override public void onRefresh() {
    initMainView(swipeRefreshLayout);
    try {
      switch (vp.getCurrentItem()) {
        case 0:
          ((YesterdayV2Fragment) list.get(0)).load(swipeRefreshLayout);
          break;
        case 1:
          ((TodayV2Fragment) list.get(1)).load(swipeRefreshLayout);
          break;
        case 2:
          ((TomorrowV2Fragment) list.get(2)).load(swipeRefreshLayout);
          break;
      }
      scrollableLayout.getHelper().setCurrentScrollableContainer(list.get(vp.getCurrentItem()));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override public void findById() {
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

  @Override public void initSlide() {
    ActionBarDrawerToggle toggle =
        new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
            R.string.navigation_drawer_close) {
          @Override public void onDrawerClosed(View drawerView) {
            invalidateOptionsMenu();
          }

          @Override public void onDrawerOpened(View drawerView) {
            //initSlideDraw(mPresenter.userInfoNewBean);
            //initUserNewView(mPresenter.userInfoNewBean);
            invalidateOptionsMenu();
          }
        };
    drawer.setDrawerListener(toggle);
    toggle.syncState();
    toggle.setHomeAsUpIndicator(R.drawable.icon_nav);
    toolbar.setNavigationIcon(R.drawable.icon_nav);
    if (!(LoginUtils.checkLoginState(getApplicationContext()))) {
      tvNickname.setText("点击登录");
    }
  }

  @Override public void showBadge() {
    badge = new BadgeView(this);
    badge.setTextSizeOff(7);
    badge.setBackground(4, Color.parseColor("#d3321b"));
    badge.setGravity(Gravity.END | Gravity.TOP);
    badge.setPadding(dip2Px(4), dip2Px(1), dip2Px(4), dip2Px(1));
    badge.setTargetView(image2);
  }

  @Override public void initViewsForTab() {
    sharedPreferencesMask = getSharedPreferences("maskActivity", 0);
    sharedPreferencesTime = getSharedPreferences("resumeTime", 0);
    mask = sharedPreferencesMask.getInt("mask", 0);
    MyFragmentAdapter adapter = new MyFragmentAdapter(getSupportFragmentManager());
    list.add(YesterdayV2Fragment.newInstance("昨天"));
    TodayV2Fragment.newInstance("今天");
    list.add(TodayV2Fragment.newInstance("今天"));
    list.add(TomorrowV2Fragment.newInstance("明天"));
    vp.setAdapter(adapter);
    vp.setOffscreenPageLimit(2);
    vp.setCurrentItem(1);
    setToday();
    scrollableLayout.getHelper().setCurrentScrollableContainer(list.get(1));
  }

  @Override public void initUserView(UserInfoBean userNewBean) {
    initUserNewView(userNewBean);
  }

  private void initUserNewView(UserInfoBean userNewBean) {
    JUtils.Log(TAG, "userNewBean" + userNewBean.toString());
    if (!TextUtils.isEmpty(userNewBean.getThumbnail())) {
      ViewUtils.loadImgToImgView(MainActivity.this, imgUser, userNewBean.getThumbnail());
    }
    if (userNewBean.isHasUsablePassword() && userNewBean.getMobile() != "") {
      loginFlag.setVisibility(View.GONE);
    } else {
      loginFlag.setVisibility(View.VISIBLE);
    }

    if (null != userNewBean.getUserBudget()) {
      budgetCash = userNewBean.getUserBudget().getBudgetCash();
    }
    //JUtils.Log(TAG, "mamaid " + userNewBean.getXiaolumm().getId());
    if ((userNewBean.getXiaolumm() != null) && (userNewBean.getXiaolumm().getId() != 0)) {
      rl_mmentry.setVisibility(View.VISIBLE);
    } else {
      rl_mmentry.setVisibility(View.INVISIBLE);
    }
  }

  public int dip2Px(float dip) {
    return (int) (dip * getResources().getDisplayMetrics().density + 0.5f);
  }

  @Override public void initDrawer(UserInfoBean userInfoBean) {
    initSlideDraw(userInfoBean);
  }

  private void initSlideDraw(UserInfoBean userInfoBean) {
    JUtils.Log(TAG, "侧滑栏初始化");
    if (!(LoginUtils.checkLoginState(getApplicationContext()))) {
      if (tvNickname != null) {
        tvNickname.setText("点击登录");
      }
    } else {
      if (tvNickname != null) {
        tvNickname.setText(userInfoBean.getNick());
      }
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
    }
  }

  @Override public void initShowCoiuponWindow(Response<IsGetcoupon> isGetcouponResponse) {
    if (isGetcouponResponse.isSuccessful()) {
      if (isGetcouponResponse.body().getIsPicked() == 0) {
        GetCouponFragment firstFragment = GetCouponFragment.newInstance("getCoupon");
        firstFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.Translucent_NoTitle);
        firstFragment.show(getFragmentManager(), "getCoupon");
      }
    }
  }

  @Override public void initMainView(SwipeRefreshLayout swipeRefreshLayout)
      throws NullPointerException {
    mPresenter.getPortalBean(swipeRefreshLayout);
  }

  @Override public void initSliderLayout(PortalBean postBean) throws NullPointerException {
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
        @Override public void onSliderClick(BaseSliderView slider) {
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
                  readyGo(ChildListActivity.class);
                } else if (jump_info.getType() == XlmmConst.JUMP_PRODUCT_LADYLIST) {
                  readyGo(LadyListActivity.class);
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

  @Override public void initCategory(PortalBean postBean) throws NullPointerException {
    JUtils.Log(TAG, "refreshCategory");
    if (postBean.getCategorys() != null) {
      ladyImage.setImageResource(0);
      childImage.setImageResource(0);
      List<PortalBean.CategorysBean> categorys = postBean.getCategorys();
      ViewUtils.loadImageWithOkhttp(categorys.get(1).getCat_img(), MainActivity.this, ladyImage);
      ViewUtils.loadImageWithOkhttp(categorys.get(0).getCat_img(), MainActivity.this, childImage);
    }
  }

  @Override public void initBrand(PortalBean postBean) throws NullPointerException {
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
          brandViews.get(i).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {

              if (!TextUtils.isEmpty(brandPromotionEntities.get(finalI1).getActApplink())) {
                JumpUtils.push_jump_proc(mContext,
                    brandPromotionEntities.get(finalI1).getActApplink());
              }
            }
          });
        }
      }
    } else {
      brand.setVisibility(View.GONE);
    }
  }

  @Override public void initPost(PortalBean postBean) throws NullPointerException {
    JUtils.Log(TAG, "refreshPost");
    if (post_activity_layout != null) {
      post_activity_layout.removeAllViews();
    }
    imageViewList.clear();
    if (null != postBean.getActivitys() && postBean.getActivitys().size() > 0) {
      post_activity_layout.setVisibility(View.VISIBLE);

      ImageView imageView;

      List<PortalBean.ActivitysBean> postActivityBean = postBean.getActivitys();
      for (int i = 0; i < postActivityBean.size(); i++) {
        imageView = new ImageView(MainActivity.this);
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        imageViewList.add(imageView);
        post_activity_layout.addView(imageView);
      }
      for (int i = 0; i < postActivityBean.size(); i++) {
        final int finalI = i;
        OkHttpUtils.get()
            .url(postActivityBean.get(i).getAct_img())
            .build()
            .execute(new BitmapCallback() {
              @Override public void onError(Call call, Exception e) {
              }

              @Override public void onResponse(Bitmap response) {
                if (response != null) {
                  imageViewList.get(finalI).setAdjustViewBounds(true);
                  int screenWidth = DisplayUtils.getScreenW(MainActivity.this);
                  LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(screenWidth,
                      LinearLayout.LayoutParams.WRAP_CONTENT);
                  imageViewList.get(finalI).setLayoutParams(lp);
                  imageViewList.get(finalI).setMaxWidth(screenWidth);
                  imageViewList.get(finalI).setMaxHeight(screenWidth * 5);
                  imageViewList.get(finalI).setImageBitmap(response);
                   if (postActivityBean.get(finalI).getAct_type().equals("coupon")) {
                    imageViewList.get(finalI).setOnClickListener(new View.OnClickListener() {
                      @Override public void onClick(View v) {
                        mPresenter.getUsercoupons(
                            postActivityBean.get(finalI).getExtras().getTemplateId());
                      }
                    });
                  }else {
                     imageViewList.get(finalI).setOnClickListener(new View.OnClickListener() {
                       @Override public void onClick(View v) {
                         MobclickAgent.onEvent(MainActivity.this, "ActivityID");
                         if (postActivityBean.get(finalI).isLogin_required()) {
                           if (LoginUtils.checkLoginState(MainActivity.this) && (null
                                   != mPresenter.userInfoNewBean
                                   && (null
                                   != mPresenter.userInfoNewBean.getMobile())
                                   && !mPresenter.userInfoNewBean.getMobile().isEmpty())) {
                             JumpUtils.jumpToWebViewWithCookies(MainActivity.this,
                                     postActivityBean.get(finalI).getAct_link(),
                                     postActivityBean.get(finalI).getId(), ActivityWebViewActivity.class,
                                     postActivityBean.get(finalI).getTitle());
                           } else {
                             if (!LoginUtils.checkLoginState(MainActivity.this)) {
                               JUtils.Toast("登录并绑定手机号后才可参加活动");
                               Bundle bundle = new Bundle();
                               bundle.putString("login", "goactivity");
                               bundle.putString("actlink",
                                       postActivityBean.get(finalI).getAct_link());
                               bundle.putInt("id", postActivityBean.get(finalI).getId());
                               bundle.putString("title", postActivityBean.get(finalI).getTitle());

                               readyGo(LoginActivity.class, bundle);
                             } else {
                               JUtils.Toast("登录成功,前往绑定手机号后才可参加活动");
                               if (null != mPresenter.userInfoNewBean) {
                                 Bundle bundle = new Bundle();
                                 bundle.putString("headimgurl",
                                         mPresenter.userInfoNewBean.getThumbnail());
                                 bundle.putString("nickname", mPresenter.userInfoNewBean.getNick());
                                 readyGo(WxLoginBindPhoneActivity.class, bundle);
                               }
                             }
                           }
                         } else {
                           JumpUtils.jumpToWebViewWithCookies(MainActivity.this,
                                   postActivityBean.get(finalI).getAct_link(),
                                   postActivityBean.get(finalI).getId(), ActivityWebViewActivity.class,
                                   postActivityBean.get(finalI).getTitle());
                         }
                       }
                     });
                   }
                }
              }
            });
      }

      if (mask != postActivityBean.get(0).getId() && !TextUtils.isEmpty(
          postActivityBean.get(0).getMask_link())) {

        MastFragment test = MastFragment.newInstance("mask");
        test.show(getFragmentManager(), "mask");
      }
    } else {
      post_activity_layout.setVisibility(View.GONE);
    }
  }

  @Override public void clickGetCounpon(ResponseBody responseBody) {
    if (null != responseBody) {
      try {
        JUtils.Log("TodayListView", responseBody.string());
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  private class MyFragmentAdapter extends FragmentPagerAdapter {

    public MyFragmentAdapter(FragmentManager fm) {
      super(fm);
    }

    @Override public Fragment getItem(int position) {
      return getCount() > position ? list.get(position) : null;
    }

    @Override public int getCount() {
      return list == null ? 0 : list.size();
    }
  }

  @Override public void onBackPressed() {
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

  @Subscribe public void initLoginInfo(UserInfoEmptyEvent event) {
    //UserNewModel.getInstance()
    //    .getProfile()
    //    .subscribeOn(Schedulers.io())
    //    .subscribe(new ServiceResponse<UserInfoBean>() {
    //      @Override public void onCompleted() {
    //        super.onCompleted();
    //      }
    //
    //      @Override public void onNext(UserInfoBean userInfoBean) {
    //        if (null != userInfoBean) {
    //          mPresenter.userInfoNewBean = userInfoBean;
    //          EventBus.getDefault().postSticky(new UserEvent(userInfoBean));
    //        }
    //      }
    //    });

    Intent intent = new Intent(MainActivity.this, MainActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(intent);
    finish();
  }
  //
  //@Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
  //public void initLoginInfoView(UserEvent event) {
  //  initUserNewView(event.userInfoBean);
  //  initSlideDraw(event.userInfoBean);
  //}

  @Override protected void onPause() {
    super.onPause();
    MobclickAgent.onPageEnd(this.getClass().getSimpleName());
    MobclickAgent.onPause(this);
  }

  @Override protected void onResume() {
    super.onResume();
    MobclickAgent.onPageStart(this.getClass().getSimpleName());
    MobclickAgent.onResume(this);
    //resumeData();

    mPresenter.getCartsNum();
    mPresenter.getUserInfoBean();

    JUtils.Log(TAG, "resume");
  }

  @Override public void iniCartsNum(CartsNumResultBean cartsNumResultBean) {
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

  @Override protected void onDestroy() {
    super.onDestroy();
    aDefault.unregister(this);
  }
}
