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
import cn.sharesdk.onekeyshare.OnekeyShare;
import com.afollestad.materialdialogs.MaterialDialog;
import com.jimei.xiaolumeimei.R;
import com.jimei.xiaolumeimei.entities.MMChooselistBean;
import com.jimei.xiaolumeimei.entities.ResponseResultBean;
import com.jimei.xiaolumeimei.entities.ShareProductBean;
import com.jimei.xiaolumeimei.model.MMProductModel;
import com.jimei.xiaolumeimei.model.ProductModel;
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
  private MaterialDialog materialDialog;

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
    holder.rebetAmount.setText(
        "¥" + (float) (Math.round(mmChooselistBean.getRebetAmount() * 100)) / 100);
    holder.lockNum.setText("累积销量 " + mmChooselistBean.getLockNum() + "件");

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
                    removeAt(position);
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
              }
            });
      }
    });

    holder.btnShare.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {

        ProductModel.getInstance()
            .getProductShareInfo(mmChooselistBean.getId() + "")
            .subscribeOn(Schedulers.io())
            .subscribe(new ServiceResponse<ShareProductBean>() {

              @Override public void onNext(ShareProductBean productBean) {

                if (null != productBean) {
                  OnekeyShare oks = new OnekeyShare();
                  //关闭sso授权
                  oks.disableSSOWhenAuthorize();
                  oks.setTitle(productBean.getTitle());
                  // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
                  oks.setTitleUrl(productBean.getShareLink());
                  // text是分享文本，所有平台都需要这个字段
                  oks.setText(productBean.getDesc());
                  // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
                  //oks.setImagePath(filePara.getFilePath());//确保SDcard下面存在此张图片
                  //oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
                  oks.setImageUrl(productBean.getShareImg());
                  oks.setUrl(productBean.getShareLink());

                  // 启动分享GUI
                  oks.show(mContext);
                }
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

  public void showIndeterminateProgressDialog(boolean horizontal) {
    materialDialog = new MaterialDialog.Builder(mContext)
        //.title(R.string.progress_dialog)
        .content(R.string.please_wait)
        .progress(true, 0)
        .progressIndeterminateStyle(horizontal)
        .show();
  }

  public void hideIndeterminateProgressDialog() {
    materialDialog.dismiss();
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
    @Bind(R.id.btn_share) Button btnShare;

    public MaMaStoreVH(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
