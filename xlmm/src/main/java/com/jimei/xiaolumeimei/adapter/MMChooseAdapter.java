package com.jimei.xiaolumeimei.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.afollestad.materialdialogs.MaterialDialog;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.entities.MMChooselistBean;
import com.jimei.xiaolumeimei.entities.ResponseResultBean;
import com.jimei.xiaolumeimei.model.MMProductModel;
import com.jimei.xiaolumeimei.utils.ViewUtils;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.zhy.autolayout.utils.AutoUtils;
import java.util.ArrayList;
import java.util.List;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye(www.itxuye.com) on 2016/02/14.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class MMChooseAdapter extends RecyclerView.Adapter<MMChooseAdapter.MMChooseVH> {

  private static final String TAG = MMChooseAdapter.class.getSimpleName();

  private List<MMChooselistBean.ResultsEntity> mList;
  private Context mContext;
  private MaterialDialog materialDialog;

  public MMChooseAdapter(Context context) {
    this.mContext = context;
    mList = new ArrayList<>();
  }

  public void updateWithClear(List<MMChooselistBean.ResultsEntity> list) {
    mList.clear();
    mList.addAll(list);
    notifyDataSetChanged();
  }

  public void update(List<MMChooselistBean.ResultsEntity> list) {

    mList.addAll(list);
    notifyDataSetChanged();
  }

  @Override public MMChooseVH onCreateViewHolder(ViewGroup parent, int viewType) {
    View view;

    view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_chooselist, parent, false);
    AutoUtils.autoSize(view);
    return new MMChooseVH(view);
  }

  @Override public void onBindViewHolder(MMChooseVH holder, int position) {
    MMChooselistBean.ResultsEntity mmChooselistBean = mList.get(position);
    holder.name.setText(mmChooselistBean.getName());
    ViewUtils.loadImgToImgView(mContext, holder.imageChooselist,
        mmChooselistBean.getPicPath());
    holder.agentPrice.setText(
        "¥" + (float) (Math.round(mmChooselistBean.getAgentPrice() * 100)) / 100);
    holder.stdSalePrice.setText(
        "/¥" + (float) (Math.round(mmChooselistBean.getStdSalePrice() * 100)) / 100);
    holder.rebetAmount.setText(mmChooselistBean.getRebetAmountDes() );
    holder.lockNum.setText(mmChooselistBean.getSaleNumDes());

    int inCustomerShop = mmChooselistBean.getInCustomerShop();
    if (0 == inCustomerShop) {
      holder.add.setVisibility(View.VISIBLE);
      holder.remove.setVisibility(View.INVISIBLE);
      holder.add.setClickable(true);
      holder.remove.setClickable(false);

      holder.add.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {

          showIndeterminateProgressDialog(false);
          MMProductModel.getInstance()
              .add_pro_to_shop(mmChooselistBean.getId() + "")
              .subscribeOn(Schedulers.io())
              .subscribe(new ServiceResponse<ResponseResultBean>() {
                @Override public void onNext(ResponseResultBean responseResultBean) {
                  super.onNext(responseResultBean);
                  if (responseResultBean != null) {
                    if (0 == responseResultBean.getCode()) {
                      holder.add.setVisibility(View.INVISIBLE);
                      holder.remove.setVisibility(View.VISIBLE);
                      holder.add.setClickable(false);
                      holder.remove.setClickable(true);
                      mmChooselistBean.setInCustomerShop(1);
                      notifyItemChanged(position);
                    }
                  }
                }

                @Override public void onCompleted() {
                  super.onCompleted();
                  hideIndeterminateProgressDialog();
                }

                @Override public void onError(Throwable e) {
                  super.onError(e);
                  e.printStackTrace();
                  hideIndeterminateProgressDialog();
                }
              });
        }
      });
    } else if (1 == inCustomerShop) {
      holder.add.setVisibility(View.INVISIBLE);
      holder.remove.setVisibility(View.VISIBLE);
      holder.add.setClickable(false);
      holder.remove.setClickable(true);

      holder.remove.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {

          showIndeterminateProgressDialog(false);
          MMProductModel.getInstance()
              .remove_pro_from_shop(mmChooselistBean.getId() + "")
              .subscribeOn(Schedulers.io())
              .subscribe(new ServiceResponse<ResponseResultBean>() {
                @Override public void onNext(ResponseResultBean responseResultBean) {
                  super.onNext(responseResultBean);
                  if (responseResultBean != null) {
                    if (0 == responseResultBean.getCode()) {
                      holder.add.setVisibility(View.VISIBLE);
                      holder.remove.setVisibility(View.INVISIBLE);
                      holder.add.setClickable(true);
                      holder.remove.setClickable(false);
                      mmChooselistBean.setInCustomerShop(0);
                      notifyItemChanged(position);
                    }
                  }
                }

                @Override public void onCompleted() {
                  super.onCompleted();
                  hideIndeterminateProgressDialog();
                }

                @Override public void onError(Throwable e) {
                  super.onError(e);
                  e.printStackTrace();
                  hideIndeterminateProgressDialog();
                }
              });
        }
      });
    }
  }

  @Override public int getItemCount() {
    return mList == null ? 0 : mList.size();
  }

  public void showIndeterminateProgressDialog(boolean horizontal) {
    materialDialog = new MaterialDialog.Builder(mContext)
        //.title(R.string.progress_dialog)
        .content(R.string.please_wait)
        .progress(true, 0)
        .widgetColorRes(R.color.colorAccent)
        .progressIndeterminateStyle(horizontal)
        .show();
  }

  public void hideIndeterminateProgressDialog() {
    materialDialog.dismiss();
  }

  static class MMChooseVH extends RecyclerView.ViewHolder {
    int id = R.layout.item_chooselist;
    @Bind(R.id.image_chooselist) ImageView imageChooselist;
    @Bind(R.id.name) TextView name;
    @Bind(R.id.agent_price) TextView agentPrice;
    @Bind(R.id.std_sale_price) TextView stdSalePrice;
    @Bind(R.id.rebet_amount) TextView rebetAmount;
    @Bind(R.id.lock_num) TextView lockNum;
    @Bind(R.id.add) Button add;
    @Bind(R.id.remove) Button remove;

    public MMChooseVH(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
