package com.jimei.xiaolumeimei.model;

import com.jimei.library.rx.DefaultTransform;
import com.jimei.xiaolumeimei.entities.AwardCarryBean;
import com.jimei.xiaolumeimei.entities.CarryLogListBean;
import com.jimei.xiaolumeimei.entities.CashoutPolicy;
import com.jimei.xiaolumeimei.entities.CategoryBean;
import com.jimei.xiaolumeimei.entities.ChooseListBean;
import com.jimei.xiaolumeimei.entities.ClickcarryBean;
import com.jimei.xiaolumeimei.entities.DrawCouponBean;
import com.jimei.xiaolumeimei.entities.MMShoppingBean;
import com.jimei.xiaolumeimei.entities.MMVisitorsBean;
import com.jimei.xiaolumeimei.entities.MaMaReNewBean;
import com.jimei.xiaolumeimei.entities.MamaFansBean;
import com.jimei.xiaolumeimei.entities.MamaFortune;
import com.jimei.xiaolumeimei.entities.MamaLivenessBean;
import com.jimei.xiaolumeimei.entities.MamaSelfListBean;
import com.jimei.xiaolumeimei.entities.MamaUrl;
import com.jimei.xiaolumeimei.entities.MiPushOrderCarryBean;
import com.jimei.xiaolumeimei.entities.NinePicBean;
import com.jimei.xiaolumeimei.entities.OderCarryBean;
import com.jimei.xiaolumeimei.entities.PersonalCarryRankBean;
import com.jimei.xiaolumeimei.entities.PortalBean;
import com.jimei.xiaolumeimei.entities.PotentialFans;
import com.jimei.xiaolumeimei.entities.ProductListBean;
import com.jimei.xiaolumeimei.entities.RecentCarryBean;
import com.jimei.xiaolumeimei.entities.ResponseResultBean;
import com.jimei.xiaolumeimei.entities.ResultBean;
import com.jimei.xiaolumeimei.entities.ResultEntity;
import com.jimei.xiaolumeimei.entities.SaveTimeBean;
import com.jimei.xiaolumeimei.entities.UserInfoBean;
import com.jimei.xiaolumeimei.entities.WeekTaskRewardBean;
import com.jimei.xiaolumeimei.entities.WithdrawCashHisBean;
import com.jimei.xiaolumeimei.entities.WxQrcode;
import com.jimei.xiaolumeimei.xlmmService.RetrofitClient;
import com.jimei.xiaolumeimei.xlmmService.api.MamaService;

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
    private static MamaService mamaService;

    private static MamaService getService() {
        if (mamaService == null) {
            mamaService = RetrofitClient.createAdapter().create(MamaService.class);
        }
        return mamaService;
    }

    private MamaInfoModel() {
    }

    public static MamaInfoModel getInstance() {
        return single_model;
    }

    //得到提现历史
    public Observable<WithdrawCashHisBean> getWithdrawCashHis(String page) {
        return getService()
                .getWithdrawCashHis(page)
                .compose(new DefaultTransform<>());
    }

    //得到妈妈粉丝列表
    public Observable<MamaFansBean> getMamaFans(String page) {
        return getService()
                .getMamaFans(page)
                .compose(new DefaultTransform<>());
    }

    //妈妈余额兑换现金消费券
    public Observable<DrawCouponBean> drawCoupon(String template_id) {
        return getService()
                .drawCoupon(template_id, "1")
                .compose(new DefaultTransform<>());
    }

    //妈妈小额提现
    public Observable<ResultEntity> getNoauditCashout(double amount, String verify_code) {
        return getService()
                .getNoauditCashout(amount, verify_code)
                .compose(new DefaultTransform<>());
    }

    //得到妈妈访客列表
    public Observable<MMVisitorsBean> getMamaVisitor(int page) {
        return getService()
                .getMamaVisitor(14, page)
                .compose(new DefaultTransform<>());
    }

    //创建提款单信息
    public Observable<ResponseResultBean> withdraw_cash(String fund_type) {
        return getService()
                .withdraw_cash(fund_type)
                .compose(new DefaultTransform<>());
    }

    //转账到小鹿钱包
    public Observable<ResponseResultBean> toWallet(String cashout_amount) {
        return getService()
                .toWallet(cashout_amount)
                .compose(new DefaultTransform<>());
    }

    //cancel提款单信息
    public Observable<ResponseResultBean> cancel_withdraw_cash(String id) {
        return getService()
                .cancel_withdraw_cash(id)
                .compose(new DefaultTransform<>());
    }

    //得到妈妈财富
    public Observable<MamaFortune> getMamaFortune() {
        return getService()
                .getMamaFortune()
                .compose(new DefaultTransform<>());
    }

    //得到妈妈活跃值
    public Observable<MamaLivenessBean> getMamaLiveness(String page) {
        return getService()
                .getMamaLiveness(page)
                .compose(new DefaultTransform<>());
    }

    public Observable<PotentialFans> getPotentialFans(String page) {
        return getService()
                .getPotentialFans(page)
                .compose(new DefaultTransform<>());
    }

    public Observable<MaMaReNewBean> getRegisterProInfo() {
        return getService()
                .getRegisterProInfo()
                .compose(new DefaultTransform<>());
    }

    public Observable<Object> mamaRegisterPay(String product_id, String sku_id,
                                              String payment, String channel, String num,
                                              String post_fee, String discount_fee, String uuid,
                                              String total_fee, String wallet_renew_deposit) {
        return getService()
                .mamaRegisterPay(product_id, sku_id, payment, channel, num, post_fee,
                        discount_fee, uuid, total_fee, wallet_renew_deposit)
                .compose(new DefaultTransform<>());
    }

    public Observable<Response<ResultBean>> exchangeDeposit(String exchange_type) {
        return getService()
                .exchangeDeposit(exchange_type)
                .compose(new DefaultTransform<>());
    }

    public Observable<List<PersonalCarryRankBean>> getPersonalCarryRankBean() {
        return getService()
                .getPersonalCarryRankBean()
                .compose(new DefaultTransform<>());
    }

    public Observable<List<PersonalCarryRankBean>> getWeekPersonalCarryRankBean() {
        return getService()
                .getWeekPersonalCarryRankBean()
                .compose(new DefaultTransform<>());
    }

    public Observable<Response<PersonalCarryRankBean>> getPersonalSelfCarryRankBean() {
        return getService()
                .getPersonalSelfCarryRankBean()
                .compose(new DefaultTransform<>());
    }

    public Observable<Response<List<PersonalCarryRankBean>>> getTeamMembers(String id) {
        return getService()
                .getTeamMembers(id)
                .compose(new DefaultTransform<>());
    }

    public Observable<Response<PersonalCarryRankBean>> getTeamSelfRank() {
        return getService()
                .getTeamSelfRank()
                .compose(new DefaultTransform<>());
    }

    public Observable<List<PersonalCarryRankBean>> getTeamCarryRankBean() {
        return getService()
                .getTeamCarryRankBean()
                .compose(new DefaultTransform<>());
    }

    public Observable<List<PersonalCarryRankBean>> getWeekTeamCarryRankBean() {
        return getService()
                .getWeekTeamCarryRankBean()
                .compose(new DefaultTransform<>());
    }

    public Observable<WeekTaskRewardBean> getTaskReward() {
        return getService()
                .getTaskReward()
                .compose(new DefaultTransform<>());
    }

    public Observable<List<CategoryBean>> getCategory() {
        return getService()
                .getCategory()
                .compose(new DefaultTransform<>());
    }

    public Observable<CashoutPolicy> getCashoutPolicy() {
        return getService()
                .getCashoutPolicy()
                .compose(new DefaultTransform<>());
    }

    public Observable<ChooseListBean> getChooseList(int page, String sort_field, String cid, int reverse) {
        if ("".equals(sort_field) && "".equals(cid)) {
            return getService()
                    .getChooseList(page)
                    .compose(new DefaultTransform<>());
        } else if ("".equals(sort_field)) {
            return getService()
                    .getChooseListByCid(page, cid)
                    .compose(new DefaultTransform<>());
        } else if ("".equals(cid)) {
            return getService()
                    .getChooseListBySort(page, sort_field, reverse)
                    .compose(new DefaultTransform<>());
        } else {
            return getService()
                    .getChooseList(page, sort_field, cid, reverse)
                    .compose(new DefaultTransform<>());
        }
    }

    public Observable<MMShoppingBean> getShareShopping() {
        return getService()
                .getShareShopping()
                .compose(new DefaultTransform<>());
    }

    //test
    public Observable<MamaUrl> getMamaUrl() {
        return getService()
                .getMamaUrl("1.0")
                .compose(new DefaultTransform<>());
    }

    public Observable<MamaSelfListBean> getMaMaselfList() {
        return getService()
                .getMaMaselfList()
                .compose(new DefaultTransform<>());
    }

    public Observable<UserInfoBean> getUserInfo() {
        return getService()
                .getUserInfo()
                .compose(new DefaultTransform<>());
    }

    public Observable<List<MiPushOrderCarryBean>> getLatestOrderCarry() {
        return getService()
                .getLatestOrderCarry()
                .compose(new DefaultTransform<>());
    }

    public Observable<RecentCarryBean> getRecentCarry(String from, String days) {
        return getService()
                .getRecentCarry(from, days)
                .compose(new DefaultTransform<>());
    }

    public Observable<List<NinePicBean>> getNinePic(int sale_category) {
        if (sale_category == -1) {
            return getService()
                    .getNinePic()
                    .compose(new DefaultTransform<>());
        } else {
            return getService()
                    .getNinePic(sale_category)
                    .compose(new DefaultTransform<>());
        }
    }

    public Observable<List<NinePicBean>> getNinePicByModelId(int model_id) {
        return getService()
                .getNinePicByModelId(model_id)
                .compose(new DefaultTransform<>());
    }

    public Observable<ProductListBean> getBoutiqueList(int page) {
        return getService()
                .getBoutiqueList(page)
                .compose(new DefaultTransform<>());
    }

    public Observable<List<NinePicBean>> getNinePicByOrdering() {
        return getService()
                .getNinePic("-save_times")
                .compose(new DefaultTransform<>());
    }

    //得到全部历史收益
    public Observable<CarryLogListBean> getMamaAllCarryLogs(String page) {
        return getService()
                .getMamaAllCarryLogs(page)
                .compose(new DefaultTransform<>());
    }

    public Observable<OderCarryBean> getMamaAllOderCarryLogs(String page) {
        return getService()
                .getMamaAllOderCarryLogs(page)
                .compose(new DefaultTransform<>());
    }

    public Observable<OderCarryBean> getMamaAllOderCarryLogs(int page) {
        return getService()
                .getMamaAllOderCarryLogs("direct", page)
                .compose(new DefaultTransform<>());
    }

    public Observable<AwardCarryBean> getMamaAllAwardCarryLogs(String page) {
        return getService()
                .getMamaAllAwardCarryLogs(page)
                .compose(new DefaultTransform<>());
    }

    public Observable<ClickcarryBean> getMamaAllClickCarryLogs(String page) {
        return getService()
                .getMamaAllClickCarryLogs(page)
                .compose(new DefaultTransform<>());
    }

    public Observable<WxQrcode> getWxCode() {
        return getService()
                .getWxCode()
                .compose(new DefaultTransform<>());
    }

    public Observable<PortalBean> getPortalBean() {
        return getService()
                .getPortalBean()
                .compose(new DefaultTransform<>());
    }

    public Observable<SaveTimeBean> saveTime(int id, int save_times) {
        return getService()
                .saveTime(id, save_times)
                .compose(new DefaultTransform<>());
    }
}
