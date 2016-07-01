package com.jimei.xiaolumeimei.ui.mminfo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.XlmmApp;
import com.jimei.xiaolumeimei.base.BasePresenterActivity;
import com.jimei.xiaolumeimei.base.CommonWebViewActivity;
import com.jimei.xiaolumeimei.data.XlmmApi;
import com.jimei.xiaolumeimei.entities.MMShoppingBean;
import com.jimei.xiaolumeimei.entities.MamaFortune;
import com.jimei.xiaolumeimei.entities.RecentCarryBean;
import com.jimei.xiaolumeimei.event.WebViewEvent;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.BoutiqueWebviewActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MMChooseListActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MMFans1Activity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MMNinePicActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MMShareCodeWebViewActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MMShoppingListActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MMStoreWebViewActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MMcarryLogActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MamaDrawCashActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MamaLivenessActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MamaVisitorActivity;
import com.jimei.xiaolumeimei.utils.JumpUtils;
import com.jude.utils.JUtils;
import com.umeng.analytics.MobclickAgent;
import java.util.Calendar;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

/**
 * Created by itxuye on 2016/6/24.
 */
public class MMInfoActivity extends BasePresenterActivity<MMInfoPresenter, MMInfoModel>
    implements MMInfoContract.View, View.OnClickListener, OnChartValueSelectedListener {
  String TAG = "MMInfoActivity";
  @Bind(R.id.imgUser) ImageView imgUser;
  @Bind(R.id.btn_two_dimen) TextView btn_two_dimen;
  @Bind(R.id.rl_chart) RelativeLayout rl_chart;
  @Bind(R.id.btn_chooselist) TextView btn_chooselist;
  @Bind(R.id.chart1) LineChart mChart;
  @Bind(R.id.img_left) ImageView img_left;
  @Bind(R.id.img_right) ImageView img_right;
  @Bind(R.id.rl_empty_chart) RelativeLayout rl_empty_chart;
  @Bind(R.id.tv_visit2) TextView tv_today_visit2;
  @Bind(R.id.tv_today_order2) TextView tv_today_order2;
  @Bind(R.id.tv_today_fund2) TextView tv_today_fund2;
  @Bind(R.id.rl_mama_info) RelativeLayout rlMamaInfo;
  @Bind(R.id.rl_order) LinearLayout rlOrder;
  @Bind(R.id.rl_chooselist) RelativeLayout rlChooselist;
  @Bind(R.id.rl_party) RelativeLayout rl_party;
  @Bind(R.id.rl_push) RelativeLayout rl_push;
  @Bind(R.id.rl_shop) RelativeLayout rl_shop;
  @Bind(R.id.rl_two_dimen) RelativeLayout rlTwoDimen;
  @Bind(R.id.tv_invite_num) TextView tv_invite_num;
  @Bind(R.id.rl_fans) RelativeLayout rl_fans;
  @Bind(R.id.tv_fansnum) TextView tv_fansnum;
  @Bind(R.id.rl_orderlist) RelativeLayout rl_orderlist;
  @Bind(R.id.tv_order) TextView tv_order;
  @Bind(R.id.rl_income) RelativeLayout rl_income;
  @Bind(R.id.tv_fund) TextView tv_fund;
  @Bind(R.id.visit_layout) LinearLayout visitLayout;
  @Bind(R.id.order_layout) LinearLayout orderLayout;
  @Bind(R.id.fund_layout) LinearLayout fundLayout;

  @Bind(R.id.tv_mamalevel) TextView tvMamalevel;
  @Bind(R.id.tv_mamashengyu) TextView tvShengyu;
  @Bind(R.id.imgExam) ImageView imgExam;
  @Bind(R.id.tv_yue) TextView tvYue;
  @Bind(R.id.yue_layout) LinearLayout yueLayout;
  @Bind(R.id.tv_leiji) TextView tvLeiji;
  @Bind(R.id.leiji_layout) LinearLayout leijiLayout;
  @Bind(R.id.tv_huoyue) TextView tvHuoyue;
  @Bind(R.id.huoyue_layout) LinearLayout huoyueLayout;

  private String title, sharelink, desc, shareimg;

  MamaFortune mamaFortune;
  private SharedPreferences sharedPreferences;
  private String cookies;
  private String domain;
  private double carrylogMoney;
  private String s;
  private String from;
  private String actlink;
  private String shareMmcode;
  private boolean isThisWeek = true;

  @Override protected void initData() {
    mPresenter.getShareShopping();
    mPresenter.getMamaFortune();
    mPresenter.getRefund();
  }

  @Override protected void setListener() {
    img_left.setOnClickListener(this);
    img_right.setOnClickListener(this);
    yueLayout.setOnClickListener(this);
    leijiLayout.setOnClickListener(this);
    huoyueLayout.setOnClickListener(this);
    imgExam.setOnClickListener(this);

    rlTwoDimen.setOnClickListener(this);
    rl_fans.setOnClickListener(this);
    rl_orderlist.setOnClickListener(this);
    rl_income.setOnClickListener(this);

    rlChooselist.setOnClickListener(this);
    rl_party.setOnClickListener(this);
    rl_push.setOnClickListener(this);
    rl_shop.setOnClickListener(this);
    visitLayout.setOnClickListener(this);
    orderLayout.setOnClickListener(this);
    fundLayout.setOnClickListener(this);
  }

  @Override protected void getBundleExtras(Bundle extras) {

  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.activity_mamainfo;
  }

  @Override protected void initViews() {

  }

  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
  }

  @Override public void initMMview(MamaFortune fortune) {
    JUtils.Log(TAG,fortune.toString());
    tv_fund.setText(
        Double.toString((double) (Math.round(fortune.getMamaFortune().getCarryValue() * 100)) / 100)
            + "元");
    tv_invite_num.setText(fortune.getMamaFortune().getInviteNum() + "位");
    tv_fansnum.setText(fortune.getMamaFortune().getFansNum() + "人");
    tv_order.setText(s + "个");

    tvYue.setText(
        fortune.getMamaFortune().getCashValue()+"");
    tvLeiji.setText(fortune.getMamaFortune().getCarryValue()+"");
    tvHuoyue.setText(fortune.getMamaFortune().getActiveValueNum()+"");
    tvMamalevel.setText(fortune.getMamaFortune().getMamaLevelDisplay());
    tvShengyu.setText(fortune.getMamaFortune().getExtraInfo().getSurplusDays());
  }

  @Override public void initShareInfo(MMShoppingBean shoppingBean) {
    title = shoppingBean.getShopInfo().getName();
    sharelink = shoppingBean.getShopInfo().getPreviewShopLink();
    shareimg = shoppingBean.getShopInfo().getThumbnail();
    desc = shoppingBean.getShopInfo().getDesc();
  }

  @Override public void init_chart() {
    //mChart.setOnChartGestureListener(this);
    mChart.setOnChartValueSelectedListener(this);
    mChart.setDrawGridBackground(false);

    // no description text
    mChart.setDescription("");
    mChart.setNoDataText("");

    mChart.setTouchEnabled(true);

    // enable scaling and dragging
    mChart.setDragEnabled(true);
    mChart.setScaleEnabled(false);
    mChart.setBackgroundColor(Color.WHITE);

    XAxis xAxis = mChart.getXAxis();
    xAxis.setEnabled(true);     //是否显示X坐标轴 及 对应的刻度竖线，默认是true
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

    mChart.getLegend().setEnabled(false);
    mChart.getAxisLeft().setDrawGridLines(false);
    mChart.getAxisRight().setDrawGridLines(false);

    mChart.getXAxis().setPosition(XAxis.XAxisPosition.TOP);

    //x y 坐标是否显示
    //mChart.getXAxis().setEnabled(false);
    mChart.getAxisRight().setEnabled(false);
    mChart.getAxisLeft().setEnabled(false);

    // add data
  }

  @Override public void initMMdata(MamaFortune fortune) {
    mamaFortune = fortune;
    shareMmcode = fortune.getMamaFortune().getExtraInfo().getInviteUrl();
    actlink = fortune.getMamaFortune().getMamaEventLink();
    JUtils.Log(TAG, "actlink" + actlink);
    carrylogMoney = ((double) (Math.round(fortune.getMamaFortune().getCarryValue() * 100)) / 100);
    s = Integer.toString(fortune.getMamaFortune().getOrderNum());
  }

  @Override public void setRlVisiBility() {
    rl_empty_chart.setVisibility(View.VISIBLE);
    rl_chart.setVisibility(View.INVISIBLE);
  }

  @Override public void setChartData(LineData data) {
    mChart.setData(data);
    mChart.setVisibility(View.VISIBLE);
    mChart.animateX(2500, Easing.EasingOption.EaseInOutQuart);
    mChart.setVisibleXRangeMaximum(6);
  }

  @Override public void initTodatText(List<RecentCarryBean.ResultsEntity> his_refund) {
    tv_today_visit2.setText(
        Integer.toString(his_refund.get(his_refund.size() - 1).getVisitorNum()));
    tv_today_order2.setText(Integer.toString(his_refund.get(his_refund.size() - 1).getOrderNum()));
    tv_today_fund2.setText(Double.toString(
        (double) (Math.round(his_refund.get(his_refund.size() - 1).getCarry() * 100)) / 100));
  }

  @Override public void onClick(View v) {
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
        rl_empty_chart.setVisibility(View.INVISIBLE);
        rl_chart.setVisibility(View.VISIBLE);
        mChart.clear();
        mPresenter.setDataOfPreviousWeek();
        tv_today_visit2.setText(Integer.toString(
            mPresenter.his_refund.get(mPresenter.his_refund.size() - 1).getVisitorNum()));
        tv_today_order2.setText(Integer.toString(
            mPresenter.his_refund.get(mPresenter.his_refund.size() - 1).getOrderNum()));
        tv_today_fund2.setText(Double.toString((double) (Math.round(
            mPresenter.his_refund.get(mPresenter.his_refund.size() - 1).getCarry() * 100)) / 100));
        break;
      case R.id.img_right:
        isThisWeek = true;
        rl_empty_chart.setVisibility(View.INVISIBLE);
        rl_chart.setVisibility(View.VISIBLE);
        mChart.clear();
        mPresenter.setDataOfThisWeek();
        tv_today_visit2.setText(Integer.toString(
            mPresenter.his_refund.get(mPresenter.his_refund.size() - 1).getVisitorNum()));
        tv_today_order2.setText(Integer.toString(
            mPresenter.his_refund.get(mPresenter.his_refund.size() - 1).getOrderNum()));
        tv_today_fund2.setText(Double.toString((double) (Math.round(
            mPresenter.his_refund.get(mPresenter.his_refund.size() - 1).getCarry() * 100)) / 100));
        break;
      case R.id.rl_chooselist:
        startActivity(new Intent(this, MMChooseListActivity.class));
        break;
      case R.id.rl_party:
        intent = new Intent(this, BoutiqueWebviewActivity.class);
        sharedPreferences = getSharedPreferences("xlmmCookiesAxiba", Context.MODE_PRIVATE);
        cookies = sharedPreferences.getString("cookiesString", "");
        domain = sharedPreferences.getString("cookiesDomain", "");

        Bundle bundlerl_party = new Bundle();
        bundlerl_party.putString("cookies", cookies);
        bundlerl_party.putString("domain", domain);
        bundlerl_party.putString("Cookie", sharedPreferences.getString("Cookie", ""));
        bundlerl_party.putString("actlink", actlink);
        intent.putExtras(bundlerl_party);
        startActivity(intent);
        break;
      case R.id.rl_push:
        startActivity(new Intent(this, MMNinePicActivity.class));
        break;
      case R.id.rl_shop:

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

        break;
      case R.id.rl_two_dimen:

        Intent intentrl_two_dimen = new Intent(this, MMShareCodeWebViewActivity.class);
        sharedPreferences = getSharedPreferences("xlmmCookiesAxiba", Context.MODE_PRIVATE);
        cookies = sharedPreferences.getString("cookiesString", "");
        domain = sharedPreferences.getString("cookiesDomain", "");

        Bundle bundlerl_two_dimen = new Bundle();
        bundlerl_two_dimen.putString("cookies", cookies);
        bundlerl_two_dimen.putString("domain", domain);
        bundlerl_two_dimen.putString("Cookie", sharedPreferences.getString("Cookie", ""));
        bundlerl_two_dimen.putString("actlink", shareMmcode);
        intentrl_two_dimen.putExtras(bundlerl_two_dimen);
        startActivity(intentrl_two_dimen);
        break;
      case R.id.rl_fans:
        sharedPreferences = getSharedPreferences("xlmmCookiesAxiba", Context.MODE_PRIVATE);
        SharedPreferences sharedPreferences2 =
            XlmmApp.getmContext().getSharedPreferences("APICLIENT", Context.MODE_PRIVATE);
        String baseUrl = "http://" + sharedPreferences2.getString("BASE_URL", "");
        if (baseUrl.equals("http://")) {
          baseUrl = XlmmApi.APP_BASE_URL;
        }
        String cookies = sharedPreferences.getString("cookiesString", "");
        String actlink = baseUrl + "/pages/fans-explain.html";
        String domain = sharedPreferences.getString("cookiesDomain", "");
        String sessionid = sharedPreferences.getString("Cookie", "");

        JUtils.Log(TAG, "GET cookie:" + cookies + " actlink:" + actlink + " domain:" + domain +
            " sessionid:" + sessionid);

        EventBus.getDefault().postSticky(new WebViewEvent(cookies, domain, actlink, -1, sessionid));

        startActivity(new Intent(this, MMFans1Activity.class));
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
          JumpUtils.jumpToWebViewWithCookies(this,
              mamaFortune.getMamaFortune().getExtraInfo().getNextLevelExamUrl(), -1,
              CommonWebViewActivity.class, "妈妈考试");
        }
        break;
    }
  }

  private void jumpTpMMCarryLogActivity() {
    Intent intent1 = new Intent(this, MMcarryLogActivity.class);
    Bundle bundlerl_income = new Bundle();
    bundlerl_income.putString("carrylogMoney", carrylogMoney + "");
    intent1.putExtras(bundlerl_income);
    startActivity(intent1);
  }

  @Override protected void onResume() {
    super.onResume();
    MobclickAgent.onPageStart(this.getClass().getSimpleName());
    MobclickAgent.onResume(this);
  }

  @Override protected void onPause() {
    super.onPause();
    MobclickAgent.onPageEnd(this.getClass().getSimpleName());
    MobclickAgent.onPause(this);
  }

  @Override public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
    Log.i("Entry selected", e.toString());
    Log.i("Entry selected",
        "low: " + mChart.getLowestVisibleXIndex() + ", high: " + mChart.getHighestVisibleXIndex());
    tv_today_visit2.setText(
        Integer.toString(mPresenter.show_refund.get(e.getXIndex()).getVisitorNum()));
    //from = (MAX_RECENT_DAYS - 1 - e.getXIndex()) + "";
    if (isThisWeek) {
      from = (mPresenter.show_refund.size() - e.getXIndex() - 1) + "";
    } else {
      from = (mPresenter.show_refund.size() - e.getXIndex() - 1 + Calendar.getInstance()
          .get(Calendar.DAY_OF_WEEK) - 1) + "";
    }
    tv_today_order2.setText(
        Integer.toString(mPresenter.show_refund.get(e.getXIndex()).getOrderNum()));
    tv_today_fund2.setText(Double.toString(
        (double) (Math.round(mPresenter.show_refund.get(e.getXIndex()).getCarry() * 100)) / 100));
  }

  @Override public void onNothingSelected() {

  }
}
