package com.jimei.xiaolumeimei.ui.activity.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
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
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import cn.iwgang.countdownview.CountdownView;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseActivity;
import com.jimei.xiaolumeimei.base.BaseFragment;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.entities.CartsNumResultBean;
import com.jimei.xiaolumeimei.entities.PostActivityBean;
import com.jimei.xiaolumeimei.entities.PostBean;
import com.jimei.xiaolumeimei.entities.UserInfoBean;
import com.jimei.xiaolumeimei.model.ActivityModel;
import com.jimei.xiaolumeimei.model.CartsModel;
import com.jimei.xiaolumeimei.model.ProductModel;
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
import com.jimei.xiaolumeimei.ui.activity.user.WxLoginBindPhoneActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MamaInfoActivity;
import com.jimei.xiaolumeimei.ui.fragment.v1.ChildFragment;
import com.jimei.xiaolumeimei.ui.fragment.v1.view.MastFragment;
import com.jimei.xiaolumeimei.ui.fragment.v2.TodayV2Fragment;
import com.jimei.xiaolumeimei.ui.fragment.v2.YesterdayV2Fragment;
import com.jimei.xiaolumeimei.utils.JumpUtils;
import com.jimei.xiaolumeimei.utils.LoginUtils;
import com.jimei.xiaolumeimei.utils.StatusBarUtil;
import com.jimei.xiaolumeimei.utils.ViewUtils;
import com.jimei.xiaolumeimei.widget.badgelib.BadgeView;
import com.jimei.xiaolumeimei.widget.banner.Indicators.PagerIndicator;
import com.jimei.xiaolumeimei.widget.banner.SliderLayout;
import com.jimei.xiaolumeimei.widget.banner.SliderTypes.BaseSliderView;
import com.jimei.xiaolumeimei.widget.banner.SliderTypes.DefaultSliderView;
import com.jimei.xiaolumeimei.widget.scrolllayout.ScrollableLayout;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import com.squareup.okhttp.ResponseBody;
import com.umeng.update.UmengUpdateAgent;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity
    implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener,
    ViewPager.OnPageChangeListener {
  private static final String POST_URL = "?imageMogr2/format/jpg/quality/80";
  public static String TAG = "MainActivity";
  @Bind(R.id.tool_bar) Toolbar toolbar;
  @Bind(R.id.rv_cart) RelativeLayout carts;
  @Bind(R.id.img_mmentry) ImageView img_mmentry;
  List<String> postString = new ArrayList<>();
  List<String> appString = new ArrayList<>();
  List<PostBean.WemPostersEntity> wemPosters = new ArrayList<>();
  List<PostBean.WemPostersEntity> wemPostersEntities = new ArrayList<>();
  Map<String, String> map = new HashMap<>();
  List<ImageView> imageViewList = new ArrayList<>();
  DrawerLayout drawer;
  TextView tvNickname;
  ImageView imgUser;
  TextView tvPoint;
  TextView tvCoupon;
  TextView tvMoney;
  UserInfoBean userInfoBean = new UserInfoBean();
  @Bind(R.id.image_1) ImageView image1;
  @Bind(R.id.image_2) ImageView image2;
  @Bind(R.id.scrollableLayout) ScrollableLayout scrollableLayout;
  @Bind(R.id.imag_yesterday) ImageView imagYesterday;
  @Bind(R.id.imag_today) ImageView imagToday;
  @Bind(R.id.imag_tomorror) ImageView imagTomorror;
  private View view;
  //private View head;
  private LinearLayout post_activity_layout;
  private CountdownView countTime;
  private SliderLayout mSliderLayout;
  private PagerIndicator mPagerIndicator;
  private String cookies;
  private String domain;
  private SharedPreferences sharedPreferencesMask;
  private SharedPreferences sharedPreferences;
  private int mask;
  private List<BaseFragment> list = new ArrayList<>();
  private ViewPager vp;
  private int num;
  private BadgeView badge;
  private double budgetCash;
  private TextView msg1;
  private TextView msg2;
  private TextView msg3;
  private ImageView loginFlag;

  public static int dp2px(Context context, int dp) {
    float scale = context.getResources().getDisplayMetrics().density;
    return (int) (dp * scale + 0.5f);
  }

  @Override protected int provideContentViewId() {
    return R.layout.activity_main;
  }

  @Override protected void setListener() {
    carts.setOnClickListener(this);
    img_mmentry.setOnClickListener(this);
    imagYesterday.setOnClickListener(this);
    imagTomorror.setOnClickListener(this);
    imagToday.setOnClickListener(this);
  }

  @Override protected void initData() {
    new Thread(() -> {
      try {
        Thread.sleep(500 * 60);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      UmengUpdateAgent.update(MainActivity.this);
    }).start();
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
              NavigationView navigationView =
                  (NavigationView) drawer.findViewById(R.id.nav_view);
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
    initViewsForTab();
  }

  public void initViewsForTab() {
    view = LayoutInflater.from(this).inflate(R.layout.activity_main, null);
    post_activity_layout = (LinearLayout) findViewById(R.id.post_activity);
    countTime = (CountdownView) findViewById(R.id.countTime);
    mSliderLayout = (SliderLayout) findViewById(R.id.slider);
    mPagerIndicator = (PagerIndicator) findViewById(R.id.pi_header);
    vp = (ViewPager) findViewById(R.id.viewPager);
    sharedPreferencesMask = getSharedPreferences("maskActivity", 0);
    mask = sharedPreferencesMask.getInt("mask", 0);
    MyFragmentAdapter adapter = new MyFragmentAdapter(getSupportFragmentManager());
    list.add(YesterdayV2Fragment.newInstance("昨天"));
    list.add(TodayV2Fragment.newInstance("今天"));
    list.add(ChildFragment.newInstance("明天"));
    vp.setAdapter(adapter);
    vp.addOnPageChangeListener(this);
    scrollableLayout.getHelper().setCurrentScrollableContainer(list.get(0));

    //vp.setCurrentItem(0);

    initDataForTab();
  }

  private void initDataForTab() {
    initPost();
    Subscription subscription5 = Observable.timer(1, 1, TimeUnit.SECONDS)
        .onBackpressureDrop()
        .map(aLong -> calcLeftTime())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Action1<Long>() {
          @Override public void call(Long aLong) {
            if (aLong > 0) {
              countTime.updateShow(aLong);
            } else {
              countTime.setVisibility(View.INVISIBLE);
            }
          }
        }, Throwable::printStackTrace);
    addSubscription(subscription5);
  }

  private void initPost() {
    Subscription subscribe2 = ProductModel.getInstance()
        .getTodayPost()
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<PostBean>() {
          @Override public void onNext(PostBean postBean) {

            wemPosters.clear();
            wemPostersEntities.clear();
            postString.clear();
            appString.clear();

            try {

              wemPosters.addAll(postBean.getWemPosters());
              wemPostersEntities.addAll(postBean.getmChdPosters());
              for (PostBean.WemPostersEntity str : wemPosters) {
                postString.add(str.getPicLink() + POST_URL);
                appString.add(str.getAppLink());
              }
              for (PostBean.WemPostersEntity str : wemPostersEntities) {
                postString.add(str.getPicLink() + POST_URL);
                appString.add(str.getAppLink());
              }

              for (int i = 0; i < postString.size(); i++) {
                map.put(postString.get(i), appString.get(i));
              }

              if (mSliderLayout != null) {
                mSliderLayout.removeAllSliders();
              }

              for (String name : map.keySet()) {
                DefaultSliderView textSliderView = new DefaultSliderView(MainActivity
                    .this);
                // initialize a SliderLayout
                textSliderView.image(name)
                    .setScaleType(BaseSliderView.ScaleType.CenterInside);
                //add your extra information
                textSliderView.bundle(new Bundle());
                textSliderView.getBundle().putString("extra", map.get(name));
                mSliderLayout.addSlider(textSliderView);
                mSliderLayout.setDuration(3000);
                mSliderLayout.setCustomIndicator(mPagerIndicator);
                textSliderView.setOnSliderClickListener(
                    new BaseSliderView.OnSliderClickListener() {
                      @Override public void onSliderClick(BaseSliderView slider) {
                        Intent intent;
                        if (slider.getBundle() != null) {
                          String extra = slider.getBundle().getString("extra");
                          if (!TextUtils.isEmpty(extra)) {
                            JumpUtils.JumpInfo jump_info = JumpUtils.get_jump_info(extra);
                            if (extra.startsWith("http://")) {
                              intent = new Intent(MainActivity.this,
                                  ActivityWebViewActivity.class);
                              //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                              SharedPreferences sharedPreferences =
                                  getSharedPreferences("COOKIESxlmm",
                                      Context.MODE_PRIVATE);
                              String cookies = sharedPreferences.getString("Cookies", "");
                              Bundle bundle = new Bundle();
                              bundle.putString("cookies", cookies);
                              bundle.putString("actlink", extra);
                              intent.putExtras(bundle);
                              startActivity(intent);
                            } else {
                              if (jump_info.getType() == XlmmConst.JUMP_PROMOTE_TODAY) {
                                intent =
                                    new Intent(MainActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("fragment", 1);
                                startActivity(intent);
                                finish();
                              } else if (jump_info.getType()
                                  == XlmmConst.JUMP_PROMOTE_PREVIOUS) {
                                intent =
                                    new Intent(MainActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("fragment", 2);
                                startActivity(intent);
                                finish();
                              } else if (jump_info.getType()
                                  == XlmmConst.JUMP_PRODUCT_CHILDLIST) {
                                intent =
                                    new Intent(MainActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("fragment", 3);
                                startActivity(intent);
                                finish();
                              } else if (jump_info.getType()
                                  == XlmmConst.JUMP_PRODUCT_LADYLIST) {
                                intent =
                                    new Intent(MainActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("fragment", 4);
                                startActivity(intent);
                                finish();
                              } else {
                                JumpUtils.push_jump_proc(MainActivity.this, extra);
                              }
                            }
                          }
                        }
                      }
                    });
              }
            } catch (NullPointerException ex) {
              ex.printStackTrace();
            }
          }
        });
    addSubscription(subscribe2);
    Subscription subscribe6 = ActivityModel.getInstance()
        .getPostActivity()
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<List<PostActivityBean>>() {
          @Override public void onNext(List<PostActivityBean> postActivityBean) {
            try {
              if (null != postActivityBean) {
                post_activity_layout.setVisibility(View.VISIBLE);
                ImageView imageView;

                for (int i = 0; i < postActivityBean.size(); i++) {
                  imageView = new ImageView(MainActivity.this);
                  imageViewList.add(imageView);
                  post_activity_layout.addView(imageView);
                }
                for (int i = 0; i < postActivityBean.size(); i++) {
                  final int finalI = i;
                  OkHttpUtils.get()
                      .url(postActivityBean.get(i).getActImg())
                      .build()
                      .execute(new BitmapCallback() {
                        @Override public void onError(Call call, Exception e) {
                        }

                        @Override public void onResponse(Bitmap response) {
                          int maxHeight = dp2px(MainActivity.this, 300);
                          if (response != null) {
                            int height =
                                (int) ((float) view.getWidth() / response.getWidth()
                                    * response.getHeight());
                            if (height > maxHeight) height = maxHeight;
                            LinearLayout.LayoutParams layoutParams =
                                new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT, height);
                            layoutParams.setMargins(0, dp2px(MainActivity.this, 10), 0,
                                0);
                            imageViewList.get(finalI).setLayoutParams(layoutParams);
                            imageViewList.get(finalI).setImageBitmap(response);
                            if (postActivityBean.get(finalI)
                                .getActType()
                                .equals("webview")) {
                              imageViewList.get(finalI)
                                  .setOnClickListener(new View.OnClickListener() {
                                    @Override public void onClick(View v) {
                                      //syncCookie(getActivity(), postBean.getActivity().getActLink());
                                      if (postActivityBean.get(finalI)
                                          .isLoginRequired()) {
                                        if (LoginUtils.checkLoginState(MainActivity.this)
                                            && (null != getUserInfoBean()
                                            && (null
                                            != getUserInfoBean().getMobile())
                                            && !getUserInfoBean().getMobile()
                                            .isEmpty())) {
                                          Intent intent = new Intent(MainActivity.this,
                                              ActivityWebViewActivity.class);
                                          //sharedPreferences =
                                          //    getActivity().getSharedPreferences("COOKIESxlmm",
                                          //        Context.MODE_PRIVATE);
                                          //String cookies = sharedPreferences.getString("Cookies", "");
                                          //Bundle bundle = new Bundle();
                                          //bundle.putString("cookies", cookies);
                                          sharedPreferences =
                                              getSharedPreferences("xlmmCookiesAxiba",
                                                  Context.MODE_PRIVATE);
                                          cookies =
                                              sharedPreferences.getString("cookiesString",
                                                  "");
                                          domain =
                                              sharedPreferences.getString("cookiesDomain",
                                                  "");
                                          Bundle bundle = new Bundle();
                                          bundle.putString("cookies", cookies);
                                          bundle.putString("domain", domain);
                                          bundle.putString("Cookie",
                                              sharedPreferences.getString("Cookie", ""));
                                          bundle.putString("actlink",
                                              postActivityBean.get(finalI).getActLink());
                                          bundle.putInt("id",
                                              postActivityBean.get(finalI).getId());
                                          intent.putExtras(bundle);
                                          startActivity(intent);
                                        } else {
                                          if (!LoginUtils.checkLoginState(
                                              MainActivity.this)) {
                                            JUtils.Toast("登录并绑定手机号后才可参加活动");
                                            Intent intent = new Intent(MainActivity.this,
                                                LoginActivity.class);
                                            Bundle bundle = new Bundle();
                                            bundle.putString("login", "main");
                                            intent.putExtras(bundle);
                                            startActivity(intent);
                                          } else {
                                            JUtils.Toast("登录成功,前往绑定手机号后才可参加活动");
                                            Intent intent = new Intent(MainActivity.this,
                                                WxLoginBindPhoneActivity.class);
                                            if (null != getUserInfoBean()) {
                                              Bundle bundle = new Bundle();
                                              bundle.putString("headimgurl",
                                                  getUserInfoBean().getThumbnail());
                                              bundle.putString("nickname",
                                                  getUserInfoBean().getNick());
                                              intent.putExtras(bundle);
                                            }
                                            startActivity(intent);
                                          }
                                        }
                                      } else {
                                        Intent intent = new Intent(MainActivity.this,
                                            ActivityWebViewActivity.class);
                                        //sharedPreferences =
                                        //    getActivity().getSharedPreferences("COOKIESxlmm",
                                        //        Context.MODE_PRIVATE);
                                        //cookies = sharedPreferences.getString("Cookies", "");
                                        sharedPreferences =
                                            getSharedPreferences("xlmmCookiesAxiba",
                                                Context.MODE_PRIVATE);
                                        cookies =
                                            sharedPreferences.getString("cookiesString",
                                                "");
                                        domain =
                                            sharedPreferences.getString("cookiesDomain",
                                                "");
                                        Bundle bundle = new Bundle();
                                        bundle.putInt("id",
                                            postActivityBean.get(finalI).getId());
                                        bundle.putString("cookies", cookies);
                                        bundle.putString("domain", domain);
                                        bundle.putString("actlink",
                                            postActivityBean.get(finalI).getActLink());
                                        intent.putExtras(bundle);
                                        startActivity(intent);
                                      }
                                    }
                                  });
                            } else if (postActivityBean.get(finalI)
                                .getActType()
                                .equals("coupon")) {
                              imageViewList.get(finalI)
                                  .setOnClickListener(new View.OnClickListener() {
                                    @Override public void onClick(View v) {
                                      Subscription subscribe7 =
                                          ActivityModel.getInstance()
                                              .getUsercoupons(postActivityBean.get(finalI)
                                                  .getExtras()
                                                  .getTemplateId())
                                              .subscribeOn(Schedulers.io())
                                              .subscribe(
                                                  new ServiceResponse<ResponseBody>() {
                                                    @Override public void onNext(
                                                        ResponseBody responseBody) {
                                                      if (null != responseBody) {
                                                        try {
                                                          JUtils.Log("TodayListView",
                                                              responseBody.string());
                                                        } catch (IOException e) {
                                                          e.printStackTrace();
                                                        }
                                                      }
                                                    }
                                                  });
                                      addSubscription(subscribe7);
                                    }
                                  });
                            }
                          }
                        }
                      });
                }

                if (mask != postActivityBean.get(0).getId() && !TextUtils.isEmpty(
                    postActivityBean.get(0).getMaskLink())) {

                  MastFragment test = MastFragment.newInstance("mask");
                  test.show(getFragmentManager(), "mask");
                }
              } else {
                post_activity_layout.setVisibility(View.INVISIBLE);
              }
            } catch (NullPointerException e) {
              e.printStackTrace();
            }
          }
        });
    addSubscription(subscribe6);
  }

  private long calcLeftTime() {
    Date now = new Date();
    Date nextDay14PM = new Date();
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(nextDay14PM);
    calendar.add(Calendar.DATE, 1);
    calendar.set(Calendar.HOUR_OF_DAY, 14);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    nextDay14PM = calendar.getTime();
    long left;
    if (nextDay14PM.getTime() - now.getTime() > 0) {
      left = nextDay14PM.getTime() - now.getTime();
      return left;
    } else {
      return 0;
    }
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

      case R.id.imag_yesterday:
        vp.setCurrentItem(0);
        break;
      case R.id.imag_today:
        vp.setCurrentItem(1);
        break;
      case R.id.imag_tomorror:
        vp.setCurrentItem(2);
        break;
    }

    if (v.getId() != R.id.imag_today
        && v.getId() != R.id.imag_tomorror
        && v.getId() != R.id.imag_yesterday) {
      if (!(LoginUtils.checkLoginState(getApplicationContext()))) {
        login(flag);
      } else {
        startActivity(intent);
      }
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
          @Override public void onNext(UserInfoBean userNewBean) {
            if (userNewBean != null) {
              userInfoBean = userNewBean;
              if (LoginUtils.checkLoginState(getApplicationContext())) {
                if ((userNewBean.getThumbnail() != null) && (!userNewBean.getThumbnail()
                    .isEmpty())) {
                  ViewUtils.loadImgToImgView(MainActivity.this, imgUser,
                      userNewBean.getThumbnail());
                }
                if (userInfoBean != null) {
                  if (userInfoBean.isHasUsablePassword()
                      && userInfoBean.getMobile() != "") {
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

    //swith_fragment();

    Subscription subscribe = CartsModel.getInstance()
        .show_carts_num()
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<CartsNumResultBean>() {
          @Override public void onNext(CartsNumResultBean cartsNumResultBean) {
            if (cartsNumResultBean != null) {
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

  ////MIpush跳转
  //public void swith_fragment() {
  //  int tabid = 0;
  //  if (getIntent().getExtras() != null) {
  //    tabid = getIntent().getExtras().getInt("fragment");
  //    JUtils.Log(TAG, "jump to fragment:" + tabid);
  //    if ((tabid >= 1) && (tabid <= 4)) {
  //      try {
  //        mTabLayout.setScrollPosition(tabid - 1, 0, true);
  //        mViewPager.setCurrentItem(tabid - 1);
  //      } catch (Exception e) {
  //        e.printStackTrace();
  //      }
  //    }
  //  }
  //}

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

  @Override public void onPageScrolled(int position, float positionOffset,
      int positionOffsetPixels) {

  }

  @Override public void onPageSelected(int position) {
    scrollableLayout.getHelper().setCurrentScrollableContainer(list.get(position));
    switch (position) {
      case 0:
        //radioGroup.check(R.id.rb_yesterday);
        break;
      case 1:
        //radioGroup.check(R.id.rb_today);
        break;
      case 2:
        //radioGroup.check(R.id.rb_tomorror);
        break;
    }
  }

  @Override public void onPageScrollStateChanged(int state) {

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
}
