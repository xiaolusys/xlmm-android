package com.jimei.xiaolumeimei.presenter.main;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.jimei.xiaolumeimei.entities.UserInfoBean;
import com.jimei.xiaolumeimei.model.UserNewModel;
import com.jimei.xiaolumeimei.ui.activity.main.MainActivity;
import com.jimei.xiaolumeimei.utils.LoginUtils;
import com.jimei.xiaolumeimei.utils.ViewUtils;
import com.jimei.xiaolumeimei.view.main.IMainView;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;

import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * Created by wisdom on 16/5/18.
 */
public class MainPresenter implements IMainPresenter {
    private static final String TAG = MainPresenter.class.getSimpleName();
    IMainView mainView;
    private TextView tvNickname;
    private UserInfoBean userInfoBean;
    private double budgetCash;
    private TextView msg1;
    private TextView msg2;
    private TextView msg3;
    private TextView tvMoney;
    private TextView tvPoint;
    private TextView tvCoupon;

    public MainPresenter(IMainView mainView) {
        this.mainView = mainView;
    }

    @Override
    public void initDrawer() {
        JUtils.Log(TAG, "侧滑栏初始化");
        if (!(LoginUtils.checkLoginState(((MainActivity) mainView).getApplicationContext()))) {
            if (tvNickname != null) {
                tvNickname.setText("点击登录");
            }
        } else {
            if ((tvNickname != null) && (userInfoBean != null)) {
                tvNickname.setText(userInfoBean.getNick());
            }
            if ((userInfoBean != null) && (!TextUtils.isEmpty(
                    userInfoBean.getThumbnail()))) {
                ViewUtils.loadImgToImgView((Context) mainView, ((MainActivity) mainView).getImgUser(),
                        userInfoBean.getThumbnail());
            }
            if ((null != userInfoBean) && (userInfoBean.getWaitpayNum() > 0)) {
                msg1.setVisibility(View.VISIBLE);
                msg1.setText(Integer.toString(userInfoBean.getWaitpayNum()));
            } else {
                msg1.setVisibility(View.INVISIBLE);
            }
            Log.i(TAG, "" + userInfoBean.getWaitpayNum());

            if ((null != userInfoBean) && (userInfoBean.getWaitgoodsNum() > 0)) {
                msg2.setVisibility(View.VISIBLE);
                msg2.setText(Integer.toString(userInfoBean.getWaitgoodsNum()));
            } else {
                msg2.setVisibility(View.INVISIBLE);
            }

            if ((null != userInfoBean) && (userInfoBean.getRefundsNum() > 0)) {
                msg3.setVisibility(View.VISIBLE);
                msg3.setText(Integer.toString(userInfoBean.getRefundsNum()));
            } else {
                msg3.setVisibility(View.INVISIBLE);
            }
            String pointStr = userInfoBean.getScore() + "";
            tvPoint.setText(pointStr);
            if (userInfoBean.getUserBudget() != null) {
                String moneyStr = (float) (Math.round(userInfoBean.getUserBudget().getBudgetCash() * 100)) / 100 + "";
                tvMoney.setText(moneyStr);
            }
            String couponStr = userInfoBean.getCouponNum() + "";
            tvCoupon.setText(couponStr);
        }
    }

    public void getUserInfo() {
        Subscription subscribe = UserNewModel.getInstance()
                .getProfile()
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<UserInfoBean>() {


                    @Override
                    public void onNext(UserInfoBean userNewBean) {
                        if (userNewBean != null) {
                            userInfoBean = userNewBean;
                            if (LoginUtils.checkLoginState(((MainActivity) mainView).getApplicationContext())) {
                                if ((userNewBean.getThumbnail() != null) && (!userNewBean.getThumbnail()
                                        .isEmpty())) {
                                    ViewUtils.loadImgToImgView((Context) mainView, ((MainActivity) mainView).getImgUser(),
                                            userNewBean.getThumbnail());
                                }
                                if (userInfoBean != null) {
                                    if (userInfoBean.isHasUsablePassword()
                                            && userInfoBean.getMobile() != "") {
                                        ((MainActivity) mainView).getLoginFlag().setVisibility(View.GONE);
                                    } else {
                                        ((MainActivity) mainView).getLoginFlag().setVisibility(View.VISIBLE);
                                    }
                                }
                                if (null != userNewBean.getUserBudget()) {
                                    budgetCash = userNewBean.getUserBudget().getBudgetCash();
                                }
                                JUtils.Log(TAG, "mamaid " + userInfoBean.getXiaolumm().getId());
                                if ((userInfoBean.getXiaolumm() != null) && (userInfoBean.getXiaolumm()
                                        .getId() != 0)) {
                                    ((MainActivity) mainView).getImg_mmentry().setVisibility(View.VISIBLE);
                                } else {
                                    ((MainActivity) mainView).getImg_mmentry().setVisibility(View.INVISIBLE);
                                }
                            }
                        }
                    }
                });
        ((MainActivity) mainView).addSubscription(subscribe);
    }

    @Override
    public void getView() {
        tvNickname = ((MainActivity) mainView).getTvNickname();
        msg1 = ((MainActivity) mainView).getMsg1();
        msg2 = ((MainActivity) mainView).getMsg2();
        msg3 = ((MainActivity) mainView).getMsg3();
        tvMoney = ((MainActivity) mainView).getTvMoney();
        tvPoint = ((MainActivity) mainView).getTvPoint();
        tvCoupon = ((MainActivity) mainView).getTvCoupon();
    }

}
