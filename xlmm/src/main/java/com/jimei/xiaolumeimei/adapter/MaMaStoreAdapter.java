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
import com.jimei.xiaolumeimei.entities.ChooseResponseBean;
import com.jimei.xiaolumeimei.entities.MMChooselistBean;
import com.jimei.xiaolumeimei.model.MMProductModel;
import com.jimei.xiaolumeimei.utils.ViewUtils;
import com.jimei.xiaolumeimei.xlmmService.ServiceResponse;
import com.zhy.autolayout.utils.AutoUtils;
import java.util.ArrayList;
import java.util.List;
import rx.schedulers.Schedulers;

/**
 * Created by itxuye(www.itxuye.com) on 2016/02/15.
 *
 * Copyright 2016年 上海己美. All rights reserved.
 */
public class MaMaStoreAdapter extends RecyclerView.Adapter<MaMaStoreAdapter.MaMaStoreVH> {

  private static final String TAG = MMChooseAdapter.class.getSimpleName();

  private List<MMChooselistBean> mList;
  private Context mContext;

  public MaMaStoreAdapter(Context context) {
    this.mContext = context;
    mList = new ArrayList<>();
  }

  public void updateWithClear(List<MMChooselistBean> list) {
    mList.clear();
    mList.addAll(list);
    notifyDataSetChanged();
  }

  public void update(List<MMChooselistBean> list) {

    mList.addAll(list);
    notifyDataSetChanged();
  }

  @Override public MaMaStoreVH onCreateViewHolder(ViewGroup parent, int viewType) {
    View view;

    view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_store, parent, false);
    AutoUtils.autoSize(view);
    return new MaMaStoreVH(view);
  }

  @Override public void onBindViewHolder(MaMaStoreVH holder, int position) {
    MMChooselistBean mmChooselistBean = mList.get(position);
    holder.name.setText(mmChooselistBean.getName());
    ViewUtils.loadImgToImgView(mContext, holder.imageChooselist,
        mmChooselistBean.getPicPath());
    holder.agentPrice.setText("¥" + mmChooselistBean.getAgentPrice());
    holder.stdSalePrice.setText("/¥" + mmChooselistBean.getStdSalePrice());
    holder.rebetAmount.setText("¥" + mmChooselistBean.getRebetAmount());
    holder.lockNum.setText("累积销量 " + mmChooselistBean.getLockNum() + "件");

    holder.remove.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {

        MaterialDialog.Builder content =
            new MaterialDialog.Builder(mContext).content("正在下架.....");

        MaterialDialog show = content.show();

        MMProductModel.getInstance()
            .remove_pro_from_shop(mmChooselistBean.getId() + "")
            .subscribeOn(Schedulers.newThread())
            .subscribe(new ServiceResponse<ChooseResponseBean>() {
              @Override public void onNext(ChooseResponseBean chooseResponseBean) {
                super.onNext(chooseResponseBean);
                if (chooseResponseBean != null) {
                  if (0 == chooseResponseBean.getCode()) {
                    removeAt(position);
                  }
                }
              }

              @Override public void onCompleted() {
                super.onCompleted();
                show.dismiss();
              }

              @Override public void onError(Throwable e) {
                super.onError(e);
                show.dismiss();
              }
            });
      }
    });
  }

  @Override public int getItemCount() {
    return mList == null ? 0 : mList.size();
  }

  //删除某一项
  public void removeAt(int position) {
    mList.remove(position);
    notifyItemRemoved(position);
    notifyItemRangeChanged(position, mList.size());
  }

  static class MaMaStoreVH extends RecyclerView.ViewHolder {

    int id = R.layout.item_store;
    @Bind(R.id.image_chooselist) ImageView imageChooselist;
    @Bind(R.id.name) TextView name;
    @Bind(R.id.agent_price) TextView agentPrice;
    @Bind(R.id.std_sale_price) TextView stdSalePrice;
    @Bind(R.id.rebet_amount) TextView rebetAmount;
    @Bind(R.id.lock_num) TextView lockNum;
    @Bind(R.id.remove) Button remove;

    public MaMaStoreVH(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
