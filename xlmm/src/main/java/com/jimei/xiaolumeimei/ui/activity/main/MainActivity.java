package com.jimei.xiaolumeimei.ui.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import butterknife.Bind;
import com.afollestad.materialdialogs.MaterialDialog;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseActivity;
import com.jimei.xiaolumeimei.entities.LogOutBean;
import com.jimei.xiaolumeimei.entities.UserInfoBean;
import com.jimei.xiaolumeimei.model.UserModel;
import com.jimei.xiaolumeimei.ui.activity.trade.AllOrdersActivity;
import com.jimei.xiaolumeimei.ui.activity.trade.AllRefundsActivity;
import com.jimei.xiaolumeimei.ui.activity.trade.CartActivity;
import com.jimei.xiaolumeimei.ui.activity.trade.WaitPayOrdersActivity;
import com.jimei.xiaolumeimei.ui.activity.trade.WaitSendOrdersActivity;
import com.jimei.xiaolumeimei.ui.activity.user.CouponActivity;
import com.jimei.xiaolumeimei.ui.activity.user.LoginActivity;
import com.jimei.xiaolumeimei.ui.activity.user.MembershipPointActivity;
import com.jimei.xiaolumeimei.ui.activity.user.SettingActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MamaInfoActivity;
import com.jimei.xiaolumeimei.ui.fragment.ChildListFragment;
import com.jimei.xiaolumeimei.ui.fragment.LadyListFragment;
import com.jimei.xiaolumeimei.ui.fragment.PreviousFragment;
import com.jimei.xiaolumeimei.ui.fragment.TodayFragment;
import com.jimei.xiaolumeimei.utils.LoginUtils;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import java.util.ArrayList;
import java.util.List;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity
    implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
  public  static String TAG = "MainActivity";
  @Bind(R.id.tab_layout) TabLayout mTabLayout;
  @Bind(R.id.view_pager) ViewPager mViewPager;
  @Bind(R.id.tool_bar) Toolbar toolbar;
  @Bind(R.id.fab) FloatingActionButton carts;
  ImageView imgPoint;
  ImageView imgCoupon;
  ImageView imgUser;
  List<Fragment> fragments;
  List<String> titles;
  UserModel model = new UserModel();

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

    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle =
        new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
            R.string.navigation_drawer_close);
    drawer.setDrawerListener(toggle);
    toggle.syncState();

    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);

    View llayout = navigationView.getHeaderView(0);
    imgPoint = (ImageView) llayout.findViewById(R.id.imgPoint);
    imgPoint.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (LoginUtils.checkLoginState(getApplicationContext())) {
          Intent intent = new Intent(MainActivity.this, MembershipPointActivity.class);
          startActivity(intent);
        }
        else{
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
        if (LoginUtils.checkLoginState(getApplicationContext())) {
          Intent intent = new Intent(MainActivity.this, CouponActivity.class);
          startActivity(intent);
        }
        else{
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
        }

      }
    });
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
                LoginUtils.delLoginInfo(getApplicationContext());
                model.customer_logout()
                    .subscribeOn(Schedulers.io())
                    .subscribe(new ServiceResponse<LogOutBean>() {
                      @Override public void onNext(LogOutBean responseBody) {
                        super.onNext(responseBody);

                        if (responseBody.getCode() == 0) {
                          JUtils.Toast("退出成功");
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

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()){
      case R.id.action_settings:
        JUtils.Log(TAG,"xiaolu mama entry");
        if (!LoginUtils.checkLoginState(getApplicationContext())) {
            /*未登录进入登录界面*/

          Intent intent = new Intent(MainActivity.this, LoginActivity.class);
          Bundle bundle = new Bundle();
          bundle.putString("login", "main");
          intent.putExtras(bundle);
          startActivity(intent);
          //startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
        else {
          getUserInfo();
        }
        break;
      default:
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.mainframe_menu,menu);
    return super.onCreateOptionsMenu(menu);
  }

  private void getUserInfo() {
    UserModel model = new UserModel();
    model.getUserInfo()
        .subscribeOn(Schedulers.newThread())
        .subscribe(new ServiceResponse<UserInfoBean>() {
          @Override
          public void onNext(UserInfoBean user) {
            if((user.getResults().get(0).getXiaolumm() != null)
                && (user.getResults().get(0).getXiaolumm().getId() != 0)){
              JUtils.Log(TAG, "i am xiaolumama, id="+ user.getResults().get(0).getXiaolumm().getId());
              Intent intent = new Intent(MainActivity.this, MamaInfoActivity.class);
              startActivity(intent);
            }
            else{
              JUtils.Toast("您还不是小鹿妈妈，赶紧加入我们吧！");
            }


          }

          @Override
          public void onCompleted() {
            super.onCompleted();
          }

          @Override
          public void onError(Throwable e) {

            Log.e(TAG, "error:, "   + e.toString());
            super.onError(e);
          }
        });

  }
}
