package com.jimei.xiaolumeimei.ui.activity.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseActivity;
import com.jimei.xiaolumeimei.entities.CartsNumResultBean;
import com.jimei.xiaolumeimei.entities.UserInfoBean;
import com.jimei.xiaolumeimei.model.CartsModel;
import com.jimei.xiaolumeimei.model.UserModel;
import com.jimei.xiaolumeimei.model.UserNewModel;
import com.jimei.xiaolumeimei.ui.activity.trade.AllOrdersActivity;
import com.jimei.xiaolumeimei.ui.activity.trade.AllRefundsActivity;
import com.jimei.xiaolumeimei.ui.activity.trade.CartActivity;
import com.jimei.xiaolumeimei.ui.activity.user.CouponActivity;
import com.jimei.xiaolumeimei.ui.activity.user.CustomProblemActivity;
import com.jimei.xiaolumeimei.ui.activity.user.InformationActivity;
import com.jimei.xiaolumeimei.ui.activity.user.LoginActivity;
import com.jimei.xiaolumeimei.ui.activity.user.MembershipPointActivity;
import com.jimei.xiaolumeimei.ui.activity.user.WalletActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MamaInfoActivity;
import com.jimei.xiaolumeimei.ui.fragment.v1.ChildFragment;
import com.jimei.xiaolumeimei.ui.fragment.v1.LadyFragment;
import com.jimei.xiaolumeimei.ui.fragment.v1.PreviousFragment;
import com.jimei.xiaolumeimei.ui.fragment.v1.TodayFragment;
import com.jimei.xiaolumeimei.utils.LoginUtils;
import com.jimei.xiaolumeimei.utils.StatusBarUtil;
import com.jimei.xiaolumeimei.utils.ViewUtils;
import com.jimei.xiaolumeimei.widget.badgelib.BadgeView;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import com.umeng.update.UmengUpdateAgent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import rx.Subscription;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity
    implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
  public static String TAG = "MainActivity";
  @Bind(R.id.tab_layout) TabLayout mTabLayout;
  @Bind(R.id.view_pager) ViewPager mViewPager;
  @Bind(R.id.tool_bar) Toolbar toolbar;
  //@Bind(R.id.fab) FloatingActionButton carts;
  @Bind(R.id.rv_cart) RelativeLayout carts;
  @Bind(R.id.img_mmentry) ImageView img_mmentry;

  DrawerLayout drawer;
  TextView tvNickname;

  ImageView imgUser;

  TextView tvPoint;
  TextView tvCoupon;
  TextView tvMoney;

  List<Fragment> fragments;
  List<String> titles;
  UserInfoBean userInfoBean = new UserInfoBean();
  @Bind(R.id.image_1) ImageView image1;
  @Bind(R.id.image_2) ImageView image2;
  private int num;
  private BadgeView badge;
  private double budgetCash;
  private TextView msg1;
  private TextView msg2;
  private TextView msg3;
  private ImageView loginFlag;

  @Override protected int provideContentViewId() {
    return R.layout.activity_main;
  }

  @Override protected void setListener() {
    carts.setOnClickListener(this);
    img_mmentry.setOnClickListener(this);
  }

  @Override protected void initData() {
    initFragment();

    initTitles();

    initTabLayout();

    new Thread(() -> {
      try {
        Thread.sleep(500 * 60);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      UmengUpdateAgent.update(MainActivity.this);
    }).start();

  }

  private void initTabLayout() {
    mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(0)));
    mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(1)));
    mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(2)));
    mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(3)));

    MainTabAdapter mAdapter =
        new MainTabAdapter(getSupportFragmentManager(), fragments, titles);
    mViewPager.setAdapter(mAdapter);
    mViewPager.setOffscreenPageLimit(3);
    mTabLayout.setupWithViewPager(mViewPager);
    //mTabLayout.setTabsFromPagerAdapter(mAdapter);
    mTabLayout.setTabMode(TabLayout.MODE_FIXED);
  }

  private void initTitles() {
    titles = new ArrayList<>();
    titles.add("今日上新");
    titles.add("昨日特卖");
    titles.add("萌娃专区");
    titles.add("时尚女装");
  }

  private void initFragment() {
    fragments = new ArrayList<>();
    fragments.add(TodayFragment.newInstance("今日上新"));
    fragments.add(PreviousFragment.newInstance("昨日特卖"));
    fragments.add(ChildFragment.newInstance("萌娃专区"));
    fragments.add(LadyFragment.newInstance("时尚女装"));
  }

  @Override protected void initView() {
    //toolbar.setTitle("小鹿美美");
    //setSupportActionBar(toolbar);

    drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    StatusBarUtil.setColorForDrawerLayout(this, drawer,
        getResources().getColor(R.color.colorAccent), 0);

    ActionBarDrawerToggle toggle =
        new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
            R.string.navigation_drawer_close) {
          @Override public void onDrawerClosed(View drawerView) {
            //getActionBar().setTitle(mTitle);
            invalidateOptionsMenu();
          }

          @Override public void onDrawerOpened(View drawerView) {
            //getActionBar().setTitle(mDrawerTitle);
            if (!(LoginUtils.checkLoginState(getApplicationContext()))) {
              if (tvNickname != null) {
                tvNickname.setText("点击登录");
              }
            } else {
              if ((tvNickname != null) && (userInfoBean != null)) {
                tvNickname.setText(userInfoBean.getNick());
              }

              if ((userInfoBean != null) && (!TextUtils.isEmpty(
                  userInfoBean.getThumbnail()))) {
                ViewUtils.loadImgToImgView(MainActivity.this, imgUser,
                    userInfoBean.getThumbnail());
              }

              //获得待支付和待收货数目
              android.support.design.widget.NavigationView navigationView =
                  (android.support.design.widget.NavigationView) drawer.findViewById(
                      R.id.nav_view);
              LinearLayout nav_tobepaid = (LinearLayout) navigationView.getMenu()
                  .findItem(R.id.nav_tobepaid)
                  .getActionView();
              msg1 = (TextView) nav_tobepaid.findViewById(R.id.msg);
              LinearLayout nav_tobereceived = (LinearLayout) navigationView.getMenu()
                  .findItem(R.id.nav_tobereceived)
                  .getActionView();
              msg2 = (TextView) nav_tobereceived.findViewById(R.id.msg);
              LinearLayout nav_refund = (LinearLayout) navigationView.getMenu()
                  .findItem(R.id.nav_returned)
                  .getActionView();
              msg3 = (TextView) nav_refund.findViewById(R.id.msg);

              if ((null != userInfoBean) && (userInfoBean.getWaitpayNum() > 0)) {
                msg1.setVisibility(View.VISIBLE);
                msg1.setText(Integer.toString(userInfoBean.getWaitpayNum()));
              } else {
                msg1.setVisibility(View.INVISIBLE);
              }

              Log.i(TAG, "" + userInfoBean.getWaitpayNum());

              if ((null != userInfoBean) && (userInfoBean.getWaitgoodsNum() > 0)) {
                msg2.setVisibility(View.VISIBLE);
                msg2.setText(Integer.toString(userInfoBean.getWaitgoodsNum()));
              } else {
                msg2.setVisibility(View.INVISIBLE);
              }

              if ((null != userInfoBean) && (userInfoBean.getRefundsNum() > 0)) {
                msg3.setVisibility(View.VISIBLE);
                msg3.setText(Integer.toString(userInfoBean.getRefundsNum()));
              } else {
                msg3.setVisibility(View.INVISIBLE);
              }
            }

            invalidateOptionsMenu();
          }
        };
    drawer.setDrawerListener(toggle);
    toggle.syncState();
    //toggle.setDrawerIndicatorEnabled(false);
    //toggle.setHomeAsUpIndicator(R.drawable.ic_deerhead);
    //toolbar.setNavigationIcon(R.drawable.ic_deerhead);

    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);

    View llayout = navigationView.getHeaderView(0);

    tvCoupon = (TextView) llayout.findViewById(R.id.tvDiscount);
    tvMoney = (TextView) llayout.findViewById(R.id.tvMoney);
    tvPoint = (TextView) llayout.findViewById(R.id.tvScore);
    imgUser = (ImageView) llayout.findViewById(R.id.imgUser);
    llayout.findViewById(R.id.ll_money).setOnClickListener(this);
    llayout.findViewById(R.id.ll_score).setOnClickListener(this);
    llayout.findViewById(R.id.ll_discount).setOnClickListener(this);
    llayout.findViewById(R.id.imgUser).setOnClickListener(this);

    loginFlag = (ImageView) llayout.findViewById(R.id.login_flag);

    tvNickname = (TextView) llayout.findViewById(R.id.tvNickname);
    if (!(LoginUtils.checkLoginState(getApplicationContext()))) {
      tvNickname.setText("点击登录");
    }

    badge = new BadgeView(this);
    badge.setTextSizeOff(7);
    badge.setBackground(4, Color.parseColor("#d3321b"));
    badge.setGravity(Gravity.END | Gravity.TOP);
    badge.setPadding(dip2Px(4), dip2Px(1), dip2Px(4), dip2Px(1));
    badge.setTargetView(image2);
    //badge.setBadgeGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
    //        StatusBarUtil.setColorForDrawerLayout(MainActivity.this,drawer, getResources()
    //                .getColor(R.color.colorAccent), 0);
  }

  @Override public boolean onNavigationItemSelected(MenuItem item) {
    Intent intent;
    Bundle bundle;

    int id = item.getItemId();

    if (!LoginUtils.checkLoginState(getApplicationContext())) {
            /*未登录进入登录界面*/

      intent = new Intent(MainActivity.this, LoginActivity.class);
      bundle = new Bundle();
      bundle.putString("login", "main");
      intent.putExtras(bundle);
      startActivity(intent);
      //startActivity(new Intent(MainActivity.this, LoginActivity.class));
    } else {
      if (id == R.id.nav_tobepaid) {
        intent = new Intent(MainActivity.this, AllOrdersActivity.class);
        bundle = new Bundle();
        bundle.putInt("fragment", 2);
        intent.putExtras(bundle);
        startActivity(intent);
      } else if (id == R.id.nav_tobereceived) {
        intent = new Intent(MainActivity.this, AllOrdersActivity.class);
        bundle = new Bundle();
        bundle.putInt("fragment", 3);
        intent.putExtras(bundle);
        startActivity(intent);
      } else if (id == R.id.nav_returned) {
        startActivity(new Intent(MainActivity.this, AllRefundsActivity.class));
      } else if (id == R.id.nav_orders) {
        intent = new Intent(MainActivity.this, AllOrdersActivity.class);
        bundle = new Bundle();
        bundle.putInt("fragment", 1);
        intent.putExtras(bundle);
        startActivity(intent);
      } else if (id == R.id.nav_problem) {
        intent = new Intent(new Intent(MainActivity.this, CustomProblemActivity.class));
        Bundle bundle1 = new Bundle();
        bundle1.putString("actlink", "http://m.xiaolumeimei.com/mall/#/faq");
        intent.putExtras(bundle1);
        startActivity(intent);
      } else if (id == R.id.nav_complain) {
        startActivity(new Intent(MainActivity.this, ComplainActivity.class));
        //Log.d(TAG, "start complain activity ");
      }
    }

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
    return true;
  }

  @Override public void onBackPressed() {
    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
  }

  @Override public void onClick(View v) {
    drawer.closeDrawers();
    String flag = "main";
    Intent intent = new Intent(MainActivity.this, MainActivity.class);
    switch (v.getId()) {
      case R.id.rv_cart:
        intent = new Intent(MainActivity.this, CartActivity.class);
        flag = "cart";
        break;
      case R.id.img_mmentry:
        JUtils.Log(TAG, "xiaolu mama entry");
        intent = new Intent(MainActivity.this, MamaInfoActivity.class);
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
        intent = new Intent(MainActivity.this, CouponActivity.class);
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

  private void login(String flag) {
    JUtils.Log(TAG, "need login");
    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
    Bundle bundle = new Bundle();
    bundle.putString("login", flag);
    intent.putExtras(bundle);
    startActivity(intent);
  }

  private void getUserInfo() {
    Subscription subscribe = UserNewModel.getInstance()
            .getProfile()
            .subscribeOn(Schedulers.io())
            .subscribe(new ServiceResponse<UserInfoBean>() {
              @Override
              public void onNext(UserInfoBean userNewBean) {
                if (userNewBean != null) {
                  userInfoBean = userNewBean;
                  if (LoginUtils.checkLoginState(getApplicationContext())) {
                    if ((userNewBean.getThumbnail() != null) && (!userNewBean.getThumbnail()
                            .isEmpty())) {
                      ViewUtils.loadImgToImgView(MainActivity.this, imgUser,
                              userNewBean.getThumbnail());
                    }
                    if (userInfoBean != null) {
                      if (userInfoBean.isHasUsablePassword() && userInfoBean.getMobile() != "") {
                        loginFlag.setVisibility(View.GONE);
                      } else {
                        loginFlag.setVisibility(View.VISIBLE);
                      }
                    }
                    int score = userNewBean.getScore();
                    if (null != userNewBean.getUserBudget()) {
                      budgetCash = userNewBean.getUserBudget().getBudgetCash();
                    }
                    if (tvPoint != null) {
                      tvPoint.setText(score + "");
                    }
                    if (tvMoney != null) {
                      tvMoney.setText((float) (Math.round(budgetCash * 100)) / 100 + "");
                    }

                    if (tvCoupon != null) {
                      tvCoupon.setText(userNewBean.getCouponNum() + "");
                    }

                    JUtils.Log(TAG, "mamaid " + userInfoBean.getXiaolumm().getId());
                    if ((userInfoBean.getXiaolumm() != null) && (userInfoBean.getXiaolumm()
                            .getId() != 0)) {
                      img_mmentry.setVisibility(View.VISIBLE);
                    } else {
                      img_mmentry.setVisibility(View.INVISIBLE);
                    }

                    if ((userInfoBean.getNick() != null) && (!userInfoBean.getNick()
                            .isEmpty())) {
                      tvNickname.setText(userInfoBean.getNick());
                    } else {
                      tvNickname.setText("小鹿妈妈");
                    }

                    if ((null != userInfoBean) && (userInfoBean.getWaitpayNum() > 0)) {
                      msg1.setVisibility(View.VISIBLE);
                      msg1.setText(Integer.toString(userInfoBean.getWaitpayNum()));
                    } else {
                      msg1.setVisibility(View.INVISIBLE);
                    }

                    Log.i(TAG, "" + userInfoBean.getWaitpayNum());

                    if ((null != userInfoBean) && (userInfoBean.getWaitgoodsNum() > 0)) {
                      msg2.setVisibility(View.VISIBLE);
                      msg2.setText(Integer.toString(userInfoBean.getWaitgoodsNum()));
                    } else {
                      msg2.setVisibility(View.INVISIBLE);
                    }

                    if ((null != userInfoBean) && (userInfoBean.getRefundsNum() > 0)) {
                      msg3.setVisibility(View.VISIBLE);
                      msg3.setText(Integer.toString(userInfoBean.getRefundsNum()));
                    } else {
                      msg3.setVisibility(View.INVISIBLE);
                    }
                  }
                }
              }
            });
    addSubscription(subscribe);
  }

  @Override protected void onResume() {
    super.onResume();

    swith_fragment();

    Subscription subscribe = CartsModel.getInstance()
        .show_carts_num()
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<CartsNumResultBean>() {
          @Override public void onNext(CartsNumResultBean cartsNumResultBean) {
            if (cartsNumResultBean != null && cartsNumResultBean.getResult() != 0) {
              num = cartsNumResultBean.getResult();
              badge.setBadgeCount(num);

              if (calcLefttowTime(cartsNumResultBean.getLastCreated()) != 0) {
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
        });
    addSubscription(subscribe);
    getUserInfo();
    JUtils.Log(TAG, "resume");

  }

  @Override protected void onStop() {
    JUtils.Log(TAG, "stop");
    super.onStop();
  }

  public UserInfoBean getUserInfoBean() {
    return userInfoBean;
  }

  //MIpush跳转
  public void swith_fragment() {
    int tabid = 0;
    if (getIntent().getExtras() != null) {
      tabid = getIntent().getExtras().getInt("fragment");
      JUtils.Log(TAG, "jump to fragment:" + tabid);
      if ((tabid >= 1) && (tabid <= 4)) {
        try {
          mTabLayout.setScrollPosition(tabid - 1, 0, true);
          mViewPager.setCurrentItem(tabid - 1);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
  }

  private long calcLefttowTime(long crtTime) {
    long left = 0;
    Date now = new Date();
    try {
      if (crtTime * 1000 - now.getTime() > 0) {
        left = crtTime * 1000 - now.getTime();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return left;
  }

  private int dip2Px(float dip) {
    return (int) (dip * getResources().getDisplayMetrics().density + 0.5f);
  }

  class MainTabAdapter extends FragmentPagerAdapter {
    private List<Fragment> listFragment;
    private List<String> listTitle;

    public MainTabAdapter(FragmentManager fm, List<Fragment> listFragment,
        List<String> listTitle) {
      super(fm);
      this.listFragment = listFragment;
      this.listTitle = listTitle;
    }

    @Override public Fragment getItem(int position) {
      return listFragment.get(position);
    }

    @Override public int getCount() {
      return listFragment.size();
    }

    @Override public CharSequence getPageTitle(int position) {
      return listTitle.get(position);
    }
  }
}
