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
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
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


public class AllRefundsActivity extends AppCompatActivity {
    String TAG = "RefundsActivity";
    AllRefundsBean all_refunds_info = new AllRefundsBean();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        Log.d(TAG,"onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allorders);
        this.setTitle("退款退货");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //get all orders info from server


        //config allorders list adaptor
        ListView all_refunds_listview = (ListView) findViewById(R.id.all_refunds_listview);

        AllRefundsListAdapter all_refunds_adapter = new AllRefundsListAdapter(this, all_refunds_info.getResults());
        all_refunds_listview.setAdapter(all_refunds_adapter);
        all_refunds_listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                Log.d(TAG, "onItemClick "+arg2 + " " + arg3);
                Intent intent = new Intent(AllRefundsActivity.this, RefundDetailActivity.class);
                //intent.putExtra("refunddetail", one_refund_detail);
                startActivity(intent);
            }

        });
    }



    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        if (item.getItemId() == android.R.id.home) {
            Log.d(TAG,"nav back");
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //从server端获得所有refund订单数据，可能要查询几次




}
