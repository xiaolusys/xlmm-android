package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.AgentInfoBean;
import com.jimei.xiaolumeimei.entities.MMShoppingBean;
import com.jimei.xiaolumeimei.entities.MamaFortune;
import com.jimei.xiaolumeimei.entities.RecentCarryBean;
import com.jimei.xiaolumeimei.model.MMProductModel;
import com.jimei.xiaolumeimei.model.MamaInfoModel;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * Created by wulei on 2016/2/4.
 */
public class MamaInfoActivity extends BaseSwipeBackCompatActivity
    implements View.OnClickListener, OnChartGestureListener,
    OnChartValueSelectedListener {
  private static final int MAX_RECENT_DAYS = 15;
  String TAG = "MamaInfoActivity";
  List<RecentCarryBean.ResultsEntity> his_refund = new ArrayList<>();
  List<RecentCarryBean.ResultsEntity> show_refund = new ArrayList<>();
  int get_num = 0;

  private String title, sharelink, desc, shareimg;
  private SharedPreferences sharedPreferences;
  private String cookies;
  private String domain;

  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.imgUser) ImageView imgUser;
  @Bind(R.id.btn_two_dimen) TextView btn_two_dimen;
  @Bind(R.id.tv_cashinfo) TextView tv_cashinfo;
  @Bind(R.id.tv_cash) TextView tv_cash;
  @Bind(R.id.btn_chooselist) TextView btn_chooselist;

  @Bind(R.id.tv_liveness) TextView tv_liveness;
  @Bind(R.id.img_liveness) com.jimei.xiaolumeimei.widget.RotateTextView img_liveness;
  @Bind(R.id.pb_hook) ProgressBar mProgressBar;

  @Bind(R.id.chart1) LineChart mChart;
  @Bind(R.id.img_left) ImageView img_left;
  @Bind(R.id.img_right) ImageView img_right;
  @Bind(R.id.rl_empty_chart) RelativeLayout rl_empty_chart;

  @Bind(R.id.tv_visit2) TextView tv_today_visit2;
  @Bind(R.id.tv_visit1) TextView tv_today_visit1;
  @Bind(R.id.tv_today_order2) TextView tv_today_order2;
  @Bind(R.id.tv_today_order1) TextView tv_today_order1;
  @Bind(R.id.tv_today_fund2) TextView tv_today_fund2;
  @Bind(R.id.tv_today_fund1) TextView tv_today_fund1;

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

  AgentInfoBean mamaAgentInfo;
  MamaFortune mamaFortune;
  private double carrylogMoney;
  private String s;
  private String from;
  private String actlink;
  private String shareMmcode;


  @Override protected void setListener() {
    toolbar.setOnClickListener(this);
    tv_cashinfo.setOnClickListener(this);
    tv_cash.setOnClickListener(this);

    tv_liveness.setOnClickListener(this);
    img_liveness.setOnClickListener(this);

    img_left.setOnClickListener(this);
    img_right.setOnClickListener(this);

    rlTwoDimen.setOnClickListener(this);
    rl_fans.setOnClickListener(this);
    rl_orderlist.setOnClickListener(this);
    rl_income.setOnClickListener(this);

    rlChooselist.setOnClickListener(this);
    rl_party.setOnClickListener(this);
    rl_push.setOnClickListener(this);
    rl_shop.setOnClickListener(this);
    tv_today_order1.setOnClickListener(this);
    tv_today_fund1.setOnClickListener(this);
    tv_today_visit1.setOnClickListener(this);
  }

  @Override protected void getBundleExtras(Bundle extras) {

  }

  @Override protected int getContentViewLayoutID() {
    return R.layout.activity_mamainfo;
  }

  @Override protected void initViews() {
    toolbar.setTitle("");
    setSupportActionBar(toolbar);
    finishBack(toolbar);
  }

  @Override protected void initData() {

    MMProductModel.getInstance()
            .getShareShopping()
            .subscribeOn(Schedulers.io())
            .subscribe(new ServiceResponse<MMShoppingBean>() {

              @Override public void onNext(MMShoppingBean mmShoppingBean) {

                if (null != mmShoppingBean) {
                  title = (String) mmShoppingBean.getShopInfo().getName();
                  sharelink = mmShoppingBean.getShopInfo().getPreview_shop_link();
                  shareimg = mmShoppingBean.getShopInfo().getThumbnail();
                  desc = mmShoppingBean.getShopInfo().getDesc();
                }
              }
            });

    Subscription subscribe3 = MamaInfoModel.getInstance()
        .getMamaFortune()
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<MamaFortune>() {
          @Override public void onNext(MamaFortune fortune) {
            JUtils.Log(TAG, "fortune=" + fortune.toString());

            JUtils.Log(TAG, "cash ="
                + fortune.getMama_fortune().getCash_value()
                + " all fund="
                + fortune.getMama_fortune().getCarry_value());
            actlink = fortune.getMama_fortune().getmMamaEventLink();
            mamaFortune = fortune;
            tv_cash.setText(Double.toString(
                (double) (Math.round(fortune.getMama_fortune().getCash_value() * 100))
                    / 100));
            tv_fund.setText(Double.toString(
                (double) (Math.round(fortune.getMama_fortune().getCarry_value() * 100))
                    / 100) + "元");

            carrylogMoney =
                ((double) (Math.round(fortune.getMama_fortune().getCarry_value() * 100))
                    / 100);

            tv_invite_num.setText(fortune.getMama_fortune().getInvite_num() + "位");
            tv_fansnum.setText(fortune.getMama_fortune().getFans_num() + "人");
            JUtils.Log(TAG, "fans num =" + fortune.getMama_fortune().getFans_num());

            show_liveness(fortune.getMama_fortune().getActive_value_num());
            JUtils.Log(TAG,
                "all orders num =" + fortune.getMama_fortune().getOrder_num());
            MamaInfoActivity.this.s =
                Integer.toString(fortune.getMama_fortune().getOrder_num());
            tv_order.setText(MamaInfoActivity.this.s + "个");
          }
        });
    addSubscription(subscribe3);

    Subscription subscribe = MamaInfoModel.getInstance()
        .getAgentInfoBean()
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<AgentInfoBean>() {
          @Override public void onNext(AgentInfoBean pointBean) {

            if (null != pointBean) {
               shareMmcode = pointBean.getShareMmcode();
              mamaAgentInfo = pointBean;
            }
          }
        });
    addSubscription(subscribe);


    /*Subscription subscribe1 = MMProductModel.getInstance()
        .getShoppingList("1")
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<ShoppingListBean>() {
          @Override public void onNext(ShoppingListBean shoppingListBean) {
            super.onNext(shoppingListBean);
            if (shoppingListBean != null) {
              JUtils.Log(TAG, "all orders num =" + shoppingListBean.getCount());
              tv_order.setText(Integer.toString(shoppingListBean.getCount()) + "个订单");
            }
          }
        });

    addSubscription(subscribe1);*/
    get_his_refund();
  }

  @Override protected boolean toggleOverridePendingTransition() {
    return false;
  }

  @Override protected TransitionMode getOverridePendingTransitionMode() {
    return null;
  }

  @Override public void onClick(View v) {
    Intent intent;
    switch (v.getId()) {
      case R.id.tv_cash:
      case R.id.tv_cashinfo:
        if (mamaFortune != null) {
          intent = new Intent(MamaInfoActivity.this, MamaWithdrawCashActivity.class);
          intent.putExtra("cash", mamaFortune.getMama_fortune().getCash_value());

          startActivity(intent);
        }
        break;
      case R.id.tv_liveness:
      case R.id.img_liveness:
        if (mamaFortune != null) {
          intent = new Intent(MamaInfoActivity.this, MamaLivenessActivity.class);
          intent.putExtra("liveness",
              mamaFortune.getMama_fortune().getActive_value_num());

          startActivity(intent);
        }
        break;
      case R.id.img_left:
        rl_empty_chart.setVisibility(View.INVISIBLE);
        mChart.clear();
        setDataOfPreviousWeek();
        tv_today_visit2.setText(
            Integer.toString(show_refund.get(show_refund.size() - 1).getVisitorNum()));
        tv_today_order2.setText(Integer.toString(
            (int) (show_refund.get(show_refund.size() - 1).getOrderNum())));
        tv_today_fund2.setText(Double.toString((double) (Math.round(
            show_refund.get(show_refund.size() - 1).getCarry() * 100)) / 100));
        break;
      case R.id.img_right:
        rl_empty_chart.setVisibility(View.INVISIBLE);
        mChart.clear();
        setDataOfThisWeek();
        tv_today_visit2.setText(
            Integer.toString(show_refund.get(show_refund.size() - 1).getVisitorNum()));
        tv_today_order2.setText(Integer.toString(
            (int) (show_refund.get(show_refund.size() - 1).getOrderNum())));
        tv_today_fund2.setText(Double.toString((double) (Math.round(
            show_refund.get(show_refund.size() - 1).getCarry() * 100)) / 100));
        break;
      case R.id.rl_chooselist:
        startActivity(new Intent(MamaInfoActivity.this, MMChooseListActivity.class));
        break;
      case R.id.rl_party:
        intent = new Intent(this, BoutiqueWebviewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("actlink", actlink);
        intent.putExtras(bundle);
        startActivity(intent);
        break;
      case R.id.rl_push:
        startActivity(new Intent(MamaInfoActivity.this, MMNinePicActivity.class));
        break;
      case R.id.rl_shop:

        Intent intentrl_shop = new Intent(this, MMWebViewActivity.class);
        sharedPreferences = getSharedPreferences("xlmmCookiesAxiba", Context.MODE_PRIVATE);
        cookies = sharedPreferences.getString("cookiesString", "");
        domain = sharedPreferences.getString("cookiesDomain", "");

        Bundle bundlerl_shop = new Bundle();
        bundlerl_shop.putString("cookies", cookies);
        bundlerl_shop.putString("domain", domain);
        bundlerl_shop.putString("Cookie", sharedPreferences.getString("Cookie", ""));
        bundlerl_shop.putString("link", sharelink);
        intentrl_shop.putExtras(bundlerl_shop);
        startActivity(intentrl_shop);

//        startActivity(new Intent(MamaInfoActivity.this, MaMaMyStoreActivity.class));
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
        bundlerl_two_dimen.putString("link", shareMmcode);
        intentrl_two_dimen.putExtras(bundlerl_two_dimen);
        startActivity(intentrl_two_dimen);
        break;
      case R.id.rl_fans:
        startActivity(new Intent(MamaInfoActivity.this, MamaFansActivity.class));
        break;
      case R.id.rl_orderlist:
        Intent intent2 = new Intent(MamaInfoActivity.this, MMShoppingListActivity.class);
        Bundle bundle1 = new Bundle();
        bundle1.putString("order", s);
        intent2.putExtras(bundle1);
        startActivity(intent2);
        break;
      case R.id.tv_today_order1:
        Intent intent3 = new Intent(MamaInfoActivity.this, MMShoppingListActivity.class);
        Bundle bundle3 = new Bundle();
        bundle3.putString("order", s);
        intent3.putExtras(bundle3);
        startActivity(intent3);
        break;
      case R.id.rl_income:
        Intent intent1 = new Intent(MamaInfoActivity.this, MMcarryLogActivity.class);
        Bundle bundlerl_income = new Bundle();
        bundlerl_income.putString("carrylogMoney", carrylogMoney + "");
        intent1.putExtras(bundlerl_income);
        startActivity(intent1);
        //startActivity(new Intent(MamaInfoActivity.this, MMCarryLogListActivity.class));
        break;
      case R.id.tv_today_fund1:
        Intent intent4 = new Intent(MamaInfoActivity.this, MMcarryLogActivity.class);
        Bundle bundle4 = new Bundle();
        bundle4.putString("carrylogMoney", carrylogMoney + "");
        intent4.putExtras(bundle4);
        startActivity(intent4);
        //startActivity(new Intent(MamaInfoActivity.this, MMCarryLogListActivity.class));
        break;
      //case R.id.rl_share:
      //  startActivity(new Intent(MamaInfoActivity.this, ShareAllowanceActivity.class));
      //  break;

      case R.id.tv_visit1:
        Intent intent5 =
            new Intent(new Intent(MamaInfoActivity.this, MamaVisitorActivity.class));
        Bundle bundle2 = new Bundle();
        bundle2.putString("from", from);
        intent5.putExtras(bundle2);
        startActivity(intent5);
        break;
    }
  }

  private void init_chart() {

    //mChart.setOnChartGestureListener(this);
    mChart.setOnChartValueSelectedListener(this);
    mChart.setDrawGridBackground(false);

    // no description text
    mChart.setDescription("");
    mChart.setNoDataText("");
    //mChart.setNoDataTextDescription("您暂无订单收益!");
    //mChart.invalidate();

    // enable touch gestures
    mChart.setTouchEnabled(true);

    // enable scaling and dragging
    mChart.setDragEnabled(true);
    mChart.setScaleEnabled(false);
    // mChart.setScaleXEnabled(true);
    // mChart.setScaleYEnabled(true);

    // if disabled, scaling can be done on x- and y-axis separately
    //mChart.setPinchZoom(true);

    // set an alternative background color
    mChart.setBackgroundColor(Color.WHITE);

    // create a custom MarkerView (extend MarkerView) and specify the layout
    // to use for it
    //MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);

    // set the marker to the chart
    //mChart.setMarkerView(mv);

    // x-axis limit line
    /*LimitLine llXAxis = new LimitLine(10f, "Index 10");
    llXAxis.setLineWidth(4f);
    llXAxis.enableDashedLine(10f, 10f, 0f);
    llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
    llXAxis.setTextSize(10f);*/

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


    /*Typeface tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

    LimitLine ll1 = new LimitLine(130f, "Upper Limit");
    ll1.setLineWidth(4f);
    ll1.enableDashedLine(10f, 10f, 0f);
    ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
    ll1.setTextSize(10f);
    ll1.setTypeface(tf);

    LimitLine ll2 = new LimitLine(-30f, "Lower Limit");
    ll2.setLineWidth(4f);
    ll2.enableDashedLine(10f, 10f, 0f);
    ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
    ll2.setTextSize(10f);
    ll2.setTypeface(tf);*/

    //YAxis leftAxis = mChart.getAxisLeft();
    //leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
    //leftAxis.addLimitLine(ll1);
    //leftAxis.addLimitLine(ll2);
    //leftAxis.setAxisMaxValue(220f);
    //leftAxis.setAxisMinValue(-50f);
    //leftAxis.setYOffset(20f);
    //leftAxis.enableGridDashedLine(10f, 10f, 0f);
    //leftAxis.setDrawZeroLine(false);

    // limit lines are drawn behind data (and not on top)
    //leftAxis.setDrawLimitLinesBehindData(true);

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

  private void setData(ArrayList<String> xVals, ArrayList<Entry> yVals) {

    // create a dataset and give it a type
    LineDataSet set1 = new LineDataSet(yVals, "");
    // set1.setFillAlpha(110);
    // set1.setFillColor(Color.RED);

    // set the line to be drawn like this "- - - - - -"
    //set1.enableDashedLine(20f, 5f, 0f);
    //set1.enableDashedHighlightLine(20f, 5f, 0f);
    set1.setColor(Color.parseColor("#F5B123"));
    set1.setCircleColor(Color.parseColor("#F5B123"));
    set1.setLineWidth(1f);
    set1.setCircleRadius(2f);
    set1.setDrawCircleHole(false);
    set1.setValueTextSize(9f);

    set1.setHighlightLineWidth(1.75f); // 线宽
    set1.setHighLightColor(Color.parseColor("#F5B123")); // 高亮的线的颜色
    //Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_red);
    //set1.setFillDrawable(drawable);
    //set1.setDrawFilled(true);
    set1.setDrawHorizontalHighlightIndicator(false);
    set1.setDrawValues(true);

    set1.setValueFormatter(new ValueFormatter() {
      @Override
      public String getFormattedValue(float value, Entry entry, int dataSetIndex,
          ViewPortHandler viewPortHandler) {
        return "";
      }
    });

    ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
    dataSets.add(set1); // add the datasets

    // create a data object with the datasets
    LineData data = new LineData(xVals, dataSets);

    // set data
    mChart.setData(data);
    mChart.setVisibility(View.VISIBLE);

    mChart.animateX(2500, Easing.EasingOption.EaseInOutQuart);
    mChart.setVisibleXRangeMaximum(6);

    /*if (show_his_refund.size() > 7) {
      mChart.moveViewToX(MAX_RECENT_DAYS - 6);
    }
    Calendar cal= Calendar.getInstance();
    JUtils.Log(TAG, "DAY_OF_WEEK:"+ cal.get(Calendar.DAY_OF_WEEK));
    if(cal.get(Calendar.DAY_OF_WEEK) == 1) {
      mChart.moveViewToX(MAX_RECENT_DAYS - 6);
    }
    else{
      mChart.moveViewToX(MAX_RECENT_DAYS - 6 + 8 - cal.get(Calendar.DAY_OF_WEEK));
    }*/
  }

  private void setDataOfThisWeek() {

    ArrayList<String> xVals =
        new ArrayList<String>(Arrays.asList("一", "二", "三", "四", "五", "六", "日"));

    Calendar cal = Calendar.getInstance();
    int days = 0;
    if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
      days = 7;
    } else {
      days = cal.get(Calendar.DAY_OF_WEEK) - 1;
    }
    JUtils.Log(TAG,
        "DAY_OF_WEEK:" + cal.get(Calendar.DAY_OF_WEEK) + " ,get days " + days);

    show_refund.clear();
    ArrayList<Entry> yVals = new ArrayList<Entry>();
    for (int i = 0; i < days; i++) {
      float val = 0;
      if (his_refund.size() > 0) {
        val = (float) his_refund.get(MAX_RECENT_DAYS - days + i).getCarry();
        JUtils.Log(TAG,"val===="+val);
        show_refund.add(his_refund.get(MAX_RECENT_DAYS - days + i));
      }
      yVals.add(new Entry(val, i));
    }

    // set data

      setData(xVals, yVals);
    if (isEmptyData(show_refund)) {
      rl_empty_chart.setVisibility(View.VISIBLE);
    }
  }

  private void setDataOfPreviousWeek() {

    Calendar cal = Calendar.getInstance();
    int month = cal.get(Calendar.MONTH) + 1;//得到月，因为从0开始的，所以要加1
    int day = cal.get(Calendar.DAY_OF_MONTH);//得到天
    int days = 0;
    if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
      days = 7;
    } else {
      days = cal.get(Calendar.DAY_OF_WEEK) - 1;
    }
    JUtils.Log(TAG,
        "DAY_OF_WEEK:" + cal.get(Calendar.DAY_OF_WEEK) + " ,get days " + days);
    cal.add(Calendar.DAY_OF_YEAR, 0 - days - 7);//日期减days天数

    ArrayList<String> xVals = new ArrayList<String>();
    for (int i = 0; i < 7; i++) {
      cal.add(Calendar.DAY_OF_YEAR, 1);//日期+1
      xVals.add(
          "" + (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.DAY_OF_MONTH));
      //JUtils.Log(TAG,
      //    "DAY: " + (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.DAY_OF_MONTH));
    }

    show_refund.clear();
    ArrayList<Entry> yVals = new ArrayList<Entry>();
    for (int i = 0; i < 7; i++) {
      float val = 0;
      if (his_refund.size() > 0) {
        val = (float) his_refund.get(MAX_RECENT_DAYS - days + i - 7).getCarry();
        show_refund.add(his_refund.get(MAX_RECENT_DAYS - days + i - 7));
      }
      yVals.add(new Entry(val, i));
    }

    // set data

      setData(xVals, yVals);
    if (isEmptyData(show_refund)) {
      rl_empty_chart.setVisibility(View.VISIBLE);
    }
  }

  @Override public void onChartGestureStart(MotionEvent me,
      ChartTouchListener.ChartGesture lastPerformedGesture) {
    Log.i("Gesture", "START");
  }

  @Override public void onChartGestureEnd(MotionEvent me,
      ChartTouchListener.ChartGesture lastPerformedGesture) {
    Log.i("Gesture", "END, lastGesture: " + lastPerformedGesture);

    // un-highlight values after the gesture is finished and no single-tap
    if (lastPerformedGesture != ChartTouchListener.ChartGesture.SINGLE_TAP) {
      mChart.highlightValues(
          null); // or highlightTouch(null) for callback to onNothingSelected(...)
    }
  }

  @Override public void onChartLongPressed(MotionEvent me) {
    Log.i("LongPress", "Chart longpressed.");
  }

  @Override public void onChartDoubleTapped(MotionEvent me) {
    Log.i("DoubleTap", "Chart double-tapped.");
  }

  @Override public void onChartSingleTapped(MotionEvent me) {
    Log.i("SingleTap", "Chart single-tapped.");
  }

  @Override public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX,
      float velocityY) {
    Log.i("Fling", "Chart flinged. VeloX: " + velocityX + ", VeloY: " + velocityY);
  }

  @Override public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
    Log.i("Scale / Zoom", "ScaleX: " + scaleX + ", ScaleY: " + scaleY);
  }

  @Override public void onChartTranslate(MotionEvent me, float dX, float dY) {
    Log.i("Translate / Move", "dX: " + dX + ", dY: " + dY);
  }

  @Override public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
    Log.i("Entry selected", e.toString());
    Log.i("Entry selected", "low: "
        + mChart.getLowestVisibleXIndex()
        + ", high: "
        + mChart.getHighestVisibleXIndex());
    tv_today_visit2.setText(
        Integer.toString(show_refund.get(e.getXIndex()).getVisitorNum()));
    from = (MAX_RECENT_DAYS - 1 - e.getXIndex()) + "";
    JUtils.Log(TAG, "第" + e.getXIndex() + "个");
    tv_today_order2.setText(
        Integer.toString((int) (show_refund.get(e.getXIndex()).getOrderNum())));
    tv_today_fund2.setText(Double.toString(
        (double) (Math.round(show_refund.get(e.getXIndex()).getCarry() * 100)) / 100));

    /*if (Double.compare(show_his_refund.get(e.getXIndex()).getRefund(), 0) == 0) {
      tv_today_fund2.setText(Float.toString(
          (float) (Math.round(show_his_refund.get(e.getXIndex()).getRefund() * 100))
              / 100));

      Subscription subscribe = MMProductModel.getInstance()
          .getOneDayAgentOrders(MAX_RECENT_DAYS - 1 - e.getXIndex())
          .subscribeOn(Schedulers.io())
          .subscribe(new ServiceResponse<OneDayAgentOrdersBean>() {
            @Override public void onNext(OneDayAgentOrdersBean oneDayBean) {
              super.onNext(oneDayBean);
              if (oneDayBean != null) {
                show_his_refund.get(e.getXIndex())
                    .setRefund(oneDayBean.getShops().get(0).getDayly_amount());

                JUtils.Log(TAG, "incom= " + oneDayBean.getShops()
                    .get(e.getXIndex())
                    .getDayly_amount());
                if ((show_his_refund.get(e.getXIndex()) != null)) {
                  tv_today_order2.setText(
                      Float.toString(show_his_refund.get(e.getXIndex()).getOrder_num()));
                  tv_today_fund2.setText(Float.toString((float) (Math.round(
                      show_his_refund.get(e.getXIndex()).getRefund() * 100)) / 100));
                }
              }
            }
          });
      addSubscription(subscribe);
    } else {
      tv_today_fund2.setText(Float.toString(
          (float) (Math.round(show_his_refund.get(e.getXIndex()).getRefund() * 100))
              / 100));
    }*/
  }

  @Override public void onNothingSelected() {
    Log.i("Nothing selected", "Nothing selected.");
  }

  void get_his_refund() {
    for (int i = 0; i < MAX_RECENT_DAYS; i++) {
      RecentCarryBean.ResultsEntity hisRefund = new RecentCarryBean.ResultsEntity();
      hisRefund.setVisitorNum(0);
      hisRefund.setOrderNum(0);
      hisRefund.setCarry(0.0);
      his_refund.add(i, hisRefund);
    }

    Subscription subscribe = MMProductModel.getInstance()
        .getRecentCarry("0", Integer.toString(MAX_RECENT_DAYS))
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<RecentCarryBean>() {
          @Override public void onNext(RecentCarryBean recentDayBean) {
            super.onNext(recentDayBean);
            if (recentDayBean != null) {

              his_refund.clear();
              his_refund.addAll(recentDayBean.getResults());

              JUtils.Log(TAG, "get_num =" + get_num + " " + "size= " + his_refund.size());
              if ((his_refund.size() > 0)) {
                init_chart();
                setDataOfThisWeek();

                if (his_refund.get(0) != null) {
                  tv_today_visit2.setText(Integer.toString(
                      his_refund.get(his_refund.size() - 1).getVisitorNum()));
                  tv_today_order2.setText(Integer.toString(
                      his_refund.get(his_refund.size() - 1).getOrderNum()));
                  tv_today_fund2.setText(Double.toString((double) (Math.round(
                      his_refund.get(his_refund.size() - 1).getCarry() * 100)) / 100));
                }
              }
            }
          }
        });
    addSubscription(subscribe);
    /*for (int i = 0; i < MAX_RECENT_DAYS; i++) {
      HisRefund hisRefund = new HisRefund();
      hisRefund.setOrder_num(0);
      hisRefund.setRefund(0);
      show_his_refund.add(i, hisRefund);
    }

    Subscription subscribe = MMProductModel.getInstance()
        .getLatestAgentOrders(MAX_RECENT_DAYS)
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<List<Integer>>() {
          @Override public void onNext(List<Integer> oneDayBean) {
            super.onNext(oneDayBean);
            if (oneDayBean != null) {
              for (int i = 0; i < MAX_RECENT_DAYS; i++) {
                show_his_refund.get(MAX_RECENT_DAYS - 1 - i)
                    .setOrder_num(oneDayBean.get(i));
              }

              JUtils.Log(TAG,
                  "get_num =" + get_num + " " + "size= " + show_his_refund.size());
              if ((show_his_refund.size() > 0)) {
                init_chart();
                setData(MAX_RECENT_DAYS);
                mChart.setVisibility(View.VISIBLE);

                mChart.animateX(2500, Easing.EasingOption.EaseInOutQuart);
                mChart.setVisibleXRangeMaximum(6);

                if (show_his_refund.size() > 7) {
                  mChart.moveViewToX(MAX_RECENT_DAYS - 6);
                }
                if (show_his_refund.get(0) != null) {
                  tv_today_order2.setText(
                      Integer.toString(show_his_refund.get(0).getOrder_num()));
                  tv_today_fund2.setText(Float.toString(
                      (float) (Math.round(show_his_refund.get(0).getRefund() * 100))
                          / 100));
                }
              }
            }
          }
        });
    addSubscription(subscribe);

    for (int i = 0; i < MAX_RECENT_DAYS; i++) {
      JUtils.Log(TAG, " day  =" + (MAX_RECENT_DAYS - 1 - i));
      get_his_one_day_refund(i);
    }*/
  }

  /*void get_his_one_day_refund(int day) {
    final int finalI = day;
    Subscription subscribe = MMProductModel.getInstance()
        .getOneDayAgentOrders(MAX_RECENT_DAYS - 1 - day)
        .subscribeOn(Schedulers.io())
        .subscribe(new ServiceResponse<OneDayAgentOrdersBean>() {
          @Override public void onNext(OneDayAgentOrdersBean oneDayBean) {
            super.onNext(oneDayBean);
            if (oneDayBean != null) {
              synchronized (this) {
                show_his_refund.get(finalI)
                    .setRefund(oneDayBean.getShops().get(0).getDayly_amount());
              }
              JUtils.Log(TAG, "incom= " + oneDayBean.getShops().get(0).getDayly_amount());
              if ((show_his_refund.get(0) != null) && (finalI == 0)) {
                tv_today_order2.setText(
                    Integer.toString(show_his_refund.get(0).getOrder_num()));
                tv_today_fund2.setText(Float.toString(
                    (float) (Math.round(show_his_refund.get(0).getRefund() * 100))
                        / 100));
              }
            }
          }
        });
    addSubscription(subscribe);
  }

  float calc_refund(List<OneDayAgentOrdersBean.ShopsEntity> list) {
    float sum = 0;
    if (list == null) return 0;
    for (int i = 0; i < list.size(); i++) {
      sum += list.get(i).getTicheng_cash();
    }
    return sum;
  }*/

  @Override protected void onStop() {
    super.onStop();
  }

  private void show_liveness(int liveness) {
    JUtils.Log(TAG, "liveness:" + liveness);
    img_liveness.setText(Integer.toString(liveness));
    if (liveness > 100) liveness = 100;
    mProgressBar.setProgress(liveness);
    mProgressBar.setVisibility(View.VISIBLE);

    /*AutoRelativeLayout.LayoutParams laParams=(AutoRelativeLayout.LayoutParams)img_hook
        .getLayoutParams();
    laParams.width= getWindowManager().getDefaultDisplay().getWidth() *
    liveness/ 100;
    img_hook.setLayoutParams(laParams);*/

    //AutoRelativeLayout.LayoutParams laParams1=(AutoRelativeLayout.LayoutParams)
    //    img_liveness.getLayoutParams();
    AutoRelativeLayout.LayoutParams laParams1 =
        new AutoRelativeLayout.LayoutParams(AutoRelativeLayout.LayoutParams.WRAP_CONTENT,
            AutoRelativeLayout.LayoutParams.WRAP_CONTENT);
    JUtils.Log(TAG, "" + laParams1.leftMargin + " " + laParams1.height);
    JUtils.Log(TAG, "show_liveness left:"
        + getWindowManager().getDefaultDisplay().getWidth() * liveness / 100
        + "width:"
        + img_liveness.getWidth());
    //laParams1.leftMargin = getWindowManager().getDefaultDisplay().getWidth() *
    //    liveness/ 100 - laParams1.width;

    laParams1.setMargins(
        getWindowManager().getDefaultDisplay().getWidth() * liveness / 100
            - img_liveness.getWidth(), rlMamaInfo.getHeight() - img_liveness.getHeight(),
        0, 0);

    img_liveness.setLayoutParams(laParams1);
  }

  private boolean isEmptyData(List<RecentCarryBean.ResultsEntity> list_refund) {
    boolean result = true;
    if (list_refund != null) {
      for (int i = 0; i < list_refund.size(); i++) {
        if (Double.compare(list_refund.get(i).getCarry(), 0) != 0) {
          result = false;
          break;
        }
      }
    }
    return result;
  }
}