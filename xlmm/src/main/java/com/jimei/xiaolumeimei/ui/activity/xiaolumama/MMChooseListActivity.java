package com.jimei.xiaolumeimei.ui.activity.xiaolumama;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.MMChooselistBean;
import com.jimei.xiaolumeimei.entities.MMStoreBean;
import com.jimei.xiaolumeimei.entities.ResponseResultBean;
import com.jimei.xiaolumeimei.model.MMProductModel;
import com.jimei.xiaolumeimei.utils.ViewUtils;
import com.jimei.xiaolumeimei.widget.DividerItemDecoration;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.umeng.analytics.MobclickAgent;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye(www.itxuye.com) on 2016/02/14.
 * <p>
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class MMChooseListActivity extends BaseSwipeBackCompatActivity
        implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    public static final String TAG = "MMChooseListActivity";

    private static final String COMMISSION = "rebet_amount";//佣金
    private static final String SALES = "sale_num";//销量
    private static final String CHILD = "1";//童装
    private static final String LADY = "2";//女装
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.spinner_choose)
    AppCompatSpinner spinnerChoose;
    @Bind(R.id.tv_commission)
    TextView tvCommission;
    @Bind(R.id.tv_sales)
    TextView tvSales;
    @Bind(R.id.chooselist_xey)
    RecyclerView chooselistXey;
    @Bind(R.id.choosenum)
    TextView choosenum;
    @Bind(R.id.choose_tv)
    TextView chooseTv;
    boolean isLoading = false;
    int lastVisibleItemPosition = 0;
    private MMChooseAdapter mmChooseAdapter;
    private boolean isAll, isLady, isChild;
    private int chooseNum;
    private String sortfeild = "";
    private String category = "";
    private int page = 2;
    private int pagesize = 10;

    @Override
    protected void setListener() {
        spinnerChoose.setOnItemSelectedListener(this);
        tvCommission.setOnClickListener(this);
        tvSales.setOnClickListener(this);
        choosenum.setOnClickListener(this);
        chooseTv.setOnClickListener(this);
    }

    @Override
    protected void initData() {

        String[] countriesStr = {"  全部  ", "  女装  ", "  童装  "};
        ArrayAdapter adapter =
                new ArrayAdapter<String>(this, R.layout.item_choosespinner, countriesStr);
        //设置下拉列表风格
        adapter.setDropDownViewResource(R.layout.item_choosespinner_dropdown);
        spinnerChoose.setAdapter(adapter);

        chooseNum = getChooseNum();

        sortfeild = "";
        category = "";
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.chooselist_activity;
    }

    @Override
    protected void initViews() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        finishBack(toolbar);

        //choose_num = (TextView) toolbar.findViewById(R.id.choose_num);

        initRecyclerView();
    }

    private void initRecyclerView() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        chooselistXey.setLayoutManager(layoutManager);
        chooselistXey.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        chooselistXey.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if ((newState == RecyclerView.SCROLL_STATE_IDLE) && (lastVisibleItemPosition + 1
                        == mmChooseAdapter.getItemCount())) {
                    if (!isLoading) {
                        isLoading = true;
                        loadMoreData(page + "", MMChooseListActivity.this);
                        page++;
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
            }
        });
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        tvCommission.setTextColor(Color.parseColor("#4A4A4A"));
        tvSales.setTextColor(Color.parseColor("#4A4A4A"));

        if (position == 0) {
            isAll = true;
            isChild = false;
            isLady = false;
            sortfeild = "";
            category = "";
        } else if (position == 1) {
            isAll = false;
            isChild = false;
            isLady = true;
            sortfeild = "";
            category = LADY;
        } else if (position == 2) {
            isAll = false;
            isChild = true;
            isLady = false;
            sortfeild = "";
            category = CHILD;
        }
        getChooseListLadyorChildSort(sortfeild, category);
    }

    private void getChooseListLadyorChildSort(String sortfeild, String category) {
        page = 1;
        isLoading = false;

        showIndeterminateProgressDialog(false);
        Subscription subscribe = MMProductModel.getInstance()
                .getMMChooseLadyOrChildSortListSort(sortfeild, category, "" + page, "" + pagesize)
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<MMChooselistBean>() {
                    @Override
                    public void onNext(MMChooselistBean mmChooselistBeans) {
                        super.onNext(mmChooselistBeans);
                        try {
                            if ((mmChooselistBeans != null) && (mmChooselistBeans.getResults()
                                    != null)) {
                                mmChooseAdapter.updateWithClear(mmChooselistBeans.getResults());
                                page = 2;
                            }
                        } catch (NullPointerException ex) {
                        }
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        hideIndeterminateProgressDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        e.printStackTrace();
                    }
                });
        addSubscription(subscribe);
    }

    private void loadMoreData(String page, Context context) {

        Subscription subscription2 = MMProductModel.getInstance()
                .getMMChooseLadyOrChildSortListSort(sortfeild, category, "" + page, "" + pagesize)
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<MMChooselistBean>() {
                    @Override
                    public void onNext(MMChooselistBean mmChooselistBeans) {
                        isLoading = false;
                        try {
                            if ((mmChooselistBeans != null) && (mmChooselistBeans.getResults()
                                    != null)) {
                                mmChooseAdapter.update(mmChooselistBeans.getResults());
                            }

                            if (null == mmChooselistBeans.getNext()) {
                                Toast.makeText(context, "没有更多了", Toast.LENGTH_SHORT).show();
                            }
                        } catch (NullPointerException ex) {
                        }
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                    }
                });
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_commission:

                tvCommission.setTextColor(Color.parseColor("#F5B123"));
                tvSales.setTextColor(Color.parseColor("#4A4A4A"));

                if (isLady) {
                    sortfeild = COMMISSION;
                    category = LADY;
                } else if (isAll) {
                    sortfeild = COMMISSION;
                    category = "";
                } else if (isChild) {
                    sortfeild = COMMISSION;
                    category = CHILD;
                }

                getChooseListLadyorChildSort(sortfeild, category);

                break;
            case R.id.tv_sales:
                tvCommission.setTextColor(Color.parseColor("#4A4A4A"));
                tvSales.setTextColor(Color.parseColor("#F5B123"));
                if (isLady) {
                    sortfeild = SALES;
                    category = LADY;
                } else if (isAll) {
                    sortfeild = SALES;
                    category = "";
                } else if (isChild) {
                    sortfeild = SALES;
                    category = CHILD;
                }

                getChooseListLadyorChildSort(sortfeild, category);

                break;

            case R.id.choosenum:
            case R.id.choose_tv:
                startActivity(new Intent(this, HaveChoosedActivity.class));
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(this.getClass().getSimpleName());
        MobclickAgent.onResume(this);
        mmChooseAdapter = new MMChooseAdapter(this);
        chooselistXey.setAdapter(mmChooseAdapter);

        Subscription subscribe = MMProductModel.getInstance()
                .getMMChooseLadyOrChildSortListSort(sortfeild, category, "1", "" + pagesize)
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<MMChooselistBean>() {
                    @Override
                    public void onNext(MMChooselistBean mmChooselistBeans) {
                        super.onNext(mmChooselistBeans);
                        try {
                            if ((mmChooselistBeans != null) && (mmChooselistBeans.getResults()
                                    != null)) {
                                mmChooseAdapter.update(mmChooselistBeans.getResults());
                                chooseNum = mmChooselistBeans.getResults().get(0).getShop_product_num();
                                choosenum.setText("" + chooseNum);
                            }
                        } catch (NullPointerException ex) {
                        }
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
        addSubscription(subscribe);
    }

    public int getChooseNum() {

        final int[] num = new int[1];

        MMProductModel.getInstance()
                .getMMStoreList()
                .subscribeOn(Schedulers.io())
                .subscribe(new ServiceResponse<List<MMStoreBean>>() {
                    @Override
                    public void onNext(List<MMStoreBean> mmStoreBeen) {
                        if (mmStoreBeen != null) {
                            num[0] = mmStoreBeen.size();
                        } else {
                            num[0] = 0;
                        }
                    }
                });

        return num[0];
    }

    class MMChooseAdapter extends RecyclerView.Adapter<MMChooseAdapter.MMChooseVH> {

        private final String TAG = MMChooseAdapter.class.getSimpleName();

        private List<MMChooselistBean.ResultsBean> mList;
        private Context mContext;
        private MaterialDialog materialDialog;

        public MMChooseAdapter(Context context) {
            this.mContext = context;
            mList = new ArrayList<>();
        }

        public void updateWithClear(List<MMChooselistBean.ResultsBean> list) {
            mList.clear();
            mList.addAll(list);
            notifyDataSetChanged();
        }

        public void update(List<MMChooselistBean.ResultsBean> list) {

            mList.addAll(list);
            notifyDataSetChanged();
        }

        @Override
        public MMChooseVH onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;

            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_chooselist, parent, false);
            AutoUtils.autoSize(view);
            return new MMChooseVH(view);
        }

        @Override
        public void onBindViewHolder(MMChooseVH holder, int position) {
            MMChooselistBean.ResultsBean mmChooselistBean = mList.get(position);
            holder.name.setText(mmChooselistBean.getName());
            ViewUtils.loadImgToImgView(mContext, holder.imageChooselist,
                    mmChooselistBean.getPic_path());
            holder.agentPrice.setText(
                    "¥" + (float) (Math.round(mmChooselistBean.getAgent_price() * 100)) / 100);
            holder.stdSalePrice.setText(
                    "/¥" + (float) (Math.round(mmChooselistBean.getStd_sale_price() * 100)) / 100);
            holder.rebetAmount.setText("你的" + mmChooselistBean.getLevel_info().getRebet_amount_des());
            holder.lockNum.setText(mmChooselistBean.getLevel_info().getSale_num_des());
            holder.vip.setText(mmChooselistBean.getLevel_info().getNext_agencylevel_desc());
            String des = mmChooselistBean.getLevel_info().getNext_rebet_amount_des();
            holder.vipMoney.setText(des);
            int inCustomerShop = mmChooselistBean.getIn_customer_shop();
            if (0 == inCustomerShop) {
                holder.add.setVisibility(View.VISIBLE);
                holder.remove.setVisibility(View.INVISIBLE);
                holder.add.setClickable(true);
                holder.remove.setClickable(false);

                holder.add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //                        showIndeterminateProgressDialog(false);
                        Subscription subscribe = MMProductModel.getInstance()
                                .add_pro_to_shop(mmChooselistBean.getId() + "")
                                .subscribeOn(Schedulers.io())
                                .subscribe(new ServiceResponse<ResponseResultBean>() {
                                    @Override
                                    public void onNext(ResponseResultBean responseResultBean) {
                                        super.onNext(responseResultBean);
                                        if (responseResultBean != null) {
                                            if (0 == responseResultBean.getCode()) {
                                                holder.add.setVisibility(View.INVISIBLE);
                                                holder.remove.setVisibility(View.VISIBLE);
                                                holder.add.setClickable(false);
                                                holder.remove.setClickable(true);
                                                mmChooselistBean.setIn_customer_shop(1);
                                                notifyItemChanged(position);
                                                chooseNum++;
                                                choosenum.setText("" + chooseNum);
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCompleted() {
                                        super.onCompleted();
                                        //                                        hideIndeterminateProgressDialog();
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        super.onError(e);
                                        e.printStackTrace();
                                        //                                        hideIndeterminateProgressDialog();
                                    }
                                });
                        addSubscription(subscribe);
                    }
                });
            } else if (1 == inCustomerShop) {
                holder.add.setVisibility(View.INVISIBLE);
                holder.remove.setVisibility(View.VISIBLE);
                holder.add.setClickable(false);
                holder.remove.setClickable(true);

                holder.remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //                        showIndeterminateProgressDialog(false);
                        Subscription subscribe = MMProductModel.getInstance()
                                .remove_pro_from_shop(mmChooselistBean.getId() + "")
                                .subscribeOn(Schedulers.io())
                                .subscribe(new ServiceResponse<ResponseResultBean>() {
                                    @Override
                                    public void onNext(ResponseResultBean responseResultBean) {
                                        super.onNext(responseResultBean);
                                        if (responseResultBean != null) {
                                            if (0 == responseResultBean.getCode()) {
                                                holder.add.setVisibility(View.VISIBLE);
                                                holder.remove.setVisibility(View.INVISIBLE);
                                                holder.add.setClickable(true);
                                                holder.remove.setClickable(false);
                                                mmChooselistBean.setIn_customer_shop(0);
                                                notifyItemChanged(position);
                                                chooseNum--;
                                                choosenum.setText("" + chooseNum);
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCompleted() {
                                        super.onCompleted();
                                        //                                        hideIndeterminateProgressDialog();
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        super.onError(e);
                                        e.printStackTrace();
                                        //                                        hideIndeterminateProgressDialog();
                                    }
                                });
                        addSubscription(subscribe);
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return mList == null ? 0 : mList.size();
        }

        class MMChooseVH extends RecyclerView.ViewHolder {
            int id = R.layout.item_chooselist;
            @Bind(R.id.image_chooselist)
            ImageView imageChooselist;
            @Bind(R.id.name)
            TextView name;
            @Bind(R.id.agent_price)
            TextView agentPrice;
            @Bind(R.id.std_sale_price)
            TextView stdSalePrice;
            @Bind(R.id.rebet_amount)
            TextView rebetAmount;
            @Bind(R.id.lock_num)
            TextView lockNum;
            @Bind(R.id.add)
            LinearLayout add;
            @Bind(R.id.remove)
            LinearLayout remove;
            @Bind(R.id.vip)
            TextView vip;
            @Bind(R.id.vip_money)
            TextView vipMoney;

            public MMChooseVH(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
        MobclickAgent.onPause(this);
    }
}
