package com.jimei.xiaolumeimei.ui.mminfo;

import com.github.mikephil.charting.data.Entry;
import com.jimei.xiaolumeimei.base.BaseModel;
import com.jimei.xiaolumeimei.base.BasePresenter;
import com.jimei.xiaolumeimei.base.BaseView;
import com.jimei.xiaolumeimei.entities.AgentInfoBean;
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
    Observable<AgentInfoBean> getAgentInfoBean();
    Observable<RecentCarryBean> getRecentCarry(String from, String day);

  }

  interface View extends BaseView {

     void initMMview(MamaFortune mamaFortune);
     void show_liveness(int liveness);
     void init_chart();
    void initShareInfo();

  }

  abstract class Presenter extends BasePresenter<Model, View> {

    public abstract void getShareShopping();
    public abstract void getMamaFortune();
    public abstract void getAgentInfoBean();
    public abstract void initPointBean();
    public abstract void setDataOfThisWeek();
    public abstract void setDataOfPreviousWeek();
    public abstract boolean isEmptyData(List<RecentCarryBean.ResultsEntity> list_refund);
    public abstract void setData(ArrayList<String> xVals, ArrayList<Entry> yVals);
    public abstract void initMMdata(MamaFortune mamaFortune);
    public abstract void getRecentCarry(String from, String day);

    @Override
    public void onStart() {}
  }

}
