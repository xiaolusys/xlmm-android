package com.jimei.xiaolumeimei.model;

import com.jimei.library.rx.DefaultTransform;
import com.jimei.xiaolumeimei.entities.AllowanceBean;
import com.jimei.xiaolumeimei.entities.DrawCouponBean;
import com.jimei.xiaolumeimei.entities.MMVisitorsBean;
import com.jimei.xiaolumeimei.entities.MaMaReNewBean;
import com.jimei.xiaolumeimei.entities.MamaFansBean;
import com.jimei.xiaolumeimei.entities.MamaFortune;
import com.jimei.xiaolumeimei.entities.MamaLivenessBean;
import com.jimei.xiaolumeimei.entities.MamaUrl;
import com.jimei.xiaolumeimei.entities.PersonalCarryRankBean;
import com.jimei.xiaolumeimei.entities.PotentialFans;
import com.jimei.xiaolumeimei.entities.ResponseResultBean;
import com.jimei.xiaolumeimei.entities.WithdrawCashHisBean;
import com.jimei.xiaolumeimei.xlmmService.XlmmRetrofitClient;
import java.util.List;
import okhttp3.ResponseBody;
import retrofit2.Response;
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

  //得到提现历史
  public Observable<WithdrawCashHisBean> getWithdrawCashHis(String page) {
    return XlmmRetrofitClient.getService()
        .getWithdrawCashHis(page)
        .compose(new DefaultTransform<>());
  }

  //得到妈妈粉丝列表
  public Observable<MamaFansBean> getMamaFans(String page) {
    return XlmmRetrofitClient.getService().getMamaFans(page).compose(new DefaultTransform<>());
  }

  //妈妈余额兑换现金消费券
  public Observable<DrawCouponBean> drawCoupon(String template_id) {
    return XlmmRetrofitClient.getService()
        .drawCoupon(template_id, "1")
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
  public Observable<ResponseResultBean> toWallet(String cashout_amount) {
    return XlmmRetrofitClient.getService()
        .toWallet(cashout_amount)
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
    return XlmmRetrofitClient.getService().getAllowance(page).compose(new DefaultTransform<>());
  }

  //得到妈妈财富
  public Observable<MamaFortune> getMamaFortune() {
    return XlmmRetrofitClient.getService().getMamaFortune().compose(new DefaultTransform<>());
  }

  //得到妈妈活跃值
  public Observable<MamaLivenessBean> getMamaLiveness(String page) {
    return XlmmRetrofitClient.getService().getMamaLiveness(page).compose(new DefaultTransform<>());
  }

  //得到妈妈活跃值
  public Observable<PotentialFans> getPotentialFans(String page) {
    return XlmmRetrofitClient.getService().getPotentialFans(page).compose(new DefaultTransform<>());
  }

  public Observable<MamaUrl> getMamaUrl() {
    return XlmmRetrofitClient.getService().getMamaUrl("1.0").compose(new DefaultTransform<>());
  }

  public Observable<MaMaReNewBean> getRegisterProInfo() {

    return XlmmRetrofitClient.getService().getRegisterProInfo().compose(new DefaultTransform<>());
  }

  public Observable<Response<ResponseBody>> mamaRegisterPay(String product_id, String sku_id,
      String payment, String channel, String num, String post_fee, String discount_fee, String uuid,
      String total_fee) {

    return XlmmRetrofitClient.getService()
        .mamaRegisterPay(product_id, sku_id, payment, channel, num, post_fee, discount_fee, uuid,
            total_fee)
        .compose(new DefaultTransform<>());
  }

  public Observable<Response<List<PersonalCarryRankBean>>> getPersonalCarryRankBean() {

    return XlmmRetrofitClient.getService()
        .getPersonalCarryRankBean()
        .compose(new DefaultTransform<>());
  }

  public Observable<Response<PersonalCarryRankBean>> getPersonalSelfCarryRankBean() {

    return XlmmRetrofitClient.getService()
        .getPersonalSelfCarryRankBean()
        .compose(new DefaultTransform<>());
  }

  public Observable<Response<List<PersonalCarryRankBean>>> getTeamMembers(String id) {

    return XlmmRetrofitClient.getService()
        .getTeamMembers(id)
        .compose(new DefaultTransform<>());
  }

  public Observable<Response<PersonalCarryRankBean>> getTeamSelfRank() {

    return XlmmRetrofitClient.getService()
        .getTeamSelfRank()
        .compose(new DefaultTransform<>());
  }
}
