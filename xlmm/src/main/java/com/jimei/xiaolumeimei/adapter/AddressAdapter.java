package com.jimei.xiaolumeimei.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.afollestad.materialdialogs.MaterialDialog;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.entities.AddressBean;
import com.jimei.xiaolumeimei.ui.activity.user.ChanggeAddressActivity;
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

        }
      });

      ((AddressDefaultVH) holder).card.setOnLongClickListener(
          new View.OnLongClickListener() {
            @Override public boolean onLongClick(View v) {

              new MaterialDialog.Builder(context).
                  title("删除地址").
                  content("您确定要删除吗？").
                  positiveText("确定").
                  negativeText("取消").
                  callback(new MaterialDialog.ButtonCallback() {
                    @Override public void onPositive(MaterialDialog dialog) {



                      dialog.dismiss();
                    }

                    @Override public void onNegative(MaterialDialog dialog) {
                      dialog.dismiss();
                    }
                  }).show();

              return false;
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
          Intent intent = new Intent(context, ChanggeAddressActivity.class);
        }
      });

      ((AddressVH) holder).card.setOnLongClickListener(new View.OnLongClickListener() {
            @Override public boolean onLongClick(View v) {

              new MaterialDialog.Builder(context).
                  title("删除地址").
                  content("您确定要删除吗？").
                  positiveText("确定").
                  negativeText("取消").
                  callback(new MaterialDialog.ButtonCallback() {
                    @Override public void onPositive(MaterialDialog dialog) {

                      dialog.dismiss();
                    }

                    @Override public void onNegative(MaterialDialog dialog) {
                      dialog.dismiss();
                    }
                  }).show();


              return false;
            }
          });
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
