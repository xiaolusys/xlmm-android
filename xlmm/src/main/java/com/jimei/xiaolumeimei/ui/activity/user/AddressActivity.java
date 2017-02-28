package com.jimei.xiaolumeimei.ui.activity.user;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.jimei.library.widget.RecyclerViewDivider;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.XlmmApp;
import com.jimei.xiaolumeimei.adapter.AddressAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.AddressBean;
import com.jimei.xiaolumeimei.entities.event.AddressChangeEvent;
import com.jimei.xiaolumeimei.service.ServiceResponse;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.Bind;

/**
 * Created by itxuye(www.itxuye.com) on 2016/01/18.
 * <p>
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class AddressActivity extends BaseSwipeBackCompatActivity
    implements View.OnClickListener {

    @Bind(R.id.address_recyclerView)
    RecyclerView addressRecyclerView;
    @Bind(R.id.addAdress)
    Button addAdress;
    @Bind(R.id.layout)
    LinearLayout layout;
    private AddressAdapter adapter;

    @Override
    protected void setListener() {
        addAdress.setOnClickListener(this);
    }

    @Override
    public View getLoadingView() {
        return layout;
    }

    @Override
    protected void initData() {
        addSubscription(XlmmApp.getAddressInteractor(this)
            .getAddressList(new ServiceResponse<List<AddressBean>>() {
                @Override
                public void onNext(List<AddressBean> list) {
                    super.onNext(list);
                    if (list != null) {
                        adapter.updateWithClear(list);
                    }
                }
            }));
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.address_activity;
    }


    @Override
    protected void initViews() {
        EventBus.getDefault().register(this);
        addressRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerViewDivider divider = new RecyclerViewDivider(RecyclerViewDivider.VERTICAL);
        divider.setSize(3);
        divider.setColor(getResources().getColor(R.color.bg_grey));
        addressRecyclerView.addItemDecoration(divider);
        adapter = new AddressAdapter(this);
        addressRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addAdress:
                startActivity(new Intent(AddressActivity.this, AddAddressActivity.class));
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshAddress(AddressChangeEvent event) {
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
