package com.jimei.xiaolumeimei.ui.fragment.mminfo;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.jimei.library.utils.DateUtils;
import com.jimei.library.utils.JUtils;
import com.jimei.library.widget.badgelib.BadgeView;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.ActivityListAdapter;
import com.jimei.xiaolumeimei.base.BaseBindingFragment;
import com.jimei.xiaolumeimei.base.CommonWebViewActivity;
import com.jimei.xiaolumeimei.databinding.FragmentMamaFirstBinding;
import com.jimei.xiaolumeimei.entities.MMShoppingBean;
import com.jimei.xiaolumeimei.entities.MamaFortune;
import com.jimei.xiaolumeimei.entities.MamaSelfListBean;
import com.jimei.xiaolumeimei.entities.MamaUrl;
import com.jimei.xiaolumeimei.entities.PortalBean;
import com.jimei.xiaolumeimei.entities.RecentCarryBean;
import com.jimei.xiaolumeimei.model.MMInfoModel;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.DayPushActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.GoodWeekActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MMShareCodeWebViewActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MMShoppingListActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MMStoreWebViewActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MMcarryLogActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MamaActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MamaChooseActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MamaReNewActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MamaVisitorActivity;
import com.jimei.xiaolumeimei.utils.JumpUtils;
import com.jimei.xiaolumeimei.widget.NoDoubleClickListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import rx.Observable;

public class MamaFirstFragment extends BaseBindingFragment<FragmentMamaFirstBinding> implements View.OnClickListener, OnChartValueSelectedListener {
    private static final String TITLE = "title";
    private static final String ID = "id";
    List<RecentCarryBean.ResultsEntity> show_refund = new ArrayList<>();
    List<RecentCarryBean.ResultsEntity> his_refund = new ArrayList<>();

    private String shareLink;
    private String shopLink;
    private String msgUrl;
    private double mCashValue;
    private int orderNum;
    private String carryLogMoney;
    private String hisConfirmedCashOut;
    private BadgeView mBadgeView;
    private int mCurrent_dp_turns_num;
    private long lastClickTime = 0;

    public static MamaFirstFragment newInstance(String title, int id) {
        MamaFirstFragment fragment = new MamaFirstFragment();
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        args.putInt(ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initData() {
        MMInfoModel mmInfoModel = MMInfoModel.getInstance();
        addSubscription(Observable.mergeDelayError(mmInfoModel.getMamaUrl(), mmInfoModel.getShareShopping(),
                mmInfoModel.getMamaFortune(), mmInfoModel.getRecentCarry("0", "7"), mmInfoModel.getMaMaselfList())
                .subscribe(o -> {
                    if (o != null) {
                        if (o instanceof MamaUrl) {
                            initUrl((MamaUrl) o);
                        } else if (o instanceof MMShoppingBean) {
                            shopLink = ((MMShoppingBean) o).getShopInfo().getPreviewShopLink();
                        } else if (o instanceof MamaFortune) {
                            initFortune((MamaFortune) o);
                        } else if (o instanceof RecentCarryBean) {
                            his_refund.clear();
                            his_refund.addAll(((RecentCarryBean) o).getResults());
                            if ((his_refund.size() > 0)) {
                                setDataOfThisWeek();
                                initTodatText(his_refund);
                            }
                        } else if (o instanceof MamaSelfListBean) {
                            initTask((MamaSelfListBean) o);
                        }
                    }
                }, Throwable::printStackTrace, () -> b.scrollView.scrollTo(0, 0)));
        setListener();
    }

    private void initUrl(MamaUrl mamaUrl) {
        MamaUrl.ResultsBean resultsBean = mamaUrl.getResults().get(0);
        shareLink = resultsBean.getExtra().getInvite();
        msgUrl = resultsBean.getExtra().getNotice();
        List<PortalBean.ActivitysBean> activities = resultsBean.getMama_activities();
        if (activities != null && activities.size() > 0) {
            ActivityListAdapter adapter = new ActivityListAdapter(mActivity);
            adapter.updateWithClear(activities);
            b.lv.setAdapter(adapter);
        }
        b.scrollView.scrollTo(0, 0);
    }

    public void init_chart() {
        //mChart.setOnChartGestureListener(this);
        b.chart.setOnChartValueSelectedListener(this);
        b.chart.setDrawGridBackground(false);
        // no description text
        b.chart.setDescription("");
        b.chart.setNoDataText("");
        b.chart.setTouchEnabled(true);
        // enable scaling and dragging
        b.chart.setDragEnabled(true);
        b.chart.setScaleEnabled(false);
        b.chart.setBackgroundColor(Color.WHITE);
        XAxis xAxis = b.chart.getXAxis();
        xAxis.setEnabled(true);     //是否显示X坐标轴 及
        // 对应的刻度竖线，默认是true
        xAxis.setDrawAxisLine(true); //是否绘制坐标轴的线，即含有坐标的那条线，默认是true
        xAxis.setDrawGridLines(true); //是否显示X坐标轴上的刻度竖线，默认是true
        xAxis.setDrawLabels(true); //是否显示X坐标轴上的刻度，默认是true
        xAxis.setTextColor(getResources().getColor(R.color.colorAccent)); //X轴上的刻度的颜色
        xAxis.setTextSize(9f); //X轴上的刻度的字的大小 单位dp
        //      xAxis.setTypeface(Typeface tf); //X轴上的刻度的字体
        xAxis.setGridColor(getResources().getColor(R.color.colorAccent)); //X轴上的刻度竖线的颜色
        xAxis.setGridLineWidth(1); //X轴上的刻度竖线的宽 float类型
        xAxis.enableGridDashedLine(3, 8, 0); //虚线表示X轴上的刻度竖线(float lineLength, float spaceLength, float phase)三个参数，1.线长，2.虚线间距，3.虚线开始坐标
        b.chart.getLegend().setEnabled(false);
        b.chart.getAxisLeft().setDrawGridLines(false);
        b.chart.getAxisRight().setDrawGridLines(false);
        b.chart.getXAxis().setPosition(XAxis.XAxisPosition.TOP);
        //x y 坐标是否显示
        //mChart.getXAxis().setEnabled(false);
        b.chart.getAxisRight().setEnabled(false);
        b.chart.getAxisLeft().setEnabled(false);
    }

    public void setChartData(LineData data) {
        b.chart.setData(data);
        b.chart.animateX(2500, Easing.EasingOption.EaseInOutQuart);
        b.chart.setVisibleXRangeMaximum(6);
    }

    public void setDataOfThisWeek() {
        Calendar cal = Calendar.getInstance();
        ArrayList<String> xVals = new ArrayList<>();
        cal.add(Calendar.DAY_OF_YEAR, -7);
        for (int i = 0; i < 7; i++) {
            cal.add(Calendar.DAY_OF_YEAR, 1);//日期+1
            xVals.add((cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.DAY_OF_MONTH));
        }
        show_refund.clear();
        ArrayList<Entry> yVals = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            float val = 0;
            if (his_refund.size() > 0) {
                val = (float) his_refund.get(i).getCarry();
                show_refund.add(his_refund.get(i));
            }
            yVals.add(new Entry(val, i));
        }
        setData(xVals, yVals);
    }

    public void initTodatText(List<RecentCarryBean.ResultsEntity> his_refund) {
        b.tvVisit.setText(Integer.toString(his_refund.get(his_refund.size() - 1).getVisitorNum()));
        b.tvOrder.setText(Integer.toString(his_refund.get(his_refund.size() - 1).getOrderNum()));
        b.tvFund.setText(Double.toString(
                (double) (Math.round(his_refund.get(his_refund.size() - 1).getCarry() * 100)) / 100));
    }

    public void setData(ArrayList<String> xVals, ArrayList<Entry> yVals) {
        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(yVals, "");
        set1.setColor(getResources().getColor(R.color.colorAccent));
        set1.setCircleColor(getResources().getColor(R.color.colorAccent));
        set1.setLineWidth(1f);
        set1.setCircleRadius(2f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);
        set1.setHighlightLineWidth(1.75f); // 线宽
        set1.setHighLightColor(getResources().getColor(R.color.colorAccent)); // 高亮的线的颜色
        set1.setDrawHorizontalHighlightIndicator(false);
        set1.setDrawValues(true);
        set1.setValueFormatter((value, entry, dataSetIndex, viewPortHandler) -> "");
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1); // add the datasets
        // create a data object with the datasets
        LineData data = new LineData(xVals, dataSets);
        setChartData(data);
    }

    private void initTask(MamaSelfListBean bean) {
        int unread_cnt = bean.getUnread_cnt();
        if (unread_cnt > 0) {
            b.imageNotice.setVisibility(View.VISIBLE);
        } else {
            b.imageNotice.setVisibility(View.GONE);
        }
        List<MamaSelfListBean.ResultsBean> results = bean.getResults();
        List<String> list = new ArrayList<>();
//        if (unread_cnt > 0) {
        for (int i = 0; i < results.size(); i++) {
//                if (!results.get(i).isRead()) {
            list.add(results.get(i).getTitle());
//                }
        }
        b.marqueeView.startWithList(list);
//        }
    }

    private void initFortune(MamaFortune mamaFortune) {
        MamaFortune.MamaFortuneBean fortune = mamaFortune.getMamaFortune();
        MamaFortune.MamaFortuneBean.ExtraFiguresBean figures = fortune.getExtra_figures();
        b.tvIncome.setText(figures.getWeek_duration_total() + "");
        b.tvDayIncome.setText(figures.getToday_carry_record() + "");
        b.tvRank.setText(figures.getWeek_duration_rank() + "");
        b.tvWeekTask.setText("本周完成任务" + (figures.getTask_percentage() * 100) + "%");
        b.tvDay.setText(fortune.getExtraInfo().getSurplusDays() + "");
        mCashValue = fortune.getCashValue();
        orderNum = fortune.getOrderNum();
        carryLogMoney = fortune.getCarryValue() + "";
        if (fortune.getExtraInfo() != null) {
            hisConfirmedCashOut = fortune.getExtraInfo().getHisConfirmedCashOut();
        }
        ((MamaActivity) mActivity).hideIndeterminateProgressDialog();
        SharedPreferences preferences = mActivity.getSharedPreferences("push_num", Context.MODE_PRIVATE);
        String time = preferences.getString("time", "");
        int num = preferences.getInt("num", 0);
        mBadgeView = new BadgeView(mActivity);
        mBadgeView.setTextSizeOff(7);
        mBadgeView.setBackground(6, Color.parseColor("#FF3840"));
        mBadgeView.setGravity(Gravity.END | Gravity.TOP);
        mBadgeView.setPadding(JUtils.dip2px(4), JUtils.dip2px(1), JUtils.dip2px(4), JUtils.dip2px(1));
        mBadgeView.setTargetView(b.viewPush);
        mCurrent_dp_turns_num = fortune.getCurrent_dp_turns_num();
        try {
            String str = DateUtils.dateToString(new Date(), DateUtils.yyyyMMDD);
            if (time.equals(str)) {
                if (fortune.getCurrent_dp_turns_num() > num) {
                    mBadgeView.setBadgeCount((mCurrent_dp_turns_num - num));
                } else {
                    mBadgeView.setVisibility(View.GONE);
                }
            } else {
                if (fortune.getCurrent_dp_turns_num() > 0) {
                    mBadgeView.setBadgeCount(mCurrent_dp_turns_num);
                } else {
                    mBadgeView.setVisibility(View.GONE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setListener() {
        b.llRenew.setOnClickListener(this);
        b.llRank.setOnClickListener(this);
        b.llShare.setOnClickListener(this);
        b.llPush.setOnClickListener(this);
        b.llChoose.setOnClickListener(this);
        b.llShop.setOnClickListener(this);
        b.tvGoodWeek.setOnClickListener(this);
        b.tvMsg.setOnClickListener(this);
        b.visitLayout.setOnClickListener(this);
        b.orderLayout.setOnClickListener(this);
        b.fundLayout.setOnClickListener(this);
    }

    @Override
    protected void initViews() {
        init_chart();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_mama_first;
    }

    @Override
    public void onClick(View v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > NoDoubleClickListener.MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            switch (v.getId()) {
                case R.id.ll_renew:
                    Intent renewIntent = new Intent(mActivity, MamaReNewActivity.class);
                    renewIntent.putExtra("mamaCarryValue", mCashValue);
                    startActivity(renewIntent);
                    break;
                case R.id.ll_rank:
//                startActivity(new Intent(mActivity, PersonalCarryRankActivity.class));
                    break;
                case R.id.ll_share:
                    if (shopLink != null && !"".equals(shopLink)) {
                        JumpUtils.jumpToWebViewWithCookies(mActivity, shopLink, -1,
                                MMStoreWebViewActivity.class);
                    }
                    break;
                case R.id.ll_push:
                    SharedPreferences preferences = mActivity.getSharedPreferences("push_num", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    String str = null;
                    try {
                        str = DateUtils.dateToString(new Date(), DateUtils.yyyyMMDD);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    editor.putString("time", str);
                    editor.putInt("num", mCurrent_dp_turns_num);
                    editor.apply();
                    if (mBadgeView != null) {
                        mBadgeView.setVisibility(View.GONE);
                    }
                    startActivity(new Intent(mActivity, DayPushActivity.class));
                    break;
                case R.id.ll_choose:
                    startActivity(new Intent(mActivity, MamaChooseActivity.class));
                    break;
                case R.id.ll_shop:
                    if (shareLink != null && !"".equals(shareLink)) {
                        JumpUtils.jumpToWebViewWithCookies(mActivity, shareLink, 26,
                                MMShareCodeWebViewActivity.class);
                    }
                    break;
                case R.id.tv_good_week:
                    startActivity(new Intent(mActivity, GoodWeekActivity.class));
                    break;
                case R.id.tv_msg:
                    if (msgUrl != null && !"".equals(msgUrl)) {
                        b.imageNotice.setVisibility(View.GONE);
                        JumpUtils.jumpToWebViewWithCookies(mActivity, msgUrl, -1,
                                CommonWebViewActivity.class, "信息通知", false);
                    }
                    break;
                case R.id.visit_layout:
                    startActivity(new Intent(mActivity, MamaVisitorActivity.class));
                    break;
                case R.id.order_layout:
                    Intent intent3 = new Intent(mActivity, MMShoppingListActivity.class);
                    Bundle bundle3 = new Bundle();
                    bundle3.putInt("orderNum", orderNum);
                    intent3.putExtras(bundle3);
                    startActivity(intent3);
                    break;
                case R.id.fund_layout:
                    Bundle incomeBundle = new Bundle();
                    incomeBundle.putString("carrylogMoney", carryLogMoney);
                    incomeBundle.putString("hisConfirmedCashOut", hisConfirmedCashOut);
                    Intent incomeIntent = new Intent(mActivity, MMcarryLogActivity.class);
                    incomeIntent.putExtras(incomeBundle);
                    startActivity(incomeIntent);
                    break;
            }
        }
    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
        b.tvVisit.setText(Integer.toString(show_refund.get(e.getXIndex()).getVisitorNum()));
        b.tvOrder.setText(Integer.toString(show_refund.get(e.getXIndex()).getOrderNum()));
        b.tvFund.setText(Double.toString(
                (double) (Math.round(show_refund.get(e.getXIndex()).getCarry() * 100)) / 100));
    }

    @Override
    public void onNothingSelected() {

    }
}
