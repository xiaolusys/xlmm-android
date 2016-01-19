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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.AllOrdersBean;
import com.jimei.xiaolumeimei.model.TradeModel;
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
import com.jimei.xiaolumeimei.adapter.AllRefundsListAdapter;
import com.jimei.xiaolumeimei.data.XlmmApi;

import com.jimei.xiaolumeimei.entities.AllRefundsBean;
import com.jimei.xiaolumeimei.entities.OrderDetailBean;
import com.jimei.xiaolumeimei.entities.RefundDetailBean;

import butterknife.Bind;
import rx.schedulers.Schedulers;


public class AllRefundsActivity extends BaseSwipeBackCompatActivity {
    String TAG = "AllRefundsActivity";
    AllRefundsBean all_refunds_info = new AllRefundsBean();
    @Bind(R.id.toolbar) Toolbar toolbar;
    TradeModel model = new TradeModel();
    LinearLayout rlayout;
    TextView tx_empty_info = new TextView(this);
    private AllRefundsListAdapter mAllRefundsAdapter;

    @Override protected void setListener() {

    }
    @Override protected void getBundleExtras(Bundle extras) {

    }

    @Override protected int getContentViewLayoutID() {
        return R.layout.activity_allrefunds;
    }

    @Override protected void initViews() {
        toolbar.setTitle("退款退货");
        setSupportActionBar(toolbar);
        rlayout = (LinearLayout) findViewById(R.id.llayout_allrefunds);

        //config allorders list adaptor
        ListView all_refunds_listview = (ListView) findViewById(R.id.all_refunds_listview);

        mAllRefundsAdapter = new AllRefundsListAdapter(this);
        all_refunds_listview.setAdapter(mAllRefundsAdapter);
    }
    //从server端获得所有订单数据，可能要查询几次
    @Override protected void initData() {
        model.getRefundsBean()
                .subscribeOn(Schedulers.newThread())
                .subscribe(new ServiceResponse<AllRefundsBean>() {
                    @Override public void onNext(AllRefundsBean allRefundsBean) {
                        List<AllRefundsBean.ResultsEntity> results = allRefundsBean.getResults();
                        if (0 == results.size()){
                            fillEmptyInfo();
                        }
                        else
                        {
                            tx_empty_info.setVisibility(View.GONE);
                            mAllRefundsAdapter.update(results);
                        }

                        Log.i(TAG, allRefundsBean.toString());
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
        tx_empty_info.setText("亲，你还没有任何退货单！");

        rlayout.addView(tx_empty_info);
    }


}
