package com.jimei.xiaolumeimei.ui.fragment.main;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;

import com.jimei.library.utils.JUtils;
import com.jimei.library.utils.ViewUtils;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseBindingFragment;
import com.jimei.xiaolumeimei.base.CommonWebViewActivity;
import com.jimei.xiaolumeimei.databinding.FragmentMyTabBinding;
import com.jimei.xiaolumeimei.entities.UserInfoBean;
import com.jimei.xiaolumeimei.entities.event.RefreshPersonalEvent;
import com.jimei.xiaolumeimei.model.MainModel;
import com.jimei.xiaolumeimei.ui.activity.main.ComplainActivity;
import com.jimei.xiaolumeimei.ui.activity.trade.AllOrdersActivity;
import com.jimei.xiaolumeimei.ui.activity.trade.AllRefundsActivity;
import com.jimei.xiaolumeimei.ui.activity.user.AllCouponActivity;
import com.jimei.xiaolumeimei.ui.activity.user.InformationActivity;
import com.jimei.xiaolumeimei.ui.activity.user.LoginActivity;
import com.jimei.xiaolumeimei.ui.activity.user.MembershipPointActivity;
import com.jimei.xiaolumeimei.ui.activity.user.WalletActivity;
import com.jimei.xiaolumeimei.ui.activity.xiaolumama.MamaActivity;
import com.jimei.xiaolumeimei.utils.JumpUtils;
import com.jimei.xiaolumeimei.utils.LoginUtils;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.net.UnknownHostException;

import retrofit2.adapter.rxjava.HttpException;

public class MyTabFragment extends BaseBindingFragment<FragmentMyTabBinding> implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private boolean mamaFlag;
    private int mamaId;

    public static MyTabFragment newInstance() {
        return new MyTabFragment();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_my_tab;
    }

    @Override
    public View getLoadingView() {
        return b.layout;
    }

    @Override
    public void initData() {
        mamaFlag = false;
        if (LoginUtils.checkLoginState(mActivity)) {
            addSubscription(MainModel.getInstance()
                    .getProfile()
                    .subscribe(new ServiceResponse<UserInfoBean>() {
                        @Override
                        public void onNext(UserInfoBean userInfoBean) {
                            fillDataToView(userInfoBean);
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            hideIndeterminateProgressDialog();
                            if (b.swipeLayout.isRefreshing()) {
                                b.swipeLayout.setRefreshing(false);
                            }
                            if (e instanceof UnknownHostException && !JUtils.isNetWorkAvilable()) {
                                showNetworkError();
                            } else if (e instanceof HttpException) {
                                if (((HttpException) e).code() == 403) {
                                    LoginUtils.delLoginInfo(mActivity);
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
    protected void initViews() {
        EventBus.getDefault().register(this);
        b.swipeLayout.setColorSchemeResources(R.color.colorAccent);
    }

    @Override
    public void setListener() {
        b.rlHead.setOnClickListener(this);
        b.imgUser.setOnClickListener(this);
        b.tvNickname.setOnClickListener(this);
        b.llMoney.setOnClickListener(this);
        b.llScore.setOnClickListener(this);
        b.llDiscount.setOnClickListener(this);
        b.llPay.setOnClickListener(this);
        b.llWait.setOnClickListener(this);
        b.llRefund.setOnClickListener(this);
        b.llOrder.setOnClickListener(this);
        b.llShop.setOnClickListener(this);
        b.llComplain.setOnClickListener(this);
        b.llProblem.setOnClickListener(this);
        b.swipeLayout.setOnRefreshListener(this);
    }

    @Override
    public void onClick(View v) {
        if (!LoginUtils.checkLoginState(mActivity)) {
            Bundle bundle = new Bundle();
            bundle.putString("login", "main");
            readyGo(LoginActivity.class, bundle);
        } else {
            Bundle bundle;
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
                    readyGo(MembershipPointActivity.class);
                    break;
                case R.id.ll_discount:
                    readyGo(AllCouponActivity.class);
                    break;
                case R.id.ll_pay:
                    bundle = new Bundle();
                    bundle.putInt("fragment", 2);
                    readyGo(AllOrdersActivity.class, bundle);
                    break;
                case R.id.ll_wait:
                    bundle = new Bundle();
                    bundle.putInt("fragment", 3);
                    readyGo(AllOrdersActivity.class, bundle);
                    break;
                case R.id.ll_refund:
                    readyGo(AllRefundsActivity.class);
                    break;
                case R.id.ll_order:
                    bundle = new Bundle();
                    bundle.putInt("fragment", 1);
                    readyGo(AllOrdersActivity.class, bundle);
                    break;
                case R.id.ll_shop:
                    if (mamaFlag) {
                        bundle = new Bundle();
                        bundle.putString("mamaid", mamaId + "");
                        readyGo(MamaActivity.class, bundle);
                    } else {
                        new AlertDialog.Builder(mActivity)
                                .setTitle("提示")
                                .setMessage("您暂时不是小鹿妈妈,请关注\"小鹿美美\"公众号,获取更多信息哦!")
                                .setPositiveButton("确定", (dialog, which) -> dialog.dismiss())
                                .show();
                    }
                    break;
                case R.id.ll_complain:
                    readyGo(ComplainActivity.class);
                    break;
                case R.id.ll_problem:
                    JumpUtils.jumpToWebViewWithCookies(mActivity, "https://m.xiaolumeimei.com/mall/faq", -1,
                            CommonWebViewActivity.class, false, true);
                    break;
            }
        }
    }

    private void fillDataToView(UserInfoBean userInfoBean) {
        if (userInfoBean != null) {
            if ((userInfoBean.getXiaolumm() != null) && (userInfoBean.getXiaolumm().getId() != 0)) {
                mamaFlag = true;
                mamaId = userInfoBean.getXiaolumm().getId();
            } else {
                mamaFlag = false;
            }
            if (!TextUtils.isEmpty(userInfoBean.getThumbnail())) {
                ViewUtils.loadImgToImgView(mActivity, b.imgUser, userInfoBean.getThumbnail());
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
            b.tvScore.setText("" + userInfoBean.getScore());
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
            b.loginFlag.setVisibility(View.GONE);
            b.tvNickname.setText("点击登录");
            b.tvDiscount.setText("0");
            b.tvScore.setText("0");
            b.tvMoney.setText("0.00");
            b.textPay.setVisibility(View.GONE);
            b.textWait.setVisibility(View.GONE);
            b.textRefund.setVisibility(View.GONE);
        }
        hideIndeterminateProgressDialog();
        if (b.swipeLayout.isRefreshing()) {
            b.swipeLayout.setRefreshing(false);
        }
    }

    protected void readyGo(Class<?> clazz) {
        Intent intent = new Intent(mActivity, clazz);
        startActivity(intent);
    }

    protected void readyGo(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(mActivity, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void reLoadData(RefreshPersonalEvent event) {
        initData();
    }

    @Override
    public void onRefresh() {
        initData();
    }
}
