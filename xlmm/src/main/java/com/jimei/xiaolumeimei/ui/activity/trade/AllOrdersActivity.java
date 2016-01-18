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
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jimei.xiaolumeimei.adapter.AllOrdersListAdapter;
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

import rx.schedulers.Schedulers;


public class AllOrdersActivity extends BaseSwipeBackCompatActivity {
    String TAG = "AllOrdersActivity";
    TradeModel model = new TradeModel();
    AllOrdersBean all_orders_info = new AllOrdersBean();
    private AllOrdersListAdapter mAllOrderAdapter;

    @Override protected void setListener() {

    }
    @Override protected void getBundleExtras(Bundle extras) {

    }

    @Override protected int getContentViewLayoutID() {
        return R.layout.activity_allorders;
    }

    @Override protected void initViews() {
    //config allorders list adaptor
        ListView all_orders_listview = (ListView) findViewById(R.id.all_orders_listview);

        mAllOrderAdapter = new AllOrdersListAdapter(this);
        all_orders_listview.setAdapter(mAllOrderAdapter);
    }
    //从server端获得所有订单数据，可能要查询几次
    @Override protected void initData() {
        model.getAlloderBean()
                .subscribeOn(Schedulers.newThread())
                .subscribe(new ServiceResponse<AllOrdersBean>() {
                    @Override public void onNext(AllOrdersBean allOrdersBean) {
                        List<AllOrdersBean.ResultsEntity> results = allOrdersBean.getResults();

                        mAllOrderAdapter.update(results);

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


}
