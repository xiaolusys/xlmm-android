package com.jimei.xiaolumeimei.ui.mminfo;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.jimei.xiaolumeimei.base.BaseModel;
import com.jimei.xiaolumeimei.base.BasePresenter;
import com.jimei.xiaolumeimei.base.BaseView;
import com.jimei.xiaolumeimei.entities.MMShoppingBean;
import com.jimei.xiaolumeimei.entities.MamaFortune;
import com.jimei.xiaolumeimei.entities.RecentCarryBean;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;

/**
 * Created by itxuye on 2016/6/24.
 */
public interface MMInfoContract {

  interface Model extends BaseModel {
    Observable<MMShoppingBean> getShareShopping();

    Observable<MamaFortune> getMamaFortune();

    Observable<RecentCarryBean> getRecentCarry(String from, String day);
  }

  interface View extends BaseView {
    void initMMview(MamaFortune mamaFortune);

    void initShareInfo(MMShoppingBean shoppingBean);

    void init_chart();

    void initMMdata(MamaFortune fortune);

    void setRlVisiBility();

    void setChartData(LineData data);

    void initTodatText(List<RecentCarryBean.ResultsEntity> his_refund);
  }

  abstract class Presenter extends BasePresenter<Model, View> {

    public abstract void getShareShopping();

    public abstract void getMamaFortune();

    public abstract void setDataOfThisWeek();

    public abstract void setDataOfPreviousWeek();

    public abstract boolean isEmptyData(List<RecentCarryBean.ResultsEntity> list_refund);

    public abstract void setData(ArrayList<String> xVals, ArrayList<Entry> yVals);

    public abstract void getRefund();

    @Override public void onStart() {
    }
  }
}
