package com.jimei.xiaolumeimei.ui.activity.user;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.jimei.library.widget.RecyclerViewDivider;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.adapter.AddressAdapter;
import com.jimei.xiaolumeimei.base.BaseSwipeBackCompatActivity;
import com.jimei.xiaolumeimei.entities.AddressBean;
import com.jimei.xiaolumeimei.model.AddressModel;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import butterknife.Bind;
import rx.Subscription;

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
    private AddressAdapter adapter;

    @Override
    protected void setListener() {
        addAdress.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(this.getClass().getSimpleName());
        MobclickAgent.onResume(this);
        Subscription subscribe = AddressModel.getInstance()
                .getAddressList()
                .subscribe(new ServiceResponse<List<AddressBean>>() {
                    @Override
                    public void onNext(List<AddressBean> list) {
                        super.onNext(list);
                        if (list != null) {
                            adapter.updateWithClear(list);
                        }
                    }
                });
        addSubscription(subscribe);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.address_activity;
    }


    @Override
    protected void initViews() {
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


    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
        MobclickAgent.onPause(this);
    }
}
