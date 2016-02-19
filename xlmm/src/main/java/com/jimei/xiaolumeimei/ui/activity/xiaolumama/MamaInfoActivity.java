package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
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
import com.jimei.xiaolumeimei.entities.MamaFansBean;
import com.jimei.xiaolumeimei.entities.OneDayAgentOrdersBean;
import com.jimei.xiaolumeimei.entities.ShoppingListBean;
import com.jimei.xiaolumeimei.model.MMProductModel;
import com.jimei.xiaolumeimei.model.MamaInfoModel;
import com.jimei.xiaolumeimei.ui.activity.main.MainActivity;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import java.util.ArrayList;
import java.util.List;
import rx.schedulers.Schedulers;

/**
 * Created by wulei on 2016/2/4.
 */
public class MamaInfoActivity extends BaseSwipeBackCompatActivity
    implements View.OnClickListener, OnChartGestureListener,
    OnChartValueSelectedListener {
  String TAG = "MamaInfoActivity";
  private static final int MAX_RECENT_DAYS = 15;

  List<HisRefund> show_his_refund = new ArrayList<HisRefund>();
  int get_num = 0;

  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.btn_jump) Button btn_jump;
  @Bind(R.id.imgUser) ImageView imgUser;
  @Bind(R.id.btn_two_dimen) TextView btn_two_dimen;
  @Bind(R.id.tv_fansnum) TextView tv_fansnum;
  @Bind(R.id.tv_cash) TextView tv_cash;
  @Bind(R.id.btn_chooselist) TextView btn_chooselist;
  @Bind(R.id.btn_store) TextView btn_store;
  @Bind(R.id.chart1) LineChart mChart;

  AgentInfoBean mamaAgentInfo;
  @Bind(R.id.app_bar_layout) AppBarLayout appBarLayout;
  @Bind(R.id.tv_Point) TextView tvPoint;
  @Bind(R.id.rl_mama_info) RelativeLayout rlMamaInfo;
  @Bind(R.id.tv_order1) TextView tvOrder1;
  @Bind(R.id.tv_order2) TextView tvOrder2;
  @Bind(R.id.tv_fund1) TextView tvFund1;
  @Bind(R.id.tv_fund2) TextView tv_fund2;
  @Bind(R.id.rl_order) RelativeLayout rlOrder;
  @Bind(R.id.rl_two_dimen) RelativeLayout rlTwoDimen;
  @Bind(R.id.btn_share) TextView btnShare;
  @Bind(R.id.rl_share) RelativeLayout rlShare;
  @Bind(R.id.rl_chooselist) RelativeLayout rlChooselist;
  @Bind(R.id.rl_store) RelativeLayout rlStore;
  @Bind(R.id.rl_btn) RelativeLayout rlBtn;
  @Bind(R.id.tv_today_order2) TextView tv_today_order2;
  @Bind(R.id.tv_today_fund2) TextView tv_today_fund2;

  @Override protected void setListener() {
    btn_jump.setOnClickListener(this);
    toolbar.setOnClickListener(this);
    imgUser.setOnClickListener(this);

    btn_two_dimen.setOnClickListener(this);
    tv_fansnum.setOnClickListener(this);
    btnShare.setOnClickListener(this);

    btn_chooselist.setOnClickListener(this);
    btn_store.setOnClickListener(this);
    tvOrder1.setOnClickListener(this);
    tvFund1.setOnClickListener(this);
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
    MamaInfoModel.getInstance()
        .getAgentInfoBean()
        .subscribeOn(Schedulers.newThread())
        .subscribe(new ServiceResponse<AgentInfoBean>() {
          @Override public void onNext(AgentInfoBean pointBean) {
            JUtils.Log(TAG, "AgentInfoBean=" + pointBean.toString());
            mamaAgentInfo = pointBean;

            JUtils.Log(TAG, "cash =" + pointBean.getCash() + " all fund=" + pointBean.getMmclog().getMci());
            tv_cash.setText(Double.toString(pointBean.getCash()));
            tv_fund2.setText(Double.toString(Math.round((pointBean.getMmclog().getMci()*100)/100)));
          }
        });

    MamaInfoModel.getInstance().getMamaFans()
        .subscribeOn(Schedulers.newThread())
        .subscribe(new ServiceResponse<List<MamaFansBean>>() {
          @Override public void onNext(List<MamaFansBean> fansBeen) {
            JUtils.Log(TAG,"size ="+ fansBeen.size());
            tv_fansnum.setText("我的粉丝 " + fansBeen.size());
            JUtils.Log(TAG, "fans num =" + fansBeen.size());
          }
        });

    MMProductModel.getInstance()
            .getShoppingList("1")
            .subscribeOn(Schedulers.io())
            .subscribe(new ServiceResponse<ShoppingListBean>() {
              @Override public void onNext(ShoppingListBean shoppingListBean) {
                super.onNext(shoppingListBean);
                if (shoppingListBean != null) {
                  int count = shoppingListBean.getCount();
                  JUtils.Log(TAG, "all orders num =" + shoppingListBean.getCount());
                  tvOrder2.setText(Integer.toString(shoppingListBean.getCount()));
                }
              }
            });

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
      case R.id.btn_jump:
        startActivity(new Intent(MamaInfoActivity.this, MainActivity.class));
        finish();
        break;
      case R.id.btn_two_dimen:
        intent = new Intent(MamaInfoActivity.this, TwoDimenCodeActivity.class);
        intent.putExtra("myurl", mamaAgentInfo.getShare_mmcode());
        startActivity(intent);

        break;
      case R.id.imgUser:
        intent = new Intent(MamaInfoActivity.this, WithdrawCashActivity.class);
        intent.putExtra("cash", mamaAgentInfo.getCash());

        startActivity(intent);
        break;
      case R.id.tv_fansnum:
        startActivity(new Intent(MamaInfoActivity.this, MamaFansActivity.class));
        break;

      case R.id.btn_share:
        startActivity(new Intent(MamaInfoActivity.this, ShareAllowanceActivity.class));
        break;

      case R.id.btn_chooselist:
        //startActivity(new Intent(MamaInfoActivity.this, MMChooseListActivity.class));
        break;

      case R.id.btn_store:
        //startActivity(new Intent(MamaInfoActivity.this, MaMaMyStoreActivity.class));
        break;
      case R.id.tv_order1:
        startActivity(new Intent(MamaInfoActivity.this, MMShoppingListActivity.class));
        break;
      case R.id.tv_fund1:
        startActivity(new Intent(MamaInfoActivity.this, MMCarryLogListActivity.class));
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
    mChart.setNoDataTextDescription("您暂无订单收益!");
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
    // mChart.setBackgroundColor(Color.GRAY);

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
    llXAxis.setTextSize(10f);

    XAxis xAxis = mChart.getXAxis();

    Typeface tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

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
    mChart.getXAxis().setDrawGridLines(false);
    mChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

    mChart.getXAxis().setEnabled(false);
    mChart.getAxisRight().setEnabled(false);


    // add data


  }

  private void setData(int count) {

    ArrayList<String> xVals = new ArrayList<String>();
    for (int i = 0; i < count; i++) {
      xVals.add((i) + "");
    }

    ArrayList<Entry> yVals = new ArrayList<Entry>();

    for (int i = 0; i < count; i++) {

      //float val = (float) (Math.random() * 100) + 3;
      float val = 0;
      if(show_his_refund.size() > 0) {
        val = show_his_refund.get(i).getOrder_num();
      }
      yVals.add(new Entry(val, i));
    }

    // create a dataset and give it a type
    LineDataSet set1 = new LineDataSet(yVals, "");
    // set1.setFillAlpha(110);
    // set1.setFillColor(Color.RED);

    // set the line to be drawn like this "- - - - - -"
    set1.enableDashedLine(20f, 5f, 0f);
    set1.enableDashedHighlightLine(20f, 5f, 0f);
    set1.setColor(R.color.colorAccent);
    set1.setCircleColor(R.color.colorAccent);
    set1.setLineWidth(1f);
    set1.setCircleRadius(3f);
    set1.setDrawCircleHole(false);
    set1.setValueTextSize(9f);
    //Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_red);
    //set1.setFillDrawable(drawable);
    //set1.setDrawFilled(true);

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
    Log.i("", "low: "
        + mChart.getLowestVisibleXIndex()
        + ", high: "
        + mChart.getHighestVisibleXIndex());
    tv_today_order2.setText(Integer.toString((int)(e.getVal())));
    tv_today_fund2.setText(Float.toString(Math.round((show_his_refund.get(e.getXIndex()).getRefund()*100)/100)));
  }

  @Override public void onNothingSelected() {
    Log.i("Nothing selected", "Nothing selected.");
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // TODO: add setContentView(...) invocation
    ButterKnife.bind(this);
  }

  final static class HisRefund {
    int order_num;
    float refund;

    public int getOrder_num() {
      return order_num;
    }

    public float getRefund() {
      return refund;
    }

    public void setOrder_num(int order_num) {
      this.order_num = order_num;
    }

    public void setRefund(float refund) {
      this.refund = refund;
    }
  }

  void get_his_refund(){
    for(int i=0; i< MAX_RECENT_DAYS; i++){
      HisRefund hisRefund = new HisRefund();
      hisRefund.setOrder_num(0);
      hisRefund.setRefund(0);
      show_his_refund.add(i, hisRefund);
    }

    for(int i=0; i< MAX_RECENT_DAYS; i++){
      JUtils.Log(TAG, " day  =" + (MAX_RECENT_DAYS - 1 - i));
      final int finalI = i;

      MMProductModel.getInstance()
          .getOneDayAgentOrders(MAX_RECENT_DAYS - 1 - i)
          .subscribeOn(Schedulers.io())
          .subscribe(new ServiceResponse<OneDayAgentOrdersBean>() {
            @Override public void onNext(OneDayAgentOrdersBean oneDayBean) {
              super.onNext(oneDayBean);
              if (oneDayBean != null) {
                int count = oneDayBean.getShops().size();
                HisRefund hisRefund = new HisRefund();
                hisRefund.setOrder_num(count);
                hisRefund.setRefund(calc_refund(oneDayBean.getShops()));
                JUtils.Log(TAG, "finalI=" + finalI + " one day  orders num =" + hisRefund.getOrder_num() + " "
                    + "refund= " + hisRefund.getRefund());

                synchronized (this) {
                  show_his_refund.set(finalI, hisRefund);
                  get_num++;
                }
                JUtils.Log(TAG, "get_num =" + get_num + " "
                        + "size= " + show_his_refund.size());
                if((get_num == MAX_RECENT_DAYS - 1) && (show_his_refund.size() > 0)){
                  init_chart();
                  setData(MAX_RECENT_DAYS);
                  mChart.setVisibility(View.VISIBLE);

                  mChart.animateX(2500, Easing.EasingOption.EaseInOutQuart);
                  mChart.setVisibleXRangeMaximum(6);

                  if(show_his_refund.size() > 7) {
                    mChart.moveViewToX(MAX_RECENT_DAYS - 6);
                  }
                  if(show_his_refund.get(0) != null) {
                    tv_today_order2.setText(Float.toString(show_his_refund.get(0).getOrder_num()));
                    tv_today_fund2.setText(Float.toString(Math.round((show_his_refund.get(0).getRefund()*100)/100)));
                  }
                }
              }
            }
          });
    }
  }
  float calc_refund(List<OneDayAgentOrdersBean.ShopsEntity> list){
    float sum = 0;
    if(list == null) return 0;
    for(int i = 0; i < list.size(); i++){
      sum+= list.get(i).getTicheng_cash();
    }
    return sum;
  }
}