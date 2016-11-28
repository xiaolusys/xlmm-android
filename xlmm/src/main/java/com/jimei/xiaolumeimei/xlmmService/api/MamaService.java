package com.jimei.xiaolumeimei.xlmmService.api;

import com.jimei.xiaolumeimei.entities.AwardCarryBean;
import com.jimei.xiaolumeimei.entities.BoutiqueListBean;
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
import com.jimei.xiaolumeimei.entities.RecentCarryBean;
import com.jimei.xiaolumeimei.entities.ResponseResultBean;
import com.jimei.xiaolumeimei.entities.ResultBean;
import com.jimei.xiaolumeimei.entities.ResultEntity;
import com.jimei.xiaolumeimei.entities.SaveTimeBean;
import com.jimei.xiaolumeimei.entities.UserInfoBean;
import com.jimei.xiaolumeimei.entities.WeekTaskRewardBean;
import com.jimei.xiaolumeimei.entities.WithdrawCashHisBean;
import com.jimei.xiaolumeimei.entities.WxQrcode;

import java.util.List;

import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by wisdom on 16/11/23.
 */

public interface MamaService {

    @GET("/rest/v1/pmt/cashout")
    Observable<WithdrawCashHisBean> getWithdrawCashHis(
            @Query("page") String page);

    //获取粉丝列表
    @GET("/rest/v2/mama/fans")
    Observable<MamaFansBean> getMamaFans(
            @Query("page") String page);

    //妈妈余额兑换现金消费券
    @GET("/rest/v1/pmt/cashout/exchange_coupon")
    Observable<DrawCouponBean> drawCoupon(
            @Query("template_id") String template_id,
            @Query("exchange_num") String exchange_num);

    @FormUrlEncoded
    @POST("/rest/v1/pmt/cashout/noaudit_cashout")
    Observable<ResultEntity> getNoauditCashout(
            @Field("amount") double amount,
            @Field("verify_code") String verify_code);

    //获取访客列表
    @GET("/rest/v2/mama/visitor")
    Observable<MMVisitorsBean> getMamaVisitor(
            @Query("recent") int recent,
            @Query("page") int page);

    //创建提款单信息
    @FormUrlEncoded
    @POST("/rest/v1/pmt/cashout")
    Observable<ResponseResultBean> withdraw_cash(
            @Field("choice") String fund_type);

    //妈妈钱包转账到小鹿钱包
    @FormUrlEncoded
    @POST("/rest/v1/pmt/cashout/cashout_to_budget")
    Observable<ResponseResultBean> toWallet(
            @Field("cashout_amount") String cashout_amount);

    //cancel提款单信息
    @FormUrlEncoded
    @POST("/rest/v1/pmt/cashout/cancal_cashout")
    Observable<ResponseResultBean> cancel_withdraw_cash(
            @Field("id") String id);

    @GET("/rest/v2/mama/fortune")
    Observable<MamaFortune> getMamaFortune();

    @GET("/rest/v2/mama/activevalue")
    Observable<MamaLivenessBean> getMamaLiveness(
            @Query("page") String page);

    //获得未来粉丝列表
    @GET("/rest/v2/potential_fans")
    Observable<PotentialFans> getPotentialFans(
            @Query("page") String page);

    @GET("/rest/v1/pmt/xlmm/get_register_pro_info")
    Observable<MaMaReNewBean> getRegisterProInfo();

    @FormUrlEncoded
    @POST("/rest/v1/pmt/xlmm/mama_register_pay")
    Observable<Object> mamaRegisterPay(
            @Field("product_id") String product_id,
            @Field("sku_id") String sku_id,
            @Field("payment") String payment,
            @Field("channel") String channel,
            @Field("num") String num,
            @Field("post_fee") String post_fee,
            @Field("discount_fee") String discount_fee,
            @Field("uuid") String uuid,
            @Field("total_fee") String total_fee,
            @Field("wallet_renew_deposit") String wallet_renew_deposit);

    @FormUrlEncoded
    @POST("/rest/v1/pmt/cashout/exchange_deposit")
    Observable<Response<ResultBean>> exchangeDeposit(
            @Field("exchange_type") String exchange_type);

    @GET("/rest/v2/mama/rank/carry_total_rank")
    Observable<List<PersonalCarryRankBean>> getPersonalCarryRankBean();

    @GET("/rest/v2/rank/carry_duration_rank")
    Observable<List<PersonalCarryRankBean>> getWeekPersonalCarryRankBean();

    @GET("/rest/v2/mama/rank/self_rank")
    Observable<Response<PersonalCarryRankBean>> getPersonalSelfCarryRankBean();

    @GET("/rest/v2/mama/rank/{id}/get_team_members")
    Observable<Response<List<PersonalCarryRankBean>>> getTeamMembers(
            @Path("id") String id);

    @GET("/rest/v2/mama/teamrank/self_rank")
    Observable<Response<PersonalCarryRankBean>> getTeamSelfRank();

    @GET("/rest/v2/mama/teamrank/carry_total_rank")
    Observable<List<PersonalCarryRankBean>> getTeamCarryRankBean();

    @GET("/rest/v2/mama/teamrank/carry_duration_rank")
    Observable<List<PersonalCarryRankBean>> getWeekTeamCarryRankBean();

    @GET("/rest/v2/mama/mission/weeklist")
    Observable<WeekTaskRewardBean> getTaskReward();

    @GET("/rest/v2/categorys")
    Observable<List<CategoryBean>> getCategory();

    @GET("/rest/v2/cashout_policy")
    Observable<CashoutPolicy> getCashoutPolicy();

    @GET("/rest/v2/modelproducts/product_choice")
    Observable<ChooseListBean> getChooseList(
            @Query("page") int page);

    @GET("/rest/v2/modelproducts/product_choice")
    Observable<ChooseListBean> getChooseListBySort(
            @Query("page") int page,
            @Query("sort_field") String sort_field,
            @Query("reverse") int reverse);

    @GET("/rest/v2/modelproducts/product_choice")
    Observable<ChooseListBean> getChooseListByCid(
            @Query("page") int page,
            @Query("cid") String cid);

    @GET("/rest/v2/modelproducts/product_choice")
    Observable<ChooseListBean> getChooseList(
            @Query("page") int page,
            @Query("sort_field") String sort_field,
            @Query("cid") String cid,
            @Query("reverse") int reverse);

    @GET("/rest/v1/pmt/cushop/customer_shop")
    Observable<MMShoppingBean> getShareShopping();

    @GET("/rest/v1/mmwebviewconfig")
    Observable<MamaUrl> getMamaUrl(
            @Query("version") String version);

    @GET("/rest/v2/mama/message/self_list")
    Observable<MamaSelfListBean> getMaMaselfList();

    //获取用户信息
    @GET("/rest/v1/users/profile")
    Observable<UserInfoBean> getUserInfo();

    @GET("/rest/v2/ordercarry/get_latest_order_carry")
    Observable<List<MiPushOrderCarryBean>> getLatestOrderCarry();

    @GET("/rest/v2/mama/dailystats")
    Observable<RecentCarryBean> getRecentCarry(
            @Query("from") String from,
            @Query("days") String days);

    @GET("/rest/v1/pmt/ninepic")
    Observable<List<NinePicBean>> getNinePic();

    @GET("/rest/v1/pmt/ninepic")
    Observable<List<NinePicBean>> getNinePic(
            @Query("sale_category") int sale_category);

    @GET("/rest/v1/pmt/ninepic/get_nine_pic_by_modelid")
    Observable<List<NinePicBean>> getNinePicByModelId(
            @Query("model_id") int model_id);

    @GET("/rest/v1/pmt/ninepic")
    Observable<List<NinePicBean>> getNinePic(
            @Query("ordering") String ordering);

    @GET("/rest/v2/mama/carry")
    Observable<CarryLogListBean> getMamaAllCarryLogs(
            @Query("page") String page);

    @GET("/rest/v2/mama/ordercarry")
    Observable<OderCarryBean> getMamaAllOderCarryLogs(
            @Query("page") String page);

    @GET("/rest/v2/mama/ordercarry")
    Observable<OderCarryBean> getMamaAllOderCarryLogs(
            @Query("carry_type") String carry_type,
            @Query("page") int page);

    @GET("/rest/v2/mama/awardcarry")
    Observable<AwardCarryBean> getMamaAllAwardCarryLogs(
            @Query("page") String page);

    @GET("/rest/v2/mama/clickcarry")
    Observable<ClickcarryBean> getMamaAllClickCarryLogs(
            @Query("page") String page);

    @GET("/rest/v2/qrcode/get_wxpub_qrcode")
    Observable<WxQrcode> getWxCode();

    @GET("/rest/v1/portal")
    Observable<PortalBean> getPortalBean();

    @FormUrlEncoded
    @PATCH("/rest/v1/pmt/ninepic/{id}")
    Observable<SaveTimeBean> saveTime(
            @Path("id") int id,
            @Field("save_times") int save_times);

    @GET("/rest/v2/modelproducts/boutique")
    Observable<BoutiqueListBean> getBoutiqueList(
            @Query("page")int page
    );
}
