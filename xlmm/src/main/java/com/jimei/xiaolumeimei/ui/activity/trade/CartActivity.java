package com.jimei.xiaolumeimei.ui.activity.trade;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.CartHistoryAdapter;
import com.jimei.xiaolumeimei.adapter.CartListAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.CartsInfoBean;
import com.jimei.xiaolumeimei.entities.CartsPayinfoBean;
import com.jimei.xiaolumeimei.model.CartsModel;
import com.jimei.xiaolumeimei.ui.xlmmmain.MainActivity;
import com.jimei.xiaolumeimei.widget.DividerItemDecoration;
import com.jimei.xiaolumeimei.widget.ScrollLinearLayoutManager;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.jude.utils.JUtils;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.schedulers.Schedulers;

public class CartActivity extends BaseSwipeBackCompatActivity implements View.OnClickListener {
    @Bind(R.id.rv_cart)
    RecyclerView rvCart;
    @Bind(R.id.rv_history)
    RecyclerView rvHistory;
    @Bind(R.id.empty_content)
    LinearLayout emptyLayout;
    @Bind(R.id.go_main)
    TextView goMain;
    @Bind(R.id.total_price)
    TextView totalPrice;
    @Bind(R.id.tv_line)
    TextView tvLine;
    @Bind(R.id.confirm)
    Button confirm;
    @Bind(R.id.sv)
    ScrollView mScrollView;


    private List<Integer> ids = new ArrayList<>();
    private List<CartsInfoBean> cartList = new ArrayList<>();
    private List<CartsInfoBean> cartHisList = new ArrayList<>();
    private CartListAdapter cartListAdapter;
    private CartHistoryAdapter cartHisAdapter;


    @Override
    protected void setListener() {
        goMain.setOnClickListener(this);
        confirm.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        refreshCartList();
        addSubscription(CartsModel.getInstance()
                .getCartsHisList()
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<List<CartsInfoBean>>() {
                    @Override
                    public void onNext(List<CartsInfoBean> cartsInfoBeen) {
                        if (cartsInfoBeen != null && cartsInfoBeen.size() > 0) {
                            cartHisList.addAll(cartsInfoBeen);
                            cartHisAdapter.notifyDataSetChanged();
                            tvLine.setVisibility(View.VISIBLE);
                        } else {
                            tvLine.setVisibility(View.GONE);
                        }
                    }
                }));
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_cart;
    }

    @Override
    protected void initViews() {
        rvCart.setNestedScrollingEnabled(false);
        rvCart.setHasFixedSize(false);
        rvCart.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        ScrollLinearLayoutManager layoutManager = new ScrollLinearLayoutManager(this);
        layoutManager.setAutoMeasureEnabled(false);
        rvCart.setLayoutManager(layoutManager);
        cartListAdapter = new CartListAdapter(this, cartList);
        rvCart.setAdapter(cartListAdapter);


        rvHistory.setNestedScrollingEnabled(false);
        rvHistory.setHasFixedSize(false);
        rvHistory.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        ScrollLinearLayoutManager manager = new ScrollLinearLayoutManager(this);
        manager.setAutoMeasureEnabled(false);
        rvHistory.setLayoutManager(manager);
        cartHisAdapter = new CartHistoryAdapter(this, cartHisList);
        rvHistory.setAdapter(cartHisAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.go_main:
                startActivity(new Intent(this, MainActivity.class));
                finish();
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

    public void setPriceText() {
        addSubscription(CartsModel.getInstance()
                .getCartsInfoList(getIds())
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<CartsPayinfoBean>() {
                    @Override
                    public void onNext(CartsPayinfoBean cartsPayinfoBean) {
                        super.onNext(cartsPayinfoBean);
                        if (cartsPayinfoBean != null) {
                            totalPrice.setText("¥" + cartsPayinfoBean.getTotalFee());
                        }
                    }
                }));
    }

    public void removeCartList(CartsInfoBean cartsInfoBean) {
        cartList.remove(cartsInfoBean);
        if (cartList.size() == 0) emptyLayout.setVisibility(View.VISIBLE);
        cartListAdapter.notifyDataSetChanged();
        refreshIds();
    }

    public void refreshCartList(){
        showIndeterminateProgressDialog(false);
        addSubscription(CartsModel.getInstance()
                .getCartsList()
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<List<CartsInfoBean>>() {
                    @Override
                    public void onNext(List<CartsInfoBean> cartsInfoBeen) {
                        cartList.clear();
                        if (cartsInfoBeen != null && cartsInfoBeen.size() > 0) {
                            cartList.addAll(cartsInfoBeen);
                            emptyLayout.setVisibility(View.GONE);
                            cartListAdapter.notifyDataSetChanged();
                            ids.clear();
                            for (int i = 0; i < cartsInfoBeen.size(); i++) {
                                ids.add(cartsInfoBeen.get(i).getId());
                            }
                            setPriceText();
                        } else {
                            emptyLayout.setVisibility(View.VISIBLE);
                        }
                        mScrollView.scrollTo(0,0);
                        hideIndeterminateProgressDialog();
                    }
                }));
    }

    private void refreshIds() {
        addSubscription(CartsModel.getInstance()
                .getCartsList()
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<List<CartsInfoBean>>() {
                    @Override
                    public void onNext(List<CartsInfoBean> cartsInfoBeen) {
                        if (cartsInfoBeen != null && cartsInfoBeen.size() > 0) {
                            ids.clear();
                            for (int i = 0; i < cartsInfoBeen.size(); i++) {
                                ids.add(cartsInfoBeen.get(i).getId());
                            }
                            setPriceText();
                        }else {
                            ids.clear();
                        }
                    }
                }));
    }

    public void removeHistory(CartsInfoBean cartsInfoBean) {
        cartHisList.remove(cartsInfoBean);
        cartHisAdapter.notifyDataSetChanged();
        if (cartHisList.size() == 0) tvLine.setVisibility(View.GONE);
    }

    public void addHistory(CartsInfoBean cartsInfoBean) {
        cartHisList.add(0,cartsInfoBean);
        cartHisAdapter.notifyDataSetChanged();
        tvLine.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(this.getClass().getSimpleName());
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
        MobclickAgent.onPause(this);
    }

    private String getIds() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ids.size(); i++) {
            sb.append(ids.get(i)).append(",");
        }
        String str = new String(sb);
        return str.substring(0, str.length() - 1);
    }
}
