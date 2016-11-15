package com.jimei.xiaolumeimei.model;

import com.jimei.library.rx.DefaultTransform;
import com.jimei.xiaolumeimei.entities.CashoutPolicy;
import com.jimei.xiaolumeimei.entities.CategoryBean;
import com.jimei.xiaolumeimei.entities.ChooseListBean;
import com.jimei.xiaolumeimei.entities.DrawCouponBean;
import com.jimei.xiaolumeimei.entities.MMVisitorsBean;
import com.jimei.xiaolumeimei.entities.MaMaReNewBean;
import com.jimei.xiaolumeimei.entities.MamaFansBean;
import com.jimei.xiaolumeimei.entities.MamaFortune;
import com.jimei.xiaolumeimei.entities.MamaLivenessBean;
import com.jimei.xiaolumeimei.entities.PersonalCarryRankBean;
import com.jimei.xiaolumeimei.entities.PotentialFans;
import com.jimei.xiaolumeimei.entities.ResponseResultBean;
import com.jimei.xiaolumeimei.entities.ResultBean;
import com.jimei.xiaolumeimei.entities.ResultEntity;
import com.jimei.xiaolumeimei.entities.WeekTaskRewardBean;
import com.jimei.xiaolumeimei.entities.WithdrawCashHisBean;
import com.jimei.xiaolumeimei.xlmmService.XlmmRetrofitClient;

import java.util.List;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by 优尼世界 on 15/12/29.
 * <p>
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class MamaInfoModel {

    private static MamaInfoModel single_model = new MamaInfoModel();

    private MamaInfoModel() {
    }

    public static MamaInfoModel getInstance() {
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
        return XlmmRetrofitClient.getService()
                .getMamaFans(page)
                .compose(new DefaultTransform<>());
    }

    //妈妈余额兑换现金消费券
    public Observable<DrawCouponBean> drawCoupon(String template_id) {
        return XlmmRetrofitClient.getService()
                .drawCoupon(template_id, "1")
                .compose(new DefaultTransform<>());
    }

    //妈妈小额提现
    public Observable<ResultEntity> getNoauditCashout(double amount, String verify_code) {
        return XlmmRetrofitClient.getService()
                .getNoauditCashout(amount, verify_code)
                .compose(new DefaultTransform<>());
    }

    //得到妈妈访客列表
    public Observable<MMVisitorsBean> getMamaVisitor(int page) {
        return XlmmRetrofitClient.getService()
                .getMamaVisitor(14, page)
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

    //得到妈妈财富
    public Observable<MamaFortune> getMamaFortune() {
        return XlmmRetrofitClient.getService().
                getMamaFortune()
                .compose(new DefaultTransform<>());
    }

    //得到妈妈活跃值
    public Observable<MamaLivenessBean> getMamaLiveness(String page) {
        return XlmmRetrofitClient.getService()
                .getMamaLiveness(page)
                .compose(new DefaultTransform<>());
    }

    public Observable<PotentialFans> getPotentialFans(String page) {
        return XlmmRetrofitClient.getService()
                .getPotentialFans(page)
                .compose(new DefaultTransform<>());
    }

    public Observable<MaMaReNewBean> getRegisterProInfo() {
        return XlmmRetrofitClient.getService()
                .getRegisterProInfo()
                .compose(new DefaultTransform<>());
    }

    public Observable<Object> mamaRegisterPay(String product_id, String sku_id,
                                                              String payment, String channel, String num,
                                                              String post_fee, String discount_fee, String uuid,
                                                              String total_fee, String wallet_renew_deposit) {
        return XlmmRetrofitClient.getService()
                .mamaRegisterPay(product_id, sku_id, payment, channel, num, post_fee,
                        discount_fee, uuid, total_fee, wallet_renew_deposit)
                .compose(new DefaultTransform<>());
    }

    public Observable<Response<ResultBean>> exchangeDeposit(String exchange_type) {
        return XlmmRetrofitClient.getService()
                .exchangeDeposit(exchange_type)
                .compose(new DefaultTransform<>());
    }

    public Observable<List<PersonalCarryRankBean>> getPersonalCarryRankBean() {
        return XlmmRetrofitClient.getService()
                .getPersonalCarryRankBean()
                .compose(new DefaultTransform<>());
    }

    public Observable<List<PersonalCarryRankBean>> getWeekPersonalCarryRankBean() {
        return XlmmRetrofitClient.getService()
                .getWeekPersonalCarryRankBean()
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

    public Observable<List<PersonalCarryRankBean>> getTeamCarryRankBean() {
        return XlmmRetrofitClient.getService()
                .getTeamCarryRankBean()
                .compose(new DefaultTransform<>());
    }

    public Observable<List<PersonalCarryRankBean>> getWeekTeamCarryRankBean() {
        return XlmmRetrofitClient.getService()
                .getWeekTeamCarryRankBean()
                .compose(new DefaultTransform<>());
    }

    public Observable<WeekTaskRewardBean> getTaskReward() {
        return XlmmRetrofitClient.getService()
                .getTaskReward()
                .compose(new DefaultTransform<>());
    }

    public Observable<List<CategoryBean>> getCategory() {
        return XlmmRetrofitClient.getService()
                .getCategory()
                .compose(new DefaultTransform<>());
    }

    public Observable<CashoutPolicy> getCashoutPolicy() {
        return XlmmRetrofitClient.getService()
                .getCashoutPolicy()
                .compose(new DefaultTransform<>());
    }

    public Observable<ChooseListBean> getChooseList(int page, String sort_field, String cid, int reverse) {
        if ("".equals(sort_field) && "".equals(cid)) {
            return XlmmRetrofitClient.getService()
                    .getChooseList(page)
                    .compose(new DefaultTransform<>());
        } else if ("".equals(sort_field)) {
            return XlmmRetrofitClient.getService()
                    .getChooseListByCid(page, cid)
                    .compose(new DefaultTransform<>());
        } else if ("".equals(cid)) {
            return XlmmRetrofitClient.getService()
                    .getChooseListBySort(page, sort_field, reverse)
                    .compose(new DefaultTransform<>());
        } else {
            return XlmmRetrofitClient.getService()
                    .getChooseList(page, sort_field, cid, reverse)
                    .compose(new DefaultTransform<>());
        }
    }
}
