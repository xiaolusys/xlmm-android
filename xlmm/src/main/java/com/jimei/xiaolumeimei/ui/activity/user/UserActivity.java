package com.jimei.xiaolumeimei.ui.activity.user;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.jimei.library.utils.DateUtils;
import com.jimei.library.utils.JUtils;
import com.jimei.library.utils.ViewUtils;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.XlmmApp;
import com.jimei.xiaolumeimei.base.BaseMVVMActivity;
import com.jimei.xiaolumeimei.base.CommonWebViewActivity;
import com.jimei.xiaolumeimei.data.XlmmConst;
import com.jimei.xiaolumeimei.databinding.ActivityUserBinding;
import com.jimei.xiaolumeimei.entities.MMShoppingBean;
import com.jimei.xiaolumeimei.entities.MamaFortune;
import com.jimei.xiaolumeimei.entities.MamaSelfListBean;
import com.jimei.xiaolumeimei.entities.MamaUrl;
import com.jimei.xiaolumeimei.entities.RecentCarryBean;
import com.jimei.xiaolumeimei.entities.UserInfoBean;
import com.jimei.xiaolumeimei.entities.WxQrcode;
import com.jimei.xiaolumeimei.entities.event.WebViewEvent;
import com.jimei.xiaolumeimei.module.VipInteractor;
import com.jimei.xiaolumeimei.service.ServiceResponse;
import com.jimei.xiaolumeimei.ui.activity.trade.AllOrdersActivity;
import com.jimei.xiaolumeimei.ui.activity.trade.AllRefundsActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MMCarryActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MMFansActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MMNinePicActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MMShareCodeWebViewActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MMShoppingListActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MMStoreWebViewActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MamaChooseActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MamaVisitorActivity;
import com.jimei.xiaolumeimei.util.JumpUtils;
import com.jimei.xiaolumeimei.util.LoginUtils;

import org.greenrobot.eventbus.EventBus;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.udesk.UdeskConst;
import cn.udesk.UdeskSDKManager;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;

/**
 * Created by wisdom on 17/2/14.
 */

public class UserActivity extends BaseMVVMActivity<ActivityUserBinding> implements View.OnClickListener {

    private boolean mamaFlag;
    private String shareLink;
    private String shopLink;
    private String msgUrl;
    private int orderNum;
    private String carryLogMoney;
    private String hisConfirmedCashOut;
    private int mCurrent_dp_turns_num;
    private String fansUrl;
    private int count;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_user;
    }

    @Override
    public View getLoadingView() {
        return b.layout;
    }

    private void setIcon(int id, ImageView imageView) {
        Drawable drawable;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawable = getDrawable(id);
        } else {
            drawable = getResources().getDrawable(id);
        }
        assert drawable != null;
        int rgb = Color.rgb((int) (Math.random() * 256),
            (int) (Math.random() * 256), (int) (Math.random() * 256));
        drawable.setColorFilter(new PorterDuffColorFilter(rgb, PorterDuff.Mode.SRC_IN));
        imageView.setImageDrawable(drawable);
    }

    protected void init() {
        count = 1;
        showIndeterminateProgressDialog(true);
        mamaFlag = false;
        if (LoginUtils.checkLoginState(this)) {
            addSubscription(XlmmApp.getMainInteractor(this)
                .getProfile(new ServiceResponse<UserInfoBean>() {
                    @Override
                    public void onNext(UserInfoBean userInfoBean) {
                        fillDataToView(userInfoBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        hideIndeterminateProgressDialog();
                        if (e instanceof UnknownHostException && !JUtils.isNetWorkAvilable()) {
                            showNetworkError();
                        } else if (e instanceof HttpException) {
                            if (((HttpException) e).code() == 403) {
                                LoginUtils.delLoginInfo(UserActivity.this);
                                fillDataToView(null);
                            } else {
                                JUtils.Toast("网络异常,请下拉刷新重试!");
                            }
                        } else {
                            JUtils.Toast("信息获取失败,请下拉刷新重试!");
                        }
                    }
                }));
        } else {
            fillDataToView(null);
        }
    }

    @Override
    public void setListener() {
        b.rlHead.setOnClickListener(this);
        b.imgUser.setOnClickListener(this);
        b.tvNickname.setOnClickListener(this);
        b.llMoney.setOnClickListener(this);
        b.llScore.setOnClickListener(this);
        b.llDiscount.setOnClickListener(this);
        b.rlPay.setOnClickListener(this);
        b.rlWait.setOnClickListener(this);
        b.rlRefund.setOnClickListener(this);
        b.rlOrder.setOnClickListener(this);
        b.llShop.setOnClickListener(this);
        b.llVipDesc.setOnClickListener(this);

        b.btnFinish.setOnClickListener(this);
        b.btnService.setOnClickListener(this);
        b.llShare.setOnClickListener(this);
        b.llPush.setOnClickListener(this);
        b.llChoose.setOnClickListener(this);
        b.llShop.setOnClickListener(this);
        b.tvMsg.setOnClickListener(this);
        b.visitLayout.setOnClickListener(this);
        b.orderLayout.setOnClickListener(this);
        b.fundLayout.setOnClickListener(this);
        b.llIncome.setOnClickListener(this);
        b.llVisit.setOnClickListener(this);
        b.llOrder.setOnClickListener(this);
        b.llFans.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_finish) {
            finish();
        } else if (!LoginUtils.checkLoginState(this)) {
            Bundle bundle = new Bundle();
            bundle.putString("login", "main");
            readyGo(LoginActivity.class, bundle);
        } else {
            Bundle bundle = new Bundle();
            switch (v.getId()) {
                case R.id.rl_head:
                case R.id.img_user:
                case R.id.tv_nickname:
                    readyGo(InformationActivity.class);
                    break;
                case R.id.ll_money:
                    readyGo(WalletActivity.class);
                    break;
                case R.id.ll_score:
                    readyGo(CoinActivity.class);
                    break;
                case R.id.ll_discount:
                    readyGo(AllCouponActivity.class);
                    break;
                case R.id.rl_pay:
                    bundle.putInt("fragment", 2);
                    readyGo(AllOrdersActivity.class, bundle);
                    break;
                case R.id.rl_wait:
                    bundle.putInt("fragment", 3);
                    readyGo(AllOrdersActivity.class, bundle);
                    break;
                case R.id.rl_refund:
                    readyGo(AllRefundsActivity.class);
                    break;
                case R.id.rl_order:
                    bundle.putInt("fragment", 1);
                    readyGo(AllOrdersActivity.class, bundle);
                    break;
                case R.id.btn_service:
                    UdeskSDKManager.getInstance().showRobotOrConversation(this);
                    break;
                case R.id.ll_share:
                    if (shopLink != null && !"".equals(shopLink)) {
                        JumpUtils.jumpToWebViewWithCookies(this, shopLink, -1,
                            MMStoreWebViewActivity.class);
                    }
                    break;
                case R.id.ll_vip_desc:
                    if (count > 3) {
                        setIcon(R.drawable.drawer_pay, b.payImg);
                        setIcon(R.drawable.drawer_car, b.waitImg);
                        setIcon(R.drawable.drawer_refund, b.refundImg);
                        setIcon(R.drawable.drawer_order, b.orderImg);
                        setIcon(R.drawable.icon_vip_share, b.imgShare);
                        setIcon(R.drawable.icon_vip_push, b.imgPush);
                        setIcon(R.drawable.icon_vip_choose, b.imgChoose);
                        setIcon(R.drawable.icon_vip_shop, b.imgInvite);
                    } else {
                        count++;
                    }
                    break;
                case R.id.ll_push:
                    SharedPreferences preferences = getSharedPreferences("push_num", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    String str = null;
                    try {
                        str = DateUtils.dateToString(new Date(), DateUtils.yyyyMMDD);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    editor.putString("time", str);
                    editor.putInt("num", mCurrent_dp_turns_num);
                    editor.apply();
                    b.pushNum.setVisibility(View.GONE);
                    addSubscription(XlmmApp.getVipInteractor(this)
                        .getWxCode(new ServiceResponse<WxQrcode>() {
                            @Override
                            public void onNext(WxQrcode wxQrcode) {
                                Intent intent = new Intent(UserActivity.this, MMNinePicActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("codeLink", wxQrcode.getQrcode_link());
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        }));
                    break;
                case R.id.ll_choose:
                    readyGo(MamaChooseActivity.class);
                    break;
                case R.id.ll_shop:
                    if (shareLink != null && !"".equals(shareLink)) {
                        JumpUtils.jumpToWebViewWithCookies(this, shareLink, 26,
                            MMShareCodeWebViewActivity.class);
                    }
                    break;
                case R.id.tv_msg:
                    if (msgUrl != null && !"".equals(msgUrl)) {
                        b.imageNotice.setVisibility(View.GONE);
                        JumpUtils.jumpToWebViewWithCookies(this, msgUrl, -1,
                            CommonWebViewActivity.class, "信息通知", false);
                    }
                    break;
                case R.id.visit_layout:
                    readyGo(MamaVisitorActivity.class);
                    break;
                case R.id.order_layout:
                    bundle.putInt("orderNum", orderNum);
                    readyGo(MMShoppingListActivity.class, bundle);
                    break;
                case R.id.fund_layout:
                    bundle.putString("carrylogMoney", carryLogMoney);
                    bundle.putString("hisConfirmedCashOut", hisConfirmedCashOut);
                    readyGo(MMCarryActivity.class, bundle);
                    break;
                case R.id.ll_income:
                    bundle.putString("carrylogMoney", carryLogMoney);
                    bundle.putString("hisConfirmedCashOut", hisConfirmedCashOut);
                    readyGo(MMCarryActivity.class, bundle);
                    break;
                case R.id.ll_visit:
                    readyGo(MamaVisitorActivity.class);
                    break;
                case R.id.ll_order:
                    bundle.putInt("orderNum", orderNum);
                    readyGo(MMShoppingListActivity.class, bundle);
                    break;
                case R.id.ll_fans:
                    if (fansUrl != null && !"".equals(fansUrl)) {
                        EventBus.getDefault().postSticky(new WebViewEvent(fansUrl));
                        readyGo(MMFansActivity.class);
                    }
                    break;
            }
        }
    }

    private void fillDataToView(UserInfoBean userInfoBean) {
        if (userInfoBean != null) {
            mamaFlag = (userInfoBean.getXiaolumm() != null) && (userInfoBean.getXiaolumm().getId() != 0);
            if (mamaFlag) {
                String id = userInfoBean.getId() + "";
                Map<String, String> info = new HashMap<>();
                info.put(UdeskConst.UdeskUserInfo.USER_SDK_TOKEN, id);
                info.put(UdeskConst.UdeskUserInfo.NICK_NAME, userInfoBean.getNick() + "(ID:" + id + ")");
                info.put(UdeskConst.UdeskUserInfo.CELLPHONE, userInfoBean.getMobile());
                UdeskSDKManager.getInstance().setUserInfo(this, id, info);
                getMamaData();
                b.btnService.setVisibility(View.VISIBLE);
                b.llVip.setVisibility(View.VISIBLE);
                b.llVipDesc.setVisibility(View.VISIBLE);
            } else {
                b.btnService.setVisibility(View.GONE);
                b.llVip.setVisibility(View.GONE);
                b.llVipDesc.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(userInfoBean.getThumbnail())) {
                ViewUtils.loadImgToImgView(this, b.imgUser, userInfoBean.getThumbnail());
            } else {
                b.imgUser.setImageResource(R.drawable.img_head);
            }
            b.tvNickname.setText(userInfoBean.getNick());
            if (userInfoBean.isHasUsablePassword() && userInfoBean.getMobile() != null
                && !"".equals(userInfoBean.getMobile())) {
                b.loginFlag.setVisibility(View.GONE);
            } else {
                b.loginFlag.setVisibility(View.VISIBLE);
            }
            if (userInfoBean.getUserBudget() != null) {
                b.tvMoney.setText("" + userInfoBean.getUserBudget().getBudgetCash());
            } else {
                b.tvMoney.setText("0");
            }
            b.tvScore.setText("" + userInfoBean.getXiaoluCoin());
            b.tvDiscount.setText("" + userInfoBean.getCouponNum());
            if (userInfoBean.getWaitgoodsNum() > 0) {
                b.textWait.setText(userInfoBean.getWaitgoodsNum() + "");
                b.textWait.setVisibility(View.VISIBLE);
            } else {
                b.textWait.setText("0");
                b.textWait.setVisibility(View.INVISIBLE);
            }
            if (userInfoBean.getWaitpayNum() > 0) {
                b.textPay.setText(userInfoBean.getWaitpayNum() + "");
                b.textPay.setVisibility(View.VISIBLE);
            } else {
                b.textPay.setText("0");
                b.textPay.setVisibility(View.INVISIBLE);
            }
            if (userInfoBean.getRefundsNum() > 0) {
                b.textRefund.setText(userInfoBean.getRefundsNum() + "");
                b.textRefund.setVisibility(View.VISIBLE);
            } else {
                b.textRefund.setText("0");
                b.textRefund.setVisibility(View.INVISIBLE);
            }
        } else {
            b.imgUser.setImageResource(R.drawable.img_head);
            b.btnService.setVisibility(View.GONE);
            b.llVip.setVisibility(View.GONE);
            b.loginFlag.setVisibility(View.GONE);
            b.tvNickname.setText("点击登录");
            b.tvDiscount.setText("0");
            b.tvScore.setText("0");
            b.tvMoney.setText("0.00");
            b.textPay.setVisibility(View.GONE);
            b.llVipDesc.setVisibility(View.GONE);
            b.textWait.setVisibility(View.GONE);
            b.textRefund.setVisibility(View.GONE);
        }
        hideIndeterminateProgressDialog();
    }

    private void getMamaData() {
        UdeskSDKManager.getInstance().initApiKey(this, XlmmConst.UDESK_URL, XlmmConst.UDESK_KEY);
        VipInteractor vipInteractor = XlmmApp.getVipInteractor(this);
        addSubscription(Observable.mergeDelayError(vipInteractor.getMamaUrl(), vipInteractor.getShareShopping(),
            vipInteractor.getMamaFortune(), vipInteractor.getRecentCarry("0", "7"), vipInteractor.getMaMaSelfList())
            .subscribe(o -> {
                if (o != null) {
                    if (o instanceof MamaUrl) {
                        initUrl((MamaUrl) o);
                    } else if (o instanceof MMShoppingBean) {
                        shopLink = ((MMShoppingBean) o).getShopInfo().getPreviewShopLink();
                    } else if (o instanceof MamaFortune) {
                        initFortune((MamaFortune) o);
                    } else if (o instanceof RecentCarryBean) {
                        if (((RecentCarryBean) o).getResults().size() > 0) {
                            initTodatText(((RecentCarryBean) o).getResults());
                        }
                    } else if (o instanceof MamaSelfListBean) {
                        initTask((MamaSelfListBean) o);
                    }
                }
            }, Throwable::printStackTrace));
    }

    private void initUrl(MamaUrl mamaUrl) {
        MamaUrl.ResultsBean resultsBean = mamaUrl.getResults().get(0);
        shareLink = resultsBean.getExtra().getInvite();
        msgUrl = resultsBean.getExtra().getNotice();
        fansUrl = resultsBean.getExtra().getFans_explain();
    }

    public void initTodatText(List<RecentCarryBean.ResultsEntity> his_refund) {
        b.chartVisit.setText(Integer.toString(his_refund.get(his_refund.size() - 1).getVisitorNum()));
        b.chartOrder.setText(Integer.toString(his_refund.get(his_refund.size() - 1).getOrderNum()));
        b.chartFund.setText(Double.toString(
            (double) (Math.round(his_refund.get(his_refund.size() - 1).getCarry() * 100)) / 100));
    }

    private void initTask(MamaSelfListBean bean) {
        int unread_cnt = bean.getUnread_cnt();
        if (unread_cnt > 0) {
            b.imageNotice.setVisibility(View.VISIBLE);
        } else {
            b.imageNotice.setVisibility(View.GONE);
        }
        List<MamaSelfListBean.ResultsBean> results = bean.getResults();
        List<String> list = new ArrayList<>();
        for (int i = 0; i < results.size(); i++) {
            list.add(results.get(i).getTitle());
        }
        b.marqueeView.startWithList(list);
    }

    private void initFortune(MamaFortune mamaFortune) {
        MamaFortune.MamaFortuneBean fortune = mamaFortune.getMamaFortune();
        orderNum = fortune.getOrderNum();
        carryLogMoney = fortune.getCarryValue() + "";
        if (fortune.getExtraInfo() != null) {
            hisConfirmedCashOut = fortune.getExtraInfo().getHisConfirmedCashOut();
        }
        b.tvId.setText("ID:" + fortune.getMamaId());
        b.tvMamaName.setText(fortune.getMamaLevelDisplay());
        b.tvCarryValue.setText(fortune.getCarryValue() + "元");
        b.tvOrder.setText(fortune.getOrderNum() + "个");
        b.tvFans.setText(fortune.getFansNum() + "个");
        SharedPreferences preferences = getSharedPreferences("push_num", Context.MODE_PRIVATE);
        String time = preferences.getString("time", "");
        int num = preferences.getInt("num", 0);
        mCurrent_dp_turns_num = fortune.getCurrent_dp_turns_num();
        try {
            String str = DateUtils.dateToString(new Date(), DateUtils.yyyyMMDD);
            if (time.equals(str)) {
                if (fortune.getCurrent_dp_turns_num() > num) {
                    b.pushNum.setText((mCurrent_dp_turns_num - num) + "");
                    b.pushNum.setVisibility(View.VISIBLE);
                } else {
                    b.pushNum.setVisibility(View.GONE);
                }
            } else {
                if (fortune.getCurrent_dp_turns_num() > 0) {
                    b.pushNum.setText(mCurrent_dp_turns_num + "");
                    b.pushNum.setVisibility(View.VISIBLE);
                } else {
                    b.pushNum.setVisibility(View.GONE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

}
