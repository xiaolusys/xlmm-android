package com.jimei.xiaolumeimei.ui.mminfo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BasePresenterMVVMActivity;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.databinding.ActivityMamainfoBinding;
import com.jimei.xiaolumeimei.entities.MMShoppingBean;
import com.jimei.xiaolumeimei.entities.MaMaRenwuListBean;
import com.jimei.xiaolumeimei.entities.MamaFortune;
import com.jimei.xiaolumeimei.entities.MamaSelfListBean;
import com.jimei.xiaolumeimei.entities.MamaUrl;
import com.jimei.xiaolumeimei.entities.MiPushOrderCarryBean;
import com.jimei.xiaolumeimei.entities.RecentCarryBean;
import com.jimei.xiaolumeimei.entities.UserInfoBean;
import com.jimei.xiaolumeimei.event.HideOrderEvent;
import com.jimei.xiaolumeimei.event.MaMaInfoEmptyEvent;
import com.jimei.xiaolumeimei.event.MaMaInfoEvent;
import com.jimei.xiaolumeimei.event.SetOrderEvent;
import com.jimei.xiaolumeimei.event.ShowOrderEvent;
import com.jimei.xiaolumeimei.event.WebViewEvent;
import com.jimei.xiaolumeimei.mipush.XiaoMiMessageReceiver;
import com.jimei.xiaolumeimei.model.MamaInfoModel;
import com.jimei.xiaolumeimei.ui.activity.main.ActivityWebViewActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.BoutiqueWebviewActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.GoodWeekActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MMChooseListActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MMFans1Activity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MMLevelExamWebViewActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MMNinePicActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MMShareCodeWebViewActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MMShoppingListActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MMStoreWebViewActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MMTeamActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MMcarryLogActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MamaDrawCashActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MamaDrawCouponActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MamaLivenessActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MamaLunTanActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MamaReNewActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MamaVisitorActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.PersonalCarryRankActivity;
import com.jimei.xiaolumeimei.ui.fragment.v2.NewMMFragment;
import com.jimei.xiaolumeimei.utils.JumpUtils;
import com.jimei.xiaolumeimei.utils.LoginUtils;
import com.jimei.xiaolumeimei.utils.StatusBarUtil;
import com.jimei.xiaolumeimei.utils.ViewUtils;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.udesk.UdeskConst;
import cn.udesk.UdeskSDKManager;
import cn.udesk.xmpp.UdeskMessageManager;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye on 2016/6/24.
 */
public class MMInfoActivity
        extends BasePresenterMVVMActivity<MMInfoPresenter, MMInfoModel, ActivityMamainfoBinding>
        implements MMInfoContract.View, View.OnClickListener, OnChartValueSelectedListener {
    String TAG = "MMInfoActivity";
    private String sharelink;

    MamaFortune mamaFortune;
    private SharedPreferences sharedPreferences;
    private String cookies;
    private String domain;
    private double carrylogMoney;
    private String s;
    private String from;
    private String actlink;
    private boolean isThisWeek = true;
    private MamaUrl.ResultsBean.ExtraBean mamaResult;
    private int mmId;
    private String hisConfirmedCashOut;
    private double mamaCarryValue;
    private String mamaid;
    public static XiaoMiMessageReceiver.XiaoMiPushHandler handler = null;

    public static XiaoMiMessageReceiver.XiaoMiPushHandler getHandler() {
        return handler;
    }

    @Override
    protected void initData() {
        UdeskSDKManager.getInstance().initApiKey(this, XlmmConst.UDESK_URL, XlmmConst.UDESK_KEY);
        showIndeterminateProgressDialog(false);
        setDialogBack(R.color.transparentblack70);
        mPresenter.getMamaUrl();
        mPresenter.getShareShopping();
        mPresenter.getMamaFortune();
        mPresenter.getRefund();
        mPresenter.getMaMaselfList();
        mPresenter.getMaMaRenwuListBean(mamaid);
        mPresenter.getUserInfo();
        mPresenter.getOrderCarry();
    }

    @Override
    protected void setListener() {
        b.imgLeft.setOnClickListener(this);
        b.imgRight.setOnClickListener(this);
        b.yueLayout.setOnClickListener(this);
        b.leijiLayout.setOnClickListener(this);
        b.huoyueLayout.setOnClickListener(this);
        b.imgExam.setOnClickListener(this);
        b.mamaPay.setOnClickListener(this);

        b.rlInvite1kaidian.setOnClickListener(this);
        b.rlFans.setOnClickListener(this);
        b.rlOrderlist.setOnClickListener(this);
        b.rlIncome.setOnClickListener(this);

        b.rlChooselist.setOnClickListener(this);
        b.rlParty.setOnClickListener(this);
        b.rlPush.setOnClickListener(this);
        b.rlShop.setOnClickListener(this);
        b.visitLayout.setOnClickListener(this);
        b.orderLayout.setOnClickListener(this);
        b.fundLayout.setOnClickListener(this);
        b.rlTeam.setOnClickListener(this);
        b.rlIncome1.setOnClickListener(this);
        b.tvNoticesee.setOnClickListener(this);
        b.luntan.setOnClickListener(this);
        b.goodWeek.setOnClickListener(this);
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        mamaid = extras.getString("mamaid");
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void updateMaMaInfo(MaMaInfoEmptyEvent event) {
        MamaInfoModel.getInstance()
                .getMamaFortune()
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<MamaFortune>() {
                    @Override
                    public void onNext(MamaFortune mamaFortune) {
                        if (mamaFortune != null) {
                            EventBus.getDefault().post(new MaMaInfoEvent(mamaFortune));
                        }
                    }
                });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateMaMaUi(MaMaInfoEvent event) {
        b.tvMamashengyu.setText(
                event.mamaFortune.getMamaFortune().getExtraInfo().getSurplusDays() + "");
        b.tvYue.setText(event.mamaFortune.getMamaFortune().getCashValue() + "");
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_mamainfo;
    }

    @Override
    protected void initViews() {
        EventBus.getDefault().register(this);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorAccent), 0);
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return true;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return TransitionMode.SCALE;
    }

    @Override
    public void initMamaUrl(MamaUrl mamaUrl) {
        mamaResult = mamaUrl.getResults().get(0).getExtra();
        Glide.with(MMInfoActivity.this)
                .load(mamaResult.getPictures().getExam_pic())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(b.imgExam);
    }

    @Override
    public void initMMview(MamaFortune fortune) {
        int days = fortune.getMamaFortune().getExtraInfo().getSurplusDays();
        hisConfirmedCashOut = fortune.getMamaFortune().getExtraInfo().getHisConfirmedCashOut();
        JUtils.Log(TAG,
                fortune.toString() + "fortune.getMamaFortune().getExtraInfo().getSurplusDays()" + days);
        mamaCarryValue = (double) (Math.round(fortune.getMamaFortune().getCashValue() * 100)) / 100;
        b.tvFund.setText(
                Double.toString((double) (Math.round(fortune.getMamaFortune().getCarryValue() * 100)) / 100)
                        + "元");
        //b.tvInviteNum.setText(fortune.getMamaFortune().getInviteNum() + "位");
        //b.tvFansnum.setText(fortune.getMamaFortune().getFansNum() + "人");
        b.tvOrder.setText(s + "个");
        if (days <= 15) {
            b.mamaPay.setVisibility(View.VISIBLE);
        }

        b.tvFansnum1.setText(fortune.getMamaFortune().getFansNum() + "人");
        b.tvMamashengyu.setText(days + "");
        b.tvYue.setText(fortune.getMamaFortune().getCashValue() + "");
        b.tvLeiji.setText(fortune.getMamaFortune().getCarryValue() + "");
        b.tvHuoyue.setText(fortune.getMamaFortune().getActiveValueNum() + "");
        b.tvMamalevel.setText(fortune.getMamaFortune().getMamaLevelDisplay() + "");
        b.tvMamavip.setText(fortune.getMamaFortune().getExtraInfo().getAgencylevelDisplay() + "");
        mmId = fortune.getMamaFortune().getMamaId();
        b.mamaId.setText("ID: " + mmId);
        if (!TextUtils.isEmpty(fortune.getMamaFortune().getExtraInfo().getThumbnail())) {
            setUserImage(fortune);
        }
        hideIndeterminateProgressDialog();
    }

    private void setUserImage(MamaFortune fortune) {
        try {
            if (!TextUtils.isEmpty(fortune.getMamaFortune().getExtraInfo().getThumbnail())) {

                ViewUtils.loadImgToImgViewWithTransformCircle(this, b.imgUser,
                        fortune.getMamaFortune().getExtraInfo().getThumbnail());
            } else {
                b.imgUser.setImageResource(R.drawable.img_diamond);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initShareInfo(MMShoppingBean shoppingBean) {
        sharelink = shoppingBean.getShopInfo().getPreviewShopLink();
    }

    @Override
    public void init_chart() {
        //mChart.setOnChartGestureListener(this);
        b.chart1.setOnChartValueSelectedListener(this);
        b.chart1.setDrawGridBackground(false);

        // no description text
        b.chart1.setDescription("");
        b.chart1.setNoDataText("");

        b.chart1.setTouchEnabled(true);

        // enable scaling and dragging
        b.chart1.setDragEnabled(true);
        b.chart1.setScaleEnabled(false);
        b.chart1.setBackgroundColor(Color.WHITE);

        XAxis xAxis = b.chart1.getXAxis();
        xAxis.setEnabled(true);     //是否显示X坐标轴 及
        // 对应的刻度竖线，默认是true
        xAxis.setDrawAxisLine(true); //是否绘制坐标轴的线，即含有坐标的那条线，默认是true
        xAxis.setDrawGridLines(true); //是否显示X坐标轴上的刻度竖线，默认是true
        xAxis.setDrawLabels(true); //是否显示X坐标轴上的刻度，默认是true

        xAxis.setTextColor(Color.parseColor("#F5B123")); //X轴上的刻度的颜色
        xAxis.setTextSize(9f); //X轴上的刻度的字的大小 单位dp
        //      xAxis.setTypeface(Typeface tf); //X轴上的刻度的字体
        xAxis.setGridColor(Color.parseColor("#F5B123")); //X轴上的刻度竖线的颜色
        xAxis.setGridLineWidth(1); //X轴上的刻度竖线的宽 float类型
        xAxis.enableGridDashedLine(3, 8,
                0); //虚线表示X轴上的刻度竖线(float lineLength, float spaceLength, float phase)三个参数，1.线长，2.虚线间距，3.虚线开始坐标

        b.chart1.getLegend().setEnabled(false);
        b.chart1.getAxisLeft().setDrawGridLines(false);
        b.chart1.getAxisRight().setDrawGridLines(false);

        b.chart1.getXAxis().setPosition(XAxis.XAxisPosition.TOP);

        //x y 坐标是否显示
        //mChart.getXAxis().setEnabled(false);
        b.chart1.getAxisRight().setEnabled(false);
        b.chart1.getAxisLeft().setEnabled(false);

        // add data
    }

    @Override
    public void initMMdata(MamaFortune fortune) {
        mamaFortune = fortune;
        actlink = fortune.getMamaFortune().getMamaEventLink();
        JUtils.Log(TAG, "actlink" + actlink);
        carrylogMoney = ((double) (Math.round(fortune.getMamaFortune().getCarryValue() * 100)) / 100);
        s = Integer.toString(fortune.getMamaFortune().getOrderNum());
    }

    @Override
    public void setRlVisiBility() {
        //b.rlEmptyChart.setVisibility(View.VISIBLE);
        //b.rlChart.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setChartData(LineData data) {
        b.chart1.setData(data);
        b.chart1.setVisibility(View.VISIBLE);
        b.chart1.animateX(2500, Easing.EasingOption.EaseInOutQuart);
        b.chart1.setVisibleXRangeMaximum(6);
    }

    @Override
    public void initTodatText(List<RecentCarryBean.ResultsEntity> his_refund) {
        b.tvVisit2.setText(Integer.toString(his_refund.get(his_refund.size() - 1).getVisitorNum()));
        b.tvTodayOrder2.setText(Integer.toString(his_refund.get(his_refund.size() - 1).getOrderNum()));
        b.tvTodayFund2.setText(Double.toString(
                (double) (Math.round(his_refund.get(his_refund.size() - 1).getCarry() * 100)) / 100));
    }

    @Override
    public void getMaMaRenwuListBean(MaMaRenwuListBean maMaRenwuListBean) {
        if (!LoginUtils.isMamaRenwulist(getApplicationContext())) {
            try {
                if (maMaRenwuListBean.getConfig().isPage_pop()) {
                    LoginUtils.saveMamaRenwulist(getApplicationContext(), true);
                    NewMMFragment newMMFragment =
                            NewMMFragment.newInstance(maMaRenwuListBean, mamaResult.getAct_info());
                    newMMFragment.show(getFragmentManager(), "mamalist");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void getMaMaRenwuListBean(MamaSelfListBean mamaSelfListBean) {
        List<MamaSelfListBean.ResultsBean> results = mamaSelfListBean.getResults();
        List<String> list = new ArrayList<>();
        if (mamaSelfListBean.getUnread_cnt() > 0) {
            b.imageNotice.setVisibility(View.VISIBLE);
            for (int i = 0; i < results.size(); i++) {
                if (!results.get(i).isRead()) {
                    list.add(results.get(i).getTitle());
                }
            }
            b.marqueeView.startWithList(list);
        } else {
            b.imageNotice.setVisibility(View.GONE);
        }
    }

    @Override
    public void setUdesk(UserInfoBean userInfoBean) {
        String id = userInfoBean.getId() + "";
        Map<String, String> info = new HashMap<>();
        info.put(UdeskConst.UdeskUserInfo.USER_SDK_TOKEN, id);
        info.put(UdeskConst.UdeskUserInfo.NICK_NAME, userInfoBean.getNick() + "(ID:" + id + ")");
        info.put(UdeskConst.UdeskUserInfo.CELLPHONE, userInfoBean.getMobile());
        UdeskSDKManager.getInstance().setUserInfo(this, id, info);
        UdeskMessageManager.getInstance().event_OnNewMsgNotice.bind(this, "OnNewMsgNotice");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setOrderCarry(SetOrderEvent event) {
        MiPushOrderCarryBean bean = event.getBean();
        ViewUtils.loadImgToImgViewWithTransformCircle(MMInfoActivity.this, b.miHead, bean.getAvatar());
        b.miInfo.setText(bean.getContent());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showOrder(ShowOrderEvent event) {
        b.miLayout.setVisibility(View.VISIBLE);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void hideOrder(HideOrderEvent event) {
        b.miLayout.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.yue_layout:
                if (mamaFortune != null
                        && mamaFortune.getMamaFortune().getExtraInfo().getCouldCashOut() == 0) {
                    intent = new Intent(this, MamaDrawCouponActivity.class);
                    intent.putExtra("cash", mamaFortune.getMamaFortune().getCashValue());
                    intent.putExtra("msg", mamaFortune.getMamaFortune().getExtraInfo().getCashoutReason());
                    startActivity(intent);
                } else if (mamaFortune != null
                        && mamaFortune.getMamaFortune().getExtraInfo().getCouldCashOut() == 1) {
                    intent = new Intent(this, MamaDrawCashActivity.class);
                    intent.putExtra("cash", mamaFortune.getMamaFortune().getCashValue());
                    startActivity(intent);
                }
                break;
            case R.id.img_left:
                isThisWeek = false;
                b.rlEmptyChart.setVisibility(View.INVISIBLE);
                b.rlChart.setVisibility(View.VISIBLE);
                b.chart1.clear();
                mPresenter.setDataOfPreviousWeek();
                b.tvVisit2.setText(Integer.toString(
                        mPresenter.his_refund.get(mPresenter.his_refund.size() - 1).getVisitorNum()));
                b.tvTodayOrder2.setText(Integer.toString(
                        mPresenter.his_refund.get(mPresenter.his_refund.size() - 1).getOrderNum()));
                b.tvTodayFund2.setText(Double.toString((double) (Math.round(
                        mPresenter.his_refund.get(mPresenter.his_refund.size() - 1).getCarry() * 100)) / 100));
                break;
            case R.id.img_right:
                isThisWeek = true;
                b.rlEmptyChart.setVisibility(View.INVISIBLE);
                b.chart1.setVisibility(View.VISIBLE);
                b.chart1.clear();
                mPresenter.setDataOfThisWeek();
                b.tvVisit2.setText(Integer.toString(
                        mPresenter.his_refund.get(mPresenter.his_refund.size() - 1).getVisitorNum()));
                b.tvTodayOrder2.setText(Integer.toString(
                        mPresenter.his_refund.get(mPresenter.his_refund.size() - 1).getOrderNum()));
                b.tvTodayFund2.setText(Double.toString((double) (Math.round(
                        mPresenter.his_refund.get(mPresenter.his_refund.size() - 1).getCarry() * 100)) / 100));
                break;
            case R.id.rl_chooselist:
                startActivity(new Intent(this, MMChooseListActivity.class));
                break;
            case R.id.rl_party:
                if (mamaResult != null) {
                    MobclickAgent.onEvent(this, "XLMMUniID");
                    intent = new Intent(this, BoutiqueWebviewActivity.class);
                    sharedPreferences = getSharedPreferences("xlmmCookiesAxiba", Context.MODE_PRIVATE);
                    cookies = sharedPreferences.getString("cookiesString", "");
                    domain = sharedPreferences.getString("cookiesDomain", "");

                    Bundle bundlerl_party = new Bundle();
                    bundlerl_party.putString("cookies", cookies);
                    bundlerl_party.putString("domain", domain);
                    bundlerl_party.putString("Cookie", sharedPreferences.getString("Cookie", ""));
                    bundlerl_party.putString("actlink", mamaResult.getAct_info());
                    intent.putExtras(bundlerl_party);
                    startActivity(intent);
                }
                break;
            case R.id.rl_push:
                startActivity(new Intent(this, MMNinePicActivity.class));
                break;
            case R.id.rl_shop:
                if (sharelink != null && !"".equals(sharelink)) {
                    Intent intentrl_shop = new Intent(this, MMStoreWebViewActivity.class);
                    sharedPreferences = getSharedPreferences("xlmmCookiesAxiba", Context.MODE_PRIVATE);
                    cookies = sharedPreferences.getString("cookiesString", "");
                    domain = sharedPreferences.getString("cookiesDomain", "");

                    Bundle bundlerl_shop = new Bundle();
                    bundlerl_shop.putString("cookies", cookies);
                    bundlerl_shop.putString("domain", domain);
                    bundlerl_shop.putString("Cookie", sharedPreferences.getString("Cookie", ""));
                    bundlerl_shop.putString("actlink", sharelink);
                    intentrl_shop.putExtras(bundlerl_shop);
                    startActivity(intentrl_shop);
                }
                break;
            case R.id.mama_pay:
                Bundle bundle = new Bundle();
                bundle.putDouble("mamaCarryValue", mamaCarryValue);
                readyGo(MamaReNewActivity.class, bundle);
                break;
            case R.id.rl_invite_1kaidian:
                if (mamaResult != null) {
                    JumpUtils.jumpToWebViewWithCookies(this, mamaResult.getInvite(), 26,
                            MMShareCodeWebViewActivity.class, "");
                }
                break;
            case R.id.rl_fans:
                if (mamaResult != null) {
                    sharedPreferences = getSharedPreferences("xlmmCookiesAxiba", Context.MODE_PRIVATE);
                    String cookies = sharedPreferences.getString("cookiesString", "");
                    String actlink = mamaResult.getFans_explain();
                    String domain = sharedPreferences.getString("cookiesDomain", "");
                    String sessionid = sharedPreferences.getString("Cookie", "");
                    JUtils.Log(TAG, "GET cookie:" + cookies + " actlink:" + actlink + " domain:" + domain +
                            " sessionid:" + sessionid);
                    EventBus.getDefault().postSticky(new WebViewEvent(cookies, domain, actlink, -1, sessionid));
                    startActivity(new Intent(this, MMFans1Activity.class));
                }
                break;
            case R.id.rl_orderlist:
                Intent intent2 = new Intent(this, MMShoppingListActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putString("order", s);
                intent2.putExtras(bundle1);
                startActivity(intent2);
                break;
            case R.id.order_layout:
                Intent intent3 = new Intent(this, MMShoppingListActivity.class);
                Bundle bundle3 = new Bundle();
                bundle3.putString("order", s);
                intent3.putExtras(bundle3);
                startActivity(intent3);
                break;
            case R.id.rl_income:
                jumpTpMMCarryLogActivity();
                break;
            case R.id.leiji_layout:
                jumpTpMMCarryLogActivity();
                break;
            case R.id.fund_layout:
                jumpTpMMCarryLogActivity();
                break;
            case R.id.visit_layout:
                Intent intent5 = new Intent(new Intent(this, MamaVisitorActivity.class));
                Bundle bundle2 = new Bundle();
                bundle2.putString("from", from);
                intent5.putExtras(bundle2);
                startActivity(intent5);
                break;
            case R.id.huoyue_layout:
                if (mamaFortune != null) {
                    intent = new Intent(this, MamaLivenessActivity.class);
                    intent.putExtra("liveness", mamaFortune.getMamaFortune().getActiveValueNum());
                    startActivity(intent);
                }
                break;
            case R.id.imgExam:
                if (mamaFortune != null) {
                    JumpUtils.jumpToWebViewWithCookies(this, mamaResult.getExam(), -1,
                            MMLevelExamWebViewActivity.class, "活动");
                }
                break;
            case R.id.rl_team:
                if (mamaResult != null) {
                    MobclickAgent.onEvent(this, "TeamRankID");
                    Bundle bundleAAA = new Bundle();
                    bundleAAA.putString("id", mmId + "");
                    bundleAAA.putString("url", mamaResult.getTeam_explain());
                    readyGo(MMTeamActivity.class, bundleAAA);
                }
                break;
            case R.id.rl_income1:
                readyGo(PersonalCarryRankActivity.class);
                break;
            case R.id.tv_noticesee:
                if (mamaResult != null) {
                    MobclickAgent.onEvent(this, "XLMMNoticeID");
                    JumpUtils.jumpToWebViewWithCookies(this, mamaResult.getNotice(), -1,
                            ActivityWebViewActivity.class, "信息通知");
                }
                break;
            case R.id.luntan:
                if (mamaResult != null) {
                    JumpUtils.jumpToWebViewWithCookies(this, mamaResult.getForum(),
                            -1, MamaLunTanActivity.class);
                }
                break;
            case R.id.good_week:
                startActivity(new Intent(this, GoodWeekActivity.class));
                break;
        }
    }

    private void jumpTpMMCarryLogActivity() {
        Bundle bundlerl_income = new Bundle();
        bundlerl_income.putString("carrylogMoney", carrylogMoney + "");
        bundlerl_income.putString("hisConfirmedCashOut", hisConfirmedCashOut + "");
        readyGo(MMcarryLogActivity.class, bundlerl_income);
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler = new XiaoMiMessageReceiver.XiaoMiPushHandler(this);
        mPresenter.getMamaFortune();
        mPresenter.getRefund();
        mPresenter.getMaMaselfList();
        MobclickAgent.onPageStart(this.getClass().getSimpleName());
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler = null;
        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
        MobclickAgent.onPause(this);
    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
        Log.i("Entry selected", e.toString());
        b.tvVisit2.setText(Integer.toString(mPresenter.show_refund.get(e.getXIndex()).getVisitorNum()));
        //from = (MAX_RECENT_DAYS - 1 - e.getXIndex()) + "";
        if (isThisWeek) {
            from = (mPresenter.show_refund.size() - e.getXIndex() - 1) + "";
        } else {
            from = (mPresenter.show_refund.size() - e.getXIndex() - 1 + Calendar.getInstance()
                    .get(Calendar.DAY_OF_WEEK) - 1) + "";
        }
        b.tvTodayOrder2.setText(
                Integer.toString(mPresenter.show_refund.get(e.getXIndex()).getOrderNum()));
        b.tvTodayFund2.setText(Double.toString(
                (double) (Math.round(mPresenter.show_refund.get(e.getXIndex()).getCarry() * 100)) / 100));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_kefu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.kefu:
                UdeskSDKManager.getInstance().showRobotOrConversation(this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNothingSelected() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.setDestory();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void showNetworkError() {

    }

    @Override
    public void showEmpty(String msg) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void refreshView() {

    }
}
