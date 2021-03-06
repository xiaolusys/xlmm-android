package com.jimei.xiaolumeimei.ui.activity.trade;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.jimei.library.utils.JUtils;
import com.jimei.library.widget.DividerItemDecoration;
import com.jimei.library.widget.ScrollLinearLayoutManager;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.XlmmApp;
import com.jimei.xiaolumeimei.adapter.CartHistoryAdapter;
import com.jimei.xiaolumeimei.adapter.CartListAdapter;
import com.jimei.xiaolumeimei.base.BaseMVVMActivity;
import com.jimei.xiaolumeimei.databinding.ActivityCartBinding;
import com.jimei.xiaolumeimei.entities.CartsInfoBean;
import com.jimei.xiaolumeimei.entities.CartsPayinfoBean;
import com.jimei.xiaolumeimei.service.ServiceResponse;
import com.jimei.xiaolumeimei.ui.activity.main.TabActivity;
import com.jimei.xiaolumeimei.widget.ICartHelper;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wisdom on 16/12/2.
 */

public class CartActivity extends BaseMVVMActivity<ActivityCartBinding> implements View.OnClickListener, ICartHelper {
    private List<Integer> ids = new ArrayList<>();
    private List<CartsInfoBean> cartList = new ArrayList<>();
    private List<CartsInfoBean> cartHisList = new ArrayList<>();
    private CartListAdapter cartListAdapter;
    private CartHistoryAdapter cartHisAdapter;
    private boolean isNormal;
    private int type;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_cart;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        isNormal = extras.getBoolean("isNormal", false);
    }

    @Override
    public void getIntentUrl(Uri uri) {
        String ptype = uri.getQueryParameter("type");
        if (ptype != null && "0".equals(ptype)) {
            isNormal = true;
        }
    }

    @Override
    protected void initViews() {
        b.rvCart.setNestedScrollingEnabled(false);
        b.rvCart.setHasFixedSize(false);
        b.rvCart.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        ScrollLinearLayoutManager layoutManager = new ScrollLinearLayoutManager(this);
        layoutManager.setAutoMeasureEnabled(false);
        b.rvCart.setLayoutManager(layoutManager);
        cartListAdapter = new CartListAdapter(this, cartList, this);
        b.rvCart.setAdapter(cartListAdapter);

        b.rvHistory.setNestedScrollingEnabled(false);
        b.rvHistory.setHasFixedSize(false);
        b.rvHistory.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        ScrollLinearLayoutManager manager = new ScrollLinearLayoutManager(this);
        manager.setAutoMeasureEnabled(false);
        b.rvHistory.setLayoutManager(manager);
        cartHisAdapter = new CartHistoryAdapter(this, cartHisList, this);
        b.rvHistory.setAdapter(cartHisAdapter);
    }

    @Override
    public View getLoadingView() {
        return b.layout;
    }

    @Override
    protected void initData() {
        if (isNormal) {
            type = 0;
            cartHisAdapter.setType(0);
        } else {
            type = 5;
        }
        refreshCartList();
        refreshHisCartList();
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
                readyGoThenKill(TabActivity.class);
                break;
            case R.id.confirm:
                if (ids.size() > 0) {
                    MobclickAgent.onEvent(this, "BuyID");
                    Intent intent = new Intent(this, CartsPayInfoActivity.class);
                    intent.putExtra("ids", getIds());
                    startActivity(intent);
                    finish();
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
        addSubscription(XlmmApp.getCartsInteractor(this)
            .getCartsPayInfoList(getIds(), new ServiceResponse<CartsPayinfoBean>() {
                @Override
                public void onNext(CartsPayinfoBean cartsPayinfoBean) {
                    if (cartsPayinfoBean != null) {
                        b.totalPrice.setText("¥" + cartsPayinfoBean.getTotalFee());
                        b.confirmLayout.setVisibility(View.VISIBLE);
                    }
                }
            }));
    }

    private void refreshHisCartList() {
        addSubscription(XlmmApp.getCartsInteractor(this)
            .getCartsHisList(type, new ServiceResponse<List<CartsInfoBean>>() {
                @Override
                public void onNext(List<CartsInfoBean> cartsInfoBeen) {
                    cartHisList.clear();
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

    public void removeCartList(CartsInfoBean cartsInfoBean) {
        cartList.remove(cartsInfoBean);
        if (cartList.size() == 0) {
            b.emptyContent.setVisibility(View.VISIBLE);
            b.totalPrice.setText("¥0");
            b.confirmLayout.setVisibility(View.GONE);
        }
        cartListAdapter.notifyDataSetChanged();
        refreshIds();
    }

    private void refreshIds() {
        addSubscription(XlmmApp.getCartsInteractor(this)
            .getCartsList(type, new ServiceResponse<List<CartsInfoBean>>() {
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
        addSubscription(XlmmApp.getCartsInteractor(this)
            .getCartsList(type, new ServiceResponse<List<CartsInfoBean>>() {
                @Override
                public void onNext(List<CartsInfoBean> cartsInfoBeen) {
                    cartList.clear();
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
                        b.confirmLayout.setVisibility(View.GONE);
                    }
                    b.sv.scrollTo(0, 0);
                    hideIndeterminateProgressDialog();
                }
            }));
    }
}
