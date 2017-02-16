package com.jimei.xiaolumeimei.ui.activity.user;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.jimei.library.widget.RecyclerViewDivider;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.AddressSelectAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.AddressBean;
import com.jimei.xiaolumeimei.entities.event.AddressChangeEvent;
import com.jimei.xiaolumeimei.model.AddressModel;
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
public class AddressSelectActivity extends BaseSwipeBackCompatActivity
        implements View.OnClickListener {

    @Bind(R.id.address_recyclerView)
    RecyclerView addressRecyclerView;
    @Bind(R.id.addAdress)
    Button addAdress;
    @Bind(R.id.layout)
    LinearLayout layout;
    private AddressSelectAdapter adapter;
    private String addressId;
    private boolean idFlag;

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
        showIndeterminateProgressDialog(false);
        addSubscription(AddressModel.getInstance()
                .getAddressList()
                .subscribe(new ServiceResponse<List<AddressBean>>() {
                    @Override
                    public void onNext(List<AddressBean> list) {
                        super.onNext(list);
                        if (list != null) {
                            adapter.updateWithClear(list);
                            hideIndeterminateProgressDialog();
                        }
                    }
                }));
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        addressId = extras.getString("addressId");
        idFlag = extras.getBoolean("idFlag", false);
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
        adapter = new AddressSelectAdapter(this, addressId);
        adapter.setIdFlag(idFlag);
        addressRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshAddress(AddressChangeEvent event) {
        initData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addAdress:
                Bundle addressBundle = new Bundle();
                addressBundle.putBoolean("idFlag", idFlag);
                readyGo(AddAddressActivity.class, addressBundle);
                break;
        }
    }


}
