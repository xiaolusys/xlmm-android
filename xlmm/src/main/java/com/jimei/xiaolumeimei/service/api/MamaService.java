package com.jimei.xiaolumeimei.service.api;

import com.jimei.xiaolumeimei.entities.AwardCarryBean;
import com.jimei.xiaolumeimei.entities.CarryLogListBean;
import com.jimei.xiaolumeimei.entities.CategoryBean;
import com.jimei.xiaolumeimei.entities.ChooseListBean;
import com.jimei.xiaolumeimei.entities.ClickcarryBean;
import com.jimei.xiaolumeimei.entities.MMShoppingBean;
import com.jimei.xiaolumeimei.entities.MMVisitorsBean;
import com.jimei.xiaolumeimei.entities.MamaFansBean;
import com.jimei.xiaolumeimei.entities.MamaFortune;
import com.jimei.xiaolumeimei.entities.MamaSelfListBean;
import com.jimei.xiaolumeimei.entities.MamaUrl;
import com.jimei.xiaolumeimei.entities.NinePicBean;
import com.jimei.xiaolumeimei.entities.OderCarryBean;
import com.jimei.xiaolumeimei.entities.PortalBean;
import com.jimei.xiaolumeimei.entities.ProductListBean;
import com.jimei.xiaolumeimei.entities.RecentCarryBean;
import com.jimei.xiaolumeimei.entities.ResponseResultBean;
import com.jimei.xiaolumeimei.entities.ResultEntity;
import com.jimei.xiaolumeimei.entities.SaveTimeBean;
import com.jimei.xiaolumeimei.entities.UserInfoBean;
import com.jimei.xiaolumeimei.entities.WithdrawCashHisBean;
import com.jimei.xiaolumeimei.entities.WxQrcode;

import java.util.List;

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

    //获取访客列表
    @GET("/rest/v2/mama/visitor")
    Observable<MMVisitorsBean> getMamaVisitor(
        @Query("recent") int recent,
        @Query("page") int page);

    //创建提款单信息
    @FormUrlEncoded
    @POST("/rest/v1/pmt/cashout")
    Observable<ResultEntity> withdraw_cash(
        @Field("choice") String fund_type);

    @FormUrlEncoded
    @POST("/rest/v2/mmcashout/cash_out_2_budget")
    Observable<ResultEntity> toWallet(
        @Field("value") String value);

    //cancel提款单信息
    @FormUrlEncoded
    @POST("/rest/v1/pmt/cashout/cancal_cashout")
    Observable<ResponseResultBean> cancel_withdraw_cash(
        @Field("id") String id);

    @GET("/rest/v2/mama/fortune")
    Observable<MamaFortune> getMamaFortune();

    @GET("/rest/v2/categorys")
    Observable<List<CategoryBean>> getCategory();

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
    Observable<ProductListBean> getBoutiqueList(
        @Query("page") int page
    );
}
