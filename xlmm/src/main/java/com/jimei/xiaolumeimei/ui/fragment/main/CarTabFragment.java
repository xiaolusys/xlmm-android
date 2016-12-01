package com.jimei.xiaolumeimei.ui.fragment.main;

import android.content.Intent;
import android.view.View;

import com.jimei.library.utils.JUtils;
import com.jimei.library.widget.DividerItemDecoration;
import com.jimei.library.widget.ScrollLinearLayoutManager;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.CartHistoryAdapter;
import com.jimei.xiaolumeimei.adapter.CartListAdapter;
import com.jimei.xiaolumeimei.base.BaseActivity;
import com.jimei.xiaolumeimei.base.BaseBindingFragment;
import com.jimei.xiaolumeimei.databinding.FragmentCarTabBinding;
import com.jimei.xiaolumeimei.entities.CartsInfoBean;
import com.jimei.xiaolumeimei.entities.CartsPayinfoBean;
import com.jimei.xiaolumeimei.entities.event.RefreshCarNumEvent;
import com.jimei.xiaolumeimei.model.CartsModel;
import com.jimei.xiaolumeimei.ui.activity.main.TabActivity;
import com.jimei.xiaolumeimei.ui.activity.trade.CartsPayInfoActivity;
import com.jimei.xiaolumeimei.ui.xlmmmain.MainActivity;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wisdom on 16/11/29.
 */

public class CarTabFragment extends BaseBindingFragment<FragmentCarTabBinding> implements View.OnClickListener {
    private List<Integer> ids = new ArrayList<>();
    private List<CartsInfoBean> cartList = new ArrayList<>();
    private List<CartsInfoBean> cartHisList = new ArrayList<>();
    private CartListAdapter cartListAdapter;
    private CartHistoryAdapter cartHisAdapter;

    public static CarTabFragment newInstance() {
        return new CarTabFragment();
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
        refreshCartList();
        addSubscription(CartsModel.getInstance()
                .getCartsHisList()
                .subscribe(new ServiceResponse<List<CartsInfoBean>>() {
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
                // TODO: 16/11/29  跳转到主页
                startActivity(new Intent(mActivity, MainActivity.class));
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
        ((TabActivity) mActivity).showCarNum();
        addSubscription(CartsModel.getInstance()
                .getCartsInfoList(getIds())
                .subscribe(new ServiceResponse<CartsPayinfoBean>() {
                    @Override
                    public void onNext(CartsPayinfoBean cartsPayinfoBean) {
                        super.onNext(cartsPayinfoBean);
                        if (cartsPayinfoBean != null) {
                            b.totalPrice.setText("¥" + cartsPayinfoBean.getTotalFee());
                            b.confirmLayout.setVisibility(View.VISIBLE);
                        }
                    }
                }));
    }

    public void removeCartList(CartsInfoBean cartsInfoBean) {
        cartList.remove(cartsInfoBean);
        if (cartList.size() == 0) {
            b.emptyContent.setVisibility(View.VISIBLE);
            b.totalPrice.setText("¥0");
            ((TabActivity) mActivity).showCarNum();
            b.confirmLayout.setVisibility(View.GONE);
        }
        cartListAdapter.notifyDataSetChanged();
        refreshIds();
    }

    private void refreshIds() {
        addSubscription(CartsModel.getInstance()
                .getCartsList()
                .subscribe(new ServiceResponse<List<CartsInfoBean>>() {
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
        addSubscription(CartsModel.getInstance()
                .getCartsList()
                .subscribe(new ServiceResponse<List<CartsInfoBean>>() {
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void reLoadData(RefreshCarNumEvent event) {
        initData();
    }
}
