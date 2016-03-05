package com.jimei.xiaolumeimei.ui.activity.main;

import android.content.Intent;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import com.afollestad.materialdialogs.MaterialDialog;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseActivity;
import com.jimei.xiaolumeimei.entities.CartsNumResultBean;
import com.jimei.xiaolumeimei.entities.CouponBean;
import com.jimei.xiaolumeimei.entities.LogOutBean;
import com.jimei.xiaolumeimei.entities.UserInfoBean;
import com.jimei.xiaolumeimei.model.CartsModel;
import com.jimei.xiaolumeimei.model.UserModel;
import com.jimei.xiaolumeimei.model.UserNewModel;
import com.jimei.xiaolumeimei.ui.activity.trade.AllOrdersActivity;
import com.jimei.xiaolumeimei.ui.activity.trade.AllRefundsActivity;
import com.jimei.xiaolumeimei.ui.activity.trade.CartActivity;
import com.jimei.xiaolumeimei.ui.activity.trade.WaitPayOrdersActivity;
import com.jimei.xiaolumeimei.ui.activity.trade.WaitSendOrdersActivity;
import com.jimei.xiaolumeimei.ui.activity.user.CouponActivity;
import com.jimei.xiaolumeimei.ui.activity.user.LoginActivity;
import com.jimei.xiaolumeimei.ui.activity.user.MembershipPointActivity;
import com.jimei.xiaolumeimei.ui.activity.user.SettingActivity;
import com.jimei.xiaolumeimei.ui.activity.user.WalletActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MamaInfoActivity;
import com.jimei.xiaolumeimei.ui.fragment.ChildListFragment;
import com.jimei.xiaolumeimei.ui.fragment.LadyListFragment;
import com.jimei.xiaolumeimei.ui.fragment.PreviousFragment;
import com.jimei.xiaolumeimei.ui.fragment.TodayFragment;
import com.jimei.xiaolumeimei.utils.LoginUtils;
import com.jimei.xiaolumeimei.utils.ViewUtils;
import com.jimei.xiaolumeimei.widget.badgelib.BadgeView;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import com.umeng.update.UmengUpdateAgent;
import com.xiaomi.mipush.sdk.MiPushClient;
import java.util.ArrayList;
import java.util.List;
import rx.Subscriber;
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

  DrawerLayout drawer;
  TextView tvNickname;

  ImageView imgPoint;
  ImageView imgCoupon;
  ImageView imgUser;
  ImageView imaMoney;

  TextView tvPoint;
  TextView tvCoupon;
  TextView tvMoney;
  TextView tvMoney1;

  List<Fragment> fragments;
  List<String> titles;
  UserInfoBean userInfoBean = new UserInfoBean();
  private CartsModel cartsModel = new CartsModel();
  private int num;
  private BadgeView badge;
  private double budgetCash;
  private Subscription subscribe;

  @Override protected int provideContentViewId() {
    return R.layout.activity_main;
  }

  @Override protected void setListener() {
    carts.setOnClickListener(this);
  }

  @Override protected void initData() {
    initFragment();

    initTitles();

    initTabLayout();

    getUserInfo();

    UmengUpdateAgent.update(this);
  }

  private void initTabLayout() {
    mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(0)));
    mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(1)));
    mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(2)));
    mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(3)));

    MainTabAdapter mAdapter =
        new MainTabAdapter(getSupportFragmentManager(), fragments, titles);
    mViewPager.setAdapter(mAdapter);
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
    fragments.add(new TodayFragment());
    fragments.add(new PreviousFragment());
    fragments.add(new ChildListFragment());
    fragments.add(new LadyListFragment());
  }

  @Override protected void initView() {
    toolbar.setTitle("小鹿美美");
    setSupportActionBar(toolbar);
    toolbar.setNavigationIcon(R.drawable.ic_deerhead);

    drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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

              if ((userInfoBean != null) && (!userInfoBean.getThumbnail().isEmpty())) {
                ViewUtils.loadImgToImgView(MainActivity.this, imgUser,
                    userInfoBean.getThumbnail());
              }
            }
            invalidateOptionsMenu();
          }
        };
    drawer.setDrawerListener(toggle);
    toggle.syncState();

    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);

    View llayout = navigationView.getHeaderView(0);

    tvCoupon = (TextView) llayout.findViewById(R.id.tvDiscount);
    tvMoney = (TextView) llayout.findViewById(R.id.tvMoney);
    tvPoint = (TextView) llayout.findViewById(R.id.tvScore);

    imaMoney = (ImageView) llayout.findViewById(R.id.imgMoney);
    imaMoney.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        drawer.closeDrawers();
        if (LoginUtils.checkLoginState(getApplicationContext())) {
          Intent intent = new Intent(MainActivity.this, WalletActivity.class);
          Bundle bundle = new Bundle();
          bundle.putDouble("money", budgetCash);
          intent.putExtras(bundle);
          startActivity(intent);
        } else {
          Intent intent = new Intent(MainActivity.this, LoginActivity.class);
          Bundle bundle = new Bundle();
          bundle.putString("login", "money");
          intent.putExtras(bundle);
          startActivity(intent);
        }
      }
    });

    tvMoney1 = (TextView) llayout.findViewById(R.id.tvMoney1);
    tvMoney1.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        drawer.closeDrawers();
        if (LoginUtils.checkLoginState(getApplicationContext())) {
          Intent intent = new Intent(MainActivity.this, WalletActivity.class);
          Bundle bundle = new Bundle();
          bundle.putDouble("money", budgetCash);
          intent.putExtras(bundle);
          startActivity(intent);
        } else {
          Intent intent = new Intent(MainActivity.this, LoginActivity.class);
          Bundle bundle = new Bundle();
          bundle.putString("login", "money");
          intent.putExtras(bundle);
          startActivity(intent);
        }
      }
    });

    imgPoint = (ImageView) llayout.findViewById(R.id.imgPoint);
    imgPoint.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        drawer.closeDrawers();
        if (LoginUtils.checkLoginState(getApplicationContext())) {
          Intent intent = new Intent(MainActivity.this, MembershipPointActivity.class);
          startActivity(intent);
        } else {
          Intent intent = new Intent(MainActivity.this, LoginActivity.class);
          Bundle bundle = new Bundle();
          bundle.putString("login", "point");
          intent.putExtras(bundle);
          startActivity(intent);
        }
      }
    });

    imgCoupon = (ImageView) llayout.findViewById(R.id.imgCoupon);
    imgCoupon.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        drawer.closeDrawers();
        if (LoginUtils.checkLoginState(getApplicationContext())) {
          Intent intent = new Intent(MainActivity.this, CouponActivity.class);
          startActivity(intent);
        } else {
          Intent intent = new Intent(MainActivity.this, LoginActivity.class);
          Bundle bundle = new Bundle();
          bundle.putString("login", "coupon");
          intent.putExtras(bundle);
          startActivity(intent);
        }
      }
    });

    imgUser = (ImageView) llayout.findViewById(R.id.imgUser);
    imgUser.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {

        if (!(LoginUtils.checkLoginState(getApplicationContext()))) {
          Intent intent = new Intent(MainActivity.this, LoginActivity.class);
          Bundle bundle = new Bundle();
          bundle.putString("login", "main");
          intent.putExtras(bundle);
          startActivity(intent);
          drawer.closeDrawers();
        }
      }
    });

    tvNickname = (TextView) llayout.findViewById(R.id.tvNickname);
    if (!(LoginUtils.checkLoginState(getApplicationContext()))) {
      tvNickname.setText("点击登录");
    }

    badge = new BadgeView(this);
    badge.setTargetView(carts);
    //badge.setBadgeGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
  }

  @Override public boolean onNavigationItemSelected(MenuItem item) {

    int id = item.getItemId();

    if (!LoginUtils.checkLoginState(getApplicationContext())) {
            /*未登录进入登录界面*/

      Intent intent = new Intent(MainActivity.this, LoginActivity.class);
      Bundle bundle = new Bundle();
      bundle.putString("login", "main");
      intent.putExtras(bundle);
      startActivity(intent);
      //startActivity(new Intent(MainActivity.this, LoginActivity.class));
    } else {
      if (id == R.id.nav_tobepaid) {
        startActivity(new Intent(MainActivity.this, WaitPayOrdersActivity.class));
      } else if (id == R.id.nav_tobereceived) {
        startActivity(new Intent(MainActivity.this, WaitSendOrdersActivity.class));
      } else if (id == R.id.nav_returned) {
        startActivity(new Intent(MainActivity.this, AllRefundsActivity.class));
      } else if (id == R.id.nav_orders) {
        startActivity(new Intent(MainActivity.this, AllOrdersActivity.class));
      } else if (id == R.id.nav_setting) {
        startActivity(new Intent(MainActivity.this, SettingActivity.class));
      } else if (id == R.id.nav_complain) {
        startActivity(new Intent(MainActivity.this, ComplainActvity.class));
        //Log.d(TAG, "start complain activity ");
      } else if (id == R.id.nav_login) {
        new MaterialDialog.Builder(MainActivity.this).
            title("注销登录").
            content("您确定要退出登录吗？").
            positiveText("注销").
            negativeText("取消").
            callback(new MaterialDialog.ButtonCallback() {
              @Override public void onPositive(MaterialDialog dialog) {
                final String finalAccount = LoginUtils.getUserAccount(MainActivity.this);
                LoginUtils.delLoginInfo(getApplicationContext());
                UserModel.getInstance()
                    .customer_logout()
                    .subscribeOn(Schedulers.io())
                    .subscribe(new ServiceResponse<LogOutBean>() {
                      @Override public void onNext(LogOutBean responseBody) {
                        super.onNext(responseBody);

                        if (responseBody.getCode() == 0) {
                          JUtils.Toast("退出成功");
                          if (tvNickname != null) {
                            tvNickname.setText("点击登录");
                          }

                          if ((!finalAccount.isEmpty())) {
                            MiPushClient.unsetUserAccount(getApplicationContext(),
                                finalAccount, null);
                            JUtils.Log(TAG, "unset useraccount: " + finalAccount);
                          }
                        }
                      }
                    });

                dialog.dismiss();
              }

              @Override public void onNegative(MaterialDialog dialog) {
                dialog.dismiss();
              }
            }).show();
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
    if (LoginUtils.checkLoginState(getApplicationContext())) {
      startActivity(new Intent(MainActivity.this, CartActivity.class));
    } else {
      Intent intent = new Intent(MainActivity.this, LoginActivity.class);
      Bundle bundle = new Bundle();
      bundle.putString("login", "cart");
      intent.putExtras(bundle);
      startActivity(intent);
    }
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_settings:
        JUtils.Log(TAG, "xiaolu mama entry");
        if (!LoginUtils.checkLoginState(getApplicationContext())) {
            /*未登录进入登录界面*/
          JUtils.Log(TAG, "need login");
          Intent intent = new Intent(MainActivity.this, LoginActivity.class);
          Bundle bundle = new Bundle();
          bundle.putString("login", "main");
          intent.putExtras(bundle);
          startActivity(intent);
          //startActivity(new Intent(MainActivity.this, LoginActivity.class));
        } else {
          checkMamaInfo();
        }
        break;
      default:
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.mainframe_menu, menu);
    return super.onCreateOptionsMenu(menu);
  }

  private void checkMamaInfo() {
    JUtils.Log(TAG, "check mama userinfo");

    UserModel.getInstance()
        .getUserInfo()
        .subscribeOn(Schedulers.newThread())
        .subscribe(new ServiceResponse<UserInfoBean>() {
          @Override public void onNext(UserInfoBean user) {
            if ((user.getXiaolumm() != null) && (user.getXiaolumm().getId() != 0)) {
              JUtils.Log(TAG, "i am xiaolumama, id=" + user.getXiaolumm().getId());
              Intent intent = new Intent(MainActivity.this, MamaInfoActivity.class);
              startActivity(intent);
            } else {
              JUtils.Toast("您还不是小鹿妈妈，赶紧加入我们吧！");
            }
          }

          @Override public void onCompleted() {
            super.onCompleted();
          }

          @Override public void onError(Throwable e) {

            Log.e(TAG, "getUserInfo error: " + e.getLocalizedMessage());
            super.onError(e);
          }
        });
  }

  private void getUserInfo() {
    UserModel.getInstance()
        .getUserInfo()
        .subscribeOn(Schedulers.newThread())
        .subscribe(new ServiceResponse<UserInfoBean>() {
          @Override public void onNext(UserInfoBean user) {
            userInfoBean = user;
          }

          @Override public void onCompleted() {
            super.onCompleted();
          }

          @Override public void onError(Throwable e) {
            e.printStackTrace();
            Log.e(TAG, "getUserInfo error1 ");
            super.onError(e);
          }
        });
  }

  @Override protected void onResume() {
    super.onResume();

    swith_fragment();

    //显示购物车数量
    cartsModel.show_carts_num()
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<CartsNumResultBean>() {
          @Override public void onNext(CartsNumResultBean cartsNumResultBean) {
            super.onNext(cartsNumResultBean);
            if (cartsNumResultBean != null) {
              num = cartsNumResultBean.getResult();
              badge.setBadgeCount(num);
            }
          }
        });

    JUtils.Log(TAG, "resume");
    getUserInfo();

    subscribe = UserNewModel.getInstance()
        .getProfile()
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<UserInfoBean>() {
          @Override public void onNext(UserInfoBean userNewBean) {
            if (userNewBean != null) {
              userInfoBean = userNewBean;
              if ((LoginUtils.checkLoginState(getApplicationContext()))
                  && (!userNewBean.getThumbnail().isEmpty())) {
                ViewUtils.loadImgToImgView(MainActivity.this, imgUser,
                    userNewBean.getThumbnail());
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
            }
          }
        });

    if (LoginUtils.checkLoginState(getApplicationContext()) && (tvNickname != null)) {
      if ((userInfoBean != null)
          && (userInfoBean.getNick() != null)
          && (!userInfoBean.getNick().isEmpty())) {
        tvNickname.setText(userInfoBean.getNick());
      } else {
        tvNickname.setText("小鹿妈妈");
      }
    }

    UserModel.getInstance()
        .getUnusedCouponBean()
        .subscribeOn(Schedulers.newThread())
        .unsafeSubscribe(new Subscriber<CouponBean>() {
          @Override public void onCompleted() {

          }

          @Override public void onError(Throwable e) {

          }

          @Override public void onNext(CouponBean couponBean) {
            if ((couponBean != null) && (tvCoupon != null)) {
              tvCoupon.setText(couponBean.getCount() + "");
            } else {
              JUtils.Log(TAG, "err:" + (tvCoupon == null));
            }
          }
        });
  }

  @Override protected void onDestroy() {
    JUtils.Log(TAG, "destroy");
    super.onDestroy();
  }

  @Override protected void onPause() {
    JUtils.Log(TAG, "pause");
    super.onPause();
  }

  @Override protected void onRestart() {
    JUtils.Log(TAG, "restart");
    super.onRestart();
  }

  @Override protected void onResumeFragments() {
    JUtils.Log(TAG, "resume fragments");
    super.onResumeFragments();
  }

  @Override protected void onStart() {
    JUtils.Log(TAG, "start");
    super.onStart();
  }

  @Override protected void onStop() {
    JUtils.Log(TAG, "stop");
    super.onStop();

    if (subscribe != null && subscribe.isUnsubscribed()) {
      subscribe.unsubscribe();
    }
  }

  public UserInfoBean getUserInfoBean() {
    return userInfoBean;
  }

  public void swith_fragment(){
    int tabid = 0;
    if(getIntent().getExtras() != null) {
      tabid = getIntent().getExtras().getInt("fragment");
      JUtils.Log(TAG,"jump to fragment:"+tabid);
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
