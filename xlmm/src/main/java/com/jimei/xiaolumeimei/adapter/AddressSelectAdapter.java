package com.jimei.xiaolumeimei.adapter;

import android.app.Activity;
import android.content.Intent;
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
public class AddressSelectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private static final String TAG = AddressSelectAdapter.class.getSimpleName();
  private List<AddressBean> mList;
  private Activity context;

  public AddressSelectAdapter(Activity context) {
    this.context = context;
    mList = new ArrayList<>();
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

      ((AddressDefaultVH) holder).card.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          setBundle(addressBean);
        }
      });
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

      ((AddressVH) holder).card.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          setBundle(addressBean);
        }
      });
    }
  }

  private void setBundle(AddressBean addressBean) {
    Intent intent = new Intent();
    //Bundle bundle = new Bundle();
    //bundle.putString("receiver_state", addressBean.getReceiverState());
    //bundle.putString("receiver_district", addressBean.getReceiverDistrict());
    //bundle.putString("receiver_city", addressBean.getReceiverCity());
    //
    //bundle.putString("receiver_name", addressBean.getReceiverName());
    //bundle.putString("id", addressBean.getId());
    //bundle.putString("mobile", addressBean.getReceiverMobile());
    //bundle.putString("address1", addressBean.getReceiverState()
    //    + addressBean.getReceiverCity()
    //    + addressBean.getReceiverDistrict());
    //bundle.putString("address2", addressBean.getReceiverAddress());

    intent.putExtra("name", addressBean.getReceiverName());
    intent.putExtra("addr_id", addressBean.getId());
    intent.putExtra("phone", addressBean.getReceiverMobile());
    intent.putExtra("addressDetails", addressBean.getReceiverState()
        + addressBean.getReceiverCity()
        + addressBean.getReceiverDistrict()
        + addressBean.getReceiverAddress());

    context.setResult(Activity.RESULT_OK, intent);
    context.finish();
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
    @Bind(R.id.address_defalut) TextView addressDefalut;
    @Bind(R.id.receiver_name) TextView receiverName;
    @Bind(R.id.receiver_mobile) TextView receiverMobile;
    @Bind(R.id.receiver_address) TextView receiverAddress;

    View card;

    public AddressDefaultVH(View itemView) {
      super(itemView);
      card = itemView;
      ButterKnife.bind(this, itemView);
    }
  }

  static class AddressVH extends RecyclerView.ViewHolder {
    @Bind(R.id.receiver_name) TextView receiverName;
    @Bind(R.id.receiver_mobile) TextView receiverMobile;
    @Bind(R.id.receiver_address) TextView receiverAddress;
    View card;

    public AddressVH(View itemView) {
      super(itemView);
      card = itemView;
      ButterKnife.bind(this, itemView);
    }
  }
}
