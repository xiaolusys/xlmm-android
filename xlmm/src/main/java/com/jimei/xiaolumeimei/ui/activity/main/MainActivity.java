package com.jimei.xiaolumeimei.ui.activity.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
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

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.BrandlistAdapter;
import com.jimei.xiaolumeimei.base.BaseActivity;
import com.jimei.xiaolumeimei.base.BaseFragment;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.entities.BrandListBean;
import com.jimei.xiaolumeimei.entities.CartsNumResultBean;
import com.jimei.xiaolumeimei.entities.PortalBean;
import com.jimei.xiaolumeimei.entities.PostBean;
import com.jimei.xiaolumeimei.entities.UserInfoBean;
import com.jimei.xiaolumeimei.model.ActivityModel;
import com.jimei.xiaolumeimei.model.CartsModel;
import com.jimei.xiaolumeimei.model.ProductModel;
import com.jimei.xiaolumeimei.model.UserNewModel;
import com.jimei.xiaolumeimei.ui.activity.product.BrandListActivity;
import com.jimei.xiaolumeimei.ui.activity.product.ChildListActivity;
import com.jimei.xiaolumeimei.ui.activity.product.LadyListActivity;
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
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.BoutiqueWebviewActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MamaInfoActivity;
import com.jimei.xiaolumeimei.ui.fragment.v1.view.MastFragment;
import com.jimei.xiaolumeimei.ui.fragment.v2.TodayV2Fragment;
import com.jimei.xiaolumeimei.ui.fragment.v2.TomorrowV2Fragment;
import com.jimei.xiaolumeimei.ui.fragment.v2.YesterdayV2Fragment;
import com.jimei.xiaolumeimei.utils.DisplayUtils;
import com.jimei.xiaolumeimei.utils.JumpUtils;
import com.jimei.xiaolumeimei.utils.LoginUtils;
import com.jimei.xiaolumeimei.utils.StatusBarUtil;
import com.jimei.xiaolumeimei.utils.ViewUtils;
import com.jimei.xiaolumeimei.widget.BrandView;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import okhttp3.Call;
import rx.Subscription;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener,
        ViewPager.OnPageChangeListener, ScrollableLayout.OnScrollListener/*, RadioGroup.OnCheckedChangeListener*/ {
    private static final String POST_URL = "?imageMogr2/format/jpg/quality/80";
    public static String TAG = "MainActivity";
    @Bind(R.id.tool_bar)
    Toolbar toolbar;
    @Bind(R.id.rv_cart)
    RelativeLayout carts;
    @Bind(R.id.img_mmentry)
    ImageView img_mmentry;
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
    @Bind(R.id.image_1)
    ImageView image1;
    @Bind(R.id.image_2)
    ImageView image2;
    @Bind(R.id.scrollableLayout)
    ScrollableLayout scrollableLayout;
    @Bind(R.id.swipe_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Bind(R.id.brand)
    LinearLayout brand;
    @Bind(R.id.post_mainactivity)
    LinearLayout post_activity_layout;
    @Bind(R.id.imag_yesterday)
    ImageView imagYesterday;
    @Bind(R.id.imag_today)
    ImageView imagToday;
    @Bind(R.id.imag_tomorror)
    ImageView imagTomorror;
    @Bind(R.id.child_img)
    ImageView childImage;
    @Bind(R.id.lady_img)
    ImageView ladyImage;

    //  private CountdownView countTime;
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
    private View view;

    public static int dp2px(Context context, int dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void setListener() {
        carts.setOnClickListener(this);
        img_mmentry.setOnClickListener(this);
        imagYesterday.setOnClickListener(this);
        imagTomorror.setOnClickListener(this);
        imagToday.setOnClickListener(this);
        childImage.setOnClickListener(this);
        ladyImage.setOnClickListener(this);
        //radioGroup.setOnCheckedChangeListener(this);
        scrollableLayout.setOnScrollListener(this);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initPost(swipeRefreshLayout);
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
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(10000);
                        if (swipeRefreshLayout != null) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (swipeRefreshLayout.isRefreshing()) {
                                        JUtils.Toast("网络状态异常,请重新加载~~ ");
                                    }
                                    swipeRefreshLayout.setRefreshing(false);
                                }
                            });
                        }
                    }
                }).start();
            }
        });
    }

    @Override
    protected void initData() {
        new Thread(() -> {
            try {
                Thread.sleep(500 * 60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            UmengUpdateAgent.update(MainActivity.this);
        }).start();
    }

    @Override
    protected void initView() {
        //toolbar.setTitle("小鹿美美");
        //setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        StatusBarUtil.setColorForDrawerLayout(this, drawer,
                getResources().getColor(R.color.colorAccent), 0);

        ActionBarDrawerToggle toggle =
                new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
                        R.string.navigation_drawer_close) {
                    @Override
                    public void onDrawerClosed(View drawerView) {
                        //getActionBar().setTitle(mTitle);
                        invalidateOptionsMenu();
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
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
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        initViewsForTab();


    }

    public void initViewsForTab() {
        view = LayoutInflater.from(this).inflate(R.layout.post_layout, null);
//    post_activity_layout = (LinearLayout) findViewById(R.id.post_activity);
        //    countTime = (CountdownView) findViewById(R.id.countTime);
        mSliderLayout = (SliderLayout) findViewById(R.id.slider);
        mPagerIndicator = (PagerIndicator) findViewById(R.id.pi_header);
        vp = (ViewPager) findViewById(R.id.viewPager);
        sharedPreferencesMask = getSharedPreferences("maskActivity", 0);
        mask = sharedPreferencesMask.getInt("mask", 0);
        MyFragmentAdapter adapter = new MyFragmentAdapter(getSupportFragmentManager());
        list.add(YesterdayV2Fragment.newInstance("昨天"));
        list.add(TodayV2Fragment.newInstance("今天"));
        list.add(TomorrowV2Fragment.newInstance("明天"));
        vp.setAdapter(adapter);
        vp.setOffscreenPageLimit(2);

        vp.setCurrentItem(1);
        imagYesterday.setImageDrawable(
                getResources().getDrawable(R.drawable.yesterday_nochoose));
        imagToday.setImageDrawable(getResources().getDrawable(R.drawable.today_choose));
        imagTomorror.setImageDrawable(
                getResources().getDrawable(R.drawable.tomorror_nochoose));
        vp.addOnPageChangeListener(this);
        scrollableLayout.getHelper().setCurrentScrollableContainer(list.get(1));

        initDataForTab();
    }

    private void initDataForTab() {
        initPost(null);
        //    Subscription subscription5 = Observable.timer(1, 1, TimeUnit.SECONDS)
        //        .onBackpressureDrop()
        //        .map(aLong -> calcLeftTime())
        //        .observeOn(AndroidSchedulers.mainThread())
        //        .subscribe(new Action1<Long>() {
        //          @Override public void call(Long aLong) {
        //            if (aLong > 0) {
        //              countTime.updateShow(aLong);
        //            } else {
        //              countTime.setVisibility(View.INVISIBLE);
        //            }
        //          }
        //        }, Throwable::printStackTrace);
        //    addSubscription(subscription5);
    }

    private void initPost(SwipeRefreshLayout swipeRefreshLayout) {
        Subscription subscribe2 = ProductModel.getInstance()
                .getPortalBean()
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<PortalBean>() {
                    @Override
                    public void onNext(PortalBean postBean) {

                        wemPosters.clear();
                        wemPostersEntities.clear();
                        postString.clear();
                        appString.clear();

                        try {

                            List<PortalBean.PostersBean> posters = postBean.getPosters();

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
                                textSliderView.image(name + POST_URL)
                                        .setScaleType(BaseSliderView.ScaleType.CenterInside);
                                //add your extra information
                                textSliderView.bundle(new Bundle());
                                textSliderView.getBundle().putString("extra", map.get(name));
                                mSliderLayout.addSlider(textSliderView);
                                mSliderLayout.setDuration(3000);
//                                mSliderLayout.setCustomIndicator(mPagerIndicator);
                                mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Left_Bottom);
                                textSliderView.setOnSliderClickListener(
                                        new BaseSliderView.OnSliderClickListener() {
                                            @Override
                                            public void onSliderClick(BaseSliderView slider) {
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
//                                                            if (jump_info.getType() == XlmmConst.JUMP_PROMOTE_TODAY) {
//                                                                intent =
//                                                                        new Intent(MainActivity.this, MainActivity.class);
//                                                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                                                intent.putExtra("fragment", 1);
//                                                                startActivity(intent);
//                                                                finish();
//                                                            } else if (jump_info.getType()
//                                                                    == XlmmConst.JUMP_PROMOTE_PREVIOUS) {
//                                                                intent =
//                                                                        new Intent(MainActivity.this, MainActivity.class);
//                                                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                                                intent.putExtra("fragment", 2);
//                                                                startActivity(intent);
//                                                                finish();
//                                                            } else

                                                            if (jump_info.getType()
                                                                    == XlmmConst.JUMP_PRODUCT_CHILDLIST) {
                                                                intent =
                                                                        new Intent(MainActivity.this, ChildListActivity.class);
//                                                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                                                intent.putExtra("fragment", 3);
                                                                startActivity(intent);
//                                                                finish();
                                                            } else if (jump_info.getType()
                                                                    == XlmmConst.JUMP_PRODUCT_LADYLIST) {
                                                                intent =
                                                                        new Intent(MainActivity.this, LadyListActivity.class);
//                                                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                                                intent.putExtra("fragment", 4);
                                                                startActivity(intent);
//                                                                finish();
                                                            } else {
                                                                JumpUtils.push_jump_proc(MainActivity.this, extra);
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        });
                            }

                            if (postBean.getCategorys() != null) {
                                ladyImage.setImageResource(0);
                                childImage.setImageResource(0);

                                List<PortalBean.CategorysBean> categorys = postBean.getCategorys();

//                                Glide.with(MainActivity.this)
//                                        .load(categorys.get(0).getCat_img())
//                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
//                                        //.placeholder(R.drawable.parceholder)
//                                        .centerCrop()
//                                        .override(DisplayUtils.getScreenW(MainActivity.this) / 2, 330)
//                                        .into(childImage);

                                ViewUtils.loadImageWithOkhttp(categorys.get(1).getCat_img(), MainActivity.this, ladyImage);
                                ViewUtils.loadImageWithOkhttp(categorys.get(0).getCat_img(), MainActivity.this, childImage);

//                                Glide.with(MainActivity.this)
//                                        .load(categorys.get(1).getCat_img())
//                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
//                                        //.placeholder(R.drawable.parceholder)
//                                        .centerCrop()
//                                        .override(DisplayUtils.getScreenW(MainActivity.this) / 2, 330)
//                                        .into(ladyImage);
                            }

                            List<BrandlistAdapter> brandlistAdapters = new ArrayList<>();
                            //List<RecyclerView> recyclerViews = new ArrayList<>();
                            List<BrandView> brandViews = new ArrayList<>();
                            brand.removeAllViews();
                            //LinearLayoutManager manager = new LinearLayoutManager(this);
                            //manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                            //recyclerView.setLayoutManager(manager);
                            //recyclerView.addItemDecoration(new SpaceItemDecoration(5));
                            //
                            //brandlistAdapter = new BrandlistAdapter(this);
                            //recyclerView.setAdapter(brandlistAdapter);
                            brand.removeAllViews();
                            brandViews.clear();
                            if (postBean.getPromotion_brands() != null) {
                                brand.setVisibility(View.VISIBLE);

                                List<PortalBean.PromotionBrandsBean> brandpromotionEntities =
                                        postBean.getPromotion_brands();
                                if (brandpromotionEntities.size() != 0) {

                                    BrandlistAdapter brandlistAdapter;
                                    //RecyclerView recyclerView;
                                    BrandView brandView;
                                    for (int i = 0; i < brandpromotionEntities.size(); i++) {
                                        //LinearLayoutManager manager = new LinearLayoutManager(MainActivity
                                        //    .this);
                                        //manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                                        brandlistAdapter = new BrandlistAdapter(MainActivity.this);
                                        brandlistAdapters.add(brandlistAdapter);
                                        //recyclerView = new RecyclerView(MainActivity.this);
                                        brandView = new BrandView(MainActivity.this);
                                        //recyclerView.setLayoutManager(manager);
                                        brandView.addItemDecoration(20);

                                        brandViews.add(brandView);
                                        //recyclerViews.add(recyclerView);
                                        brand.addView(brandView);
                                    }

                                    JUtils.Log(TAG,
                                            "brandlistAdapters.size()====" + brandlistAdapters.size());
                                    for (int i = 0; i < brandlistAdapters.size(); i++) {
                                        brandViews.get(i)
                                                .setBrandtitleImage(brandpromotionEntities.get(i).getBrand_pic());
                                        brandViews.get(i).setBrandDesText(brandpromotionEntities.get(i).getBrand_desc());
                                        brandViews.get(i).setAdapter(brandlistAdapters.get(i));
                                        final int finalI = i;
                                        ProductModel.getInstance()
                                                .getBrandlistProducts(brandpromotionEntities.get(i).getId(), 1, 10)
                                                .subscribeOn(Schedulers.io())
                                                .subscribe(new ServiceResponse<BrandListBean>() {

                                                    @Override
                                                    public void onError(Throwable e) {
                                                        super.onError(e);
                                                        JUtils.Log(TAG, "-------onError");
                                                    }

                                                    @Override
                                                    public void onCompleted() {
                                                        super.onCompleted();
                                                        JUtils.Log(TAG, "-------onCompleted");
                                                    }

                                                    @Override
                                                    public void onNext(BrandListBean brandpromotionBean) {
                                                        if (null != brandpromotionBean) {
                                                            if (null != brandpromotionBean.getResults()) {
                                                                JUtils.Log(TAG, brandpromotionBean.toString());
                                                                brandlistAdapters.get(finalI)
                                                                        .update(brandpromotionBean.getResults());
                                                            }
                                                        }
                                                    }
                                                });

                                        final int finalI1 = i;
                                        brandViews.get(i).setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Intent intent = new Intent(MainActivity.this, BrandListActivity.class);
                                                Bundle bundle = new Bundle();
                                                bundle.putInt("id", brandpromotionEntities.get(finalI1).getId());
                                                intent.putExtras(bundle);
                                                startActivity(intent);
                                            }
                                        });

                                        brandlistAdapters.get(i).setListener(new BrandlistAdapter.itemOnclickListener() {
                                            @Override
                                            public void itemClick() {
                                                Intent intent = new Intent(MainActivity.this, BrandListActivity.class);
                                                Bundle bundle = new Bundle();
                                                bundle.putInt("id", brandpromotionEntities.get(finalI1).getId());
                                                intent.putExtras(bundle);
                                                startActivity(intent);
                                            }
                                        });
                                    }
                                }
                            } else {
                                brand.setVisibility(View.GONE);
                            }

                            post_activity_layout.removeAllViews();
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
                                                @Override
                                                public void onError(Call call, Exception e) {
                                                }

                                                @Override
                                                public void onResponse(Bitmap response) {
                                                    int maxHeight = dp2px(MainActivity.this, 300);
                                                    if (response != null) {
//                                                        int height =
//                                                                (int) ((float) ((response.getWidth() + 10) * postActivityBean.size()) / response.getWidth()
//                                                                        * response.getHeight());
//                                                        if (height > maxHeight) height = maxHeight;
//                                                        LinearLayout.LayoutParams layoutParams =
//                                                                new LinearLayout.LayoutParams(
//                                                                        LinearLayout.LayoutParams.MATCH_PARENT, height);
//                                                        layoutParams.setMargins(0, dp2px(MainActivity.this, 10), 0,
//                                                                0);

//                                                        LinearLayout.LayoutParams layoutParams = getLayoutParams(response, DisplayUtils.getScreenW(MainActivity.this));
                                                        imageViewList.get(finalI).setAdjustViewBounds(true);

                                                        int screenWidth = DisplayUtils.getScreenW(MainActivity.this);
                                                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(screenWidth, LinearLayout.LayoutParams.WRAP_CONTENT);
//                                                        lp.width = screenWidth;
//                                                        lp.height = LinearLayout.LayoutParams.WRAP_CONTENT;
                                                        imageViewList.get(finalI).setLayoutParams(lp);

                                                        imageViewList.get(finalI).setMaxWidth(screenWidth);
                                                        imageViewList.get(finalI).setMaxHeight(screenWidth * 5);

//                                                        imageViewList.get(finalI).setLayoutParams(layoutParams);
                                                        imageViewList.get(finalI).setImageBitmap(response);
                                                        if (postActivityBean.get(finalI)
                                                                .getAct_type()
                                                                .equals("webview")) {
                                                            imageViewList.get(finalI)
                                                                    .setOnClickListener(new View.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(View v) {
                                                                            //syncCookie(getActivity(), postBean.getActivity().getActLink());
                                                                            if (postActivityBean.get(finalI)
                                                                                    .isLogin_required()) {
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
                                                                                            postActivityBean.get(finalI).getAct_link());
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
                                                                                        postActivityBean.get(finalI).getAct_link());
                                                                                intent.putExtras(bundle);
                                                                                startActivity(intent);
                                                                            }
                                                                        }
                                                                    });
                                                        } else if (postActivityBean.get(finalI)
                                                                .getAct_type()
                                                                .equals("coupon")) {
                                                            imageViewList.get(finalI)
                                                                    .setOnClickListener(new View.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(View v) {
                                                                            Subscription subscribe7 =
                                                                                    ActivityModel.getInstance()
                                                                                            .getUsercoupons(postActivityBean.get(finalI)
                                                                                                    .getExtras()
                                                                                                    .getTemplateId())
                                                                                            .subscribeOn(Schedulers.io())
                                                                                            .subscribe(
                                                                                                    new ServiceResponse<ResponseBody>() {
                                                                                                        @Override
                                                                                                        public void onNext(
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
                                        postActivityBean.get(0).getMask_link())) {

                                    MastFragment test = MastFragment.newInstance("mask");
                                    test.show(getFragmentManager(), "mask");
                                }
                            } else {
                                post_activity_layout.setVisibility(View.GONE);
                            }
                        } catch (NullPointerException ex) {
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onCompleted() {
                        if (swipeRefreshLayout != null) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (swipeRefreshLayout != null) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                        JUtils.Toast("数据加载失败!");
                    }
                });
        addSubscription(subscribe2);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
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
//                Intent intent1 = new Intent(this, ComplainWebActivity.class);
//                sharedPreferences =
//                        getSharedPreferences("xlmmCookiesAxiba", Context.MODE_PRIVATE);
//                cookies = sharedPreferences.getString("cookiesString", "");
//                domain = sharedPreferences.getString("cookiesDomain", "");
//
//                Bundle bundle1 = new Bundle();
//                bundle1.putString("cookies", cookies);
//                bundle1.putString("domain", domain);
//                bundle1.putString("Cookie", sharedPreferences.getString("Cookie", ""));
//                bundle1.putString("actlink", "http://m.xiaolumeimei.com/pages/tousu.html");
//                intent1.putExtras(bundle1);
//                startActivity(intent1);
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
            case R.id.child_img:
                intent = new Intent(MainActivity.this, ChildListActivity.class);
                startActivity(intent);
                break;
            case R.id.lady_img:
                intent = new Intent(MainActivity.this, LadyListActivity.class);
                startActivity(intent);
                break;

            case R.id.imag_yesterday:
                vp.setCurrentItem(0);
                imagYesterday.setImageDrawable(
                        getResources().getDrawable(R.drawable.yesterday_choose));
                imagToday.setImageDrawable(getResources().getDrawable(R.drawable.today_nochoose));
                imagTomorror.setImageDrawable(
                        getResources().getDrawable(R.drawable.tomorror_nochoose));
                break;
            case R.id.imag_today:
                vp.setCurrentItem(1);
                imagYesterday.setImageDrawable(
                        getResources().getDrawable(R.drawable.yesterday_nochoose));
                imagToday.setImageDrawable(getResources().getDrawable(R.drawable.today_choose));
                imagTomorror.setImageDrawable(
                        getResources().getDrawable(R.drawable.tomorror_nochoose));
                break;
            case R.id.imag_tomorror:
                vp.setCurrentItem(2);
                imagYesterday.setImageDrawable(
                        getResources().getDrawable(R.drawable.yesterday_nochoose));
                imagToday.setImageDrawable(getResources().getDrawable(R.drawable.today_nochoose));
                imagTomorror.setImageDrawable(
                        getResources().getDrawable(R.drawable.tomorror_choose));
                break;
        }

        if (v.getId() != R.id.imag_today
                && v.getId() != R.id.imag_tomorror
                && v.getId() != R.id.imag_yesterday
                && v.getId() != R.id.lady_img
                && v.getId() != R.id.child_img) {
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

    @Override
    protected void onResume() {
        super.onResume();

        //swith_fragment();

        Subscription subscribe = CartsModel.getInstance()
                .show_carts_num()
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<CartsNumResultBean>() {
                    @Override
                    public void onNext(CartsNumResultBean cartsNumResultBean) {
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

    @Override
    protected void onStop() {
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

    @Override
    public void onPageScrolled(int position, float positionOffset,
                               int positionOffsetPixels) {
        swipeRefreshLayout.setEnabled(false);
    }

    @Override
    public void onPageSelected(int position) {
        scrollableLayout.getHelper().setCurrentScrollableContainer(list.get(position));
        switch (position) {
            case 0:
                //radioGroup.check(R.id.rb_yesterday);
                imagYesterday.setImageDrawable(
                        getResources().getDrawable(R.drawable.yesterday_choose));
                imagToday.setImageDrawable(getResources().getDrawable(R.drawable.today_nochoose));
                imagTomorror.setImageDrawable(
                        getResources().getDrawable(R.drawable.tomorror_nochoose));
                break;
            case 1:
                //radioGroup.check(R.id.rb_today);
                imagYesterday.setImageDrawable(
                        getResources().getDrawable(R.drawable.yesterday_nochoose));
                imagToday.setImageDrawable(getResources().getDrawable(R.drawable.today_choose));
                imagTomorror.setImageDrawable(
                        getResources().getDrawable(R.drawable.tomorror_nochoose));
                break;
            case 2:
                //radioGroup.check(R.id.rb_tomorror);
                imagYesterday.setImageDrawable(
                        getResources().getDrawable(R.drawable.yesterday_nochoose));
                imagToday.setImageDrawable(getResources().getDrawable(R.drawable.today_nochoose));
                imagTomorror.setImageDrawable(
                        getResources().getDrawable(R.drawable.tomorror_choose));
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        swipeRefreshLayout.setEnabled(true);
    }

    @Override
    public void onScroll(int currentY, int maxY) {
        if (currentY > 0) {
            swipeRefreshLayout.setEnabled(false);
        } else {
            swipeRefreshLayout.setEnabled(true);
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
    }

    public static LinearLayout.LayoutParams getLayoutParams(Bitmap bitmap, int screenWidth) {

        float rawWidth = bitmap.getWidth();
        float rawHeight = bitmap.getHeight();

        float width = 0;
        float height = 0;

        if (rawWidth > screenWidth) {
            height = (rawHeight / rawWidth) * screenWidth;
            width = screenWidth;
        } else {
            width = rawWidth;
            height = rawHeight;
        }

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((int) width, (int) height);

        return layoutParams;
    }
}
