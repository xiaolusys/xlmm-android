package com.jimei.xiaolumeimei.model;

import com.jimei.library.rx.DefaultTransform;
import com.jimei.xiaolumeimei.entities.AgentInfoBean;
import com.jimei.xiaolumeimei.entities.AllowanceBean;
import com.jimei.xiaolumeimei.entities.MMVisitorsBean;
import com.jimei.xiaolumeimei.entities.MamaFansBean;
import com.jimei.xiaolumeimei.entities.MamaFortune;
import com.jimei.xiaolumeimei.entities.MamaLivenessBean;
import com.jimei.xiaolumeimei.entities.ResponseResultBean;
import com.jimei.xiaolumeimei.entities.WithdrawCashHisBean;
import com.jimei.xiaolumeimei.xlmmService.XlmmRetrofitClient;
import rx.Observable;

/**
 * Created by 优尼世界 on 15/12/29.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class MamaInfoModel {

  private volatile static MamaInfoModel single_model;

  private MamaInfoModel() {
  }

  public static MamaInfoModel getInstance() {
    if (single_model == null) {
      synchronized (MamaInfoModel.class) {
        if (single_model == null) {
          single_model = new MamaInfoModel();
        }
      }
    }
    return single_model;
  }

  //得到全部订单数据列表
  public Observable<AgentInfoBean> getAgentInfoBean() {
    return XlmmRetrofitClient.getService()
        .getAgentInfoBean()
        .compose(new DefaultTransform<>());
  }

  //得到提现历史
  public Observable<WithdrawCashHisBean> getWithdrawCashHis() {
    return XlmmRetrofitClient.getService()
        .getWithdrawCashHis()
        .compose(new DefaultTransform<>());
  }

  //得到妈妈粉丝列表
  public Observable<MamaFansBean> getMamaFans(String page) {
    return XlmmRetrofitClient.getService()
        .getMamaFans(page)
        .compose(new DefaultTransform<>());
  }

  //得到妈妈访客列表
  public Observable<MMVisitorsBean> getMamaVisitor(String from, String page) {
    return XlmmRetrofitClient.getService()
        .getMamavisitor(from, page)
        .compose(new DefaultTransform<>());
  }

  //创建提款单信息
  public Observable<ResponseResultBean> withdraw_cash(String fund_type) {
    return XlmmRetrofitClient.getService()
        .withdraw_cash(fund_type)
        .compose(new DefaultTransform<>());
  }

  //转账到小鹿钱包
  public Observable<ResponseResultBean> toWallet(String fund_type) {
    return XlmmRetrofitClient.getService()
            .toWallet(fund_type)
            .compose(new DefaultTransform<>());
  }

  //cancel提款单信息
  public Observable<ResponseResultBean> cancel_withdraw_cash(String id) {
    return XlmmRetrofitClient.getService()
        .cancel_withdraw_cash(id)
        .compose(new DefaultTransform<>());
  }

  //得到分享补贴
  public Observable<AllowanceBean> getAllowance(String page) {
    return XlmmRetrofitClient.getService()
        .getAllowance(page)
        .compose(new DefaultTransform<>());
  }

  //得到妈妈财富
  public Observable<MamaFortune> getMamaFortune() {
    return XlmmRetrofitClient.getService()
        .getMamaFortune()
        .compose(new DefaultTransform<>());
  }

  //得到妈妈活跃值
  public Observable<MamaLivenessBean> getMamaLiveness(String page) {
    return XlmmRetrofitClient.getService()
        .getMamaLiveness(page)
        .compose(new DefaultTransform<>());
  }
}
