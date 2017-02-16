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
import com.jimei.library.utils.ViewUtils;
import com.jimei.library.widget.badgelib.BadgeView;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseBindingFragment;
import com.jimei.xiaolumeimei.base.CommonWebViewActivity;
import com.jimei.xiaolumeimei.databinding.FragmentMamaFirstBinding;
import com.jimei.xiaolumeimei.entities.MMShoppingBean;
import com.jimei.xiaolumeimei.entities.MamaFortune;
import com.jimei.xiaolumeimei.entities.MamaSelfListBean;
import com.jimei.xiaolumeimei.entities.MamaUrl;
import com.jimei.xiaolumeimei.entities.RecentCarryBean;
import com.jimei.xiaolumeimei.entities.event.WalletEvent;
import com.jimei.xiaolumeimei.entities.event.WebViewEvent;
import com.jimei.xiaolumeimei.model.MamaInfoModel;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.DayPushActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MMCarryLogActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MMFansActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MMShareCodeWebViewActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MMShoppingListActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MMStoreWebViewActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MamaChooseActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MamaVisitorActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MamaWalletActivity;
import com.jimei.xiaolumeimei.util.JumpUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.udesk.UdeskSDKManager;
import rx.Observable;

public class MamaFirstFragment extends BaseBindingFragment<FragmentMamaFirstBinding> implements
    View.OnClickListener, OnChartValueSelectedListener {
    private static final String TITLE = "title";
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
    private String fansUrl;

    public static MamaFirstFragment newInstance(String title) {
        MamaFirstFragment fragment = new MamaFirstFragment();
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initData() {
        refreshFortune();
        MamaInfoModel mmInfoModel = MamaInfoModel.getInstance();
        addSubscription(Observable.mergeDelayError(mmInfoModel.getMamaUrl(), mmInfoModel.getShareShopping(),
            mmInfoModel.getMamaFortune(), mmInfoModel.getRecentCarry("0", "7"), mmInfoModel.getMaMaselfList())
            .subscribe(o -> {
                if (o != null) {
                    if (o instanceof MamaUrl) {
                        initUrl((MamaUrl)  o);
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
            }, throwable -> {
                throwable.printStackTrace();
                hideIndeterminateProgressDialog();
            }, this::hideIndeterminateProgressDialog));
    }

    private void initUrl(MamaUrl mamaUrl) {
        MamaUrl.ResultsBean resultsBean = mamaUrl.getResults().get(0);
        shareLink = resultsBean.getExtra().getInvite();
        msgUrl = resultsBean.getExtra().getNotice();
        fansUrl = resultsBean.getExtra().getFans_explain();
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
        b.chartVisit.setText(Integer.toString(his_refund.get(his_refund.size() - 1).getVisitorNum()));
        b.chartOrder.setText(Integer.toString(his_refund.get(his_refund.size() - 1).getOrderNum()));
        b.chartFund.setText(Double.toString(
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
        for (int i = 0; i < results.size(); i++) {
            list.add(results.get(i).getTitle());
        }
        b.marqueeView.startWithList(list);
    }

    private void initFortune(MamaFortune mamaFortune) {
        MamaFortune.MamaFortuneBean fortune = mamaFortune.getMamaFortune();
        b.tvDay.setText(fortune.getExtraInfo().getSurplusDays() + "");
        mCashValue = fortune.getCashValue();
        orderNum = fortune.getOrderNum();
        carryLogMoney = fortune.getCarryValue() + "";
        if (fortune.getExtraInfo() != null) {
            hisConfirmedCashOut = fortune.getExtraInfo().getHisConfirmedCashOut();
            if (fortune.getExtraInfo().getThumbnail() != null) {
                ViewUtils.loadImgToImgView(mActivity, b.ivHead,
                    fortune.getExtraInfo().getThumbnail());
            }
            b.tvLevel.setText(fortune.getExtraInfo().getAgencylevelDisplay());
        }
        b.tvId.setText("ID:" + fortune.getMamaId());
        b.tvMamaName.setText(fortune.getMamaLevelDisplay());
        b.tvCashValue.setText(fortune.getCashValue() + "元");
        b.tvCarryValue.setText(fortune.getCarryValue() + "元");
        b.tvOrder.setText(fortune.getOrderNum() + "个");
        b.tvFans.setText(fortune.getFansNum() + "个");
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

    public void setListener() {
        b.btnFinish.setOnClickListener(this);
        b.btnService.setOnClickListener(this);
        b.llShare.setOnClickListener(this);
        b.llPush.setOnClickListener(this);
        b.llChoose.setOnClickListener(this);
        b.llShop.setOnClickListener(this);
        b.tvMsg.setOnClickListener(this);
        b.visitLayout.setOnClickListener(this);
        b.orderLayout.setOnClickListener(this);
        b.fundLayout.setOnClickListener(this);
        b.llWallet.setOnClickListener(this);
        b.llIncome.setOnClickListener(this);
        b.llVisit.setOnClickListener(this);
        b.llOrder.setOnClickListener(this);
        b.llFans.setOnClickListener(this);
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
        switch (v.getId()) {
            case R.id.btn_finish:
                mActivity.finish();
                break;
            case R.id.btn_service:
                UdeskSDKManager.getInstance().showRobotOrConversation(mActivity);
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
                Intent incomeIntent = new Intent(mActivity, MMCarryLogActivity.class);
                incomeIntent.putExtras(incomeBundle);
                startActivity(incomeIntent);
                break;
            case R.id.ll_wallet:
                Intent walletIntent = new Intent(mActivity, MamaWalletActivity.class);
                walletIntent.putExtra("cash", mCashValue);
                startActivity(walletIntent);
                break;
            case R.id.ll_income:
                Bundle incomeBundle2 = new Bundle();
                incomeBundle2.putString("carrylogMoney", carryLogMoney);
                incomeBundle2.putString("hisConfirmedCashOut", hisConfirmedCashOut);
                Intent incomeIntent2 = new Intent(mActivity, MMCarryLogActivity.class);
                incomeIntent2.putExtras(incomeBundle2);
                startActivity(incomeIntent2);
                break;
            case R.id.ll_visit:
                Intent visitIntent = new Intent(mActivity, MamaVisitorActivity.class);
                startActivity(visitIntent);
                break;
            case R.id.ll_order:
                Intent orderIntent = new Intent(mActivity, MMShoppingListActivity.class);
                orderIntent.putExtra("orderNum", orderNum);
                startActivity(orderIntent);
                break;
            case R.id.ll_fans:
                if (fansUrl != null && !"".equals(fansUrl)) {
                    EventBus.getDefault().postSticky(new WebViewEvent(fansUrl));
                    startActivity(new Intent(mActivity, MMFansActivity.class));
                }
                break;
        }
    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
        b.chartVisit.setText(Integer.toString(show_refund.get(e.getXIndex()).getVisitorNum()));
        b.chartOrder.setText(Integer.toString(show_refund.get(e.getXIndex()).getOrderNum()));
        b.chartFund.setText(Double.toString(
            (double) (Math.round(show_refund.get(e.getXIndex()).getCarry() * 100)) / 100));
    }

    @Override
    public void onNothingSelected() {

    }

    public void refreshFortune() {
        addSubscription(MamaInfoModel.getInstance()
            .getMamaFortune()
            .subscribe(this::initFortune, throwable -> {
                throwable.printStackTrace();
                hideIndeterminateProgressDialog();
            }));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void reLoadData(WalletEvent event) {
        refreshFortune();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public View getLoadingView() {
        return b.ll;
    }
}
