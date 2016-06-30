package com.jimei.xiaolumeimei.ui.mminfo;

import android.graphics.Color;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.jimei.xiaolumeimei.entities.RecentCarryBean;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created by itxuye on 2016/6/24.
 */
public class MMInfoPresenter extends MMInfoContract.Presenter {

  private static final int MAX_RECENT_DAYS = 15;
  List<RecentCarryBean.ResultsEntity> his_refund = new ArrayList<>();
  List<RecentCarryBean.ResultsEntity> show_refund = new ArrayList<>();

  @Override public void getShareShopping() {
    mRxManager.add(mModel.getShareShopping().subscribe(mmShoppingBean -> {
      /*mRxManager.post("mmShoppingBean",mmShoppingBean);*/
      if (null!=mmShoppingBean)
      mView.initShareInfo(mmShoppingBean);
    }, Throwable::printStackTrace));

  }

  @Override public void getMamaFortune() {
        mRxManager.add(mModel.getMamaFortune().subscribe(mamaFortune -> {
          if (null!=mamaFortune) {
            mView.initMMdata(mamaFortune);
            mView.initMMview(mamaFortune);
          }
        }, Throwable::printStackTrace));
  }

  @Override public void getAgentInfoBean() {
        mRxManager.add(mModel.getAgentInfoBean().subscribe(agentInfoBean -> {
          if (null!=agentInfoBean)
          mView.initPointBean(agentInfoBean);
        }));
  }


  @Override public void setDataOfThisWeek() {
    ArrayList<String> xVals =
        new ArrayList<String>(Arrays.asList("一", "二", "三", "四", "五", "六", "日"));

    Calendar cal = Calendar.getInstance();
    int days = 0;
    if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
      days = 7;
    } else {
      days = cal.get(Calendar.DAY_OF_WEEK) - 1;
    }

    //show_refund.clear();
    ArrayList<Entry> yVals = new ArrayList<Entry>();
    for (int i = 0; i < days; i++) {
      float val = 0;
      if (his_refund.size() > 0) {
        val = (float) his_refund.get(MAX_RECENT_DAYS - days + i).getCarry();
        show_refund.add(his_refund.get(MAX_RECENT_DAYS - days + i));
      }
      yVals.add(new Entry(val, i));
    }

    // set data

    setData(xVals, yVals);
    if (isEmptyData(show_refund)) {
      mView.setRlVisiBility();
    }
  }

  @Override public void setDataOfPreviousWeek() {
    Calendar cal = Calendar.getInstance();
    int month = cal.get(Calendar.MONTH) + 1;//得到月，因为从0开始的，所以要加1
    int day = cal.get(Calendar.DAY_OF_MONTH);//得到天
    int days = 0;
    if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
      days = 7;
    } else {
      days = cal.get(Calendar.DAY_OF_WEEK) - 1;
    }
    cal.add(Calendar.DAY_OF_YEAR, 0 - days - 7);//日期减days天数

    ArrayList<String> xVals = new ArrayList<String>();
    for (int i = 0; i < 7; i++) {
      cal.add(Calendar.DAY_OF_YEAR, 1);//日期+1
      xVals.add("" + (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.DAY_OF_MONTH));
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
      mView.setRlVisiBility();
    }
  }

  @Override public boolean isEmptyData(List<RecentCarryBean.ResultsEntity> list_refund) {
    return false;
  }

  @Override public void setData(ArrayList<String> xVals, ArrayList<Entry> yVals) {
    // create a dataset and give it a type
    LineDataSet set1 = new LineDataSet(yVals, "");

    set1.setColor(Color.parseColor("#F5B123"));
    set1.setCircleColor(Color.parseColor("#F5B123"));
    set1.setLineWidth(1f);
    set1.setCircleRadius(2f);
    set1.setDrawCircleHole(false);
    set1.setValueTextSize(9f);

    set1.setHighlightLineWidth(1.75f); // 线宽
    set1.setHighLightColor(Color.parseColor("#F5B123")); // 高亮的线的颜色

    set1.setDrawHorizontalHighlightIndicator(false);
    set1.setDrawValues(true);

    set1.setValueFormatter(new ValueFormatter() {
      @Override public String getFormattedValue(float value, Entry entry, int dataSetIndex,
          ViewPortHandler viewPortHandler) {
        return "";
      }
    });

    ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
    dataSets.add(set1); // add the datasets

    // create a data object with the datasets
    LineData data = new LineData(xVals, dataSets);

    mView.setChartData(data);
  }


 public void getRecentCarry() {
      mRxManager.add(mModel.getRecentCarry("0",Integer.toString(MAX_RECENT_DAYS)).subscribe(
          recentCarryBean -> {
            if (null != recentCarryBean) {
              his_refund.clear();
              his_refund.addAll(recentCarryBean.getResults());

              if ((his_refund.size() > 0)) {
                mView.init_chart();
                setDataOfThisWeek();

                if (his_refund.get(0) != null) {
                  mView.initTodatText(his_refund);
                }
              }
            }
          }));
  }

  @Override
  public void getRefund() {
    for (int i = 0; i < MAX_RECENT_DAYS; i++) {
      RecentCarryBean.ResultsEntity hisRefund = new RecentCarryBean.ResultsEntity();
      hisRefund.setVisitorNum(0);
      hisRefund.setOrderNum(0);
      hisRefund.setCarry(0.0);
      his_refund.add(i, hisRefund);
    }

    getRecentCarry();

  }
}
