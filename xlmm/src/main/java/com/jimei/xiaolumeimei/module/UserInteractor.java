package com.jimei.xiaolumeimei.module;

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
import com.jimei.xiaolumeimei.service.ServiceResponse;

import java.util.ArrayList;

import rx.Observable;
import rx.Subscription;

/**
 * Created by wisdom on 17/2/28.
 */

public interface UserInteractor {
    Subscription getUserInfo(ServiceResponse<UserInfoBean> response);

    Subscription setNickName(int userid, NicknameBean nickname, ServiceResponse<UserBean> response);

    Subscription customerLogout(ServiceResponse<LogoutBean> response);

    Subscription getCoinHisList(String page, ServiceResponse<CoinHistoryListBean> response);

    Observable<ArrayList<CouponEntity>> getCouponList(int status);

    Observable<ArrayList<CouponEntity>> getCouponList(int status, int coupon_type);

    Subscription getCouponSelectEntity(String cart_ids, ServiceResponse<CouponSelectEntity> response);

    Subscription wxappLogin(String noncestr, String timestamp, String sign, String headimgurl,
                            String nickname, String openid, String unionid,
                            ServiceResponse<CodeBean> response);

    Subscription needSetInfo(ServiceResponse<NeedSetInfoBean> response);

    Subscription getUserAccount(String platform, String regid, String device_id,
                                ServiceResponse<UserAccountBean> response);

    Subscription getWxPubAuthInfo(ServiceResponse<WxPubAuthInfo> response);

    Subscription userWithDrawCash(String amount, String verify_code, ServiceResponse<UserWithDrawResult> response);

    Subscription getVerifyCode(ServiceResponse<ResultEntity> response);

    Subscription getCodeBean(String mobile, String action, ServiceResponse<CodeBean> response);

    Subscription verifyCode(String mobile, String action, String code, ServiceResponse<CodeBean> response);

    Subscription resetPassword(String mobile, String password1, String password2, String code,
                               ServiceResponse<CodeBean> response);

    Subscription passwordLogin(String username, String password, String next,
                               ServiceResponse<CodeBean> response);

    Subscription openDebug(String debug_secret, ServiceResponse<CodeBean> response);

    Subscription budgetDetailBean(int page, ServiceResponse<BudgetDetailBean> response);

    Subscription getVersion(ServiceResponse<VersionBean> response);
}


