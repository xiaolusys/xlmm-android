package com.jimei.xiaolumeimei.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.entities.AddressBean;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by itxuye(www.itxuye.com) on 2016/01/18.
 *
 * Copyright 2015年 上海己美. All rights reserved.
 */
public class AddressAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private List<AddressBean> mList;

  private Context context;

  public AddressAdapter(Context context) {
    this.context = context;
    mList = new ArrayList<>();
  }

  public void update(List<AddressBean> list) {

    mList.addAll(list);
    notifyDataSetChanged();
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view;
    if (viewType == 0) {
      view = LayoutInflater.from(parent.getContext())
          .inflate(R.layout.item_default_address, parent, false);
      return new AddressDefaultVH(view);
    } else if (viewType == 1) {
      view = LayoutInflater.from(parent.getContext())
          .inflate(R.layout.item_add, parent, false);
      return new AddressVH(view);
    }

    return null;
  }

  @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    if (holder instanceof AddressDefaultVH) {
      AddressDefaultVH defaultVH = (AddressDefaultVH) holder;

      AddressBean addressBean = mList.get(position);

      defaultVH.receiverMobile.setText(addressBean.getReceiverMobile());
      defaultVH.receiverAddress.setText(addressBean.getReceiverState()
          + ""
          + addressBean.getReceiverCity()
          + ""
          + addressBean.getReceiverDistrict()
          + ""
          + addressBean.getReceiverAddress());
      defaultVH.receiverName.setText(addressBean.getReceiverName());
    } else if (holder instanceof AddressVH) {

      AddressVH addressVH = (AddressVH) holder;

      AddressBean addressBean = mList.get(position);

      addressVH.receiverMobile.setText(addressBean.getReceiverMobile());
      addressVH.receiverAddress.setText(addressBean.getReceiverState()
          + ""
          + addressBean.getReceiverCity()
          + ""
          + addressBean.getReceiverDistrict()
          + ""
          + addressBean.getReceiverAddress());
      addressVH.receiverName.setText(addressBean.getReceiverName());
    }
  }

  @Override public int getItemCount() {
    return mList == null ? 0 : mList.size();
  }

  @Override public int getItemViewType(int position) {
    AddressBean addressBean = mList.get(position);

    if (addressBean.isDefaultX()) {
      return 0;
    } else {
      return 1;
    }
  }

  static class AddressDefaultVH extends RecyclerView.ViewHolder {
    int id = R.layout.item_default_address;
    @Bind(R.id.address_defalut) TextView addressDefalut;
    @Bind(R.id.receiver_name) TextView receiverName;
    @Bind(R.id.receiver_mobile) TextView receiverMobile;
    @Bind(R.id.receiver_address) TextView receiverAddress;

    public AddressDefaultVH(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }

  static class AddressVH extends RecyclerView.ViewHolder {
    int id = R.layout.item_add;
    @Bind(R.id.receiver_name) TextView receiverName;
    @Bind(R.id.receiver_mobile) TextView receiverMobile;
    @Bind(R.id.receiver_address) TextView receiverAddress;

    public AddressVH(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
