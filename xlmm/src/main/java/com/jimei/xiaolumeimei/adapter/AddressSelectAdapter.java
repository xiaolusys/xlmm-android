package com.jimei.xiaolumeimei.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jimei.library.widget.SmoothCheckBox;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.entities.AddressBean;
import com.jimei.xiaolumeimei.ui.activity.user.ChangeAddressActivity;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by itxuye(www.itxuye.com) on 2016/01/18.
 * <p>
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class AddressSelectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<AddressBean> mList;
    private Activity context;
    private String addressId;
    private boolean idFlag;

    public AddressSelectAdapter(Activity context, String addressId) {
        this.context = context;
        this.addressId = addressId;
        mList = new ArrayList<>();
        idFlag = false;
    }

    public void update(List<AddressBean> list) {

        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void updateWithClear(List<AddressBean> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == 0) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_default_address_select, parent, false);
            return new AddressDefaultVH(view);
        } else if (viewType == 1) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_add_select, parent, false);
            return new AddressVH(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof AddressDefaultVH) {
            AddressDefaultVH defaultVH = (AddressDefaultVH) holder;

            AddressBean addressBean = mList.get(position);
            defaultVH.smoothCheckBox.setCanClickable(false);
            if (addressId != null && addressId.equals(addressBean.getId())) {
                defaultVH.smoothCheckBox.setChecked(true);
            } else {
                defaultVH.smoothCheckBox.setChecked(false);
            }

            defaultVH.receiverMobile.setText(addressBean.getReceiverMobile());
            defaultVH.receiverAddress.setText(addressBean.getReceiverState()
                    + ""
                    + addressBean.getReceiverCity()
                    + ""
                    + addressBean.getReceiverDistrict()
                    + ""
                    + addressBean.getReceiverAddress());
            defaultVH.receiverName.setText(addressBean.getReceiverName());

            ((AddressDefaultVH) holder).card.setOnClickListener(v -> setBundle(addressBean));

            ((AddressDefaultVH) holder).change.setOnClickListener(v -> setBundle1(addressBean));
        } else if (holder instanceof AddressVH) {

            AddressVH addressVH = (AddressVH) holder;

            AddressBean addressBean = mList.get(position);
            addressVH.smoothCheckBox.setCanClickable(false);
            if (addressId != null && addressId.equals(addressBean.getId())) {
                addressVH.smoothCheckBox.setChecked(true);
            } else {
                addressVH.smoothCheckBox.setChecked(false);
            }

            addressVH.receiverMobile.setText(addressBean.getReceiverMobile());
            addressVH.receiverAddress.setText(addressBean.getReceiverState()
                    + ""
                    + addressBean.getReceiverCity()
                    + ""
                    + addressBean.getReceiverDistrict()
                    + ""
                    + addressBean.getReceiverAddress());
            addressVH.receiverName.setText(addressBean.getReceiverName());

            ((AddressVH) holder).card.setOnClickListener(v -> setBundle(addressBean));

            ((AddressVH) holder).change.setOnClickListener(v -> setBundle1(addressBean));
        }
    }

    private void setBundle(AddressBean addressBean) {
        Intent intent = new Intent();
        intent.putExtra("name", addressBean.getReceiverName());
        intent.putExtra("addr_id", addressBean.getId());
        intent.putExtra("phone", addressBean.getReceiverMobile());
        intent.putExtra("addressDetails", addressBean.getReceiverState()
                + addressBean.getReceiverCity()
                + addressBean.getReceiverDistrict()
                + addressBean.getReceiverAddress());
        intent.putExtra("addressId", addressBean.getId());
        intent.putExtra("idNo", addressBean.getmIdentificationNo());
        context.setResult(Activity.RESULT_OK, intent);
        context.finish();
    }

    private void setBundle1(AddressBean addressBean) {
        Intent intent = new Intent(context, ChangeAddressActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("receiver_state", addressBean.getReceiverState());
        bundle.putString("receiver_district", addressBean.getReceiverDistrict());
        bundle.putString("receiver_city", addressBean.getReceiverCity());
        bundle.putBoolean("idFlag", idFlag);
        bundle.putString("receiver_name", addressBean.getReceiverName());
        bundle.putString("id", addressBean.getId());
        bundle.putString("mobile", addressBean.getReceiverMobile());
        bundle.putString("address1", addressBean.getReceiverState()
                + addressBean.getReceiverCity()
                + addressBean.getReceiverDistrict());
        bundle.putString("address2", addressBean.getReceiverAddress());
        bundle.putString("idNo", addressBean.getmIdentificationNo());
        bundle.putBoolean("isDefaultX", addressBean.isDefaultX());
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        AddressBean addressBean = mList.get(position);

        if (addressBean.isDefaultX()) {
            return 0;
        } else {
            return 1;
        }
    }

    public void setIdFlag(boolean idFlag) {
        this.idFlag = idFlag;
    }

    static class AddressDefaultVH extends RecyclerView.ViewHolder {
        @Bind(R.id.receiver_name)
        TextView receiverName;
        @Bind(R.id.receiver_mobile)
        TextView receiverMobile;
        @Bind(R.id.receiver_address)
        TextView receiverAddress;
        @Bind(R.id.change)
        Button change;
        @Bind(R.id.scb)
        SmoothCheckBox smoothCheckBox;

        View card;

        public AddressDefaultVH(View itemView) {
            super(itemView);
            card = itemView;
            AutoUtils.auto(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class AddressVH extends RecyclerView.ViewHolder {
        @Bind(R.id.receiver_name)
        TextView receiverName;
        @Bind(R.id.receiver_mobile)
        TextView receiverMobile;
        @Bind(R.id.receiver_address)
        TextView receiverAddress;
        @Bind(R.id.change)
        Button change;
        @Bind(R.id.scb)
        SmoothCheckBox smoothCheckBox;
        View card;

        public AddressVH(View itemView) {
            super(itemView);
            card = itemView;
            AutoUtils.auto(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
