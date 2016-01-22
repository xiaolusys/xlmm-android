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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jimei.xiaolumeimei.adapter.AllOrdersListAdapter;
import com.jimei.xiaolumeimei.adapter.WaitPayOrdersListAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.model.TradeModel;
import com.jimei.xiaolumeimei.ui.activity.main.MainActivity;
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


public class WaitPayOrdersActivity extends BaseSwipeBackCompatActivity implements View.OnClickListener{
    String TAG = "WaitPayOrdersActivity";

    @Bind(R.id.btn_jump)    Button btn_jump;
    TradeModel model = new TradeModel();
    private WaitPayOrdersListAdapter mAllOrderAdapter;

    @Override protected void setListener() {
        btn_jump.setOnClickListener(this);
    }
    @Override protected void getBundleExtras(Bundle extras) {

    }

    @Override protected int getContentViewLayoutID() {
        return R.layout.activity_waitpayorders;
    }

    @Override protected void initViews() {

        ListView all_orders_listview = (ListView) findViewById(R.id.all_orders_listview);

        mAllOrderAdapter = new WaitPayOrdersListAdapter(this);
        all_orders_listview.setAdapter(mAllOrderAdapter);

        TextView tx_info = (TextView) findViewById(R.id.tx_info);
        tx_info.setText("亲，您暂时还没有待支付订单哦~快去看看吧！");
    }
    //从server端获得所有订单数据，可能要查询几次
    @Override protected void initData() {
        model.getWaitPayOrdersBean()
                .subscribeOn(Schedulers.newThread())
                .subscribe(new ServiceResponse<AllOrdersBean>() {
                    @Override public void onNext(AllOrdersBean allOrdersBean) {
                        List<AllOrdersBean.ResultsEntity> results = allOrdersBean.getResults();
                        if (0 == results.size()){

                        }
                        else
                        {
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_jump:
                Intent intent = new Intent(WaitPayOrdersActivity.this, MainActivity.class);
                startActivity(intent);
                break;

        }
    }
}
