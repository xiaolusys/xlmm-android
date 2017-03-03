package com.jimei.xiaolumeimei.module;

import com.jimei.library.rx.DefaultTransform;
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
import com.jimei.xiaolumeimei.service.api.UserService;

import java.util.ArrayList;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;

/**
 * Created by wisdom on 17/2/28.
 */

public class UserInteractorImpl implements UserInteractor {

    private final UserService service;

    @Inject
    public UserInteractorImpl(UserService service) {
        this.service = service;
    }

    @Override
    public Subscription getUserInfo(ServiceResponse<UserInfoBean> response) {
        return service.getUserInfo()
            .compose(new DefaultTransform<>())
            .subscribe(response);
    }

    @Override
    public Subscription setNickName(int userid, NicknameBean nickname, ServiceResponse<UserBean> response) {
        return service.setNickName(userid, nickname)
            .compose(new DefaultTransform<>())
            .subscribe(response);
    }

    @Override
    public Subscription customerLogout(ServiceResponse<LogoutBean> response) {
        return service.customerLogout()
            .compose(new DefaultTransform<>())
            .subscribe(response);
    }

    @Override
    public Subscription getCoinHisList(String page, ServiceResponse<CoinHistoryListBean> response) {
        return service.getCoinHisList(page)
            .compose(new DefaultTransform<>())
            .subscribe(response);
    }

    @Override
    public Observable<ArrayList<CouponEntity>> getCouponList(int status) {
        return service.getCouponList(status)
            .compose(new DefaultTransform<>());
    }

    @Override
    public Observable<ArrayList<CouponEntity>> getCouponList(int status, int coupon_type) {
        return service.getCouponList(status, coupon_type)
            .compose(new DefaultTransform<>());
    }

    @Override
    public Subscription getCouponSelectEntity(String cart_ids, ServiceResponse<CouponSelectEntity> response) {
        return service.getCouponSelectEntity(cart_ids)
            .compose(new DefaultTransform<>())
            .subscribe(response);
    }

    @Override
    public Subscription wxappLogin(String noncestr, String timestamp, String sign, String headimgurl,
                                   String nickname, String openid, String unionid, ServiceResponse<CodeBean> response) {
        return service.wxappLogin(noncestr, timestamp, sign, headimgurl, nickname, openid, unionid, "android")
            .compose(new DefaultTransform<>())
            .subscribe(response);
    }

    @Override
    public Subscription needSetInfo(ServiceResponse<NeedSetInfoBean> response) {
        return service.needSetInfo()
            .compose(new DefaultTransform<>())
            .subscribe(response);
    }

    @Override
    public Subscription getUserAccount(String platform, String regid, String device_id,
                                       ServiceResponse<UserAccountBean> response) {
        return service.getUserAccount(platform, regid, device_id)
            .compose(new DefaultTransform<>())
            .subscribe(response);
    }

    @Override
    public Subscription getWxPubAuthInfo(ServiceResponse<WxPubAuthInfo> response) {
        return service.getWxPubAuthInfo()
            .compose(new DefaultTransform<>())
            .subscribe(response);
    }

    @Override
    public Subscription userWithDrawCash(String amount, String verify_code,
                                         ServiceResponse<UserWithDrawResult> response) {
        return service.userWithDrawCash(amount, verify_code)
            .compose(new DefaultTransform<>())
            .subscribe(response);
    }

    @Override
    public Subscription getVerifyCode(ServiceResponse<ResultEntity> response) {
        return service.getVerifyCode()
            .compose(new DefaultTransform<>())
            .subscribe(response);
    }

    @Override
    public Subscription getCodeBean(String mobile, String action, ServiceResponse<CodeBean> response) {
        return service.getCodeBean(mobile, action)
            .compose(new DefaultTransform<>())
            .subscribe(response);
    }

    @Override
    public Subscription verifyCode(String mobile, String action, String code, ServiceResponse<CodeBean> response) {
        return service.verifyCode(mobile, action, code, "android")
            .compose(new DefaultTransform<>())
            .subscribe(response);
    }

    @Override
    public Subscription resetPassword(String mobile, String password1, String password2, String code,
                                      ServiceResponse<CodeBean> response) {
        return service.resetPassword(mobile, password1, password2, code)
            .compose(new DefaultTransform<>())
            .subscribe(response);
    }

    @Override
    public Subscription passwordLogin(String username, String password, String next,
                                      ServiceResponse<CodeBean> response) {
        return service.passwordLogin(username, password, next, "android")
            .compose(new DefaultTransform<>())
            .subscribe(response);
    }

    @Override
    public Subscription openDebug(String debug_secret, ServiceResponse<CodeBean> response) {
        return service.openDebug(debug_secret)
            .compose(new DefaultTransform<>())
            .subscribe(response);
    }

    @Override
    public Subscription budgetDetailBean(int page, ServiceResponse<BudgetDetailBean> response) {
        return service.budgetDetailBean(page)
            .compose(new DefaultTransform<>())
            .subscribe(response);
    }

    @Override
    public Subscription getVersion(ServiceResponse<VersionBean> response) {
        return service.getVersion()
            .compose(new DefaultTransform<>())
            .subscribe(response);
    }
}
