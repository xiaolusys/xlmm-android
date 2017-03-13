package com.jimei.xiaolumeimei.service.api;

import com.jimei.xiaolumeimei.entities.BudgetDetailBean;
import com.jimei.xiaolumeimei.entities.CodeBean;
import com.jimei.xiaolumeimei.entities.CoinHistoryListBean;
import com.jimei.xiaolumeimei.entities.CouponEntity;
import com.jimei.xiaolumeimei.entities.CouponSelectEntity;
import com.jimei.xiaolumeimei.entities.LogoutBean;
import com.jimei.xiaolumeimei.entities.NeedSetInfoBean;
import com.jimei.xiaolumeimei.entities.NicknameBean;
import com.jimei.xiaolumeimei.entities.ResultEntity;
import com.jimei.xiaolumeimei.entities.UserAccountBean;
import com.jimei.xiaolumeimei.entities.UserBean;
import com.jimei.xiaolumeimei.entities.UserInfoBean;
import com.jimei.xiaolumeimei.entities.UserWithDrawResult;
import com.jimei.xiaolumeimei.entities.VersionBean;
import com.jimei.xiaolumeimei.entities.WxPubAuthInfo;

import java.util.ArrayList;

import retrofit2.http.Body;
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

public interface UserService {
    //获取用户信息
    @GET("/rest/v1/users/profile")
    Observable<UserInfoBean> getUserInfo();

    //设置用户昵称
    @PATCH("/rest/v1/users/{id}")
    Observable<UserBean> setNickName(
        @Path("id") int id,
        @Body NicknameBean nickname);

    @POST("/rest/v1/users/customer_logout")
    Observable<LogoutBean> customerLogout();

    //获取用户积分记录信息
    @GET("/rest/v2/xiaolucoin/history")
    Observable<CoinHistoryListBean> getCoinHisList(
        @Query("page") int page);

    //获取优惠券
    @GET("/rest/v1/usercoupons/get_user_coupons")
    Observable<ArrayList<CouponEntity>> getCouponList(
        @Query("status") int status);

    //获取优惠券
    @GET("/rest/v1/usercoupons/get_user_coupons")
    Observable<ArrayList<CouponEntity>> getCouponList(
        @Query("status") int status,
        @Query("coupon_type") int coupon_type);

    //购物车选择优惠券
    @GET("/rest/v1/usercoupons/coupon_able")
    Observable<CouponSelectEntity> getCouponSelectEntity(
        @Query("cart_ids") String cart_ids);

    @FormUrlEncoded
    @POST("/rest/v2/weixinapplogin")
    Observable<CodeBean> wxappLogin(
        @Query("noncestr") String noncestr,
        @Query("timestamp") String timestamp,
        @Query("sign") String sign,
        @Field("headimgurl") String headimgurl,
        @Field("nickname") String nickname,
        @Field("openid") String openid,
        @Field("unionid") String unionid,
        @Field("devtype") String devtype);

    @GET("/rest/v1/users/need_set_info")
    Observable<NeedSetInfoBean> needSetInfo();

    //get push useraccount
    @FormUrlEncoded
    @POST("/rest/v1/push/set_device")
    Observable<UserAccountBean> getUserAccount(
        @Field("platform") String platform,
        @Field("regid") String regid,
        @Field("device_id") String device_id);

    @GET("/rest/v1/users/get_wxpub_authinfo")
    Observable<WxPubAuthInfo> getWxPubAuthInfo();

    //创建提款信息
    @FormUrlEncoded
    @POST("/rest/v1/users/budget_cash_out")
    Observable<UserWithDrawResult> userWithDrawCash(
        @Field("cashout_amount") String amount,
        @Field("verify_code") String verify_code);

    @POST("/rest/v2/request_cashout_verify_code")
    Observable<ResultEntity> getVerifyCode();

    //发送验证码
    @FormUrlEncoded
    @POST("/rest/v2/send_code")
    Observable<CodeBean> getCodeBean(
        @Field("mobile") String mobile,
        @Field("action") String action);

    //验证码验证
    @FormUrlEncoded
    @POST("/rest/v2/verify_code")
    Observable<CodeBean> verifyCode(
        @Field("mobile") String mobile,
        @Field("action") String action,
        @Field("verify_code") String code,
        @Field("devtype") String devtype);

    //设置账号密码
    @FormUrlEncoded
    @POST("/rest/v2/reset_password")
    Observable<CodeBean> resetPassword(
        @Field("mobile") String mobile,
        @Field("password1") String password1,
        @Field("password2") String password2,
        @Field("verify_code") String code);

    //用户账号密码
    @FormUrlEncoded
    @POST("/rest/v2/passwordlogin")
    Observable<CodeBean> passwordLogin(
        @Field("username") String username,
        @Field("password") String password,
        @Field("next") String next,
        @Field("devtype") String devtype);

    @FormUrlEncoded
    @POST("/rest/v1/users/open_debug_for_app")
    Observable<CodeBean> openDebug(
        @Field("debug_secret") String debug_secret);


    @GET("/rest/v1/users/get_budget_detail")
    Observable<BudgetDetailBean> budgetDetailBean(
        @Query("page") int page);

    @GET("/sale/apprelease/newversion")
    Observable<VersionBean> getVersion();
}
