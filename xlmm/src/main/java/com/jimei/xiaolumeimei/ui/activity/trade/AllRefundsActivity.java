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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.AllOrdersBean;
import com.jimei.xiaolumeimei.model.TradeModel;
import com.jimei.xiaolumeimei.ui.activity.main.MainActivity;
import com.jimei.xiaolumeimei.ui.activity.user.MembershipPointActivity;
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


public class AllRefundsActivity extends BaseSwipeBackCompatActivity implements View.OnClickListener{
    String TAG = "AllRefundsActivity";

    @Bind(R.id.btn_jump) Button btn_jump;
    TradeModel model = new TradeModel();
    private AllRefundsListAdapter mAllRefundsAdapter;

    @Override protected void setListener() {
        btn_jump.setOnClickListener(this);
    }
    @Override protected void getBundleExtras(Bundle extras) {

    }

    @Override protected int getContentViewLayoutID() {
        return R.layout.activity_allrefunds;
    }

    @Override protected void initViews() {
        //config allorders list adaptor
        ListView all_refunds_listview = (ListView) findViewById(R.id.all_refunds_listview);
        mAllRefundsAdapter = new AllRefundsListAdapter(this);
        all_refunds_listview.setEmptyView(findViewById(R.id.rlayout_order_empty));
        all_refunds_listview.setAdapter(mAllRefundsAdapter);
        all_refunds_listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                Log.d(TAG, "onItemClick "+arg2 + " " + arg3);
                int order_id = mAllRefundsAdapter.getOrderId(arg2);
                Intent intent = new Intent(AllRefundsActivity.this, RefundDetailActivity.class);

                intent.putExtra("orderinfo", order_id);
                Log.d(TAG,"transfer orderid  "+order_id+" to OrderDetailActivity");
                startActivity(intent);
            }

        });

        TextView tx_info = (TextView) findViewById(R.id.tx_info);
        tx_info.setText("亲，您暂时还没有退货（款）订单哦~快去看看吧！");

    }
    //从server端获得所有订单数据，可能要查询几次
    @Override protected void initData() {
        model.getRefundsBean()
                .subscribeOn(Schedulers.newThread())
                .subscribe(new ServiceResponse<AllRefundsBean>() {
                    @Override public void onNext(AllRefundsBean allRefundsBean) {
                        List<AllRefundsBean.ResultsEntity> results = allRefundsBean.getResults();
                        if (0 == results.size()){
                            Log.d(TAG," NO redunds data");
                        }
                        else
                        {
                            Log.d(TAG," redunds data num " + results.size());

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_jump:
                Intent intent = new Intent(AllRefundsActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                break;

        }
    }
}
