package com.jimei.xiaolumeimei.ui.activity.trade;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jimei.xiaolumeimei.adapter.WaitSendOrdersListAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.model.TradeModel;
import com.jimei.xiaolumeimei.ui.activity.main.MainActivity;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;

import java.util.List;

import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.entities.AllOrdersBean;

import butterknife.Bind;
import rx.schedulers.Schedulers;


public class WaitSendOrdersActivity extends BaseSwipeBackCompatActivity implements View.OnClickListener{
    String TAG = "WaitSendOrdersActivity";
    @Bind(R.id.btn_jump)    Button btn_jump;
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.rlayout_order_empty) RelativeLayout rl_empty;

    TradeModel model = new TradeModel();
    private WaitSendOrdersListAdapter mAllOrderAdapter;


    @Override protected void setListener() {
        btn_jump.setOnClickListener(this);
        toolbar.setOnClickListener(this);
    }
    @Override protected void getBundleExtras(Bundle extras) {

    }

    @Override protected int getContentViewLayoutID() {
        return R.layout.activity_waitsendorders;
    }

    @Override protected void initViews() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.back);

        ListView all_orders_listview = (ListView) findViewById(R.id.all_orders_listview);

        mAllOrderAdapter = new WaitSendOrdersListAdapter(this);
        all_orders_listview.setAdapter(mAllOrderAdapter);

        TextView tx_info = (TextView) findViewById(R.id.tx_info);
        tx_info.setText("亲，您暂时还没有待收货订单哦~快去看看吧！");
    }
    //从server端获得所有订单数据，可能要查询几次
    @Override protected void initData() {
        model.getWaitSendOrdersBean()
                .subscribeOn(Schedulers.newThread())
                .subscribe(new ServiceResponse<AllOrdersBean>() {
                    @Override public void onNext(AllOrdersBean allOrdersBean) {
                        List<AllOrdersBean.ResultsEntity> results = allOrdersBean.getResults();
                        if (0 == results.size()){
                            rl_empty.setVisibility(View.VISIBLE);
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
                Intent intent = new Intent(WaitSendOrdersActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.toolbar:
                finish();
                break;

        }
    }
}
