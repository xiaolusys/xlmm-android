package com.jimei.xiaolumeimei.ui.fragment.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jimei.library.utils.JUtils;
import com.jimei.library.widget.DividerItemDecoration;
import com.jimei.library.widget.ScrollLinearLayoutManager;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.XlmmApp;
import com.jimei.xiaolumeimei.adapter.CartHistoryAdapter;
import com.jimei.xiaolumeimei.adapter.CartListAdapter;
import com.jimei.xiaolumeimei.base.BaseActivity;
import com.jimei.xiaolumeimei.base.BaseBindingFragment;
import com.jimei.xiaolumeimei.databinding.FragmentCarTabBinding;
import com.jimei.xiaolumeimei.entities.CartsInfoBean;
import com.jimei.xiaolumeimei.entities.CartsPayinfoBean;
import com.jimei.xiaolumeimei.entities.event.CartEvent;
import com.jimei.xiaolumeimei.entities.event.CartNumEvent;
import com.jimei.xiaolumeimei.entities.event.LoginEvent;
import com.jimei.xiaolumeimei.entities.event.LogoutEvent;
import com.jimei.xiaolumeimei.service.ServiceResponse;
import com.jimei.xiaolumeimei.ui.activity.main.TabActivity;
import com.jimei.xiaolumeimei.ui.activity.trade.CartsPayInfoActivity;
import com.jimei.xiaolumeimei.util.LoginUtils;
import com.jimei.xiaolumeimei.widget.ICartHelper;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wisdom on 16/11/29.
 */

public class CartTabFragment extends BaseBindingFragment<FragmentCarTabBinding> implements View.OnClickListener, ICartHelper {
    private List<Integer> ids = new ArrayList<>();
    private List<CartsInfoBean> cartList = new ArrayList<>();
    private List<CartsInfoBean> cartHisList = new ArrayList<>();
    private CartListAdapter cartListAdapter;
    private CartHistoryAdapter cartHisAdapter;

    public static CartTabFragment newInstance() {
        return new CartTabFragment();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_car_tab;
    }

    @Override
    public View getLoadingView() {
        return b.layout;
    }

    @Override
    public void initData() {
        if (LoginUtils.checkLoginState(mActivity)) {
            refreshCartList();
            refreshHisCartList();
        }
    }

    private void refreshHisCartList() {
        addSubscription(XlmmApp.getCartsInteractor(mActivity)
            .getCartsHisList(new ServiceResponse<List<CartsInfoBean>>() {
                @Override
                public void onNext(List<CartsInfoBean> cartsInfoBeen) {
                    cartHisList.clear();
                    cartHisAdapter.notifyDataSetChanged();
                    if (cartsInfoBeen != null && cartsInfoBeen.size() > 0) {
                        cartHisList.addAll(cartsInfoBeen);
                        cartHisAdapter.notifyDataSetChanged();
                        b.tvLine.setVisibility(View.VISIBLE);
                    } else {
                        b.tvLine.setVisibility(View.GONE);
                    }
                }
            }));
    }

    @Override
    protected void initViews() {
        EventBus.getDefault().register(this);
        b.rvCart.setNestedScrollingEnabled(false);
        b.rvCart.setHasFixedSize(false);
        b.rvCart.addItemDecoration(new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL_LIST));
        ScrollLinearLayoutManager layoutManager = new ScrollLinearLayoutManager(mActivity);
        layoutManager.setAutoMeasureEnabled(false);
        b.rvCart.setLayoutManager(layoutManager);
        cartListAdapter = new CartListAdapter((BaseActivity) mActivity, cartList, this);
        b.rvCart.setAdapter(cartListAdapter);

        b.rvHistory.setNestedScrollingEnabled(false);
        b.rvHistory.setHasFixedSize(false);
        b.rvHistory.addItemDecoration(new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL_LIST));
        ScrollLinearLayoutManager manager = new ScrollLinearLayoutManager(mActivity);
        manager.setAutoMeasureEnabled(false);
        b.rvHistory.setLayoutManager(manager);
        cartHisAdapter = new CartHistoryAdapter((BaseActivity) mActivity, cartHisList, this);
        b.rvHistory.setAdapter(cartHisAdapter);
    }

    @Override
    public void setListener() {
        b.goMain.setOnClickListener(this);
        b.confirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.go_main:
                Bundle bundle = new Bundle();
                bundle.putString("flag", "main");
                Intent intent1 = new Intent(mActivity, TabActivity.class);
                intent1.putExtras(bundle);
                startActivity(intent1);
                break;
            case R.id.confirm:
                if (ids.size() > 0) {
                    MobclickAgent.onEvent(mActivity, "BuyID");
                    Intent intent = new Intent(mActivity, CartsPayInfoActivity.class);
                    intent.putExtra("ids", getIds());
                    startActivity(intent);
                } else {
                    JUtils.Toast("请至少选择一件商品哦!");
                }
                break;
        }
    }

    private String getIds() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ids.size(); i++) {
            sb.append(ids.get(i)).append(",");
        }
        String str = new String(sb);
        return str.substring(0, str.length() - 1);
    }

    public void setPriceText() {
        addSubscription(XlmmApp.getCartsInteractor(mActivity)
            .getCartsPayInfoList(getIds(), new ServiceResponse<CartsPayinfoBean>() {
                @Override
                public void onNext(CartsPayinfoBean cartsPayinfoBean) {
                    if (cartsPayinfoBean != null) {
                        b.totalPrice.setText("¥" + cartsPayinfoBean.getTotalFee());
                        b.confirm.setClickable(true);
                        b.confirmLayout.setVisibility(View.VISIBLE);
                        EventBus.getDefault().post(new CartNumEvent());
                    }
                }
            }));

    }

    public void removeCartList(CartsInfoBean cartsInfoBean) {
        cartList.remove(cartsInfoBean);
        if (cartList.size() == 0) {
            b.emptyContent.setVisibility(View.VISIBLE);
            b.totalPrice.setText("¥0");
            b.confirm.setClickable(false);
            b.confirmLayout.setVisibility(View.GONE);
            EventBus.getDefault().post(new CartNumEvent());
        }
        cartListAdapter.notifyDataSetChanged();
        refreshIds();
    }

    private void refreshIds() {
        addSubscription(XlmmApp.getCartsInteractor(mActivity)
            .getCartsList(new ServiceResponse<List<CartsInfoBean>>() {
                @Override
                public void onNext(List<CartsInfoBean> cartsInfoBeen) {
                    if (cartsInfoBeen != null && cartsInfoBeen.size() > 0) {
                        ids.clear();
                        for (int i = 0; i < cartsInfoBeen.size(); i++) {
                            ids.add(cartsInfoBeen.get(i).getId());
                        }
                        setPriceText();
                    } else {
                        ids.clear();
                    }
                }
            }));
    }

    public void removeHistory(CartsInfoBean cartsInfoBean) {
        cartHisList.remove(cartsInfoBean);
        cartHisAdapter.notifyDataSetChanged();
        if (cartHisList.size() == 0) b.tvLine.setVisibility(View.GONE);
    }

    public void addHistory(CartsInfoBean cartsInfoBean) {
        cartHisList.add(0, cartsInfoBean);
        cartHisAdapter.notifyDataSetChanged();
        b.tvLine.setVisibility(View.VISIBLE);
    }

    public void refreshCartList() {
        showIndeterminateProgressDialog(false);
        addSubscription(XlmmApp.getCartsInteractor(mActivity)
            .getCartsList(new ServiceResponse<List<CartsInfoBean>>() {
                @Override
                public void onNext(List<CartsInfoBean> cartsInfoBeen) {
                    cartList.clear();
                    cartListAdapter.notifyDataSetChanged();
                    if (cartsInfoBeen != null && cartsInfoBeen.size() > 0) {
                        cartList.addAll(cartsInfoBeen);
                        b.emptyContent.setVisibility(View.GONE);
                        cartListAdapter.notifyDataSetChanged();
                        ids.clear();
                        for (int i = 0; i < cartsInfoBeen.size(); i++) {
                            ids.add(cartsInfoBeen.get(i).getId());
                        }
                        setPriceText();
                    } else {
                        b.emptyContent.setVisibility(View.VISIBLE);
                        b.totalPrice.setText("¥0");
                        b.confirm.setClickable(false);
                        b.confirmLayout.setVisibility(View.GONE);
                    }
                    b.sv.scrollTo(0, 0);
                    hideIndeterminateProgressDialog();
                }

                @Override
                public void onError(Throwable e) {
                    if (e instanceof UnknownHostException && !JUtils.isNetWorkAvilable()) {
                        showNetworkError();
                    } else {
                        JUtils.Toast("数据加载失败!");
                    }
                }
            }));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void initLogin(LoginEvent event) {
        EventBus.getDefault().post(new CartNumEvent());
        initData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void initCart(CartEvent event) {
        EventBus.getDefault().post(new CartNumEvent());
        initData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void initLogout(LogoutEvent event) {
        EventBus.getDefault().post(new CartNumEvent());
        cartList.clear();
        cartHisList.clear();
        cartHisAdapter.notifyDataSetChanged();
        cartListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }
}
