package com.jimei.xiaolumeimei.ui.activity.trade;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jimei.xiaolumeimei.adapter.AllOrdersListAdapter;
import com.jimei.xiaolumeimei.adapter.WaitSendOrdersListAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.model.TradeModel;
import com.jimei.xiaolumeimei.widget.SpaceItemDecoration;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.AllOrdersListAdapter;
import com.jimei.xiaolumeimei.data.XlmmApi;
import com.jimei.xiaolumeimei.entities.AllOrdersBean;

import butterknife.Bind;
import rx.schedulers.Schedulers;


public class WaitSendOrdersActivity extends BaseSwipeBackCompatActivity {
    String TAG = "WaitSendOrdersActivity";
    @Bind(R.id.toolbar) Toolbar toolbar;
    TradeModel model = new TradeModel();
    private WaitSendOrdersListAdapter mAllOrderAdapter;
    LinearLayout rlayout;
    TextView  tx_empty_info;

    @Override protected void setListener() {

    }
    @Override protected void getBundleExtras(Bundle extras) {

    }

    @Override protected int getContentViewLayoutID() {
        return R.layout.activity_waitsendorders;
    }

    @Override protected void initViews() {
        toolbar.setTitle("等待发货订单");
        setSupportActionBar(toolbar);

        rlayout = (LinearLayout) findViewById(R.id.llayout_allorders);
        tx_empty_info = new TextView(mContext);
    //config allorders list adaptor
        ListView all_orders_listview = (ListView) findViewById(R.id.all_orders_listview);

        mAllOrderAdapter = new WaitSendOrdersListAdapter(this);
        all_orders_listview.setAdapter(mAllOrderAdapter);
    }
    //从server端获得所有订单数据，可能要查询几次
    @Override protected void initData() {
        model.getWaitSendOrdersBean()
                .subscribeOn(Schedulers.newThread())
                .subscribe(new ServiceResponse<AllOrdersBean>() {
                    @Override public void onNext(AllOrdersBean allOrdersBean) {
                        List<AllOrdersBean.ResultsEntity> results = allOrdersBean.getResults();
                        if (0 == results.size()){
                            fillEmptyInfo();
                        }
                        else
                        {
                            tx_empty_info.setVisibility(View.GONE);
                            mAllOrderAdapter.update(results);
                        }

                        Log.i(TAG, allOrdersBean.toString());
                    }
                });
    }

    @Override protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

    private void fillEmptyInfo(){

        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        tx_empty_info.setLayoutParams(lp);
        tx_empty_info.setText("亲，你还没有任何订单，快去抢购吧！");

        rlayout.addView(tx_empty_info);
    }
}
