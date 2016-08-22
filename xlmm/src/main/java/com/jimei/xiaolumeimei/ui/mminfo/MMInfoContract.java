package com.jimei.xiaolumeimei.ui.mminfo;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.jimei.xiaolumeimei.base.BaseModel;
import com.jimei.xiaolumeimei.base.BasePresenter;
import com.jimei.xiaolumeimei.base.BaseView;
import com.jimei.xiaolumeimei.entities.MMShoppingBean;
import com.jimei.xiaolumeimei.entities.MaMaRenwuListBean;
import com.jimei.xiaolumeimei.entities.MamaFortune;
import com.jimei.xiaolumeimei.entities.MamaSelfListBean;
import com.jimei.xiaolumeimei.entities.MamaUrl;
import com.jimei.xiaolumeimei.entities.RecentCarryBean;
import com.jimei.xiaolumeimei.entities.UserInfoBean;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by itxuye on 2016/6/24.
 */
public interface MMInfoContract {

  interface Model extends BaseModel {
    Observable<MMShoppingBean> getShareShopping();

    Observable<MamaFortune> getMamaFortune();

    Observable<RecentCarryBean> getRecentCarry(String from, String day);

    Observable<MamaUrl> getMamaUrl();

    Observable<Response<MaMaRenwuListBean>> getMaMaRenwuListBean(String id);

    Observable<Response<MamaSelfListBean>> getMaMaselfList();

    Observable<UserInfoBean>  getUserInfo();
  }

  interface View extends BaseView {
    void initMamaUrl(MamaUrl mamaUrl);

    void initMMview(MamaFortune mamaFortune);

    void initShareInfo(MMShoppingBean shoppingBean);

    void init_chart();

    void initMMdata(MamaFortune fortune);

    void setRlVisiBility();

    void setChartData(LineData data);

    void initTodatText(List<RecentCarryBean.ResultsEntity> his_refund);

    void getMaMaRenwuListBean(MaMaRenwuListBean maMaRenwuListBean);

    void getMaMaRenwuListBean(MamaSelfListBean mamaSelfListBean);

    void setUdesk(UserInfoBean userInfoBean);
  }

  abstract class Presenter extends BasePresenter<Model, View> {

    public abstract void getShareShopping();

    public abstract void getMamaFortune();

    public abstract void getMamaUrl();

    public abstract void setDataOfThisWeek();

    public abstract void setDataOfPreviousWeek();

    public abstract boolean isEmptyData(List<RecentCarryBean.ResultsEntity> list_refund);

    public abstract void setData(ArrayList<String> xVals, ArrayList<Entry> yVals);

    public abstract void getRefund();

    public abstract void getMaMaRenwuListBean(String id);

    public abstract void getMaMaselfList();

    public abstract void getUserInfo();

    @Override public void onStart() {
    }
  }
}
